package socket.socket_003;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SendThread implements Runnable{
	private DataOutputStream dout;

	public SendThread(DataOutputStream dout){
		this.dout = dout;
	}
	public synchronized void run(){
		boolean flag = true;
		while(flag){
			BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
			try {
				String msg = buf.readLine();
				if(msg.equals("bye")){
					flag = false;
				}else{
					dout.writeUTF(msg);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
