/**
 * Title: EhandErrorQs.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-18 下午04:18:32
 * @version V1.0
 */
package com.success.protocol.ticket.zzy;

/**
 * com.success.protocol.ticket.zzy
 * EhandErrorQs.java
 * EhandErrorQs
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-18 下午04:18:32
 * 
 */

public class EhandErrorQs extends EhandDataPack {
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userId
	 * @param errorCode
	 */
	public EhandErrorQs(String command,String userId,String errorCode){
		this.command = command;
		this.userid = userId;
		this.errorcode = errorCode;
	}

	/* (非 Javadoc)
	 *Title: decodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.ticket.zzy.EhandDataPack#decodeMessageBody()
	 */
	@Override
	public void decodeMessageBody() throws Exception {
		// TODO 自动生成方法存根

	}

	/* (非 Javadoc)
	 *Title: encodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.ticket.zzy.EhandDataPack#encodeMessageBody()
	 */
	@Override
	public void encodeMessageBody() throws Exception {
		this.setMessageBody("");
	}

}
