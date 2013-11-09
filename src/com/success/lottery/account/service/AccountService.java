package com.success.lottery.account.service;

import java.util.Date;
import java.util.List;

import com.success.lottery.account.model.AccountTransactionModel;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.exception.LotteryException;
/**
 * 模块异常LotteryException说明： 模块异常编号：10 模块异常类型：0-业务校验不通过 1-程序错误 异常列表说明 100501 -
 * 注册用户信息（登录名、手机号码或邮件地址）已经存在 100502 - 注册时用户密码为空 101501 - 检查用户注册信息时出现异常 101502 -
 * 检查用户注册信息时出现异常
 * 
 * @author bing.li
 * 
 */
public interface AccountService{
    
	//修改密码过程中出现异常
	public static final int USERPASSWORDEXCEPTION=100101;
	public static final String USERPASSWORDEXCEPTION_STR="修改密码过程中出现异常";
	
	//修改基本资料QQ,性别，地址，电话过程中出现异常
	public static final int USERINFOFORBAISE=100103;
	public static final String USERINFOFORBAISE_STR="修改基本信息出现异常";
	
	public static final int USERINFOFORIDCARD=100104;
	public static final String USERINFOFORIDCARD_STR="修改身份证号码出现异常";
	//修改信息出现异常
	public static final int USERINFOEXCEPTION=100102;
	public static final String USERINFOEXCEPTION_STR="修改信息出现异常";
	// 注册用户已经存在
	public static final int		USERISEXIST							= 100501;
	public static final String	USERISEXIST_STR						= "注册用户已经存在";
	//用户名不能为空
	public static final int USERNAMENULL 							=100591;
	public static final String USERNAMENULL_STR						="注册时用户名不能为空";
	
	//邮箱不能为空
	public static final int EMAILNULL								=100592;
	public static final String EMAILNULL_STR						="注册时邮件地址不能为空";
	
	//请填写有效的邮件地址
	public static final int EMLVALIDATA								=100593;
	public static final String EMLVALIDATA_STR						="注册时请填写有效的邮件地址";
	
	// 注册用户密码为空
	public static final int		PASSWORDISNULL						= 100502;
	public static final String	PASSWORDISNULL_STR					= "注册用户密码为空";
	// 注册时手机号码为空
	public static final int		MOBILEISNULL						= 100503;
	public static final String	MOBILEISNULL_STR					= "注册时手机号码为空";
	// 所要查找的用户信息没有找到（UserId, 登录名、手机号码或邮件地址）
	public static final int		NOTFOUNDUSER						= 100504;
	public static final String	NOTFOUNDUSER_STR					= "所要查找的用户信息没有找到（UserId, 登录名、手机号码或邮件地址）";
	// 用户资金不足
	public static final int		NOTENOUGHMONEY						= 100505;
	public static final String	NOTENOUGHMONEY_STR					= "用户资金不足";
	// 用户状态非正常，可能被冻结或注销
	public static final int		USERSTATUSERROR						= 100506;
	public static final String	USERSTATUSERROR_STR					= "用户状态非正常，可能被冻结或注销";
	// 渠道流水号为空
	public static final int		SOURCESEQUENCEISNULL				= 100507;
	public static final String	SOURCESEQUENCEISNULL_STR			= "渠道流水号为空";
	// 交易金额小于等于0
	public static final int		TRANSACTIONAMOUNTISZERO				= 100508;
	public static final String	TRANSACTIONAMOUNTISZERO_STR			= "交易金额小于等于0";
	// 找不到的交易类别
	public static final int		NOTFOUNDTRANSACTIOYTYPE				= 100509;
	public static final String	NOTFOUNDTRANSACTIOYTYPE_STR			= "找不到的交易类别";
	// 交易时没有找到相关的用户
	public static final int		NOTTRANSACTIONUSER					= 100510;
	public static final String	NOTTRANSACTIONUSER_STR				= "交易时没有找到相关的用户";
	// 更新账户余额时未能成功，update 返回0
	public static final int		NOTUPDATEACCOUNT					= 100511;
	public static final String	NOTUPDATEACCOUNT_STR				= "更新账户余额时未能成功，update 返回0";
	// 更新用户信息时未能成功，update 返回0
	public static final int		NOTUPDATEUSERINFO					= 100512;
	public static final String	NOTUPDATEUSERINFO_STR				= "更新用户信息时未能成功，update 返回0";

	// 要注销的用户已经注销
	public static final int		USERISUNREGISTED					= 100513;
	public static final String	USERISUNREGISTED_STR				= "要注销的用户已经注销";

	public final static int LOGINFAILED = 100514;
	public final static String LOGINFAILED_STR = "登录失败！登录用户名和密码不正确";
	
    //注册用户登录名为空
	public static final int		LOGINNAMEISNULL						= 100514;
	public static final String	LOGINNAMEISNULL_STR					= "注册用户用户名为空";
	
    //注册用户邮箱地址为空
	public static final int		EMAILISNULL						= 100515;
	public static final String	EMAILISNULL_STR					= "注册用户邮箱地址为空";

	//绑定银行卡时开户行为空
	public static final int 	BANKNAMEISNULL					=100594;
	public static final String  BANKNAMEISNULL_STR				="银行卡绑定时开户行名称不能为空";
	
	
	//绑定银行卡时开户行为空			
	public static final int 	DETAILEBANKNAMEISNULL					=100595;
	public static final String  DETAILEBANKNAMEISNULL_STR				="银行卡绑定时支行名称不能为空";
	
