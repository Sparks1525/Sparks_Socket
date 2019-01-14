package socket.socket_003;

import java.io.DataInputStream;
import java.io.IOException;

public class PrintThread implements Runnable{
	private DataInputStream din;
	private String ip;
	public PrintThread(DataInputStream din, String ip){
		this.din = din;
		this.ip = ip;
	}
	public void run(){
		while(true){
			try {
				String msg = din.readUTF();
				System.out.println(ip+":"+msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
