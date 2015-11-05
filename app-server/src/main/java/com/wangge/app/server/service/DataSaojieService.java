package com.wangge.app.server.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.DataSaojie;
import com.wangge.app.server.entity.Region;
import com.wangge.app.server.entity.Task;
import com.wangge.app.server.entity.TaskSaojie;
import com.wangge.app.server.repository.DataSaojieRepository;
import com.wangge.app.server.repository.RegionRepository;
import com.wangge.app.server.repository.TaskSaojieRepository;

@Service
public class DataSaojieService {
	@Resource
	private DataSaojieRepository dataSaojieRepository;
	
	@Autowired
	private TaskSaojieRepository taskSaojieRepository;
	
	@Autowired
	private RegionRepository regionRepository;
	
	public void addDataSaojie(DataSaojie dataSaojie){
		
		dataSaojieRepository.save(dataSaojie);
	}
	
	
	public List<DataSaojie> getSaojieDataByregionId(String regionId){
		
		  Region region =  regionRepository.findById(regionId);
		  
		  for(Task task : region.getTask()){
			  task.getDataSaojie();
			  return (List<DataSaojie>)  task.getDataSaojie();
		  }
		   			
	//		TaskSaojie s = taskSaojieRepository.findByRegions(regionId);
			
			//return (List<DataSaojie>) s.getDataSaojie();
		  return null;
	}

}
