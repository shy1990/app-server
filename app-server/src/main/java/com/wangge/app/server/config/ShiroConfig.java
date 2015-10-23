package com.wangge.app.server.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import com.wangge.app.server.shiro.realm.SalesmanHmacReaml;
import com.wangge.security.shiro.web.filter.authc.HmacAuthcFilter;

@Configuration
@Profile("prod")
public class ShiroConfig {


	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setLoginUrl("/login");
		shiroFilter.setSuccessUrl("/");
		shiroFilter.setUnauthorizedUrl("/forbidden");

		Map<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("hmacAuthc", new HmacAuthcFilter());
		filters.put("rest", new NoSessionCreationFilter());
		filters.put("anon", new AnonymousFilter());
		shiroFilter.setFilters(filters);

		Map<String, String> filterChainDefinitionMapping = new LinkedHashMap<String, String>();
		
		filterChainDefinitionMapping.put("/login", "anon");
		filterChainDefinitionMapping.put("/**", "rest,hmacAuthc");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
		shiroFilter.setSecurityManager(securityManager());
		return shiroFilter;
	}

	@Bean(name = "securityManager")
	public org.apache.shiro.mgt.SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(salesmanRealm());
		return securityManager;
	}
	
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor", "salesmanRepository" })
	public SalesmanHmacReaml salesmanRealm() {
		return new SalesmanHmacReaml();
	}
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

}
