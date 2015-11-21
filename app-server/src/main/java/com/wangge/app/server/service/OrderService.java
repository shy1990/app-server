package com.wangge.app.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Order;
import com.wangge.app.server.repository.OrderRepository;
import com.wangge.app.server.vo.OrderPub;
import com.wangge.common.entity.Region;
@Service
public class OrderService {
	@Autowired
	private OrderRepository or;
	
	/**
	 * 
	 * @Description: 根据地区编码查询所属下的订单
	 * @param @param region
	 * @param @param page
	 * @param @return   
	 * @return Page<Order>  
	 * @throws
	 * @author changjun
	 * @date 2015年11月20日
	 */
	public List<OrderPub> findByRegion(Region region,Pageable page){
		Page<Order> pOrder=or.findByRegion(region, page);
		int totalPage = (pOrder.getTotalPages()+pOrder.getSize()-1)/pOrder.getSize();
		List<OrderPub> list = new ArrayList<OrderPub>();
		OrderPub order = new OrderPub();
		if(pOrder!=null && pOrder.getTotalPages()>0){
			for(Order o:pOrder){
				order.setTotalPage(totalPage);
				order.setCreateTime(o.getCreateTime());
				order.setOrderNum(o.getId());
				order.setStatus(o.getStatus().getName());
				order.setUsername(o.getShopName());
				order.setTotalCost(Double.parseDouble(o.getAmount()+""));
				list.add(order);
				order = null;
			}
		}
		return list;
	}

	public Order findOne(String ordernum) {
		return or.findOne(ordernum);
	}
	
}
