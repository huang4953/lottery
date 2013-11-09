package com.success.lottery.account.service;

import java.util.Date;
import java.util.List;

import com.success.lottery.account.model.AccountTransactionModel;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.exception.LotteryException;
/**
 * ģ���쳣LotteryException˵���� ģ���쳣��ţ�10 ģ���쳣���ͣ�0-ҵ��У�鲻ͨ�� 1-������� �쳣�б�˵�� 100501 -
 * ע���û���Ϣ����¼�����ֻ�������ʼ���ַ���Ѿ����� 100502 - ע��ʱ�û�����Ϊ�� 101501 - ����û�ע����Ϣʱ�����쳣 101502 -
 * ����û�ע����Ϣʱ�����쳣
 * 
 * @author bing.li
 * 
 */
public interface AccountService{
    
	//�޸���������г����쳣
	public static final int USERPASSWORDEXCEPTION=100101;
	public static final String USERPASSWORDEXCEPTION_STR="�޸���������г����쳣";
	
	//�޸Ļ�������QQ,�Ա𣬵�ַ���绰�����г����쳣
	public static final int USERINFOFORBAISE=100103;
	public static final String USERINFOFORBAISE_STR="�޸Ļ�����Ϣ�����쳣";
	
	public static final int USERINFOFORIDCARD=100104;
	public static final String USERINFOFORIDCARD_STR="�޸����֤��������쳣";
	//�޸���Ϣ�����쳣
	public static final int USERINFOEXCEPTION=100102;
	public static final String USERINFOEXCEPTION_STR="�޸���Ϣ�����쳣";
	// ע���û��Ѿ�����
	public static final int		USERISEXIST							= 100501;
	public static final String	USERISEXIST_STR						= "ע���û��Ѿ�����";
	//�û�������Ϊ��
	public static final int USERNAMENULL 							=100591;
	public static final String USERNAMENULL_STR						="ע��ʱ�û�������Ϊ��";
	
	//���䲻��Ϊ��
	public static final int EMAILNULL								=100592;
	public static final String EMAILNULL_STR						="ע��ʱ�ʼ���ַ����Ϊ��";
	
	//����д��Ч���ʼ���ַ
	public static final int EMLVALIDATA								=100593;
	public static final String EMLVALIDATA_STR						="ע��ʱ����д��Ч���ʼ���ַ";
	
	// ע���û�����Ϊ��
	public static final int		PASSWORDISNULL						= 100502;
	public static final String	PASSWORDISNULL_STR					= "ע���û�����Ϊ��";
	// ע��ʱ�ֻ�����Ϊ��
	public static final int		MOBILEISNULL						= 100503;
	public static final String	MOBILEISNULL_STR					= "ע��ʱ�ֻ�����Ϊ��";
	// ��Ҫ���ҵ��û���Ϣû���ҵ���UserId, ��¼�����ֻ�������ʼ���ַ��
	public static final int		NOTFOUNDUSER						= 100504;
	public static final String	NOTFOUNDUSER_STR					= "��Ҫ���ҵ��û���Ϣû���ҵ���UserId, ��¼�����ֻ�������ʼ���ַ��";
	// �û��ʽ���
	public static final int		NOTENOUGHMONEY						= 100505;
	public static final String	NOTENOUGHMONEY_STR					= "�û��ʽ���";
	// �û�״̬�����������ܱ������ע��
	public static final int		USERSTATUSERROR						= 100506;
	public static final String	USERSTATUSERROR_STR					= "�û�״̬�����������ܱ������ע��";
	// ������ˮ��Ϊ��
	public static final int		SOURCESEQUENCEISNULL				= 100507;
	public static final String	SOURCESEQUENCEISNULL_STR			= "������ˮ��Ϊ��";
	// ���׽��С�ڵ���0
	public static final int		TRANSACTIONAMOUNTISZERO				= 100508;
	public static final String	TRANSACTIONAMOUNTISZERO_STR			= "���׽��С�ڵ���0";
	// �Ҳ����Ľ������
	public static final int		NOTFOUNDTRANSACTIOYTYPE				= 100509;
	public static final String	NOTFOUNDTRANSACTIOYTYPE_STR			= "�Ҳ����Ľ������";
	// ����ʱû���ҵ���ص��û�
	public static final int		NOTTRANSACTIONUSER					= 100510;
	public static final String	NOTTRANSACTIONUSER_STR				= "����ʱû���ҵ���ص��û�";
	// �����˻����ʱδ�ܳɹ���update ����0
	public static final int		NOTUPDATEACCOUNT					= 100511;
	public static final String	NOTUPDATEACCOUNT_STR				= "�����˻����ʱδ�ܳɹ���update ����0";
	// �����û���Ϣʱδ�ܳɹ���update ����0
	public static final int		NOTUPDATEUSERINFO					= 100512;
	public static final String	NOTUPDATEUSERINFO_STR				= "�����û���Ϣʱδ�ܳɹ���update ����0";

