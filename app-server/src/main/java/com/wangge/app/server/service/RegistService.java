package com.wangge.app.server.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Regist;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.repository.RegistDataRepository;
import com.wangge.app.server.repository.RegistRepository;
import com.wangge.app.server.repository.SalesmanRepository;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class RegistService {


	@Resource
	private RegistRepository registRepository;
	@Resource
	private RegistDataRepository registDataReposity;
	@Resource
	private SalesmanRepository salesmanRepository;

	public void addSaoRegist(Regist regist) {
		registRepository.save(regist);
	}
	
	public List<Regist> findAllSaojie() {

		return registRepository.findAllRegist();
	}
	
	public Salesman findSalesmanbyId(String id){
		return salesmanRepository.findOne(id);

	}
	/**
	 * 
	 * 功能: 查询注册列表下的所有注册实例 
	 *  作者： store
	 *  版本： 1.0 
	 *  日期： 2015年12月10
	 *
	 */
public List<RegistData> findreidById (Collection<Regist> listSjid) {
		
		return registDataReposity.findByRegistIn(listSjid);
	}
	/**
	 * 
	 * 功能: 保存 
	 *  作者： store
	 *  版本： 1.0 
	 *  日期： 2015年12月10
	 *
	 */
	public void saveRegist(Regist regist) {
		registRepository.save(regist);
	}
	
	/**
	 * 
	 * 功能: 通过业务员查询 
	 * 作者：store 
	 * 版本： 1.0 
	 * 日期： 2015年11月21日下午3:17:50
	 *
	 */
	public List<Regist> findBySalesman(Salesman salesman) {
		return registRepository.findBySalesman(salesman);
	}
	
}

