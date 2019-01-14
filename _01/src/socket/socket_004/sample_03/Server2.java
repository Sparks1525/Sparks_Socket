package socket.socket_004.sample_03;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
	public static void main(String[] args) throws IOException{
		ServerSocket server = new ServerSocket(10086);
		Socket client = null;
		boolean flag = true;
		while(flag){
			client = server.accept();
			System.out.println("success");
			new Thread(new ServerThread2(client)).start();
		}
		client.close();
		server.close();
	}

}
