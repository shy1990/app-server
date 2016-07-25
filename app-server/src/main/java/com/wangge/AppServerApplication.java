package com.wangge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.wangge.app.constant.AppInterface;

@EnableConfigurationProperties({AppInterface.class}) 
@SpringBootApplication
public class AppServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppServerApplication.class, args);
	}
}
