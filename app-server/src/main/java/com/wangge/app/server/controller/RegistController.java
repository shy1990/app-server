package com.wangge.app.server.controller;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.UserMember;
import com.wangge.app.server.service.RegistService;

@RestController
@RequestMapping(value="/v1")
public class RegistController {
	private static final Logger logger = Logger
			.getLogger(RegistController.class);
	
	@Autowired
	private RegistService registService;
	
	/*@RequestMapping(value = "/findRegistMap/{region_id}")
	public ResponseEntity<Map<String,List<?>>> test(@PathVariable String region_id) {

		logger.debug("region_id"+region_id);
		
		UserMember um= 
		return new ResponseEntity<String>(jsonStr, HttpStatus.OK);
	}
	
	*//**
	 * 业务人员区域信息
	 * @param username
	 * @return
	 *//*
	@RequestMapping(value="/{username}/regions",method=RequestMethod.GET)
	public ResponseEntity<Map<String,List<Region>>> salesmanRegions(@PathVariable("username") String username){
		logger.debug("username:"+username);
		
		Region r1=new CustomRegion("101","女儿国","123.123,87.565^123.123,87.565^123.123,87.565");
		Region r2=new CustomRegion("101","男儿国","123.123,87.565^123.123,87.565^123.123,87.565");
		Map<String,List<Region>> map=new HashMap<String, List<Region>>();
		map.put("open", Lists.newArrayList(r1));
		map.put("nopen", Lists.newArrayList(r2));
		return new ResponseEntity<Map<String,List<Region>>>(map,HttpStatus.OK);
	}*/
	
	/**
	 * 店铺信息录入
	 * @param username
	 * @param json
	 * @return http
	 */
	@RequestMapping(value="/member",method=RequestMethod.POST)
	public ResponseEntity<Void> add(@RequestBody JSONObject json){
		UserMember um=new UserMember();
		um.setId(json.getString("username"));
		um.setShopName(json.getString("shopname"));
		um.setPhone(json.getString("phone"));
		um.setConsignee(json.getString("consignee"));
		um.setCounterNumber(json.getInt("counter_number"));
		registService.save(um);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
