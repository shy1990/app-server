package com.wangge.app.server.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.SalesmanManager;
import com.wangge.app.server.service.SalesmanManagerService;

@RestController
@RequestMapping(value = "/v1/salemanManager")
public class SalesmanManagerController {

//	private static final Logger logger = Logger
//			.getLogger(SalesmanManagerController.class);
//	@Resource
//	private SalesmanManagerService salesmanManagerService;
//
//	@RequestMapping(value = "/findSalesmanManager", method = RequestMethod.POST)
//	public ResponseEntity<SalesmanManager> findSalesmanManager(String userName) {
//		SalesmanManager sa = salesmanManagerService.findByUsername(userName);
//		return new ResponseEntity<SalesmanManager>(sa,HttpStatus.OK);
//	}
}
