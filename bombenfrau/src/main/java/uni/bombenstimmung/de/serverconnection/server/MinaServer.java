package uni.bombenstimmung.de.serverconnection.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.serverconnection.ConnectionData;

public class MinaServer {

	public static void initServerConnection() throws IOException {
		
		IoAcceptor acceptor = new NioSocketAcceptor(); 
//		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec",  new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));  
		acceptor.setHandler(new ServerHandler());
//		acceptor.getSessionConfig().setReadBufferSize(2048);
//	    acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.bind(new InetSocketAddress(ConnectionData.TCP_PORT));
		
		ConsoleDebugger.printMessage("Started server connection on port '"+ConnectionData.TCP_PORT+"'");
		
	}
	
}