	//绑定银行卡时银行卡号不能为空
	public static final int     BANKCARDIDISNULL						=100596;
	public static final String  BANKCARDIDISNULL_STR					="银行卡绑定时yin不能为空";    
	
	//绑定银行卡时出错
	public static final int     BINDINGBANKCARDISERR			   =100597;
	public static final String     BINDINGBANKCARDISERR_STR			   ="绑定银行卡时出错";
	//注册时用户名已经存在
    public static final int     LOGINNAMEALREADYEXIST				=100598;
    public static final String  LOGINNAMEALREADYEXIST_STR 			="用户名已经存在";
    
    //注册时手机号码已经存在
    public static final int     MOBILEPHONEALREADYEXIST				=100599;
    public static final String  MOBILEPHONEALREADYEXIST_STR			="手机号码已经存在";
    
    //注册时邮件地址已经存在
    public static final int    	EMAILALREADYEXIST					=100600;
    public static final	String  EMAILALREADYEXIST_STR				="邮件地址已经存在";
    
	// 用户资金账户余额不足，无法完成资金调整
	public static final int		NOTENOUGHMONEYTOSUB					= 100550;
	public static final String	NOTENOUGHMONEYTOSUB_STR				= "用户本金账户余额不足，无法完成本金调整";
	// 用户资金账户余额不足，无法完成投注
	public static final int		NOTENOUGHMONEYTOBET					= 100551;
	public static final String	NOTENOUGHMONEYTOBET_STR				= "用户资金账户余额不足，无法完成投注";
	// 用户资金账户余额不足，无法完成资金冻结
	public static final int		NOTENOUGHMONEYTOFROZEN				= 100552;
	public static final String	NOTENOUGHMONEYTOFROZEN_STR			= "用户资金账户余额不足，无法完成资金冻结";
	// 用户冻结账户余额不足，无法完成冻结资金扣除
	public static final int		NOTENOUGHFROZENMONEY				= 100553;
	public static final String	NOTENOUGHFROZENMONEY_STR			= "用户冻结账户余额不足，无法完成冻结资金扣除";
	// 用户冻结账户余额不足，无法完成冻结资金返还
	public static final int		NOTENOUGHFROZENMONEYTORETURN		= 100554;
	public static final String	NOTENOUGHFROZENMONEYTORETURN_STR	= "用户冻结账户余额不足，无法完成冻结资金返还";
	// 注册用户时出现程序异常
	public static final int		USERREGISTEREXCEPTION				= 101501;
	public static final String	USERREGISTEREXCEPTION_STR			= "注册用户时出现程序异常：";
	// 注册新增用户时出现异常（短信为绑定用户绑定时异常）
	public static final int		ADDUSEREXCEPTION					= 101502;
	public static final String	ADDUSEREXCEPTION_STR				= "注册新增用户时出现异常（短信为绑定用户绑定时异常）：";
	// 查找用户时出现程序异常
	public static final int		FINDUSEREXCEPTION					= 101503;
	public static final String	FINDUSEREXCEPTION_STR				= "查找用户时出现程序异常：";
	// 进行账户交易时出现异常
	public static final int		TRANSACTIONEXCEPTION				= 101504;
	public static final String	TRANSACTIONEXCEPTION_STR			= "进行账户交易时出现异常：";

	// 更新用户信息时出现程序异常
	public static final int		UPDATEUSERINFOEXCEPTION					= 101505;
	public static final String	UPDATEUSERINFOEXCEPTION_STR				= "更新用户信息时出现程序异常：";

	// 注销用户时出现程序异常
	public static final int		USERUNREGISTEREXCEPTION				= 101506;
	public static final String	USERUNREGISTEREXCEPTION_STR			= "注销用户时出现程序异常：";
	
	public final static int LOGINEXCEPTION = 100514;
	public final static String LOGINEXCEPTION_STR = "登录失败！用户登录时出现异常：";
	
	
	// 交易类别 11001 交易过程中程序异常本金充值过程中出现异常：
	public static final int		TRANSACTION11001EXCEPTION			= 101510;
	public static final String	TRANSACTION11001EXCEPTION_STR		= "本金充值过程中出现异常：";
	// 交易类别 11002 交易过程中程序异常投注订单派奖交易过程中出现异常：
	public static final int		TRANSACTION11002EXCEPTION			= 101511;
	public static final String	TRANSACTION11002EXCEPTION_STR		= "投注订单派奖交易过程中出现异常：";
	// 交易类别 11003 交易过程中程序异常合买发起人佣金派送交易过程中出现异常：
	public static final int		TRANSACTION11003EXCEPTION			= 101512;
	public static final String	TRANSACTION11003EXCEPTION_STR		= "合买发起人佣金派送交易过程中出现异常：";
	// 交易类别 11004 交易过程中程序异常合买中奖奖金派送交易过程中出现异常：
	public static final int		TRANSACTION11004EXCEPTION			= 101513;
	public static final String	TRANSACTION11004EXCEPTION_STR		= "合买中奖奖金派送交易过程中出现异常：";

