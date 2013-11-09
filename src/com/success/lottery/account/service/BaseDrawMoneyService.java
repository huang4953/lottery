/**
 * Title: DrawMoneyService.java
 * @Package com.success.lottery.account.service
 * Description: (��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-26 ����07:41:07
 * @version V1.0
 */
package com.success.lottery.account.service;

import java.util.Date;
import java.util.List;

import com.success.lottery.account.model.DrawMoneyDomain;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.account.service
 * DrawMoneyService.java
 * DrawMoneyService
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-26 ����07:41:07
 * 
 */

public interface BaseDrawMoneyService {
	
	public static final int E_01_CODE = 101601;
	public static final String E_01_DESC = "�����쳣";

	/**
	 * 
	 * Title: insertDrawMoney<br>
	 * Description: <br>
	 *            �������ֱ�<br>
	 * @param drawMoneyDomain �����������
	 * @return ������ˮ��
	 * @throws LotteryException
	 */
	public String insertDrawMoney(DrawMoneyDomain drawMoneyDomain) throws LotteryException;
	
	/**
	 * 
	 * Title: updateDrawMoney<br>
	 * Description: <br>
	 *            �������ֱ�<br>
	 * @param drawid ����id
	 * @param opUser ����Ա
	 * @param drawstatus ����״̬��0:���룬1:����ͨ�� ,2:����ܾ�
	 * @param dealreason �ܾ�ԭ���ͨ��ԭ��
	 * @param reserve �����ֶ�
	 * @return �ɹ����µļ�¼��
	 * @throws LotteryException
	 */
	public int updateDrawMoney(String drawid,String opUser,int drawstatus,String dealreason,String reserve) throws LotteryException;
	
	/**
	 * 
	 * Title: queryDrawMoneyInfo<br>
	 * Description: <br>
	 *            ��������id��ѯ������Ϣ<br>
	 * @param drawId ����id
	 * @return DrawMoneyDomain
	 * @throws LotteryException
	 */
	public DrawMoneyDomain queryDrawMoneyInfo(String drawId) throws LotteryException;
	
	/**
	 * 
	 * Title: queryDrawMoneyInfo<br>
	 * Description: <br>
	 *            �����û��˻�������״̬��ѯ�û���������Ϣ<br>
	 * @param userId �û��˻�
	 * @param drawType ������� 0:��������
	 * @param drawstatus ״̬ 0Ĭ�������ύ״̬,1����ͨ��,2���벻ͨ��
	 * @return List<DrawMoneyDomain>
	 * @throws LotteryException
	 */
	public List<DrawMoneyDomain> queryDrawMoneyInfo(long userId,List<Integer> drawType,List<Integer> drawstatus) throws LotteryException;
	
