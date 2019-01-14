package socket.socket_011.sample_01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public class Server {
	private final static Logger logger = Logger.getLogger(Client.class.getName());
	private static int port = 8888;
	
	
	public static void main(String[] args){
		Selector selector = null;
		SocketChannel socketChannel = null;  
		ServerSocketChannel serverSocketChannel = null;
		ServerHandler serverHandler = new ServerHandler();
		
		try {
			logger.info("服务端启动......");
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().setReuseAddress(true);
			serverSocketChannel.socket().bind(new InetSocketAddress(port));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			while(true){ 
				if(selector.select()>0){
					Set<SelectionKey> selectedKeys = selector.selectedKeys();  
	                Iterator<SelectionKey> it = selectedKeys.iterator(); 
	                while(it.hasNext()){
	                	SelectionKey key = it.next();
	                	if(key.isAcceptable()){
	                		logger.info("服务端接收成功");
	                		serverHandler.handleAccept(key);
	                	}
	                	else if(key.isReadable()){
	                		logger.info("服务端读取信息");
	                		serverHandler.handleRead(key);
	                		
	                	}
	                	else if(key.isWritable()){
	                		logger.info("服务端发送信息");
	                		serverHandler.handleWrite(key);
	                		
	                	}
	                	it.remove();
	                }
				}
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {  
            try {  
                selector.close();  
            } catch(Exception ex) {}  
            try {  
                serverSocketChannel.close();  
            } catch(Exception ex) {}  
        } 
	}
}
