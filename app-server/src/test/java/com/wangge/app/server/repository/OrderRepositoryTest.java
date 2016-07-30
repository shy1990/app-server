package com.wangge.app.server.repository;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.app.server.AppServerApplication;
import com.wangge.app.server.entity.Order;
import com.wangge.app.server.entity.OrderItem;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class OrderRepositoryTest {
	@Autowired
	private OrderRepository or;
	@Autowired
	private RegionRepository rr;

	@Test
	@Transactional
	public void testQuery() {
		Order order = or.findOne("20151105210643034");
		System.out.println(order.getShopName() + " " + order.getAmount() + " " + order.getCreateTime() + "  "
				+ order.getStatus().getName() + "  " + order.getRegion().getName());
		for (OrderItem item : order.getItems()) {
			System.out.println("item:" + item.getName());
		}
	}

	@Test
	@Transactional
	public void testQueryByRegion() {
		Page<Order> page = or.findByRegion(rr.findById("370126"), null);
		for (Order order : page) {
			System.out.println(order.getShopName() + " " + order.getAmount() + " " + order.getCreateTime() + "  "
					+ order.getStatus().getName() + "  " + order.getRegion().getName());
		}

	}

}
