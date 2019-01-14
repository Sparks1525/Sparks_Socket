package thread.thread_001;

public class Thread1{

	public static void main(String[] args) {
		Thread th0 = null;
		th0=new ThreadTest0();
		th0.start();
		
		Thread th1 =null;
		th1 = new ThreadTest1();
		th1.start();
		
		th0 = new ThreadTest1();
		th0.start();
		th1 = new ThreadTest0();
		th1.start();
		//th.run();
		for(int x=0; x<6; x++)
			System.out.println("main run:"+Thread.currentThread().getName()+"----"+x);
	}

}

class ThreadTest0 extends Thread{
	public void run()
	{
		for(int x=0; x<6; x++)
			System.out.println("ThreadTest0 run:"+Thread.currentThread().getName()+"----"+x);
	}
}
class ThreadTest1 extends Thread{
	public void run()
	{
		for(int x=0; x<6; x++)
			System.out.println("ThreadTest1 run:"+Thread.currentThread().getName()+"----"+x);
	}
}
