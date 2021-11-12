/*
 * Player
 *
 * Version 1.0
 * Author: Benni
 *
 * Das Objekt das einen Spieler für den Server repräsentiert, vorallem seine Client connection session und seine id
 */

package uni.bombenstimmung.de.objects;

import java.awt.Color;
import java.awt.Graphics;

import org.apache.mina.core.session.IoSession;

import uni.bombenstimmung.de.game.GameData;
import uni.bombenstimmung.de.graphics.GraphicsHandler;
import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.serverconnection.ConnectionData;
import uni.bombenstimmung.de.serverconnection.server.MinaServer;

public class Player {

	private int id;
	private IoSession session;
	
	private int x = (GameData.MAP_DIMENSION/2)*GameData.FIELD_DIMENSION, y = x;
	private Color color = Color.PINK;
	
	private int placedBombs = 0;
	
	private boolean exploded = false;
	
	/**
	 * Das Spieler Objekt das für den Server den jeweilig verbundenen Spieler repräsentiert.
	 * Vorallem seine Connection-Session und seine ID werden hier verwaltet (ID wird automatisch zugewiesen und automatisch hochgezählt)
	 * @param session - Die Spieler Session
	 */
	public Player(IoSession session) {
		
		ConnectionData.lastPlayerID++;
		this.id = ConnectionData.lastPlayerID;
		this.session = session;
		
		ConnectionData.connectedPlayer.add(this);
		ConsoleDebugger.printMessage("Client ("+this.id+") connected!");
		
	}
	/**
	 * Alternative erstellung ohne ClientConnection um den SPieler im Game darzustellen.
	 * @param id - Die ID des Spielers
	 * @param color - Die Farbe des Spielers
	 */
	public Player(int id, Color color) {
		
		this.id = id;
		this.color = color;
		
	}
	
	/**
	 * Bringt den Spieler wieder zurück (Zb wenn eine neue Runde startet)
	 */
	public void revive() {
		
		this.exploded = false;
		
	}
	
	/**
	 * Triggered wenn der Spieler von einer Bombe getroffen weird
	 */
	public void explode() {
		
		this.exploded = true;
		
	}
	
	/**
	 * Stellt den Spieler im Game dar
	 * @param g - Das Graphikobjekt
	 */
	public void draw(Graphics g) {
		
		int playerX = GraphicsHandler.getPlayerCoordianteByMoveFactor(x, true);
		int playerY = GraphicsHandler.getPlayerCoordianteByMoveFactor(y, false);
		g.setColor(this.color);
		g.fillOval(playerX-GameData.FIELD_DIMENSION/2, playerY-GameData.FIELD_DIMENSION/2, GameData.PLAYER_DIMENSION, GameData.PLAYER_DIMENSION);		
		try {
			GameData.runningGame.getMap()[GraphicsHandler.getCoordianteByPixel(playerX, true)][GraphicsHandler.getCoordianteByPixel(playerY, false)].drawHighlight(g, this.color);
		}catch(IndexOutOfBoundsException error) {}
		
	}
	
	/**
	 * Sendet die angegebene Nachricht zu dem Client den dieser Player repräsentiert.
	 * Wichtig: Die message darf nicht das Trennsymbol beinhalten, siehe {@link ConnectionData}
	 * @param messageID - int - Die ID der Nachricht, an der deren Inhalt zugeordnet wird
	 * @param message - String - Die eigentliche Nachricht die Informationen enthält
	 */
	public void sendMessage(int messageID, String message) {
		
		MinaServer.sendMessageToClient(this.session, messageID, message);
		
	}
	
	/**
	 * Trennt die Verbindung zum Client und informiert diesen darüber, damit es keine plötzliche Trennung ist
	 */
	public void forceDisconnect() {
		
		this.sendMessage(999, "Forced Server disconnect!");
		this.session.closeNow();
		ConsoleDebugger.printMessage("Client ("+this.id+") has been disconnected!");
		
	}
	
	/**
	 * Wird aufgerufen wenn die Verbindung vom Client getrennt wird.
	 * Führt dazu das auch hier auf dem Server die Session beendet wird
	 */
	public void disconnected() {
		
		this.session.closeNow();
		if(GameData.runningGame != null) {
			GameData.runningGame.unregisterPlayer(this.id);
		}
		ConnectionData.connectedPlayer.remove(this);
		ConsoleDebugger.printMessage("Client ("+this.id+") disconnected!");
		
	}
	
	/**
	 * Encodet diesen Spieler in einen String
	 * @return - String - Die Daten des SPielers als String
	 */
	public String convertToStringData() {
		return this.id+":"+this.color.getRed()+","+this.color.getGreen()+","+this.color.getBlue()+":"+this.x+":"+this.y;
	}
	
	public int getPlacedBombs() {
		return placedBombs;
	}
	public void increaseBombCOunt() {
		this.placedBombs++;
	}
	public void decreaseBombCOunt() {
		this.placedBombs--;
	}
	
	public boolean isExploded() {
		return exploded;
	}
	public void setColor(Color newColor) {
		this.color = newColor;
	}
	
	public int getId() {
		return id;
	}
	public IoSession getSession() {
		return session;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Color getColor() {
		return color;
	}
	
}
