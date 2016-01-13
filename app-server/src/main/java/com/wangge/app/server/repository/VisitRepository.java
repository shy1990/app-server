package com.wangge.app.server.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {
	
	List<Visit> findBySalesman(Salesman salesman);
}