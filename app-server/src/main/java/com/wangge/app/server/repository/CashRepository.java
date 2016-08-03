package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.cash.entity.Cash;

public interface CashRepository extends JpaRepository<Cash, Integer>{

  List<Cash> findByUserIdAndStatus(String userId,Integer status);
  
  List<Cash> findByUserIdAndCashIdIn(String userId,Integer[] ids);
  
}
