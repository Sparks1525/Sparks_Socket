package socket.socket_002;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {
	private Selector selector = null;
	private ServerSocketChannel serverSocketChannel = null;
	private int port = 10086;
	private Charset charset = Charset.forName("GBK");
	
	public EchoServer() throws IOException{
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		System.out.println("服务器启动");
	}
	public void service() throws IOException{
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while(selector.select() > 0){
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = readyKeys.iterator();
			while(it.hasNext()){
				SelectionKey key = null;
				try{
					key = (SelectionKey)it.next();
					it.remove();
					if(key.isAcceptable()){
						ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
						SocketChannel socketChannel = (SocketChannel)ssc.accept();
						System.out.println("接收到客户连接，来自："+socketChannel.socket().getInetAddress()
								+":"+socketChannel.socket().getPort());
						socketChannel.configureBlocking(false);
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						socketChannel.register(selector, SelectionKey.OP_READ
								|SelectionKey.OP_WRITE,buffer);
					}
					if(key.isReadable()){
						receive(key);
					}
					if(key.isWritable()){
						send(key);
					}
				} catch(IOException e){
					e.printStackTrace();
					try{
						if(key!=null){
							key.cancel();
							key.channel().close();
						}
					}catch(Exception ex){
						e.printStackTrace();
					}
				}
			}//while
		}//while
	}
	
	public void send(SelectionKey key) throws IOException{
		ByteBuffer buffer = (ByteBuffer)key.attachment();
		SocketChannel socketChannel = (SocketChannel)key.channel();
		buffer.flip();//把极限设为位置，把位置设为0
		String data = decode(buffer);
		if(data.indexOf("/r/n") == -1)
			return;
		String outputData = data.substring(0,data.indexOf("/n") + 1);
		System.out.println(outputData);
		ByteBuffer outputBuffer = encode("echo:" + outputData);
		//发送一行字符串
		while(outputBuffer.hasRemaining())
			socketChannel.write(outputBuffer);
		ByteBuffer temp = encode(outputData);
		buffer.position(temp.limit());
		buffer.compact();//删除已经处理的字符串
		if(outputData.contentEquals("bye/r/n")){
			key.cancel();
			socketChannel.close();
			System.out.println("关闭与客户的连接");
		}
	}
	public void receive(SelectionKey key) throws IOException{
		ByteBuffer buffer = (ByteBuffer)key.attachment();
		SocketChannel socketChannel = (SocketChannel)key.channel();
		ByteBuffer readBuff = ByteBuffer.allocate(32);
		socketChannel.read(readBuff);
		readBuff.flip();
		buffer.limit(buffer.capacity());
		buffer.put(readBuff);//把读到的数据放到buffer中
	}
	
	public String decode(ByteBuffer buffer){
		//解码
		CharBuffer charBuffer = charset.decode(buffer);
		return charBuffer.toString();
	}
	
	public ByteBuffer encode(String str){
		//编码
		return charset.encode(str);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		EchoServer server = new EchoServer();
		server.service();
	}

}