1、Spring数据源 无
2、Spring事务管理器 无
3、Spring Context配置文件名称：com/success/lottery/business/applicationContext-business.xml
4、Spring配置的Service bead id
	busLotteryBetService com.success.lottery.business.service.impl.BetService 投注服务 服务接口:BetServiceInterf
	
	busLotteryCashPrizeService com.success.lottery.business.service.impl.CashPrizeService 兑奖服务 服务接口:CashPrizeInterf
	
	busLotteryDispatchPrizeService com.success.lottery.business.service.impl.DispatchPrizeService 派奖服务 服务接口:DispatchPrizeInterf
	
	busPrizeDrawMoneyService com.success.lottery.business.service.impl.DrawMoneyService 奖金提现服务 服务接口:DrawMoneyInterf
	
	busLotteryManagerService com.success.lottery.business.service.impl.LotteryManagerService 彩期管理服务 服务接口:LotteryManagerInterf
	
	busPlanOrderManagerService com.success.lottery.business.service.impl.PlanOrderManagerService 订单方案管理服务 服务接口:PlanOrderManagerInterf
	
5、该配置文件必须依赖于
	com.success.lottery.term.applicationContext-term.xml
	com.success.lottery.account.applicationContext-account.xml
	com.success.lottery.order.applicationContext-order.xml
