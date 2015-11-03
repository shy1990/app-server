package com.wangge.app.server.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.SalesmanRepository;

@RestController
@RequestMapping(value = "/v1/saleman")
public class SalesmanController {

	private static final Logger logger = Logger
			.getLogger(SalesmanController.class);
	@Resource
	private SalesmanRepository salesmanRepository;

	@RequestMapping(value = "/{username}/password", method = RequestMethod.PUT)
	public ResponseEntity<Json> changePassword(@PathVariable("username") String username,@RequestBody JSONObject jsons) {
		String oldPassword=jsons.getString("oldPassword");
		String planPassword=jsons.getString("planPassword");
		Json json = new Json();
		Salesman sa = salesmanRepository.findByUsernameAndPassword(username, oldPassword);
		if(sa!= null && !"".equals(sa)){
			sa.setPassword(planPassword);
			salesmanRepository.save(sa);
			json.setMsg("修改成功！");
			return new ResponseEntity<Json>(json,HttpStatus.OK);
		}else{
			json.setMsg("修改失败！");
			return new ResponseEntity<Json>(json,HttpStatus.UNAUTHORIZED);
		}
		
		
		
		
		
	}
}
