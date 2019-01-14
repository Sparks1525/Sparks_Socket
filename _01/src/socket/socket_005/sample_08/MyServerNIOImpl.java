package socket.socket_005.sample_08;

import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
import java.lang.reflect.Method;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.ServerSocketChannel;  
import java.nio.channels.SocketChannel;  
import java.util.Iterator;  
import java.util.logging.Level;  
import java.util.logging.Logger;  
  
public class MyServerNIOImpl implements MyServer {  
  
    private final static Logger logger = Logger.getLogger(MyServerNIOImpl.class.getName());  
  
    private int port;  
  
    public MyServerNIOImpl(int port) {  
        this.port = port;  
    }  
  
    public void startup() throws Exception {  
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                Selector selector = null;  
                ServerSocketChannel serverSocketChannel = null;  
  
                try {  
                    selector = Selector.open();  
                    serverSocketChannel = ServerSocketChannel.open();  
                    serverSocketChannel.configureBlocking(false);  
  
                    serverSocketChannel.socket().setReuseAddress(true);  
                    serverSocketChannel.socket().bind(new InetSocketAddress(port));  
  
                    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  
  
                    while (selector.select() > 0) {  
                        try {  
                            Iterator<SelectionKey> it = selector.selectedKeys().iterator();  
                            while (it.hasNext()) {  
                                SelectionKey readyKey = it.next();  
                                it.remove();  
                                invoke((ServerSocketChannel) readyKey.channel());  
                            }  
                        } catch(Exception ex) {  
                            logger.log(Level.SEVERE, ex.getMessage(), ex);  
                        }  
                    }  
                } catch (Exception ex) {  
                    logger.log(Level.SEVERE, ex.getMessage(), ex);  
                } finally {  
                    try {  
                        selector.close();  
                    } catch(Exception ex) {}  
                    try {  
                        serverSocketChannel.close();  
                    } catch(Exception ex) {}  
                }  
            }  
        }).start();  
    }  
  
    @Override  
    public void shutdown() throws Exception {  
        // Implement me  
    }  
  
    private void invoke(ServerSocketChannel serverSocketChannel) throws Exception {  
        SocketChannel socketChannel = null;  
        try {  
            socketChannel = serverSocketChannel.accept();  
            MyRequest myRequest = receiveData(socketChannel);  
            MyResponse myResponse = execute(myRequest);  
            sendData(socketChannel, myResponse);  
        } finally {  
            try {  
                socketChannel.close();  
            } catch(Exception ex) {}  
        }  
    }  
  
    private MyResponse execute(MyRequest request) throws Exception {  
        Class clazz = request.getRequestClass();  
  
        String methodName = request.getRequestMethod();  
        Class<?>[] parameterTypes = request.getRequestParameterTypes();  
        Method method = clazz.getDeclaredMethod(methodName, parameterTypes);  
  
        Object[] parameterValues = request.getRequestParameterValues();  
        final Object obj = method.invoke(clazz.newInstance(), parameterValues);  
  
        return new MyGenericResponse(obj);  
    }  
  
    private MyRequest receiveData(SocketChannel socketChannel) throws IOException {  
        MyRequest myRequest = null;  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
  
        try {  
            byte[] bytes;  
            int size = 0;  
            while ((size = socketChannel.read(buffer)) >= 0) {  
                buffer.flip();  
                bytes = new byte[size];  
                buffer.get(bytes);  
                baos.write(bytes);  
                buffer.clear();  
            }  
            bytes = baos.toByteArray();  
            Object obj = SerializableUtil.toObject(bytes);  
            myRequest = (MyRequest)obj;  
        } finally {  
            try {  
                baos.close();  
            } catch(Exception ex) {}  
        }  
        return myRequest;  
    }  
  
    private void sendData(SocketChannel socketChannel, MyResponse myResponse) throws IOException {  
        byte[] bytes = SerializableUtil.toBytes(myResponse);  
        ByteBuffer buffer = ByteBuffer.wrap(bytes);  
        socketChannel.write(buffer);  
    }  
} 
