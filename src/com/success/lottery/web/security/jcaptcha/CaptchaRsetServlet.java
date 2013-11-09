package com.success.lottery.web.security.jcaptcha;

import net.sf.json.cache.rege.JsonCacheUtil;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
@Controller
public class CaptchaRsetServlet  implements ApplicationListener<ApplicationEvent> {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof ContextRefreshedEvent){
			JsonCacheUtil.APPLICATIONINIT(false,ServletActionContext.getServletContext().getRealPath("/"));
		}
	}

}
