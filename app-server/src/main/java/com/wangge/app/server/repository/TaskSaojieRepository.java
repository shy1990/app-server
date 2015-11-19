package com.wangge.app.server.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;

public interface TaskSaojieRepository extends JpaRepository<Saojie, String> {
	List<Saojie> findBySalesman(Salesman salesman);

}
