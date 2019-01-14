package socket.socket_004.sample_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread2 implements Runnable{
	private Socket client;
	public ServerThread2(Socket client){
		this.client = client;
	}
	public void run(){
		try {
			PrintStream out = new PrintStream(client.getOutputStream());
			BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
			boolean flag = true;
			while(flag){
				String str = buf.readLine();
				if(str==null||"".equals(str)){
					flag = false;
				} else{
					if("bye".equals(str)){
						out.println("echo:"+str);
					}
				}
			}
			out.close();
			client.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
	