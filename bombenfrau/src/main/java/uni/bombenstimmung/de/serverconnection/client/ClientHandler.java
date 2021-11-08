/*
 * ClientHandler
 *
 * Version 1.0
 * Author: Benni
 *
 * Arbeitet alle Client bezogenen Evente und Methoden ab
 */

package uni.bombenstimmung.de.serverconnection.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.serverconnection.ConnectionData;

public class ClientHandler extends IoHandlerAdapter {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		
//	    cause.printStackTrace();
	    
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {}
	
	@Override
	public void sessionClosed(IoSession session) {
		
		ConsoleDebugger.printMessage("Connection to the server was closed unplanned!");
		System.exit(0);
		
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		
		String messageRaw = message.toString();
		String[] splitMessage = messageRaw.split(ConnectionData.SPLIT_CHAR);
		try {
			int messageID = Integer.parseInt(splitMessage[0]);
			String finalMessage = splitMessage[1];
			MinaClient.receiveMessageFromServer(messageID, finalMessage);
		}catch(NumberFormatException error) {
			ConsoleDebugger.printMessage("Invalid message syntax! MessageId is not an Integer! (RawMessage: "+messageRaw+")");
		}
	    
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {}

}
