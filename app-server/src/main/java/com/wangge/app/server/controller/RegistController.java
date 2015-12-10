package com.wangge.app.server.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.wangge.app.server.entity.Regist;
import com.wangge.app.server.entity.Regist.RegistStatus;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.entity.Saojie.SaojieStatus;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.service.RegionService;
import com.wangge.app.server.service.RegistService;
import com.wangge.app.server.service.SalesmanManagerService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.service.SaojieDataService;
import com.wangge.app.server.service.SaojieService;
import com.wangge.app.server.service.TaskSaojieService;
import com.wangge.app.server.util.JWtoAdrssUtil;
import com.wangge.common.entity.Region;

@RestController
@RequestMapping(value = "/v1/regist/")
public class RegistController {

	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//
	@Resource
	private SalesmanService salesmanService;
	@Resource
	private RegistService registService;
	@Resource
	private RegionService regionService;
		
		/**
		 * 
		 * 功能: 添加注册任务
		 * 详细： 	
		 * 作者： 	Administrator
		 * 版本：  1.0
		 * 日期：  2015年11月12日上午11:33:38
		 *
		 */
		@RequestMapping(value = "/addRegist", method = RequestMethod.POST)
		public ResponseEntity<String> addTask(String registName,String salesmanid,String regionid,String registStart,String registEnd,String registCount,String registDes,String userName ) {
			
			Regist regist = new Regist();
			regist.setDescription(registDes);
			try {
				regist.setExpiredTime(sdf.parse(registEnd));
				regist.setBeginTime(sdf.parse(registStart));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			regist.setName(registName);
			regist.setSalesman(salesmanService.findSalesmanbyId(salesmanid));
			regist.setRegion(regionService.findRegion(regionid));
			regist.setMinValue(Integer.parseInt(registCount));
			List<Regist> listSaojie= registService.findBySalesman(salesmanService.findSalesmanbyId(salesmanid));
			if(listSaojie.size()>0){
				regist.setOrder(listSaojie.size()-1);
				regist.setParent(listSaojie.get(0));
				if(listSaojie.size()==1){
					regist.setStatus(RegistStatus.PENDING);
				}
			}else{
				regist.setOrder(0);
			}
			registService.saveRegist(regist);
			return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
		
		
//		@RequestMapping(value = "/findAllRegist", method = RequestMethod.POST)
//		public ResponseEntity<List<Region>> findAllTask(String userid){
//			List<Region> listRegion=regionService.findAllRegion();
//			
//			return new ResponseEntity<List<Region>>(listRegion,HttpStatus.OK);
//		}
//		
		@RequestMapping(value = "/findRegistByUserId", method = RequestMethod.POST)
		public ResponseEntity<List<Map<String,Object>>> findTaskByUserId(String userid){
		 Salesman man=registService.findSalesmanbyId(userid.trim());
//		 Json json = new Json();
			Collection<Regist> listSjid=registService.findBySalesman(man);
			List<RegistData> rdList = registService.findreidById(listSjid);
			List<Map<String,Object>> sdmap = new ArrayList<Map<String,Object>>();
			for(RegistData rd:rdList){
				Map<String,Object> map = new HashMap<String,Object>();
//				sjbuf.append("{").append("\"").append("coordinate").append("\"").append(":").append("\"").append(sj.getCoordinate()).append("\",")
//				.append("\"").append("description").append("\"").append(":").append("\"").append(sj.getDescription()).append("\",")
//				.append("\"").append("id").append("\"").append(":").append("\"").append(sj.getId()).append("\",")
//				.append("\"").append("name").append("\"").append(":").append("\"").append(sj.getName()).append("\",")
//				.append("\"").append("imgurl").append("\"").append(":").append("\"").append(sj.getImageUrl()).append("\"}");
//				String pointStr = rd.getCoordinate(); 
//				String lat=pointStr.split("-")[0];
// 		  		String lag=pointStr.split("-")[1];
// 		  		String url="http://api.map.baidu.com/geocoder/v2/?ak=702632E1add3d4953d0f105f27c294b9&callback=renderReverse&location="+lag+","+lat+"&output=json&pois=1";
// 		  		String jsonString = JWtoAdrssUtil.getdata(url);
// 		  	    String jsonstr=jsonString.substring(0,jsonString.length()-1);
// 		  	    String address = jsonstr.substring(jsonstr.indexOf("formatted_address")+20,jsonstr.indexOf("business")-3);
 		  		map.put("shopname", rd.getName());
				map.put("consignee", rd.getConsignee());
				map.put("id", rd.getId());
				map.put("regionname", rd.getRegion().getName());
				map.put("address", rd.getReceivingAddress());
				sdmap.add(map);
			}
//			json.setObj(sdmap);
//			String json = JSONObject.fromObject(sdmap).toString();
//			sjbuf.append("]}");
			return new ResponseEntity<List<Map<String,Object>>>(sdmap,HttpStatus.OK);
		}
		
//		@RequestMapping(value = "/upstatus", method = RequestMethod.POST)
//		public ResponseEntity<Map<String, Object> > upstatus(String taskid){
//			Saojie sj = sjs.findSapjiebyId(Long.valueOf(taskid.trim()).longValue());
//			sj.setStatus(sj.getStatus().AGREE);
//			sjs.saveSaojie(sj);
//		     if(sj.getOrder()!=null){
//		    	 Integer norder = sj.getOrder()+1;
//		    	 Saojie saojie = sjs.findNextSapjiebyId(sj.getSalesman().getId(), norder);
//		    	 if(saojie!=null){
//		    		 saojie.setStatus(sj.getStatus().PENDING);
//			    	 sjs.saveSaojie(saojie); 
//		    	 }
//		     }
//		     Map<String, Object>  map=new HashMap<String, Object>();
//		     map.put("status", sj.getStatus());
//		     if(sj.getOrder()!=null){
//		     map.put("nextid", sj.getOrder());
//		     }
//			return new ResponseEntity<Map<String, Object> >(map,HttpStatus.OK);
//		}
		
		
		
}
