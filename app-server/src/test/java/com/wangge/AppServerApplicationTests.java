/*
package com.wangge;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.wangge.app.server.entity.WaterOrderCash;
import com.wangge.app.server.repository.WaterOrderCashRepository;
import com.wangge.app.server.service.CashService;
import com.wangge.app.server.service.WaterOrderService;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate.HttpClientOption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
//git.oschina.net/sylarlove/app-server.git

import com.wangge.app.server.monthTask.repository.MonthTaskSubRepository;
import com.wangge.app.server.repository.OrderSignforRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class AppServerApplicationTests {
	@Autowired
//	private RegionRepository rp;
	MonthTaskSubRepository montRep;
	@Autowired
	OrderSignforRepository orderRep;
	@Autowired
	CashService cashService;
	@Autowired
	WaterOrderCashRepository waterOrderCashRepository;
	@Test
	public void contextLoads() {
//		RestTemplate rt = new HmacRestTemplet("zhangsan", "zhangsan", HttpClientOption.ENABLE_REDIRECTS);
//		ResponseEntity obj = rt.getForEntity("http://localhost:8080/test?test={test}", JSONObject.class, "hello");
//		System.out.println(obj.getBody().toString());

	}
	
	@Test  
    public void testNativeQuery1() {  
		  EntityManagerFactory emf = null;
	        EntityManager em = emf.createEntityManager();   
	        //定义SQL   
	        String sql = "SELECT * FROM SJZAIXIAN.SJ_TB_ADMIN";   
	        //创建原生SQL查询QUERY实例   
	        Query query =  (Query) em.createNativeQuery(sql); 
	        //执行查询，返回的是对象数组(Object[])列表,   
	        //每一个对象数组存的是相应的实体属性   
	        List<Object> objecArraytList = ((javax.persistence.Query) query).getResultList();   
	        for(int i=0;i<objecArraytList.size();i++) {   
	            Object[] obj = (Object[]) objecArraytList.get(i);   
	           //使用obj[0],obj[1],obj[2]...取出属性　
	            System.out.println(obj);        
	        }   
	        em.close();   
	    }
  @Test
  public void testT(){
//    Object  daycount=  orderRep.countByuserAndDay("A37172304120", "2016-07-14",1);
//   System.out.println(daycount.toString());
//   Object monthcount=  orderRep.countByuserAndMonth("A37172304120", "2016-07");
//   System.out.println(monthcount.toString());
  }
  @Test
  public void test1(){
	  WaterOrderCash waterOrderCash = waterOrderCashRepository.findOne("DL1477448929086");
	  cashService.pushWaterOrderToMall(waterOrderCash);
  }
}
*/
