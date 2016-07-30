package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.cash.entity.WaterOrderCash;
import com.wangge.app.server.cash.entity.WaterOrderDetail;

public interface WaterOrderDetailRepository extends JpaRepository<WaterOrderDetail, Integer>{

  List<WaterOrderDetail> findBySerialNo(String SerialNo);
  
}
