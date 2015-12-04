package com.wangge.app.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {
	
	
}