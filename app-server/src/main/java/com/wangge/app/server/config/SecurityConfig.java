package com.wangge.app.server.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("prod")
public class SecurityConfig {

	@Bean(name = "shiroFilter")
	@DependsOn("securityManager")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setLoginUrl("/login");
		shiroFilter.setSuccessUrl("/");
		shiroFilter.setUnauthorizedUrl("/forbidden");

		Map<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("authc", new FormAuthenticationFilter());
		filters.put("rest", new NoSessionCreationFilter());
		filters.put("anon", new AnonymousFilter());
		shiroFilter.setFilters(filters);

		Map<String, String> filterChainDefinitionMapping = new LinkedHashMap<String, String>();

		filterChainDefinitionMapping.put("/login", "anon");
		filterChainDefinitionMapping.put("/**", "rest,hmacAuthc");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
		shiroFilter.setSecurityManager(securityManager);
		return shiroFilter;
	}

}
