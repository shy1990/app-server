package com.wangge.app.server.repository;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.TaskSaojie;
import com.wangge.app.server.entity.TaskTarget;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class TaskSaojieRepositoryTest {
	@Autowired
	private TaskSaojieRepository tsr;
	@Autowired
	private SalesmanRepository sr;
	@Autowired
	private SalesmanManagerRepository smr;
	@Autowired
	private RegionRepository rr;

	@Test
	public void testAdd() {
		TaskSaojie entity = new TaskSaojie();
		entity.setDescription("扫街吧骚222年");
		entity.setEndTime(new Date());
		entity.setName("扫接任务1");
		entity.setSalesman(sr.findByUsername("yewu01"));
		entity.setCreateBy(smr.findByUsername("yewum01"));
		entity.setRegions(Lists.newArrayList(rr.getOne("3")));
		entity.setTargets(Lists.newArrayList(new TaskTarget("最小数量",true,20.00f)));
		TaskSaojie save = tsr.save(entity);
		
	}
	@Test
	public void testDel() {
	tsr.delete("ff80818150d045c50150d045c94f0000");
		
	}

}
