/**
 * Title: BusCoopPlanDomain.java
 * @Package com.success.lottery.business.domain
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-4-18 ����01:58:26
 * @version V1.0
 */
package com.success.lottery.business.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * com.success.lottery.business.domain
 * BusCoopPlanDomain.java
 * BusCoopPlanDomain
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-4-18 ����01:58:26
 * 
 */

public class BusCoopPlanDomain implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = 9042890182214115009L;
	
	private String planId;         //varchar(32) not null comment '������ţ�Ψһ�����ظ���FAYYYYMMDDHHMISSnnnn',                                                                               
	private long userId;           //bigint not null comment      '����������',                                                                                                                  
	private String areaCode;       //varchar(10) not null comment '��Ӧ�����˵�AreaCode',                                                                                                        
	private int lotteryId = -1;         //int not null, ����id                                                                                                                                   
	private String startTerm;      //varchar(32) not null comment '��ʼ�ںţ����η���Ͷע��ʼ���ںţ���׷����ΪͶע������',                                                                      
	private int playType = -1;          //int not null comment         '�ο�����״̬��ʽ���Ķ���-�����淨Ͷע������������',                                                                         
	private int betType = -1;           //int not null comment         '�ο�����״̬��ʽ���Ķ���-�����淨Ͷע������������',                                                                         
	private int betCodeMode = 0;   //int not null default 0 comme 'Ͷע������Դ��ʽ��0-ֱ�����ݣ�Ĭ�ϡ�1-�ļ��ϴ����ݲ�����',                                                                    
	private String betCode;        //varchar(512) not null com    'Ͷע���ݣ�ע��ע֮����"^"�ָ������codeMode=1��Ϊ�ļ�·����Ͷע���ݸ�ʽ�ο�����״̬��ʽ���Ķ���-�����淨Ͷע������������',  
	private int chaseCount;        //int not null comment         '׷����������������ǰ��ʼ�ڣ����δ׷����Ϊ1��',                                                                               
	private int chasedCount = 0;   //int comment                  '��������Ʊ�ɹ���������Ѿ�׷��������������ǰ��ʼ�ڣ����δ׷�ţ��򶩵���Ʊ�ɹ���Ϊ1',                                         
	private int winStoped = 1;     //bool default true comment    '�н����Ƿ�ֹͣ׷�ţ�0-��ֹͣ��1-ֹͣ��Ĭ��Ϊ1',                                                                               
	private int planType = 0;      //int not null default 0 c     '0-������1-���򣻺��򷽰���׼׷�ţ���ʱ��������',                                                                              
	private int betMultiple;       //int not null comment         '��������������Ͷע�ı���',                                                                                                    
	private int unitAmount = 0;    //numeric(12,0) not null       '���ڷ����������ܽ��=���ڷ������*׷������',                                                                              
	private Timestamp planTime;     //datetime not null comment    '�����ύʱ��',                                                                                                                
	private Timestamp stopTime;     //datetime comment             '���򷽰��Ϲ���ֹʱ��',                                                                                                        
	private String planTitle;      //varchar(32) comment          '�������⡢������',                                                                                                            
	private String planDescription;//varchar(2048) comment        '������顢������',                                                                                                            
	private int planOpenType = 0;  //int default 0 comment        '����������ʽ 0-���� 1-����󹫿���������',                                                                                    
	private int totalUnit;         //int comment                  '�ܷ�����������',                                                                                                              
	private long unitPrice;         //numeric(12,0) comment        'ÿ�ݵ��ۡ�������',                                                                                                            
	private int unitBuySelf;       //int comment                  '���׷�����������',                                                                                                            
	private int commisionPercent;  //int comment                  'Ӷ������������н����Ӷ������������ã���д10��Ϊ 10%',                                                                      
	private int selledUnit;        //int comment                  '�Ѿ��Ϲ��ķ�����������',                                                                                                      
	private int planStatus = 0;    //int not null default 0 comme '�ο�����״̬��ʽ���Ķ���-������Ʊ����״̬',                                                                                   
	private int planSource = 0;    //int not null default 0 comme 'Ͷע�����Ǵ��Ǹ��������ģ�0-WEB��1-SMS��2-WAP',                                                                               
	private String reserve;        //varchar(32), 
	
	private String loginName;//*�����ڵ�¼�������ظ�������ֱ��ʹ���ֻ�����
	private String mobilePhone;//*�����ڵ�¼��ҵ��Ҫ�����
	private int status;//�û�״̬��1-���� 0-ע�� 2-����
	private String nickName;//��ʾ��ҳ������ƣ���ѡ����δ������ʾ�ֻ����룬��δ���ֻ�������ʾ�˻�Id
	private String realName;//��ʵ��������������д
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getBetCode() {
		return betCode;
	}
	public void setBetCode(String betCode) {
		this.betCode = betCode;
	}
	public int getBetCodeMode() {
		return betCodeMode;
	}
	public void setBetCodeMode(int betCodeMode) {
		this.betCodeMode = betCodeMode;
	}
	public int getBetMultiple() {
		return betMultiple;
	}
	public void setBetMultiple(int betMultiple) {
		this.betMultiple = betMultiple;
	}
	public int getBetType() {
		return betType;
	}
	public void setBetType(int betType) {
		this.betType = betType;
	}
	public int getChaseCount() {
		return chaseCount;
	}
	public void setChaseCount(int chaseCount) {
		this.chaseCount = chaseCount;
	}
	public int getChasedCount() {
		return chasedCount;
	}
	public void setChasedCount(int chasedCount) {
		this.chasedCount = chasedCount;
	}
	public int getCommisionPercent() {
		return commisionPercent;
	}
	public void setCommisionPercent(int commisionPercent) {
		this.commisionPercent = commisionPercent;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPlanDescription() {
		return planDescription;
	}
	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public int getPlanOpenType() {
		return planOpenType;
	}
	public void setPlanOpenType(int planOpenType) {
		this.planOpenType = planOpenType;
	}
	public int getPlanSource() {
		return planSource;
	}
	public void setPlanSource(int planSource) {
		this.planSource = planSource;
	}
	public int getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(int planStatus) {
		this.planStatus = planStatus;
	}
	public Timestamp getPlanTime() {
		return planTime;
	}
	public void setPlanTime(Timestamp planTime) {
		this.planTime = planTime;
	}
	public String getPlanTitle() {
		return planTitle;
	}
	public void setPlanTitle(String planTitle) {
		this.planTitle = planTitle;
	}
	public int getPlanType() {
		return planType;
	}
	public void setPlanType(int planType) {
		this.planType = planType;
	}
	public int getPlayType() {
		return playType;
	}
	public void setPlayType(int playType) {
		this.playType = playType;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public int getSelledUnit() {
		return selledUnit;
	}
	public void setSelledUnit(int selledUnit) {
		this.selledUnit = selledUnit;
	}
	public String getStartTerm() {
		return startTerm;
	}
	public void setStartTerm(String startTerm) {
		this.startTerm = startTerm;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getStopTime() {
		return stopTime;
	}
	public void setStopTime(Timestamp stopTime) {
		this.stopTime = stopTime;
	}
	public int getTotalUnit() {
		return totalUnit;
	}
	public void setTotalUnit(int totalUnit) {
		this.totalUnit = totalUnit;
	}
	public int getUnitAmount() {
		return unitAmount;
	}
	public void setUnitAmount(int unitAmount) {
		this.unitAmount = unitAmount;
	}
	public int getUnitBuySelf() {
		return unitBuySelf;
	}
	public void setUnitBuySelf(int unitBuySelf) {
		this.unitBuySelf = unitBuySelf;
	}
	public long getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(long unitPrice) {
		this.unitPrice = unitPrice;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getWinStoped() {
		return winStoped;
	}
	public void setWinStoped(int winStoped) {
		this.winStoped = winStoped;
	}

}
