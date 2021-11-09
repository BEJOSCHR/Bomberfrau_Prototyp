/*
 * Game
 *
 * Version 1.0
 * Author: Benni
 *
 * Das Objekt das ein Game für den Server repräsentiert, vorallem seine Spieler und die Map
 */

package uni.bombenstimmung.de.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.Color;
import java.awt.Graphics;

import uni.bombenstimmung.de.graphics.GraphicsData;
import uni.bombenstimmung.de.graphics.GraphicsHandler;
import uni.bombenstimmung.de.handler.MovementHandler;
import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.objects.Player;
import uni.bombenstimmung.de.serverconnection.ConnectionData;
import uni.bombenstimmung.de.serverconnection.ConnectionType;

public class Game {

	//Local Client Data
	private Color color = Color.RED;
	private int moveX = (GameData.MAP_DIMENSION/2)*GameData.FIELD_DIMENSION, moveY = moveX;
	
	//Other Client Data
	private List<Player> players = new ArrayList<Player>();
	
	//General Data
	private boolean gameHasStarted = false;
	private boolean movementAllowed = true;
	private int timeRemaining = GameData.COUNTDOWN_DURATION;
	private boolean thisClientIsHost = false;
	private String mapData = "";
	private Field map[][] = new Field[GameData.MAP_DIMENSION][GameData.MAP_DIMENSION];
	private List<Color> freeColors = new ArrayList<Color>();
	
	/**
	 * Das SPiel Objekt in dem das eigentlich Spiel abläuft.
	 * verwaltet hauptsächlich die Map und die Spieler.
	 * @param host - Gibt an ob dieser Client der host von dem Spiel ist oder beigetreten ist
	 */
	public Game(boolean host) {
		
		this.thisClientIsHost = host;
		
		if(GameData.runningGame != null) {
			ConsoleDebugger.printMessage("Cant create a new game, beacause the old one is still running! (Local variable is not null)");
			return;
		}
		
		GameData.isRunning = true;
		GameData.runningGame = this;
		
		//FILL FREECOLORS - RED already used
		this.freeColors.add(Color.YELLOW);
		this.freeColors.add(Color.BLUE);
		this.freeColors.add(Color.ORANGE);
		
		//FILL MAP
		for(int x = 0 ; x < GameData.MAP_DIMENSION ; x++) {
			for(int y = 0 ; y < GameData.MAP_DIMENSION ; y++) {
				this.map[x][y] = new Field(x, y, FieldType.DEFAULT);
			}
		}
		resettMap();
		
		//START MOVEMENT HANDLER
		MovementHandler.startMovementTimer();
		
	}
	
