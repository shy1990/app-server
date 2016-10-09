package com.wangge.app.server.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.Receipt;
import com.wangge.app.server.repository.ReceiptRepository;

@Service
public class ReceiptService {
	@Resource
	private ReceiptRepository receiptRepository;
	@Resource
	private OrderSignforService orderSignforService;

	public JSONArray findByOrderNo(String orderno) {
		JSONArray jsonArray = null ;
		List<Receipt> list = receiptRepository.findByOrderno(orderno);
		if(list != null && list.size() > 0){
		    jsonArray = new JSONArray();
			jsonArray.add(list);
		}
		return jsonArray;
	}

	public void save(Receipt r) {
		receiptRepository.save(r);
		
	}

	@Transactional(rollbackFor=Exception.class)
	public void addOrUpdateReceipt(JSONObject jsons) {
		String accountId = jsons.getString("accountId");
		Float amountCollected = jsons.getFloat("amountCollected");
		int  accountType = jsons.getInteger("accountType");
		String orderno = jsons.getString("orderNum");
		Float actualPayNum = jsons.getFloat("actualPayNum");
		OrderSignfor orderSignfor	= orderSignforService.findbyOrderNum(orderno);
		
		if(orderSignfor != null){
			if(accountType== 1){
				orderSignfor.setArrears(actualPayNum-amountCollected);
				orderSignfor.setPayAmount(amountCollected);
				orderSignfor.setUpdateTime(new Date());
			}else{
				
				boolean flag = orderSignfor.getArrears()-amountCollected == 0 ? true : false;
				if(flag){
					orderSignfor.setArrears(orderSignfor.getArrears()-amountCollected);
					orderSignfor.setPayAmount(orderSignfor.getPayAmount()+amountCollected);
					orderSignfor.setOverTime(new Date());
				}
			}
			orderSignforService.saveOrderSignfor(orderSignfor);
			Receipt receipt = new Receipt(amountCollected, accountType, new Date(), accountId, orderno);
			receiptRepository.save(receipt);
		}
		
		
	}

}
