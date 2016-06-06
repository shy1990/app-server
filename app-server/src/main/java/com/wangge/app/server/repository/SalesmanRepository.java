package com.wangge.app.server.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Salesman;

public interface SalesmanRepository extends JpaRepository<Salesman, String>  {
	public Salesman findByUserUsername(String username);
	@Query("select id,user.username,user.nickname,region.id from Salesman ") 
	public List<Salesman> findUserName();
	
	@Query("select count(*) from Salesman r where r.region.id=?")
	public int findSaleNum(String regionid);
	
	public Salesman findByRegion_Id(String id);
	public Salesman findByUserUsernameAndUserPassword(String username,String password);
	
	public Salesman findByMobile(String mobile);
	
}