	/**
	 * Startet das Spiel, danach können keine Spieler mehr joinen!
	 * Kann nur vom host aufgerufen werden!
	 */
	public void startGame() { 
		
		if(this.thisClientIsHost == false) {
			ConsoleDebugger.printMessage("A none host tried to start the game!");
			return;
		}
		
		//TODO TP PLAYER vie updatePlayerPos
		
		//TODO SEND MAP
		mapData = GameData.MAP_1; //TODO CHOOSE MAPE
		for(Player player : this.players) {
			player.sendMessage(101, mapData);
		}
		updateMap(mapData);
		
		//SEND COUNTDOWN START TO OTHERS
		for(Player player : this.players) {
			player.sendMessage(300, "Countdown Start");
		}
		startCountdown();
		
	}
	/**
	 * Wird vom Spielstart/ConnectionClient aufgerufen und gibt nach einiger Zeit die Bewegung frei
	 */
	public void startCountdown() { 
		
		this.timeRemaining = GameData.COUNTDOWN_DURATION;
		this.movementAllowed = false;
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				timeRemaining--;
				if(timeRemaining == 0) {
					this.cancel();
					movementAllowed = true;
					gameHasStarted = true;
				}
			}
		}, 0, 1000);
		
	}
	
	/**
	 * Updatet die Spielerposition
	 * @param playerID - int - Die ID von dem sich bewegenden Spieler
	 * @param newMoveFactorX - int - Der neue X-Movefaktor
	 * @param newMoveFactorY - int - Der neue Y-Movefaktor
	 */
	public void updatePlayerPos(int playerID, int newMoveFactorX, int newMoveFactorY) {
		
		boolean updatedPlayer = false;
		
		for(Player player : this.players) {
			
			if(player.getId() == playerID) {
				player.setX(newMoveFactorX);
				player.setY(newMoveFactorY);
				updatedPlayer = true;
			}
			if(ConnectionData.connectionType == ConnectionType.SERVER) {
				player.sendMessage(400, playerID+":"+newMoveFactorX+":"+newMoveFactorY);
			}
		}
		
		if(updatedPlayer == false) {
			//NO ONE UPDATE SO THIS CLIENT
			this.moveX = newMoveFactorX;
			this.moveY = newMoveFactorY;
		}
		
	}
	
	/**
	 * Wird nur aufgerufen vom Server wenn sich jemand neues einloggt in das game. 
	 * Hier wird demjenigen dann der derzeitige Spielstand übermittelt und ihm eine farbe zugewiesen.
	 * Es können maximal 3 Spieler regestriert werden! (3 + Lokalen Spieler = 4 Player)
	 * @param newPlayer - Der Neue Spieler
	 */
	public void registerPlayer(Player newPlayer) {
		
		if(this.getPlayerCount() >= 4) {
			//TODO SEND INFO TO NEW PLAYER
			return;
		}else if(this.gameHasStarted == true) {
			//TODO ALLREADY STARTED
			return;
		}
		
		newPlayer.setColor(getNextFreeColor());
		newPlayer.sendMessage(101, mapData);
		
		//SEND DATA TO NEW PLAYER
		newPlayer.sendMessage(100, newPlayer.getColor().getRed()+","+newPlayer.getColor().getGreen()+","+newPlayer.getColor().getBlue());
		//SEND ALREADY REGISTERED PLAYER
		for(Player player : this.players) {
			newPlayer.sendMessage(200, player.convertToStringData());
		}
		//SEND LOKAL PLAYER
		newPlayer.sendMessage(200, 0+":"+this.color.getRed()+","+this.color.getGreen()+","+this.color.getBlue()+":"+this.moveX+":"+this.moveY);
		//SEND DATA TO OTHER PLAYER ABOUT NEW PLAYER
		for(Player player : this.players) {
			player.sendMessage(200, newPlayer.convertToStringData());
		}
		
		this.players.add(newPlayer);
		
	}
	/**
	 * Fügt einen Spieler LOKAL zum Spiel hinzu
	 * @param newPlayer - Der neue Spieler
	 */
	public void addPlayer(Player newPlayer) {
		
		this.players.add(newPlayer);
		
	}
	
	/**
	 * Wird nur aufgerufen vom Server wenn sich jemand ausloggt aus dem game. 
	 * Hier wird derjenige dann entfernt und seine Farbe wieder frei gesetzt
	 * @param playerID - Die ID von dem der sich ausloggt
	 */
	public void unregisterPlayer(int playerID) {
		
		for(Player player : this.players) {
			if(player.getId() == playerID) {
				this.freeColors.add(player.getColor());
				this.players.remove(player);
				//SEND LEAVE TO ALL OTHER PLAYER
				for(Player otherPlayer : this.players) {
					otherPlayer.sendMessage(201, player.getId()+"");
				}
				return;
			}
		}
		ConsoleDebugger.printMessage("Couldnt find player to remove in game for the id '"+playerID+"'!");
		
	}
	/**
	 * Entfernt diesen Spieler LOKAL aus dem game
	 * @param playerID - Die ID von dem Spieler der entfernt werden soll
	 */
	public void removePlayer(int playerID) {
		
		for(Player player : this.players) {
			if(player.getId() == playerID) {
				this.players.remove(player);
				return;
			}
		}
		ConsoleDebugger.printMessage("Couldnt find player to remove in game for the id '"+playerID+"'!");
		
	}
	
	/**
	 * Der Teil der vom Label aufgerufen wird und das Spiel grafisch darstellt
	 * @param g - Das Graphics Object
	 */
	public void drawGame(Graphics g) {
		
		//DRAW COUNTDOWN ETC
		
		//MAP
		for(int y = GameData.MAP_DIMENSION-1 ; y >= 0 ; y--) {
			for(int x = GameData.MAP_DIMENSION-1 ; x >= 0 ; x--) {
				this.map[x][y].draw(g);
			}
		}
		
		//OTHER PLAYER
		for(Player player : this.players) {
			player.draw(g);
		}
		
		//LOKAL PLAYER
		int playerX = GraphicsHandler.getPlayerCoordianteByMoveFactor(moveX, true);
		int playerY = GraphicsHandler.getPlayerCoordianteByMoveFactor(moveY, false);
		g.setColor(this.color);
		g.fillOval(playerX-GameData.FIELD_DIMENSION/2, playerY-GameData.FIELD_DIMENSION/2, GameData.PLAYER_DIMENSION, GameData.PLAYER_DIMENSION);		
		try {
			this.map[GraphicsHandler.getCoordianteByPixel(playerX, true)][GraphicsHandler.getCoordianteByPixel(playerY, false)].drawHighlight(g, this.color);
		}catch(IndexOutOfBoundsException error) {}
		
		if(this.isGameStarted() == false) {
			int sectioHeight = 60;
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, GraphicsData.height-sectioHeight, GraphicsData.width, sectioHeight);
			if(this.movementAllowed == false) {
				GraphicsHandler.drawCentralisedText(g, Color.ORANGE, 30, ""+this.timeRemaining, GraphicsData.width/2, GraphicsData.height-sectioHeight/2);
			}else if(this.isThisClientHost() == false) {
				GraphicsHandler.drawCentralisedText(g, Color.ORANGE, 22, "Waiting for host to start!", GraphicsData.width/2, GraphicsData.height-sectioHeight/2);
			}
		}
		
	}
	
	/**
	 * Updatet die Felder der Map abhängig von der übertragenen MapData
	 * @param mapData - String - Enthält die Daten über die felder der Map
	 */
	public void updateMap(String mapData) {
		
		//SYNTAX: 1,1,BL:1,2,BL: ...
		
		//RESETT MAP
		resettMap();
		
		//CHANGE NOT DEFAULT FIELDS
		String[] fieldData = mapData.split(":");
		for(String field : fieldData) {
			String[] data = field.split(",");
			int x = Integer.parseInt(data[0]);
			int y = Integer.parseInt(data[1]);
			FieldType type = FieldType.getFieldTypeFromRepresentation(data[2]);
			this.map[x][y].changeType(type);
		}
		
	}
	
	/**
	 * Setzt die map auf den Default wert zurück (Frei+Border)
	 */
	private void resettMap() {
		
		//DEFAULT
		for(int x = 0 ; x < GameData.MAP_DIMENSION ; x++) {
			for(int y = 0 ; y < GameData.MAP_DIMENSION ; y++) {
				this.map[x][y].changeType(FieldType.DEFAULT);
			}
		}
		
		//RAND
		for(int x = 0 ; x < GameData.MAP_DIMENSION ; x++) {
			for(int y = 0 ; y < GameData.MAP_DIMENSION ; y++) {
				if(x == 0 || y == 0 || x == GameData.MAP_DIMENSION-1 || y == GameData.MAP_DIMENSION-1) {
					this.map[x][y].changeType(FieldType.BORDER);
				}
			}
		}
		
	}
	
	/**
	 * Gibt eine der Freien Farben zurück oder WHITE wenn keine mehr da sind (sollte nie vorkommen)
	 * @return - {@link Color} - Die ausgewähle freie Farbe
	 */
	public Color getNextFreeColor() {
		
		if(this.freeColors.isEmpty()) {
			ConsoleDebugger.printMessage("All colors are used in this game! Giving Default Color WHITE");
			return Color.WHITE;
		}
		
		int random = new Random().nextInt(this.freeColors.size());
		Color color = this.freeColors.get(random);
		this.freeColors.remove(color);
		return color;
		
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	
	public int getMoveX() {
		return moveX;
	}
	public int getMoveY() {
		return moveY;
	}
	public void setMoveX(int moveX) {
		this.moveX = moveX;
	}
	public void setMoveY(int moveY) {
		this.moveY = moveY;
	}
//	public void addMoveX(int moveX) {
//		this.moveX += moveX;
//	}
//	public void addMoveY(int moveY) {
//		this.moveY += moveY;
//	}
	
	public Field[][] getMap() {
		return map;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public int getPlayerCount() {
		return players.size()+1; //LOKALER PLAYER IST NICHT IN DER LISTE
	}
	
	public boolean isGameStarted() {
		return gameHasStarted;
	}
	public boolean isMovementAllowed() {
		return movementAllowed;
	}
	public boolean isThisClientHost() {
		return thisClientIsHost;
	}
	
}
