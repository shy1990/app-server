package com.wangge.app.server.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Assess;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.service.AssessService;
import com.wangge.app.server.service.SalesmanService;

@RestController
@RequestMapping(value = "/v1/assess")
public class AssessController {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//
	@Resource
	private SalesmanService saleService;
	@Resource
	private AssessService assessService;
	private static final Logger logger = Logger.getLogger(AssessController.class);
	
	@RequestMapping(value = "/findAllAssess", method = RequestMethod.POST)
	public ResponseEntity<List<Assess>> findAllTask(String userid){
		
		List<Assess> listAssess=assessService.getAllList();
		
		return new ResponseEntity<List<Assess>>(listAssess,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addAssess", method = RequestMethod.POST)
	public ResponseEntity<String> addAssess(String salesmanid,String assessStage,String assessActivenum,String assessOrdernum,String assessCycle,String accessTime){
		Assess assess = new Assess();
		assess.setAssessActivenum(assessActivenum);
		assess.setAssessCycle(assessCycle);
		assess.setAssessOrdernum(assessOrdernum);
		assess.setAssessStage(assessStage);
		Salesman salesman = saleService.findSalesmanbyId(salesmanid);
		if(salesman!=null){
			assess.setSalesman(salesman);
		}
		try {
			assess.setAssessTime(sdf.parse(accessTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		assessService.addAssesses(assess);
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}
	
	
}