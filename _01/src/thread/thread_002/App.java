package thread.thread_002;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShareReSource share = new ShareReSource();
		new Thread(new Producer(share),"Producer1").start();
		new Thread(new Producer(share),"Producer2").start();
		new Thread(new Consumer(share),"Consumer").start();
	}

}
