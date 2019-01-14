package thread.thread_001;

class Cuss implements Runnable{
	private int sum;
	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		for(int i = 0; i<3;i++){
			sum = sum + 100;
			System.out.println("sum:"+sum);
		}
		
	}
	
}
public class Thread3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cuss cs = new Cuss();
		Thread td1 = new Thread(cs);
		Thread td2 = new Thread(cs);
		td1.start();
		td2.start();
	}

}
