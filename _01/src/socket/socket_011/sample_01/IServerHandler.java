package socket.socket_011.sample_01;

import java.nio.channels.SelectionKey;

public interface IServerHandler {
	public void handleAccept(SelectionKey key);
	public void handleRead(SelectionKey key);
	public void handleWrite(SelectionKey key);
}
