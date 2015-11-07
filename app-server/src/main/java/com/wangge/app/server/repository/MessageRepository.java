package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Message;


public interface MessageRepository extends JpaRepository<Message, Long>{
	
}
