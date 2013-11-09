package com.success.admin.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.utils.AutoProperties;

public class AdminUserTools{

	private static Log			logger					= LogFactory.getLog(AdminUserTools.class.getName());
	private static String		resource				= "com.success.admin.user.roleinfo";

	/**
	 * ��ý�ɫ����
	 * @param roleId
	 * 		ָ���Ľ�ɫid
	 * @return
	 * 		���Ҳ�������null
	 */
	public static String getRoleName(String roleId){
		return AutoProperties.getString(roleId + ".name", null, resource);
	}
	
	/**
	 * ���Ȩ������
	 * @param privilegeId
	 * 		ָ����Ȩ��id
	 * @return
	 * 		���Ҳ�������null
	 */
	public static String getPrivilegeName(String privilegeId){
		return AutoProperties.getString(privilegeId + ".name", null, resource);
	}
	
	/**
	 * ���Ȩ��Title����
	 * @param titleId
	 * 		Ȩ��TitleId
	 * @return
	 * 		���Ҳ�������null
	 */
	public static String getPrivilegeTitleName(String titleId){
		return AutoProperties.getString(titleId + ".name", null, resource);
	}
	
	/**
	 * ���Ȩ��TitleId
	 * @param privilegeId
	 * 		�ƶ���Ȩ��Id 
	 * @return
	 * 		���Ҳ�������null
	 */
	public static String getPrivilegeTitleId(String privilegeId){
		return AutoProperties.getString(privilegeId + ".titleId", null, resource);
	}
	
	/**
	 * ���Ȩ�޵��漰�����Url
	 * @param privilegeId
	 * 		�ƶ���Ȩ��Id 
	 * @return
	 * 		���Ҳ�������null
	 */
	public static String getPrivilegeRelativeUrl(String privilegeId){
		return AutoProperties.getString(privilegeId + ".relativeUrl", null, resource);
	}

	/**
	 * ���Ȩ���漰�ķ��ʹؼ���
	 * @param privilegeId
	 * 		�ƶ���Ȩ��Id 
	 * @return
	 * 		���Ҳ�������null
	 */
	public static String getPrivilegeAccessKey(String privilegeId){
		return AutoProperties.getString(privilegeId + ".accessKey", null, resource);
	}

	
	/**
	 * Ȩ��Title�Ƿ�����
	 * @param titleId
	 * 		ָ����Ȩ��TitleId
	 * @return
	 * 		ֻ�е�����titleId.enable=trueʱ���� true, ���򷵻�false
	 */
	public static boolean isEnablePrivilegeTitle(String titleId){
		return "true".equals(AutoProperties.getString(titleId + ".enabled", "false", resource).trim());
	}

	/**
	 * Ȩ���Ƿ�����
	 * @param privilegeId
	 * 		ָ����Ȩ��privilegeId
	 * @return
	 * 		ֻ�е�����privilegeId.enable=trueʱ���� true, ���򷵻�false
	 */
	public static boolean isEnablePrivilege(String privilegeId){
		return "true".equals(AutoProperties.getString(privilegeId + ".enabled", "false", resource).trim());
	}

	/**
	 * Ȩ���Ƿ���ʾ��Title��
	 * @param privilegeId
	 * 		ָ����Ȩ��privilegeId
	 * @return
	 * 		ֻ�е�����privilegeId.viewInMenu=trueʱ���� true, ���򷵻�false
	 */
	public static boolean isViewInMenu(String privilegeId){
		return "true".equals(AutoProperties.getString(privilegeId + ".viewInMenu", "false", resource).trim());
	}

	public static boolean isInTitle(String privilegeId, String titleId){
		return getPrivilegeTitleId(privilegeId) != null && getPrivilegeTitleId(privilegeId).equals(titleId);
	}
	
	/**
	 * �������н�ɫ��Ϣ����ɫ�б���Ϣ����Դ�ļ���RoleList�ֶ���
	 * @return
	 * 		Map<roleId, roleName>: Map<��ɫId, ��ɫ����>
	 * 		���Ҳ�������null
	 */
	public static Map<String, String> getRoles(){
		try{
			String roleStr = AutoProperties.getString("RoleList", null, resource);
			if(StringUtils.isBlank(roleStr)){
				return null;
			}
			String[] roles = roleStr.split("\\|");
			Map<String, String> roleMap = new HashMap<String, String>();
			for(String role : roles){
				if(!StringUtils.isBlank(role)){
					String s[] = role.split(",");
					if(s.length == 2){
						roleMap.put(s[0], s[1]);
					}
				}
			}
			return roleMap;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * ��������Ȩ��Title��Ϣ����ɫ�б���Ϣ����Դ�ļ���TitleList�ֶ���
	 * @return
	 * 		Map<titleId, titleName>: Map<TitleId, Title����>
	 * 		���Ҳ�������null
	 */
	public static Map<String, String> getTitles(){
		try{
			String titleStr = AutoProperties.getString("TitleList", null, resource);
			if(StringUtils.isBlank(titleStr)){
				return null;
			}
			String[] titles = titleStr.split("\\|");
			Map<String, String> titleMap = new TreeMap<String, String>();
			for(String title : titles){
				if(!StringUtils.isBlank(title)){
					String s[] = title.split(",");
					if(s.length == 2){
						titleMap.put(s[0], s[1]);
					}
				}
			}
			return titleMap;
		}catch(Exception e){
			return null;
		}		
	}
	
	/**
	 * ����ָ��TitleId������Ȩ���б�
	 * @param titleId
	 * 		ָ����TitleId
	 * @return
	 * 		���Ҳ�������null
	 */
	public static List<String> getPrivileges(String titleId){
		try{
			String privilegeListStr = AutoProperties.getString(titleId + ".PPageList", null, resource);
			if(StringUtils.isBlank(privilegeListStr)){
				return null;
			}
			String[] privileges = privilegeListStr.split(",");
			List<String> privilegeList = new ArrayList<String>();
			for(String privilege : privileges){
				if(!StringUtils.isBlank(privilege)){
					privilegeList.add(privilege);
				}
			}
			return privilegeList;
		}catch(Exception e){
			return null;
		}
	}
	
	
	public static void main(String[] args){
		System.out.println(AdminUserTools.getRoleName("R100001"));
		System.out.println(AdminUserTools.getRoleName("R000001"));
		
		System.out.println(AdminUserTools.getPrivilegeName("P100001"));
		
		System.out.println(AdminUserTools.getPrivilegeTitleName("T100001"));
		
		System.out.println(AdminUserTools.getPrivilegeTitleId("P100001"));
		
		System.out.println(AdminUserTools.getPrivilegeAccessKey("P100001"));
		
		System.out.println(AdminUserTools.getPrivilegeRelativeUrl("P100001"));
		
		System.out.println(AdminUserTools.isEnablePrivilege("P100001"));
		
		System.out.println(AdminUserTools.isEnablePrivilegeTitle("T100001"));
		
		System.out.println(AdminUserTools.isViewInMenu("P100004"));
		
		System.out.println(AdminUserTools.getRoles());
		System.out.println(AdminUserTools.getTitles());
		System.out.println(AdminUserTools.getPrivileges("T100001"));		
	}
}
