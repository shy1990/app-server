package com.wangge.app.server.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.OrderSignfor;

public interface OrderSignforRepository extends JpaRepository<OrderSignfor, Long>{
  

  List<OrderSignfor> findByFastmailNo(String fastMailNo);

  OrderSignfor findByOrderNoAndUserPhone(String orderNo, String userPhone);


}
