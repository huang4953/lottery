package com.success.lottery.sms;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.protocol.smip.SMIP_ActiveTest;
import com.success.protocol.smip.SMIP_ActiveTestResp;
import com.success.protocol.smip.SMIP_Connect;
import com.success.protocol.smip.SMIP_ConnectResp;
import com.success.protocol.smip.SMIP_DataPack;
import com.success.protocol.smip.SMIP_MO;
import com.success.protocol.smip.SMIP_MT;
import com.success.protocol.smip.SMIP_MTResp;
import com.success.protocol.smip.SMIP_OrderResp;
import com.success.protocol.smip.SMIP_QueryResp;
import com.success.protocol.smip.SMIP_Sequence;
import com.success.protocol.smip.SMIP_Terminate;
import com.success.protocol.smip.SMIP_TerminateResp;
import com.success.utils.AutoProperties;
import com.success.utils.LogTools;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.net.SocketClient;

/**
 * thread name: C-ip:port-localport
 * name: C-ip:port
 * ipPort: ip:port
 * info: C - SMSClient - ip:port - time - MO[in 0/succ 0/fail 0] - MT[0/0/0/0] - ORDER[0/0/0] - QUERY[0/0/0]
 * @author bing.li
 *
 */
public class SMSClient implements SocketClient{

	private String name;
	private String bootTime;
	private Socket socket = null;
	private InputStream in = null;
	private OutputStream out = null; 
	private SMIP_Terminate terminator = new SMIP_Terminate(0);
	private SMIP_DataPack dataPack;
	private SMIP_ActiveTest activeTest = new SMIP_ActiveTest(0);
//	private String confFileName = "com.success.lottery.sms.sms";
	private String ip;
	private int port;
	private String ipPort;
	private Log logger = LogFactory.getLog(SMSClient.class.getName());
	private boolean isStop = false;

	private Map<String, MT> mtMap = new HashMap<String, MT>();
	private Map<String, Order> orderMap = new HashMap<String, Order>();
	private Map<String, Query> queryMap = new HashMap<String, Query>();

	//从sms platform 进来的MO数量
	private int moIn;
	//成功放入MOProcess队列的MO数量
	private int moSucc;
	//放入MOProcess队列失败的数量
	private int moFail;
	
	//从MTProcess队列获得的MT下发请求数量
	private int mtIn;
	//成功提交到sms platform的MT请求数量
	private int mtSucc;
	//提交到sms platform失败的MT请求数量
	private int mtFail;

	//从sms platform进来的Order请求数量
	private int orderIn;
	//已经成功响应给sms platform的OrderResp数量
	private int orderSucc;
	//未能成功响应给sms platform的OrderResp数量（包括放入OrderProcess队列失败的Order请求）
	private int orderFail;

	//从sms platform进来的Query请求数量
	private int queryIn;
	//已经成功响应给sms platform的QueryResp数量
	private int querySucc;
	//未能成功响应给sms platform的QueryResp数量（包括放入QueryProcess队列失败的Query请求）
	private int queryFail;
	
	private String smsAppId;
	private String passwd;
	private int interval = 200;
	private int reconnectInterval = 30000;
	private int sendInterval = 200;
	private int connectionN = 3;
	private int connectionC = 180000;
	private int connectionT = 60000;
	private int connectionW = 16;

	public SMSClient(){
	}

	@Override
	public String close(){
		try {
			if (out != null) {
				terminator.setSequenceID(SMIP_Sequence.getNextVal(name));
				terminator.writePack(out);
			}
		} catch (Exception e) {
			logger.error(name + " send Exit Exception:" + e.toString());
		}
		try {
			if (out != null) {
				out.close();
			}
		} catch (Exception e) {
			logger.error(name + " out.close() Exception:" + e.toString());
		}
		try {
			if (in != null) {
				in.close();
			}
		} catch (Exception e) {
			logger.error(name + " in.close() Exception:" + e.toString());
		}
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (Exception e) {
			logger.error(name + " socket.close() Exception:" + e.toString());
		}
		socket = null;
		in = null;
		out = null;
		logger.info(name + " disconnected...");
		return null;
	}

