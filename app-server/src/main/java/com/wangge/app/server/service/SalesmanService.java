package com.wangge.app.server.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.SalesmanRepository;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class SalesmanService {


	@Resource
	private SalesmanRepository salesmanRepository;

	public Salesman findByUsernameAndPassword(String username, String password) {

		Salesman salesman = salesmanRepository.findByUserUsernameAndUserPassword(
				username, password);

		return salesman;
	}

	public Salesman findByUsername(String username) {

		return salesmanRepository.findByUserUsername(username);
	}

	public void save(Salesman salesman) {
		salesmanRepository.save(salesman);
	}

	public Salesman login(String username, String password, String phone) {
		
		return  salesmanRepository.findByUserUsernameAndUserPassword(username, password);
	}

}
