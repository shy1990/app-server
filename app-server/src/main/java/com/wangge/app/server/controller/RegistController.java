package com.wangge.app.server.controller;

import com.wangge.app.server.config.http.HttpRequestHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1")
public class RegistController {

	@Resource
	private HttpRequestHandler httpRequestHandler;
	@Value("${app-interface.url}")
	private String url;
	/**
	 * 
	 * @Description: 业务人员注册区域
	 * @param @param userId
	 * @return ResponseEntity<Map<String,List<RegionVo>>>
	 * @author peter
	 * @date 2015年12月1日
	 * @version V2.0
	 */
	@RequestMapping(value="/{id}/regist",method=RequestMethod.GET)
	public ResponseEntity<Object> salesmanRegions(@PathVariable("id") String userId){
		return httpRequestHandler.exchange(url + "/{id}/regist", HttpMethod.GET,null,null,userId);
	}
	
	/**
   * 
   * @Description: 查询开发数量
   * @param @param userId
   * @param @return
   * @return ResponseEntity<Map<String,Integer>>
   * @author peter
   * @date 2015年12月2日
   * @version V2.0
   */
  @RequestMapping(value="/{id}/registNum",method=RequestMethod.GET)
  public ResponseEntity<Object> registNum(@PathVariable("id") String userId){
		return httpRequestHandler.exchange(url + "/{id}/registNum", HttpMethod.GET,null,null,userId);
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
		public ResponseEntity<Object> addRegist(String registName,String salesmanid,String regionid,String registStart,String registEnd,String registCount,String registDes,String userName ) {
			return httpRequestHandler.exchange(url + "/addRegist", HttpMethod.POST,null,registName,salesmanid,regionid,registStart,registEnd,registCount,registDes,userName,null);
	}
		
		
		/*@RequestMapping(value = "/findAllRegist", method = RequestMethod.POST)
		public ResponseEntity<List<Regist>> findAllTask(){
			List<Regist> listRegist=registService.findAllRegist();
			
			return new ResponseEntity<List<Regist>>(listRegist,HttpStatus.OK);
		}*/
		
		/*@RequestMapping(value = "/findAllRd", method = RequestMethod.POST)
		public ResponseEntity<List<RegistData>> findAllRd(String registid){
			List<RegistData> listRd=registService.findAllRd(registid);
			
			return new ResponseEntity<List<RegistData>>(listRd,HttpStatus.OK);
		}*/
	/*	
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
		}*/
		
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
