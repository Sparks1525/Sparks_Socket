package socket.socket_004.sample_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2 {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket("localhost",10086);
		client.setSoTimeout(10000);
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		PrintStream out = new PrintStream(client.getOutputStream());
		BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
		boolean flag = true;
		while(flag){
			System.out.println("请输入信息");
			String str = input.readLine();
			out.print(str);
			if("bye".equals(str)){
				flag = false;
			} else{
				String echo = buf.readLine();
				System.out.println(echo);
			}
		}
		input.close();
		if(client!=null){
			client.close();
		}
	}
}
