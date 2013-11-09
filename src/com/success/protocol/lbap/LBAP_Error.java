/**
 * Title: LBAP_Error.java
 * @Package com.success.protocol.lbap
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-1 上午11:14:36
 * @version V1.0
 */
package com.success.protocol.lbap;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * com.success.protocol.lbap
 * LBAP_Error.java
 * LBAP_Error
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-1 上午11:14:36
 * 
 */

public class LBAP_Error extends LBAP_DataPack {
	

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_Error(String result,String command,String clientId,String messageId) {
		this.setResult(result);
		this.setCommand(command);
		this.setClientId(clientId);
		this.setMessageId(messageId);
	}
	
	public LBAP_Error(String result,String command,String clientId,String messageId,String messageBody) {
		this.setResult(result);
		this.setCommand(command);
		this.setClientId(clientId);
		this.setMessageId(messageId);
		this.setMessageBody(messageBody);
	}

	/* (非 Javadoc)
	 *Title: decodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.lbap.LBAP_DataPack#decodeMessageBody()
	 */
	@Override
	public void decodeMessageBody() throws Exception {
		// TODO 自动生成方法存根

	}

	/* (非 Javadoc)
	 *Title: encodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.lbap.LBAP_DataPack#encodeMessageBody()
	 */
	@Override
	public void encodeMessageBody(){
		this.setEncryptionType(0);
		String message = "";
		if(StringUtils.isNotEmpty(this.getMessageBody())){
			message = this.getMessageBody();
		}else{
			try {
				message = LbapMess.getMess(this.getResult());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.setMessageBody("<errorMsg>"+message+"</errorMsg>");
		
	}

}
