package com.wangge.app.server.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.repositoryimpl.OrderSignforRepositoryImpl;

/**
 * 
 * @author SongBaozhen
 *
 */
@RestController
@RequestMapping(value = "/v1")
public class importExcelController {

	private static final Logger logger = Logger
			.getLogger(importExcelController.class);
	@Autowired
  private OrderSignforRepositoryImpl osr;
	/**
	 * 
	 * 功能: 查询区域
	 * 详细： 	
	 * 作者： 	jiabin
	 * 版本：  1.0
	 * 日期：  2015年11月6日下午1:48:34
	 *
	 */
	@RequestMapping(value="/import/updateOrderSignforByOrderNo",method=RequestMethod.POST)
	public ResponseEntity<String> updateOrderSignforByOrderNo(String fastmailno,String orderno,String fastmailTime){
	  osr.updateOrderSignforByOrderNo(fastmailno,orderno,fastmailTime);
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
}

