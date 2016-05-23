package com.wangge.app.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.Region;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.event.afterDailyEvent;
import com.wangge.app.server.pojo.Color;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.SaojieDataRepository;
import com.wangge.app.server.repositoryimpl.ActiveImpl;
import com.wangge.app.server.repositoryimpl.DateInterval;
import com.wangge.app.server.repositoryimpl.PickingImpl;
import com.wangge.app.server.service.AssessService;
import com.wangge.app.server.service.DataSaojieService;
import com.wangge.app.server.service.RegistDataService;
import com.wangge.app.server.service.RegistService;
import com.wangge.app.server.service.SalesmanService;

@RestController
@RequestMapping(value = "/v1")
public class RegistDataController {

	private static final Logger logger = Logger.getLogger(RegistDataController.class);

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
	@Resource
	private SaojieDataRepository dataSaojieRepository;
	@Resource
	private ActiveImpl apl;
	@Resource
	private PickingImpl ppl;
	@Resource
	private DateInterval dateInterval;
	@Resource
	private ApplicationContext cxt;

	/**
	 * 
	 * @Description: 获取已注册和未注册店铺
	 * @param @param
	 *            regionId
	 * @param @return
	 * @return ResponseEntity<Map<String,List<SaojieData>>>
	 * @author peter
	 * @date 2015年12月5日
	 * @version V2.0
	 */
	@RequestMapping(value = "/{regionId}/regist_data", method = RequestMethod.GET)
	public ResponseEntity<List<SaojieData>> list(@PathVariable("regionId") String regionId) {

		List<SaojieData> Data = dataSaojieService.getSaojieDataByregion(regionId);
	  List<SaojieData> list = new ArrayList<SaojieData>();
		
	  for(SaojieData sj : Data){
	      SaojieData sjdata = new SaojieData();
        sjdata.setId(sj.getId());
        sjdata.setImageUrl(sj.getImageUrl());
        sjdata.setName(sj.getName());
        sjdata.setCoordinate(sj.getCoordinate());
        sjdata.setDescription(sj.getDescription());
        // sjdata.setRegion(sj.getRegion());
        if(sj.getRegistData() != null){
         String memberId = sj.getRegistData().getMemberId();
          sjdata.setRegistId(sj.getRegistData().getId());
          sjdata.setDateInterval(dateInterval.examDateInterval(memberId));
          sjdata.setColorStatus(getPercent(memberId).getNum());
          sjdata.setIncSize(getPickingSize(memberId));
        }else{
          sjdata.setDateInterval(-1);
          sjdata.setColorStatus(Color.gray.getNum());
         // sjdata.setIncSize(1);
        }
        
       
        list.add(sjdata);
	  }
		return new ResponseEntity< List<SaojieData>>(list, HttpStatus.OK);
	}
	
	/**
	 * 
	* @Title: getPercent 
	* @Description: TODO(根据当前请求日期到月初所请求区域所有注册商家的二次提货量的商家总和返回请求区域的销售状态值(以颜色区分当前区域的销售情况)) 
	* @param @param area
	* @param @return    设定文件 
	* @return Color    返回类型 
	* @throws
	 */
	 private Color getPercent(String memberId){
	    Double a =  apl.examTwiceShop(memberId);
	    if(a > 0){
	      return Color.green;
	    }else {
	      Double c = apl.examOneceShop(memberId);
	      if(c > 0){
	          return Color.yellow;
	      }
          return Color.black;
	    }
	 }
	 /**
	  * 
	 * @Title: getPickingSize 
	 * @Description: TODO(根据当前请求日期到月初请求区域的商家的提货量判断是否为大客户) 
	 * @param @param area
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	  */
	 private int getPickingSize(String memberId){
	   int totalItenNum = ppl.examPickingNum(memberId);
	   if(totalItenNum > 20){
	     return 2;
	   }
	   return 1;
	 }

