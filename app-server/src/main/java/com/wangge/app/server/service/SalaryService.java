package com.wangge.app.server.service;


import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Salary;
import com.wangge.app.server.repository.SalaryRespository;

/**
 * Created by 神盾局 on 2016/6/13.
 */
@Service
public class SalaryService {
    
	@Resource
	private SalaryRespository salaryRespository;
	
	public Salary findSalary(String tel,String months) {
		return  salaryRespository.findByTelAndMonths(tel, months);
	}
	
}
