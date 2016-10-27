package com.wangge.app.server.config.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 描述: TODO:
 * 包名: com.wangge.app.server.config.
 * 作者: barton.
 * 日期: 16-7-23.
 * 项目名称: app-server
 * 版本: 1.0
 * JDK: since 1.8
 */
@Configuration
public class RestTemplateConfig {
  @Bean(name = "restTemplate")
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
