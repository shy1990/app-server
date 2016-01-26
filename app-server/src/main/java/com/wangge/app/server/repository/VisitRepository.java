package com.wangge.app.server.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {
	
  @Query("select v from Visit v where v.salesman.id=?1 order by v.status,v.beginTime desc,v.expiredTime desc")
	Page<Visit> findBySalesmanId(String salesmanId,Pageable page);
}