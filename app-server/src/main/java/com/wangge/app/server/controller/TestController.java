package com.wangge.app.server.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@RequestMapping(value = "/test")
	public String test(String test,@RequestBody String content) {
		System.out.println("test:"+test+"  "+content);
		return test;
	}
}
