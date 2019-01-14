package socket.socket_011.sample_03;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import socket.socket_012.ChatRoomServer;

public class Server {
	private Selector selector = null;
	static final int PORT = 9999;
	private Charset charset = Charset.forName("UTF-8");
	
	public void init() throws IOException{
		selector = Selector.open();
		ServerSocketChannel server = ServerSocketChannel.open();
		server.bind(new InetSocketAddress(PORT));
		server.configureBlocking(false);
		server.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("Server is listening now...");
		
		while(true){
			int readyChannels = selector.select();
			if(readyChannels == 0) continue;
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
			while(keyIterator.hasNext()){
				SelectionKey sk = (SelectionKey)keyIterator.next();
				keyIterator.remove();
				dealWithSelectionKey(server,sk);
			}
		}
	}
	
	public void dealWithSelectionKey(ServerSocketChannel server,SelectionKey sk) throws IOException {
		if(sk.isAcceptable()){
            SocketChannel sc = server.accept();        
            sc.configureBlocking(false);        
            sc.register(selector, SelectionKey.OP_READ);
            sk.interestOps(SelectionKey.OP_ACCEPT);
            System.out.println("Server is listening from client :" + sc.getRemoteAddress());
            sc.write(charset.encode("Please input ."));
        }
        
        if(sk.isReadable()){   
            SocketChannel sc = (SocketChannel)sk.channel(); 
            ByteBuffer buff = ByteBuffer.allocate(1024);
            StringBuilder content = new StringBuilder();
            try{
                while(sc.read(buff) > 0){
                    buff.flip();
                    content.append(charset.decode(buff));  
                }
                
                for(SelectionKey key : selector.keys())
                {
                    Channel targetchannel = key.channel();
                    if(targetchannel instanceof SocketChannel && targetchannel!=sc){
                        SocketChannel dest = (SocketChannel)targetchannel;
                        dest.write(charset.encode(content.toString()));
                    }
                }
                System.out.println("Server is listening from client " + sc.getRemoteAddress() + " data rev is: " + content);
                sk.interestOps(SelectionKey.OP_READ);
            }catch (IOException io){
                sk.cancel();
                if(sk.channel() != null){
                    sk.channel().close();
                }
            }
        }
	}
	public static void main(String[] args) throws IOException{
		new Server().init();
	}
}
