package socket.socket_005.sample_08;

import java.util.List;  

public interface MyUserService {  
  
    List<User> list(int size);  
  
    User findByName(String name);  
  
    void test();  
}