	// 交易类别 11005 本金增加调整过程中程序异常
	public static final int		TRANSACTION11005EXCEPTION			= 101534;
	public static final String	TRANSACTION11005EXCEPTION_STR		= "本金增加调整交易过程中出现异常：";

	
	// 交易类别 10001 交易过程中程序异常减少本金账户余额交易过程中出现异常：
	public static final int		TRANSACTION10001EXCEPTION			= 101514;
	public static final String	TRANSACTION10001EXCEPTION_STR		= "减少本金账户余额交易过程中出现异常：";
	// 交易类别 10002 交易过程中程序异常投注交易过程中出现异常：
	public static final int		TRANSACTION10002EXCEPTION			= 101515;
	public static final String	TRANSACTION10002EXCEPTION_STR		= "投注交易过程中出现异常：";
	// 交易类别 20002 交易过程中程序异常追号订单资金冻结交易过程中出现异常：
	public static final int		TRANSACTION20002EXCEPTION			= 101516;
	public static final String	TRANSACTION20002EXCEPTION_STR		= "追号订单资金冻结交易过程中出现异常：";
	// 交易类别 20003 交易过程中程序异常发起合买资金冻结交易过程中出现异常：
	public static final int		TRANSACTION20003EXCEPTION			= 101517;
	public static final String	TRANSACTION20003EXCEPTION_STR		= "发起合买资金冻结交易过程中出现异常：";
	// 交易类别 20005 交易过程中程序异常参与合买资金冻结交易过程中出现异常：
	public static final int		TRANSACTION20005EXCEPTION			= 101518;
	public static final String	TRANSACTION20005EXCEPTION_STR		= "参与合买资金冻结交易过程中出现异常：";
	// 交易类别 20007 交易过程中程序异常奖金提现申请资金冻结交易过程中出现异常：
	public static final int		TRANSACTION20007EXCEPTION			= 101519;
	public static final String	TRANSACTION20007EXCEPTION_STR		= "奖金提现申请资金冻结交易过程中出现异常：";
	// 交易类别 20008 交易过程中程序异常本金提现申请资金冻结交易过程中出现异常：
	public static final int		TRANSACTION20008EXCEPTION			= 101520;
	public static final String	TRANSACTION20008EXCEPTION_STR		= "本金提现申请资金冻结交易过程中出现异常：";
	// 交易类别 30001 交易过程中程序异常追号订单投注成功冻结资金扣除交易过程中出现异常：
	public static final int		TRANSACTION30001EXCEPTION			= 101521;
	public static final String	TRANSACTION30001EXCEPTION_STR		= "追号订单投注成功冻结资金扣除交易过程中出现异常：";
	// 交易类别 30002 交易过程中程序异常合买方案投注成功冻结资金扣除交易过程中出现异常：
	public static final int		TRANSACTION30002EXCEPTION			= 101522;
	public static final String	TRANSACTION30002EXCEPTION_STR		= "合买方案投注成功冻结资金扣除交易过程中出现异常：";
	// 交易类别 30003 交易过程中程序异常参与合买方案投注成功冻结资金扣除交易过程中出现异常：
	public static final int		TRANSACTION30003EXCEPTION			= 101523;
	public static final String	TRANSACTION30003EXCEPTION_STR		= "参与合买方案投注成功冻结资金扣除交易过程中出现异常：";
	// 交易类别 30004 交易过程中程序异常奖金提现申请完成冻结资金扣除交易过程中出现异常：
	public static final int		TRANSACTION30004EXCEPTION			= 101524;
	public static final String	TRANSACTION30004EXCEPTION_STR		= "奖金提现申请完成冻结资金扣除交易过程中出现异常：";
	// 交易类别 30005 交易过程中程序异常本金提现申请完成冻结资金扣除交易过程中出现异常：
	public static final int		TRANSACTION30005EXCEPTION			= 101525;
	public static final String	TRANSACTION30005EXCEPTION_STR		= "本金提现申请完成冻结资金扣除交易过程中出现异常：";
	// 交易类别 31001 交易过程中程序异常投注订单失败退款交易过程中出现异常：
	public static final int		TRANSACTION31001EXCEPTION			= 101526;
	public static final String	TRANSACTION31001EXCEPTION_STR		= "投注订单失败退款交易过程中出现异常：";
	// 交易类别 31003 交易过程中程序异常合买方案撤销返还冻结资金交易过程中出现异常：
	public static final int		TRANSACTION31003EXCEPTION			= 101527;
	public static final String	TRANSACTION31003EXCEPTION_STR		= "合买方案撤销返还冻结资金交易过程中出现异常：";
	// 交易类别 31005 交易过程中程序异常参与合买方案撤销返还冻结资金交易过程中出现异常：
	public static final int		TRANSACTION31005EXCEPTION			= 101528;
	public static final String	TRANSACTION31005EXCEPTION_STR		= "参与合买方案撤销返还冻结资金交易过程中出现异常：";
	// 交易类别 31006 交易过程中程序异常追号订单撤销返还冻结资金交易过程中出现异常：
	public static final int		TRANSACTION31006EXCEPTION			= 101529;
	public static final String	TRANSACTION31006EXCEPTION_STR		= "追号订单撤销返还冻结资金交易过程中出现异常：";
	// 交易类别 31007 交易过程中程序异常奖金提现申请拒绝返还冻结资金交易过程中出现异常：
	public static final int		TRANSACTION31007EXCEPTION			= 101530;
	public static final String	TRANSACTION31007EXCEPTION_STR		= "奖金提现申请拒绝返还冻结资金交易过程中出现异常：";
	// 交易类别 31008 交易过程中程序异常本金提现申请拒绝返还冻结资金交易过程中出现异常：
	public static final int		TRANSACTION31008EXCEPTION			= 101531;
	public static final String	TRANSACTION31008EXCEPTION_STR		= "本金提现申请拒绝返还冻结资金交易过程中出现异常：";
	public static final int GETACCOUNTEXCEPTION = 101532;
	public static final String	GETACCOUNTEXCEPTION_STR		= "库中没有你查询的用户资料的数目";
	public static final int GETACCOUNTLISTEXCEPTION = 101533;
	public static final String	GETACCOUNTLISTEXCEPTION_STR		= "库中没有你查询的用户资料";
	
