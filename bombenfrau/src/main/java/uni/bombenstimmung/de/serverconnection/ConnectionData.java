/*
 * ConnectionData
 *
 * Version 1.0
 * Author: Benni
 *
 * Enthält alle übergreifenden Daten der Serververbindung
 */

package uni.bombenstimmung.de.serverconnection;

import org.apache.mina.core.session.IoSession;

public class ConnectionData {
	
	public static final String IP = "127.0.0.1";
	public static final int TCP_PORT = 25652;
	public static final int TIMEOUT_DELAY = 10*1000;
	
	public static IoSession session;
	
}