	// Ҫע�����û��Ѿ�ע��
	public static final int		USERISUNREGISTED					= 100513;
	public static final String	USERISUNREGISTED_STR				= "Ҫע�����û��Ѿ�ע��";

	public final static int LOGINFAILED = 100514;
	public final static String LOGINFAILED_STR = "��¼ʧ�ܣ���¼�û��������벻��ȷ";
	
    //ע���û���¼��Ϊ��
	public static final int		LOGINNAMEISNULL						= 100514;
	public static final String	LOGINNAMEISNULL_STR					= "ע���û��û���Ϊ��";
	
    //ע���û������ַΪ��
	public static final int		EMAILISNULL						= 100515;
	public static final String	EMAILISNULL_STR					= "ע���û������ַΪ��";

	//�����п�ʱ������Ϊ��
	public static final int 	BANKNAMEISNULL					=100594;
	public static final String  BANKNAMEISNULL_STR				="���п���ʱ���������Ʋ���Ϊ��";
	
	
	//�����п�ʱ������Ϊ��			
	public static final int 	DETAILEBANKNAMEISNULL					=100595;
	public static final String  DETAILEBANKNAMEISNULL_STR				="���п���ʱ֧�����Ʋ���Ϊ��";
	
	//�����п�ʱ���п��Ų���Ϊ��
	public static final int     BANKCARDIDISNULL						=100596;
	public static final String  BANKCARDIDISNULL_STR					="���п���ʱyin����Ϊ��";    
	
	//�����п�ʱ����
	public static final int     BINDINGBANKCARDISERR			   =100597;
	public static final String     BINDINGBANKCARDISERR_STR			   ="�����п�ʱ����";
	//ע��ʱ�û����Ѿ�����
    public static final int     LOGINNAMEALREADYEXIST				=100598;
    public static final String  LOGINNAMEALREADYEXIST_STR 			="�û����Ѿ�����";
    
    //ע��ʱ�ֻ������Ѿ�����
    public static final int     MOBILEPHONEALREADYEXIST				=100599;
    public static final String  MOBILEPHONEALREADYEXIST_STR			="�ֻ������Ѿ�����";
    
    //ע��ʱ�ʼ���ַ�Ѿ�����
    public static final int    	EMAILALREADYEXIST					=100600;
    public static final	String  EMAILALREADYEXIST_STR				="�ʼ���ַ�Ѿ�����";
    
	// �û��ʽ��˻����㣬�޷�����ʽ����
	public static final int		NOTENOUGHMONEYTOSUB					= 100550;
	public static final String	NOTENOUGHMONEYTOSUB_STR				= "�û������˻����㣬�޷���ɱ������";
	// �û��ʽ��˻����㣬�޷����Ͷע
	public static final int		NOTENOUGHMONEYTOBET					= 100551;
	public static final String	NOTENOUGHMONEYTOBET_STR				= "�û��ʽ��˻����㣬�޷����Ͷע";
	// �û��ʽ��˻����㣬�޷�����ʽ𶳽�
	public static final int		NOTENOUGHMONEYTOFROZEN				= 100552;
	public static final String	NOTENOUGHMONEYTOFROZEN_STR			= "�û��ʽ��˻����㣬�޷�����ʽ𶳽�";
	// �û������˻����㣬�޷���ɶ����ʽ�۳�
	public static final int		NOTENOUGHFROZENMONEY				= 100553;
	public static final String	NOTENOUGHFROZENMONEY_STR			= "�û������˻����㣬�޷���ɶ����ʽ�۳�";
	// �û������˻����㣬�޷���ɶ����ʽ𷵻�
	public static final int		NOTENOUGHFROZENMONEYTORETURN		= 100554;
	public static final String	NOTENOUGHFROZENMONEYTORETURN_STR	= "�û������˻����㣬�޷���ɶ����ʽ𷵻�";
	// ע���û�ʱ���ֳ����쳣
	public static final int		USERREGISTEREXCEPTION				= 101501;
	public static final String	USERREGISTEREXCEPTION_STR			= "ע���û�ʱ���ֳ����쳣��";
	// ע�������û�ʱ�����쳣������Ϊ���û���ʱ�쳣��
	public static final int		ADDUSEREXCEPTION					= 101502;
	public static final String	ADDUSEREXCEPTION_STR				= "ע�������û�ʱ�����쳣������Ϊ���û���ʱ�쳣����";
	// �����û�ʱ���ֳ����쳣
	public static final int		FINDUSEREXCEPTION					= 101503;
	public static final String	FINDUSEREXCEPTION_STR				= "�����û�ʱ���ֳ����쳣��";
	// �����˻�����ʱ�����쳣
	public static final int		TRANSACTIONEXCEPTION				= 101504;
	public static final String	TRANSACTIONEXCEPTION_STR			= "�����˻�����ʱ�����쳣��";

