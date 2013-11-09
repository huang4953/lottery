
package com.success.lottery.ticket.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.ticket.dao.impl.BetTicketDaoImpl;
import com.success.lottery.ticket.domain.BetTicketAcountDomain;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.domain.WinOrderTicketDomain;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.lottery.util.LotterySequence;
import com.success.utils.ApplicationContextUtils;

public class BetTicketService implements BetTicketServiceInterf {
	private static Log logger = LogFactory.getLog(BetTicketService.class);
	
	private BetTicketDaoImpl betTicketDao;
	
	/*
	 * (非 Javadoc)
	*Title: insertBetTicket
	*Description: 
	* @param betTicket
	* @return
	* @throws LotteryException
	* @see com.success.lottery.ticket.service.interf.BetTicketServiceInterf#insertBetTicket(com.success.lottery.ticket.domain.BetTicketDomain)
	 */
	public String insertBetTicket(BetTicketDomain betTicket) throws LotteryException {
		try {
			betTicket.setTicketSequence(LotterySequence.getInstatce("CP").getSequence());
			logger.debug("setTicketSequence======"+betTicket.getTicketSequence());
			return this.getBetTicketDao().insertBetTicket(betTicket);
		} catch (Exception e) {
			logger.error("insertBetTicket error :", e);
			throw new LotteryException(E_411005_CODE,E_411005_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: insertBetTicketBatch
	*Description: 
	* @param betTicketList
	* @return
	* @throws LotteryException
	* @see com.success.lottery.ticket.service.interf.BetTicketServiceInterf#insertBetTicketBatch(java.util.List)
	 */
	public List<String> insertBetTicketBatch(List<BetTicketDomain> betTicketList)
			throws LotteryException {

		try {
			for (BetTicketDomain tickDomain : betTicketList) {
				tickDomain.setTicketSequence(LotterySequence.getInstatce("CP")
						.getSequence());
				logger.debug("setTicketSequence======="
						+ tickDomain.getTicketSequence());
			}

			return this.getBetTicketDao().insertBetOrderBatch(betTicketList);
		} catch (DataAccessException e) {
			logger.error("insertBetTicketBatch error :", e);
			throw new LotteryException(E_411005_CODE, E_411005_DESC);
		}
	}
	
	/*
	 * (非 Javadoc)
	*Title: queryBetOrderIdTicket
	*Description: 
	* @param orderId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.ticket.service.interf.BetTicketServiceInterf#queryBetOrderIdTicket(java.lang.String)
	 */
	public int queryBetOrderIdTicket(String orderId) throws LotteryException {
		try {
			return this.getBetTicketDao().queryBetOrderIdTicket(orderId);
		} catch (Exception e) {
			logger.error("queryBetOrderIdTicket error :", e);
			throw new LotteryException(E_411003_CODE,E_411003_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: updateBetTicketPrintInfo
	*Description: 
	* @param ticketSequence
	* @param ticketStatus
	* @param ticketId
	* @param printerId
	* @param printResult
	* @param ticketData
	* @param ticketDataMD
	* @param ticketPassword
	* @return
	* @throws LotteryException
	* @see com.success.lottery.ticket.service.interf.BetTicketServiceInterf#updateBetTicketPrintInfo(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int updateBetTicketPrintInfo(String ticketSequence,int ticketStatus, String ticketId, String printerId, String printResult, String ticketData, String ticketDataMD, String ticketPassword) throws LotteryException {
		try {
			BetTicketDomain ticketDomain = new BetTicketDomain();
			ticketDomain.setTicketSequence(ticketSequence);
			ticketDomain.setTicketStatus(ticketStatus);
			ticketDomain.setTicketId(ticketId);
			ticketDomain.setPrinterId(printerId);
			ticketDomain.setPrintResult(printResult);
			ticketDomain.setTicketData(ticketData);
			ticketDomain.setTicketDataMD(ticketDataMD);
			ticketDomain.setTicketPassword(ticketPassword);
			return this.getBetTicketDao().updateBetTicketPrintInfo(ticketDomain);
		} catch (Exception e) {
			logger.error("updateBetTicketPrintInfo Error :", e);
			throw new LotteryException(E_411004_CODE,E_411004_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: updateBetTicketPrizeResult
	*Description: 
	* @param ticketSequence
	* @param preTaxPrize
	* @param prizeResult
	* @return
	* @throws LotteryException
	* @see com.success.lottery.ticket.service.interf.BetTicketServiceInterf#updateBetTicketPrizeResult(java.lang.String, long, int)
	 */
	public int updateBetTicketPrizeResult(String ticketSequence, long preTaxPrize, int prizeResult) throws LotteryException {
		try {
			return this.getBetTicketDao().updateBetTicketPrizeResult(ticketSequence, preTaxPrize, prizeResult);
		} catch (Exception e) {
			logger.error("updateBetTicketPrizeResult Error :", e);
			throw new LotteryException(E_411004_CODE,E_411004_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: updateBetTicketStatus
	*Description: 
	* @param ticketSequence
	* @param ticketStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.ticket.service.interf.BetTicketServiceInterf#updateBetTicketStatus(java.lang.String, int)
	 */
	public int updateBetTicketStatus(String ticketSequence, int ticketStatus) throws LotteryException {
		try {
			return this.getBetTicketDao().updateBetTicketStatus(ticketSequence, ticketStatus);
		} catch (RuntimeException e) {
			logger.error("updateBetTicketStatus Error :", e);
			throw new LotteryException(E_411004_CODE,E_411004_DESC);
		}
	}
	
	public BetTicketDaoImpl getBetTicketDao() {
		return betTicketDao;
	}
	public void setBetTicketDao(BetTicketDaoImpl betTicketDao) {
		this.betTicketDao = betTicketDao;
	}

	@Override
	public List<BetTicketDomain> getTickets(String orderId) throws LotteryException{
		try {
			return this.getBetTicketDao().getTicketsByOrderId(orderId);
		} catch (RuntimeException e) {
			logger.error("getTickets Error :", e);
			throw new LotteryException(E_411003_CODE,E_411003_DESC + ":" + e.toString());
		}
	}
	@Override
	public List<BetTicketDomain> getCreateFileTicketes(int lotteryId, String term, int pageNumber, int perPageNumber) throws LotteryException{
		if(lotteryId <= 0){
			//return null;
			lotteryId = -1;
		}
		if(StringUtils.isBlank(term)){
			//return null;
			term = null;
		}
		List<Integer> status = new ArrayList<Integer>();
		status.add(Integer.valueOf(0));
		
		return (List<BetTicketDomain>)this.getBetTicketDao().getTicketes(lotteryId, term, status, perPageNumber * (pageNumber - 1), perPageNumber);
	}
	@Override
	public int getCreateFileTicketesCount(int lotteryId, String term) throws LotteryException{
		if(lotteryId <= 0){
			//return 0;
			lotteryId = -1;
		}
		if(StringUtils.isBlank(term)){
			//return 0;
			term = null;
		}
		List<Integer> status = new ArrayList<Integer>();
		status.add(Integer.valueOf(0));
		
		return this.getBetTicketDao().getTicketesCount(lotteryId, term, status);
	}
	@Override
	public List<BetTicketDomain> getTicketFiles(int lotteryId, String term, int pageNumber, int perPageNumber) throws LotteryException{
		if(lotteryId <= 0){
			//return 0;
			lotteryId = -1;
		}
		if(StringUtils.isBlank(term)){
			//return 0;
			term = null;
		}
		return (List<BetTicketDomain>)this.getBetTicketDao().getTicketFiles(lotteryId, term, perPageNumber * (pageNumber - 1), perPageNumber);
	}
	@Override
	public int getTicketFilesCount(int lotteryId, String term) throws LotteryException{
		if(lotteryId <= 0){
			//return 0;
			lotteryId = -1;
		}
		if(StringUtils.isBlank(term)){
			//return 0;
			term = null;
		}
		return this.getBetTicketDao().getTicketFilesCount(lotteryId, term);
	}

	@Override
	public List<BetTicketDomain> getConfirmTicketes(int lotteryId, String term, int ticketStatus, int pageNumber, int perPageNumber) throws LotteryException{
		if(lotteryId <= 0){
			//return 0;
			lotteryId = -1;
		}
		if(StringUtils.isBlank(term)){
			//return 0;
			term = null;
		}
		List<Integer> status = new ArrayList<Integer>();
		if((ticketStatus != 1 && ticketStatus != 6 && ticketStatus != 7 && ticketStatus != -1)){
			return null;
		}
		if(ticketStatus == -1){
			status.add(Integer.valueOf(1));
			status.add(Integer.valueOf(6));
			status.add(Integer.valueOf(7));
		}else{
			status.add(Integer.valueOf(ticketStatus));
		}
		return (List<BetTicketDomain>)this.getBetTicketDao().getTicketes(lotteryId, term, status, perPageNumber * (pageNumber - 1), perPageNumber);
	}
	@Override
	public int getConfirmTicketesCount(int lotteryId, String term, int ticketStatus) throws LotteryException{
		if(lotteryId <= 0){
			//return 0;
			lotteryId = -1;
		}
		if(StringUtils.isBlank(term)){
			//return 0;
			term = null;
		}
		List<Integer> status = new ArrayList<Integer>();
		if((ticketStatus != 1 && ticketStatus != 6 && ticketStatus != 7 && ticketStatus != -1)){
			return 0;
		}
		if(ticketStatus == -1){
			status.add(Integer.valueOf(1));
			status.add(Integer.valueOf(6));
			status.add(Integer.valueOf(7));
		}else{
			status.add(Integer.valueOf(ticketStatus));
		}
		return this.getBetTicketDao().getTicketesCount(lotteryId, term, status);
	}

	@Override
	public List<WinOrderTicketDomain> getWinTicketes(int lotteryId, String term, String orderId, int pageNumber, int perPageNumber) throws LotteryException{
		if(lotteryId <= 0){
			//return 0;
			lotteryId = -1;
		}
		if(StringUtils.isBlank(term)){
			//return 0;
			term = null;
		}
		if(StringUtils.isBlank(orderId)){
			orderId = null;
		}
		List<Integer> winStatus = new ArrayList<Integer>();
		winStatus.add(Integer.valueOf(2));
		winStatus.add(Integer.valueOf(3));
		winStatus.add(Integer.valueOf(299));
		winStatus.add(Integer.valueOf(399));
		return this.getBetTicketDao().getTicketOrderes(lotteryId, term, orderId, null, winStatus, null, perPageNumber * (pageNumber - 1), perPageNumber);
	}
	
	@Override
	public int getWinTicketesCount(int lotteryId, String term, String orderId) throws LotteryException{
		if(lotteryId <= 0){
			//return 0;
			lotteryId = -1;
		}
		if(StringUtils.isBlank(term)){
			//return 0;
			term = null;
		}
		if(StringUtils.isBlank(orderId)){
			orderId = null;
		}
		List<Integer> winStatus = new ArrayList<Integer>();
		winStatus.add(Integer.valueOf(2));
		winStatus.add(Integer.valueOf(3));
		winStatus.add(Integer.valueOf(299));
		winStatus.add(Integer.valueOf(399));
		return this.getBetTicketDao().getTicketOrderesCount(lotteryId, term, orderId, null, winStatus, null);
	}
	
	@Override
	public BetTicketDomain getTicket(String ticketSequence) throws LotteryException{
		return this.getBetTicketDao().getTicket(ticketSequence);
	}
	
	public BetOrderDomain getOrderByTicketSequence(String ticketSequence) throws LotteryException{
		return this.getBetTicketDao().getOrder(ticketSequence);
	}
	
	public static void main(String[] args) throws LotteryException{
		BetTicketServiceInterf ticketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		System.out.println(ticketService.getCreateFileTicketesCount(1000001, "10061"));
		for(BetTicketDomain ticket : ticketService.getCreateFileTicketes(1000001, "10061", 1, 50)){
			System.out.println(ticket.getTicketSequence());
		}
		System.out.println("--------------------------------------------------------");
		
		System.out.println(ticketService.getTicketFilesCount(1000001, "10061"));
		for(BetTicketDomain ticket : ticketService.getTicketFiles(1000001, "10061", 1, 50)){
			System.out.println(ticket.getTicketData());
		}
		System.out.println("--------------------------------------------------------");

		System.out.println(ticketService.getWinTicketesCount(1000001, "10061", null));
		for(WinOrderTicketDomain ticket : ticketService.getWinTicketes(1000001, "10061", null, 1, 50)){
			System.out.println(ticket.getTicketData());
		}
	}
	@Override
	public List<BetTicketAcountDomain> getBetTicketes(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime,
			int page, int perPageNumber) throws LotteryException {
		List list = null;
		try {
			Timestamp beginTimeCovent = null;
			Timestamp endTimeCovent = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(beginTime != null){
				beginTimeCovent = Timestamp.valueOf(format.format(beginTime) + " 00:00:00");
			}
			if(endTime != null){
				endTimeCovent = Timestamp.valueOf(format.format(endTime) + " 23:59:59");
			}
			int startNumber = perPageNumber * (page - 1);
			int endNumber = page * perPageNumber;
			list = this.getBetTicketDao().selectBetTicketesList(orderId,ticketSequence,lotteryId,betTerm_begin
					,betTerm_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTimeCovent, endTimeCovent,startNumber,endNumber);
		} catch (Exception e) {
			throw new LotteryException(E_411007_CODE,E_411007_DESC+e.toString());
		}
		return list;
	}
	@Override
	public int getBetTicketesCount(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime)
			throws LotteryException {
		int pageCount = 0;
		try {
			Timestamp beginTimeCovent = null;
			Timestamp endTimeCovent = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(beginTime != null){
				beginTimeCovent = Timestamp.valueOf(format.format(beginTime) + " 00:00:00");
			}
			if(endTime != null){
				endTimeCovent = Timestamp.valueOf(format.format(endTime) + " 23:59:59");
			}
			pageCount = this.getBetTicketDao().selectBetTicketesCount(orderId,ticketSequence,lotteryId,betTerm_begin
					,betTerm_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTimeCovent, endTimeCovent);
		} catch (Exception e) {
			throw new LotteryException(E_411017_CODE,E_411017_DESC+e.toString());
		}
		return pageCount;
	}
	@Override
	public long getBetTicketesMoney(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime) throws LotteryException {
		long betTicketesMoney = 0;
		try {
			Timestamp beginTimeCovent = null;
			Timestamp endTimeCovent = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(beginTime != null){
				beginTimeCovent = Timestamp.valueOf(format.format(beginTime) + " 00:00:00");
			}
			if(endTime != null){
				endTimeCovent = Timestamp.valueOf(format.format(endTime) + " 23:59:59");
			}
			betTicketesMoney = this.getBetTicketDao().selectBetTicketesMoney(orderId,ticketSequence,lotteryId,betTerm_begin
					,betTerm_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTimeCovent, endTimeCovent);
		
		} catch (Exception e) {
			throw new LotteryException(E_4110023_CODE,E_411023_DESC+e.toString());
		}
		return betTicketesMoney;
	}
	@Override
	public long getBetTicketesNumber(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime) throws LotteryException {
		long pageCount = 0;
		try {
			Timestamp beginTimeCovent = null;
			Timestamp endTimeCovent = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(beginTime != null){
				beginTimeCovent = Timestamp.valueOf(format.format(beginTime) + " 00:00:00");
			}
			if(endTime != null){
				endTimeCovent = Timestamp.valueOf(format.format(endTime) + " 23:59:59");
			}
			pageCount = this.getBetTicketDao().selectBetTicketesNumber(orderId,ticketSequence,lotteryId,betTerm_begin
					,betTerm_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTimeCovent, endTimeCovent);
		
		} catch (Exception e) {
			throw new LotteryException(E_4110021_CODE,E_411021_DESC+e.toString());
		}
		return pageCount;
	}
	@Override
	public List<BetTicketAcountDomain> getWinningBetTicketes(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime,
			Date endTime, int page, int perPageNumber) throws LotteryException {
		List list = null;
		try {
			Timestamp beginTimeCovent = null;
			Timestamp endTimeCovent = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(beginTime != null){
				beginTimeCovent = Timestamp.valueOf(format.format(beginTime) + " 00:00:00");
			}
			if(endTime != null){
				endTimeCovent = Timestamp.valueOf(format.format(endTime) + " 23:59:59");
			}
			int startNumber = perPageNumber * (page - 1);
			int endNumber = page * perPageNumber;
			list = this.getBetTicketDao().selectWinningBetTicketes(orderId,ticketSequence,lotteryId,betTerm_begin
					,betTerm_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTimeCovent, endTimeCovent,startNumber,endNumber);
		} catch (Exception e) {
			throw new LotteryException(E_411009_CODE,E_411009_DESC+e.toString());
		}
		return list;
	}
	@Override
	public int getWinningBetTicketesCount(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime)
			throws LotteryException {
		int pageCount = 0;
		try {
			Timestamp beginTimeCovent = null;
			Timestamp endTimeCovent = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(beginTime != null){
				beginTimeCovent = Timestamp.valueOf(format.format(beginTime) + " 00:00:00");
			}
			if(endTime != null){
				endTimeCovent = Timestamp.valueOf(format.format(endTime) + " 23:59:59");
			}
			pageCount = this.getBetTicketDao().selectWinningBetTicketesCount(orderId,ticketSequence,lotteryId,betTerm_begin
					,betTerm_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTimeCovent, endTimeCovent);
		} catch (Exception e) {
			throw new LotteryException(E_411019_CODE,E_411019_DESC+e.toString());
		}
		return pageCount;
	}
	@Override
	public List<BetTicketAcountDomain> getBetTicketes4OrderId(String orderId)
			throws LotteryException {
		List<BetTicketAcountDomain> list = null;
		try{
			list = this.getBetTicketDao().selectBetTicketes4OrderId(orderId);
		}catch(Exception e){
			throw new LotteryException(E_4110011_CODE,E_411011_DESC+e.toString());
		}
		return list;
	}
	@Override
	public long getWinningBetTicketesMoney(String orderId,String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult, String accountId,
			String userName, String planSourceId,Date beginTime,
			Date endTime) throws LotteryException {
		long betTicketesMoney = 0;
		try {
			Timestamp beginTimeCovent = null;
			Timestamp endTimeCovent = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(beginTime != null){
				beginTimeCovent = Timestamp.valueOf(format.format(beginTime) + " 00:00:00");
			}
			if(endTime != null){
				endTimeCovent = Timestamp.valueOf(format.format(endTime) + " 23:59:59");
			}
			betTicketesMoney = this.getBetTicketDao().selectWinningBetTicketesMoney(orderId,ticketSequence,lotteryId,betTerm_begin
					,betTerm_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTimeCovent, endTimeCovent);
		} catch (Exception e) {
			throw new LotteryException(E_4110033_CODE,E_411033_DESC+e.toString());
		}
		return betTicketesMoney;
	}
	@Override
	public long getWinningBetTicketesNumber(String orderId,String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult, String accountId,
			String userName, String planSourceId,Date beginTime,
			Date endTime) throws LotteryException {
		long pageCount = 0;
		try {
			Timestamp beginTimeCovent = null;
			Timestamp endTimeCovent = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(beginTime != null){
				beginTimeCovent = Timestamp.valueOf(format.format(beginTime) + " 00:00:00");
			}
			if(endTime != null){
				endTimeCovent = Timestamp.valueOf(format.format(endTime) + " 23:59:59");
			}
			pageCount = this.getBetTicketDao().selectWinningBetTicketesNumber(orderId,ticketSequence,lotteryId,betTerm_begin
					,betTerm_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTimeCovent, endTimeCovent);
		} catch (Exception e) {
			throw new LotteryException(E_4110031_CODE,E_411031_DESC+e.toString());
		}
		return pageCount;
	}
	@Override
	public BetTicketAcountDomain getBetTicketAcount(String ticketSequence)
			throws LotteryException {
		BetTicketAcountDomain betTicketAcountDomain= null;
		try {
			betTicketAcountDomain = this.getBetTicketDao().selectBetTicketAcount(ticketSequence);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return betTicketAcountDomain;
	}

	public List<BetTicketDomain> getTicketes(List<Integer> ticketStatus, String condition, int limitNumber) throws LotteryException {
		try {
			if(ticketStatus == null || ticketStatus.isEmpty()){
				return null;
			}
			return this.getBetTicketDao().queryUndeliverTicketQueTicket(ticketStatus, condition, limitNumber);
		} catch (Exception e) {
			logger.error("getTicketes Error :", e);
			throw new LotteryException(BetTicketServiceInterf.E_411003_CODE,BetTicketServiceInterf.E_411003_DESC);
		}
	}

	public int updateBetTicketesStatus(List<String> ticketSequences, int ticketStatus, List<Integer> whoes) throws LotteryException {
		try {
			if(ticketSequences == null || ticketSequences.isEmpty()){
				return 0;
			}
			return this.getBetTicketDao().updateBetTicketStatus(ticketSequences, ticketStatus, whoes);
		} catch (Exception e) {
			logger.error("updateBetTicketStatus Error :", e);
			throw new LotteryException(BetTicketServiceInterf.E_411004_CODE, BetTicketServiceInterf.E_411004_DESC);
		}
	}

}
