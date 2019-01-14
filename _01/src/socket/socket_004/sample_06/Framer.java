package socket.socket_004.sample_06;

import java.io.IOException;  
import java.io.OutputStream;  
  
public interface Framer {  
  void frameMsg(byte[] message, OutputStream out) throws IOException;  
  byte[] nextMsg() throws IOException;  
}  