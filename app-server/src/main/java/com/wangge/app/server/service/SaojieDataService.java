package com.wangge.app.server.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.repository.SaojieDataRepository;
@Service
public class SaojieDataService {

	@Autowired
	private SaojieDataRepository saojieDataRepository;
	
	
	public List<SaojieData> findsjidById (Collection<Saojie> listSjid) {
		
		return saojieDataRepository.findBySaojieIn(listSjid);
	}
}
