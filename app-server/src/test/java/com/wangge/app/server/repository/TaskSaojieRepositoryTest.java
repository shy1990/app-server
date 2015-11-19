package com.wangge.app.server.repository;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.Saojie.SaojieStatus;
import com.wangge.common.repository.RegionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class TaskSaojieRepositoryTest {
	@Autowired
	private SaojieRepository tsr;
	@Autowired
	private SalesmanRepository sr;
	@Autowired
	private RegionRepository rr;

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

	}

}
