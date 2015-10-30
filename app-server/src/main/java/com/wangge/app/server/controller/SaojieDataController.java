package com.wangge.app.server.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.wangge.app.server.entity.SaojieData;

@RestController
@RequestMapping(value = "/v1")
public class SaojieDataController {

	private static final Logger logger = Logger
			.getLogger(SaojieDataController.class);
	/**
	 * 获取指定业务扫街数据
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/{username}/saojie_data",method=RequestMethod.GET)
	public ResponseEntity<List<SaojieData>> list(@PathVariable("username") String username){
		logger.debug("username:"+username);
		
		SaojieData d1=new SaojieData("张三通讯", "12345,56789");
		d1.setId(1L);
		d1.setImageUrl("http://image.3j1688.com/1.jpg");
		d1.setRemark("asdfasdf");
		SaojieData d2=new SaojieData("张三1通讯", "124452,56789");
		d2.setId(1L);
		d2.setImageUrl("http://image.3j1688.com/1.jpg");
		d2.setRemark("asdfasdf");
		SaojieData d3=new SaojieData("张三2通讯", "126545,555789");
		d3.setId(1L);
		d3.setImageUrl("http://image.3j1688.com/1.jpg");
		d3.setRemark("asdfasdf");
		return new ResponseEntity<List<SaojieData>>(Lists.newArrayList(d1,d2,d3),HttpStatus.OK);
	}
	

	@RequestMapping(value="/{username}/saojie_data",method=RequestMethod.POST)
	public ResponseEntity<SaojieData> add(@PathVariable("username") String username,@RequestBody JSONObject json){
		String name =json.getString("name");
		String remark =json.getString("remark");
		String coordinate =json.getString("coordinate");
		String imageUrl=null;
		if (json.containsKey("imageUrl")) {
			imageUrl=json.getString("imageUrl");
		}
		SaojieData data=new SaojieData(name, coordinate);
		data.setRemark(remark);
		data.setImageUrl(imageUrl);
		data.setId(1L);
		return new ResponseEntity<SaojieData>(data,HttpStatus.CREATED);
	}
}
