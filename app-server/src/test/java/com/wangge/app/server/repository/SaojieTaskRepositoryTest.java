package com.wangge.app.server.repository;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.SaojieTask;
import com.wangge.common.entity.Task;
import com.wangge.common.repository.RegionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class SaojieTaskRepositoryTest {
	@Autowired
	private SaojieTaskRepository str;

	@Autowired
	private SalesmanRepository sr;
	@Autowired
	private RegionRepository rr;

	@Test
	public void testAdd() {
		Salesman sl = sr.findByUsername("yewu01");
		SaojieTask entity = new SaojieTask(sl);
		entity.setDescription("扫街任务阿伟");
		entity.setEndTime(new Date());
		entity.setName("扫接任务");
		entity.setStatus(Task.TaskStatus.NOSTART);
		entity.setRegions(rr.findAll(Lists.newArrayList("100", "101")));
		str.saveAndFlush(entity);
	}

	@Test
	public void testQuery() {
		SaojieTask st = str.findOne(1250L);
		System.out.println(st);
	}
}
