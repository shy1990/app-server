package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Salesman;

public interface SalesmanRepository extends JpaRepository<Salesman, Long>  {
	public Salesman findByUsername(String username);
}
