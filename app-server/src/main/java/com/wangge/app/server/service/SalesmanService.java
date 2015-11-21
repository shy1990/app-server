package com.wangge.app.server.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.repository.SalesmanRepository;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class SalesmanService {


	@Autowired
	private SalesmanRepository salesmanRepository;

	public List<Salesman>  findSalesman(){
		List<Salesman> listSalesma=salesmanRepository.findUserName();;
		return listSalesma;
	}
	
}
