package socket.socket_005.sample_08;

public interface MyClient {  
	  
    public <T> T execute(MyRequest request, MyResponseHandler<T> handler);  
  
    public MyResponse execute(MyRequest request);  
  
} 
