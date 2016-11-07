package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Cash;

public interface CashRepository extends JpaRepository<Cash, Integer>{

	@EntityGraph("graph.Cash.orderItem")
  List<Cash> findByUserIdAndStatus(String userId,Integer status);

  List<Cash> findByUserIdAndStatusAndCashIdIn(String userId,Integer status, Integer[] ids);
  
}
