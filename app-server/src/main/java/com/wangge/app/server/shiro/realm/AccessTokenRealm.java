package com.wangge.app.server.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.wangge.app.server.entity.Token;
import com.wangge.app.server.repository.TokenRepository;
import com.wangge.security.shiro.exception.AccessTokenInvalidException;
import com.wangge.security.shiro.web.filter.authc.AccessToken;

public class AccessTokenRealm extends AuthorizingRealm {
	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof AccessToken;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		AccessToken upToken = (AccessToken) token;
		String tokenStr = upToken.getToken();
		Token accessToken = tokenRepository.findByAccessToken(tokenStr);
		
		if (accessToken == null) {
			throw new AccessTokenInvalidException("access token [" + accessToken + "] is not found.");
		}
		return new SimpleAuthenticationInfo(accessToken.getUser(), accessToken.getAccessToken(), getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//TODO 用户授权
		return null;
	}

}
