package com.wangge.app.server.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.service.RegionService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.service.SaojieDataService;
import com.wangge.app.server.service.SaojieService;
import com.wangge.common.entity.Region;

@RestController
@RequestMapping(value = "/v1/task/")
public class TaskController {
//
//	private static final Logger logger = Logger
//			.getLogger(TaskController.class);
//	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//
//	@Resource
//	private SalesmanService salesmanService;
	@Resource
	private SalesmanService sms;
	@Resource
	private RegionService res;
	@Resource
	private SaojieDataService sds;
	@Resource
	private SaojieService sjs;
	
	
		
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
		
		
		@RequestMapping(value = "/findAllSaojie", method = RequestMethod.POST)
		public ResponseEntity<List<Saojie>> findAllTask(String userid){
			List<Saojie> listTaskSaojie=sjs.findAllSaojie();
			
			return new ResponseEntity<List<Saojie>>(listTaskSaojie,HttpStatus.OK);
		}
		
		@RequestMapping(value = "/findTaskByUserId", method = RequestMethod.POST)
		public ResponseEntity<List<Map<String,Object>>> findTaskByUserId(String userid){
		 Salesman man=sms.findSalesmanbyId(userid.trim());
//		 Json json = new Json();
			Collection<Saojie> listSjid=sjs.findBySalesman(man);
		
			Collection<Region> listRegion=res.findRegiondbyParentid(man.getRegion().getId());
			 List<SaojieData> sdList = sds.findsjidById(listRegion);
			
			List<Map<String,Object>> sdmap = new ArrayList<Map<String,Object>>();
			for(SaojieData sj:sdList){
				Map<String,Object> map = new HashMap<String,Object>();
//				sjbuf.append("{").append("\"").append("coordinate").append("\"").append(":").append("\"").append(sj.getCoordinate()).append("\",")
//				.append("\"").append("description").append("\"").append(":").append("\"").append(sj.getDescription()).append("\",")
//				.append("\"").append("id").append("\"").append(":").append("\"").append(sj.getId()).append("\",")
//				.append("\"").append("name").append("\"").append(":").append("\"").append(sj.getName()).append("\",")
//				.append("\"").append("imgurl").append("\"").append(":").append("\"").append(sj.getImageUrl()).append("\"}");
				String pointStr = sj.getCoordinate(); 
				String lat=pointStr.split("-")[0];
 		  		String lag=pointStr.split("-")[1];
// 		  		String url="http://api.map.baidu.com/geocoder/v2/?ak=702632E1add3d4953d0f105f27c294b9&callback=renderReverse&location="+lag+","+lat+"&output=json&pois=1";
// 		  		String jsonString = JWtoAdrssUtil.getdata(url);
// 		  	    String jsonstr=jsonString.substring(0,jsonString.length()-1);
// 		  	    String address = jsonstr.substring(jsonstr.indexOf("formatted_address")+20,jsonstr.indexOf("business")-3);
 		  		map.put("coordinate", sj.getCoordinate());
				map.put("description", sj.getDescription());
				map.put("id", sj.getId());
				map.put("imageUrl", sj.getImageUrl());
				map.put("taskname", sj.getName());
				map.put("regionname", sj.getRegion().getName());
//				map.put("address", address);
				sdmap.add(map);
			}
//			json.setObj(sdmap);
//			String json = JSONObject.fromObject(sdmap).toString();
//			sjbuf.append("]}");
			return new ResponseEntity<List<Map<String,Object>>>(sdmap,HttpStatus.OK);
		}
		
		@RequestMapping(value = "/upstatus", method = RequestMethod.POST)
		public ResponseEntity<Map<String, Object> > upstatus(String taskid){
			Saojie sj = sjs.findSapjiebyId(Long.valueOf(taskid.trim()).longValue());
			sj.setStatus(sj.getStatus().AGREE);
			sjs.saveSaojie(sj);
		     if(sj.getOrder()!=null){
		    	 Integer norder = sj.getOrder()+1;
		    	 Saojie saojie = sjs.findNextSapjiebyId(sj.getSalesman().getId(), norder);
		    	 if(saojie!=null){
		    		 saojie.setStatus(sj.getStatus().PENDING);
			    	 sjs.saveSaojie(saojie); 
		    	 }
		     }
		     Map<String, Object>  map=new HashMap<String, Object>();
		     map.put("status", sj.getStatus());
		     if(sj.getOrder()!=null){
		     map.put("nextid", sj.getOrder());
		     }
			return new ResponseEntity<Map<String, Object> >(map,HttpStatus.OK);
		}
		
		
		
}
