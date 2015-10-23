package com.wangge.app.server.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DataSourceConfig {

	/**
	 * 注入阿里的DruidDataSource
	 * 
	 * @return
	 */
	@Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
	@Primary
	@ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
	public DataSource dataSource() {
		return new DruidDataSource();
	}

}
