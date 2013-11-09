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
	 * 获得角色名称
	 * @param roleId
	 * 		指定的角色id
	 * @return
	 * 		查找不到返回null
	 */
	public static String getRoleName(String roleId){
		return AutoProperties.getString(roleId + ".name", null, resource);
	}
	
	/**
	 * 获得权限名称
	 * @param privilegeId
	 * 		指定的权限id
	 * @return
	 * 		查找不到返回null
	 */
	public static String getPrivilegeName(String privilegeId){
		return AutoProperties.getString(privilegeId + ".name", null, resource);
	}
	
	/**
	 * 获得权限Title名称
	 * @param titleId
	 * 		权限TitleId
	 * @return
	 * 		查找不到返回null
	 */
	public static String getPrivilegeTitleName(String titleId){
		return AutoProperties.getString(titleId + ".name", null, resource);
	}
	
	/**
	 * 获得权限TitleId
	 * @param privilegeId
	 * 		制定的权限Id 
	 * @return
	 * 		查找不到返回null
	 */
	public static String getPrivilegeTitleId(String privilegeId){
		return AutoProperties.getString(privilegeId + ".titleId", null, resource);
	}
	
	/**
	 * 获得权限的涉及的相对Url
	 * @param privilegeId
	 * 		制定的权限Id 
	 * @return
	 * 		查找不到返回null
	 */
	public static String getPrivilegeRelativeUrl(String privilegeId){
		return AutoProperties.getString(privilegeId + ".relativeUrl", null, resource);
	}

	/**
	 * 获得权限涉及的访问关键字
	 * @param privilegeId
	 * 		制定的权限Id 
	 * @return
	 * 		查找不到返回null
	 */
	public static String getPrivilegeAccessKey(String privilegeId){
		return AutoProperties.getString(privilegeId + ".accessKey", null, resource);
	}

	
	/**
	 * 权限Title是否启用
	 * @param titleId
	 * 		指定的权限TitleId
	 * @return
	 * 		只有当配置titleId.enable=true时返回 true, 否则返回false
	 */
	public static boolean isEnablePrivilegeTitle(String titleId){
		return "true".equals(AutoProperties.getString(titleId + ".enabled", "false", resource).trim());
	}

	/**
	 * 权限是否启用
	 * @param privilegeId
	 * 		指定的权限privilegeId
	 * @return
	 * 		只有当配置privilegeId.enable=true时返回 true, 否则返回false
	 */
	public static boolean isEnablePrivilege(String privilegeId){
		return "true".equals(AutoProperties.getString(privilegeId + ".enabled", "false", resource).trim());
	}

	/**
	 * 权限是否显示在Title下
	 * @param privilegeId
	 * 		指定的权限privilegeId
	 * @return
	 * 		只有当配置privilegeId.viewInMenu=true时返回 true, 否则返回false
	 */
	public static boolean isViewInMenu(String privilegeId){
		return "true".equals(AutoProperties.getString(privilegeId + ".viewInMenu", "false", resource).trim());
	}

	public static boolean isInTitle(String privilegeId, String titleId){
		return getPrivilegeTitleId(privilegeId) != null && getPrivilegeTitleId(privilegeId).equals(titleId);
	}
	
	/**
	 * 返回所有角色信息，角色列表信息在资源文件的RoleList字段中
	 * @return
	 * 		Map<roleId, roleName>: Map<角色Id, 角色名称>
	 * 		查找不到返回null
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
	 * 返回所有权限Title信息，角色列表信息在资源文件的TitleList字段中
	 * @return
	 * 		Map<titleId, titleName>: Map<TitleId, Title名称>
	 * 		查找不到返回null
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
	 * 返回指定TitleId的所有权限列表
	 * @param titleId
	 * 		指定的TitleId
	 * @return
	 * 		查找不到返回null
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
