package com.wangge.app.server.service;

import java.util.ArrayList;
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
		int taskValue = 0;
		int dataSaojieNum = 0;
		List<Saojie> child = new ArrayList<Saojie>();
		SaojieData data =  dataSaojieRepository.save(dataSaojie);
		taskValue = dataSaojie.getSaojie().getMinValue();
	    dataSaojieNum = getDtaCountBySaojieId(dataSaojie.getSaojie().getId());
		if(taskValue == dataSaojieNum){
			dataSaojie.getSaojie().setStatus(SaojieStatus.AGREE);
			Saojie sj2 =  taskSaojieRepository.findByOrderAndSalesman(dataSaojie.getSaojie().getOrder()+1,dataSaojie.getSaojie().getSalesman());
			if(sj2 != null && !"".equals(sj2)){
				sj2.setStatus(SaojieStatus.PENDING);
				
				child.add(sj2);
				
			}
			child.add(dataSaojie.getSaojie());
			dataSaojie.getSaojie().setChildren(child);
			taskSaojieRepository.save(dataSaojie.getSaojie());
		}
		
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

/*	public void updateSaojie(Saojie saojie) {
		taskSaojieRepository.save(saojie);
		
	}*/

	/*public Saojie findByOrderAndSalesman(Integer id, Salesman salesman) {
		
		return taskSaojieRepository.findByOrderAndSalesman(id,salesman);
	}*/
	
	public Saojie findByRegion(Region region) {
			
			return taskSaojieRepository.findByRegion(region);
	}
	
	public SaojieData findBySaojieData(Long id) {
		return dataSaojieRepository.findOne(id);
	}
	
}
