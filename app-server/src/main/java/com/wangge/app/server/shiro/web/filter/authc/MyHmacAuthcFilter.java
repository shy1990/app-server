package com.wangge.app.server.shiro.web.filter.authc;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;

import com.wangge.app.server.entity.User;
import com.wangge.security.shiro.web.filter.authc.HmacAuthcFilter;

public class MyHmacAuthcFilter extends HmacAuthcFilter{
	@Override
	protected String getKey(ServletRequest request,ServletResponse response){
		Subject subject = getSubject(request, response);
		User user=(User)subject.getPrincipal();
		if (user!=null) {
			return user.getHmacKey();
		}
		return null;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}


}
