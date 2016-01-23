package com.wangge.app.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.wangge.app.server.entity.Assess;
import com.wangge.app.server.entity.Regist;
import com.wangge.app.server.entity.Regist.RegistStatus;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.service.AssessService;
import com.wangge.app.server.service.DataSaojieService;
import com.wangge.app.server.service.RegistDataService;
import com.wangge.app.server.service.RegistService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.common.entity.Region;

@RestController
@RequestMapping(value = "/v1")
public class RegistDataController {

	private static final Logger logger = Logger
			.getLogger(RegistDataController.class);

	@Resource
	private SalesmanService salesmanService;
	@Resource
	private RegistService registService;
	@Resource
	private DataSaojieService dataSaojieService;
	@Resource
	private RegistDataService registDataService;
	@Resource
  private AssessService assessService;
	
	/**
	 * 
	 * @Description: 获取已注册和未注册店铺
	 * @param @param regionId
	 * @param @return
	 * @return ResponseEntity<Map<String,List<SaojieData>>>
	 * @author peter
	 * @date 2015年12月5日
	 * @version V2.0
	 */
	@RequestMapping(value = "/{regionId}/regist_data", method = RequestMethod.GET)
	public ResponseEntity<Map<String,List<SaojieData>>> list(
			@PathVariable("regionId") String regionId) {

		List<SaojieData> Data = dataSaojieService
				.getSaojieDataByregion(regionId);
		Map<String, List<SaojieData>> result = Maps.newHashMap();
		List<SaojieData> listsj=new ArrayList<SaojieData>();
		List<SaojieData> listrg=new ArrayList<SaojieData>();
 		for(SaojieData sj:Data){
 			if(sj.getRegistData() != null && !"".equals(sj.getRegistData())){
 				SaojieData sjdata=new SaojieData();
 	 			sjdata.setId(sj.getId());
 	 			sjdata.setImageUrl(sj.getImageUrl());
 	 			sjdata.setName(sj.getName());
 	 			sjdata.setCoordinate(sj.getCoordinate());
 	 			sjdata.setDescription(sj.getDescription()==null?"":sj.getDescription());
// 	 			sjdata.setRegion(sj.getRegion());
 	 			sjdata.setRegistId(sj.getRegistData().getId());
 	 			listsj.add(sjdata);
 			}else{
 				SaojieData sjdata=new SaojieData();
 	 			sjdata.setId(sj.getId());
 	 			sjdata.setImageUrl(sj.getImageUrl());
 	 			sjdata.setName(sj.getName());
 	 			sjdata.setCoordinate(sj.getCoordinate());
 	 			sjdata.setDescription(sj.getDescription()==null?"":sj.getDescription());
// 	 			sjdata.setRegion(sj.getRegion());
 	 			listrg.add(sjdata);
 			}
 		}
 		result.put("regShop", listsj);
 		result.put("unregShop", listrg);
		return new ResponseEntity<Map<String,List<SaojieData>>>(result, HttpStatus.OK);
	}
	

