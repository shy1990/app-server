package com.wangge.app.server.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.Region;
import com.wangge.app.server.repository.SalesmanRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class RegionServiceTest {
	@Autowired
	private RegionService rs;
	@Autowired
	private SalesmanRepository sr;
	@Test
	public void testGetSaojie() {
		
		Map<String, List<Region>> saojie = rs.getSaojie(sr.findOne("10001"));
		System.out.println(saojie);
	}

}
