package socket.socket_005.sample_06;

import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.ClosedChannelException;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.ServerSocketChannel;  
import java.nio.channels.SocketChannel;  
import java.util.Iterator;  
import java.util.logging.Level;  
import java.util.logging.Logger;  
  
public class MyServer {  
  
    private final static Logger logger = Logger.getLogger(MyServer.class.getName());  
      
    public static void main(String[] args) {  
        Selector selector = null;  
        ServerSocketChannel serverSocketChannel = null;  
          
        try {  
            // Selector for incoming time requests  
            selector = Selector.open();  
  
            //创建一个新的套接字服务并设置为非阻塞模式
            // Create a new server socket and set to non blocking mode  
            serverSocketChannel = ServerSocketChannel.open();  
            serverSocketChannel.configureBlocking(false);  
              
            //将服务器套接字绑定到本地主机和端口
            /*
             * 为了确保一个进程关闭socket后，即使它还没释放端口，
             * 同一主机上的其他进程可以立刻重用该端口，
             * 可以调用socket的setReuseAddress(true) 
             * 需要注意的是setReuseAddress(boolean on)方法必须在socket还未绑定到一个本地端口之前调用，
             * 否则无效 
             */
            // Bind the server socket to the local host and port  
            serverSocketChannel.socket().setReuseAddress(true);  
            serverSocketChannel.socket().bind(new InetSocketAddress(10000));  
              
            
            /*
             * 寄存器在服务器套接字上接受选择器。
             * 这个步骤告诉选择器，当接受操作发生时，
             * 套接字希望被放在就绪列表中，
             * 因此允许多路复用的非阻塞I/O发生。
             */
            // Register accepts on the server socket with the selector. This  
            // step tells the selector that the socket wants to be put on the  
            // ready list when accept operations occur, so allowing multiplexed  
            // non-blocking I/O to take place.  
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  
      
            /*
             * 这里发生的一切。当上述任何操作发生时，
             * 该方法将返回，线程已被中断，等等。
             */
            // Here's where everything happens. The select method will  
            // return when any operations registered above have occurred, the  
            // thread has been interrupted, etc.  
            while (selector.select() > 0) {  
                // Someone is ready for I/O, get the ready keys  
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();  
      
                //通过就绪键收集和处理日期请求。
                // Walk through the ready keys collection and process date requests.  
                while (it.hasNext()) {  
                    SelectionKey readyKey = it.next();  
                    it.remove();  
                      
                    //将关键索引插入选择器，以便检索已准备好I/O的套接字。
                    // The key indexes into the selector so you  
                    // can retrieve the socket that's ready for I/O  
                    execute((ServerSocketChannel) readyKey.channel());  
                }  
            }  
        } catch (ClosedChannelException ex) {  
            logger.log(Level.SEVERE, null, ex);  
        } catch (IOException ex) {  
            logger.log(Level.SEVERE, null, ex);  
        } finally {  
            try {  
                selector.close();  
            } catch(Exception ex) {}  
            try {  
                serverSocketChannel.close();  
            } catch(Exception ex) {}  
        }  
    }  
  
    private static void execute(ServerSocketChannel serverSocketChannel) throws IOException {  
        SocketChannel socketChannel = null;  
        try {  
            socketChannel = serverSocketChannel.accept();  
            MyRequestObject myRequestObject = receiveData(socketChannel);  
            logger.log(Level.INFO, myRequestObject.toString());  
              
            MyResponseObject myResponseObject = new MyResponseObject(  
                    "response for " + myRequestObject.getName(),   
                    "response for " + myRequestObject.getValue());  
            sendData(socketChannel, myResponseObject);  
            logger.log(Level.INFO, myResponseObject.toString());  
        } finally {  
            try {  
                socketChannel.close();  
            } catch(Exception ex) {}  
        }  
    }  
      
    private static MyRequestObject receiveData(SocketChannel socketChannel) throws IOException {  
        MyRequestObject myRequestObject = null;  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
          
        try {  
            byte[] bytes;  
            int size = 0;  
            //通过socketChannel向buufer中写数据
            while ((size = socketChannel.read(buffer)) >= 0) {  
                buffer.flip();  
                bytes = new byte[size];  
                buffer.get(bytes); //使用get()方法从Buffer中读取数据
                baos.write(bytes);  
                buffer.clear();  
            }  
            bytes = baos.toByteArray();  
            Object obj = SerializableUtil.toObject(bytes);  
            myRequestObject = (MyRequestObject)obj;  
        } finally {  
            try {  
                baos.close();  
            } catch(Exception ex) {}  
        }  
        return myRequestObject;  
    }  
  
    private static void sendData(SocketChannel socketChannel, MyResponseObject myResponseObject) throws IOException {  
        byte[] bytes = SerializableUtil.toBytes(myResponseObject);  
        ByteBuffer buffer = ByteBuffer.wrap(bytes);  
        socketChannel.write(buffer);  
    }  
}  