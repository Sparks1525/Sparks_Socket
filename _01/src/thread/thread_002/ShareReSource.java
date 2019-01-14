package thread.thread_002;

public class ShareReSource {
	private String name;
	private String sex;
	
	private boolean isEmpty=true;
	
	public synchronized void push(String name,String sex){
		while(!isEmpty){
			try {
				System.out.println(Thread.currentThread().getName()+"wait");
				this.wait();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		System.out.println(Thread.currentThread()+"set");
		this.name = name;
		this.sex = sex;
		isEmpty = false;
		this.notifyAll();
	}
	public synchronized void popup(){
		while(isEmpty){
			
			try {
				System.out.println(Thread.currentThread().getName()+"wait");
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println(this.name+"----"+this.sex);
		isEmpty = true;
		this.notifyAll();
	}
}
