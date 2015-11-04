package com.wangge.app.server.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.Salesman;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class SalesmanRepositoryTest {
	@Autowired
	private SalesmanRepository sr;
	@Autowired
	private RegionRepository rr;

	@Test
	public void testAdd() {
		Salesman entity=new Salesman();
		entity.setRegion(rr.findOne("1"));
		entity.setId("10001");
		entity.setPassword("123456");
		entity.setPhone("18615696354");
		entity.setUsername("yewu01");
		sr.save(entity);
	}

}