	// �����û���Ϣʱ���ֳ����쳣
	public static final int		UPDATEUSERINFOEXCEPTION					= 101505;
	public static final String	UPDATEUSERINFOEXCEPTION_STR				= "�����û���Ϣʱ���ֳ����쳣��";

	// ע���û�ʱ���ֳ����쳣
	public static final int		USERUNREGISTEREXCEPTION				= 101506;
	public static final String	USERUNREGISTEREXCEPTION_STR			= "ע���û�ʱ���ֳ����쳣��";
	
	public final static int LOGINEXCEPTION = 100514;
	public final static String LOGINEXCEPTION_STR = "��¼ʧ�ܣ��û���¼ʱ�����쳣��";
	
	
	// ������� 11001 ���׹����г����쳣�����ֵ�����г����쳣��
	public static final int		TRANSACTION11001EXCEPTION			= 101510;
	public static final String	TRANSACTION11001EXCEPTION_STR		= "�����ֵ�����г����쳣��";
	// ������� 11002 ���׹����г����쳣Ͷע�����ɽ����׹����г����쳣��
	public static final int		TRANSACTION11002EXCEPTION			= 101511;
	public static final String	TRANSACTION11002EXCEPTION_STR		= "Ͷע�����ɽ����׹����г����쳣��";
	// ������� 11003 ���׹����г����쳣��������Ӷ�����ͽ��׹����г����쳣��
	public static final int		TRANSACTION11003EXCEPTION			= 101512;
	public static final String	TRANSACTION11003EXCEPTION_STR		= "��������Ӷ�����ͽ��׹����г����쳣��";
	// ������� 11004 ���׹����г����쳣�����н��������ͽ��׹����г����쳣��
	public static final int		TRANSACTION11004EXCEPTION			= 101513;
	public static final String	TRANSACTION11004EXCEPTION_STR		= "�����н��������ͽ��׹����г����쳣��";

