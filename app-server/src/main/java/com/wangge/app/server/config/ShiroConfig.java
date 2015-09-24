package com.wangge.app.server.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.wangge.app.server.shiro.realm.AccessTokenRealm;
import com.wangge.app.server.shiro.realm.UserRealm;
import com.wangge.app.server.shiro.web.filter.authc.MyHmacAuthcFilter;
import com.wangge.security.shiro.web.filter.authc.TokenAuthcFilter;

@Configuration
public class ShiroConfig {


	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setLoginUrl("/token");
		shiroFilter.setSuccessUrl("/");
		shiroFilter.setUnauthorizedUrl("/forbidden");

		Map<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("hmacAuthc", new MyHmacAuthcFilter());
		filters.put("rest", new NoSessionCreationFilter());
		filters.put("anon", new AnonymousFilter());
		filters.put("token",  new TokenAuthcFilter());
		shiroFilter.setFilters(filters);

		Map<String, String> filterChainDefinitionMapping = new LinkedHashMap<String, String>();
		
		filterChainDefinitionMapping.put("/console/**", "anon");
		filterChainDefinitionMapping.put("/token", "rest,anon");
		filterChainDefinitionMapping.put("/v1/**", "rest,token,hmacAuthc");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
		shiroFilter.setSecurityManager(securityManager());
		return shiroFilter;
	}

	@Bean(name = "securityManager")
	public org.apache.shiro.mgt.SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		Collection<Realm> realms=new ArrayList<>();
		realms.add(userRealm());
		realms.add(accessTokenRealm());
		securityManager.setRealms(realms);
		return securityManager;
	}
	
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor", "userRepository" })
	public AccessTokenRealm accessTokenRealm() {
		return new AccessTokenRealm();
	}
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor", "userRepository" })
	public UserRealm userRealm() {
		return new UserRealm();
	}
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

}
