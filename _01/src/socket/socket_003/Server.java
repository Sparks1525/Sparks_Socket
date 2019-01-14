package socket.socket_003;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static String ip;
	public static void main(String[] args) throws IOException{
		ServerSocket ss = new ServerSocket(10086);
		System.out.println("服务器启动......");
		Socket s = null;
		boolean flag = true;
		while(flag){
			s=ss.accept();
			ip = s.getInetAddress().toString();
			ip = ip.substring(ip.indexOf("/")+1);
			System.out.println("服务器连接成功......");
			System.out.println(ip+"上线了");
			
			InputStream in  = s.getInputStream();
			OutputStream out = s.getOutputStream();
			
			DataInputStream din = new DataInputStream(in);
			DataOutputStream dout = new DataOutputStream(out);
			
			SendThread it = new SendThread(dout);
			PrintThread ot = new PrintThread(din,"client");
			
			new Thread(it).start();
			new Thread(ot).start();
		}
		System.out.println("服务器连接关闭......");
		s.close();
		ss.close();
	}
}
