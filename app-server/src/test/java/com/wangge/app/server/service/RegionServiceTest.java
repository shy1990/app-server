package com.wangge.app.server.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.repository.SaojieDataRepository;
import com.wangge.common.entity.Region;
import com.wangge.common.repository.RegionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class RegionServiceTest {
	@Autowired
	private RegionService rs;
	@Autowired
	private SalesmanRepository sr;
	@Autowired
	private DataSaojieService ds;
	@Autowired
	private RegionRepository rt;
	@Resource
	private SaojieDataRepository sdr;
	@Test
	public void testGetSaojie() {
		
		Map<String, List<Region>> saojie = rs.getSaojie(sr.findOne("10001"));
		System.out.println(saojie);
	}
	
	@Test
	public void findRegionByid() {
//		
//		List<SaojieData> Data = ds
//				.getSaojieDataByregion(rt.findOne("37010501"));
//		System.out.println(Data);
	}

	
	public void testDataSaojie(){
		long id = 5L;
		//SaojieData sjd = ds.getSaojieDataById(6);
		SaojieData sjd2 = sdr.findOne(id);
		System.out.println("name=============="+sjd2.getName());
	}
}
