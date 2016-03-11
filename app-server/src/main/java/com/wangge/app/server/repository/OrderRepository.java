package com.wangge.app.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Order;
import com.wangge.app.server.entity.Region;

public interface OrderRepository extends JpaRepository<Order, String> {
	Page<Order> findByRegion(Region region,Pageable page);
}
