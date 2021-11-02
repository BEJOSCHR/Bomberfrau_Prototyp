/*
 * ClientConnection
 *
 * Version 1.0
 * Author: Benni
 *
 * Der Clientseitige Teil der Serververbindung
 */

package uni.bombenstimmung.de.serverconnection;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.serverconnection.packages.PingRequest;
import uni.bombenstimmung.de.serverconnection.packages.PingResponse;

public class ClientConnection extends Listener {

	/**
	 * Versucht eine Serververbindung aufzubauen und registriert alle sendbaren und empfangbaren Packet-Klassen
	 */
	public static void connectToServer() {
		
		ConnectionData.client = new Client();
		ConnectionData.client.start();
		
		ConsoleDebugger.printMessage("Connecting to server on '"+ConnectionData.IP+":"+ConnectionData.PORT+"'...", false);
		
		try {
			ConnectionData.client.connect(5000, ConnectionData.IP, ConnectionData.PORT);
		} catch (IOException error) {
			ConsoleDebugger.printMessage("Connecting to server '"+ConnectionData.IP+"' on port '"+ConnectionData.PORT+"' failed with error: ");
			error.printStackTrace();
			return;
		}
		
		Kryo kryo = ConnectionData.client.getKryo();
		kryo.register(PingRequest.class);
		kryo.register(PingResponse.class);
		
		ConnectionData.client.addListener(new ClientConnection());
		
		ConsoleDebugger.printMessage(" connected!");
		
		//Test package
		ConsoleDebugger.printMessage("Sending ping request ("+System.currentTimeMillis()+")...");
		ConnectionData.client.sendTCP(new PingRequest());
		
		while(true) {}
		
	}
	
	//EVENTS
	
	@Override
	public void connected(Connection con) {}
	
	@Override
	public void disconnected(Connection con) {
		
		ConsoleDebugger.printMessage("Disconnected from server!");
		
	}
	
	@Override
	public void received(Connection con, Object content) {
		
		if(content instanceof PingResponse) {
			//PING
			PingResponse pingResponse = (PingResponse) content;
			long originalSendTime = pingResponse.getOriginalSendTime();
			long currentMillis = System.currentTimeMillis();
			ConsoleDebugger.printMessage("PingResponse: (SendTime: "+(originalSendTime)+") (Total: "+(currentMillis-originalSendTime)+") (Ping: "+((currentMillis-originalSendTime)/2)+")");
		}
		
	}
	
}
