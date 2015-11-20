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

	
}