	/**
	 * 
	 * Title: queryDrawMoneyInfo<br>
	 * Description: <br>
	 *              <br>��ѯ���ּ�¼����Ҫ��˵�
	 * @param province
	 * @param city
	 * @param userIdentify
	 * @param userName
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws LotteryException
	 */
	public List<DrawMoneyDomain> queryDrawMoneyInfo(String province,String city,String userIdentify,String userName,String beginTime,String endTime) throws LotteryException;
	/**
	 * 
	 * Title: queryDrawMoneyHisInfo<br>
	 * Description: <br>
	 *              <br> ��ѯ������ʷ��Ϣ
	 * @param province
	 * @param city
	 * @param userIdentify
	 * @param userName
	 * @param beginTime
	 * @param endTime
	 * @param opUser
	 * @param drawStatus
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<DrawMoneyDomain> queryDrawMoneyHisInfo(String province,String city,String userIdentify,String userName,String beginTime,String endTime,String opUser,String drawStatus,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: queryDrawAccountHisInfo<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
	 * @param userIdentify
	 * @param accountUserName
	 * @param beginTime
	 * @param endTime
	 * @param opUser
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<DrawMoneyDomain> queryDrawAccountHisInfo(String userIdentify,String accountUserName,String beginTime,String endTime,String opUser,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: queryDrawAccountHisCount<br>
	 * Description: <br>
	 *              <br>
	 * @param userIdentify
	 * @param accountUserName
	 * @param beginTime
	 * @param endTime
	 * @param opUser
	 * @return
	 * @throws LotteryException
	 */
	public int queryDrawAccountHisCount(String userIdentify,String accountUserName,String beginTime,String endTime,String opUser) throws LotteryException;
	/**
	 * 
	 * Title: queryDrawMoneyHisCount<br>
	 * Description: <br>
	 *              <br>��ѯ������ʷ��Ϣ��¼��
	 * @param province
	 * @param city
	 * @param userIdentify
	 * @param userName
	 * @param beginTime
	 * @param endTime
	 * @param opUser
	 * @param drawStatus
	 * @return
	 * @throws LotteryException
	 */
	public int queryDrawMoneyHisCount(String province,String city,String userIdentify,String userName,String beginTime,String endTime,String opUser,String drawStatus) throws LotteryException;
	
	
	/**
	 * 
	 * Title: queryDrawMoneyInfo<br>
	 * Description: <br>
	 *            ��������״̬��ѯ������Ϣ<br>
	 * @param drawType ������� 0:��������
	 * @param drawstatus ����״̬ 0Ĭ�������ύ״̬,1����ͨ��,2���벻ͨ��
	 * @return  List<DrawMoneyDomain>
	 * @throws LotteryException
	 */
	public List<DrawMoneyDomain> queryDrawMoneyInfo(List<Integer> drawType,List<Integer> drawstatus) throws LotteryException;
	/**
	 * 
	 * Title: queryAdjustAccountUserInfo<br>
	 * Description: <br>
	 *              <br>��ѯ���Ե�����ȵ��û���Ϣ
	 * @param userIdentify
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<UserAccountModel> queryAdjustAccountUserInfo(String userIdentify,String userName,String areaCode,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: queryAdjustAccountUserCount<br>
	 * Description: <br>
	 *              <br>��ѯ���Ե�����ȵ��û���Ϣͳ��
	 * @param userIdentify
	 * @return
	 * @throws LotteryException
	 */
	public int queryAdjustAccountUserCount(String userIdentify,String userName,String areaCode) throws LotteryException;
	/**
	 * 
	 * Title: queryDrawMoneyInfoForUpdate<br>
	 * Description: <br>
	 *            ��ȡ������Ϣ�������ü�¼<br>
	 * @param drawId
	 * @return DrawMoneyDomain
	 * @throws LotteryException
	 */
	public DrawMoneyDomain queryDrawMoneyInfoForUpdate(String drawId) throws LotteryException;
	
	/**
	 * ��ѯ�û����ּ�¼�����ڿ�ʼʱ����ȡ���ڵ��ڵ���0ʱ0��0�룬���ڽ���ʱ����ȡС�ڵ��ڵ��� 23:59:59
	 * @param userId
	 *		�û�id 
	 * @param startDate
	 * 		��ʼʱ�䣬ֻ�������գ����û�п�ʼʱ������д null 
	 * @param endDate
	 * 		����ʱ�䣬���û�н���ʱ������д null 
	 * @param drawStatus
	 * 		��ѯ������д-1������״̬���б����£�
	 *          -1 - ȫ��
	 * 			0 - ������
	 * 			1 - ��ͨ��
	 * 			2 - �Ѿܾ�
	 * @param start
	 * @param count
	 * @return
	 * 		���ز�ѯ���Ľ��ױ䶯��
	 * 		���еı��β�ѯ��������������ͨ�� drawstatus + drawmoney + procedurefee����ó���
	 * 		Ҳ��ͨ��drawid�ֶεó�������drawid�ֶ�ΪString���ͣ���Ҫ����ת��Ϊint
	 * 		���б���indexΪ0��DrawMoneyDomain�������ֶ��д�Ų�ͬ��ֵ��
	 * 			drawstatus�����ֳɹ�������
	 * 			userId�����ֳɹ����ܽ��
	 * 			drawmoney�����ֱ��ܾ���������
	 * 			drawtype�����ֱ��ܾ��Ľ��
	 * 			procedurefee�����ִ����������
	 * 			operatetype�����ִ�����Ľ��
	 * 			drawid����ŵ�Ϊ���β�ѯ��������������û�в�ѯ��Ҳ�ɻ�ø�ֵΪ0; �ر�Ҫע����ֶ�����ΪString����Ҫ����ת��Ϊint
	 * @throws LotteryException
	 */
	public List<DrawMoneyDomain> getUserDrawMoney(long userId, Date startDate, Date endDate, int drawStatus, int start, int count) throws LotteryException;
	
}
