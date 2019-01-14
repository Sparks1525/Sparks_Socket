package socket.socket_011.sample_02;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public class Server implements Runnable{
	private static final Logger log = Logger.getLogger(Server.class.getName());  
	private static final int PORT = 9999;
	private static final String HOST = "localhost";
	private Handler handler = new ServerHandler();
	
	public void run(){
		try {
			Selector selector = Selector.open();
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(HOST,PORT));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			log.info("Server: socket server started."); 
			while(true){
				int readyChannels = selector.select();
				if(readyChannels>0){
					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					Iterator<SelectionKey> it = selectedKeys.iterator();
					while(it.hasNext()){
						SelectionKey selectionKey = it.next();
						if(selectionKey.isAcceptable()){
							log.info("Server: SelectionKey is acceptable.");
							handler.handleAccept(selectionKey);
						}
						else if(selectionKey.isReadable()){
							log.info("Server: SelectionKey is readable.");
							handler.handleRead(selectionKey);
						}
						else if(selectionKey.isWritable()){
							log.info("Server: SelectionKey is writable.");  
							handler.handleWrite(selectionKey);
						}
						it.remove();
					}
				}
				
			}
		} catch (ClosedChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	interface Handler {  
        void handleAccept(SelectionKey key) throws IOException;        
        void handleRead(SelectionKey key) throws IOException;    
        void handleWrite(SelectionKey key) throws IOException;  
    }  
	
	class ServerHandler implements Handler {  
		   
        public void handleAccept(SelectionKey key) throws IOException {  
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            log.info("Server: accept client socket " + socketChannel);
            socketChannel.configureBlocking(false);
            socketChannel.register(key.selector(), SelectionKey.OP_READ);
        }  
  
        public void handleRead(SelectionKey key) throws IOException {  
        	ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        	SocketChannel socketChannel = (SocketChannel)key.channel();
        	int count = socketChannel.read(byteBuffer);
        	if (count > 0) { 
        		String msg = new String(byteBuffer.array());
            	log.info("Server receive data: "+ msg);  
            	key.interestOps(SelectionKey.OP_WRITE);
        	}
        	else{
        		key.cancel();
        	}
        }  
    
        public void handleWrite(SelectionKey key) throws IOException { 
        	SocketChannel channel = (SocketChannel)key.channel();
        	channel.write(ByteBuffer.wrap(new String("client").getBytes()));
        	key.interestOps(SelectionKey.OP_READ);
        }  
    }  
	public static void main(String[] args){
		new Thread(new Server()).start();
	}

}