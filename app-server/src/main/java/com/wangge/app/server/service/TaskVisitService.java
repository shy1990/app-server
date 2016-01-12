package com.wangge.app.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Visit;
import com.wangge.app.server.repository.VisitRepository;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class TaskVisitService {
	@Autowired
	private VisitRepository visitRepository;
	
	public List<Visit> findBySalesman(Salesman salesman){
		List<Visit> list = visitRepository.findBySalesman(salesman);
		return list;
	}
	
	public void addVisit(Visit visit){
		
		visitRepository.save(visit);
	}
}
