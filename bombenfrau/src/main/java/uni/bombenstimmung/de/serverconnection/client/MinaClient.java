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

import java.awt.Color;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import uni.bombenstimmung.de.game.Game;
import uni.bombenstimmung.de.game.GameData;
import uni.bombenstimmung.de.graphics.DisplayType;
import uni.bombenstimmung.de.graphics.GraphicsData;
import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.objects.Player;
import uni.bombenstimmung.de.serverconnection.ConnectionData;
import uni.bombenstimmung.de.serverconnection.ConnectionType;

public class MinaClient {

	private static NioSocketConnector connector = null;
	
	/**
	 * Versucht eine Verbindung zum Server aufzubauen, versucht dies einige Male
	 * Es ist wichtig den selben codec Filter zu setzten wie im Server (Charset UTF-8)!
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static boolean initClientConnection() throws IOException, InterruptedException {
		
		ConnectionData.connectionType = ConnectionType.CLIENT;
		
		connector = new NioSocketConnector();
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
		        ConsoleDebugger.printMessage("If no clientID is received, the game is already running or full!");
		        return true;
		    } catch (RuntimeIoException e) {
		    	ConsoleDebugger.printMessage("failed!");
//		        e.printStackTrace();
		        Thread.sleep(2000);
		    }
		    if(tryCount == ConnectionData.CONNECT_ATTEMPTS) {
		    	//ABORT
		    	ConsoleDebugger.printMessage("Aborting connection after "+ConnectionData.CONNECT_ATTEMPTS+" failed attempts!");
		    	return false;
		    }
		    tryCount++;
		}
		
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
				GameData.runningGame = new Game(false);
				GraphicsData.drawState = DisplayType.INGAME;
			}catch(NumberFormatException error) {
				ConsoleDebugger.printMessage("Invalid clientID! Cant convert to Integer: "+message+"");
			}
			break;
		case 100:
			//Farbe (farbe)
			Color color = getColorFromString(message);
			ConsoleDebugger.printMessage("Received color: "+message);
			GameData.runningGame.setColor(color);
			break;
		case 101:
			//Map
			int mapNumber = Integer.parseInt(message);
			ConsoleDebugger.printMessage("Received mapNumber: "+mapNumber);
			GameData.runningGame.updateMap(mapNumber);
			break;
		case 200:
			//New player (id:color:x:y)
			String[] data = message.split(":");
			int id = Integer.parseInt(data[0]);
			Color newColor = getColorFromString(data[1]);
			int moveFactorX = Integer.parseInt(data[2]);
			int moveFactorY = Integer.parseInt(data[3]);
			Player newPlayer = new Player(id, newColor);
			newPlayer.setX(moveFactorX);
			newPlayer.setY(moveFactorY);
			GameData.runningGame.addPlayer(newPlayer);
			break;
		case 201:
			//Remove player (id)
			int id2 = Integer.parseInt(message);
			GameData.runningGame.removePlayer(id2);
			break;
		case 300:
			//Start Spiel Countdown
			GameData.runningGame.startCountdown();
			break;
		case 400:
			String[] data1 = message.split(":");
			int playerID = Integer.parseInt(data1[0]);
			int newMoveFactorX = Integer.parseInt(data1[1]);
			int newMoveFactorY = Integer.parseInt(data1[2]);
			GameData.runningGame.updatePlayerPos(playerID, newMoveFactorX, newMoveFactorY);
			break;
		case 500:
			//Add Bomb
			String[] data2 = message.split(":");
			int placerID = Integer.parseInt(data2[0]);
			int bombX = Integer.parseInt(data2[1]);
			int bombY = Integer.parseInt(data2[2]);
			int liveTime = Integer.parseInt(data2[3]);
			int explodeRadius = Integer.parseInt(data2[4]);
			GameData.runningGame.addBomb(placerID, bombX, bombY, liveTime, explodeRadius);
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
	
	private static Color getColorFromString(String colorString) {
		
		//RGB
		String[] data = colorString.split(",");
		int r = Integer.parseInt(data[0]);
		int g = Integer.parseInt(data[1]);
		int b = Integer.parseInt(data[2]);
		return new Color(r, g, b);
		
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
			connector.dispose();
		}
		
	}
	
}
