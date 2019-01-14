package thread.thread_001;

public class Thread10 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Apple1 a = new Apple1();
		new Thread(a,"小A").start();
		new Thread(a,"小B").start();
		new Thread(a,"小C").start();
	}

}
class Apple1 implements Runnable{
	private Integer nums = 100;
	public void run(){
		for(int i = 0;i<100;i++){
			take();
		}
	}
	public synchronized void take(){
		if(nums>0){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"当前拿走第"+nums--);
		}
	}
}
