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

	public void addReceipt(Receipt r) {
		receiptRepository.save(r);
		
	}

	@Transactional(rollbackFor=Exception.class)
	public void addOrUpdateReceipt(JSONObject jsons) throws Exception {
		String accountId = jsons.getString("accountId");
		Float amountCollected = jsons.getFloat("amountCollected");
		int  billType = jsons.getInteger("billType");
		String orderno = jsons.getString("orderNum");
		Float actualPayNum = jsons.getFloat("actualPayNum");
		OrderSignfor orderSignfor	= orderSignforService.findbyOrderNum(orderno);
		Float arrears = orderSignfor.getArrears();
		if(orderSignfor != null){
			if(billType== 1){
					orderSignfor.setArrears(actualPayNum-amountCollected);
					orderSignfor.setPayAmount(amountCollected);
					orderSignfor.setUpdateTime(new Date());
			}else{
				    
				    if(arrears-amountCollected >= 0){
				    	orderSignfor.setArrears(arrears-amountCollected);
						orderSignfor.setPayAmount(orderSignfor.getPayAmount()+amountCollected);
						if(arrears-amountCollected == 0){
							orderSignfor.setOverTime(new Date());
						}
						createReceipt(accountId, amountCollected, billType,
								orderno);
				    }else{
				    	throw new RuntimeException("输入金额大于尾款");
				    }
					
			}
			orderSignforService.saveOrderSignfor(orderSignfor);
			
		}
		
		
	}
   /**
    * 新增一条收款明细
    * @param accountId
    * @param amountCollected
    * @param billType
    * @param orderno
    */
	private void createReceipt(String accountId, Float amountCollected,
			int billType, String orderno) {
		Receipt receipt = new Receipt(amountCollected, billType, new Date(), accountId, orderno);
		receiptRepository.save(receipt);
	}
    

}
