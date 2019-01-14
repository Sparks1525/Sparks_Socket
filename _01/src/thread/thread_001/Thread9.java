package thread.thread_001;

public class Thread9 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Apple a = new Apple();
		new Thread(a,"小A").start();
		new Thread(a,"小B").start();
		new Thread(a,"小C").start();
	}

}
class Apple implements Runnable{
	private Integer nums = 100;
	Object obj = new Object();
	public void run(){
		for(int i=0;i<100;i++){
			//synchronized(Apple.class){
			//synchronized(this){
			synchronized(obj){
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
	}
	
}
