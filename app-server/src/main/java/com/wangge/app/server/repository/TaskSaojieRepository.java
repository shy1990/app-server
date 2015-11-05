package com.wangge.app.server.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.TaskSaojie;

public interface TaskSaojieRepository extends JpaRepository<TaskSaojie, String> {
	List<TaskSaojie> findBySalesman(Salesman salesman);

}
