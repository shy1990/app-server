package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Regist;


public interface RegistRepository extends JpaRepository<Regist, Long> {
	
}
