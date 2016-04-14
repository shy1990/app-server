package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Phone;



public interface PhoneRepository extends JpaRepository<Phone, Long> {
	

	
}
