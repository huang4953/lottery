package com.success.lottery.web.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;

@SuppressWarnings("serial")
public class BindUrlInteceptor extends AbstractInterceptor {
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("进行URL绑定检查...");
	
		ActionContext ac = invocation.getInvocationContext();
		String name = "";
		try {
			name = ac.getName();
			// boolean isNeedBind = true;
			if (name.indexOf("login") > -1) {
				// isNeedBind = false;
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			System.out.println("Interceptor=>" + request.getSession().getId());
			if (request != null) {
				System.out.println("requestURI:" + request.getRequestURI());
			}
			// String from = "";
			String remoteIp = "";
			try {
				remoteIp = request.getHeader("Cdn-Src-Ip");

				if (remoteIp == null) {
					remoteIp = request.getHeader("x-forwarded-for");

					if (remoteIp == null) {
						remoteIp = request.getRemoteAddr();
						// from = "源站";
					} else {
						remoteIp = remoteIp.split("\\,")[0];
						// from = "帝联";
					}
				} else {
					// from = "网宿";
				}

			} catch (Exception e) {
				e.printStackTrace();
				remoteIp = request.getRemoteAddr();
			}

			// System.out.println(from+"-->时间:"+DateTools.getNowDateYYYY_MM_DD_HH_MM_SS()+"访问者:"+remoteIp+"
			// 路径"+forwardUrl+"标识:"+times);
			// baseAction.setForwardUrl(URLEncoder.encode(forwardUrl, "GBK"));
			((LotteryWebBaseActon) invocation.getAction())
					.setRemoteIp(remoteIp);
		} catch (Exception e) {
			e.printStackTrace();
			// baseAction.setForwardUrl("/");
		}

		/*
		 * MemberLoginSession userSession =
		 * (MemberLoginSession)ac.getSession().get(Constant.MEMBER_LOGIN_SESSION);
		 * String beforeAccount = ""; String beforeIp = ""; if(userSession !=
		 * null){ beforeAccount = userSession.getMember().getAccount(); beforeIp =
		 * userSession.getIp();
		 * System.out.println("标识号:"+times+"操作前，帐号:"+beforeAccount+",IP:"+beforeIp); }
		 */
		return invocation.invoke();
	}

}
