package com.wangge.app.server.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wangge.app.server.entity.Assess;
import com.wangge.app.server.entity.Salesman;

public interface AssessRepository extends JpaRepository<Assess, Long> {
	
	List<Assess> findBySalesman(Salesman salesman);
	
}
