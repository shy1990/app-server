package com.wangge.app.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.repository.SaojieRepository;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class SaojieService {


	@Autowired
	private SaojieRepository saojieRepository;
	
	public List<Saojie> findBySalesman(Salesman salesman){
		return saojieRepository.findBySalesman(salesman);
	}
	
}