	//交易类别 20009 交易过程中程序异常发起合买保底资金冻结交易过程中出现异常：
	public static final int		TRANSACTION20009EXCEPTION			= 101534;
	public static final String	TRANSACTION20009EXCEPTION_STR		= "发起合买保底资金冻结交易过程中出现异常：";
	//交易类别 30006 交易过程中程序异常合买方案投注成功保底冻结资金扣除交易过程中出现异常：
	public static final int		TRANSACTION30006EXCEPTION			= 101535;
	public static final String	TRANSACTION30006EXCEPTION_STR		= "合买方案投注成功保底冻结资金扣除交易过程中出现异常：";
	//交易类别 31009 交易过程中程序异常合买方案保底返还冻结资金交易过程中出现异常：
	public static final int		TRANSACTION31009EXCEPTION			= 101536;
	public static final String	TRANSACTION31009EXCEPTION_STR		= "合买方案保底返还冻结资金交易过程中出现异常：";
	
	public static final int SENDMAILEXCEPTION = 101540;
	public static final String	SENDMAILEXCEPTION_STR		= "发送密码找回邮件出现异常：";
	
	//如果没有对应到定义的交易类型，则要抛出异常，否则统计将不准确
	public static final int TRANSACTION31010EXCEPTION=100542;
	public static final String TRANSACTION31010EXCEPTION_STR="交易类型匹配出现异常";
   public static final int DATEFORMATEXCEPTION=101541;
   public static final String DATEFORMATEXCEPTION_STR="时间转换异彩";
	
	//交易类型 - 本金充值
	public static final int		TS_ADDFUNDS			= 11001;
	//交易类型 - 投注订单中奖奖金
	public static final int		TS_BETPRIZE			= 11002;
	
	//交易渠道 - 环迅支付 OR 快钱充值
	public static final int 		SRC_IPS				= 1001;
	//交易渠道 -  快钱充值
	public static final int 		SRC_BILL				= 1004;
	
	/**
	 * 通过UserI获得用户信息
	 * 
	 * @param userId
	 *            用户UserId
	 * @return 用户信息
	 * @throws LotteryException
	 * 				AccountService.NOTFOUNDUSER
	 * 				AccountService.USERSTATUSERROR
	 * 				AccountService.FINDUSEREXCEPTION
	 */
	public UserAccountModel getUserInfo(long userId) throws LotteryException;
	
	/**
	 * 
	 * Title: getUserInfoNoStatus<br>
	 * Description: <br>
	 *              <br>用户信息，不限制用户状态，只能使用在查询用户详细信息时
	 * @param userId
	 * @return 用户信息
	 * @throws LotteryException
	 *          AccountService.NOTFOUNDUSER
	 *          AccountService.FINDUSEREXCEPTION
	 */
	public UserAccountModel getUserInfoNoStatus(long userId) throws LotteryException;

	/**
	 * 通过UserI获得用户信息，并判断用户本金账户+奖金账户金额是否大于amount；
	 * 
	 * @param userId
	 *            用户UserId
	 * @param amount
	 *            需要判断的金额，将判断用户奖金账户+本金账户余额是否大于该金额，否则抛出异常
	 * @return 用户信息
	 * @throws LotteryException
	 * @throws LotteryException
	 * 				AccountService.NOTFOUNDUSER
	 * 				AccountService.NOTENOUGHMONEY
	 * 				AccountService.USERSTATUSERROR
	 * 				AccountService.FINDUSEREXCEPTION
	 */
	public UserAccountModel getUserInfo(long userId, int amount) throws LotteryException;

	/**
	 * 通过用户标识（loginName, mobilePhone, email）获得用户信息
	 * 
	 * @param userIdentify
	 *            用户标识符
	 * @return 用户信息
	 * @throws LotteryException
	 * @throws LotteryException
	 * 				AccountService.NOTFOUNDUSER
	 * 				AccountService.USERSTATUSERROR
	 * 				AccountService.FINDUSEREXCEPTION
	 */
	public UserAccountModel getUserInfo(String userIdentify) throws LotteryException;
	/**
	 * 
	 * Title: getUserInfoNoStatus<br>
	 * Description: <br>
	 *              <br>获取用户信息，不限制用户状态，只能使用在查询用户详细信息时
	 * @param userIdentify
	 * @return 用户信息
	 * @throws LotteryException
	 */
	public UserAccountModel getUserInfoNoStatus(String userIdentify) throws LotteryException;

	/**
	 * 通过用户标识（loginName, mobilePhone, email）获得用户信息，并判断用户本金账户+奖金账户金额是否大于amount；
	 * 
	 * @param userIdentify
	 *            用户标识符
	 * @param amount
	 *            需要判断的金额，将判断用户奖金账户+本金账户余额是否大于该金额，否则抛出异常
	 * @return 用户信息
	 * @throws LotteryException
	 * @throws LotteryException
	 * 				AccountService.NOTFOUNDUSER
	 * 				AccountService.NOTENOUGHMONEY
	 * 				AccountService.USERSTATUSERROR
	 * 				AccountService.FINDUSEREXCEPTION
	 */
	public UserAccountModel getUserInfo(String userIdentify, int amount) throws LotteryException;

