package com.wangge.app.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Visit;
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
	
	public List<VisitVo> findBySalesman(Salesman salesman,Pageable page){
		List<VisitVo> result = Lists.newArrayList();
		Page<Visit> pVisit = visitRepository.findBySalesman(salesman,page);
		int totalPage = (pVisit.getTotalPages()+pVisit.getSize()-1)/pVisit.getSize();
		VisitVo visitVo;
		if(pVisit != null && pVisit.getTotalPages() > 0){
			for(Visit visit : pVisit){
				visitVo = new VisitVo();
				visitVo.setTotalPage(totalPage);
				visitVo.setId(String.valueOf(visit.getId()));
				visitVo.setShopName(visit.getRegistData().getShopName());
				visitVo.setAddress(visit.getRegistData().getReceivingAddress());
				visitVo.setImageurl(visit.getRegistData().getImageUrl());
				visitVo.setStatus(visit.getStatus());
				result.add(visitVo);
			}
		}
		return result;
	}
	
	public Visit findByVisitId(Long visitId) {
		
		return visitRepository.findOne(visitId);
	}
	
	public void save(Visit visit) {
		visitRepository.save(visit);
	}
}
