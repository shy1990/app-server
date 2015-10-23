package com.wangge.app.server.shiro.realm;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.common.entity.User;
import com.wangge.security.shiro.realm.HmacRealm;

public class SalesmanHmacReaml extends HmacRealm {
	@Autowired
	private SalesmanRepository salesmanRespository;

	@Override
	protected User getUser(String username) {
		return salesmanRespository.findByUsername(username);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO 授权
		return null;
	}
	

}
