package thread.thread_002;

public class Producer implements Runnable{
	private ShareReSource share = null;
	public Producer(ShareReSource share){
		this.share = share;
	}
	
	public void run(){
		for(int i = 0;i<5000;i++){
			if(i%2==0){
				share.push("张三", "男");
			}else{
				share.push("李四","女");
			}
		}
	}
}
