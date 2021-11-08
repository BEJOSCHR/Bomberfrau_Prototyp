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

import org.apache.mina.core.session.IoSession;

import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.serverconnection.ConnectionData;
import uni.bombenstimmung.de.serverconnection.server.MinaServer;

public class Player {

	private int id;
	private IoSession session;
	
	private int x = 0, y = 0;
	private Color color = Color.PINK;
	
	/**
	 * Das Spieler Objekt das für den Server den jeweilig verbundenen Spieler repräsentiert.
	 * Vorallem seine Connection-Session und seine ID werden hier verwaltet (ID wird automatisch zugewiesen und automatisch hochgezählt)
	 * @param session
	 */
	public Player(IoSession session) {
		
		ConnectionData.lastPlayerID++;
		this.id = ConnectionData.lastPlayerID;
		this.session = session;
		
		ConnectionData.connectedPlayer.add(this);
		ConsoleDebugger.printMessage("Client ("+this.id+") connected!");
		
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
		ConnectionData.connectedPlayer.remove(this);
		ConsoleDebugger.printMessage("Client ("+this.id+") has been disconnected!");
		
	}
	
	/**
	 * Wird aufgerufen wenn die Verbindung vom Client getrennt wird.
	 * Führt dazu das auch hier auf dem Server die Session beendet wird
	 */
	public void disconnected() {
		
		this.session.closeNow();
		ConnectionData.connectedPlayer.remove(this);
		ConsoleDebugger.printMessage("Client ("+this.id+") disconnected!");
		
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
	public Color getColor() {
		return color;
	}
	
}
