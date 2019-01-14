package socket.socket_004.sample_01;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		//创建一个服务端，并设端口为10086，客户端必须连在10086端口上
		ServerSocket server = new ServerSocket(10086);
		//接收过来的客户端
		Socket client = server.accept();
		//获取服务端的输出流对象
		PrintStream ps = new PrintStream(client.getOutputStream());
		System.out.println("sck.isConnected()):"+client.isConnected());
		ps.println("Server Output");
		ps.close();
		client.close();
		server.close();
		System.out.println("client:"+client);

	}

}
