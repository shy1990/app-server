package com.wangge.app.server.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Assess;
import com.wangge.app.server.entity.Assess.AssessStatus;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.SalesmanStatus;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.service.AssessService;
import com.wangge.app.server.service.RegionService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.util.JWtoAdrssUtil;

@RestController
@RequestMapping(value = "/v1/assess")
public class AssessController {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//
	@Resource
	private SalesmanService saleService;
	@Resource
	private AssessService assessService;
	@Resource
	private RegionService regionService;
	private static final Logger logger = Logger.getLogger(AssessController.class);
	
	@RequestMapping(value = "/findAllAssess", method = RequestMethod.POST)
	public ResponseEntity<List<Map<String,Object>>> findAllTask(String userid){
		
		List<Assess> listAssess=assessService.getAllList();
		
		List<Map<String,Object>> sdmap = new ArrayList<Map<String,Object>>();
		for(Assess assess:listAssess){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("assessActivenum", assess.getAssessActivenum());
			map.put("assessStage", assess.getAssessStage());
			map.put("assessOrdernum", assess.getAssessOrdernum());
			map.put("salesId", assess.getSalesman().getId());
			map.put("assessCycle", assess.getAssessCycle());
			sdmap.add(map);
		}
		
		return new ResponseEntity<List<Map<String,Object>>>(sdmap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addAssess", method = RequestMethod.POST)
	public ResponseEntity<String> addAssess(String salesmanid,String assessStage,String assessActivenum,String assessOrdernum,String assessCycle,String accessTime,String regionid,String regionzh,String taskid){
		Assess assess = new Assess();
		assess.setStatus(AssessStatus.PENDING);
		assess.setAssessActivenum(assessActivenum);
		assess.setAssessCycle(assessCycle);
		assess.setAssessOrdernum(assessOrdernum);
		assess.setAssessStage(assessStage);
		Salesman salesman = saleService.findSalesmanbyId(salesmanid);
		
		  salesman.setStatus(SalesmanStatus.kaifa);
		
		if(salesman!=null){
			assess.setSalesman(salesman);
		}
		assess.setAssessDefineArea(regionid);
		assess.setAssessArea(taskid);
		try {
			assess.setAssessTime(sdf.parse(accessTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		assess.setAssesszh(regionzh);
		assessService.addAssesses(assess);
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}
	
	
	
	
}
