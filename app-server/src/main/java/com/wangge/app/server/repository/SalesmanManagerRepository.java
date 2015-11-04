package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.SalesmanManager;

public interface SalesmanManagerRepository extends JpaRepository<SalesmanManager, String>  {
	public SalesmanManager findByUsername(String username);
}
