package com.wangge.app.server.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.CustomRegion;
import com.wangge.app.server.entity.Region;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class RegionRepositoryTest {
	@Autowired
	private RegionRepository rr;

	@Test
	public void testAdd() {
		Region entity=new CustomRegion("3","逗逼国", "125.681496-42.913345=125.67182-42.90182=125.66952-42.885338=125.67182-42.869267=125.681496-42.854794=125.70459-42.85108=125.72654-42.854794=125.74253-42.864193=125.74368-42.88322=125.73966-42.89929=125.72654-42.913345=125.70689-42.922108=");
		entity.setParent(rr.getOne("0"));
		rr.save(entity);
	}
	
	@Test
	public void testFind(){
		Region entity=rr.findOne("3");
		System.out.println(entity);
	}

}
