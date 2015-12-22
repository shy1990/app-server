package com.wangge.app.server.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Visit;
import com.wangge.app.server.entity.Visit.VisitStatus;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.VisitRepository;
import com.wangge.app.server.service.RegistDataService;
import com.wangge.app.server.service.TaskVisitService;
import com.wangge.app.server.vo.VisitVo;

@RestController
@RequestMapping(value = "/v1")
public class VisitTaskController {
	private static final Logger logger = Logger.getLogger(VisitTaskController.class);
	
	@Autowired
	private TaskVisitService taskVisitService;
	@Resource
	private VisitRepository vr;
	@Resource
	private RegistDataService registDataService;
	
	Json json = new Json();
	
	/**
	 * 
	 * @Description: 获取拜访任务列表
	 * @param @param salesman
	 * @param @return
	 * @return ResponseEntity<List<Visit>>
	 * @author peter
	 * @date 2015年12月11日
	 * @version V2.0
	 */
	@RequestMapping(value = "/task/{userId}/visitList",method = RequestMethod.GET)
	public ResponseEntity<List<VisitVo>> visitList(@PathVariable("userId")Salesman salesman) {
		List<VisitVo> result = Lists.newArrayList();
		List<Visit> tv = taskVisitService.findBySalesman(salesman);
		for(Visit visit : tv){
			VisitVo visitVo = new VisitVo();
			visitVo.setId(String.valueOf(visit.getId()));
			visitVo.setShopName(visit.getRegistData().getShopName());
			visitVo.setAddress(visit.getRegistData().getReceivingAddress());
			visitVo.setImageurl(visit.getRegistData().getImage_Url());
			visitVo.setStatus(visit.getStatus());
			result.add(visitVo);
		}
		return new ResponseEntity<List<VisitVo>>(result, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @Description: 拉取一条拜访记录
	 * @param @param visitId
	 * @param @return
	 * @return ResponseEntity<Json>
	 * @author Peter
	 * @date 2015年12月11日
	 * @version V2.0
	 */
	@RequestMapping(value = "/task/{visitId}/infoVisit",method = RequestMethod.GET)
	public ResponseEntity<VisitVo> visitInfo(@PathVariable("visitId") String visitId) {
		Visit taskVisit = taskVisitService.findByVisitId(Long.parseLong(visitId));
		VisitVo visitVo = new VisitVo();
		if(taskVisit != null && !"".equals(taskVisit)){
			if(VisitStatus.FINISHED.equals(taskVisit.getStatus())){
				visitVo.setShopName(taskVisit.getRegistData().getShopName());
				visitVo.setAddress(taskVisit.getAddress());
				visitVo.setSummary(taskVisit.getSummary());
				visitVo.setStatus(taskVisit.getStatus());
				String imageurl1=taskVisit.getImageurl1();
				String imageurl2=taskVisit.getImageurl2();
				String imageurl3=taskVisit.getImageurl3();
				if(imageurl1 != null && !"".equals(imageurl1)){
					visitVo.setImageurl1(imageurl1);
				}
				if(imageurl2 != null && !"".equals(imageurl2)){
					visitVo.setImageurl2(imageurl2);
				}
				if(imageurl3 != null && !"".equals(imageurl3)){
					visitVo.setImageurl3(imageurl3);
				}
				return new ResponseEntity<VisitVo>(visitVo, HttpStatus.OK);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: 保存提交的拜访记录
	 * @param @param jsons
	 * @param @return
	 * @return ResponseEntity<Json>
	 * @author peter
	 * @date 2015年12月11日
	 * @version V2.0
	 */
	@RequestMapping(value = "/task/addVisit",method = RequestMethod.POST)
	public ResponseEntity<Json> addVisit(@RequestBody JSONObject jsons) {
		try {
			if(jsons.containsKey("visitId")){
				String visitId=jsons.getString("visitId");
				Visit taskVisit = taskVisitService.findByVisitId(Long.parseLong(visitId));
				if(taskVisit != null && !"".equals(taskVisit)){
					taskVisit.setAddress(jsons.getString("address"));
					taskVisit.setStatus(VisitStatus.FINISHED);
					taskVisit.setExpiredTime(new Date());
					taskVisit.setSummary(jsons.getString("summary"));
					if(jsons.containsKey("imageurl1")){
						String imageurl1=jsons.getString("imageurl1");
						taskVisit.setImageurl1(imageurl1);
					}
					if(jsons.containsKey("imageurl2")){
						String imageurl2 = jsons.getString("imageurl2");
						taskVisit.setImageurl2(imageurl2);
					}
					if(jsons.containsKey("imageurl3")){
						String imageurl3 = jsons.getString("imageurl3");
						taskVisit.setImageurl3(imageurl3);
					}
					taskVisitService.save(taskVisit);
					json.setMsg("保存成功!");
					return new ResponseEntity<Json>(json, HttpStatus.CREATED);
				}else{
					json.setMsg("保存失败!");
					return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
				}
			}else{
				Visit taskVisit = new Visit();
				RegistData rd = registDataService.findRegistDataById(Long.parseLong(jsons.getString("registId")));
				taskVisit.setRegistData(rd);
				taskVisit.setSalesman(rd.getSalesman());
				taskVisit.setAddress(jsons.getString("address"));
				taskVisit.setStatus(VisitStatus.FINISHED);
				taskVisit.setExpiredTime(new Date());
				taskVisit.setSummary(jsons.getString("summary"));
				if(jsons.containsKey("imageurl1")){
					String imageurl1=jsons.getString("imageurl1");
					taskVisit.setImageurl1(imageurl1);
				}
				if(jsons.containsKey("imageurl2")){
					String imageurl2 = jsons.getString("imageurl2");
					taskVisit.setImageurl2(imageurl2);
				}
				if(jsons.containsKey("imageurl3")){
					String imageurl3 = jsons.getString("imageurl3");
					taskVisit.setImageurl3(imageurl3);
				}
				taskVisitService.save(taskVisit);
				json.setMsg("保存成功!");
				return new ResponseEntity<Json>(json, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("addVisit() occur error...");
			json.setMsg("保存异常!");
			return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
		}
		
	}
	
}
