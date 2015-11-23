package com.wangge.app.server.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.entity.Saojie.SaojieStatus;
import com.wangge.app.server.repository.SaojieDataRepository;
import com.wangge.app.server.repository.SaojieRepository;
import com.wangge.common.entity.Region;
import com.wangge.common.repository.RegionRepository;

@Service
public class DataSaojieService {
	@Resource
	private SaojieDataRepository dataSaojieRepository;

	@Autowired
	private SaojieRepository taskSaojieRepository;

	@Autowired
	private RegionRepository regionRepository;

	public SaojieData addDataSaojie(SaojieData dataSaojie) {

		SaojieData data =  dataSaojieRepository.save(dataSaojie);
		
		return data;
	}

	public List<SaojieData> getSaojieDataByregion(String regionId) {


		return dataSaojieRepository.findByRegionId(regionId);
	}

	public SaojieData findSaojieDataById(Long saojieDataId) {
		
		return dataSaojieRepository.findOne(saojieDataId);
	}


	public Saojie findBySalesman(Salesman salesman) {
	List<Saojie>	list = taskSaojieRepository.findBySalesman(salesman);
	    for(Saojie saojie : list){
	    	if(SaojieStatus.PENDING.equals(saojie.getStatus())){
	    		for(Saojie sj : saojie.getChildren()){
	    			if(SaojieStatus.PENDING.equals(sj.getStatus())){
	    				return sj;
	    			}
	    		}
	    	}
	    }
		return null;
	}
	
	public int getDtaCountBySaojieId(Long long1) {
		List<SaojieData>  datasaojie = dataSaojieRepository.findBySaojieId(long1);
		if(datasaojie != null && datasaojie.size() > 0){
			return datasaojie.size();
		}else{
			return 0;
		}
	}

	public void updateSaojie(Saojie saojie) {
		taskSaojieRepository.save(saojie);
		
	}

	public Saojie findByOrder(Integer id) {
		
		return taskSaojieRepository.findOne(Long.parseLong(String.valueOf(id)));
	}
}
