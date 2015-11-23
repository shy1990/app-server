package com.wangge.app.server.controller;

import java.text.ParseException;
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

import com.google.common.collect.Lists;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.service.RegionService;
import com.wangge.app.server.service.SalesmanManagerService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.service.TaskSaojieService;
import com.wangge.common.entity.Region;

@RestController
@RequestMapping(value = "/v1/task")
public class TaskController {
//
//	private static final Logger logger = Logger
//			.getLogger(TaskController.class);
//	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//
//	@Resource
//	private SalesmanService salesmanService;
//	@Resource
//	private SalesmanManagerService sms;
//	@Resource
//	private RegionService res;
	@Resource
	private TaskSaojieService tss;
		
		/**
		 * 
		 * 功能: 添加扫街任务
		 * 详细： 	
		 * 作者： 	Administrator
		 * 版本：  1.0
		 * 日期：  2015年11月12日上午11:33:38
		 *
		 */
		@RequestMapping(value = "/addTask", method = RequestMethod.POST)
		public ResponseEntity<String> addTask(String taskName,String salesmanid,String regionid,String taskStart,String taskEnd,String taskCount,String taskDes,String userName ) {
			
			
			System.out.println(taskName+salesmanid+regionid+taskDes+taskEnd+taskStart+userName);
			
			
			
			
//			TaskSaojie ts=new TaskSaojie();
//			TaskSaojie entity = new TaskSaojie();
//			List<Region> listRegion =new ArrayList<Region>();
//			entity.setDescription(taskDes);
//			try {
//				entity.setEndTime(sdf.parse(taskEnd));
//				entity.setStartTime(sdf.parse(taskStart));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			entity.setName(taskName);
//			
//			entity.setSalesman(salesmanService.findByid(salesmanid));
//			System.out.println(userName.trim());
//			entity.setCreateBy(sms.findByUsername(userName.trim()));
//			if(regionid.contains(",")){
//				for(int i=0;i<regionid.split(",").length;i++){
//					listRegion.add(res.findRegion(regionid.split(",")[i]));
//				}
//				entity.setRegions(listRegion);
//			}else{
//				entity.setRegions(Lists.newArrayList(res.findRegion(regionid)));
//			}
//			
//			entity.setTargets(new TaskTarget("最小数量",true,Float.parseFloat(taskCount)));
//			//List<TaskSaojie> listTaskSaojie=tss.findTaskSJbysalesman(salesmanService.findByid(salesmanid));
//			tss.addSaojieTask(entity);
//			List<TaskSaojie> listTaskSaojie=tss.findTaskSJbysalesman(salesmanService.findByid(salesmanid));
//			if(listTaskSaojie.size()>1){
//				for(TaskSaojie tasksaojie:listTaskSaojie){
//					if(null==tasksaojie.getNext()&&!entity.getId().equals(tasksaojie.getId())){
//						tasksaojie.setNext(entity);
//						tss.addSaojieTask(tasksaojie);
//					}
//				}
//			}else{
//				entity.setStatus(entity.getStatus().PENDING);
//				tss.addSaojieTask(entity);
//			}
			return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
		
		
//		@RequestMapping(value = "/findAllTask", method = RequestMethod.POST)
//		public ResponseEntity<List<Saojie>> findAllTask(String userName){
//			List<Saojie> listTaskSaojie=tss.findBycreateBy(sms.findByUsername(userName));
//			
//			return new ResponseEntity<List<Saojie>>(listTaskSaojie,HttpStatus.OK);
//		}
//		
//		@RequestMapping(value = "/upstatus", method = RequestMethod.POST)
//		public ResponseEntity<Map<String, Object> > upstatus(String taskid,String userid){
//
//			Saojie sj = tss.findByTaskId(taskid);
//			sj.setStatus(sj.getStatus().MANUAL_AGREE);
//		     tss.saveTask(sj);
//		     if(sj.getNext()!=null){
//		    	 sj.getNext().setStatus(sj.getStatus().PENDING);
//		    	 tss.saveTask(sj.getNext());
//		     }
//		     Map<String, Object>  map=new HashMap<String, Object>();
//		     map.put("status", sj.getStatus());
//		     if(sj.getNext()!=null){
//		     map.put("nextid", sj.getOrder());
//		     }
//			return new ResponseEntity<Map<String, Object> >(map,HttpStatus.OK);
//		}
}
