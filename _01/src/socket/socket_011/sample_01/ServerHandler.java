package socket.socket_011.sample_01;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerHandler implements IServerHandler{

	@Override
	public void handleAccept(SelectionKey key) {
		// TODO Auto-generated method stub
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
		try {
			SocketChannel socketChannel = serverSocketChannel.accept();  
			socketChannel.configureBlocking(false);
			socketChannel.register(key.selector(), SelectionKey.OP_READ);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public void handleRead(SelectionKey key) {
		// TODO Auto-generated method stub
		SocketChannel socketChannel = (SocketChannel) key.channel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		try {
			int readBytes = socketChannel.read(byteBuffer);
			while(readBytes>0){
				String msg = new String(byteBuffer.array(),0,readBytes);
				System.out.println("服务端读取的信息为："+msg);
				byteBuffer.flip();
				socketChannel.write(byteBuffer);
				byteBuffer.clear();
				socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//socketChannel.close(); 
	}

	@Override
	public void handleWrite(SelectionKey key) {
		// TODO Auto-generated method stub
		SocketChannel socketChannel = (SocketChannel) key.channel();
		ByteBuffer byteBuffer = ByteBuffer.wrap(new String("服务端发送信息").getBytes());
		try {
			socketChannel.write(byteBuffer);
			socketChannel.register(key.selector(), SelectionKey.OP_READ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
