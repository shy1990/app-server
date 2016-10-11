package com.wangge.app.server.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.ChildAccount;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.Saojie.SaojieStatus;
import com.wangge.app.server.service.OrderSignforService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class TaskSaojieRepositoryTest {
	@Autowired
	private SaojieRepository tsr;
	@Autowired
	private SalesmanRepository sr;
	@Autowired
	private RegionRepository rr;
	@Resource
	private ChildAccountRepostory cr;
	@Resource
	private OrderSignforRepository orderSignforR;

	@Test
	public void testAdd() {
		Saojie entity = new Saojie();
		entity.setDescription("扫街");
		entity.setExpiredTime(new Date());
		entity.setName("钓鱼岛");
		entity.setSalesman(sr.findByUserUsername("yewu01"));
		entity.setRegion(rr.getOne("100"));
		entity.setBeginTime(new Date());
		entity.setMinValue(10);
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

	}

	@Test
	public void testDel() {
	  ChildAccount C = cr.findBySimId("460028545000000");
	  
	  if(C != null){
	    System.out.println("================="+C.getTruename());
	  }
	}
	@Test
	public void testOrderSignfor(){
		Page<OrderSignfor>	o =	orderSignforR.findByUserIdAndCreatTime("A37018707220",2,1,new PageRequest(0,10,new Sort(Direction.DESC, "id")));
	
		
		System.out.println("===================>>"+o.getContent().toString());
	}
	public static void main(String[] args) {
		Float f = 10.00f;
		BigDecimal b = new BigDecimal("20.00");
		System.out.println("f======="+f);
		System.out.println("b====="+b);
		
	}

}
