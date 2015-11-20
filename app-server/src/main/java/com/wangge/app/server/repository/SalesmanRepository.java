package com.wangge.app.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Salesman;

public interface SalesmanRepository extends JpaRepository<Salesman, String>  {
	public Salesman findByUserUsername(String username);
//
//	public Salesman findByUsernameAndPassword(String username,String password);
//	
//	public Salesman findByUsernameAndPasswordAndPhone(String username,String password,String phone);
	
	@Query("select count(*) from Salesman r where r.region.id=?")
	public int findSaleNum(String regionid);
}
