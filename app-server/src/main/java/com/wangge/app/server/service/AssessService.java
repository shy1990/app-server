package com.wangge.app.server.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Assess;
import com.wangge.app.server.repository.AssessRepository;

@Service
public class AssessService {
	@Resource
	private AssessRepository ars;
	
	
	/**
	 * 
	 * @Description: 客户开发列表
	 * @param @param 
	 * @param @param 
	 * @param @return   
	 * @return  
	 * @throws
	 * @author
	 * @date 
	 */
	public List<Assess> getAllList(){
		List<Assess> list =(List<Assess>) ars.findAll();
		
		return list;
	}
	
	public String addAssesses(Assess assess){
		
		ars.save(assess);
		return "OK";
	}
}
