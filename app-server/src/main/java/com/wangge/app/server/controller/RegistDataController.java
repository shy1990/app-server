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
import com.wangge.app.server.entity.Regist;
import com.wangge.app.server.entity.Regist.RegistStatus;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.service.DataSaojieService;
import com.wangge.app.server.service.RegistDataService;
import com.wangge.app.server.service.RegistService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.common.entity.Region;

@RestController
@RequestMapping(value = "/v1/rd")
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

	/**
	 * 
	 * @Description: 获取已注册
	 * @param @param
	 * @param @return
	 * @return ResponseEntity<List<RegistData>>
	 * @author store
	 * 
	 * @version V2.0
	 */
	@RequestMapping(value = "/registData", method = RequestMethod.POST)
	public ResponseEntity<List<Map<String, Object>>> list() {

		List<RegistData> registList = registDataService.findAll();

		List<Map<String, Object>> clist = new ArrayList<Map<String, Object>>();
		if (registList != null && registList.size() > 0) {
			for (RegistData rd : registList) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", rd.getId());
				m.put("shopname", rd.getShopName());
				m.put("phone", rd.getPhoneNum());
				m.put("consignee", rd.getConsignee());
				m.put("address", rd.getReceivingAddress());
				if (rd.getSalesman() != null) {
					m.put("userid", rd.getSalesman().getId());
				}else{
					m.put("userid", null);
				}
				clist.add(m);
			}
		}

		return new ResponseEntity<List<Map<String, Object>>>(clist,
				HttpStatus.OK);
	}

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
	// @RequestMapping(value = "/{regionId}/regist_data", method =
	// RequestMethod.GET)
	// public ResponseEntity<Map<String,List<SaojieData>>> list(
	// @PathVariable("regionId") String regionId) {
	//
	// List<SaojieData> Data = dataSaojieService
	// .getSaojieDataByregion(regionId);
	// Map<String, List<SaojieData>> result = Maps.newHashMap();
	// List<SaojieData> listsj=new ArrayList<SaojieData>();
	// List<SaojieData> listrg=new ArrayList<SaojieData>();
	// for(SaojieData sj:Data){
	// if(sj.getRegistData() != null && !"".equals(sj.getRegistData())){
	// SaojieData sjdata=new SaojieData();
	// sjdata.setId(sj.getId());
	// sjdata.setImageUrl(sj.getImageUrl());
	// sjdata.setName(sj.getName());
	// sjdata.setCoordinate(sj.getCoordinate());
	// sjdata.setDescription(sj.getDescription()==null?"":sj.getDescription());
	// //sjdata.setRegion(sj.getRegion());
	// listsj.add(sjdata);
	// }else{
	// SaojieData sjdata=new SaojieData();
	// sjdata.setId(sj.getId());
	// sjdata.setImageUrl(sj.getImageUrl());
	// sjdata.setName(sj.getName());
	// sjdata.setCoordinate(sj.getCoordinate());
	// sjdata.setDescription(sj.getDescription()==null?"":sj.getDescription());
	// //sjdata.setRegion(sj.getRegion());
	// listrg.add(sjdata);
	// }
	// }
	// result.put("regShop", listsj);
	// result.put("unregShop", listrg);
	// return new ResponseEntity<Map<String,List<SaojieData>>>(result,
	// HttpStatus.OK);
	// }
	//
	/**
	 * 
	 * @Description: 添加和获取注册店铺数据
	 * @param @param region
	 * @param @param jsons
	 * @param @return
	 * @return ResponseEntity<Json>
	 * @author peter
	 * @date 2015年12月5日
	 * @version V2.0
	 */
	@RequestMapping(value = "/{regionId}/{saojieId}/regist_data", method = RequestMethod.POST)
	public ResponseEntity<Json> add(@PathVariable("regionId") Region region,
			@PathVariable("saojieId") Long saojieId,
			@RequestBody JSONObject jsons) {
		Json json = new Json();
		int taskValue = 0;
		int dataRegistNum = 0;
		List<Regist> child = new ArrayList<Regist>();
		String userId = jsons.getString("userId");
		String name = jsons.getString("shopname");
		String consignee = jsons.getString("consignee");
		String receivingAddress = jsons.getString("receivingAddress");
		String counterNumber = jsons.getString("counterNumber");
		String loginAccount = jsons.getString("loginAccount");
		String phoneNum = jsons.getString("phoneNum");
		Regist regist = registDataService.findByRegion(region);
		Salesman salesman = salesmanService.findSalesmanbyId(userId);
		if (regist != null && !"".equals(regist)) {
			RegistData data = new RegistData(name, consignee, receivingAddress,
					counterNumber, loginAccount, phoneNum);
			data.setRegist(regist);
			data.setRegion(regist.getRegion());
			data.setSalesman(salesman);
			data.setCreatetime(new Date());
			RegistData registData = registDataService.addRegistData(data);
			// 更新扫街
			SaojieData sjData = dataSaojieService.findBySaojieData(saojieId);
			sjData.setRegistData(registData);
			dataSaojieService.addDataSaojie(sjData);
			// 注册任务达标改状态
			taskValue = regist.getMinValue();
			dataRegistNum = registDataService.getDataCountByRegistId(regist
					.getId());
			if (taskValue == dataRegistNum) {
				regist.setStatus(RegistStatus.AGREE);
				registDataService.updateRegist(regist);
			}
			json.setMsg("保存成功！");
			json.setObj(registData);
		} else {
			json.setMsg("保存失败！");
		}

		return new ResponseEntity<Json>(json, HttpStatus.CREATED);
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
		String name = jsons.getString("shopname");
		String consignee = jsons.getString("consignee");
		String receivingAddress = jsons.getString("receivingAddress");
		String counterNumber = jsons.getString("counterNumber");
		String loginAccount = jsons.getString("loginAccount");
		String phoneNum = jsons.getString("phoneNum");
		Json json = new Json();
		RegistData dataRegist = registDataService.findRegistDataById(Long
				.parseLong(registDataId));
		if (dataRegist != null && !"".equals(dataRegist)) {
			dataRegist.setShopName(name);
			dataRegist.setConsignee(consignee);
			dataRegist.setReceivingAddress(receivingAddress);
			dataRegist.setCounterNumber(counterNumber);
			dataRegist.setLoginAccount(loginAccount);
			dataRegist.setPhoneNum(phoneNum);

			try {
				registDataService.addRegistData(dataRegist);
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
}
