package com.success.utils.ssmp;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.success.utils.ssmp.monitor.QueueSpectacle;
public class SSMPQueue<E> extends ArrayBlockingQueue<E> implements QueueSpectacle{

	private static final long					serialVersionUID	= 1L;
	private static Hashtable<String, SSMPQueue>	queMap				= new Hashtable<String, SSMPQueue>();

	public synchronized static <E>SSMPQueue<E> getQueue(String queueName){
		SSMPQueue<E> que = queMap.get(queueName);
		if(que == null){
			int capacity = 4000;
			try{
				capacity = Integer.parseInt(System.getProperty(queueName + ".capacity"));
			}catch(Exception e){
				capacity = 4000;
			}
			que = new SSMPQueue<E>(capacity, true);
			que.setQueueName(queueName);
			queMap.put(queueName, que);
		}
		return que;
	}

	public static boolean containsQueue(String queueName){
		return queMap.containsKey(queueName);
	}

	public static String showAll(){
		StringBuffer sb = new StringBuffer();
		for(SSMPQueue<?> que : queMap.values()){
			sb.append(que.showInfo() + "\n");
		}
		return sb.toString();
	}

//	public static void setConfFileName(String confFileName){
//		confFileName = confFileName;
//	}

	
	private volatile long	inSucc	= 0;
	private volatile long	inFail	= 0;
	private volatile long	outSucc	= 0;
	private volatile long	outFail	= 0;
	private String			queueName;

	public void setQueueName(String queueName){
		this.queueName = queueName;
	}

	@Override
	public boolean add(E e){
		boolean rc = super.add(e);
		if(rc){
			inSucc++;
		}else{
			inFail++;
		}
		return rc;
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException{
		boolean rc = super.offer(e, timeout, unit);
		if(rc){
			inSucc++;
		}else{
			inFail++;
		}
		return rc;
	}

	@Override
	public boolean offer(E e){
		boolean rc = super.offer(e);
		if(rc){
			inSucc++;
		}else{
			inFail++;
		}
		return rc;
	}

	@Override
	public E peek(){
		return null;
	}

	@Override
	public E poll(){
		E e = super.poll();
		if(e != null){
			outSucc++;
		}
		return e;
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException{
		E e = super.poll(timeout, unit);
		if(e != null){
			outSucc++;
		}
		return e;
	}

	@Override
	public void put(E e) throws InterruptedException{
		super.put(e);
		inSucc++;
	}

	@Override
	public E take() throws InterruptedException{
		E e = super.take();
		outSucc++;
		return e;
	}

	/**
	 * [inSucc-inFail/outSucc-outFail/size/remainingCapacity]
	 * 
	 * @return
	 */
	public String showInfo(){
		return "Q - " + this.queueName + " - [" + inSucc + "-" + inFail + "/" + outSucc + "-" + outFail + "/" + size() + "/" + this.remainingCapacity() + "]";
	}

	private SSMPQueue(int capacity){
		super(capacity);
	}

	private SSMPQueue(int capacity, boolean fair){
		super(capacity, fair);
	}

	private SSMPQueue(int capacity, boolean fair, Collection<? extends E> c){
		super(capacity, fair, c);
	}

	public void help(PrintWriter pw){
		pw.println(queueName + " usage!");
		pw.flush();
	}

	@Override
	public String showDetail(){
		StringBuffer sb = new StringBuffer();
		sb.append(showInfo()).append("\n").append("œÍœ∏–≈œ¢£∫").append("\n").append("\tappend print detail information!").append("\n");
		return sb.toString();
	}

	@Override
	public void spectacle(String command, PrintWriter pw){
		
	}

	
	public long getInSucc(){
		return inSucc;
	}

	
	public long getInFail(){
		return inFail;
	}

	
	public long getOutSucc(){
		return outSucc;
	}

	
	public long getOutFail(){
		return outFail;
	}
	
	
}
