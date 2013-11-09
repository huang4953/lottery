/**
 * Title: SalesResult.java
 * @Package com.success.lottery.util
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-3 下午06:49:39
 * @version V1.0
 */
package com.success.lottery.util;

/**
 * com.success.lottery.util
 * SalesResult.java
 * SalesResult
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-3 下午06:49:39
 * 
 */

public class SalesResult implements Comparable{
	
	private int slaesId;//
	private String homeTeam;//主队
	private String awayTeam;//客队
	private String leagueMatch;//赛制
	private String gameDate;//比赛时间
	private String reserve;//保留
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	public String getGameDate() {
		return gameDate;
	}
	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getLeagueMatch() {
		return leagueMatch;
	}
	public void setLeagueMatch(String leagueMatch) {
		this.leagueMatch = leagueMatch;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public int getSlaesId() {
		return slaesId;
	}
	public void setSlaesId(int slaesId) {
		this.slaesId = slaesId;
	}
	public int compareTo(Object arg0) {
		return this.slaesId - ((SalesResult)arg0).getSlaesId();
	}

}
