package com.wangge.app.server.repository;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.Salesman;
import com.wangge.common.repository.RegionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class SalesmanRepositoryTest {
	@Autowired
	private SalesmanRepository sr;
	@Autowired
	private RegionRepository rr;
	@Test
	public void testAdd() {
		Salesman entity=new Salesman("2", "yewu01", "yewu01", rr.findOne("101"));
		sr.save(entity);
	}

}
