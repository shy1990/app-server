package com.wangge.app.server.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Assess;
import com.wangge.app.server.service.AssessService;

@RestController
@RequestMapping(value = "/v1/assess")
public class AssessController {
	
	@Source
	private AssessService assessService;
	private static final Logger logger = Logger.getLogger(AssessController.class);
	
	@RequestMapping(value = "/findAllAssess", method = RequestMethod.POST)
	public ResponseEntity<List<Assess>> findAllTask(String userid){
		
		List<Assess> listAssess=assessService.getAllList();
		
		return new ResponseEntity<List<Assess>>(listAssess,HttpStatus.OK);
	}
	
}
