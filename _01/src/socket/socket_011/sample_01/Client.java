package socket.socket_011.sample_01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;



public class Client implements Runnable{
	 private final static Logger logger = Logger.getLogger(Client.class.getName());  
	 private String addr = "localhost";
	 private int port = 8888;
	 private IClientHandler clientHandler = new ClientHandler();
	 public void run(){
		 SocketChannel socketChannel = null;
		 
		 try {
			logger.info("客户端启动......");
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			Selector selector = Selector.open();
			SocketAddress socketAddress = new InetSocketAddress(this.addr,this.port);
			socketChannel.connect(socketAddress);		
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			
			while(true){
				if(selector.select()>0){
					Set<SelectionKey> selectedKeys = selector.selectedKeys();  
	                Iterator<SelectionKey> it = selectedKeys.iterator();  
	                while(it.hasNext()){
	                	SelectionKey key = it.next();
	                	if(key.isConnectable()){
	                		logger.info("客户端"+Thread.currentThread().getName()+"连接");
	                		clientHandler.handleConnect(key);
	                		
	                	}
	                	else if(key.isReadable()){
	                		logger.info("客户端读取信息");
	                		clientHandler.handleRead(key);
	                		
	                	}
	                	else if(key.isWritable()){
	                		logger.info("客户端发送信息");
	                		clientHandler.handleWrite(key);
	                		
	                	}
	                	it.remove();
	                }
				}
			}
			
		 
		 
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				socketChannel.close();
				logger.info("客户端关闭");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 
	 }
	 public static void main(String[] args){
			new Thread(new Client()).start();;
	 }
}
