package com.wangge.app.server.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Visit;
import com.wangge.app.server.entity.Visit.VisitStatus;

public interface VisitRepository extends JpaRepository<Visit, Long> {
	
  @Query("select v from Visit v where v.salesman.id=?1 and v.status=?2 order by v.status,v.beginTime desc,v.expiredTime desc,v.id asc")
	Page<Visit> findBySalesmanId(String salesmanId,VisitStatus flag,Pageable page);
}