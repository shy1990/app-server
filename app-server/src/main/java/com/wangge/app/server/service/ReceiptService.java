package com.wangge.app.server.service;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.Receipt;
import com.wangge.app.server.entity.dto.ReceiptDto;
import com.wangge.app.server.repository.ReceiptRepository;
import com.wangge.app.server.vo.ReceiptVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReceiptService {
	@Resource
	private ReceiptRepository receiptRepository;
	@Resource
	private OrderSignforService orderSignforService;

	public ReceiptVo findByOrderNo(String orderno) throws ParseException {
		
		ReceiptVo vo = createReceiptVo(orderno);
		
		return vo;
	}

	public void addReceipt(Receipt r) {
		receiptRepository.save(r);
		
	}

	@Transactional(rollbackFor=Exception.class)
	public ReceiptVo addOrUpdateReceipt(JSONObject jsons) throws Exception {
		String accountId = jsons.getString("accountId");
		Float amountCollected = jsons.getFloat("amountCollected");
		int  receiptType = jsons.getInteger("billType");
		String orderno = jsons.getString("orderNum");
		Float actualPayNum = jsons.getFloat("actualPayNum");
		OrderSignfor orderSignfor	= orderSignforService.findbyOrderNum(orderno);
		Float arrears = orderSignfor.getArrears();
		if(orderSignfor != null){
			 if(arrears-amountCollected >= 0){
				 if(receiptType== 1){
						orderSignfor.setArrears(actualPayNum-amountCollected);
						orderSignfor.setPayAmount(amountCollected);
						orderSignfor.setUpdateTime(new Date());
						orderSignfor =	updateBillStatus(amountCollected, actualPayNum,
								orderSignfor);
						createReceipt(accountId, amountCollected ,   receiptType,
								orderno);
				}else{
					    
					orderSignfor.setArrears(arrears-amountCollected);
					orderSignfor.setPayAmount(orderSignfor.getPayAmount()+amountCollected);
					orderSignfor =	updateBillStatus(amountCollected, arrears,
							orderSignfor);
					createReceipt(accountId, amountCollected ,   receiptType,
							orderno);
						
				}
				   orderSignforService.saveOrderSignfor(orderSignfor);
			    }else{
			    	throw new RuntimeException("输入金额大于尾款");
			    }
			 
		}
		return createReceiptVo(orderno);
		
	}

	private OrderSignfor updateBillStatus(Float amountCollected, Float arrears,
			OrderSignfor orderSignfor) {
		if(arrears-amountCollected == 0){
			orderSignfor.setBillStatus(0);//0 整单已付
			orderSignfor.setOverTime(new Date());
		}else if(arrears-amountCollected > 0){
			orderSignfor.setBillStatus(2);//2 部分未付
		}
		return orderSignfor;
	}
	

	private ReceiptVo createReceiptVo(String orderno) throws ParseException{
		OrderSignfor orderSignfor = orderSignforService.findbyOrderNum(orderno);
		
		ReceiptVo vo = new ReceiptVo();
		vo.setArrears(orderSignfor.getArrears());
		vo.setPayAmount(orderSignfor.getPayAmount());
		
		vo.setReceipts(createReceiptDtoList(orderno));
		return vo;
	}
	
	
	private List<ReceiptDto> createReceiptDtoList(String orderno) throws ParseException{
		List<Object[]> objects = receiptRepository.findReceiptVoByOrderNo(orderno);
		List<ReceiptDto> receiptsDto = new ArrayList<ReceiptDto>();
		for(int i= 0;i<objects.size();i++){
			ReceiptDto dto = new ReceiptDto();
			dto.setUserName(objects.get(i)[0]+ "");
			dto.setAmountCollected(Float.parseFloat(objects.get(i)[1]+""));
			dto.setReceiptType(Integer.parseInt(objects.get(i)[2]+""));
			dto.setCreateTime(objects.get(i)[3]+"");
			receiptsDto.add(dto);
		}
		return receiptsDto;
	}
   /**
    * 新增一条收款明细
    * @param accountId
    * @param amountCollected
    * @param
    * @param orderno
    */
	private void createReceipt(String accountId, Float amountCollected,
			int receiptType, String orderno) {
		Receipt receipt = new Receipt(amountCollected, receiptType, new Date(), accountId, orderno);
		receiptRepository.save(receipt);
	}
    

}
