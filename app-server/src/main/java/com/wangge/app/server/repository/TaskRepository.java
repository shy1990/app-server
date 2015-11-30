package com.wangge.app.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Saojie;


public interface TaskRepository extends JpaRepository<Saojie, String> {
	
}
