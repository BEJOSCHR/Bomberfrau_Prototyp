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
import com.esotericsoftware.kryonet.Server;

import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.serverconnection.packages.PingRequest;
import uni.bombenstimmung.de.serverconnection.packages.PingResponse;

public class ServerConnection {

	/**
	 * Startet, bindet, öffnet die Serververbindung und registriert alle sendbaren und empfangbaren Packet-Klassen
	 */
	public static void openServerConnection() {
		
		Server server = new Server();
		server.start();
		
		try {
			server.bind(ConnectionData.PORT);
		} catch (IOException error) {
			ConsoleDebugger.printMessage("Binding server to port '"+ConnectionData.PORT+"' failed with error: ");
			error.printStackTrace();
		}
		
		Kryo kryo = server.getKryo();
		kryo.register(PingRequest.class);
		kryo.register(PingResponse.class);
		
		ConsoleDebugger.printMessage("Started server! Listening on port '"+ConnectionData.PORT+"'");
		
		while(true) {}
		
	}
	
}
