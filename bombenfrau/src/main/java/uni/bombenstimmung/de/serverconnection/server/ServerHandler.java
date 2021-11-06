package uni.bombenstimmung.de.serverconnection.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import uni.bombenstimmung.de.main.ConsoleDebugger;

public class ServerHandler extends IoHandlerAdapter {
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		
	    cause.printStackTrace();
	    
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		
		ConsoleDebugger.printMessage("Session connected! '"+session.getRemoteAddress()+"'");
		session.write("Wellcome client!");
		
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		
		ConsoleDebugger.printMessage("Session closed! '"+session.getRemoteAddress()+"'");
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		
		ConsoleDebugger.printMessage("Received '"+message+"'");
		
	    String str = message.toString();
	    if(str.trim().equalsIgnoreCase("quit") ) {
	        session.close();
	        return;
	    }
	    
	    session.write("Answer to Client "+str);
	    ConsoleDebugger.printMessage("Message written...");
	    
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
