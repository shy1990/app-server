package com.wangge.app.server.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Regist;
import com.wangge.app.server.entity.Regist.RegistStatus;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.service.AssessService;
import com.wangge.app.server.service.RegionService;
import com.wangge.app.server.service.RegistService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.vo.RegionVo;

@RestController
@RequestMapping(value = "/v1/regist/")
public class RegistController {

	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//
	@Resource
	private SalesmanService salesmanService;
	@Resource
	private RegistService registService;
	@Resource
  private AssessService assessService;
	@Resource
	private RegionService regionService;
	/**
	 * 
	 * @Description: 业务人员注册区域
	 * @param @param salesman
	 * @return ResponseEntity<Map<String,List<RegionVo>>>
	 * @author peter
	 * @date 2015年12月1日
	 * @version V2.0
	 */
	@RequestMapping(value="/{id}/regist",method=RequestMethod.GET)
	public ResponseEntity<Map<String,List<RegionVo>>> salesmanRegions(@PathVariable("id") Salesman salesman){

	     Map<String, List<RegionVo>>   regionMap = assessService.getRegistRegion(salesman);
	   
		return new ResponseEntity<Map<String,List<RegionVo>>>(regionMap,HttpStatus.OK);
	}
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
		public ResponseEntity<String> addRegist(String registName,String salesmanid,String regionid,String registStart,String registEnd,String registCount,String registDes,String userName ) {
			
			Regist regist = new Regist();
			regist.setDescription(registDes);
			try {
				regist.setExpiredTime(sdf.parse(registEnd));
				regist.setBeginTime(sdf.parse(registStart));
			} catch (ParseException e) {
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
		
		
		@RequestMapping(value = "/findAllRegist", method = RequestMethod.POST)
		public ResponseEntity<List<Regist>> findAllTask(){
			List<Regist> listRegist=registService.findAllRegist();
			
			return new ResponseEntity<List<Regist>>(listRegist,HttpStatus.OK);
		}
		
		@RequestMapping(value = "/findAllRd", method = RequestMethod.POST)
		public ResponseEntity<List<RegistData>> findAllRd(String registid){
			List<RegistData> listRd=registService.findAllRd(registid);
			
			return new ResponseEntity<List<RegistData>>(listRd,HttpStatus.OK);
		}
		
		@RequestMapping(value = "/findRegistByUserId", method = RequestMethod.POST)
		public ResponseEntity<List<Map<String,Object>>> findTaskByUserId(String userid){
		 Salesman man=registService.findSalesmanbyId(userid.trim());
			Collection<Regist> listSjid=registService.findBySalesman(man);
			List<RegistData> rdList = registService.findreidById(listSjid);
			List<Map<String,Object>> sdmap = new ArrayList<Map<String,Object>>();
			for(RegistData rd:rdList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("phone", rd.getPhoneNum());
				map.put("loginer", rd.getLoginAccount());
				map.put("counterNumber", rd.getCounterNumber());
 		  		map.put("shopname", rd.getShopName());
				map.put("consignee", rd.getConsignee());
				map.put("id", rd.getId());
				map.put("regionname", rd.getRegion().getName());
				map.put("address", rd.getReceivingAddress());
				sdmap.add(map);
			}
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