	// ������� 11005 �������ӵ��������г����쳣
	public static final int		TRANSACTION11005EXCEPTION			= 101534;
	public static final String	TRANSACTION11005EXCEPTION_STR		= "�������ӵ������׹����г����쳣��";

	
	// ������� 10001 ���׹����г����쳣���ٱ����˻����׹����г����쳣��
	public static final int		TRANSACTION10001EXCEPTION			= 101514;
	public static final String	TRANSACTION10001EXCEPTION_STR		= "���ٱ����˻����׹����г����쳣��";
	// ������� 10002 ���׹����г����쳣Ͷע���׹����г����쳣��
	public static final int		TRANSACTION10002EXCEPTION			= 101515;
	public static final String	TRANSACTION10002EXCEPTION_STR		= "Ͷע���׹����г����쳣��";
	// ������� 20002 ���׹����г����쳣׷�Ŷ����ʽ𶳽ύ�׹����г����쳣��
	public static final int		TRANSACTION20002EXCEPTION			= 101516;
	public static final String	TRANSACTION20002EXCEPTION_STR		= "׷�Ŷ����ʽ𶳽ύ�׹����г����쳣��";
	// ������� 20003 ���׹����г����쳣��������ʽ𶳽ύ�׹����г����쳣��
	public static final int		TRANSACTION20003EXCEPTION			= 101517;
	public static final String	TRANSACTION20003EXCEPTION_STR		= "��������ʽ𶳽ύ�׹����г����쳣��";
	// ������� 20005 ���׹����г����쳣��������ʽ𶳽ύ�׹����г����쳣��
	public static final int		TRANSACTION20005EXCEPTION			= 101518;
	public static final String	TRANSACTION20005EXCEPTION_STR		= "��������ʽ𶳽ύ�׹����г����쳣��";
	// ������� 20007 ���׹����г����쳣�������������ʽ𶳽ύ�׹����г����쳣��
	public static final int		TRANSACTION20007EXCEPTION			= 101519;
	public static final String	TRANSACTION20007EXCEPTION_STR		= "�������������ʽ𶳽ύ�׹����г����쳣��";
	// ������� 20008 ���׹����г����쳣�������������ʽ𶳽ύ�׹����г����쳣��
	public static final int		TRANSACTION20008EXCEPTION			= 101520;
	public static final String	TRANSACTION20008EXCEPTION_STR		= "�������������ʽ𶳽ύ�׹����г����쳣��";
	// ������� 30001 ���׹����г����쳣׷�Ŷ���Ͷע�ɹ������ʽ�۳����׹����г����쳣��
	public static final int		TRANSACTION30001EXCEPTION			= 101521;
	public static final String	TRANSACTION30001EXCEPTION_STR		= "׷�Ŷ���Ͷע�ɹ������ʽ�۳����׹����г����쳣��";
	// ������� 30002 ���׹����г����쳣���򷽰�Ͷע�ɹ������ʽ�۳����׹����г����쳣��
	public static final int		TRANSACTION30002EXCEPTION			= 101522;
	public static final String	TRANSACTION30002EXCEPTION_STR		= "���򷽰�Ͷע�ɹ������ʽ�۳����׹����г����쳣��";
	// ������� 30003 ���׹����г����쳣������򷽰�Ͷע�ɹ������ʽ�۳����׹����г����쳣��
	public static final int		TRANSACTION30003EXCEPTION			= 101523;
	public static final String	TRANSACTION30003EXCEPTION_STR		= "������򷽰�Ͷע�ɹ������ʽ�۳����׹����г����쳣��";
	// ������� 30004 ���׹����г����쳣��������������ɶ����ʽ�۳����׹����г����쳣��
	public static final int		TRANSACTION30004EXCEPTION			= 101524;
	public static final String	TRANSACTION30004EXCEPTION_STR		= "��������������ɶ����ʽ�۳����׹����г����쳣��";
	// ������� 30005 ���׹����г����쳣��������������ɶ����ʽ�۳����׹����г����쳣��
	public static final int		TRANSACTION30005EXCEPTION			= 101525;
	public static final String	TRANSACTION30005EXCEPTION_STR		= "��������������ɶ����ʽ�۳����׹����г����쳣��";
	// ������� 31001 ���׹����г����쳣Ͷע����ʧ���˿�׹����г����쳣��
	public static final int		TRANSACTION31001EXCEPTION			= 101526;
	public static final String	TRANSACTION31001EXCEPTION_STR		= "Ͷע����ʧ���˿�׹����г����쳣��";
	// ������� 31003 ���׹����г����쳣���򷽰��������������ʽ��׹����г����쳣��
	public static final int		TRANSACTION31003EXCEPTION			= 101527;
	public static final String	TRANSACTION31003EXCEPTION_STR		= "���򷽰��������������ʽ��׹����г����쳣��";
	// ������� 31005 ���׹����г����쳣������򷽰��������������ʽ��׹����г����쳣��
	public static final int		TRANSACTION31005EXCEPTION			= 101528;
	public static final String	TRANSACTION31005EXCEPTION_STR		= "������򷽰��������������ʽ��׹����г����쳣��";
	// ������� 31006 ���׹����г����쳣׷�Ŷ����������������ʽ��׹����г����쳣��
	public static final int		TRANSACTION31006EXCEPTION			= 101529;
	public static final String	TRANSACTION31006EXCEPTION_STR		= "׷�Ŷ����������������ʽ��׹����г����쳣��";
	// ������� 31007 ���׹����г����쳣������������ܾ����������ʽ��׹����г����쳣��
	public static final int		TRANSACTION31007EXCEPTION			= 101530;
	public static final String	TRANSACTION31007EXCEPTION_STR		= "������������ܾ����������ʽ��׹����г����쳣��";
	// ������� 31008 ���׹����г����쳣������������ܾ����������ʽ��׹����г����쳣��
	public static final int		TRANSACTION31008EXCEPTION			= 101531;
	public static final String	TRANSACTION31008EXCEPTION_STR		= "������������ܾ����������ʽ��׹����г����쳣��";
	public static final int GETACCOUNTEXCEPTION = 101532;
	public static final String	GETACCOUNTEXCEPTION_STR		= "����û�����ѯ���û����ϵ���Ŀ";
	public static final int GETACCOUNTLISTEXCEPTION = 101533;
	public static final String	GETACCOUNTLISTEXCEPTION_STR		= "����û�����ѯ���û�����";
	
	//������� 20009 ���׹����г����쳣������򱣵��ʽ𶳽ύ�׹����г����쳣��
	public static final int		TRANSACTION20009EXCEPTION			= 101534;
	public static final String	TRANSACTION20009EXCEPTION_STR		= "������򱣵��ʽ𶳽ύ�׹����г����쳣��";
	//������� 30006 ���׹����г����쳣���򷽰�Ͷע�ɹ����׶����ʽ�۳����׹����г����쳣��
	public static final int		TRANSACTION30006EXCEPTION			= 101535;
	public static final String	TRANSACTION30006EXCEPTION_STR		= "���򷽰�Ͷע�ɹ����׶����ʽ�۳����׹����г����쳣��";
	//������� 31009 ���׹����г����쳣���򷽰����׷��������ʽ��׹����г����쳣��
	public static final int		TRANSACTION31009EXCEPTION			= 101536;
	public static final String	TRANSACTION31009EXCEPTION_STR		= "���򷽰����׷��������ʽ��׹����г����쳣��";
	
	public static final int SENDMAILEXCEPTION = 101540;
	public static final String	SENDMAILEXCEPTION_STR		= "���������һ��ʼ������쳣��";
	
	//���û�ж�Ӧ������Ľ������ͣ���Ҫ�׳��쳣������ͳ�ƽ���׼ȷ
	public static final int TRANSACTION31010EXCEPTION=100542;
	public static final String TRANSACTION31010EXCEPTION_STR="��������ƥ������쳣";
   public static final int DATEFORMATEXCEPTION=101541;
   public static final String DATEFORMATEXCEPTION_STR="ʱ��ת�����";
	
