/**
 * Title: EhandErrorQs.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-18 ����04:18:32
 * @version V1.0
 */
package com.success.protocol.ticket.zzy;

/**
 * com.success.protocol.ticket.zzy
 * EhandErrorQs.java
 * EhandErrorQs
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-18 ����04:18:32
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

	/* (�� Javadoc)
	 *Title: decodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.ticket.zzy.EhandDataPack#decodeMessageBody()
	 */
	@Override
	public void decodeMessageBody() throws Exception {
		// TODO �Զ����ɷ������

	}

	/* (�� Javadoc)
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
