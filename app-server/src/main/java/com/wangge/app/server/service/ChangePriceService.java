package com.wangge.app.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Changerprice;
import com.wangge.app.server.repository.PriceRepository;

@Service
public class ChangePriceService {
	@Autowired
	private PriceRepository pr;
	
	public Changerprice findById(String id){
		Changerprice changerprice = pr.findOne(Long.parseLong(id));
		return changerprice;
	}
	
	public void saveChangerprice(Changerprice changerprice) {
		pr.save(changerprice);
	}
	
	public List<Changerprice> findAllChangerprice() {

		return pr.findAllChangerprice();
	}

	public List<Changerprice> findAll(){
		List<Changerprice> list = pr.findAll();
		return list;
	}

	}
