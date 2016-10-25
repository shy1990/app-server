package com.wangge.app.server.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.wangge.app.server.util.DateUtil;
import com.wangge.app.server.vo.BillHistoryVo;
import com.wangge.app.server.vo.BillVo;
import com.wangge.app.server.vo.OrderVo;
import com.wangge.app.server.vo.ReceiptVo;

@RestController
@RequestMapping("/v1/bill")
public class BillController {
	@Resource
	private ReceiptService receiptService;
	@Resource
	private OrderSignforService orderSignforService;
	
	@RequestMapping(value="/addOrEditBill",method=RequestMethod.POST)
	public  ResponseEntity<Json> addOrUpdateReceipt(@RequestBody JSONObject jsons) {
		Json json = new Json();
		ReceiptVo ReceiptVo;
		try {
			ReceiptVo = receiptService.addOrUpdateReceipt(jsons);
			json.setObj(ReceiptVo);
			json.setCode(0);
			return new ResponseEntity<Json>(json,HttpStatus.OK);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			json.setCode(1);
			return new ResponseEntity<Json>(json,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	/**
	 * @throws Exception 
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
		try {
			orderSignforService.settlement(jsons);
			json.setMsg("订单结清");
			json.setCode(0);
			return new ResponseEntity<Json>(json,HttpStatus.OK);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			json.setCode(1);
			return new ResponseEntity<Json>(json,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	/**
	 * 昨日今日对账单列表
	 * @param userId
	 * @param day
	 * @param jsons
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryBillList/{userId}/{day}", method = RequestMethod.GET)
	public ResponseEntity<BillVo> queryBillList(
			@PathVariable("userId") String userId,
			@PathVariable("day") String day,
			@RequestParam(defaultValue = "1", required = false, value = "pageNumber") int pageNumber,
			@RequestParam(defaultValue = "10", required = false, value = "pageSize") int pageSize) {

		BillVo vo = orderSignforService.getBillList(userId, day, pageNumber,
				pageSize);

		return new ResponseEntity<BillVo>(vo, HttpStatus.OK);
	}
	/**
	 * 获取总欠款(今日，昨日，历史)
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/queryArrears/{userId}",method = RequestMethod.GET)
	public ResponseEntity<Map<String, Float>> queryArrears(@PathVariable("userId")String userId){
		Map<String, Float> Arrears = orderSignforService.queryArrears(userId);
		return  new ResponseEntity<Map<String, Float>>(Arrears,HttpStatus.OK);
	}
	/**
	 * 历史欠款列表
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryBillHistoryList/{userId}/history", method = RequestMethod.GET)
	public ResponseEntity<BillHistoryVo> queryBillHistoryList(
			@PathVariable("userId") String userId,
			@RequestParam(defaultValue = "1", value = "pageNumber", required = false) int pageNumber,
			@RequestParam(defaultValue = "10", required = false, value = "pageSize") int pageSize) {

		BillHistoryVo historyVo = orderSignforService.queryBillHistory(userId, pageNumber, pageSize);

		return new ResponseEntity<BillHistoryVo>(historyVo,HttpStatus.OK);
	}
	
	/**
	 * 根据条件查询对账单 列表
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/queryBillList/{userId}", method = RequestMethod.GET)
	public ResponseEntity<BillVo> queryBill(
			@PathVariable("userId") String userId,
			@RequestParam(defaultValue = "1", value = "pageNumber", required = false) int pageNumber,
			@RequestParam(defaultValue="10",value="pageSize",required=false) int pageSize,
			@RequestParam(defaultValue="",value="createTime",required=false)String createTime,
			@RequestParam(defaultValue="0",value="isPrimary",required=false)int isPrimary,
			@RequestParam(defaultValue="0",value="orderStatus",required=false)int orderStatus,
			@RequestParam(defaultValue="0",value="billStatus",required=false)int billStatus
			) throws ParseException {
			BillVo vo = orderSignforService.getBillList(userId,createTime,pageNumber,pageSize,isPrimary,billStatus,orderStatus);
			return new ResponseEntity<BillVo>(vo,HttpStatus.OK);
		
	}
	
	/**
	 * 查询某一天对账单列表
	 * @param userId
	 * @param date
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryBillHistoryOnedayList/{userId}/{date}", method = RequestMethod.GET)
	public ResponseEntity<BillVo> queryBillHistoryOnedayList(
			@PathVariable("userId") String userId,
			@PathVariable("date") String date,
			@RequestParam(defaultValue = "1", value = "pageNumber", required = false) int pageNumber,
			@RequestParam(defaultValue = "10", value = "pageSize", required = true) int pageSize) {
		BillVo vo = orderSignforService.getBillListOneDay(userId, date,
				pageNumber, pageSize);

		return new ResponseEntity<BillVo>(vo, HttpStatus.OK);
	}
	

}
