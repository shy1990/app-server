package com.wangge.app.server.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.SaojieData;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class SaojieDateRepositoryTest {
	@Autowired
	private SaojieDateRepository sdr;
	@Test
	public void testAdd() {
		SaojieData entity=new SaojieData("张三店", "123,5445");
		entity.setTaskId("1");
		entity.setRemark("fuck it");
		sdr.save(entity);
	}

}
