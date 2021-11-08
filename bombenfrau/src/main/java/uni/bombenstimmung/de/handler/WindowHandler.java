/*
 * WindowHandler
 *
 * Version 1.0
 * Author: Benni
 *
 * Verwaltet alle Window-Evente die vom Frame aus aufgerufen werden
 */

package uni.bombenstimmung.de.handler;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import uni.bombenstimmung.de.serverconnection.ConnectionData;
import uni.bombenstimmung.de.serverconnection.ConnectionType;
import uni.bombenstimmung.de.serverconnection.client.MinaClient;
import uni.bombenstimmung.de.serverconnection.server.MinaServer;

public class WindowHandler implements WindowListener {

	public void windowOpened(WindowEvent e) {}

	public void windowClosing(WindowEvent e) {
		
		if(ConnectionData.connectionType == ConnectionType.CLIENT) {
			
			MinaClient.disconnectFromServer();
			
		}else if(ConnectionData.connectionType == ConnectionType.SERVER) {
			
			MinaServer.shutDownServerConnection();
			
		}
		
	}

	public void windowClosed(WindowEvent e) {}

	public void windowIconified(WindowEvent e) {}

	public void windowDeiconified(WindowEvent e) {}

	public void windowActivated(WindowEvent e) {}

	public void windowDeactivated(WindowEvent e) {}

}
