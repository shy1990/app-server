package com.wangge.app.server.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.Saojie.SaojieStatus;
import com.wangge.app.server.service.RegionService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.service.SaojieService;

@RestController
@RequestMapping(value = "/v1")
public class SaojieController {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//
	@Resource
	private SalesmanService salesmanService;
	@Resource
	private RegionService regionService;
	@Resource
	private SaojieService saojieService;

	@RequestMapping(value = "/addSaojie", method = RequestMethod.POST)
	public ResponseEntity<String> addTask(String taskName,String salesmanid,String regionid,
			String taskStart,String taskEnd,String taskCount,String taskDes,String userName ) {
			Saojie entity = new Saojie();
			entity.setDescription(taskDes);
			try {
				entity.setExpiredTime(sdf.parse(taskEnd));
				entity.setBeginTime(sdf.parse(taskStart));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			entity.setName(taskName);
			entity.setSalesman(salesmanService.findSalesmanbyId(salesmanid));
			entity.setRegion(regionService.findRegion(regionid));
			entity.setMinValue(Integer.parseInt(taskCount));
			List<Saojie> listSaojie= saojieService.findBySalesman(salesmanService.findSalesmanbyId(salesmanid));
			if(listSaojie.size()>0){
				entity.setOrder(listSaojie.size()-1);
				entity.setParent(listSaojie.get(0));
				if(listSaojie.size()==1){
					entity.setStatus(SaojieStatus.PENDING);
				}
			}else{
				entity.setOrder(0);
			}
			saojieService.saveSaojie(entity);
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findAllSaojie", method = RequestMethod.POST)
	public ResponseEntity<List<Saojie>> findAllSaojie(String userName){
		List<Saojie> listTaskSaojie=saojieService.findAllSaojie();
		
		return new ResponseEntity<List<Saojie>>(listTaskSaojie,HttpStatus.OK);
	}
	
	
}
