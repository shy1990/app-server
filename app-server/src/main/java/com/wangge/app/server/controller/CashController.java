package com.wangge.app.server.controller;

import com.wangge.app.server.pojo.CashPart;
import com.wangge.app.server.pojo.CashShopGroup;
import com.wangge.app.server.repository.WaterOrderDetailRepository;
import com.wangge.app.server.service.CashService;
import com.wangge.app.util.JsonResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/cash")
public class CashController {

	private static final Logger logger = Logger.getLogger(CashController.class);
	@Resource
	private CashService cashService;
	@Resource
	private WaterOrderDetailRepository waterOrderDetailRepository;

	/**
	 * 现金订单购物车
	 *
	 * @param request
	 * @param userId
	 * @return
	 */
//	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
//	@ResponseBody
//	public ResponseEntity<JsonResponse<List<CashPart>>> getCsahList(HttpServletRequest request,
//	                                                                @PathVariable("userId") String userId) {
//		//
//		JsonResponse<List<CashPart>> cashJson = new JsonResponse<>();
//		try {
//
//			List<CashPart> cashlist = cashService.findByUserId(userId);
//			if (cashlist.size() > 0) {
//				cashJson.setResult(cashlist);
//				cashJson.setSuccessMsg("操作成功");
//			} else {
//				cashJson.setResult(null);
//				cashJson.setSuccessMsg("未查到相关记录");
//			}
//		} catch (Exception e) {
//			logger.info(e.getMessage());
//		}
//		return new ResponseEntity<>(cashJson, HttpStatus.OK);
//	}
	/**
	 * 现金订单购物车
	 *
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<JsonResponse<List<CashShopGroup>>> getCsahList_(HttpServletRequest request,
	                                                                     @PathVariable("userId") String userId) {
		//
		JsonResponse<List<CashShopGroup>> cashJson = new JsonResponse<>();
		try {

			List<CashShopGroup> cashlist = cashService.findByUserId_(userId);
			if (cashlist.size() > 0) {
				cashJson.setResult(cashlist);
				cashJson.setSuccessMsg("操作成功");
			} else {
				cashJson.setResult(null);
				cashJson.setSuccessMsg("未查到相关记录");
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return new ResponseEntity<>(cashJson, HttpStatus.OK);
	}

	/**
	 * 结算收现金订单产生流水单
	 * @param userId
	 * @param cashIds
	 * @return
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.POST)
	public ResponseEntity<JsonResponse<String>> OverCash(@PathVariable("userId") String userId,
	                                                     @RequestParam String cashIds) {
		JsonResponse<String> json = new JsonResponse<>();
		try {
			String serialNo = cashService.cashToWaterOrder(userId, cashIds);
			if("订单已存在".equals(serialNo)){
				json.setErrorMsg("订单已结算");
				return new ResponseEntity<>(json, HttpStatus.OK);
			}else if (StringUtils.isNotEmpty(serialNo)) {
				json.setResult(serialNo);
				json.setSuccessMsg("结算成功");
				return new ResponseEntity<>(json, HttpStatus.OK);
			}
			json.setErrorMsg("操作失败");

		} catch (Exception e) {
			logger.info("结算订单失败", e);
		}
		return new ResponseEntity<>(json, HttpStatus.OK);
	}
}
