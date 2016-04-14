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
	/**
	 * 
	 * 功能: 查询salesman list
	 * 详细： 	
	 * 作者： 	jiabin
	 * 版本：  1.0
	 * 日期：  2015年11月21日上午10:38:38
	 *
	 */
	public List<Salesman>  findSalesman(){
		List<Salesman> listSalesma=salesmanRepository.findUserName();;
		return listSalesma;
	}
	public Salesman findByUsernameAndPassword(String username, String password) {

		Salesman salesman = salesmanRepository.findByUserUsernameAndUserPassword(
				username, password);

		return salesman;
	}
	
	public Salesman findSalesmanbyId(String id){
		return salesmanRepository.findOne(id);

	}

	public void save(Salesman salesman) {
		salesmanRepository.save(salesman);
	}

	public List<Salesman> findAll(){
		return salesmanRepository.findAll();
	}
	public Salesman login(String username, String password) {
		
		return  salesmanRepository.findByUserUsernameAndUserPassword(username, password);
	}
  public String getSalesman(String userId) {
    Salesman man = salesmanRepository.findOne(userId);
    if(man != null){
       return man.getRegion().getName();
    }
    return null;
  }
}
