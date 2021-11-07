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

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.objects.Player;
import uni.bombenstimmung.de.serverconnection.ConnectionData;

public class MinaServer {

	/**
	 * Startet den Server, so dass dann CLients sich auf den Port verbinden können
	 * Es ist wichtig den selben codec Filter zu setzten wie im Client (Charset UTF-8)!
	 * @throws IOException
	 */
	public static void initServerConnection() throws IOException {
		
		IoAcceptor acceptor = new NioSocketAcceptor(); 
//		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec",  new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));  
		acceptor.setHandler(new ServerHandler());
//		acceptor.getSessionConfig().setReadBufferSize(2048);
//	    acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.bind(new InetSocketAddress(ConnectionData.TCP_PORT));
		
		ConsoleDebugger.printMessage("Started server connection on port '"+ConnectionData.TCP_PORT+"'");
		
	}
	
	/**
	 * Wird aufgerufen wenn eine nachricht von einem Client diesen Server erreicht
	 * @param sender - {@link Player} - Der absender der Nachricht (Player wird im Event anhand der session gefunden)
	 * @param messageID - int - Die ID der Nachricht, an der deren Inhalt zugeordnet wird
	 * @param message - String - Die eigentliche Nachricht mit den Informationen
	 */
	public static void receiveMessageFromClient(Player sender, int messageID, String message) {
		
		switch(messageID) {
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
	
	public static void clientConnected(IoSession session) {
		
		Player player = new Player(session);
		player.sendMessage(001, ""+player.getId());
		
	}
	
	public static void clientDisconnected(Player player) {
		
		player.disconnected();
		
	}
	
	public static Player getPlayer(IoSession session) {
		for(Player player : ConnectionData.connectedPlayer) {
			if(player.getSession() == session) {
				return player;
			}
		}
		return null;
	}
	public static Player getPlayer(int id) {
		for(Player player : ConnectionData.connectedPlayer) {
			if(player.getId() == id) {
				return player;
			}
		}
		return null;
	}
	
}
