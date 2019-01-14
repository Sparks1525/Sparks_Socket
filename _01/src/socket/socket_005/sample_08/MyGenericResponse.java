package socket.socket_005.sample_08;

public class MyGenericResponse implements MyResponse {  
	  
    private Object obj = null;  
  
    public MyGenericResponse(Object obj) {  
        this.obj = obj;  
    }  
  
    @Override  
    public Object getResult() {  
        return obj;  
    }  
}  