-- ����ϵͳ����Ա��ɫȨ�޶�Ӧ��Ϣ
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R000001", "all", 0, "all", null);

-- �������Ȩ�޽�ɫȨ�޶�Ӧ��Ϣ����ϵͳ����Ա�������û�������ӵ�иý�ɫ��
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R000002", "PT00010001", 1, "index", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R000002", "PT00010002", 1, "top", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R000002", "PT00010003", 1, "menu", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R000002", "PT00010004", 1, "mainframe", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R000002", "PT00010005", 1, "welcome", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R000002", "PT00010006", 1, "changePasswdShow", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R000002", "PT00010007", 1, "changepasswd", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R000002", "PT00010008", 1, "getTermList", null);


-- �����������ɫȨ�޶�Ӧ��Ϣ
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT02010001", 1, "accountQuery", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT02010002", 1, "accountTransQuery", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT02010003", 1, "ipsQuery", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT02010004", 1, "canDraPrizeShow", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT02010005", 1, "adjustaccountshow", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT02010006", 1, "PrizeHistoryShow", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT02010007", 1, "drawPrizeConfirm", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT02010008", 1, "drawPrizeDeal", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT02010009", 1, "drawPrizeDetail", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT02010010", 1, "adjustaccounthistory", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT02010011", 1, "adjustconfirm", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT02010012", 1, "adjustDeal", null);
-- ����������ϸ����
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT04010002", 1, "showOrderInfoDetail", null);
-- ������Ա��ϸ��Ϣ����
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100001", "PT01010002", 1, "showUserInfoDetail", null);

-- ҵ�������Ա�������ڹ���Ͷע�������н�����
-- ��Ա����
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT01010001", 1, "queryMember", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT01010002", 1, "showUserInfoDetail", null);
-- ���ڹ���
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010001", 1, "queryAllTerm", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010002", 1, "showTermSaleInfo", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010003", 1, "showWinInfo", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010004", 1, "showLimitNumber", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010005", 1, "showInputWinInfo", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010006", 1, "showTermInfoDetail", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010007", 1, "superWinInput", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010008", 1, "colorWinInput", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010009", 1, "arrangeWinInput", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010010", 1, "ballWinInput", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010011", 1, "showlimitInputInfo", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010012", 1, "inputLimitInfo", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010013", 1, "showInputSaleInfo", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT03010014", 1, "ballSaleInput", null);
-- Ͷע����
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT04010001", 1, "showBetOrderInfo", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT04010002", 1, "showOrderInfoDetail", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT04010003", 1, "updateOrderInfoShow", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT04010004", 1, "updateFailOrder", null);

-- �н�����
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT05010001", 1, "showBetCashOrderInfo", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT05010002", 1, "showBetDispatchOrderInfo", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT05010003", 1, "betOrderAllCash", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT05010004", 1, "betOrderCashWait", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT05010005", 1, "dispatchOrderConfig", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100002", "PT05010006", 1, "dispatchOrder", null);


-- �����Ʊ�����ɫȨ�޶�Ӧ��Ϣ
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100003", "PT06010001", 1, "ticketCreate", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100003", "PT06010002", 1, "ticketFileDownload", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100003", "PT06010003", 1, "ticketConfirm", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100003", "PT06010004", 1, "ticketWinQuery", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100003", "PT06010005", 1, "download", null);
insert into adminroleprivileges(roleid, privilegeid, privilegetype, accesskey, reserve) values("R100003", "PT04010002", 1, "showOrderInfoDetail", null);


-- ����ϵͳ����Ա admin/chinatlt, libing/libing
insert into adminuser(loginname, password, name, mobile, status) values("admin", "d1c0797b2b13f8cd91bbff0c59548b4d", "ϵͳ����Ա", "13761874366", 1);
insert into adminuser(loginname, password, name, mobile, status) values("libing", "056094b080db1e3062a35a8a588079f5", "���Թ���Ա", "13761874366", 1);

-- ����ϵͳ����Ա��ɫ��Ϣ
insert into adminuserroles(userid, roleid) values(1, "R000001");
insert into adminuserroles(userid, roleid) values(2, "R000001");

