/*
 * MinaServer
 *
 * Version 1.0
 * Author: Benni
 *
 * Die Server-Verbindung, wenn gestartet werden hier die einkommenden Messages abgearbeitet und die Sessions der verbudnenen Clients zu verwalten
 */

package uni.bombenstimmung.de.serverconnection.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import uni.bombenstimmung.de.game.GameData;
import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.objects.Player;
import uni.bombenstimmung.de.serverconnection.ConnectionData;
import uni.bombenstimmung.de.serverconnection.ConnectionType;

public class MinaServer {

	/**
	 * Startet den Server, so dass dann CLients sich auf den Port verbinden können
	 * Es ist wichtig den selben codec Filter zu setzten wie im Client (Charset UTF-8)!
	 * @throws IOException
	 */
	public static void initServerConnection() throws IOException {
		
		ConnectionData.connectionType = ConnectionType.SERVER;
		
		ConnectionData.serverAcceptor = new NioSocketAcceptor(); 
//		ConnectionData.serverAcceptor.getFilterChain().addLast("logger", new LoggingFilter());
		ConnectionData.serverAcceptor.getFilterChain().addLast("codec",  new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));  
		ConnectionData.serverAcceptor.setHandler(new ServerHandler());
//		ConnectionData.serverAcceptor.getSessionConfig().setReadBufferSize(2048);
//	    ConnectionData.serverAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		ConnectionData.serverAcceptor.bind(new InetSocketAddress(ConnectionData.TCP_PORT));
		
		ConsoleDebugger.printMessage("Started server connection on port '"+ConnectionData.TCP_PORT+"'");
		
	}
	
	/**
	 * Schließt die Serververbindung und trennt alle verbundenen Clients
	 */
	public static void shutDownServerConnection() {
		
		for(Player player : ConnectionData.connectedPlayer) {
			player.forceDisconnect();
		}
		ConnectionData.connectedPlayer.clear();
		
		if(ConnectionData.serverAcceptor != null) {
			ConnectionData.serverAcceptor.unbind();
			ConnectionData.serverAcceptor.dispose();
			ConnectionData.serverAcceptor = null;
		}
		
	}
	
	/**
	 * Wird aufgerufen wenn eine nachricht von einem Client diesen Server erreicht
	 * @param sender - {@link Player} - Der absender der Nachricht (Player wird im Event anhand der session gefunden)
	 * @param messageID - int - Die ID der Nachricht, an der deren Inhalt zugeordnet wird
	 * @param message - String - Die eigentliche Nachricht mit den Informationen
	 */
	public static void receiveMessageFromClient(Player sender, int messageID, String message) {
		
		switch(messageID) {
		case 400:
			//UpdatePlayerPos (x:y)
			String[] data = message.split(":");
			int newMoveFactorX = Integer.parseInt(data[0]);
			int newMoveFactorY = Integer.parseInt(data[1]);
			GameData.runningGame.updatePlayerPos(sender.getId(), newMoveFactorX, newMoveFactorY);
			break;
		case 999:
			//DISCONNECT
			MinaServer.clientDisconnected(sender);
			break;
		default:
			ConsoleDebugger.printMessage("Unknown messageID '"+messageID+"'!");
			break;
		}
		
	}
	
	/**
	 * Sendet die angegebene Nachricht zu dem Client zudem die session gehört.
	 * Wichtig: Die message darf nicht das Trennsymbol beinhalten, siehe {@link ConnectionData}
	 * @param session - {@link IoSession} - Die session an die die Nachricht gehen soll
	 * @param messageID - int - Die ID der Nachricht, an der deren Inhalt zugeordnet wird
	 * @param message - String - Die eigentliche Nachricht die Informationen enthält
	 */
	public static void sendMessageToClient(IoSession session, int messageID, String message) {
		
		session.write(messageID+ConnectionData.SPLIT_CHAR+message);
		
	}
	
	/**
	 * Wird aufgerufen wenn ein Client sich connected. 
	 * Sorgt dafür das ein {@link Player} Objekt für diese session angelegt wird
	 * Informiert gleichzeitig die session über die ihr zugewiesene clientID
	 * @param session - {@link IoSession} - Die session die sich verbunden hat
	 */
	public static void clientConnected(IoSession session) {
		
		if(GameData.runningGame != null) {
			if(GameData.runningGame.isGameStarted() || GameData.runningGame.getPlayerCount() >= 4) {
				session.closeNow();
				return; //NO NEW PLAYER CAN JOIN
			}
		}
		
		Player player = new Player(session);
		player.sendMessage(001, ""+player.getId());
		GameData.runningGame.registerPlayer(player);
		
	}
	
	/**
	 * Wird aufgerufen wenn ein Client sich disconnected. 
	 * leitet diese info an das übergebene player obejkt weiter
	 * @param player - {@link Player} - Der Player der disconnectet
	 */
	public static void clientDisconnected(Player player) {
		
		player.disconnected();
		
	}
	
	/**
	 * Gibt einen gesuchten Spieler zurück
	 * @param session - {@link IoSession} - Die session die der gesuchte Player haben soll
	 * @return {@link Player} wenn einer mit der gegebenen Session gefunden wurde, sonst null
	 */
	public static Player getPlayer(IoSession session) {
		for(Player player : ConnectionData.connectedPlayer) {
			if(player.getSession() == session) {
				return player;
			}
		}
		return null;
	}
	/**
	 * Gibt einen gesuchten Spieler zurück
	 * @param id - int - Die ID die der gesuchte Player haben soll
	 * @return {@link Player} wenn einer mit der gegebenen ID gefunden wurde, sonst null
	 */
	public static Player getPlayer(int id) {
		for(Player player : ConnectionData.connectedPlayer) {
			if(player.getId() == id) {
				return player;
			}
		}
		return null;
	}
	
}
