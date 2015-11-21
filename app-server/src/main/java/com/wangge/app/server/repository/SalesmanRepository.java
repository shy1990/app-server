package com.wangge.app.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Salesman;

public interface SalesmanRepository extends JpaRepository<Salesman, String>  {
	public Salesman findByUserUsername(String username);
	//@Query("select s.id,s.user.phone,s.region.id from Salesman s left join s.user u where u.username = ? and u.password = ?")
	public Salesman findByUserUsernameAndUserPassword(String username,String password);
//
//	public Salesman findByUsernameAndPassword(String username,String password);
//	
//	public Salesman findByUsernameAndPasswordAndPhone(String username,String password,String phone);
}
