package com.wangge.app.server.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.OrderSignfor;

public interface OrderSignforRepository extends JpaRepository<OrderSignfor, Long>{
  

  List<OrderSignfor> findByUserPhoneAndFastmailNo(String userPhone,String fastMailNo);

  OrderSignfor findByOrderNoAndUserPhone(String orderNo, String userPhone);

  OrderSignfor findByOrderNo(String orderno);


}
