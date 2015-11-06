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
import com.wangge.app.server.entity.Region;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.service.RegionService;

/**
 * 
 * @author SongBaozhen
 *
 */
@RestController
@RequestMapping(value = "/v1")
public class RegionController {

	private static final Logger logger = Logger
			.getLogger(RegionController.class);
	@Resource
	private RegionService regionService;
	/**
	 * 业务人员区域信息
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/{id}/regions",method=RequestMethod.GET)
	public ResponseEntity<Map<String,List<Region>>> salesmanRegions(@PathVariable("id")Salesman salesman){
		logger.debug("username:"+salesman);
	     Map<String, List<Region>>   regionMap = regionService.getSaojie(salesman);
	   
		return new ResponseEntity<Map<String,List<Region>>>(regionMap,HttpStatus.OK);
	}
}
