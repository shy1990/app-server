package com.wangge.app.server.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.wangge.app.server.entity.SaojieTask;
import com.wangge.app.server.repository.SaojieTaskRepository;

/**
 * 扫街任务
 * 
 * @author wujiming
 *
 */
@RestController
@RequestMapping(value = "/v1")
public class SaojieTaskController {

	private static final Logger logger = Logger.getLogger(SaojieTaskController.class);
	@Autowired
	private SaojieTaskRepository str;
	/**
	 * 获取指定业务扫街任务
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/{username}/saojie_task", method = RequestMethod.GET)
	public ResponseEntity<List<SaojieData>> list(@PathVariable("username") String username) {

		SaojieData d1 = new SaojieData("张三通讯", "12345,56789");
		d1.setId(1L);
		d1.setImageUrl("http://image.3j1688.com/1.jpg");
		d1.setRemark("asdfasdf");
		SaojieData d2 = new SaojieData("张三1通讯", "124452,56789");
		d2.setId(1L);
		d2.setImageUrl("http://image.3j1688.com/1.jpg");
		d2.setRemark("asdfasdf");
		SaojieData d3 = new SaojieData("张三2通讯", "126545,555789");
		d3.setId(1L);
		d3.setImageUrl("http://image.3j1688.com/1.jpg");
		d3.setRemark("asdfasdf");
		return new ResponseEntity<List<SaojieData>>(Lists.newArrayList(d1, d2, d3), HttpStatus.OK);
	}

	/**
	 * 增加扫街任务
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/saojie_task", method = RequestMethod.POST)
	public ResponseEntity<SaojieTask> add(@RequestBody JSONObject json) {
		//TODO 新增扫街任务
		return new ResponseEntity<SaojieTask>(str.findOne(1250L), HttpStatus.CREATED);
	}
}