	//�������� - �����ֵ
	public static final int		TS_ADDFUNDS			= 11001;
	//�������� - Ͷע�����н�����
	public static final int		TS_BETPRIZE			= 11002;
	
	//�������� - ��Ѹ֧�� OR ��Ǯ��ֵ
	public static final int 		SRC_IPS				= 1001;
	//�������� -  ��Ǯ��ֵ
	public static final int 		SRC_BILL				= 1004;
	
	/**
	 * ͨ��UserI����û���Ϣ
	 * 
	 * @param userId
	 *            �û�UserId
	 * @return �û���Ϣ
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
	 *              <br>�û���Ϣ���������û�״̬��ֻ��ʹ���ڲ�ѯ�û���ϸ��Ϣʱ
	 * @param userId
	 * @return �û���Ϣ
	 * @throws LotteryException
	 *          AccountService.NOTFOUNDUSER
	 *          AccountService.FINDUSEREXCEPTION
	 */
	public UserAccountModel getUserInfoNoStatus(long userId) throws LotteryException;

	/**
	 * ͨ��UserI����û���Ϣ�����ж��û������˻�+�����˻�����Ƿ����amount��
	 * 
	 * @param userId
	 *            �û�UserId
	 * @param amount
	 *            ��Ҫ�жϵĽ����ж��û������˻�+�����˻�����Ƿ���ڸý������׳��쳣
	 * @return �û���Ϣ
	 * @throws LotteryException
	 * @throws LotteryException
	 * 				AccountService.NOTFOUNDUSER
	 * 				AccountService.NOTENOUGHMONEY
	 * 				AccountService.USERSTATUSERROR
	 * 				AccountService.FINDUSEREXCEPTION
	 */
	public UserAccountModel getUserInfo(long userId, int amount) throws LotteryException;

	/**
	 * ͨ���û���ʶ��loginName, mobilePhone, email������û���Ϣ
	 * 
	 * @param userIdentify
	 *            �û���ʶ��
	 * @return �û���Ϣ
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
	 *              <br>��ȡ�û���Ϣ���������û�״̬��ֻ��ʹ���ڲ�ѯ�û���ϸ��Ϣʱ
	 * @param userIdentify
	 * @return �û���Ϣ
	 * @throws LotteryException
	 */
	public UserAccountModel getUserInfoNoStatus(String userIdentify) throws LotteryException;

	/**
	 * ͨ���û���ʶ��loginName, mobilePhone, email������û���Ϣ�����ж��û������˻�+�����˻�����Ƿ����amount��
	 * 
	 * @param userIdentify
	 *            �û���ʶ��
	 * @param amount
	 *            ��Ҫ�жϵĽ����ж��û������˻�+�����˻�����Ƿ���ڸý������׳��쳣
	 * @return �û���Ϣ
	 * @throws LotteryException
	 * @throws LotteryException
	 * 				AccountService.NOTFOUNDUSER
	 * 				AccountService.NOTENOUGHMONEY
	 * 				AccountService.USERSTATUSERROR
	 * 				AccountService.FINDUSEREXCEPTION
	 */
	public UserAccountModel getUserInfo(String userIdentify, int amount) throws LotteryException;

	/**
	 * ͨ��UserId�����û���ʶ��loginName, mobilePhone,
	 * email������û���Ϣ�����ж��û������˻�+�����˻�����Ƿ����amount�� ��userId > 0 ���� �û���ʶ��Ϊnull
	 * ʱ�����ܲ鵽�ļ�¼����Ԥ�ڵļ�¼�� where userid=userid or loginname=userIdentify or
	 * mobilephone=userIdentify or email=userIdentify
	 * 
	 * @param userId
	 *            �û�UserId�����userId > 0����ȷ����userId���ң�
	 * @param userIdentify
	 *            �û���ʶ�������UserId > 0������ֶ�ӦΪnull��������ҵ��ļ�¼������Ԥ����Ҫ�õ��ļ�¼��
	 * @param amount
	 *            ��Ҫ�жϵĽ����ж��û������˻�+�����˻�����Ƿ���ڸý������׳��쳣
	 * @return �û���Ϣ
	 * @throws LotteryException
	 * 				AccountService.NOTFOUNDUSER
	 * 				AccountService.NOTENOUGHMONEY
	 * 				AccountService.USERSTATUSERROR
	 * 				AccountService.FINDUSEREXCEPTION
	 */
	public UserAccountModel getUserInfo(long userId, String userIdentify, int amount) throws LotteryException;
	/**
	 * WEBϵͳ���û�ע�᷽��1
	 * 
	 * @param loginName
	 *            ��¼����
	 * @param mobilePhone
	 *            �ֻ�����
	 * @param nickName
	 *            �ǳ�
	 * @param password
	 *            ����
	 * @param email
	 *            �����ַ
	 * @param realName
	 *            ��ʵ����
	 * @param idCard
	 *            ֤������
	 * @return �û���Ϣ
	 * @throws LotteryException
	 * 				AccountService.PASSWORDISNULL
	 * 				AccountService.USERISEXIST
	 * 				AccountService.USERREGISTEREXCEPTION
	 * 				AccountService.ADDUSEREXCEPTION 
	 */
	public UserAccountModel registerByWeb(String loginName, String mobilePhone, String nickName, String password, String email, String realName, String idCard) throws LotteryException;

