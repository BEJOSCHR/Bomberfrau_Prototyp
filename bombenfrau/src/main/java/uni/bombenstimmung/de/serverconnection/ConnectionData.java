/*
 * ConnectionData
 *
 * Version 1.0
 * Author: Benni
 *
 * Enthält alle übergreifenden Daten der Serververbindung
 */

package uni.bombenstimmung.de.serverconnection;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

public class ConnectionData {

	public static Server server;
	public static Client client;
	
	public static final String IP = "127.0.0.1";
	public static final int PORT = 25652;
	
}
