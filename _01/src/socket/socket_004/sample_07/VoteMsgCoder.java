package socket.socket_004.sample_07;

import java.io.IOException;  

public interface VoteMsgCoder {  
  byte[] toWire(VoteMsg msg) throws IOException;  
  VoteMsg fromWire(byte[] input) throws IOException;  
}  