	/**
	 * 通过UserId或者用户标识（loginName, mobilePhone,
	 * email）获得用户信息，并判断用户本金账户+奖金账户金额是否大于amount； 当userId > 0 并且 用户标识不为null
	 * 时，可能查到的记录不是预期的记录。 where userid=userid or loginname=userIdentify or
	 * mobilephone=userIdentify or email=userIdentify
	 * 
	 * @param userId
	 *            用户UserId，如果userId > 0，则确认用userId查找；
	 * @param userIdentify
	 *            用户标识符，如果UserId > 0，则此字段应为null，否则查找到的记录或许不是预期想要得到的记录。
	 * @param amount
	 *            需要判断的金额，将判断用户奖金账户+本金账户余额是否大于该金额，否则抛出异常
	 * @return 用户信息
	 * @throws LotteryException
	 * 				AccountService.NOTFOUNDUSER
	 * 				AccountService.NOTENOUGHMONEY
	 * 				AccountService.USERSTATUSERROR
	 * 				AccountService.FINDUSEREXCEPTION
	 */
	public UserAccountModel getUserInfo(long userId, String userIdentify, int amount) throws LotteryException;
	/**
	 * WEB系统的用户注册方法1
	 * 
	 * @param loginName
	 *            登录名称
	 * @param mobilePhone
	 *            手机号码
	 * @param nickName
	 *            昵称
	 * @param password
	 *            密码
	 * @param email
	 *            邮箱地址
	 * @param realName
	 *            真实姓名
	 * @param idCard
	 *            证件号码
	 * @return 用户信息
	 * @throws LotteryException
	 * 				AccountService.PASSWORDISNULL
	 * 				AccountService.USERISEXIST
	 * 				AccountService.USERREGISTEREXCEPTION
	 * 				AccountService.ADDUSEREXCEPTION 
	 */
	public UserAccountModel registerByWeb(String loginName, String mobilePhone, String nickName, String password, String email, String realName, String idCard) throws LotteryException;

	/**
	 * WEB系统的用户注册方法2
	 * 
	 * @param loginName
	 *            登录名称
	 * @param mobilePhone
	 *            手机号码
	 * @param nickName
	 *            昵称
	 * @param password
	 *            密码
	 * @param email
	 *            邮箱地址
	 * @return 用户信息
	 * @throws LotteryException
	 * 				AccountService.PASSWORDISNULL
	 * 				AccountService.USERISEXIST
	 * 				AccountService.USERREGISTEREXCEPTION
	 * 				AccountService.ADDUSEREXCEPTION
	 */
	public UserAccountModel registerByWeb(String loginName, String mobilePhone, String nickName, String password, String email) throws LotteryException;

	/**
	 * WEB系统的用户注册方法3
	 * 
	 * @param loginName
	 *            登录名称
	 * @param password
	 *            密码
	 * @return 用户信息
	 * @throws LotteryException
	 * 				AccountService.PASSWORDISNULL
	 * 				AccountService.USERISEXIST
	 * 				AccountService.USERREGISTEREXCEPTION
	 * 				AccountService.ADDUSEREXCEPTION
	 */
	public UserAccountModel registerByWeb(String loginName, String password) throws LotteryException;

	/**
	 * 短信系统的用户注册方法1，默认用户nickName为loginName，如果loginName为空，则为手机号码。
	 * 该注册默认绑定用户手机订阅了手机彩票业务；
	 * @param mobile
	 *            手机号码
	 * @param password
	 *            密码，当密码为空时，默认使用手机号码作为密码；
	 * @param loginName
	 *            登录名称，当登录名称为空时，默认使用手机号码作为登录名称
	 * @return 用户信息，注册失败则抛出异常
	 * @throws LotteryException
	 * 				AccountService.MOBILEISNULL
	 * 				AccountService.ADDUSEREXCEPTION
	 * 				AccountService.USERISEXIST
	 * 				AccountService.ADDUSEREXCEPTION
	 * 				AccountService.USERREGISTEREXCEPTION
	 * 				AccountService.NOTUPDATEUSERINFO
	 */
	public UserAccountModel registerBySMS(String mobile, String password, String loginName) throws LotteryException;

	/**
	 * 短信系统的用户注册方法2
	 * 
	 * @param mobile
	 *            手机号码
	 * @param password
	 *            密码
	 * @param loginName
	 *            登录名称
	 * @return 用户信息，注册失败则抛出异常
	 * @throws LotteryException
	 * 				AccountService.MOBILEISNULL
	 * 				AccountService.ADDUSEREXCEPTION
	 * 				AccountService.USERISEXIST
	 * 				AccountService.ADDUSEREXCEPTION
	 * 				AccountService.USERREGISTEREXCEPTION
	 * 				AccountService.NOTUPDATEUSERINFO
	 */
	public UserAccountModel registerBySMS(String mobile) throws LotteryException;

	/**
	 * 短信系统的用户注册方法3
	 * 
	 * @param mobile
	 *            手机号码
	 * @param password
	 *            密码
	 * @param loginName
	 *            登录名称
	 * @return 用户信息，注册失败则抛出异常
	 * @throws LotteryException
	 * 				AccountService.MOBILEISNULL
	 * 				AccountService.ADDUSEREXCEPTION
	 * 				AccountService.USERISEXIST
	 * 				AccountService.ADDUSEREXCEPTION
	 * 				AccountService.USERREGISTEREXCEPTION
	 * 				AccountService.NOTUPDATEUSERINFO
	 */
	public UserAccountModel registerBySMS(String mobile, String password) throws LotteryException;

