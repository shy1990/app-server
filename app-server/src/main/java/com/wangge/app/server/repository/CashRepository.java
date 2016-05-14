package com.wangge.app.server.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Cash;
import com.wangge.app.server.entity.OilCostRecord;

public interface CashRepository extends JpaRepository<Cash, Long>{

  List<Cash> findByUserId(String userId);
  
  
  
}
