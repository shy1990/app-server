package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.app.server.entity.WaterOrderCash;

public interface WaterOrderCashRepository extends JpaRepository<WaterOrderCash, Integer>,
JpaSpecificationExecutor<WaterOrderCash>{

  List<WaterOrderCash> findByUserId(String userId);
  
  Page<WaterOrderCash> findByUserId(String userId,Pageable pageable);
  
  
}
