package com.wangge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.wangge.app.server.entity.User;
import com.wangge.app.server.repository.UserRepository;

@SpringBootApplication
public class AppServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppServerApplication.class, args);
	}

	@Component
	static class Runner implements CommandLineRunner {
		@Autowired
		private UserRepository userRepository;

		@Override
		public void run(String... args) throws Exception {
			User user=new User();
			user.setPassword("zhangsan");
			user.setHmacKey("11111111");
			user.setUsername("zhangsan");
			userRepository.save(user);
		}
	}
}
