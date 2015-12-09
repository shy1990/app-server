package com.wangge.app.server.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.service.RegistService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.vo.RegionVo;

@RestController
@RequestMapping(value = "/v1")
public class RegistController {
	
	private static final Logger logger = Logger.getLogger(RegistController.class);
	
	@Resource
	private SalesmanService salesmanService;
	@Resource
	private RegistService registService;
	/**
	 * 
	 * @Description: 业务人员注册区域
	 * @param @param salesman
	 * @return ResponseEntity<Map<String,List<RegionVo>>>
	 * @author peter
	 * @date 2015年12月1日
	 * @version V2.0
	 */
	@RequestMapping(value="/{id}/regist",method=RequestMethod.GET)
	public ResponseEntity<Map<String,List<RegionVo>>> salesmanRegions(@PathVariable("id") Salesman salesman){
		logger.debug("username:"+salesman);
		
	     Map<String, List<RegionVo>>   regionMap = registService.getRegistRegion(salesman);
	   
		return new ResponseEntity<Map<String,List<RegionVo>>>(regionMap,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @Description: 查询开发数量
	 * @param @param salesman
	 * @param @return
	 * @return ResponseEntity<Map<String,Integer>>
	 * @author peter
	 * @date 2015年12月2日
	 * @version V2.0
	 */
	@RequestMapping(value="/{id}/registNum",method=RequestMethod.GET)
	public ResponseEntity<Map<String,Integer>> registNum(@PathVariable("id") Salesman salesman){
		logger.debug("username:"+salesman);
		
	     Map<String, Integer>   regionMap = registService.getRegistNum(salesman);
	   
		return new ResponseEntity<Map<String,Integer>>(regionMap,HttpStatus.OK);
	}
	
}
