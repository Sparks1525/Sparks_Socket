package socket.socket_011.sample_01;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class ClientHandler implements IClientHandler{
	private final static Logger logger = Logger.getLogger(Client.class.getName());
	@Override
	public void handleConnect(SelectionKey key) {
		// TODO Auto-generated method stub
		SocketChannel socketChannel = (SocketChannel) key.channel();
		if(socketChannel.isConnectionPending()){
			try {
				socketChannel.finishConnect();
				logger.info("客户端连接成功");
				socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				System.out.println("客户端读取的信息为："+msg);
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
		ByteBuffer byteBuffer = ByteBuffer.wrap(new String("客户端发送信息").getBytes());
		try {
			socketChannel.write(byteBuffer);
			socketChannel.register(key.selector(), SelectionKey.OP_READ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
