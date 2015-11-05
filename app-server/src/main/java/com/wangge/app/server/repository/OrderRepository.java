package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Message;
import com.wangge.app.server.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	public Order findByOrderNum(String orderNum);
}
