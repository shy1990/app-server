package com.wangge.app.server.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.Saojie.SaojieStatus;
import com.wangge.app.server.service.SalesmanService;

@RestController
@RequestMapping(value = "/v1")
public class SaojieController {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//
	@Resource
	private SalesmanService salesmanService;

	@RequestMapping(value = "/addSaojie", method = RequestMethod.POST)
	public ResponseEntity<String> addTask(String taskName,String salesmanid,String regionid,
			String taskStart,String taskEnd,String taskCount,String taskDes,String userName ) {
		
			Saojie entity = new Saojie();
			entity.setDescription(taskDes);
			entity.setExpiredTime(sdf.parse(taskEnd));
			entity.setName(taskName);
			entity.setSalesman(sr.findByUserUsername("yewu01"));
			entity.setRegion(rr.getOne("100"));
			entity.setBeginTime(sdf.parse(taskStart));
			entity.setMinValue(Integer.parseInt(taskCount));
			entity.setOrder(0);
			entity.setStatus(SaojieStatus.PENDING);
			tsr.save(entity);
			
			Saojie entity1 = new Saojie();
			entity1.setDescription("扫街");
			entity1.setExpiredTime(new Date());
			entity1.setName("a区");
			entity1.setSalesman(sr.findByUserUsername("yewu01"));
			entity1.setRegion(rr.getOne("100"));
			entity1.setBeginTime(new Date());
			entity1.setMinValue(3);
			entity1.setOrder(0);
			entity1.setStatus(SaojieStatus.PENDING);
			entity1.setParent(tsr.getOne(1L));
			tsr.save(entity1);
		
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
}
