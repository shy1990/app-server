package com.wangge.app.server.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.ChildAccount;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.Receipt;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.Saojie.SaojieStatus;
import com.wangge.app.server.entity.dto.OrderDto;
import com.wangge.app.server.service.OrderSignforService;
import com.wangge.app.server.service.ReceiptService;
import com.wangge.app.server.vo.OrderVo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class TaskSaojieRepositoryTest {
	@Autowired
	private SaojieRepository tsr;
	@Autowired
	private SalesmanRepository sr;
	@Autowired
	private RegionRepository rr;
	@Resource
	private ChildAccountRepostory cr;
	@Resource
	private OrderSignforRepository orderSignforR;
	@Resource
	private ReceiptRepository receiptRepository;

	@Test
	public void testAdd() {
		Saojie entity = new Saojie();
		entity.setDescription("扫街");
		entity.setExpiredTime(new Date());
		entity.setName("钓鱼岛");
		entity.setSalesman(sr.findByUserUsername("yewu01"));
		entity.setRegion(rr.getOne("100"));
		entity.setBeginTime(new Date());
		entity.setMinValue(10);
		entity.setOrder(0);
		entity.setStatus(SaojieStatus.PENDING);
		tsr.save(entity);
		
		Saojie entity1 = new Saojie();
		entity1.setDescription("扫街");
		entity1.setExpiredTime(new Date());
		entity1.setName("a区");
		entity1.setSalesman(sr.findByUserUsername("yewu01"));
		entity1.setRegion(rr.getOne("100"));
		entity1.setBeginTime(new Date());
		entity1.setMinValue(3);
		entity1.setOrder(0);
		entity1.setStatus(SaojieStatus.PENDING);
		entity1.setParent(tsr.getOne(1L));
		tsr.save(entity1);

	}

	@Test
	public void testDel() {
	  ChildAccount C = cr.findBySimId("460028545000000");
	  
	  if(C != null){
	    System.out.println("================="+C.getTruename());
	  }
	}
//	@Test
//	public void testOrderSignfor(){
//		Page<Object>	o =	orderSignforR.findByUserIdAndCreatTime("A37018707220",3,2,new PageRequest(0,10,new Sort(Direction.DESC, "id")));
//	    List<OrderVo> volist = new ArrayList<OrderVo>();
//	    List<Object> list = o.getContent();
//		for(int i=0;i<list.size();i++){
//			Object[] ob = (Object[])list.get(i);
//			List<OrderDto> dtoList = new ArrayList<OrderDto>();
//			Float totalArrear = 0f;
//			OrderVo bvo = new OrderVo();
//			bvo.setShopName(ob[0]+"");
//
//			bvo.setContent(dtoList);
//			for(int j = 0;j<list.size();j++){
//				Object[] obj = (Object[])list.get(j);
//
//				if(ob[0] == obj[0]){
//					OrderDto dto = new OrderDto();
//					dto.setOrderNo(obj[1]+"");
//					dto.setPayTyp(Integer.parseInt(obj[2]+""));
//					dto.setOrderPrice((Float)obj[3]);
//					dto.setCreateTime((Date)obj[4]);
//					dto.setArrear((Float)obj[5]);
//					dtoList.add(dto);
//					totalArrear += (Float)obj[5];
//					bvo.setTotalArreas(totalArrear);
//				}
//			}
//			volist.add(bvo);
//		}
//	}
	public static void main(String[] args) {
		/*Float f = 10.00f;
		BigDecimal b = new BigDecimal("20.00");
		System.out.println("f======="+f);
		System.out.println("b====="+b);
		*/
		int x = 10;
		int j = 0;
		for(int i=0;i<10;i++){
			j += x;
			System.out.println(">>>>>>>>>>>>>>>>"+j);
		}
		System.out.println("===================>>"+j);
	}
	
	@Test
	public void testDele(){
		List<Receipt> list = receiptRepository.findAllByOrderNo("20161008160330965");
	//	receiptRepository.deleteByOrderNo("20161008160330965");
		
		receiptRepository.delete(list);
		
	//	receiptRepository.delete(Long.parseLong("5"));
	}

}
