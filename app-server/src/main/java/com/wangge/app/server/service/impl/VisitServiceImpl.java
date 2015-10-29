package com.wangge.app.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.TaskVisit;
import com.wangge.app.server.repository.VisitRepository;
import com.wangge.app.server.service.VisitService;

@Service
public class VisitServiceImpl implements VisitService {
	@Autowired
	private VisitRepository visitRepository;

	@Override
	public void save(TaskVisit taskVisit) {
		visitRepository.save(taskVisit);
	}

	
	@Override
	public TaskVisit findById(int id) {
		TaskVisit taskVisit = visitRepository.findById(id);
		return taskVisit;
	}
	
	
}
