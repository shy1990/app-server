package com.wangge.app.server.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.Receipt;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.service.OrderSignforService;
import com.wangge.app.server.service.ReceiptService;
import com.wangge.app.server.vo.ReceiptVo;

@RestController
@RequestMapping("/v1/bill")
public class BillController {
	@Resource
	private ReceiptService receiptService;
	@Resource
	private OrderSignforService orderSignforService;
	
	@RequestMapping(value="/addOrEditBill",method=RequestMethod.POST)
	public  ResponseEntity<ReceiptVo> addOrUpdateReceipt(@RequestBody JSONObject jsons) throws Exception{
		ReceiptVo ReceiptVo = receiptService.addOrUpdateReceipt(jsons);
		return new ResponseEntity<ReceiptVo>(ReceiptVo,HttpStatus.OK);
	}
	
	
	/**
	 * 
	* @Title: settlement 
	* @Description: TODO(结清订单) 
	* @param @param jsons
	* @param @return    设定文件 
	* @return ResponseEntity<Void>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/settlementBill",method = RequestMethod.POST)
	public ResponseEntity<Json>  settlement(@RequestBody JSONObject jsons){
		Json json = new Json();
		
		orderSignforService.settlement(jsons);
		json.setMsg("success");
		json.setCode(0);
		return new ResponseEntity<Json>(json,HttpStatus.OK);
	}
	
	@RequestMapping(value="/queryBillList/{userId}/{day}",method = RequestMethod.POST)
	public ResponseEntity<Void> queryBillList(@PathVariable("userId")String userId,@PathVariable("day")String day,@RequestBody JSONObject jsons, @RequestParam(defaultValue="10",required=false,value="pageSize") int pageSize){
		
		 Page<OrderSignfor> pages = orderSignforService.getBillList(userId,day,jsons,pageSize);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	/**
	 * 获取总欠款(今日，昨日，历史)
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/queryArrears/{userId}/",method = RequestMethod.POST)
	public ResponseEntity<Map<String, Float>> queryArrears(@PathVariable("userId")String userId){
		Map<String, Float> Arrears = orderSignforService.queryArrears(userId);
		return  new ResponseEntity<Map<String, Float>>(Arrears,HttpStatus.OK);
	}

}