	@Override
	public void listen(){
		logger.info(name + " is running...");
		bootTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		new Thread(this, name).start();
	}
	
	@Override
	public String open(String ip, int port, String mode){
		this.ip = ip;
		this.port = port;
		this.ipPort = ip + "," + port;
		logger = LogFactory.getLog(this.getClass().getName() + "." + ip + "." + port);
		readConfig();
		return null;
	}

	@Override
	public boolean isAlive(){
		if(socket == null){
			return false;
		}
		if(socket.isClosed()){
			return false;
		}
		if(!socket.isConnected()){
			return false;
		}
		if(in == null){
			return false;
		}
		if(out == null){
			return false;
		}
		return true;
	}

	@Override
	public String showDetail(){
		StringBuffer sb = new StringBuffer();
		sb.append(showInfo() + "\n");
		sb.append("\t").append(this.name + "'s Parameter:").append("\n");
		sb.append("\t\t");
		try{
			sb.append("timeout=" + this.socket.getSoTimeout()).append("\n");
		}catch(IOException e){
			sb.append("timeout=" + e.toString()).append("\n");
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		sb.append("\t\t");
		sb.append("isStop=" + this.isStop).append(" , ").append("isAlive=").append(this.isAlive()).append("\n");
		sb.append("\n");
		sb.append("\t").append(this.name + "'s Queue:").append("\n");
		sb.append("\t\t");
		sb.append("to be done");
		return sb.toString();
	}

	@Override
	public String showInfo(){
		return "C - " + name + " - " + ip + ":" + port + " - " + bootTime + " - " + "MO[" + moIn + "/" + moSucc + "/" + moFail + "] - MT[" + mtIn + "/" + mtSucc + "/" + mtFail + "/" + mtMap.size() + "] - ORDER[" + orderIn + "/" + orderSucc + "/" + orderFail + "/" + orderMap.size() +"] - QUERY["  + queryIn + "/" + querySucc + "/" + queryFail + "/" + queryMap.size() + "] - " + isAlive();
	}

	@Override
	public void spectacle(String command, PrintWriter pw){
	}

	@Override
	public void stop() throws SSMPException{
		this.isStop = true;
	}

	@Override
	public String getName(){
		return this.name;
	}

	@Override
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public void run(){
		int activeTestCount = 0;
		// cts = communication timestamp;
		long cts = System.currentTimeMillis();
		// sbts = send begin timestamp
		long sbts = System.currentTimeMillis();
		// sets = send end timestamp
		long sets = 0L;

		while(!isStop){
			if(isStop && socket != null){
				try{
					terminator.setSequenceID(SMIP_Sequence.getNextVal(smsAppId));
					terminator.writePack(out);
				}catch(Exception e){
					logger.error(" send terminator exception:" + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
				}
				break;
			}
			try{
				//sendInterval = AutoProperties.getInt(name + ".sendInterval", 200, confFileName);
				int rc = 0;
				while(!isStop && socket == null){
					if((rc = openSMIP()) != 0){
						close();
						try {
							Thread.sleep(reconnectInterval);
						} catch (Throwable tt) {
						}
					}else {
						activeTestCount = 0;
						cts = System.currentTimeMillis();
						sbts = System.currentTimeMillis();
						sets = 0L;
						try {
							socket.setSoTimeout(connectionN * connectionC + 1);
						} catch (SocketException e) {
							logger.debug(" run() setSoTimeout() Exception:" + e.toString());
						}
					}
					logger.debug(" connected result:" + rc + ", isStop=" + isStop);
				}
				if(isStop){
					break;
				}
				// bts = begin timestamp;
				long bts = System.currentTimeMillis();
				// ets = end timestamp;
				long ets = System.currentTimeMillis();
				if ((rc = in.available()) > 0) {
					dataPack = SMIP_DataPack.readPack(in, out);
					switch(dataPack.getCommandID()){
						case SMIP_DataPack.SMIP_MO :
							logger.debug("got SMIP_MO:" + ((SMIP_MO) dataPack).toString());
							if(logger.isDebugEnabled()){
								LogTools.logSystemOut("got a SMIP_MO:" + ((SMIP_MO)dataPack).toString());
								LogTools.logSystemOut(SMIP_DataPack.dumpLine);
								for (String str : ((SMIP_MO)dataPack).getDataPack()) {
									LogTools.logSystemOut("  " + str, SMIP_DataPack.dumpLine.length());
								}
								LogTools.logSystemOut(SMIP_DataPack.dumpLine);
							}
							gotMO((SMIP_MO)dataPack);
							break;
						case SMIP_DataPack.SMIP_MT_RESP :
							logger.debug("got SMIP_MTResp:" + ((SMIP_MTResp) dataPack).toString());
							if(logger.isDebugEnabled()){
								LogTools.logSystemOut("got a SMIP_MTResp:" + ((SMIP_MTResp)dataPack).toString());
								LogTools.logSystemOut(SMIP_DataPack.dumpLine);
								for (String str : ((SMIP_MTResp)dataPack).getDataPack()) {
									LogTools.logSystemOut("  " + str, SMIP_DataPack.dumpLine.length());
								}
								LogTools.logSystemOut(SMIP_DataPack.dumpLine);
							}
							gotMTResp((SMIP_MTResp)dataPack);
							break;
						case SMIP_DataPack.SMIP_ACTIVE_TEST :
							if (logger.isDebugEnabled()) {
								logger.debug("got a ActiveTest:" + ((SMIP_ActiveTest)dataPack).toString());
								LogTools.logSystemOut("got a ActiveTest:" + ((SMIP_ActiveTest)dataPack).toString());
								LogTools.logSystemOut(SMIP_DataPack.dumpLine);
								for (String str : ((SMIP_ActiveTest)dataPack).getDataPack()) {
									LogTools.logSystemOut("  " + str, SMIP_DataPack.dumpLine.length());
								}
								LogTools.logSystemOut(SMIP_DataPack.dumpLine);
							}
							cts = System.currentTimeMillis();
							activeTestCount = 0;
							break;
						case SMIP_DataPack.SMIP_ACTIVE_TEST_RESP :
							if (logger.isDebugEnabled()) {
								logger.debug("got a ActiveTestResp:" + ((SMIP_ActiveTestResp)dataPack).toString());
								LogTools.logSystemOut("got a ActiveTestResp:" + ((SMIP_ActiveTestResp)dataPack).toString());
								LogTools.logSystemOut(SMIP_DataPack.dumpLine);
								for (String str : ((SMIP_ActiveTestResp)dataPack).getDataPack()) {
									LogTools.logSystemOut("  " + str, SMIP_DataPack.dumpLine.length());
								}
								LogTools.logSystemOut(SMIP_DataPack.dumpLine);
							}
							cts = System.currentTimeMillis();
							activeTestCount = 0;
							break;
						case SMIP_DataPack.SMIP_TERMINATE :
							logger.debug("got SMIP_Terminate:" + ((SMIP_Terminate) dataPack).toString());
							close();
							break;
						case SMIP_DataPack.SMIP_TERMINATE_RESP :
							logger.debug("got SMIP_TerminateResp:" + ((SMIP_TerminateResp) dataPack).toString());
							close();
							break;
						default :
							logger.warn("got Unexpected SMIP_DataPack:" + dataPack.toString());
					}
					if (rc > 4000) {
						continue;
					}
				}
				
				MT sms = null;
				//读取MT
				SMIP_MT mt = getMT(sms);
				//读取ORDERResp				
				SMIP_OrderResp orderResp = getOrderResp();
				//读取QUERYResp
				SMIP_QueryResp queryResp = getQueryResp();

				if (mt != null){
					if ((sbts = System.currentTimeMillis()) - sets < sendInterval) {
						try {
							Thread.sleep((long) (sendInterval - (sbts - sets)));
						} catch (Exception e) {
						}
					}
					try {
						sms = mtMap.get("" + mt.getSequenceID());
						if(sms != null){
							logger.debug("before writePack sms:" + sms.toString());
							sms.setOutTime(System.currentTimeMillis());
							mtMap.put("" + sms.getSequence(), sms);
						}
						mt.writePack(out);
					} catch (Exception e) {
						logger.error(" send SMIP_MT(" + mt.toString() + ") occur Exception:" + e.toString());
						if(logger.isDebugEnabled()){
							e.printStackTrace();
						}
						writeException(mt, e.toString());
					}
					sets = System.currentTimeMillis();
					logger.debug("send SMIP_MT:" + mt.toString());
					if (logger.isDebugEnabled()) {
						LogTools.logSystemOut(" send SMIP_MT:" + mt.toString());
						LogTools.logSystemOut(SMIP_DataPack.dumpLine);
						for (String str : mt.getDataPack()) {
							LogTools.logSystemOut("  " + str, SMIP_DataPack.dumpLine.length());
						}
						LogTools.logSystemOut(SMIP_DataPack.dumpLine);
					}
				} else if(orderResp != null){

				} else if(queryResp != null){
					
				} else if((sbts = System.currentTimeMillis()) - cts >= connectionC){
					activeTestCount++;
					if (activeTestCount > connectionN){
						logger.debug(" activeTest not response " + (activeTestCount - 1) + "times, disconnection...");
						close();
						break;
					}
					activeTest.setSequenceID(SMIP_Sequence.getNextVal(smsAppId));
					activeTest.writePack(out);
					cts = sbts;
					if (logger.isDebugEnabled()) {
						logger.debug("send ActiveTest, " + "sbts=" + sbts + ", cts=" + cts);
						LogTools.logSystemOut("send ActiveTest:" + activeTest.toString());
						LogTools.logSystemOut(SMIP_DataPack.dumpLine);
						for (String str : activeTest.getDataPack()) {
							LogTools.logSystemOut("  " + str, SMIP_DataPack.dumpLine.length());
						}
						LogTools.logSystemOut(SMIP_DataPack.dumpLine);
					}
				} else if((ets = System.currentTimeMillis()) - bts < interval) {
					try {
						Thread.sleep((long) (interval - (ets - bts)));
					} catch (Exception e) {
					}
				}
			} catch (InterruptedIOException ex) {
				try {
					socket.setSoTimeout(connectionN * connectionC + 1);
				} catch (SocketException e) {
				}
				logger.warn(" run() InterruptedIOException:" + ex.toString());
				if (logger.isDebugEnabled()) {
					ex.printStackTrace();
				}
				try {
					Thread.sleep(2000);
				} catch (Throwable tt) {
				}
			} catch (IOException e) {
				logger.error(" run() occur IOException:" + e.toString());
				if (logger.isDebugEnabled()) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(20000);
				} catch (Throwable tt) {
				}
				close();
			} catch (Exception e) {
				logger.error(" run() occur Exception:" + e.toString());
				if (logger.isDebugEnabled()) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(20000);
				} catch (Throwable tt) {
				}
				close();
			} catch (Throwable t) {
				logger.error(" run() throw Exception:" + t.toString());
				if (logger.isDebugEnabled()) {
					t.printStackTrace();
				}
				try {
					Thread.sleep(20000);
				} catch (Throwable tt) {
				}
				close();
				try {
					Thread.sleep(2000);
				} catch (Throwable tt) {
				}
			}
		}
		close();
		logger.info("SMSClinet " + this.name + " is shutdown!");
	}

