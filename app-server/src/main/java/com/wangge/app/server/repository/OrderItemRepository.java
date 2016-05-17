package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
  List<OrderItem> findByOrder_Id(String orderNum);
}
