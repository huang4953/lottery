/**
 * Title: TermSaleShowAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-26 下午10:22:06
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.web.admin.action
 * TermSaleShowAction.java
 * TermSaleShowAction
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-26 下午10:22:06
 * 
 */

public class TermSaleShowAction extends ActionSupport {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 3227243504472371672L;
	
	private int ex_code;
	private String ex_reason;
	private String p_lotteryId;
	private String lottery_name;
	private String p_termNo_begin = "0";
	private String p_termNo_end = "0";
	private List<LotteryTermModel> saleInfoList;
	
	public String termSaleInfoShow(){
		
		try{
			int limitNum = 15;
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			if(StringUtils.isNotEmpty(this.convertParam(this.getP_termNo_begin())) || StringUtils.isNotEmpty(this.convertParam(this.p_termNo_end))){
				limitNum = 0;
			}
			
			List<LotteryTermModel> innerSaleInfo = termManager.getLotteryNextTermInfo(this.convertParam(this.getP_lotteryId()), this.convertParam(this.getP_termNo_begin()),
					this.convertParam(this.p_termNo_end), limitNum);
			/*
			 * 扩充LotteryTermModel对象
			 */
			String tmp_p_lotteryId = this.getP_lotteryId()==null?"0":this.getP_lotteryId();
			if(innerSaleInfo != null){
				String reserve = tmp_p_lotteryId+"#"+this.getP_termNo_begin()+"#"+this.getP_termNo_end();
				for(LotteryTermModel oneTerm : innerSaleInfo){
					oneTerm.setReserve1(reserve);
				}
			}
			this.setSaleInfoList(innerSaleInfo);
					
		}catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			return ERROR; 
		}catch(Exception ex){
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
	
	private String convertParam(String param){
		return "0".equals(param) ? null : param;
	}
	
	public int getEx_code() {
		return this.ex_code;
	}
	public void setEx_code(int ex_code) {
		this.ex_code = ex_code;
	}
	public String getEx_reason() {
		return this.ex_reason;
	}
	public void setEx_reason(String ex_reason) {
		this.ex_reason = ex_reason;
	}
	public String getLottery_name() {
		return this.lottery_name;
	}
	public void setLottery_name(String lottery_name) {
		this.lottery_name = lottery_name;
	}
	public String getP_lotteryId() {
		return this.p_lotteryId;
	}
	public void setP_lotteryId(String id) {
		this.p_lotteryId = id;
	}
	public String getP_termNo_begin() {
		return this.p_termNo_begin;
	}
	public void setP_termNo_begin(String no_begin) {
		this.p_termNo_begin = no_begin;
	}
	public String getP_termNo_end() {
		return this.p_termNo_end;
	}
	public void setP_termNo_end(String no_end) {
		this.p_termNo_end = no_end;
	}
	public List<LotteryTermModel> getSaleInfoList() {
		return this.saleInfoList;
	}
	public void setSaleInfoList(List<LotteryTermModel> saleInfoList) {
		this.saleInfoList = saleInfoList;
	}
	
	

}
