package socket.socket_011.sample_02;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import socket.socket_011.sample_02.Server.Handler;
import socket.socket_011.sample_02.Server.ServerHandler;


public class Client{
	private static final Logger log = Logger.getLogger(Server.class.getName());  
	private static final int PORT = 9999;
	private static final String HOST = "localhost";
	
	public static void main(String[] args) throws IOException{
		Selector selector = Selector.open();
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_WRITE);
		log.info("Client: socket server started."); 
		while(true){
			int readyChannels = selector.select();
			System.out.println(readyChannels);
			if(readyChannels>0){
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				while(it.hasNext()){
					SelectionKey selectionKey = it.next();
					if(selectionKey.isConnectable()){
						log.info("Client: SelectionKey is connectable.");
						SocketChannel channel = (SocketChannel)selectionKey.channel();
						if(channel.isConnectionPending())
							channel.finishConnect();
						channel.register(selector, SelectionKey.OP_WRITE);
					}
					else if(selectionKey.isReadable()){
						log.info("Client: SelectionKey is readable.");
						ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			        	SocketChannel channel = (SocketChannel)selectionKey.channel();
			        	int count = channel.read(byteBuffer);
			        	if (count > 0) { 
				        	String msg = new String(byteBuffer.array());
				        	log.info("Client receive data: "+ msg);  
			        	}
			        	else{
			        		socketChannel.close();
			        	}
			        	//selectionKey.interestOps(SelectionKey.OP_WRITE);
					}
					else if(selectionKey.isWritable()){
						log.info("Client: SelectionKey is writable.");  
			        	SocketChannel channel = (SocketChannel)selectionKey.channel();
			        	channel.write(ByteBuffer.wrap(new String("server").getBytes()));
			        	channel.register(selector, SelectionKey.OP_READ);
					}
					it.remove();
				}
			}

		}
	}
}