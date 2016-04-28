package com.wangge.app.server.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.OilParameters;
import com.wangge.app.server.entity.Region;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.repository.RegionRepository;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.repository.SaojieDataRepository;
import com.wangge.app.server.repositoryimpl.ActiveImpl;
import com.wangge.app.server.repositoryimpl.RegionImpl;

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
	@Resource
	private RegionImpl regionImpl;
	@Resource
	private  OilParametersService parametersService;
	
	@Resource
  private ActiveImpl apl;
	@Test
	public void testGetSaojie() {
		
//		Map<String, List<Region>> saojie = rs.getSaojie(sr.findOne("10001"));
//		System.out.println(saojie);
	}
	
	@Test
	public void findRegionByid() {
//		
//		List<SaojieData> Data = ds
//				.getSaojieDataByregion(rt.findOne("37010501"));
//		System.out.println(Data);
	  OilParameters param = parametersService.getOilParameters("37018201");
	   System.out.println("===================="+param.getKmRatio());
	}

	
	public void testDataSaojie(){
		long id = 5L;
		//SaojieData sjd = ds.getSaojieDataById(6);
		SaojieData sjd2 = sdr.findOne(id);
		System.out.println("name=============="+sjd2.getName());
	}
	
	@Test
	public void test(){
	   Double a = 10.0;
	   Double b=15.0;
	   if(a/b > 0.4){
	   System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>===<<<<<<<<<<<<<<<<<<<<<<<<");
	   }
	}
	@Test
	public void test1(){
	 /* String str="86.64466666";  
    BigDecimal bd = new BigDecimal(Double.parseDouble(str));  
    System.out.println("++++++++++++"+bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());  
    System.out.println("=================");  
     DecimalFormat df = new DecimalFormat("#.00");   
     System.out.println(df.format(Double.parseDouble(str)));   
     System.out.println("=================");  
     System.out.println(String.format("%.2f", Double.parseDouble(str)));  
     System.out.println("=================");  
     NumberFormat nf = NumberFormat.getNumberInstance();   
     nf.setMaximumFractionDigits(2);   
     System.out.println(">>>>>>>>>>>>>>>>"+nf.format(Double.parseDouble(str))); */
	  
	  double d = 3.1465926;
	  String result = String.format("%.2f", d);
	  System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+result); 
	}
	
}
