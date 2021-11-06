package uni.bombenstimmung.de.serverconnection.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import uni.bombenstimmung.de.main.ConsoleDebugger;

public class ClientHandler extends IoHandlerAdapter {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		
	    cause.printStackTrace();
	    
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		
		ConsoleDebugger.printMessage("Server connected! '"+session.getRemoteAddress()+"'");
		
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		
		ConsoleDebugger.printMessage("Server closed! '"+session.getRemoteAddress()+"'");
		
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		
		ConsoleDebugger.printMessage("Received '"+message+"'");
	    
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		
		ConsoleDebugger.printMessage("Send '"+message+"'");
		
	}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		
		 ConsoleDebugger.printMessage("IDLE "+session.getIdleCount(status));
	    
	}

}
