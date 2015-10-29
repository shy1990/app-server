package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Order;
import com.wangge.app.server.entity.Salesman;

public interface OrderRepository extends JpaRepository<Order, Long>{
	public Order findByUsername(String username);
}
