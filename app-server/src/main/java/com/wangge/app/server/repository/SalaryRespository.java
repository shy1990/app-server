package com.wangge.app.server.repository;



import org.springframework.data.repository.PagingAndSortingRepository;

import com.wangge.app.server.entity.Salary;

/**
 * Created by 神盾局 on 2016/6/13.
 */
public interface SalaryRespository extends PagingAndSortingRepository<Salary,Long> {
	Salary  findByTelAndMonths(String  tel,String months);
}
