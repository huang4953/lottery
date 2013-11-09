/**
 * Title: EhandQueryService.java
 * @Package com.success.lottery.ehand.ehandserver.bussiness
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-24 下午06:18:25
 * @version V1.0
 */
package com.success.lottery.ehand.ehandserver.bussiness;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.ehand.ehandserver.bussiness.model.EhandTermBussiModel;
import com.success.lottery.ehand.ehandserver.bussiness.model.QueryTermModel;
import com.success.lottery.exception.LotteryException;
import com.success.protocol.ticket.zzy.CashQueryQs50206;
import com.success.protocol.ticket.zzy.EhandDataPack;
import com.success.protocol.ticket.zzy.KjggQueryQs50203;
import com.success.protocol.ticket.zzy.TermQueryQs50200;
import com.success.protocol.ticket.zzy.TermQueryQs50209;
import com.success.protocol.ticket.zzy.TermSaleQs50208;
import com.success.protocol.ticket.zzy.model.CashResult;
import com.success.protocol.ticket.zzy.model.QueryTerm;
import com.success.protocol.ticket.zzy.model.QueryTermResult;
import com.success.protocol.ticket.zzy.model.TermSaleResult;
import com.success.protocol.ticket.zzy.util.EhandUtil;

/**
 * com.success.lottery.ehand.ehandserver.bussiness
 * EhandQueryService.java
 * EhandQueryService
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-24 下午06:18:25
 * 
 */

public class EhandQueryService {
	private static Log logger = LogFactory.getLog(EhandQueryService.class);
	
