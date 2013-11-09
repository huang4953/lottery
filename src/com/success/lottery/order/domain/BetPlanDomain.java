package com.success.lottery.order.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
/**
 * 订单信息实体类
 * 
 * @author bing.li
 * 
 */
public class BetPlanDomain implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private String planId;         //varchar(32) not null comment '方案编号，唯一不能重复，FAYYYYMMDDHHMISSnnnn',                                                                               
	private long userId;           //bigint not null comment      '方案发起人',                                                                                                                  
	private String areaCode;       //varchar(10) not null comment '对应发起人的AreaCode',                                                                                                        
	private int lotteryId = -1;         //int not null, 彩种id                                                                                                                                   
	private String startTerm;      //varchar(32) not null comment '开始期号，本次方案投注开始的期号；非追号则为投注期数；',                                                                      
	private int playType = -1;          //int not null comment         '参看数据状态格式核心定义-彩种玩法投注开奖结果规则表',                                                                         
	private int betType = -1;           //int not null comment         '参看数据状态格式核心定义-彩种玩法投注开奖结果规则表',                                                                         
	private int betCodeMode = 0;   //int not null default 0 comme '投注内容来源格式，0-直接内容，默认、1-文件上传，暂不启用',                                                                    
	private String betCode;        //varchar(512) not null com    '投注内容，注与注之间用"^"分隔；如果codeMode=1则为文件路径；投注内容格式参看数据状态格式核心定义-彩种玩法投注开奖结果规则表',  
	private int chaseCount;        //int not null comment         '追号总期数，包括当前开始期，如果未追号则为1。',                                                                               
	private int chasedCount = 0;   //int comment                  '当订单出票成功后则添加已经追号期数，包括当前开始期，如果未追号，则订单出票成功后为1',                                         
	private int winStoped = 1;     //bool default true comment    '中奖后是否停止追号，0-不停止、1-停止，默认为1',                                                                               
	private int planType = 0;      //int not null default 0 c     '0-代购，1-合买；合买方案不准追号；暂时不做合买',                                                                              
	private int betMultiple;       //int not null comment         '方案倍数，本次投注的倍数',                                                                                                    
	private int unitAmount = 0;    //numeric(12,0) not null       '单期方案金额，方案总金额=单期方案金额*追号期数',                                                                              
	private Timestamp planTime;     //datetime not null comment    '方案提交时间',                                                                                                                
	private Timestamp stopTime;     //datetime comment             '合买方案认购截止时间',                                                                                                        
	private String planTitle;      //varchar(32) comment          '方案标题、合买用',                                                                                                            
	private String planDescription;//varchar(2048) comment        '方案简介、合买用',                                                                                                            
	private int planOpenType = 0;  //int default 0 comment        '方案公开方式 0-公开 1-购买后公开；合买用',                                                                                    
	private int totalUnit;         //int comment                  '总份数、合买用',                                                                                                              
	private long unitPrice;         //numeric(12,0) comment        '每份单价、合买用',                                                                                                            
	private int unitBuySelf;       //int comment                  '保底份数、合买用',                                                                                                            
	private int commisionPercent;  //int comment                  '佣金比例，方案中奖后的佣金比例、合买用，填写10则为 10%',                                                                      
	private int selledUnit;        //int comment                  '已经认购的份数，合买用',                                                                                                      
	private int planStatus = 0;    //int not null default 0 comme '参看数据状态格式核心定义-方案彩票各种状态',                                                                                   
	private int planSource = 0;    //int not null default 0 comme '投注方案是从那个渠道来的，0-WEB，1-SMS，2-WAP',                                                                               
	private String reserve;        //varchar(32), 
	
	private boolean isPutQuery = false;
	
	private Map<String,Integer> terms;//如有追号则为追号期数列表，如只对当前期投注则为null
	
	
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
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
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
	
	public int getUnitBuySelf() {
		return unitBuySelf;
	}
	public void setUnitBuySelf(int unitBuySelf) {
		this.unitBuySelf = unitBuySelf;
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
	
	public boolean isPutQuery() {
		return isPutQuery;
	}
	public void setPutQuery(boolean isPutQuery) {
		this.isPutQuery = isPutQuery;
	}
	
	public long getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(long unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Map<String, Integer> getTerms() {
		return terms;
	}
	public void setTerms(Map<String, Integer> terms) {
		this.terms = terms;
	}
	public int getUnitAmount() {
		return unitAmount;
	}
	public void setUnitAmount(int unitAmount) {
		this.unitAmount = unitAmount;
	}
	
	
	
}
