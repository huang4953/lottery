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
		System.out.println("����URL�󶨼��...");
	
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
						// from = "Դվ";
					} else {
						remoteIp = remoteIp.split("\\,")[0];
						// from = "����";
					}
				} else {
					// from = "����";
				}

			} catch (Exception e) {
				e.printStackTrace();
				remoteIp = request.getRemoteAddr();
			}

			// System.out.println(from+"-->ʱ��:"+DateTools.getNowDateYYYY_MM_DD_HH_MM_SS()+"������:"+remoteIp+"
			// ·��"+forwardUrl+"��ʶ:"+times);
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
		 * System.out.println("��ʶ��:"+times+"����ǰ���ʺ�:"+beforeAccount+",IP:"+beforeIp); }
		 */
		return invocation.invoke();
	}

}