	private void readConfig(){
		String resource = "com.success.lottery.sms.sms";
		smsAppId = AutoProperties.getString(ipPort + ".smsAppId", "", resource);
		passwd = AutoProperties.getString(ipPort + ".smsPassword", "", resource);
		interval = AutoProperties.getInt(ipPort + ".interval", 200, resource);
		reconnectInterval = AutoProperties.getInt(ipPort + ".reconnectInterval", 30000, resource);
		sendInterval = AutoProperties.getInt(ipPort + ".sendInterval", 200, resource);
		connectionN = AutoProperties.getInt(ipPort + ".connectionN", 3, resource);
		connectionC = AutoProperties.getInt(ipPort + ".connectionC", 180000, resource);
		connectionT = AutoProperties.getInt(ipPort + ".connectionT", 60000, resource);
		connectionW = AutoProperties.getInt(ipPort + ".connectionW", 16, resource);
	}

	private int openSMIP(){
		try{
			socket = new Socket(ip, port);
			logger = LogFactory.getLog(SMSClient.class.getName() + "." + ip + "." + port + "." + socket.getLocalPort());
			socket.setSoTimeout(reconnectInterval);
			socket.setSendBufferSize(300000);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			String desc = "SMSClient@" + ip + ":" + port + "-" + socket.getLocalPort();
			SMIP_Connect connect = new SMIP_Connect(0);
			connect.setFields(smsAppId, passwd, 0, 10);
			connect.setSequenceID(SMIP_Sequence.getNextVal(smsAppId));
			connect.writePack(out);
			logger.debug("send SMIP_Connect:" + connect.toString());
			if(logger.isDebugEnabled()){
				LogTools.logSystemOut(desc + " send SMIP_Connect:" + connect.toString());
				LogTools.logSystemOut(SMIP_DataPack.dumpLine);
				for (String str : connect.getDataPack()) {
					LogTools.logSystemOut("  " + str, SMIP_DataPack.dumpLine.length());
				}
				LogTools.logSystemOut(SMIP_DataPack.dumpLine);
			}
			dataPack = null;
			int retry = 0;
			while ((dataPack == null || !(dataPack instanceof SMIP_ConnectResp)) && !isStop){
				try{
					if (dataPack != null) {
						logger.error(" got error SMIP_ConnectResp:" + dataPack.toString());
						if (logger.isDebugEnabled()) {
							LogTools.logSystemOut(desc + " got error SMIP_ConnectResp:" + dataPack.toString());
							LogTools.logSystemOut(SMIP_DataPack.dumpLine);
							for (String str : dataPack.getDataPack()) {
								LogTools.logSystemOut("  " + str, SMIP_DataPack.dumpLine.length());
							}
							LogTools.logSystemOut(SMIP_DataPack.dumpLine);
						}
					}
					dataPack = SMIP_DataPack.readPack(in, out);
					logger.debug(" got SMIP_ConnectResp:" + dataPack.toString());
					if (logger.isDebugEnabled()) {
						LogTools.logSystemOut(desc + " got SMIP_ConnectResp:" + dataPack.toString());
						LogTools.logSystemOut(SMIP_DataPack.dumpLine);
						for (String str : dataPack.getDataPack()) {
							LogTools.logSystemOut("  " + str, SMIP_DataPack.dumpLine.length());
						}
						LogTools.logSystemOut(SMIP_DataPack.dumpLine);
					}
				}catch(InterruptedIOException iioe){
					logger.warn(" openSMIP() occur Exception:" + iioe.toString());
					if (logger.isDebugEnabled()) {
						iioe.printStackTrace();
					}
					if (retry++ > 3) {
						throw iioe;
					}
				}
				if (dataPack != null && dataPack instanceof SMIP_ConnectResp) {
					logger.debug(" connect result:" + ((SMIP_ConnectResp) dataPack).getStatus());
					return ((SMIP_ConnectResp) dataPack).getStatus();
				} else {
					logger.debug(" openSMIP() is stopped, isStop:" + this.isStop); 
					return -1;
				}
			}
		}catch(Exception e){
			logger.error("openSMIP() occur Exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return -3;
		}
		return -2;
	}

	private void gotMO(SMIP_MO mo){
		moIn++;
		MO sms = new MO(mo);
		sms.setClientName(this.name);
		sms.setInTime(System.currentTimeMillis());
		sms.setFromIp(ip + ":" + port);		
		String rs = SMSDispatch.dispatch(sms);
		if(rs != null){
			sms.setErrMsg(rs);
			moFail++;
			//此处考虑放入队列出错后如何处理，目前未处理
		}
		moSucc++;
		sms.setOutTime(System.currentTimeMillis());
		SMSLog.getInstance("MO").log(sms);
	}

	private void gotMTResp(SMIP_MTResp mtResp){
		long id = mtResp.getSequenceID();
		MT sms = mtMap.get("" + id);
		if(sms == null || id != sms.getSequence()){
			logger.warn("got SMIP_MTResp(" + mtResp.toString() + ") missed source id:" + id);
			return;
		}
		sms.setOutTime(System.currentTimeMillis());
		if(mtResp.getStatus() != 0){
			mtFail++;
			sms.setErrMsg("Fail submit MT:" + mtResp.getStatus() + " " + mtResp.getErrorDescription());
//			//如果是可以重试的错误
//			if(){
//				再次放入MT队列
//			}
		}else{
			mtSucc++;
		}
		mtMap.remove("" + id);
		SMSLog.getInstance("MT").log(sms);
	}
	
	private void checkMTTimeout(){
		MT sms = null;
		Iterator<Entry<String, MT>> iterator = mtMap.entrySet().iterator();
		while(iterator.hasNext()){
			sms = iterator.next().getValue();
			if(System.currentTimeMillis() - sms.getOutTime() < connectionT){
				continue;
			}
			sms.setErrMsg("Fail:not responsing>" + connectionT);
			mtFail++;
			iterator.remove();
			sms.sendCount();
			sms.addRemark("sendCount", "" + sms.getSendCount());
			SMSLog.getInstance("MT").log(sms);
			if(sms.getSendCount() <= connectionN){
//				// 重发
//					再次放入MT队列
			}
		}
	}
	
	private SMIP_MT getMT(MT sms){
		checkMTTimeout();
		sms = SMSDispatch.getMT(this.name);
		if(sms == null){
			return null;
		}
		logger.debug("SMSClient get a sms(MT):" + sms.toString());
		sms.setInTime(sms.getOutTime());
		mtIn++;
		SMIP_MT mt = null;
		try{
			mt = sms.parseToSMIPMessage();		
			sms.setClientName(this.name);
			sms.setToIp(ip + ":" + port);
			int sequence = SMIP_Sequence.getNextVal(smsAppId);
			mt.setSequenceID(sequence);
			sms.setSequence(sequence);
		}catch(Exception e){
			sms.setErrMsg("parseSMIP Fail:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		if(sms.getErrMsg() != null){
			mtFail++;
			sms.setOutTime(System.currentTimeMillis());
			SMSLog.getInstance("MT").log(sms);
			return null;
		}
		mtMap.put("" + sms.getSequence(), sms);
		return mt;
	}

	private void writeException(SMIP_MT mt, String errStr){
		String id = mt.getSequenceID() + "";
		MT sms = mtMap.get(id);
		if(sms == null){
			logger.warn("SMIP_MT(" + mt.toString() + ") missed source id:" + id);
			return;
		} else {
			sms.setErrMsg(errStr);
			SMSLog.getInstance("MT").log(sms);
		}
		mtFail++;
		mtMap.remove(id);
		sms.sendCount();
		if(sms.getSendCount() < connectionN){
//			// 重发
//			再次放入MT队列
		}
	}

	private SMIP_OrderResp getOrderResp(){
		return null;
	}

	private SMIP_QueryResp getQueryResp(){
		return null;
	}
	
	public static void main(String[] args){
		//System.out.println(new SMSClient().showInfo());
		System.out.println(AutoProperties.getInt("192.168.0.1,9676.sendInterval", 500, "com.success.lottery.sms.sms"));
		System.out.println(AutoProperties.getString("aaa", "adfasd", "com.success.lottery.sms.sms"));
		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("111", "aaa");
		map.put("111", "bbb");
		map.put("111", "ccc");
		
		System.out.println(map.size());
		System.out.println(map.toString());
		
	}

	@Override
	public void setParameter(String parameter){		
	}
}