	/**
	 * WEBϵͳ���û�ע�᷽��2
	 * 
	 * @param loginName
	 *            ��¼����
	 * @param mobilePhone
	 *            �ֻ�����
	 * @param nickName
	 *            �ǳ�
	 * @param password
	 *            ����
	 * @param email
	 *            �����ַ
	 * @return �û���Ϣ
	 * @throws LotteryException
	 * 				AccountService.PASSWORDISNULL
	 * 				AccountService.USERISEXIST
	 * 				AccountService.USERREGISTEREXCEPTION
	 * 				AccountService.ADDUSEREXCEPTION
	 */
	public UserAccountModel registerByWeb(String loginName, String mobilePhone, String nickName, String password, String email) throws LotteryException;

	/**
	 * WEBϵͳ���û�ע�᷽��3
	 * 
	 * @param loginName
	 *            ��¼����
	 * @param password
	 *            ����
	 * @return �û���Ϣ
	 * @throws LotteryException
	 * 				AccountService.PASSWORDISNULL
	 * 				AccountService.USERISEXIST
	 * 				AccountService.USERREGISTEREXCEPTION
	 * 				AccountService.ADDUSEREXCEPTION
	 */
	public UserAccountModel registerByWeb(String loginName, String password) throws LotteryException;

	/**
	 * ����ϵͳ���û�ע�᷽��1��Ĭ���û�nickNameΪloginName�����loginNameΪ�գ���Ϊ�ֻ����롣
	 * ��ע��Ĭ�ϰ��û��ֻ��������ֻ���Ʊҵ��
	 * @param mobile
	 *            �ֻ�����
	 * @param password
	 *            ���룬������Ϊ��ʱ��Ĭ��ʹ���ֻ�������Ϊ���룻
	 * @param loginName
	 *            ��¼���ƣ�����¼����Ϊ��ʱ��Ĭ��ʹ���ֻ�������Ϊ��¼����
	 * @return �û���Ϣ��ע��ʧ�����׳��쳣
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
	 * ����ϵͳ���û�ע�᷽��2
	 * 
	 * @param mobile
	 *            �ֻ�����
	 * @param password
	 *            ����
	 * @param loginName
	 *            ��¼����
	 * @return �û���Ϣ��ע��ʧ�����׳��쳣
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
	 * ����ϵͳ���û�ע�᷽��3
	 * 
	 * @param mobile
	 *            �ֻ�����
	 * @param password
	 *            ����
	 * @param loginName
	 *            ��¼����
	 * @return �û���Ϣ��ע��ʧ�����׳��쳣
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
	 * ����ϵͳ���û�ע�������Ͷ���QXAȡ��ҵ��
	 * 
	 * @param mobile
	 *            �ֻ�����
	 * @throws LotteryException
	 * 				AccountService.MOBILEISNULL
	 * 				AccountService.NOTFOUNDUSER
	 * 				AccountService.USERISUNREGISTED
	 * 				AccountService.UPDATEUSERINFOEXCEPTION
	 * 				AccountService.USERUNREGISTEREXCEPTION
	 */
	public void unregisterBySMS(String mobile) throws LotteryException;

	/**
	 * �ֻ���������ҵ��
	 * @param mobile
	 * 		�û��ֻ�����
	 * @param smsServiceType
	 * 		������ҵ������
	 * 		1-�ֻ���Ʊҵ��
	 * 		2-��Ʊ�ֻ���ҵ��
	 * @return
	 * @throws LotteryException
	 */
	public UserAccountModel registerBySMS(String mobile, int smsServiceType) throws LotteryException;

	/**
	 * �ֻ��˶�����ҵ��
	 * @param mobile
	 * 		�û��ֻ�����
	 * @param smsServiceType
	 * 		������ҵ������
	 * 		1-�ֻ���Ʊҵ��
	 * 		2-��Ʊ�ֻ���ҵ��
	 * @throws LotteryException
	 */
	public void unregisterBySMS(String mobile, int smsServiceType) throws LotteryException;
	
