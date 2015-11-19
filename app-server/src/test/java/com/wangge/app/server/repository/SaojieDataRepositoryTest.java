package com.wangge.app.server.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.SaojieData;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class SaojieDataRepositoryTest {
	@Autowired
	private SaojieDataRepository sdr;
	@Autowired
	private SaojieRepository sr;

	@Test
	public void testAdd() {
		SaojieData entity=new SaojieData();
		entity.setCoordinate("125.681496-42.913345");
		entity.setDescription("小伙子手机大卖场");
		entity.setImageUrl("http://img2.hao123.com/data/1_090aa9f754061e7dada9fae7e0565104_0");
		Saojie findOne = sr.findOne(2L);
		entity.setRegion(findOne.getRegion());
		entity.setSaojie(findOne);
		sdr.save(entity);
	}

	@Test
	public void testDel() {

	}

}
