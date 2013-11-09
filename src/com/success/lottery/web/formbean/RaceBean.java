package com.success.lottery.web.formbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.util.LotteryTools;

public class RaceBean {
	private Long id;
	private String boutIndex;//����
	private String term;
	private String matchName;//����
	private Date matchTime;//����ʱ��
	private String homeTeam;//����
	private Long concede;//����
	private String awaryTeam;//�ɶ�
	private String wholeScore;//ȫ���ȷ�
	private String matchDate;//��������
	private String extendInfo;
	
	public static List<RaceBean> getRaceBeanList(LotteryTermModel termInfo){
		Map<Integer, Map<String, String>> map = LotteryTools.splitSalesInfo(termInfo.getSalesInfo());
		List<RaceBean> arrayList = new ArrayList<RaceBean>(map.size());
		for(Map.Entry<Integer, Map<String, String>> mapEntry : map.entrySet()){
			RaceBean bean = new RaceBean();
			bean.setBoutIndex(""+mapEntry.getKey());
			bean.setHomeTeam(mapEntry.getValue().get("A"));
			bean.setAwaryTeam(mapEntry.getValue().get("B"));
			bean.setMatchDate(mapEntry.getValue().get("D"));
			bean.setMatchTime(new Date());
			bean.setMatchName(mapEntry.getValue().get("C"));
			arrayList.add(bean);
		}
		return arrayList;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBoutIndex() {
		return boutIndex;
	}
	public void setBoutIndex(String boutIndex) {
		this.boutIndex = boutIndex;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getMatchName() {
		return matchName;
	}
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	public Date getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public Long getConcede() {
		return concede;
	}
	public void setConcede(Long concede) {
		this.concede = concede;
	}
	public String getAwaryTeam() {
		return awaryTeam;
	}
	public void setAwaryTeam(String awaryTeam) {
		this.awaryTeam = awaryTeam;
	}
	public String getWholeScore() {
		return wholeScore;
	}
	public void setWholeScore(String wholeScore) {
		this.wholeScore = wholeScore;
	}
	public String getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
	public String getExtendInfo() {
		return extendInfo;
	}
	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}
}
