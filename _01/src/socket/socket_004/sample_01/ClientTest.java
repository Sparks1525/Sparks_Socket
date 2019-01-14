package socket.socket_004.sample_01;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTest {
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub

		//表示连接到互联网上的哪一台电脑中的哪一个程序
		Socket client = new Socket("localhost",10086);
		//打开客户输入流
		Scanner sc = new Scanner(client.getInputStream());
		while(sc.hasNextLine()){
			System.out.println(sc.nextLine());
		}
		sc.close();
		client.close();
	}

}
