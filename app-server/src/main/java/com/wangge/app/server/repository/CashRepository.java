package com.wangge.app.server.repository;

import com.wangge.app.server.entity.Cash;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashRepository extends JpaRepository<Cash, Integer>{

	@EntityGraph("graph.Cash.orderItem")
	List<Cash> findByUserIdAndStatus(String userId,Integer status);

  List<Cash> findByUserIdAndStatusAndCashIdIn(String userId,Integer status, Integer[] ids);

}
