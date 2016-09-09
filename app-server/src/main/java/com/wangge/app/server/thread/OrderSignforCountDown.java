package com.wangge.app.server.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.service.OrderService;
import com.wangge.app.server.service.OrderSignforService;
import com.wangge.app.server.util.DateUtil;

public class OrderSignforCountDown implements Runnable {
	
	public static void main(String[] args) {
	//	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Thread cd;
		/*cd = new Thread(new OrderSignforCountDown(
				new Date(), "20160804184839557"));
		cd.start();*/

	}
	
	private Date signForTime;
	
	private String orderNo;
	
	private OrderService oderService;
	
	

	public OrderSignforCountDown(Date signForTime,String orderNo, OrderService oderService) {
		this.signForTime = signForTime;
		this.orderNo = orderNo;
		this.oderService = oderService;
	}


	@Override
	public void run() {
		
		int i = 5*60;
			//		oderService.exsistShipStatus(orderNo);
		while (DateUtil.isCheckExpires(signForTime, 5*60L)) {
			try {
				i--;
				if(i==0){
					oderService.exsistShipStatus(orderNo);
				}
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
			
	}
	
}
