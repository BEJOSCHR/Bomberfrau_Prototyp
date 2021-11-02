/*
 * BomberfrauPrototypMain
 *
 * Version 1.0
 * Author: Benni
 *
 * Der Serverseitige Teil der Serververbindung
 */

package uni.bombenstimmung.de.serverconnection;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.serverconnection.packages.PingRequest;
import uni.bombenstimmung.de.serverconnection.packages.PingResponse;

public class ServerConnection extends Listener {

	/**
	 * Startet, bindet, öffnet die Serververbindung und registriert alle sendbaren und empfangbaren Packet-Klassen
	 */
	public static void openServerConnection() {
		
		ConnectionData.server = new Server();
		ConnectionData.server.start();
		
		try {
			ConnectionData.server.bind(ConnectionData.TCP_PORT, ConnectionData.UDP_PORT);
		} catch (IOException error) {
			ConsoleDebugger.printMessage("Binding server to port '"+ConnectionData.TCP_PORT+":"+ConnectionData.UDP_PORT+"' failed with error: ");
			error.printStackTrace();
			return;
		}
		
		Kryo kryo = ConnectionData.server.getKryo();
		kryo.register(PingRequest.class);
		kryo.register(PingResponse.class);
		
		ConnectionData.server.addListener(new ServerConnection());
		
		ConsoleDebugger.printMessage("Started server! Listening on port '"+ConnectionData.TCP_PORT+"'");
		
		while(true) {}
		
	}
	
	//EVENTS
	
	@Override
	public void connected(Connection con) {
		
		ConsoleDebugger.printMessage("	Client '"+con.getID()+"' connected");
		
	}
	
	@Override
	public void disconnected(Connection con) {
		
		ConsoleDebugger.printMessage("	Client '"+con.getID()+"' disconnected");
		
	}
	
	@Override
	public void received(Connection con, Object content) {
		
		if(content instanceof PingRequest) {
			//PING
			PingRequest pingRequest = (PingRequest) content;
			con.sendTCP(new PingResponse(pingRequest.getSendTime()));
		}
		
	}
	
}
