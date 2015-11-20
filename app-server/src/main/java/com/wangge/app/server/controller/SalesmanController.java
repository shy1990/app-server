package com.wangge.app.server.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.service.SalesmanService;

@RestController
@RequestMapping(value = "/v1/saleman")
public class SalesmanController {

	@Resource
	private SalesmanService salesmanService;

	@RequestMapping(value = "/findSalesman", method = RequestMethod.POST)
	public ResponseEntity<List<Salesman>> findSalesman() {
		List<Salesman> listSalesman=salesmanService.findSalesman();
			
	 return new ResponseEntity<List<Salesman>>(listSalesman,HttpStatus.OK);
	
}
}