	/**
	 * 
	 * @Description: 添加注册店铺数据
	 * @param @param region
	 * @param @param jsons
	 * @param @return
	 * @return ResponseEntity<Json>
	 * @author peter
	 * @date 2015年12月5日
	 * @version V2.0
	 */
	@RequestMapping(value = "/{regionId}/{saojieId}/regist_data", method = RequestMethod.POST)
	public ResponseEntity<Json> add(
			@PathVariable("regionId") Region region,
			@PathVariable("saojieId") String saojieId,
			@RequestBody JSONObject jsons) {
		Json json = new Json();
		try {
			String userId = jsons.getString("userId");
			String counterNumber=jsons.getString("counterNumber");//柜台数
			String loginAccount=jsons.getString("loginAccount");
			String clerk=jsons.getString("clerk");//营业人数
			String length=jsons.getString("length");
			String width= jsons.getString("width");
			String imageUrl=jsons.getString("imageurl");
			String imageUrl1=jsons.getString("imageurl1");
			String imageUrl2=jsons.getString("imageurl2");
			String imageUrl3=jsons.getString("imageurl3");
			String description=jsons.getString("description");
//			Assess assess  = assessService.findBySalesman(userId);
			Salesman salesman = salesmanService.findSalesmanbyId(userId);
				RegistData data = new RegistData(loginAccount, imageUrl,length, width, imageUrl1, imageUrl2, imageUrl3);
				data.setRegion(region);
				data.setSalesman(salesman);
				data.setCreatetime(new Date());
				data.setCounterNumber(counterNumber);
				data.setClerk(clerk);
				data.setDescription(description);
				Map<String,String> member = registDataService.findMemberInfo(loginAccount);
				if(member != null && !"".equals(member)){
					data.setConsignee(member.get("CONSIGNEE"));
					data.setReceivingAddress(member.get("ADDRESS"));
					data.setPhoneNum(member.get("MOBILE"));
					data.setShopName(member.get("SHOPNAME"));
				}
				RegistData registData = registDataService.addRegistData(data);
				
				//更新扫街
				SaojieData sjData =  dataSaojieService.findBySaojieData(Long.parseLong(saojieId));
				sjData.setRegistData(registData);
				dataSaojieService.addDataSaojie(sjData);
				json.setId(String.valueOf(registData.getId()));
				json.setSuccess(true);
				json.setMsg("保存成功！");
			return new ResponseEntity<Json>(json, HttpStatus.CREATED);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			json.setMsg("保存异常!");
			return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 
	 * @Description: 修改店铺录入信息
	 * @param @param jsons
	 * @param @return
	 * @return ResponseEntity<Json>
	 * @author peter
	 * @date 2015年12月5日
	 * @version V2.0
	 */
	@RequestMapping(value = "/update_registData", method = RequestMethod.POST)
	public ResponseEntity<Json> updateDataSaojie(@RequestBody JSONObject jsons) {
		String registDataId = jsons.getString("id");
		String counterNumber=jsons.getString("counterNumber");//柜台数
		String loginAccount=jsons.getString("loginAccount");
		String clerk=jsons.getString("clerk");//营业人数
		String length=jsons.getString("length");
		String width= jsons.getString("width");
		String imageUrl1=jsons.getString("imageurl1");
		String imageUrl2=jsons.getString("imageurl2");
		String imageUrl3=jsons.getString("imageurl3");
		String description=jsons.getString("description");
		Json json = new Json();
		RegistData dataRegist = registDataService.findRegistDataById(Long.parseLong(registDataId));
		if(dataRegist != null && !"".equals(dataRegist)){
			dataRegist.setCounterNumber(counterNumber);
			dataRegist.setLoginAccount(loginAccount);
			dataRegist.setClerk(clerk);
			dataRegist.setStore_length(length);
			dataRegist.setStore_width(width);
			dataRegist.setImageUrl1(imageUrl1);
			dataRegist.setImageUrl2(imageUrl2);
			dataRegist.setImageUrl3(imageUrl3);
			dataRegist.setDescription(description);
			Map<String,String> member = registDataService.findMemberInfo(loginAccount);
			if(member != null && !"".equals(member)){
				dataRegist.setConsignee(member.get("CONSIGNEE"));
				dataRegist.setReceivingAddress(member.get("ADDRESS"));
				dataRegist.setPhoneNum(member.get("MOBILE"));
				dataRegist.setShopName(member.get("SHOPNAME"));
			}
			try {
				registDataService.addRegistData(dataRegist);
				json.setSuccess(true);
				json.setMsg("修改成功！");
				return new ResponseEntity<Json>(json, HttpStatus.OK);
			} catch (Exception e) {
				logger.error("update_registData() occur error. ", e);
				e.printStackTrace();
				json.setMsg("修改异常！");
				return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);

			}
		} else {
			json.setMsg("修改失败！");
			return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
		}
	}
	
	/**
	 * 
	* @Title: registDataInfo 
	* @Description: TODO(获取一条注册店铺信息) 
	* @param @param registDataId
	* @param @return    设定文件 
	* @return ResponseEntity<RegistData>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/{registDataId}/infoRegist", method = RequestMethod.GET)
	public ResponseEntity<RegistData> registDataInfo(@PathVariable("registDataId") String registDataId) {
		RegistData dataRegist = registDataService.findRegistDataById(Long.parseLong(registDataId));
		RegistData rd = null;
		if(dataRegist != null && !"".equals(dataRegist)){
			rd = new RegistData();
			rd.setId(dataRegist.getId());
			rd.setClerk(dataRegist.getClerk());
			rd.setCounterNumber(dataRegist.getCounterNumber());
			rd.setDescription(dataRegist.getDescription());
			rd.setLoginAccount(dataRegist.getLoginAccount());
			rd.setStore_length(dataRegist.getStore_length());
			rd.setStore_width(dataRegist.getStore_width());
			rd.setImageUrl1(dataRegist.getImageUrl1());
			rd.setImageUrl2(dataRegist.getImageUrl2());
			rd.setImageUrl3(dataRegist.getImageUrl3());
			rd.setShopName(dataRegist.getShopName());
			rd.setPhoneNum(dataRegist.getPhoneNum());
			rd.setImageUrl(dataRegist.getImageUrl());
		}
		return new ResponseEntity<RegistData>(rd, HttpStatus.OK);
	}
}
