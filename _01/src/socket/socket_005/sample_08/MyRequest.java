package socket.socket_005.sample_08;

import java.io.Serializable;  

public interface MyRequest extends Serializable {  
  
    Class<?> getRequestClass();  
  
    String getRequestMethod();  
  
    Class<?>[] getRequestParameterTypes();  
  
    Object[] getRequestParameterValues();  
  
} 