package com.wangge.app.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Visit;
import com.wangge.app.server.entity.Visit.VisitStatus;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.VisitRepository;
import com.wangge.app.server.vo.VisitVo;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class TaskVisitService {
	@Autowired
	private VisitRepository visitRepository;
	
	public Json findBySalesman(Salesman salesman,Pageable page,int flag){
	  Json json = new Json();
		List<VisitVo> result = Lists.newArrayList();
		Page<Visit> pVisit = visitRepository.findBySalesman(salesman,page);
		int totalPage = (pVisit.getTotalPages()+pVisit.getSize()-1)/pVisit.getSize();
		VisitVo visitVo;
		if(pVisit != null && pVisit.getTotalPages() > 0){
			for(Visit visit : pVisit){
				if(flag ==0){
					visitVo = new VisitVo();
					visitVo.setId(String.valueOf(visit.getId()));
					visitVo.setShopName(visit.getRegistData().getShopName());
					visitVo.setAddress(visit.getRegistData().getReceivingAddress());
					visitVo.setImageurl(visit.getRegistData().getImageUrl());
					visitVo.setStatus(visit.getStatus());
					result.add(visitVo);
				}else{
					if(VisitStatus.FINISHED.equals(visit.getStatus())){
						visitVo = new VisitVo();
						visitVo.setId(String.valueOf(visit.getId()));
						visitVo.setShopName(visit.getRegistData().getShopName());
						visitVo.setAddress(visit.getRegistData().getReceivingAddress());
						visitVo.setImageurl(visit.getRegistData().getImageUrl());
						visitVo.setStatus(visit.getStatus());
						result.add(visitVo);
					}
				}
			}
			json.setSuccess(true);
			json.setObj(result);
			json.setTotalPage(totalPage);
		}
		return json;
	}
	
	public Visit findByVisitId(Long visitId) {
		
		return visitRepository.findOne(visitId);
	}
	
	public void save(Visit visit) {
		visitRepository.save(visit);
	}
}
