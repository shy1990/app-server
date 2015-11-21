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
	
	/**
	 * 
	 * 功能: 通过业务员查询
	 * 详细： 	
	 * 作者： 	Administrator
	 * 版本：  1.0
	 * 日期：  2015年11月21日下午3:17:50
	 *
	 */
	public List<Saojie> findBySalesman(Salesman salesman){
		return saojieRepository.findBySalesman(salesman);
	}
	
	/**
	 * 
	 * 功能: 保存
	 * 详细： 	
	 * 作者： 	Administrator
	 * 版本：  1.0
	 * 日期：  2015年11月21日下午3:18:29
	 *
	 */
	public void saveSaojie(Saojie saojie){
		saojieRepository.save(saojie);
	}
	
	
	public  List<Saojie> findAllSaojie(){
		
		return saojieRepository.findAllSaojie();
	}
}
