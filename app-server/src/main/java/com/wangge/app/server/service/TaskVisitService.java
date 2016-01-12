package com.wangge.app.server.service;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.entity.Visit;
import com.wangge.app.server.entity.Visit.VisitStatus;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.SaojieDataRepository;
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
	@Resource
  private SaojieDataRepository dataSaojieRepository;
	
	public Json findBySalesman(Salesman salesman,Pageable page,int flag){
	  Json json = new Json();
		List<VisitVo> result = Lists.newArrayList();
		Page<Visit> pVisit = visitRepository.findBySalesman(salesman,page);
		int totalPage = (pVisit.getTotalPages()+pVisit.getSize()-1)/pVisit.getSize();
		VisitVo visitVo;
		if(pVisit != null && pVisit.getTotalPages() > 0){
			for(Visit visit : pVisit){
			  SaojieData saojie = dataSaojieRepository.findOne(visit.getRegistData().getId());
				if(flag ==0){
					visitVo = new VisitVo();
					visitVo.setId(String.valueOf(visit.getId()));
					visitVo.setShopName(visit.getRegistData().getShopName());
					visitVo.setAddress(visit.getRegistData().getReceivingAddress());
					visitVo.setImageurl(visit.getRegistData().getImageUrl());
					visitVo.setStatus(visit.getStatus());
					if(saojie != null && !"".equals(saojie)){
					  visitVo.setCoordinate(saojie.getCoordinate());
					}
					if(VisitStatus.FINISHED.equals(visit.getStatus())){
					  visitVo.setExpiredTime(visit.getExpiredTime());
					  visitVo.setSummary(visit.getSummary());
					  visitVo.setImageurl1(visit.getImageurl1());
					  visitVo.setImageurl2(visit.getImageurl2());
					  visitVo.setImageurl3(visit.getImageurl3());
					  visitVo.setVisitAddress(visit.getAddress());
					}
					if(VisitStatus.PENDING.equals(visit.getStatus())){
					  Calendar cal = Calendar.getInstance();
		        cal.setTime(visit.getBeginTime());
		        long time1 = cal.getTimeInMillis();
		        cal.setTime(visit.getExpiredTime());
		        long time2 = cal.getTimeInMillis();
		        long timing=(time2-time1)/(1000*3600*24);
		        visitVo.setTiming(Integer.parseInt(String.valueOf(timing)));
					}
					result.add(visitVo);
				}else{
					if(VisitStatus.FINISHED.equals(visit.getStatus())){
						visitVo = new VisitVo();
						visitVo.setId(String.valueOf(visit.getId()));
						visitVo.setShopName(visit.getRegistData().getShopName());
						visitVo.setAddress(visit.getRegistData().getReceivingAddress());
						visitVo.setImageurl(visit.getRegistData().getImageUrl());
						visitVo.setStatus(visit.getStatus());
						visitVo.setExpiredTime(visit.getExpiredTime());
						visitVo.setSummary(visit.getSummary());
            visitVo.setImageurl1(visit.getImageurl1());
            visitVo.setImageurl2(visit.getImageurl2());
            visitVo.setImageurl3(visit.getImageurl3());
            visitVo.setVisitAddress(visit.getAddress());
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
