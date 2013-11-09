package com.success.utils.ssmp.net;

import com.success.utils.ssmp.monitor.ThreadSpectacle;


public interface SocketClient extends ThreadSpectacle{
	  public String open(String ip,int port,String mode);
	  public String close();
	  public void listen();
}
