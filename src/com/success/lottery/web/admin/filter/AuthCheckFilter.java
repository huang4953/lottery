package com.success.lottery.web.admin.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.admin.user.domain.AdminUser;



public class AuthCheckFilter implements Filter{

	private String defaultLoginPage; 
	private Log logger = LogFactory.getLog(AuthCheckFilter.class.getName());
	private List<String> excludes = new ArrayList<String>(); 

	@Override
	public void init(FilterConfig filterConfig) throws ServletException{
		defaultLoginPage = filterConfig.getInitParameter("LoginPage");
		if(StringUtils.isBlank(defaultLoginPage)){
			defaultLoginPage = "adminlogin.jhtml";
		}
		String excludeUrl = filterConfig.getInitParameter("ExcluderUrl");
		if(!StringUtils.isBlank(excludeUrl)){
			for(String s : excludeUrl.split(",")){
				excludes.add(s.trim());
			}
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException{
		HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) resp;	    
	    HttpSession session = request.getSession(true);
		String loginPage = request.getContextPath() + "/" + defaultLoginPage;
		String url = request.getRequestURL().toString();
	
		boolean isExcluded = false;
		for(String exclude : excludes){
			if(url.indexOf(exclude) > -1){
				isExcluded = true;
				break;
			}
		}
		
		if(!isExcluded){
			AdminUser user = (AdminUser)session.getAttribute("tlt.loginuser");
			if(user == null){
				response.sendRedirect(loginPage);
	        	return;
			}else{
				if(!user.isAdministrator() && !user.isAccessible(url)){
					//转向一个没有访问权限的提示页面
					response.sendRedirect(loginPage);
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy(){
	}

	
//	private String getRedirectURL(HttpServletRequest request, String loginPage, String optionalParams) {
//		StringBuilder buf = new StringBuilder();
//		try {
//			buf.append(request.getRequestURI());
//			String qs = request.getQueryString();
//			if (qs != null) {
//				buf.append("?").append(qs);
//			}
//		} catch (Exception e) {
//		}
//		try {
//			String url = loginPage + "?url=" + URLEncoder.encode(buf.toString(), "ISO-8859-1") + (optionalParams != null ? "&" + optionalParams : "");
//			return url;
//		} catch (Exception e) {
//			return null;
//		}
//	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
