package com.wangge.app.server.repository;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.common.entity.Region;
import com.wangge.common.repository.RegionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class RegionRepositoryTest {
	@Autowired
	private RegionRepository rr;

	@Test
	public void testAdd() {
		Region entity = new Region("100", "钓鱼岛",Region.RegionType.CITY);
		entity.setCoordinates(
				"125.681496-42.913345=125.67182-42.90182=125.66952-42.885338=125.67182-42.869267=125.681496-42.854794=125.70459-42.85108=125.72654-42.854794=125.74253-42.864193=125.74368-42.88322=125.73966-42.89929=125.72654-42.913345=125.70689-42.922108=");
		entity.setParent(rr.getOne("0"));
		rr.save(entity);
	}

	@Test
	@Transactional
	public void testFind() {
		Region entity = rr.findOne("0");
		System.out.println(entity.getName()+ entity.getType().getName());
		Collection<Region> children = entity.getChildren();
		for (Region region : children) {
			System.out.println(region.getName()+region.getType().getName());
		}
	}
//	@Test
//	@Transactional
//	public void testFindMaxId() {
//		Region entity = rr.findOne("0");
//		String maxId = rr.findMaxIdByParent(entity);
//		System.out.println(maxId);
//	}

}