	/**
	 * 
	 * Title: queryEterm50200<br>
	 * Description: <br>
	 *              <br>根据彩种id查询彩期信息
	 * @param betLotteryId 投注系统的彩种
	 * 
	 * @return
	 * @exception LotteryException
	 *                            <br>EhandUtil.E_01_CODE,发送请求通讯错误
	 *                            <br>EhandUtil.E_02_CODE,发送请求通讯返回但HTTP响应状态不正确
	 *                            <br>EhandUtil.E_03_CODE,发送请求通讯正确返回但消息解析错误
	 *                            <br>EhandUtil.E_04_CODE,请求参数不正确
	 *                            <br>EhandUtil.E_07_CODE,发送请求返回错误码[CODE]
	 *                            <br>EhandUtil.E_08_CODE,程序处理过程发生其他异常
	 *                            <br>EhandUtil.E_09_CODE,查询请求返回但没有返回消息体
	 * 
	 */
	public static EhandTermBussiModel queryEterm50200(int betLotteryId) throws LotteryException{
		boolean isNeedReSend = false;//是否需要重新发送,true表示需要重新发送
		QueryTermResult queryResult = null;//请求返回的响应数据
		EhandTermBussiModel eTerms = null;
		String reponseErrCode = "";
		String reponseErrMsg = "";
		int excep_type = -1;
		String excep_message = "";
		try{
			String eLotteryId =  EhandUtil.lotteryToZzy.get(betLotteryId);//转换成掌中奕的彩种
			
			if(StringUtils.isEmpty(eLotteryId)){
				logger.info("掌中奕奖期查询请求参数为空[50200]!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//发送请求并得到响应结果
			for (int i = 0; i < EhandUtil.reSendNum; i++) {
				reponseErrCode = "";
				excep_type = -1;
				excep_message = "";
				//发送请求并得到请求的返回数据
				List<String> requestModelInfo = new ArrayList<String>();
				requestModelInfo.add(eLotteryId);
				
				//发送请求并得到回复信息
				EhandDataPack responseTermInfo = null;
				
				try {
					responseTermInfo = new TermQueryQs50200(requestModelInfo).writePack();
				} catch (LotteryException e) {
					logger.info("掌中奕奖期查询通讯异常:"+e.getType()+e.getMessage());
					excep_type = e.getType();
					excep_message = e.getMessage();
					continue;
				}
				
				if(responseTermInfo != null){
					List<QueryTermResult> innerReponseResult = ((TermQueryQs50200)responseTermInfo).getResponseResult();
					reponseErrCode = responseTermInfo.getErrorcode();
					reponseErrMsg = responseTermInfo.getErrormsg();
					if (!"0".equals(reponseErrCode)) {// 消息的返回码不正确，0代表正确，其他的代表不正确
						logger.error("掌中奕奖期查询返回错误代码:" + reponseErrCode);
						isNeedReSend = true;
					}else{
						isNeedReSend = false;
						queryResult = (innerReponseResult == null || innerReponseResult.isEmpty()) ? null : innerReponseResult.get(0);
					}
				}else{//没有得到响应数据
					isNeedReSend = true;
				}
				
				//如果得到的响应没有通过校验，则重新发送请求
				if (isNeedReSend) {// 需要重新发送
					logger.info("掌中奕奖期查询[50200]重发请求，第" + (i+1) + "次");
					Thread.sleep(EhandUtil.reSendSleep);// 间隔5秒重新发送
				} else {
					break;
				}
				
			}
			
			if(excep_type != -1){//通讯过程发生异常
				throw new LotteryException(excep_type,excep_message);
			}
			
			if(StringUtils.isNotEmpty(reponseErrCode) && !"0".equals(reponseErrCode)){//错误的返回码
				throw new LotteryException(EhandUtil.E_07_CODE,EhandUtil.E_07_DESC.replaceAll("CODE", reponseErrCode==null?"":reponseErrCode).replaceAll("MSG", reponseErrMsg==null?"":reponseErrMsg));
			}
			
			//正确的得到了响应数据，将响应结果转换为彩期对象
			if(queryResult != null){//得到了响应数据
				eTerms = new EhandTermBussiModel();
				eTerms.setLotteryId(EhandUtil.zzyToLottery(queryResult.getLotteryId()));
				eTerms.setEhandLotteryId(queryResult.getLotteryId());
				eTerms.setIssue(queryResult.getTermNo());
				eTerms.setStartTime(queryResult.getStartTimeStamp());
				eTerms.setEndTime(queryResult.getEndTimeStamp());
				eTerms.setStatus(Integer.parseInt(queryResult.getStatus()));
			}else{
				if("0".equals(reponseErrCode)){
					throw new LotteryException(EhandUtil.E_09_CODE,EhandUtil.E_09_DESC);
				}else{
					throw new LotteryException(excep_type,excep_message);
				}
			}
			
		}catch(LotteryException ex){
			logger.error("50200,查询奖期发生异常:"+ex.getType()+":"+ex.getMessage());
			throw ex;
		}catch(Exception e){
			logger.error("50200,查询奖期出错:", e);
			throw new LotteryException(EhandUtil.E_08_CODE,EhandUtil.E_08_DESC);
		}
		return eTerms;
	}
	
	/**
	 * 
	 * Title: QueryEkjgg50203<br>
	 * Description: <br>
	 *              <br>查询开奖公告
	 * @param betLotteryId 投注系统彩种
	 * @param termNo 彩期
	 * @return
	 * @exception LotteryException
	 *                            <br>EhandUtil.E_01_CODE,发送请求通讯错误
	 *                            <br>EhandUtil.E_02_CODE,发送请求通讯返回但HTTP响应状态不正确
	 *                            <br>EhandUtil.E_03_CODE,发送请求通讯正确返回但消息解析错误
	 *                            <br>EhandUtil.E_04_CODE,请求参数不正确
	 *                            <br>EhandUtil.E_07_CODE,发送请求返回错误码[CODE]
	 *                            <br>EhandUtil.E_08_CODE,程序处理过程发生其他异常
	 *                            <br>EhandUtil.E_09_CODE,查询请求返回但没有返回消息体
	 */
	public static EhandTermBussiModel QueryEkjgg50203(int betLotteryId,String termNo) throws LotteryException{
		EhandTermBussiModel eTerms = null;
		boolean isNeedReSend = false;//是否需要重新发送,true表示需要重新发送
		QueryTermResult queryResult = null;//请求返回的响应数据
		String reponseErrCode = "";
		String reponseErrMsg = "";
		int excep_type = -1;
		String excep_message = "";
		try{
			String eLotteryId =  EhandUtil.lotteryToZzy.get(betLotteryId);//转换成掌中奕的彩种
			if(StringUtils.isEmpty(eLotteryId) || StringUtils.isEmpty(termNo)){
				logger.info("掌中奕开奖公告情况查询[50203]请求参数为空!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//发送请求并得到响应结果
			for (int i = 0; i < EhandUtil.reSendNum; i++) {
				reponseErrCode = "";
				excep_type = -1;
				excep_message = "";
				
				// 发送请求并得到请求的返回数据
				List<QueryTerm> requestModelInfo = new ArrayList<QueryTerm>();
				requestModelInfo.add(new QueryTerm(eLotteryId,termNo));
				//发送请求并得到回复信息
				EhandDataPack responseTermInfo = null;
				
				try {
					responseTermInfo = new KjggQueryQs50203(requestModelInfo).writePack();
				} catch (LotteryException e) {
					logger.info("掌中奕开奖公告查询通讯异常:"+e.getType()+e.getMessage());
					excep_type = e.getType();
					excep_message = e.getMessage();
					continue;
				}
				
				//校验返回的响应是否正确
				if(responseTermInfo != null){//得到了响应数据
					List<QueryTermResult> innerReponseResult = ((KjggQueryQs50203)responseTermInfo).getResponseResult();
					reponseErrCode = responseTermInfo.getErrorcode();
					reponseErrMsg = responseTermInfo.getErrormsg();
					if (!"0".equals(reponseErrCode)) {// 消息的返回码不正确，0代表正确，其他的代表不正确
						logger.error("掌中奕开奖公告情况查询返回错误代码:" + reponseErrCode);
						isNeedReSend = true;
					}else{
						isNeedReSend = false;
						queryResult = (innerReponseResult == null || innerReponseResult.isEmpty()) ? null : innerReponseResult.get(0);
					}
				}else{//没有得到响应数据
					isNeedReSend = true;
				}

				// 如果得到的响应没有通过校验，则重新发送请求
				if (isNeedReSend) {// 需要重新发送
					logger.info("掌中奕开奖公告情况查询重发请求，第" + (i+1) + "次");
					isNeedReSend = false;
					Thread.sleep(EhandUtil.reSendSleep);// 间隔5秒重新发送
				} else {
					break;
				}
			}
			
			if(excep_type != -1){//通讯过程发生异常
				throw new LotteryException(excep_type,excep_message);
			}
			
			if(StringUtils.isNotEmpty(reponseErrCode) && !"0".equals(reponseErrCode)){//错误的返回码
				throw new LotteryException(EhandUtil.E_07_CODE,EhandUtil.E_07_DESC.replaceAll("CODE", reponseErrCode==null?"":reponseErrCode).replaceAll("MSG", reponseErrMsg==null?"":reponseErrMsg));
			}
			
			//正确的得到了响应数据，将响应结果转换为彩期对象
			if(queryResult != null){//得到了响应数据
				eTerms = new EhandTermBussiModel();
				eTerms.setLotteryId(EhandUtil.zzyToLottery(queryResult.getLotteryId()));
				eTerms.setEhandLotteryId(queryResult.getLotteryId());
				eTerms.setIssue(queryResult.getTermNo());
				eTerms.setBonuscode(queryResult.getLotteryResult());
			}else{
				if("0".equals(reponseErrCode)){
					throw new LotteryException(EhandUtil.E_09_CODE,EhandUtil.E_09_DESC);
				}else{
					throw new LotteryException(excep_type,excep_message);
				}
			}
			
		}catch(LotteryException ex){
			logger.error("50203,开奖公告查询发生异常:"+ex.getType()+":"+ex.getMessage());
			throw ex;
		}catch(Exception e){
			logger.error("50203,开奖公告查询发生异常:", e);
			throw new LotteryException(EhandUtil.E_08_CODE,EhandUtil.E_08_DESC);
		}
		return eTerms;
	}
	
	/**
	 * 
	 * Title: QueryCashResult50206<br>
	 * Description: <br>
	 *              <br>中奖数据查询接口,该方法还没有决定如何使用
	 * @param betLotteryId
	 * @param termNo
	 * @return
	 * @exception LotteryException
	 *                            <br>EhandUtil.E_01_CODE,发送请求通讯错误
	 *                            <br>EhandUtil.E_02_CODE,发送请求通讯返回但HTTP响应状态不正确
	 *                            <br>EhandUtil.E_03_CODE,发送请求通讯正确返回但消息解析错误
	 *                            <br>EhandUtil.E_04_CODE,请求参数不正确
	 *                            <br>EhandUtil.E_07_CODE,发送请求返回错误码[CODE]
	 *                            <br>EhandUtil.E_08_CODE,程序处理过程发生其他异常
	 *                            
	 */
	public static List<CashResult> QueryCashResult50206(int betLotteryId,String termNo) throws LotteryException{
		boolean isNeedReSend = false;//是否需要重新发送,true表示需要重新发送
		List<CashResult> queryResult = null;//请求返回的响应数据
		String reponseErrCode = "";
		String reponseErrMsg = "";
		int excep_type = -1;
		String excep_message = "";
		try{
			String eLotteryId =  EhandUtil.lotteryToZzy.get(betLotteryId);//转换成掌中奕的彩种
			
			if(StringUtils.isEmpty(eLotteryId) || StringUtils.isEmpty(termNo)){
				logger.info("掌中奕中奖数据查询请求[50203]请求参数为空!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//发送请求并得到响应结果
			for (int i = 0; i < EhandUtil.reSendNum; i++) {
				reponseErrCode = "";
				excep_type = -1;
				excep_message = "";
				// 发送请求并得到请求的返回数据
				List<QueryTerm> requestModelInfo = new ArrayList<QueryTerm>();
				requestModelInfo.add(new QueryTerm(eLotteryId,termNo));
				EhandDataPack responseTermInfo = null;
				
				try {
					responseTermInfo = new CashQueryQs50206(requestModelInfo).writePack();
				} catch (LotteryException e) {
					logger.info("掌中奕中奖数据查询通讯异常:"+e.getType()+e.getMessage());
					excep_type = e.getType();
					excep_message = e.getMessage();
					continue;
				}
				
				//校验返回的响应是否正确
				if(responseTermInfo != null){//得到了响应数据
					reponseErrCode = responseTermInfo.getErrorcode();
					reponseErrMsg = responseTermInfo.getErrormsg();
					if (!"0".equals(reponseErrCode)) {// 消息的返回码不正确，0代表正确，其他的代表不正确
						logger.error("掌中奕中奖数据查询返回错误代码:" + reponseErrCode);
						isNeedReSend = true;
					}else{
						isNeedReSend = false;
						queryResult = ((CashQueryQs50206)responseTermInfo).getCashResultList();
					}
				}else{//没有得到响应数据
					isNeedReSend = true;
				}

				// 如果得到的响应没有通过校验，则重新发送请求
				if (isNeedReSend) {// 需要重新发送
					logger.info("掌中奕中奖数据查询查询重发请求，第" + (i+1) + "次");
					Thread.sleep(EhandUtil.reSendSleep);// 间隔5秒重新发送
				} else {
					break;
				}
			}
			
			if(excep_type != -1){//通讯过程有异常发生
				throw new LotteryException(excep_type,excep_message);
			}
			
			if(StringUtils.isNotEmpty(reponseErrCode) && !"0".equals(reponseErrCode)){//错误的返回码
				throw new LotteryException(EhandUtil.E_07_CODE,EhandUtil.E_07_DESC.replaceAll("CODE", reponseErrCode==null?"":reponseErrCode).replaceAll("MSG", reponseErrMsg==null?"":reponseErrMsg));
			}
			
		}catch(LotteryException ex){
			logger.error("50206,掌中奕中奖数据查询发生异常:"+ex.getType());
			throw ex;
		}catch(Exception e){
			logger.error("50206,掌中奕中奖数据查询发生异常:", e);
			throw new LotteryException(EhandUtil.E_08_CODE,EhandUtil.E_08_DESC);
		}
		return queryResult;
	}
	
	/**
	 * 
	 * Title: QueryEtermSale50208<br>
	 * Description: <br>
	 *             <br>查询彩期的销售情况
	 * @param betLotteryId
	 * @param termNo
	 * @return
	 * @exception LotteryException
	 *                            <br>EhandUtil.E_01_CODE,发送请求通讯错误
	 *                            <br>EhandUtil.E_02_CODE,发送请求通讯返回但HTTP响应状态不正确
	 *                            <br>EhandUtil.E_03_CODE,发送请求通讯正确返回但消息解析错误
	 *                            <br>EhandUtil.E_04_CODE,请求参数不正确
	 *                            <br>EhandUtil.E_07_CODE,发送请求返回错误码[CODE]
	 *                            <br>EhandUtil.E_08_CODE,程序处理过程发生其他异常
	 *                            <br>EhandUtil.E_09_CODE,查询请求返回但没有返回消息体
	 */
	public static EhandTermBussiModel QueryEtermSale50208(int betLotteryId,String termNo) throws LotteryException{
		boolean isNeedReSend = false;//是否需要重新发送,true表示需要重新发送
		EhandTermBussiModel eTerms = null;
		TermSaleResult queryResult = null;
		String reponseErrCode = "";
		String reponseErrMsg = "";
		int excep_type = -1;
		String excep_message = "";
		try{
			String eLotteryId =  EhandUtil.lotteryToZzy.get(betLotteryId);//转换成掌中奕的彩种
			if(StringUtils.isEmpty(eLotteryId) || StringUtils.isEmpty(termNo)){
				logger.info("掌中奕彩期的销售情况查询请求[50208]请求参数为空!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//发送请求并得到响应结果
			for (int i = 0; i < EhandUtil.reSendNum; i++) {
				reponseErrCode = "";
				excep_type = -1;
				excep_message = "";
				
				// 发送请求并得到请求的返回数据
				EhandDataPack responseTermInfo = null;
				List<QueryTerm> requestModelInfo = new ArrayList<QueryTerm>();
				requestModelInfo.add(new QueryTerm(eLotteryId,termNo));
				
				try {
					responseTermInfo = new TermSaleQs50208(requestModelInfo).writePack();
				} catch (LotteryException e) {
					logger.info("掌中奕彩期的销售情况查询通讯异常:"+e.getType()+e.getMessage());
					excep_type = e.getType();
					excep_message = e.getMessage();
					continue;
				}

				// 校验请求的结果，并决定是否重新发送请求
				if (responseTermInfo != null) {// 得到了响应数据
					List<TermSaleResult> innerReponseResult = ((TermSaleQs50208)responseTermInfo).getTermSaleResult();
					reponseErrCode = responseTermInfo.getErrorcode();
					reponseErrMsg = responseTermInfo.getErrormsg();
					if (!"0".equals(reponseErrCode)) {// 消息的返回码不正确，0代表正确，其他的代表不正确
						logger.error("掌中奕彩期的销售情况查询返回错误代码:" + reponseErrCode);
						isNeedReSend = true;
					}else{
						isNeedReSend = false;
						queryResult = (innerReponseResult == null || innerReponseResult.isEmpty()) ? null : innerReponseResult.get(0);
					}
				} else {// 没有得到响应数据
					isNeedReSend = true;
				}

				// 如果得到的响应没有通过校验，则重新发送请求
				if (isNeedReSend) {// 需要重新发送
					logger.info("掌中奕彩期的奖期销售情况查询重发请求，第" + (i+1) + "次");
					isNeedReSend = false;
					Thread.sleep(EhandUtil.reSendSleep);// 间隔5秒重新发送
				} else {
					break;
				}
			}
			
			if(excep_type != -1){//通讯过程发生异常
				throw new LotteryException(excep_type,excep_message);
			}
			
			if(StringUtils.isNotEmpty(reponseErrCode) && !"0".equals(reponseErrCode)){//错误的返回码
				throw new LotteryException(EhandUtil.E_07_CODE,EhandUtil.E_07_DESC.replaceAll("CODE", reponseErrCode==null?"":reponseErrCode).replaceAll("MSG", reponseErrMsg==null?"":reponseErrMsg));
			}
			
			//正确的得到了响应数据，将响应结果转换为彩期对象
			if(queryResult != null){//得到了响应数据
				eTerms = new EhandTermBussiModel();
				eTerms.setLotteryId(EhandUtil.zzyToLottery(queryResult.getLotteryId()));//转为投注系统的彩种id
				eTerms.setEhandLotteryId(queryResult.getLotteryId());
				eTerms.setIssue(queryResult.getTermNo());
				eTerms.setSalemoney(queryResult.getSaleMoney());
				eTerms.setBonusmoney(queryResult.getBonusMoney());
			}else{
				if("0".equals(reponseErrCode)){
					throw new LotteryException(EhandUtil.E_09_CODE,EhandUtil.E_09_DESC);
				}else{
					throw new LotteryException(excep_type,excep_message);
				}
			}
		}catch(LotteryException ex){
			logger.error("50208,掌中奕彩期的奖期销售情况查询发生异常:"+ex.getType()+":"+ex.getMessage());
			throw ex;
		}catch(Exception e){
			logger.error("50208,掌中奕彩期的奖期销售情况查询发生异常:", e);
			throw new LotteryException(EhandUtil.E_08_CODE,EhandUtil.E_08_DESC);
		}
		
		return eTerms;
	}
	
	/**
	 * 
	 * Title: QueryEterm50209<br>
	 * Description: <br>
	 *              <br>按照掌中奕的彩种和彩期获取掌中奕彩期的状态
	 * @param betLotteryId 投注系统的彩种
	 * @param termNo 彩期
	 * @return
	 * @exception LotteryException
	 *                            <br>EhandUtil.E_01_CODE,发送请求通讯错误
	 *                            <br>EhandUtil.E_02_CODE,发送请求通讯返回但HTTP响应状态不正确
	 *                            <br>EhandUtil.E_03_CODE,发送请求通讯正确返回但消息解析错误
	 *                            <br>EhandUtil.E_04_CODE,请求参数不正确
	 *                            <br>EhandUtil.E_07_CODE,发送请求返回错误码[CODE]
	 *                            <br>EhandUtil.E_08_CODE,程序处理过程发生其他异常
	 *                            <br>EhandUtil.E_09_CODE,查询请求返回但没有返回消息体
	 *                            
	 */
	public static EhandTermBussiModel QueryEterm50209(int betLotteryId,String termNo) throws LotteryException{
		boolean isNeedReSend = false;//是否需要重新发送,true表示需要重新发送
		QueryTermResult queryResult = null;//请求返回的响应数据
		EhandTermBussiModel eTerms = null;
		String reponseErrCode = "";
		String reponseErrMsg = "";
		int excep_type = -1;
		String excep_message = "";
		try{
			String eLotteryId =  EhandUtil.lotteryToZzy.get(betLotteryId);//转换成掌中奕的彩种
			
			if(StringUtils.isEmpty(eLotteryId) || StringUtils.isEmpty(termNo)){
				logger.info("掌中奕奖期状态查询请求参数为空[50209]!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//发送请求并得到响应结果
			for (int i = 0; i < EhandUtil.reSendNum; i++) {
				reponseErrCode = "";
				excep_type = -1;
				excep_message = "";
				
				//发送请求并得到请求的返回数据
				List<QueryTerm> requestModelInfo = new ArrayList<QueryTerm>();
				requestModelInfo.add(new QueryTerm(eLotteryId,termNo));
				
				//发送请求并得到回复信息
				EhandDataPack responseTermInfo = null;
				
				try {
					responseTermInfo = new TermQueryQs50209(requestModelInfo).writePack();
				} catch (LotteryException e) {
					logger.info("掌中奕彩期的奖期状态情况查询通讯异常:"+e.getType()+e.getMessage());
					excep_type = e.getType();
					excep_message = e.getMessage();
					continue;
				}
				
				if(responseTermInfo != null){
					List<QueryTermResult> innerReponseResult = ((TermQueryQs50209)responseTermInfo).getResponseResult();
					reponseErrCode = responseTermInfo.getErrorcode();
					reponseErrMsg = responseTermInfo.getErrormsg();
					if (!"0".equals(reponseErrCode)) {// 消息的返回码不正确，0代表正确，其他的代表不正确
						logger.error("掌中奕彩期的奖期情况查询返回错误代码:" + reponseErrCode);
						isNeedReSend = true;
					}else{
						isNeedReSend = false;
						queryResult = (innerReponseResult == null || innerReponseResult.isEmpty()) ? null : innerReponseResult.get(0);
					}
					
				}else{//没有得到响应数据
					isNeedReSend = true;
				}
				
				//如果得到的响应没有通过校验，则重新发送请求
				if (isNeedReSend) {// 需要重新发送
					logger.info("掌中奕奖期状态查询重发请求，第" + (i+1) + "次");
					Thread.sleep(EhandUtil.reSendSleep);// 间隔5秒重新发送
				} else {
					break;
				}
				
			}
			
			if(excep_type != -1){//通讯过程发生异常
				throw new LotteryException(excep_type,excep_message);
			}
			
			if(StringUtils.isNotEmpty(reponseErrCode) && !"0".equals(reponseErrCode)){//错误的返回码
				throw new LotteryException(EhandUtil.E_07_CODE,EhandUtil.E_07_DESC.replaceAll("CODE", reponseErrCode==null?"":reponseErrCode).replaceAll("MSG", reponseErrMsg==null?"":reponseErrMsg));
			}
			
			//正确的得到了响应数据，将响应结果转换为彩期对象
			if(queryResult != null){//得到了响应数据
				eTerms = new EhandTermBussiModel();
				eTerms.setLotteryId(EhandUtil.zzyToLottery(queryResult.getLotteryId()));
				eTerms.setEhandLotteryId(queryResult.getLotteryId());
				eTerms.setIssue(queryResult.getTermNo());
				eTerms.setStatus(Integer.parseInt(queryResult.getStatus()));
			}else{
				if("0".equals(reponseErrCode)){
					throw new LotteryException(EhandUtil.E_09_CODE,EhandUtil.E_09_DESC);
				}else{
					throw new LotteryException(excep_type,excep_message);
				}
			}
			
		}catch(LotteryException ex){
			logger.error("50209,掌中奕彩期的奖期状态情况查询发生异常:"+ex.getType()+":"+ex.getMessage());
			throw ex;
		}catch(Exception e){
			logger.error("50209,掌中奕彩期的奖期状态情况查询发生异常:", e);
			throw new LotteryException(EhandUtil.E_08_CODE,EhandUtil.E_08_DESC);
		}
		return eTerms;
	}
}
