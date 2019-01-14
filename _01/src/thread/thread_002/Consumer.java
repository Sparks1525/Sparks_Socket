package thread.thread_002;

public class Consumer implements Runnable{
	private ShareReSource share = null;
	public Consumer(ShareReSource share){
		this.share= share;
	}
	public void run(){
		for(int i =0 ; i<5000;i++){
			share.popup();
		}
		
	}
}
