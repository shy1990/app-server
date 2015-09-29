package com.wangge.app.server.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wangge.app.server.entity.User;
import com.wangge.app.server.repository.UserRepository;
import com.wangge.common.codec.digest.HmacUtils2;
import com.wangge.security.shiro.realm.HmacToken;

public class UserRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(UserRealm.class);
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof HmacToken;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		HmacToken upToken = (HmacToken) token;
		String username = upToken.getUsername();
		User user=userRepository.findByUsername(username);
		
		if (user == null) {
			throw new UnknownAccountException("account token [" + username + "] is not found.");
		}
		String serverDigest = HmacUtils2.hmacMd5Hex( user.getPassword(),upToken.getParams());
		if (logger.isDebugEnabled()) {
			logger.debug("serverDigest:{}",serverDigest);
		}
		return new SimpleAuthenticationInfo(user, serverDigest, getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//TODO 用户授权
		return null;
	}

}
