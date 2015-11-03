package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Salesman;
import com.wangge.common.entity.User;

public interface SalesmanRepository extends JpaRepository<Salesman, Long>  {
	public Salesman findByUsername(String username);

	public Salesman findByUsernameAndPassword(String username,String password);
	
	public Salesman findByUsernameAndPasswordAndPhone(String username,String password,String phone);
}
