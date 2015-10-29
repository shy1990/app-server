package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	public Task findOneByName(String name);
}
