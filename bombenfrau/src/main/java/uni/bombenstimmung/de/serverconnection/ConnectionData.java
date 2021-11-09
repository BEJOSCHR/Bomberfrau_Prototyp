/*
 * ConnectionData
 *
 * Version 1.1
 * Author: Benni
 *
 * Enthält alle übergreifenden Daten der Server-Client-Verbindung
 */

package uni.bombenstimmung.de.serverconnection;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IoSession;

import uni.bombenstimmung.de.objects.Player;

public class ConnectionData {

//	public static final String IP = "25.43.175.192";
	public static final String IP = "127.0.0.1";
	public static final int TCP_PORT = 25652;
	public static final int TIMEOUT_DELAY = 10*1000;
	public static final int CONNECT_ATTEMPTS = 3;
	public static final String SPLIT_CHAR = ";";
	
	public static ConnectionType connectionType = ConnectionType.CLIENT;
	
	//SERVER
	public static IoAcceptor serverAcceptor = null;
	public static int lastPlayerID = 0;
	public static List<Player> connectedPlayer = new ArrayList<Player>();
	
	//CLIENT
	public static int clientID = -1;
	public static IoSession connectionToServer = null;
	
}