	/**
	 * �˻����ף����һ���˻����ף����彻�׶���ο��˻����������
	 * 
	 * @param userId
	 *            �û�Id
	 * @param userIdentify
	 *            �û���ʶ��������Ϊnullʱʹ���û���ʶ�������û�����ʶ�������� loginName, mobilePhone, email
	 * @param transactionType
	 *            ��������
	 * @param amount
	 *            ���׽��
	 * @param sourceType
	 *            ����������Դ
	 * @param sourceSequence
	 *            ����������Դ��ˮ��
	 * @param remark
	 *            ���ױ�ע
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
	 * �˻����ף����һ���˻����ף����彻�׶���ο��˻����������
	 * 
	 * @param userIdentify
	 *            �û���ʶ��������Ϊnullʱʹ���û���ʶ�������û�����ʶ�������� loginName, mobilePhone, email
	 * @param transactionType
	 *            ��������
	 * @param amount
	 *            ���׽��
	 * @param sourceType
	 *            ����������Դ
	 * @param sourceSequence
	 *            ����������Դ��ˮ��
	 * @param remark
	 *            ���ױ�ע
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
	 * �û���¼
	 * @param loginName
	 * 		�û���¼��
	 * @param password
	 * 		�û�����
	 * @param lastLoginIp
	 * 		�û���¼IP�������Ϊ������£�����ʧ�ܲ�Ӱ���¼
	 * @return
	 * 		��¼�󷵻��û���Ϣ
	 * @throws LotteryException
	 * 		LOGINFAILED
	 * 		LOGINEXCEPTION
	 */
	public UserAccountModel login(String userIdentify, String password, String lastLoginIp) throws LotteryException;

	/**
	 * ��ѯ�˻����ױ䶯��������ڿ�ʼʱ����ȡ���ڵ��ڵ���0ʱ0��0�룬���ڽ���ʱ����ȡС�ڵ��ڵ��� 23:59:59
	 * @param userId
	 * 		�û�id������Ϊ-1
	 * @param startDate
	 * 		��ʼʱ�䣬ֻ�������գ����û�п�ʼʱ������д null 
	 * @param endDate
	 * 		����ʱ�䣬���û�н���ʱ������д null 
	 * @param transcationType
	 * 		��������б����£�<br/>
	 * 			11005 - �˻����ӵ���
	 * 			11001 - ��ֵ
	 * 			11002 - �ɽ�
	 * 			31008 - ���־ܾ�
	 * 			30004 - �������
	 * 			31006 - ׷�ų���
	 * 			30001 - ׷�Ź���
	 * 			10001 - �˻����ٵ���
	 * 			10002 - ����
	 * 			20007 - ��������
	 * 			20002 - ׷��
	 * @return
	 * 		���ز�ѯ���Ľ��ױ䶯��
	 * 		���б���indexΪ0��AccountTransactionModel�������ֶ��д�Ų�ͬ��ֵ��
	 * 			transactionType����ŵ�Ϊ���β�ѯ��������������û�в�ѯ��Ҳ�ɻ�ø�ֵΪ0;
	 * 			fundsAccount�����뽻�ױ�����
	 * 			prizeAccount�����뽻�׽��
	 * 			frozenAccount��֧�����ױ���
	 * 			commisionAccount��֧�����׽��
	 * 			advanceAccount�����ύ�ױ���
	 * 			awardAccount�����ύ�׽��
	 * 			otherAccount1���ⶳ���ױ���
	 * 			otherAccount2���ⶳ���׽��		
	 * @throws LotteryException
	 */
	public List<AccountTransactionModel> getUserTransactiones(long userId, Date startDate, Date endDate, int transcationType, int start, int count) throws LotteryException;
	
	/**
	 * ��ѯ�˻����ױ䶯��������ڿ�ʼʱ����ȡ���ڵ��ڵ���0ʱ0��0�룬���ڽ���ʱ����ȡС�ڵ��ڵ��� 23:59:59
	 * @param userIdentify
	 * 		�û���ʶ�����������û�mobilephone, loginname, email, realname����ѯ����ʹ��null 
	 * @param startDate
	 * 		��ʼʱ�䣬ֻ�������գ����û�п�ʼʱ������д null 
	 * @param endDate
	 * 		����ʱ�䣬���û�н���ʱ������д null 
	 * @param transcationType
	 * 		��������б����£�<br/>
	 * 			11005 - �˻����ӵ���
	 * 			11001 - ��ֵ
	 * 			11002 - �ɽ�
	 * 			31007 - ���־ܾ�
	 * 			30004 - �������
	 * 			31006 - ׷�ų���
	 * 			30001 - ׷�Ź���
	 * 			10001 - �˻����ٵ���
	 * 			10002 - ����
	 * 			20007 - ��������
	 * 			20002 - ׷��
	 * @return
	 * 		���ز�ѯ���Ľ��ױ䶯��
	 * 		���б���indexΪ0��AccountTransactionModel�������ֶ��д�Ų�ͬ��ֵ��
	 * 			transactionType����ŵ�Ϊ���β�ѯ��������������û�в�ѯ��Ҳ�ɻ�ø�ֵΪ0;
	 * 			fundsAccount�����뽻�ױ�����
	 * 			prizeAccount�����뽻�׽��
	 * 			frozenAccount��֧�����ױ���
	 * 			commisionAccount��֧�����׽��
	 * 			advanceAccount�����ύ�ױ���
	 * 			awardAccount�����ύ�׽��
	 * 			otherAccount1���ⶳ���ױ���
	 * 			otherAccount2���ⶳ���׽��		
	 * @throws LotteryException
	 */
	public List<AccountTransactionModel> getUserTransactiones(String userIdentify, Date startDate, Date endDate, int transcationType, int start, int count) throws LotteryException;
	
