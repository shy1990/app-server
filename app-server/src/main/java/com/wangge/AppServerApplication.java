package com.wangge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.wangge.app.server.client.RestClient;
import com.wangge.app.server.entity.User;
import com.wangge.app.server.repository.UserRepository;

@SpringBootApplication
public class AppServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppServerApplication.class, args);
//		RestClient client=new RestClient();
//		client.test("hello");
	}

	@Component
	static class Runner implements CommandLineRunner {
		@Autowired
		private UserRepository userRepository;

		@Override
		public void run(String... args) throws Exception {
			User user=new User();
			user.setPassword("zhangsan");
			user.setUsername("zhangsan");
			userRepository.save(user);
		}
	}
}
