package com.wangge.app.server.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1/member")
public class RegistController {
	
	@RequestMapping(value = "/findRegistMap/{region_id}")
	public ResponseEntity<String> test(@PathVariable String region_id) {
		String jsonStr="aa";
		return new ResponseEntity<String>(jsonStr, HttpStatus.OK);
	}
}
