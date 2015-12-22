package com.wangge.app.server.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {
	
	Page<Visit> findBySalesman(Salesman salesman,Pageable page);
}