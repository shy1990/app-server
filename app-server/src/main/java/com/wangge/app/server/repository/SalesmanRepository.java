package com.wangge.app.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Salesman;

public interface SalesmanRepository extends JpaRepository<Salesman, String>  {
//	public Salesman findByUsername(String username);
//
//	public Salesman findByUsernameAndPassword(String username,String password);
//	
//	public Salesman findByUsernameAndPasswordAndPhone(String username,String password,String phone);
}
