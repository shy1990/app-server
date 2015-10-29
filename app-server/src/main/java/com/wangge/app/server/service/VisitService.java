package com.wangge.app.server.service;

import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.TaskVisit;

@Service
public interface VisitService {
	
	public TaskVisit findById(int id);
	
	public void save(TaskVisit taskVisit);
}
