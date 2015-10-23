package com.wangge.app.server.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@Autowired
	
	@RequestMapping(value = "/test")
	public ResponseEntity<String> test(String test) {
		System.out.println("test:"+test);
		JSONObject obj=new JSONObject("{\"name\":\"xiaoming\"}");
		System.out.println(new ResponseEntity<JSONObject>(obj, HttpStatus.OK));
		String jsonStr="{\"name\":\"xiaoming\"}";
		return new ResponseEntity<String>(jsonStr, HttpStatus.OK);
	}
}