	/**
	 * 
	 * @Description: 添加注册店铺数据
	 * @param @param
	 *            region
	 * @param @param
	 *            jsons
	 * @param @return
	 * @return ResponseEntity<Json>
	 * @author peter
	 * @date 2015年12月5日
	 * @version V2.0
	 */
	@RequestMapping(value = "/{regionId}/{saojieId}/regist_data", method = RequestMethod.POST)
	public ResponseEntity<Json> add(@PathVariable("regionId") Region region, @PathVariable("saojieId") String saojieId,
			@RequestBody JSONObject jsons) {
		Json json = new Json();
		try {
			String userId = jsons.getString("userId");
			String counterNumber = jsons.getString("counterNumber");// 柜台数
			String loginAccount = jsons.getString("loginAccount");//商城会员注册手机号
			String clerk = jsons.getString("clerk");// 营业人数
			String length = jsons.getString("length");
			String width = jsons.getString("width");
			String imageUrl = jsons.getString("imageurl");
			String imageUrl1 = jsons.getString("imageurl1");
			String imageUrl2 = jsons.getString("imageurl2");
			String imageUrl3 = jsons.getString("imageurl3");
			String description = jsons.getString("description");
			int isPrimaryAccount = jsons.getIntValue("isPrimary");
			String childId = jsons.getString("childId");
			String coordinates = jsons.getString("coordinate");
			String accId = null;
			// Assess assess = assessService.findBySalesman(userId);
			Salesman salesman = salesmanService.findSalesmanbyId(userId);
			RegistData data = new RegistData(loginAccount, imageUrl, length, width, imageUrl1, imageUrl2, imageUrl3);
			data.setRegion(region);
			data.setSalesman(salesman);
			data.setCreatetime(new Date());
			data.setCounterNumber(counterNumber);
			data.setClerk(clerk);
			data.setDescription(description);
			Map<String, String> member = registDataService.findMemberInfo(loginAccount);
			if (member != null && !"".equals(member)) {
			  List<RegistData> listRegistdata=registDataService.findByLoginAccount(loginAccount);
			  if(listRegistdata.size()>=1){
			    json.setMsg("输入的账号已存在");
	        json.setSuccess(false);
	        return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
			  }
				data.setConsignee(member.get("CONSIGNEE"));
				data.setReceivingAddress(member.get("ADDRESS"));
				data.setPhoneNum(member.get("MOBILE"));
				data.setShopName(member.get("SHOPNAME"));
				data.setMemberId(member.get("MEMBERID"));
				data.setIsPrimaryAccount(isPrimaryAccount);
				if(isPrimaryAccount == 0){
				  accId = userId;
				}else{
				  accId = childId;
				}
				data.setAccountId(accId);
				RegistData registData = registDataService.addRegistData(data);
				
				// 更新扫街
				SaojieData sjData = dataSaojieService.findBySaojieData(Long.parseLong(saojieId));
				sjData.setRegistData(registData);
				sjData.setDescription(description);
				sjData.setCoordinate(coordinates);
				dataSaojieService.addDataSaojie(sjData,salesman);
				cxt.publishEvent(new afterDailyEvent(region.getId(),userId,member.get("SHOPNAME"),coordinates,isPrimaryAccount,childId,3));
				json.setId(String.valueOf(registData.getId()));
				json.setSuccess(true);
				json.setMsg("保存成功！");
				return new ResponseEntity<Json>(json, HttpStatus.CREATED);
			}else{
				json.setMsg("与商城登录帐号不匹配,请重新输入!");
				json.setSuccess(false);
				return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			json.setMsg("保存异常!");
			return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 
	 * @Description: 修改店铺录入信息
	 * @param @param
	 *            jsons
	 * @param @return
	 * @return ResponseEntity<Json>
	 * @author peter
	 * @date 2015年12月5日
	 * @version V2.0
	 */
	@RequestMapping(value = "/update_registData", method = RequestMethod.POST)
	public ResponseEntity<Json> updateDataSaojie(@RequestBody JSONObject jsons) {
		String registDataId = jsons.getString("id");
		String counterNumber = jsons.getString("counterNumber");// 柜台数
		String loginAccount = jsons.getString("loginAccount");
		String clerk = jsons.getString("clerk");// 营业人数
		String length = jsons.getString("length");
		String width = jsons.getString("width");
		String imageUrl1 = jsons.getString("imageurl1");
		String imageUrl2 = jsons.getString("imageurl2");
		String imageUrl3 = jsons.getString("imageurl3");
		String description = jsons.getString("description");
		Json json = new Json();
		RegistData dataRegist = registDataService.findRegistDataById(Long.parseLong(registDataId));
		if (dataRegist != null && !"".equals(dataRegist)) {
			dataRegist.setCounterNumber(counterNumber);
			dataRegist.setLoginAccount(loginAccount);
			dataRegist.setClerk(clerk);
			dataRegist.setStore_length(length);
			dataRegist.setStore_width(width);
			dataRegist.setImageUrl1(imageUrl1);
			dataRegist.setImageUrl2(imageUrl2);
			dataRegist.setImageUrl3(imageUrl3);
			dataRegist.setDescription(description);
			Map<String, String> member = registDataService.findMemberInfo(loginAccount);
			if (member != null && !"".equals(member)) {
				dataRegist.setConsignee(member.get("CONSIGNEE"));
				dataRegist.setReceivingAddress(member.get("ADDRESS"));
				dataRegist.setPhoneNum(member.get("MOBILE"));
				dataRegist.setShopName(member.get("SHOPNAME"));
			}
			try {
				registDataService.addRegistData(dataRegist);
				SaojieData sjData = dataSaojieService.findByRegistData(dataRegist);
				sjData.setDescription(description);
				dataSaojieRepository.save(sjData);
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
	 * @Title: registDataInfo @Description: TODO(获取一条注册店铺信息) @param @param
	 * registDataId @param @return 设定文件 @return ResponseEntity<RegistData>
	 * 返回类型 @throws
	 */
	@RequestMapping(value = "/{registDataId}/infoRegist", method = RequestMethod.GET)
	public ResponseEntity<RegistData> registDataInfo(@PathVariable("registDataId") String registDataId) {
		RegistData dataRegist = registDataService.findRegistDataById(Long.parseLong(registDataId));
		RegistData rd = null;
		if (dataRegist != null && !"".equals(dataRegist)) {
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

	@RequestMapping(value = "/rd/registData", method = RequestMethod.POST)
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
				} else {
					m.put("userid", null);
				}
				clist.add(m);
			}
		}

		return new ResponseEntity<List<Map<String, Object>>>(clist, HttpStatus.OK);
	}
}

