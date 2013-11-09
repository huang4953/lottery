/**
 * Title: CashResult.java
 * @Package com.success.lottery.web.admin.webbean
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-9-7 ����01:00:35
 * @version V1.0
 */
package com.success.lottery.web.admin.webbean;

/**
 * com.success.lottery.web.admin.webbean
 * CashResult.java
 * CashResult
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-9-7 ����01:00:35
 * 
 */

public class CashResult {
	
	private int ex_code = 0;
	private String ex_reason;
	
	private String lotteryName;
	private String p_termNo_begin;
	
	private String total_orders = "0";//�ܵĶ�����
	private String total_tz_prize = "0"; //�ܶ�����Ͷע���
	
	private String sucess_orders = "0";//�ɹ��ҽ�������
	private String sucess_tz_prize = "0";//�ɹ��ҽ�Ͷע���
	
	private String fail_orders = "0";//ʧ�ܶҽ�������
	private String fail_tz_prize = "0";//ʧ�ܶҽ�Ͷע���
	
	private String zj_orders = "0";//�н�������
	private String zj_prize = "0";//�н�������
	
	//׷�Ŵ�����
	private String zh_nextTerm;
	private String zh_total_num = "0";//�ܹ����Դ���Ķ�����
	private String zh_sucess_num = "0";//�ɹ�����Ķ�����
	private String zh_fail_num = "0";//����ʧ�ܵĵ�����
	private String zh_sucess_bet_num = "0";//תΪͶע�Ķ�����
	private String zh_sucess_limit_num = "0";//תΪ�޺ŵĶ�����
	
	//���ڸ��½��
	private String old_term_status ;//����ԭʼ״̬
	private String new_term_status;//���ڸ��º�״̬
	
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public String getP_termNo_begin() {
		return p_termNo_begin;
	}
	public void setP_termNo_begin(String no_begin) {
		p_termNo_begin = no_begin;
	}
	
	public String getEx_reason() {
		return ex_reason;
	}
	public void setEx_reason(String ex_reason) {
		this.ex_reason = ex_reason;
	}
	public int getEx_code() {
		return ex_code;
	}
	public void setEx_code(int ex_code) {
		this.ex_code = ex_code;
	}
	public String getFail_orders() {
		return fail_orders;
	}
	public void setFail_orders(String fail_orders) {
		this.fail_orders = fail_orders;
	}
	public String getFail_tz_prize() {
		return fail_tz_prize;
	}
	public void setFail_tz_prize(String fail_tz_prize) {
		this.fail_tz_prize = fail_tz_prize;
	}
	public String getSucess_orders() {
		return sucess_orders;
	}
	public void setSucess_orders(String sucess_orders) {
		this.sucess_orders = sucess_orders;
	}
	public String getSucess_tz_prize() {
		return sucess_tz_prize;
	}
	public void setSucess_tz_prize(String sucess_tz_prize) {
		this.sucess_tz_prize = sucess_tz_prize;
	}
	public String getTotal_orders() {
		return total_orders;
	}
	public void setTotal_orders(String total_orders) {
		this.total_orders = total_orders;
	}
	public String getTotal_tz_prize() {
		return total_tz_prize;
	}
	public void setTotal_tz_prize(String total_tz_prize) {
		this.total_tz_prize = total_tz_prize;
	}
	public String getZj_orders() {
		return zj_orders;
	}
	public void setZj_orders(String zj_orders) {
		this.zj_orders = zj_orders;
	}
	public String getZj_prize() {
		return zj_prize;
	}
	public void setZj_prize(String zj_prize) {
		this.zj_prize = zj_prize;
	}
	public String getNew_term_status() {
		return new_term_status;
	}
	public void setNew_term_status(String new_term_status) {
		this.new_term_status = new_term_status;
	}
	public String getOld_term_status() {
		return old_term_status;
	}
	public void setOld_term_status(String old_term_status) {
		this.old_term_status = old_term_status;
	}
	public String getZh_fail_num() {
		return zh_fail_num;
	}
	public void setZh_fail_num(String zh_fail_num) {
		this.zh_fail_num = zh_fail_num;
	}
	public String getZh_sucess_bet_num() {
		return zh_sucess_bet_num;
	}
	public void setZh_sucess_bet_num(String zh_sucess_bet_num) {
		this.zh_sucess_bet_num = zh_sucess_bet_num;
	}
	public String getZh_sucess_limit_num() {
		return zh_sucess_limit_num;
	}
	public void setZh_sucess_limit_num(String zh_sucess_limit_num) {
		this.zh_sucess_limit_num = zh_sucess_limit_num;
	}
	public String getZh_sucess_num() {
		return zh_sucess_num;
	}
	public void setZh_sucess_num(String zh_sucess_num) {
		this.zh_sucess_num = zh_sucess_num;
	}
	public String getZh_total_num() {
		return zh_total_num;
	}
	public void setZh_total_num(String zh_total_num) {
		this.zh_total_num = zh_total_num;
	}
	public String getZh_nextTerm() {
		return zh_nextTerm;
	}
	public void setZh_nextTerm(String zh_nextTerm) {
		this.zh_nextTerm = zh_nextTerm;
	}
	

}
