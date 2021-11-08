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

public class Game {

	//Local Client Data
	private Color color = Color.RED;
	private int x = 0, y = 0;
	private int moveX = 0, moveY = 0;
	
	//Other Client Data
	private List<Player> players = new ArrayList<Player>();
	
	//General Data
	private boolean gameHasStarted = false;
	private boolean movementAllowed = false;
	private int timeRemaining = GameData.COUNTDOWN_DURATION;
	private boolean thisClientIsHost = false;
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
		
		//REPLACE NONE DEFAULT FIELDTYPES - Customise Map
		for(int x = 0 ; x < GameData.MAP_DIMENSION ; x++) {
			for(int y = 0 ; y < GameData.MAP_DIMENSION ; y++) {
				if(x == 0 || y == 0 || x == GameData.MAP_DIMENSION-1 || y == GameData.MAP_DIMENSION-1) {
					//RAND
					this.map[x][y].changeType(FieldType.BORDER);
				}
			}
		}
		
		//MOVE SCREEN
		int midField = (GameData.MAP_DIMENSION)/2;
		GraphicsHandler.moveScreenToFieldCoordinates(midField, midField);
		
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
		
		this.gameHasStarted = true;
		
		//TODO TP PLAYER
		
		//TODO SEND COUNTDOWN START TO OTHERS
		startCountdown();
		
	}
	/**
	 * Wird vom Spielstart aufgerufen und gibt nach einiger Zeit die Bewegung frei
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
				}
			}
		}, 0, 1000);
		
	}
	
	
	/**
	 * Wird nur aufgerufen vom Server wenn sich jemand neues einloggt in das game. 
	 * Hier wird demjenigen dann der derzeitige Spielstand übermittelt und ihm eine farbe zugewiesen.
	 * Es können maximal 3 Spieler regestriert werden! (3 + Lokalen SPieler = 4 Player)
	 * @param newPlayer - Der Neue Spieler
	 */
	public void registerPlayer(Player newPlayer) {
		
		if(this.getPlayerCount() >= 4) {
			//TODO SEND INFO TO NEW PLAYER
			return;
		}
		
		newPlayer.setColor(getNextFreeColor());
		
		//SEND DATA TO NEW PLAYER
		//TODO SEND LOKAL PLAYER
		for(Player player : this.players) {
			//TODO
		}
		//SEND DATA TO OTHER PLAYERS ABOUT NEW PLAYER
		for(Player player : this.players) {
			//TODO
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
					//TODO
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
		
		//PLAYER
		int midX = GraphicsData.width/2, midY = GraphicsData.height/2;
		g.setColor(Color.BLACK);
		g.fillOval(midX-(GameData.PLAYER_DIMENSION/2), midY-(GameData.PLAYER_DIMENSION/2), GameData.PLAYER_DIMENSION, GameData.PLAYER_DIMENSION);		
		
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
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
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
	public void addMoveX(int moveX) {
		this.moveX += moveX;
	}
	public void addMoveY(int moveY) {
		this.moveY += moveY;
	}
	
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
