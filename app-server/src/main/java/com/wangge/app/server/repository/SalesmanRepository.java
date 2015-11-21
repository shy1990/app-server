package com.wangge.app.server.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Salesman;

public interface SalesmanRepository extends JpaRepository<Salesman, String>  {
	public Salesman findByUserUsername(String username);
	@Query("select id,user.username from Salesman ") 
	public List<Salesman> findUserName();
}
