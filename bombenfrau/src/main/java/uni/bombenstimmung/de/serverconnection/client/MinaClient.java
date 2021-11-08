/*
 * MinaClient
 *
 * Version 1.0
 * Author: Benni
 *
 * Die Client-Verbindung, wenn gestartet werden hier die einkommenden Messages abgearbeitet und die Verbidnung zum Server aufrechtgehalten
 */

package uni.bombenstimmung.de.serverconnection.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.serverconnection.ConnectionData;
import uni.bombenstimmung.de.serverconnection.ConnectionType;

public class MinaClient {

	/**
	 * Versucht eine Verbindung zum Server aufzubauen, versucht dies einige Male
	 * Es ist wichtig den selben codec Filter zu setzten wie im Server (Charset UTF-8)!
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void initClientConnection() throws IOException, InterruptedException {
		
		ConnectionData.connectionType = ConnectionType.CLIENT;
		
		NioSocketConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(ConnectionData.TIMEOUT_DELAY);
		connector.setHandler(new ClientHandler());
//		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec",  new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));  
		
		int tryCount = 1;
		for(;;) {
			ConsoleDebugger.printMessage("Try "+tryCount+" conneting to '"+ConnectionData.IP+":"+ConnectionData.TCP_PORT+"'... ", false);
		    try {
		        ConnectFuture future = connector.connect(new InetSocketAddress(ConnectionData.IP, ConnectionData.TCP_PORT));
		        future.awaitUninterruptibly();
		        ConnectionData.connectionToServer = future.getSession();
		        ConsoleDebugger.printMessage("connected!");
		        break;
		    } catch (RuntimeIoException e) {
		    	ConsoleDebugger.printMessage("failed!");
//		        e.printStackTrace();
		        Thread.sleep(2000);
		    }
		    if(tryCount == ConnectionData.CONNECT_ATTEMPTS) {
		    	//ABORT
		    	ConsoleDebugger.printMessage("Aborting connection after "+ConnectionData.CONNECT_ATTEMPTS+" failed attempts!");
		    	return;
		    }
		    tryCount++;
		}
		    
		// wait until the summation is done
		ConnectionData.connectionToServer.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
		
	}
	
	/**
	 * Wird aufgerufen wenn eine nachricht vom Server diesen Client erreicht
	 * @param messageID - int - Die ID der Nachricht, an der deren Inhalt zugeordnet wird
	 * @param message - String - Die eigentliche Nachricht mit den Informationen
	 */
	public static void receiveMessageFromServer(int messageID, String message) {
		
		switch(messageID) {
		case 001:
			//CONNECT
			try {
				ConnectionData.clientID = Integer.parseInt(message);
				ConsoleDebugger.printMessage("Received clientID "+ConnectionData.clientID+"");
			}catch(NumberFormatException error) {
				ConsoleDebugger.printMessage("Invalid clientID! Cant convert to Integer: "+message+"");
			}
			break;
		case 999:
			//FORCED DISCONECT
			ConnectionData.connectionToServer.closeNow();
			ConsoleDebugger.printMessage("Server connection got closed by host!");
			break;
		default:
			ConsoleDebugger.printMessage("Unknown messageID '"+messageID+"'!");
			break;
		}
		
	}
	
	/**
	 * Sendet die angegebene Nachricht zum Server, wenn verbunden.
	 * Wichtig: Die message darf nicht das Trennsymbol beinhalten, siehe {@link ConnectionData}
	 * @param messageID - int - Die ID der Nachricht, an der deren Inhalt zugeordnet wird
	 * @param message - String - Die eigentliche Nachricht die Informationen enthält
	 */
	public static void sendMessageToServer(int messageID, String message) {
		
		ConnectionData.connectionToServer.write(messageID+ConnectionData.SPLIT_CHAR+message);
		
	}
	
	/**
	 * Trennt die Verbindung zum Server und informiert diesen darüber, damit es keine plötzliche Trennung ist
	 */
	public static void disconnectFromServer() {
		
		if(ConnectionData.connectionToServer != null) {
			sendMessageToServer(999, "Disconnecting!");
			ConnectionData.connectionToServer.closeNow();
		}
		
	}
	
}
