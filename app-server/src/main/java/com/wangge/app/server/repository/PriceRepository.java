package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Changerprice;

public interface PriceRepository extends JpaRepository<Changerprice, Long> {
	
	@Query("select s.id,s.pricerange,s.productname,s.applyReason,s.salesman.user.nickname,s.status,s.approveReason,s.region.name,s.applyTime,s.approveTime from Changerprice s ")
	List<Changerprice> findAllChangerprice();
	
}
