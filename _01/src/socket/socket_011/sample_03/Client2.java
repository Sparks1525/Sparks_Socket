package socket.socket_011.sample_03;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Client2 {
	private Selector selector = null;
	private Charset charset = Charset.forName("UTF-8");
	static final int PORT = 9999;
	static final String HOST = "localhost";
	private SocketChannel socketChannel = null;
	
	public void init() throws IOException{
		selector = Selector.open();
		socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_READ);
		new Thread(new ClientThread()).start();
		Scanner scan = new Scanner(System.in);
		while(scan.hasNext()){
			String line = scan.nextLine();
			socketChannel.write(charset.encode(line));
		}
	}
	
	private class ClientThread implements Runnable{
		public void run(){
			try {
				while(true){
					int readyChannels = selector.select();
					if(readyChannels == 0) continue;
					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
					while(keyIterator.hasNext()){
						SelectionKey sk = (SelectionKey)keyIterator.next();
						keyIterator.remove();
						dealWithSelectionKey(sk);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private void dealWithSelectionKey(SelectionKey sk) throws IOException{
			if(sk.isReadable()){
				SocketChannel sc = (SocketChannel)sk.channel();
				ByteBuffer buff = ByteBuffer.allocate(1024);
				String content = "";
				while(sc.read(buff)>0){
					buff.flip();
					content += charset.decode(buff);
				}
				System.out.println(content);
                sk.interestOps(SelectionKey.OP_READ);
			}
		}
	}
	public static void main(String[] args) throws IOException{
		new Client2().init();
	}
	
}