	/**
	 * 短信系统的用户注销，发送短信QXA取消业务
	 * 
	 * @param mobile
	 *            手机号码
	 * @throws LotteryException
	 * 				AccountService.MOBILEISNULL
	 * 				AccountService.NOTFOUNDUSER
	 * 				AccountService.USERISUNREGISTED
	 * 				AccountService.UPDATEUSERINFOEXCEPTION
	 * 				AccountService.USERUNREGISTEREXCEPTION
	 */
	public void unregisterBySMS(String mobile) throws LotteryException;

	/**
	 * 手机订购短信业务
	 * @param mobile
	 * 		用户手机号码
	 * @param smsServiceType
	 * 		订购的业务类型
	 * 		1-手机彩票业务
	 * 		2-彩票手机报业务
	 * @return
	 * @throws LotteryException
	 */
	public UserAccountModel registerBySMS(String mobile, int smsServiceType) throws LotteryException;

	/**
	 * 手机退订短信业务
	 * @param mobile
	 * 		用户手机号码
	 * @param smsServiceType
	 * 		订购的业务类型
	 * 		1-手机彩票业务
	 * 		2-彩票手机报业务
	 * @throws LotteryException
	 */
	public void unregisterBySMS(String mobile, int smsServiceType) throws LotteryException;
	
