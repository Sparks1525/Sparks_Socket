package socket.socket_005.sample_08;

public class MyGenericRequest implements MyRequest {  
	  
    private static final long serialVersionUID = 1L;  
  
    private Class<?> requestClass;  
    private String requestMethod;  
    private Class<?>[] requestParameterTypes;  
    private Object[] requestParameterValues;  
  
    public MyGenericRequest(Class<?> requestClass, String requestMethod, Class<?>[] requestParameterTypes, Object[] requestParameterValues) {  
        this.requestClass = requestClass;  
        this.requestMethod = requestMethod;  
        this.requestParameterTypes = requestParameterTypes;  
        this.requestParameterValues = requestParameterValues;  
    }  
  
    @Override  
    public Class<?> getRequestClass() {  
        return requestClass;  
    }  
  
    @Override  
    public String getRequestMethod() {  
        return requestMethod;  
    }  
  
    @Override  
    public Class<?>[] getRequestParameterTypes() {  
        return requestParameterTypes;  
    }  
  
    @Override  
    public Object[] getRequestParameterValues() {  
        return requestParameterValues;  
    }  
}  