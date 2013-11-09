package com.success.utils.ssmp.monitor;

import java.io.PrintWriter;

/**
 * 
 * @author bing.li
 *
 */
public interface QueueSpectacle{
	public String showInfo();
	public String showDetail();
	public void spectacle(String command, PrintWriter pw);
}
