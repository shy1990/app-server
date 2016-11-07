package com.wangge.app.server.controller;


import com.wangge.app.server.entity.Cash;
import com.wangge.app.server.entity.WaterOrderCash;
import com.wangge.app.server.pojo.WaterOrderPart;
import com.wangge.app.server.service.CashService;
import com.wangge.app.server.service.WaterOrderService;
import com.wangge.app.util.JsonResponse;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/waterOrder")
public class WaterOrderController {
	private static final Logger logger = Logger.getLogger(WaterOrderController.class);

	private static final String SEARCH_OPERTOR = "sc_";

	@Resource
	private WaterOrderService waterOrderService;
	@Resource
	private CashService cashService;

	/**
	 * 结算后流水单号列表
	 *
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<JsonResponse<Page<WaterOrderPart>>> getWaterOrderList(
					@PathVariable("userId") String userId,
					@PageableDefault(page = 0, size = 10, sort = {"createDate", "payStatus"}, direction = Direction.DESC) Pageable pageable,
					HttpServletRequest request) {
		//
		JsonResponse<Page<WaterOrderPart>> waterOrderJson = new JsonResponse<>();
		try {
//			List<Order> orders = new ArrayList<>();
//			orders.add(new Order(Direction.DESC, "createDate"));
//			orders.add(new Order(Direction.ASC, "payStatus"));
//			Sort sort = new Sort(orders);
//			pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
			Page<WaterOrderPart> cashlist = waterOrderService.findByUserId(userId, pageable);
			if (cashlist.getContent().size() > 0) {
				waterOrderJson.setResult(cashlist);
				waterOrderJson.setSuccessMsg("操作成功");
			} else {
				waterOrderJson.setResult(null);
				waterOrderJson.setSuccessMsg("未查到相关记录");
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return new ResponseEntity<>(waterOrderJson, HttpStatus.OK);
	}

	/**
	 * 流水单详情
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<JsonResponse<WaterOrderPart>> getCsahList(@RequestParam(value = "seriaNo") String seriaNo,
	                                                                HttpServletRequest request) {
		//
		JsonResponse<WaterOrderPart> waterOrderJson = new JsonResponse<>();
		try {

			WaterOrderPart wop = waterOrderService.findBySerailNo(seriaNo);
			if (wop != null) {
				waterOrderJson.setResult(wop);
				waterOrderJson.setSuccessMsg("操作成功");
			} else {
				waterOrderJson.setResult(null);
				waterOrderJson.setSuccessMsg("未查到相关记录");
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return new ResponseEntity<>(waterOrderJson, HttpStatus.OK);
	}

	/**
	 * 更改流水单回调
	 * @param orderCash seriaNo流水单号
//	 * @param msg {"payDate":1477534294628,"payMoney":15465}payDate 时间戳payMoney 支付金额
	 * @return
	 */
	@RequestMapping(value = "/pay/{serialNo}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<JsonResponse<Boolean>> updateStatus(@PathVariable(value = "serialNo") WaterOrderCash orderCash, String payMoney,String payDate) {
		JsonResponse<Boolean> statusJson = new JsonResponse<>();
		try {
			System.out.println("============更改流水单回调=========="+payDate+":"+payMoney);
			Date payDate_ = new Date(Long.valueOf(payDate));
			statusJson.setResult(false);
			if(ObjectUtils.equals(null,orderCash)){
				statusJson.setErrorMsg("没有此流水单！");
				return new ResponseEntity<>(statusJson, HttpStatus.OK);
			}
			if(payDate==null||payMoney==null||"".equals(payMoney)){
				statusJson.setErrorMsg("缺少参数！");
				return new ResponseEntity<>(statusJson, HttpStatus.OK);
			}
			orderCash.setPayDate(payDate_);
			orderCash.setPayStatus(1);//更改状态：1-已付款；0-未付款
			orderCash.setPaymentMoney(Float.valueOf(payMoney));
			waterOrderService.save(orderCash); //保存流水单
			List<Cash> cashList = new ArrayList<>();
			orderCash.getOrderDetailList().forEach(waterOrderDetail -> {
				Cash cash = waterOrderDetail.getCash();
				//设置支付时间
				cash.setPayDate(payDate_);
				//修改状态
				cash.setStatus(2);
				cashList.add(cash);

			});
			cashService.save(cashList);
			statusJson.setResult(true);
			statusJson.setSuccessMsg("支付正常");
		}catch (Exception e){
			logger.info("serialNo: "+orderCash.getSerialNo()+" 流水支付单回调失败",e);
			statusJson.setErrorMsg("支付异常");
			return new ResponseEntity<>(statusJson, HttpStatus.OK);
		}
		return new ResponseEntity<>(statusJson, HttpStatus.OK);

	}


}
