package com.wangge.app.server.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping(value = "/v1/saleman")
public class SalesmanController {

	private static final Logger logger = Logger
			.getLogger(SalesmanController.class);

	@RequestMapping(value = "/{username}/password", method = RequestMethod.PUT)
	public ResponseEntity<Void> changePassword(@PathVariable("username") String username,@RequestBody JSONObject json) {
		String oldPassword=json.getString("oldPassword");
		String planPassword=json.getString("planPassword");
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
	}
}
