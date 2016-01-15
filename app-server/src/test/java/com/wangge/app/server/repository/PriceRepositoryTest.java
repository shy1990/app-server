//package com.wangge.app.server.repository;
//
//import javax.transaction.Transactional;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.data.domain.Page;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.wangge.AppServerApplication;
//import com.wangge.app.server.entity.Changerprice;
//import com.wangge.app.server.entity.Order;
//import com.wangge.app.server.entity.OrderItem;
//import com.wangge.common.repository.RegionRepository;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AppServerApplication.class)
//public class PriceRepositoryTest {
//	@Autowired
//	private PriceRepository pr;
//
//	@Test
//	@Transactional
//	public void testQuery() {
//		String id = "4";
//		Changerprice order = pr.findOne(Long.parseLong(id));
//		System.out.println(order.getProductname() + " " + order.getApproveTime() + " " + order.getApplyTime() + "  "
//				+ order.getStatus().getName() + "  " + order.getRegion().getName());
//	}
//
//
//}
