package com.success.utils.ssmp.net;

import java.net.Socket;

import com.success.utils.ssmp.monitor.ThreadSpectacle;

public interface SocketConsumer extends ThreadSpectacle{
	public void consume(Socket socket);
}
