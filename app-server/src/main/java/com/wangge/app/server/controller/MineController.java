package com.wangge.app.server.controller;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.Order;
import com.wangge.app.server.entity.OrderItem;
import com.wangge.app.server.repository.MessageRepository;
import com.wangge.app.server.repository.OrderRepository;
import com.wangge.app.server.repositoryimpl.OrderImpl;
import com.wangge.app.server.service.MessageService;
import com.wangge.app.server.service.OrderService;
import com.wangge.app.server.util.SortUtil;
import com.wangge.app.server.vo.Apply;
import com.wangge.app.server.vo.Exam;
import com.wangge.app.server.vo.OrderPub;
import com.wangge.common.repository.RegionRepository;

@RestController
@RequestMapping(value = "/v1/mine")
public class MineController {
	
	private static final Logger logger = LoggerFactory.getLogger(MineController.class);
	
	@Autowired
	private OrderImpl opl ;
	@Resource
	private MessageService mr;
	
	
	@Resource
	private OrderService or;
	@Autowired
	private RegionRepository rr;
	/**
	 * 
	 * @Description: 根据业务手机号订单号判断该订单是否属于该业务员并返回订单详情
	 * @param @param json
	 * @param @return   
	 * @return ResponseEntity<List<OrderPub>>  
	 * @throws
	 * @author changjun
	 * @date 2015年11月12日
	 */
	@RequestMapping(value = "/checkByOrderNum",method = RequestMethod.POST)
	public ResponseEntity<JSONObject> checkByOrderNum(@RequestBody  JSONObject json){
//		String mobile = json.getString("mobile");
		String ordernum = json.getString("ordernum");
		String regionId = json.getString("regionId");
		
		JSONObject jo = new JSONObject();
		Order order = or.findOne(ordernum);
		
		if(order!=null){
			if(regionId.equals(order.getRegion().getId())){
				if(opl.checkByOrderNum(ordernum)){
					StringBuffer sb = new StringBuffer();
					for (OrderItem item : order.getItems()) {
						sb.append(item.getName()+" ");
					}
					jo.put("username", order.getShopName());
					jo.put("createTime", order.getCreateTime());
					jo.put("orderNum", order.getId());
					jo.put("shipStatus", order.getStatus().getName());
					jo.put("amount", order.getAmount());
					jo.put("goods", sb);
					jo.put("state", "正常订单");
					return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
				}else{
					jo.put("state", "该订单已签收,请核实");
					return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
				}
			}else{
				jo.put("state", "该订单不属于此地区,请核实");
				return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
			}
		}
		jo.put("state", "未查询相关信息,请重新扫描");
		return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);

	}
	
	
	/**
	 * 
	 * @Description: 根据业务手机号查询所属订单的派送状态
	 * @param @param json
	 * @param @return   
	 * @return ResponseEntity<List<OrderPub>>  
	 * @throws
	 * @author changjun
	 * @date 2015年11月11日
	 */
	@RequestMapping(value = "/orderStatusList",method = RequestMethod.POST)
	public ResponseEntity<List<OrderPub>> orderStatusList(@RequestBody  JSONObject json){
		String regionId = json.getString("regionId");
		PageRequest pageRequest = SortUtil.buildPageRequest(json.getInteger("pageNumber"), json.getInteger("pageSize"),"order");
		List<OrderPub> list = or.findByRegion(rr.findById(regionId), pageRequest);
		return new ResponseEntity<List<OrderPub>>(list , HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @Description: 业务签收后更新订单状态
	 * @param @param json
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年11月21日
	 */
	@RequestMapping(value = "/updateOrderStatus",method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateOrderStatus(@RequestBody  JSONObject json){
		String status =  opl.updateOrderShipStateByOrderNum(json.getString("ordernum"), json.getString("status"));
		JSONObject jo = new JSONObject();
		jo.put("state", status);
		return new ResponseEntity<JSONObject>( jo, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @Description: 考核状态
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/examStatus",method = RequestMethod.POST)
	public ResponseEntity<Exam> examStatus(@RequestBody	JSONObject json){
		String username = json.getString("username");
		Exam ex = new Exam();
		ex.setStage("第一阶段");
		ex.setBeginDate(new Date());
		ex.setDoneNum("7");
		ex.setEndDate(new Date());
		ex.setKpiNum("10");
		double rate = 7D / 10D;
		DecimalFormat df = new DecimalFormat("0.00%");   
		ex.setRate(df.format(rate));
		ex.setRemark("考核审核标准：二次提货客户达到10家即为达标");
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("郭屯镇", 5);
		map.put("随官屯", 2);
		ex.setMap(map);
		
		return new ResponseEntity<Exam>(ex, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 我的收益
	 * @param @param json
	 * @param @return   
	 * @return ResponseEntity<Object>  
	 * @throws
	 * @author changjun
	 * @date 2015年11月3日
	 */
	@RequestMapping(value = "/myEarn",method = RequestMethod.POST)
	public ResponseEntity<Object> myEarn(@RequestBody JSONObject json){
		
		return new ResponseEntity<Object>(null,HttpStatus.OK);
	}
	/**
	 * 
	 * @Description:  任务-收货款
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/takeGoodsMoney",method = RequestMethod.POST)
	public ResponseEntity<List<OrderPub>> takeGoodsMoney(@RequestBody  JSONObject json){
		String username = json.getString("username");
		//dao查询未收款订单
		List<OrderPub> list = new ArrayList<OrderPub>();
//		OrderPub order = new OrderPub();
//		order.setOrderNum("123456789");
//		order.setUsername(username);
//		order.setAddress("大桥镇");
//		order.setCreateTime(new Date());
//		order.setPayState("未支付");
//		list.add(order);
		return new ResponseEntity<List<OrderPub>>(list, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 未收款报备
	 * @param @param username
	 * @param @param orderNum
	 * @param @param reason
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/noTaskMoneyRemark",method = RequestMethod.POST)
	public ResponseEntity<Void> noTaskMoneyRemark(@RequestBody  JSONObject json){
		String username = json.getString("username");
		String orderNum = json.getString("orderNum");
		String reason = json.getString("reason");
		System.out.println(username+":"+orderNum+":"+reason);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 申请调价
	 * @param @param username
	 * @param @param town
	 * @param @param goodsname
	 * @param @param amount
	 * @param @param reason
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/applyUpdatePrice",method = RequestMethod.POST)
	public ResponseEntity<Void> applyUpdatePrice(@RequestBody  JSONObject json){
		String username = json.getString("username");
		String area = json.getString("area");
		String goodsname = json.getString("goodsname");
		String amount = json.getString("amount");
		String reason = json.getString("reason");
		//保存
		return new ResponseEntity<Void>( HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 申请调价列表状态
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/applyPriceState",method = RequestMethod.POST)
	public ResponseEntity<List<Apply>> applyPriceState(@RequestBody  JSONObject json){
		String username = json.getString("username");
		List<Apply> list = new ArrayList<Apply>();
		Apply apply = new Apply();
		List<String> ls = new ArrayList<String>();
		ls.add("随官屯");
		ls.add("丁官屯");
		apply.setArea(ls);
		apply.setAmount(10D);
		apply.setApplyDate(new Date());
		apply.setGoodsName("小米2A");
		apply.setReason("同行竞争");
		apply.setApplyName(username);
		list.add(apply);
		return new ResponseEntity<List<Apply>>(list, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 我的足记
	 * @param @param username
	 * @param @param potints
	 * @param @param beginDate
	 * @param @param endDate
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/saveMoveMark",method = RequestMethod.POST)
	public ResponseEntity<Object> saveMoveMark(String username,String[] potints,Date beginDate,Date endDate){
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description:查看我的足迹列表
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/selMoveMarkList",method = RequestMethod.POST)
	public ResponseEntity<Object> selMoveMarkList(String username){
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
}
