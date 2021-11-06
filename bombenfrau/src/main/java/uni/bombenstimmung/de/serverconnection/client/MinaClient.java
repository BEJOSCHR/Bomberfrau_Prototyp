package uni.bombenstimmung.de.serverconnection.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Random;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.serverconnection.ConnectionData;

public class MinaClient {

	public static void initClientConnection() throws IOException, InterruptedException {
		
		NioSocketConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(ConnectionData.TIMEOUT_DELAY);
		connector.setHandler(new ClientHandler());
//		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec",  new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));  
		
		IoSession session;
		
		int tryCount = 1;
		for(;;) {
			ConsoleDebugger.printMessage("Try "+tryCount+" conneting to '"+ConnectionData.IP+":"+ConnectionData.TCP_PORT+"'... ", false);
		    try {
		        ConnectFuture future = connector.connect(new InetSocketAddress(ConnectionData.IP, ConnectionData.TCP_PORT));
		        future.awaitUninterruptibly();
		        session = future.getSession();
		        
		        ConsoleDebugger.printMessage("connected!");
		        session.write("hello server "+new Random().nextInt(999)+1);
		        
		        break;
		    } catch (RuntimeIoException e) {
		    	ConsoleDebugger.printMessage("failed!");
//		        e.printStackTrace();
		        Thread.sleep(5000);
		    }
		    tryCount++;
		}
		    
		// wait until the summation is done
		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
		
	}
	
}
