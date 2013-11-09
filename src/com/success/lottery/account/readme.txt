1、Spring数据源 bean id=dataSource
2、Spring事务管理器 bead id=transactionManager
3、Spring Context配置文件名称：com/success/lottery/account/applicationContext-account.xml, com/success/lottery/account/applicationContext-sqlmap.xml
4、Spring配置的Service bead id
userAccountService 用户使用的查询修改注册方法用到的Service
accountTransactionService 用户交易相关使用到的Service
accountService 账户相关的Service，注册、获得用户信息、交易
baseDrawMoneyService 提现基础操作