	/**
	 * ��ѯ�û���ֵ��������ڿ�ʼʱ����ȡ���ڵ��ڵ���0ʱ0��0�룬���ڽ���ʱ����ȡС�ڵ��ڵ��� 23:59:59
	 * @param userId
	 * 		�û�id,����Ϊ-1
	 * @param startDate
	 * 		��ʼʱ�䣬ֻ�������գ����û�п�ʼʱ������д null 
	 * @param endDate
	 * 		����ʱ�䣬���û�н���ʱ������д null 
	 * @param sourceType
	 * 		��ֵ�������б����£�Ŀǰֻ�л�Ѹ֧������
	 *          -1���ȫ���ĳ�ֵ��Ŀǰ����Ҫ��-1
	 * 			1001 - ��Ѹ֧��
	 * @param start
	 * @param count
	 * @return
	 *  ���б���indexΪ0��AccountTransactionModel�������ֶ��д�Ų�ͬ��ֵ��
	 *  transactionType����ŵ�Ϊ���β�ѯ��������������û�в�ѯ��Ҳ�ɻ�ø�ֵΪ0;
	 * @throws LotteryException
	 */
	public List<AccountTransactionModel> getUser11001Transactiones(long userId, Date startDate, Date endDate, int sourceType, int start, int count) throws LotteryException;
	
	
	/*
	 * ��ѯ��Ա���������ķ���
	 */
	public int getUseresCount(String mobilePhone, String realName,
			String idCard, Date create_time_begin, Date create_time_ends,
			String areaCode)throws LotteryException;
	/*
	 * ��ѯ��Ա���б�������ҳ�ķ���
	 */
	public List<UserAccountModel> getUseresInfo(String mobilePhone,
			String realName, String idCard, Date create_time_begin,
			Date create_time_ends, String areaCode, int pageIndex, int perPageNumber)throws LotteryException;
	/**
	 * �޸Ļ�Ա��ϸ
	 * @param nickName
	 *           �ǳ�
	 * @param userId
	 * 			�û�ID
	 * @param realName
	 * 			��ʵ����
	 * @param address
	 * 			��ϸ��ַ
	 * @param idcard
	 * 			���֤����
	 * @param email
	 * 			eml��ַ
	 * @return   0�޸Ĳ��ɹ���1�ɹ�
	 * @throws LotteryException
	 */
	public int updateUserdetailInfo(String nickName,long userId,String realName,String address,String idcard,String email)throws LotteryException;
	
	
	/**
	 * 
	 * @param userId
	 *  	  �û�ID
	 * @param qq
	 * @param address
	 * 			��ַ
	 * @param mobilePhone
	 * 			�绰
	 * @param sex
	 * 		  �Ա�
	 * @return  0�޸Ĳ��ɹ���1�ɹ�
	 */
	
	public int updateUserdetailbaise(long userId, String qq,String address,String mobilePhone,int sex)throws LotteryException;
	
	/**
	 * ����ID�޸���ʵ���������֤����
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
	 *              <br>�޸��û���Ϣ
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
	 * 		 ������
	 * @param userId
	 * 		  �û�ID  
	 * @return  0�޸����벻�ɹ���1�ɹ�
	 * @throws LotteryException
	 */
	public int updateUserPasswordInfo(String pwd,long userId)throws LotteryException;
	
	
	public void mailFindPassword(String address, String username, String passwd) throws LotteryException;
	
	/**
	 * ���� �������ͼ�SourceSequence��ѯ ���׼�¼
	 * @param sourceSequence
	 * @param transactionType
	 * @return AccountTransactionModel û�в�ѯ����Ϊnull
	 */
	public AccountTransactionModel getUserTransactionBySourceSequence(String sourceSequence,int sourceType);
	
	/**
	 * �����п�
	 * @param userId
	 * @param bankName  ����������
	 * @param detaileBankName ֧������
	 * @param bankCardId ���п���
	 * @return
	 * @throws LotteryException
	 */
	public int updateUserBankCard(long userId,String bankName,String detaileBankName,String bankCardId)throws LotteryException;
	
	/**
	 * �ж�Ψһ��ʾ�Ƿ���� 0�������� �����������
	 * @param id�������ǵ�¼�����������ֻ����룬�����������ַ��
	 * @return
	 */
	public int flgOnlylabeled(String id);

		
}
