/*
 * ServerHandler
 *
 * Version 1.0
 * Author: Benni
 *
 * Arbeitet alle Server bezogenen Evente und Methoden ab
 */

package uni.bombenstimmung.de.serverconnection.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.objects.Player;
import uni.bombenstimmung.de.serverconnection.ConnectionData;

public class ServerHandler extends IoHandlerAdapter {
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		
	    cause.printStackTrace();
	    
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		
		MinaServer.clientConnected(session);
		
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		
		ConsoleDebugger.printMessage("A client session was closed unplanned! Player object will be removed...");
		Player player = MinaServer.getPlayer(session);
		if(player != null) {
			player.disconnected();
		}else {
			ConsoleDebugger.printMessage("Cant find and remove player on sudden session close!");
		}
		
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		
		String messageRaw = message.toString();
		String[] splitMessage = messageRaw.split(ConnectionData.SPLIT_CHAR);
		try {
			Player sender = MinaServer.getPlayer(session);
			if(sender == null) {
				ConsoleDebugger.printMessage("Cant find player for received message session! (RawMessage: "+messageRaw+")");
				return;
			}
			int messageID = Integer.parseInt(splitMessage[0]);
			String finalMessage = splitMessage[1];
			MinaServer.receiveMessageFromClient(sender, messageID, finalMessage);
		}catch(NumberFormatException error) {
			ConsoleDebugger.printMessage("Invalid message syntax! MessageId is not an Integer! (RawMessage: "+messageRaw+")");
		}
		
	    
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {}
	
}
