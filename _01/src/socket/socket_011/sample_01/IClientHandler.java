package socket.socket_011.sample_01;

import java.nio.channels.SelectionKey;

public interface IClientHandler {
	public void handleConnect(SelectionKey key);
	public void handleRead(SelectionKey key);
	public void handleWrite(SelectionKey Key);

}