	/**
	 * 账户交易，完成一次账户交易；具体交易定义参看账户交易类别定义
	 * 
	 * @param userId
	 *            用户Id
	 * @param userIdentify
	 *            用户标识符，当不为null时使用用户标识符查找用户，标识符可以是 loginName, mobilePhone, email
	 * @param transactionType
	 *            交易类型
	 * @param amount
	 *            交易金额
	 * @param sourceType
	 *            交易渠道来源
	 * @param sourceSequence
	 *            交易渠道来源流水号
	 * @param remark
	 *            交易备注
	 * @throws LotteryException
	 * 				AccountService.SOURCESEQUENCEISNULL
	 * 				AccountService.TRANSACTIONAMOUNTISZERO
	 * 				AccountService.NOTFOUNDTRANSACTIOYTYPE
	 * 				AccountService.TRANSACTIONEXCEPTION
	 * 				AccountService.NOTTRANSACTIONUSER
	 * 				AccountService.NOTUPDATEACCOUNT
	 * 				AccountService.NOTENOUGHMONEYTOSUB
	 * 				AccountService.NOTENOUGHMONEYTOBET
	 * 				AccountService.NOTENOUGHMONEYTOFROZEN
	 * 				AccountService.NOTENOUGHFROZENMONEY
	 * 				AccountService.NOTENOUGHFROZENMONEYTORETURN
	 * 				AccountService.TRANSACTION11001EXCEPTION
	 * 				AccountService.TRANSACTION11002EXCEPTION
	 * 				AccountService.TRANSACTION11003EXCEPTION
	 * 				AccountService.TRANSACTION11004EXCEPTION
	 * 				AccountService.TRANSACTION10001EXCEPTION
	 * 				AccountService.TRANSACTION10002EXCEPTION
	 * 				AccountService.TRANSACTION20002EXCEPTION
	 * 				AccountService.TRANSACTION20003EXCEPTION
	 * 				AccountService.TRANSACTION20005EXCEPTION
	 * 				AccountService.TRANSACTION20007EXCEPTION
	 * 				AccountService.TRANSACTION20008EXCEPTION
	 * 				AccountService.TRANSACTION30001EXCEPTION
	 * 				AccountService.TRANSACTION30002EXCEPTION
	 * 				AccountService.TRANSACTION30003EXCEPTION
	 * 				AccountService.TRANSACTION30004EXCEPTION
	 * 				AccountService.TRANSACTION30005EXCEPTION
	 * 				AccountService.TRANSACTION31001EXCEPTION
	 * 				AccountService.TRANSACTION31003EXCEPTION
	 * 				AccountService.TRANSACTION31005EXCEPTION
	 * 				AccountService.TRANSACTION31006EXCEPTION
	 * 				AccountService.TRANSACTION31007EXCEPTION
	 * 				AccountService.TRANSACTION31008EXCEPTION  
	 */
	public void accountTransaction(long userId, int transactionType, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException;

	public void accountTransaction(long userId, int transactionType, int amount, int sourceType, String sourceSequence) throws LotteryException;

	/**
	 * 账户交易，完成一次账户交易；具体交易定义参看账户交易类别定义
	 * 
	 * @param userIdentify
	 *            用户标识符，当不为null时使用用户标识符查找用户，标识符可以是 loginName, mobilePhone, email
	 * @param transactionType
	 *            交易类型
	 * @param amount
	 *            交易金额
	 * @param sourceType
	 *            交易渠道来源
	 * @param sourceSequence
	 *            交易渠道来源流水号
	 * @param remark
	 *            交易备注
	 * @throws LotteryException
	 * 				AccountService.SOURCESEQUENCEISNULL
	 * 				AccountService.TRANSACTIONAMOUNTISZERO
	 * 				AccountService.NOTFOUNDTRANSACTIOYTYPE
	 * 				AccountService.TRANSACTIONEXCEPTION
	 * 				AccountService.NOTTRANSACTIONUSER
	 * 				AccountService.NOTUPDATEACCOUNT
	 * 				AccountService.NOTENOUGHMONEYTOSUB
	 * 				AccountService.NOTENOUGHMONEYTOBET
	 * 				AccountService.NOTENOUGHMONEYTOFROZEN
	 * 				AccountService.NOTENOUGHFROZENMONEY
	 * 				AccountService.NOTENOUGHFROZENMONEYTORETURN
	 * 				AccountService.TRANSACTION11001EXCEPTION
	 * 				AccountService.TRANSACTION11002EXCEPTION
	 * 				AccountService.TRANSACTION11003EXCEPTION
	 * 				AccountService.TRANSACTION11004EXCEPTION
	 * 				AccountService.TRANSACTION10001EXCEPTION
	 * 				AccountService.TRANSACTION10002EXCEPTION
	 * 				AccountService.TRANSACTION20002EXCEPTION
	 * 				AccountService.TRANSACTION20003EXCEPTION
	 * 				AccountService.TRANSACTION20005EXCEPTION
	 * 				AccountService.TRANSACTION20007EXCEPTION
	 * 				AccountService.TRANSACTION20008EXCEPTION
	 * 				AccountService.TRANSACTION30001EXCEPTION
	 * 				AccountService.TRANSACTION30002EXCEPTION
	 * 				AccountService.TRANSACTION30003EXCEPTION
	 * 				AccountService.TRANSACTION30004EXCEPTION
	 * 				AccountService.TRANSACTION30005EXCEPTION
	 * 				AccountService.TRANSACTION31001EXCEPTION
	 * 				AccountService.TRANSACTION31003EXCEPTION
	 * 				AccountService.TRANSACTION31005EXCEPTION
	 * 				AccountService.TRANSACTION31006EXCEPTION
	 * 				AccountService.TRANSACTION31007EXCEPTION
	 * 				AccountService.TRANSACTION31008EXCEPTION
	 */
	public void accountTransaction(String userIdentify, int transactionType, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException;

	public void accountTransaction(String userIdentify, int transactionType, int amount, int sourceType, String sourceSequence) throws LotteryException;

	/**
	 * 用户登录
	 * @param loginName
	 * 		用户登录名
	 * @param password
	 * 		用户密码
	 * @param lastLoginIp
	 * 		用户登录IP，如果不为空则更新，更新失败不影响登录
	 * @return
	 * 		登录后返回用户信息
	 * @throws LotteryException
	 * 		LOGINFAILED
	 * 		LOGINEXCEPTION
	 */
	public UserAccountModel login(String userIdentify, String password, String lastLoginIp) throws LotteryException;

	/**
	 * 查询账户交易变动情况，对于开始时间是取大于等于当日0时0分0秒，对于截至时间是取小于等于当日 23:59:59
	 * @param userId
	 * 		用户id，不能为-1
	 * @param startDate
	 * 		开始时间，只有年月日，如果没有开始时间则填写 null 
	 * @param endDate
	 * 		截至时间，如果没有截至时间则填写 null 
	 * @param transcationType
	 * 		交易类别，列表如下：<br/>
	 * 			11005 - 账户增加调整
	 * 			11001 - 充值
	 * 			11002 - 派奖
	 * 			31008 - 提现拒绝
	 * 			30004 - 提现完成
	 * 			31006 - 追号撤销
	 * 			30001 - 追号购彩
	 * 			10001 - 账户减少调整
	 * 			10002 - 购彩
	 * 			20007 - 提现申请
	 * 			20002 - 追号
	 * @return
	 * 		返回查询到的交易变动；
	 * 		该列表中index为0的AccountTransactionModel的以下字段中存放不同的值：
	 * 			transactionType：存放的为本次查询到的总条数，如没有查询到也可获得该值为0;
	 * 			fundsAccount：收入交易笔数；
	 * 			prizeAccount：收入交易金额
	 * 			frozenAccount：支出交易笔数
	 * 			commisionAccount：支出交易金额
	 * 			advanceAccount：冻结交易笔数
	 * 			awardAccount：冻结交易金额
	 * 			otherAccount1：解冻交易笔数
	 * 			otherAccount2：解冻交易金额		
	 * @throws LotteryException
	 */
	public List<AccountTransactionModel> getUserTransactiones(long userId, Date startDate, Date endDate, int transcationType, int start, int count) throws LotteryException;
	
	/**
	 * 查询账户交易变动情况，对于开始时间是取大于等于当日0时0分0秒，对于截至时间是取小于等于当日 23:59:59
	 * @param userIdentify
	 * 		用户标识符，可以是用户mobilephone, loginname, email, realname；查询所有使用null 
	 * @param startDate
	 * 		开始时间，只有年月日，如果没有开始时间则填写 null 
	 * @param endDate
	 * 		截至时间，如果没有截至时间则填写 null 
	 * @param transcationType
	 * 		交易类别，列表如下：<br/>
	 * 			11005 - 账户增加调整
	 * 			11001 - 充值
	 * 			11002 - 派奖
	 * 			31007 - 提现拒绝
	 * 			30004 - 提现完成
	 * 			31006 - 追号撤销
	 * 			30001 - 追号购彩
	 * 			10001 - 账户减少调整
	 * 			10002 - 购彩
	 * 			20007 - 提现申请
	 * 			20002 - 追号
	 * @return
	 * 		返回查询到的交易变动；
	 * 		该列表中index为0的AccountTransactionModel的以下字段中存放不同的值：
	 * 			transactionType：存放的为本次查询到的总条数，如没有查询到也可获得该值为0;
	 * 			fundsAccount：收入交易笔数；
	 * 			prizeAccount：收入交易金额
	 * 			frozenAccount：支出交易笔数
	 * 			commisionAccount：支出交易金额
	 * 			advanceAccount：冻结交易笔数
	 * 			awardAccount：冻结交易金额
	 * 			otherAccount1：解冻交易笔数
	 * 			otherAccount2：解冻交易金额		
	 * @throws LotteryException
	 */
	public List<AccountTransactionModel> getUserTransactiones(String userIdentify, Date startDate, Date endDate, int transcationType, int start, int count) throws LotteryException;
	
	/**
	 * 查询用户充值情况，对于开始时间是取大于等于当日0时0分0秒，对于截至时间是取小于等于当日 23:59:59
	 * @param userId
	 * 		用户id,不能为-1
	 * @param startDate
	 * 		开始时间，只有年月日，如果没有开始时间则填写 null 
	 * @param endDate
	 * 		截至时间，如果没有截至时间则填写 null 
	 * @param sourceType
	 * 		充值渠道，列表如下（目前只有环迅支付）：
	 *          -1则查全部的充值，目前不需要传-1
	 * 			1001 - 环迅支付
	 * @param start
	 * @param count
	 * @return
	 *  该列表中index为0的AccountTransactionModel的以下字段中存放不同的值：
	 *  transactionType：存放的为本次查询到的总条数，如没有查询到也可获得该值为0;
	 * @throws LotteryException
	 */
	public List<AccountTransactionModel> getUser11001Transactiones(long userId, Date startDate, Date endDate, int sourceType, int start, int count) throws LotteryException;
	
	
	/*
	 * 查询会员的总数量的方法
	 */
	public int getUseresCount(String mobilePhone, String realName,
			String idCard, Date create_time_begin, Date create_time_ends,
			String areaCode)throws LotteryException;
	/*
	 * 查询会员的列表，包括分页的方法
	 */
	public List<UserAccountModel> getUseresInfo(String mobilePhone,
			String realName, String idCard, Date create_time_begin,
			Date create_time_ends, String areaCode, int pageIndex, int perPageNumber)throws LotteryException;
	/**
	 * 修改会员详细
	 * @param nickName
	 *           昵称
	 * @param userId
	 * 			用户ID
	 * @param realName
	 * 			真实名称
	 * @param address
	 * 			详细地址
	 * @param idcard
	 * 			身份证号码
	 * @param email
	 * 			eml地址
	 * @return   0修改不成功，1成功
	 * @throws LotteryException
	 */
	public int updateUserdetailInfo(String nickName,long userId,String realName,String address,String idcard,String email)throws LotteryException;
	
	
	/**
	 * 
	 * @param userId
	 *  	  用户ID
	 * @param qq
	 * @param address
	 * 			地址
	 * @param mobilePhone
	 * 			电话
	 * @param sex
	 * 		  性别
	 * @return  0修改不成功，1成功
	 */
	
	public int updateUserdetailbaise(long userId, String qq,String address,String mobilePhone,int sex)throws LotteryException;
	
	/**
	 * 根据ID修改真实姓名，身份证号码
	 * @param userId
	 * @param realName
	 * @param idCord
	 * @return
	 */
	public int updateUserInfoID(long userId,String realName,String idCard)throws LotteryException;
	
	
	/**b
	 * 
	 * Title: updateUserdetailInfo<br>
	 * Description: <br>
	 *              <br>修改用户信息
	 * @param userId
	 * @param nickName
	 * @param phone
	 * @param email
	 * @param realName
	 * @param idcard
	 * @param bankName
	 * @param bankCardId
	 * @param address
	 * @param postcode
	 * @param qq
	 * @param msn
	 * @param sex
	 * @param birthday
	 * @param reserve
	 * @return
	 * @throws LotteryException
	 */
	public int updateUserdetailInfo(long userId,String password,String nickName,String phone,String email,String realName,String idcard,String bankName,String bankCardId,String address,String postcode,String qq,String msn,int sex,Date birthday,String reserve)throws LotteryException;
	/**
	 * 
	 * @param pwd
	 * 		 新密码
	 * @param userId
	 * 		  用户ID  
	 * @return  0修改密码不成功，1成功
	 * @throws LotteryException
	 */
	public int updateUserPasswordInfo(String pwd,long userId)throws LotteryException;
	
	
	public void mailFindPassword(String address, String username, String passwd) throws LotteryException;
	
	/**
	 * 根据 交易类型及SourceSequence查询 交易记录
	 * @param sourceSequence
	 * @param transactionType
	 * @return AccountTransactionModel 没有查询到则为null
	 */
	public AccountTransactionModel getUserTransactionBySourceSequence(String sourceSequence,int sourceType);
	
	/**
	 * 绑定银行卡
	 * @param userId
	 * @param bankName  开户行名称
	 * @param detaileBankName 支行名称
	 * @param bankCardId 银行卡号
	 * @return
	 * @throws LotteryException
	 */
	public int updateUserBankCard(long userId,String bankName,String detaileBankName,String bankCardId)throws LotteryException;
	
	/**
	 * 判断唯一标示是否存在 0代表不存在 其他代表存在
	 * @param id（可以是登录名，可以是手机号码，可以是邮箱地址）
	 * @return
	 */
	public int flgOnlylabeled(String id);

		
}
