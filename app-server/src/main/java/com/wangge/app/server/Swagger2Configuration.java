package com.wangge.app.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 描述:生成应用接口文档
 * 包名: com.wangge.app.
 * 作者: barton.
 * 日期: 16-7-21.
 * 项目名称: app-interface
 * 版本: 1.0
 * JDK: since 1.8
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class Swagger2Configuration {
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
     .title("APP-SERVER应用接口文档")
     .description("APP-SERVER应用接口文档 1.0版本")
     .version("1.0")
     .build();
  }

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
     .apiInfo(apiInfo())
     .select()
     .apis(RequestHandlerSelectors.basePackage("com.wangge.app"))
     .paths(PathSelectors.any())
     .build();
  }
}
