package socket.socket_003;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private static String ip = "localhost";
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Socket s = new Socket(ip,10086);
		System.out.println("客户端连接成功......");
		
		OutputStream out = s.getOutputStream();
		InputStream in = s.getInputStream();
		
		DataOutputStream dout = new DataOutputStream(out);
		DataInputStream din = new DataInputStream(in);
		
		SendThread it = new SendThread(dout);
		PrintThread ot = new PrintThread(din,"server");
		
		new Thread(ot).start();
		new Thread(it).start();

		
		s.close();//去掉
	}

}
