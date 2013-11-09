package com.success.admin.user.service;

import java.util.List;

import com.success.admin.user.domain.AdminUser;
import com.success.lottery.exception.LotteryException;


public interface AdminUserService{
	
	public final static int ADDUSEREXCEPTION = 161001;
	public final static String ADDUSEREXCEPTION_STR = "����û�ʱ���ֳ����쳣��";
	
	public final static int NOPASSWORDUPDATE = 160001;
	public final static String NOPASSWORDUPDATE_STR = "�޸��û�����δ�ܸ��³ɹ���UserId����������";
	
	public final static int LOGINFAILED = 160002;
	public final static String LOGINFAILED_STR = "��¼ʧ�ܣ���¼�û��������벻��ȷ";

	public final static int LOGINNOPRIVILEGESFAILED = 160003;
	public final static String LOGINNOPRIVILEGESFAILED_STR = "��¼ʧ�ܣ�δ��ȡ���û�Ȩ��";

	public final static int NOADMINUSERUPDATE = 160004;
	public final static String NOADMINUSERUPDATE_STR = "�����û���Ϣʧ�ܣ�û���ҵ��û�";
	
	public final static int NOTADMINUSER = 160005;
	public final static String NOTADMINUSER_STR = "û���ҵ��û�";
	public final static int  UPDATEUSEREXCEPTION = 160006;
	public final static String  UPDATEUSEREXCEPTION_STR = "�޸Ĺ���Աʧ��";
	/**
	 * ��Ӻ�̨�û��������û���ɫ��Ϣ�����û���ɫ��Ϣ��Ϊ��(roles != null && roles.size() > 0)ʱ�������û���ɫ��Ϣ��
	 * @param user
	 * @param roles
	 * @throws LotteryException
	 * 		ADDUSEREXCEPTION
	 */
	public void addAdminUser(AdminUser user, List<String> roles) throws LotteryException;
	
	/**
	 * �û���¼
	 * @param loginName
	 * 		�û���¼��
	 * @param password
	 * 		�û�����
	 * @param lastLoginIp
	 * 		�û���¼IP�������Ϊ������£�����ʧ�ܲ�Ӱ���¼
	 * @return
	 * 		��¼�󷵻��û���Ϣ��List<AdminRolePrivileges> privileges �����û���Ȩ����Ϣ
	 * @throws LotteryException
	 * 		LOGINFAILED
	 * 		LOGINNOPRIVILEGESFAILED
	 * 		ADDUSEREXCEPTION
	 */
	public AdminUser login(String loginName, String password, String lastLoginIp) throws LotteryException;
	
	/**
	 * �û���������
	 * @param userId
	 * 		�û�Id
	 * @param oldPassword
	 * 		������
	 * @param newPassword
	 * 		������
	 * @throws LotteryException
	 * 		NOPASSWORDUPDATE
	 * 		ADDUSEREXCEPTION
	 */
	public void changePassword(long userId, String oldPassword, String newPassword) throws LotteryException;
	
	/**
	 * �޸��û���Ϣ�������û���ɫ��Ϣ�����û���ɫ��Ϣ��Ϊ��(roles != null && roles.size() > 0)ʱ�������û���ɫ��Ϣ��
	 * @param userId
	 * 		�û�Id
	 * @param name
	 * 		�û�����
	 * @param sex
	 * 		�û��Ա�
	 * @param age
	 * 		�û�����
	 * @param telphone
	 * 		�û��绰
	 * @param mobile
	 * 		�û��ֻ�
	 * @param email
	 * 		�û�Email��ַ
	 * @param company
	 * 		�û�������˾
	 * @param roles
	 * 		�û�Ȩ����Ϣ��List�д�ŵ��ǽ�ɫId
	 * @throws LotteryException
	 * 		NOADMINUSERUPDATE
	 * 		ADDUSEREXCEPTION
	 */
	public void updateAdminUser(long userId, String name, int sex, int age, String telphone, String mobile, String email, String company, List<String> roles) throws LotteryException;

	public int getAdminUser4LoginName(String loginName) throws LotteryException;

	public List<AdminUser> getAdminUser4LoginNameList(String loginName,
			int page, int perPageNumber)throws LotteryException;

	public AdminUser getAdminUserById(Long userIdentify)throws LotteryException;

	public void updateAdminUser(AdminUser adminUser, List<String> roleIdlist)throws LotteryException;

	public AdminUser getAdminUser4LoginNameAndPws(String username,
			String password) throws LotteryException;

	public void updatePassword(long userId, String encode, String encode2) throws LotteryException;

}
