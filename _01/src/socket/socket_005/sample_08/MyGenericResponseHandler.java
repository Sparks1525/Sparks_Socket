package socket.socket_005.sample_08;

public class MyGenericResponseHandler<T> implements MyResponseHandler<T> {  
    @Override  
    public T handle(MyResponse response) {  
        return (T) response.getResult();  
    }  
}  
