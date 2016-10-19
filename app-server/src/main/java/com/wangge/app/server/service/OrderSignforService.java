package com.wangge.app.server.service;


import com.alibaba.fastjson.JSONObject;
import com.wangge.app.constant.OrderShipStatusConstant;
import com.wangge.app.server.entity.Cash;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.Receipt;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.event.afterSalesmanSignforEvent;
import com.wangge.app.server.event.afterSignforEvent;
import com.wangge.app.server.monthTask.service.MonthTaskServive;
import com.wangge.app.server.repository.OrderSignforRepository;
import com.wangge.app.server.repository.ReceiptRepository;
import com.wangge.app.server.repositoryimpl.OrderImpl;
import com.wangge.app.server.repositoryimpl.OrderSignforImpl;
import com.wangge.app.server.thread.OrderSignforCountDown;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class OrderSignforService {

  private Logger logger = Logger.getLogger(OrderSignforService.class);
  
 // private static final String url = "http://115.28.87.182:58081/v1/";
  private static final String url = "http://115.28.92.73:58080/v1/";
  @Resource
  private OrderSignforRepository osr;
  @Resource
  private CashService cs;

  @Resource
  private OrderImpl opl ;

  @Resource
  private OrderSignforImpl osi;
  @Resource
  private ApplicationContext ctx;
  @Resource
  private RegistDataService registDataService;
  @Resource
  private MonthTaskServive monthTaskServive;
  @Resource
  private OrderService orderService;
  
  @Resource
	private OrderService oderService;
  @Resource
  private ReceiptService receiptService;
  
  @Resource
	private ReceiptRepository receiptRepository;
	
  public void saveOrderSignfor(OrderSignfor xlsOrder) {
    osr.save(xlsOrder);

  }

  /**
   *
   * @Title: updateOrderSignforList
   * @Description: TODO(业务揽收)
   * @param @param fastMailNo
   * @param @param userPhone
   * @param @param signGeoPoint
   * @param @param isPrimaryAccount
   * @param @param userId
   * @param @param childId
   * @param @return    设定文件
   * @return Date    返回类型
   * @throws
   */
  @Transactional(readOnly=false,rollbackFor=Exception.class)
  public Date updateOrderSignforList(String fastMailNo,String userPhone,String signGeoPoint, int isPrimaryAccount,String userId,String childId) {
    Date date = new Date();
    String accountId = null;
    List<OrderSignfor> osList =   osr.findByUserPhoneAndFastmailNo(userPhone,fastMailNo);
            if(osList != null && osList.size() > 0){
                for(OrderSignfor os : osList){
                    os.setYewuSignforTime(date);
                    os.setYewuSignforGeopoint(signGeoPoint);
                    os.setOrderStatus(2);
                    os.setIsPrimaryAccount(isPrimaryAccount);
                    if(isPrimaryAccount==0){
                      accountId = userId;
                    }else{
                      accountId = childId;
                    }
                    os.setAccountId(accountId);
                    osr.save(os);
                     opl.updateOrderShipStateByOrderNum(os.getOrderNo(),OrderShipStatusConstant.SHOP_ORDER_SHIPSTATUS_YWSIGNEDFOR,null,null);
                  }
                ctx.publishEvent(new afterSalesmanSignforEvent( userId, signGeoPoint,  isPrimaryAccount, childId,5));
                return date;
            }
            return null;
  }


  /**
 * @throws Exception 
   * 
  * @Title: customSignforByPos 
  * @Description: TODO(客户pos签收) 
  * @param @param orderNo
  * @param @param userPhone
  * @param @param signGeoPoint
  * @param @param payType
  * @param @param smsCode
  * @param @param isPrimaryAccount
  * @param @param userId
  * @param @param childId
  * @param @param storePhone    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @Transactional(readOnly=false,rollbackFor=Exception.class)
  public void customSignforByPos(String orderNo, String userPhone,
      String signGeoPoint, int payType, String smsCode,int isPrimaryAccount,
      String userId,String childId,String accountId,String  storePhone, String walletPayNo) throws Exception  {
     
	
		  singOrder(orderNo, userPhone, signGeoPoint, payType, smsCode,
					isPrimaryAccount, userId, childId, accountId, storePhone);
		     
  }
  
  /**
   * 客户现金签收
   * @param orderNo
   * @param userPhone
   * @param signGeoPoint
   * @param payType
   * @param smsCode
   * @param isPrimaryAccount
   * @param childId
   * @param storePhone
   * @param userId
   * @param accountId
   * @param billStatus
   * @param amountCollected
   * @param walletPayNo
   * @param actualPayNum
   * @throws Exception
   */
  @Transactional(readOnly=false,rollbackFor=Exception.class)
  public void customSignforByCash(String orderNo,String userPhone,String signGeoPoint,int payType,String smsCode,int isPrimaryAccount,String childId,String storePhone,String userId,String accountId, int billStatus,Float amountCollected,String walletPayNo,Float actualPayNum) throws Exception{
  	 singOrder(orderNo, userPhone, signGeoPoint, payType, smsCode,
  				isPrimaryAccount, userId, childId, accountId, storePhone);
  	createBill(orderNo,userPhone,userId,accountId,billStatus,amountCollected,walletPayNo,actualPayNum);
  }

  /**
  *
  * @Title: updateOrderSignfor
  * @Description: TODO(客户拒签)
  * @param @param orderNo
  * @param @param userPhone
  * @param @param remark
  * @param @param signGeoPoint
  * @param @param isPrimaryAccount
  * @param @param userId
  * @param @param childId
  * @param @param storePhone    设定文件
  * @return void    返回类型
  * @throws
  */
  @Transactional(readOnly=false,rollbackFor=Exception.class)
  public void updateOrderSignfor(String orderNo, String userPhone, String remark,String signGeoPoint,int isPrimaryAccount,String userId,String childId,String  storePhone) throws Exception{
  	  OrderSignfor orderSignFor = findOrderSignFor(orderNo,userPhone);
  	  if(orderSignFor!= null){
  	        String accountId = null;
  	        orderSignFor.setCustomUnSignRemark(remark);
  	        orderSignFor.setCustomSignforTime(new Date());
  	        orderSignFor.setCustomSignforGeopoint(signGeoPoint);
  	        orderSignFor.setOrderStatus(4);
  	        orderSignFor.setIsPrimaryAccount(isPrimaryAccount);
  	        if(isPrimaryAccount==0){
  	          accountId = userId;
  	        }else{
  	          accountId = childId;
  	        }
  	        orderSignFor.setAccountId(accountId);
  	        osr.save(orderSignFor);
  	         opl.updateOrderShipStateByOrderNum(orderNo,"7");
  	         RegistData registData = registDataService.findByPhoneNum(storePhone);
  	         if(registData != null){
  	           monthTaskServive.saveExecution(registData.getId(), "客户拒签");
  	         }
  	           ctx.publishEvent(new afterSignforEvent( userId, signGeoPoint,  isPrimaryAccount, childId,7,storePhone));
  	     
  	    }else{
  	      throw new  RuntimeException("订单不存在!");
  	    }
  	     
  }
/**
 * 签收订单
 * @param orderNo
 * @param userPhone
 * @param signGeoPoint
 * @param payType
 * @param smsCode
 * @param isPrimaryAccount
 * @param userId
 * @param childId
 * @param accountId
 * @param storePhone
 * @throws Exception
 */
private void singOrder(String orderNo, String userPhone, String signGeoPoint,
		int payType, String smsCode, int isPrimaryAccount, String userId,
		String childId, String accountId, String storePhone) throws Exception{
	  updateOrderSingfor(orderNo, userPhone, signGeoPoint, payType, smsCode,
			isPrimaryAccount, accountId);
       createMonthTaskRecord(storePhone);
	    
       createOilRecord(signGeoPoint, isPrimaryAccount, userId, childId,
 			storePhone);
        updateMallOrder(orderNo,payType);
        if(1 == payType){
         	 startCountDown(orderNo,oderService);
         } 
}
/**
 * 更新订单
 * @param orderNo
 * @param userPhone
 * @param signGeoPoint
 * @param payType
 * @param smsCode
 * @param isPrimaryAccount
 * @param accountId
 */
private void updateOrderSingfor(String orderNo, String userPhone,
		String signGeoPoint, int payType, String smsCode, int isPrimaryAccount,
		String accountId) {
	OrderSignfor orderSignFor =  osr.findByOrderNoAndUserPhone(orderNo,userPhone);
       if(orderSignFor!= null){
           orderSignFor.setCustomSignforTime(new Date());
           orderSignFor.setCustomSignforGeopoint(signGeoPoint);
           orderSignFor.setOrderPayType(payType);
           orderSignFor.setOrderStatus(3);
           orderSignFor.setIsPrimaryAccount(isPrimaryAccount);
           if(smsCode != null && !"".equals(smsCode)){
             orderSignFor.setCustomSignforException(1);
           }else{
             orderSignFor.setCustomSignforException(0);
           }
          /* if(isPrimaryAccount==0){
             accountId = userId;
           }else{
             accountId = childId;
           }*/
           orderSignFor.setAccountId(accountId);
            osr.save(orderSignFor);
            
           
           /*//收现金
           try {
             if(2 == payType){
              // dealType = "现金支付";
               dealType = "cash";
               payStatus = OrderShipStatusConstant.SHOP_ORDER_PAYSTATUS_HAVETOPAY;
               Cash cash= new Cash(orderSignFor.getId(),userId);
               cs.save(cash);
<<<<<<< HEAD
               if(null!=walletPayNo){
=======
               System.out.println("walletPayNo"+walletPayNo);
               if(null!=walletPayNo&&!walletPayNo.equals("null")){
>>>>>>> branch 'youbufenzhi0523' of https://git.oschina.net/wgtechnology/app-server.git
            	   RestTemplate restTemplate = new RestTemplate();
            	   Map<String, Object> param = new HashMap<String, Object>();
                 param.put("status", "SUCCESS");
            	   String walletPayNoUrl = walletPayNo+"/status";
            	   restTemplate.put(url+walletPayNoUrl, param);
               }
              
             }
          } catch (Exception e) {
            logger.info("客户签收---->收现金--->bug:"+e.getMessage());
            throw new  IOException("收现金异常!"+e.getMessage());
          }*/
          
           // opl.updateOrderShipStateByOrderNum(orderNo,OrderShipStatusConstant.SHOP_ORDER_SHIPSTATUS_KHSIGNEDFOR,payStatus,dealType);
            
             
       }else{
         throw new  RuntimeException("订单不存在!");
       }
}

/**
 * 创建油补记录
 * @param signGeoPoint
 * @param isPrimaryAccount
 * @param userId
 * @param childId
 * @param storePhone
 */
private void createOilRecord(String signGeoPoint, int isPrimaryAccount,
		String userId, String childId, String storePhone) throws Exception{
	ctx.publishEvent(new afterSignforEvent( userId, signGeoPoint,  isPrimaryAccount, childId,6,storePhone));
}
/**
 * 创建月任务
 * @param storePhone
 * @throws Exception
 */
private void createMonthTaskRecord(String storePhone) throws Exception {
	RegistData registData = registDataService.findByPhoneNum(storePhone);
	 if(registData != null){
	   monthTaskServive.saveExecution(registData.getId(), "客户签收");
	 }
}
/**
 * 更新商城订单
 * @param orderNo
 * @param payType
 */
  private void updateMallOrder(String orderNo, int payType){
	  String dealType = "";
      String payStatus = "";
      if(payType == 2){
    	  dealType = "cash";
          payStatus = OrderShipStatusConstant.SHOP_ORDER_PAYSTATUS_HAVETOPAY;
      }
	  opl.updateOrderShipStateByOrderNum(orderNo,OrderShipStatusConstant.SHOP_ORDER_SHIPSTATUS_KHSIGNEDFOR,payStatus,dealType);
  }
  
private void startCountDown(String orderNo, OrderService oderService){
		Thread cd = new Thread(new OrderSignforCountDown(new Date(),
				orderNo, oderService));
		cd.start(); 
  }


/**
 * 创建账单和账单明细
 * @param orderNo
 * @param userPhone
 * @param userId
 * @param accountId
 * @param billStatus
 * @param amountCollected
 * @param walletPayNo
 * @param actualPayNum
 * @throws IOException
 */
private void createBill(String orderNo,String userPhone,String userId,String accountId,int billStatus,Float amountCollected,String walletPayNo,Float actualPayNum) throws IOException{
	//收现金记录 
	OrderSignfor orderSignFor = createCashRecord(orderNo, userPhone, userId);
	//更新商城红包，钱包状态
     updateQb(walletPayNo);
     //更新订单收款记录
     updateOrderReceipt(billStatus, amountCollected, actualPayNum,
			orderSignFor);
     //创建收款详情
     createReceiptInfo(accountId, billStatus, amountCollected,
			actualPayNum, orderSignFor.getOrderNo());
}
/**
 * 现金表创建一条记录
 * @param orderNo
 * @param userPhone
 * @param userId
 * @return
 * @throws IOException
 */
private OrderSignfor createCashRecord(String orderNo, String userPhone,
		String userId) throws IOException {
	OrderSignfor orderSignFor =  osr.findByOrderNoAndUserPhone(orderNo,userPhone);
	 if(orderSignFor != null){
		 try {
			 //收现金
			 String dealType = "cash";
		        String payStatus = OrderShipStatusConstant.SHOP_ORDER_PAYSTATUS_HAVETOPAY;
		         Cash cash= new Cash(orderSignFor.getId(),userId);
		         cs.save(cash);
		         
		} catch (Exception e) {
			logger.info("客户签收---->收现金--->bug:"+e.getMessage());
            throw new  IOException("收现金异常!"+e.getMessage());
		}
		 
	         
	 }else{
		 throw new  RuntimeException("订单不存在!");
	 }
	return orderSignFor;
}
/**
 * 更新钱包状态
 * @param walletPayNo
 */
private void updateQb(String walletPayNo) {
	if(null!=walletPayNo){
	   RestTemplate restTemplate = new RestTemplate();
	   Map<String, Object> param = new HashMap<String, Object>();
			param.put("status", "SUCCESS");
	   String walletPayNoUrl = walletPayNo+"/status";
	   restTemplate.put(url+walletPayNoUrl, param);
	 }
	}

/**
 * 更新订单收款记录
 * @param billStatus
 * @param amountCollected
 * @param actualPayNum
 * @param orderSignFor
 */
private void updateOrderReceipt(int billStatus, Float amountCollected,
		Float actualPayNum, OrderSignfor orderSignFor) {
	orderSignFor.setBillStatus(billStatus);
	 orderSignFor.setPayAmount(amountCollected);
	 Float arrears = actualPayNum-amountCollected;
	 orderSignFor.setArrears(arrears);
	 orderSignFor.setUpdateTime(new Date());
	 if(arrears==0){
		 orderSignFor.setOverTime(new Date());
	 }
	 osr.save(orderSignFor);
}

/**
 * 创建收款详情
 * @param accountId
 * @param billStatus
 * @param amountCollected
 * @param actualPayNum
 * @param orderSignFor
 */
private void createReceiptInfo(String accountId, int billStatus,
		Float amountCollected, Float actualPayNum, String orderNo) {
	if(billStatus == 0){
		 amountCollected =  actualPayNum;
	 }else if(billStatus == 1){
		 amountCollected = 0f;
	 }
	 Receipt r = new Receipt(amountCollected, 0, new Date(), accountId, orderNo);
	 receiptRepository.save(r);
}


  


  private OrderSignfor findOrderSignFor(String orderNo,String userPhone){
    return osr.findByOrderNoAndUserPhone(orderNo,userPhone);
  }

  public String updateMessageType(int status,String orderNum){
    return osi.updateMessageType(status, orderNum);
  }

  /**
   *
   * @Title: existOrder
   * @Description: TODO(根据订单判断订单是否已经存在)
   * @param @param orderno
   * @param @return    设定文件
   * @return boolean    返回类型
   * @throws
   */
  public boolean existOrder(String orderno) {
    OrderSignfor o = osr.findByOrderNo(orderno);
    if(o != null){
      return false;
    }
    return true;
  }


  public OrderSignfor findbyOrderNum(String orderno){

    return osr.findByOrderNo(orderno);
  }
  /**
   * 
  * @Title: existOrder 
  * @Description: TODO(根据订单判断订单是否已经存在) 
  * @param @param orderno
  * @param @return    设定文件 
  * @return boolean    返回类型 
  * @throws
 */
@Transactional(rollbackFor=Exception.class)
public void updateOrderSignfor(String orderno,String payStatus) {
	 OrderSignfor o = osr.findByOrderNo(orderno);
	 if(o != null){
			if (!StringUtils.isEmpty(payStatus)) {
				o.setOrderStatus(OrderShipStatusConstant.ORDER_SHIPSTATUS_KHSIGNEDFOR);
				o.setCustomSignforTime(new Date());
			} else {

				o.setOrderStatus(OrderShipStatusConstant.ORDER_SHIPSTATUS_YWSIGNEDFOR);
				o.setCustomSignforTime(null);
//				o.setCustomSignforGeopoint(null);
				opl.updateOrderShipStateByOrderNum(orderno,
						OrderShipStatusConstant.SHOP_ORDER_SHIPSTATUS_YWSIGNEDFOR);
			}
			osr.save(o);
	 }
}



@Transactional(rollbackFor=Exception.class)
public void settlement(JSONObject jsons) {
	String orderno = jsons.getString("orderNum");
	Float arrears = jsons.getFloat("arrears");
	String accountId = jsons.getString("accountId");
	 OrderSignfor orderSignfor = osr.findByOrderNo(orderno);
	 if(orderSignfor != null){
		 orderSignfor.setPayAmount(orderSignfor.getPayAmount()+arrears);
		 orderSignfor.setArrears(0f);//欠款为0
		 orderSignfor.setOverTime(new Date());//款项结清的时间
		 orderSignfor.setBillStatus(0);//收款的状态为已付清
		 osr.save(orderSignfor);
	 }
	 Receipt r = new Receipt(arrears, 0, new Date(), accountId, orderno);
	 receiptService.save(r);
	
	
}

public Page<OrderSignfor> getBillList(String userId, String day,
		JSONObject jsons) {
	int pageNo = jsons.getIntValue("pageNumber");
	int pageSize = 10;
	String  startDate = "";
	String endDate = "";
	 Float todayArrears = 0f;
	 Float yesterdayArrears=0f;
	 Float historyArrears=0f;
	 if(day.equals("today")){
		  startDate = "sysdate-1";
		 endDate = "sysdate";
	 }else if(day.equals("yesterday")){
		   startDate = "sysdate-2";
		   endDate = "sysdate-1";
			 
	 }
	 Page<OrderSignfor>	o = osr.findByUserIdAndCreatTime(userId, startDate, endDate, new PageRequest(pageNo > 0 ?pageNo-1:0,pageSize > 0 ? pageSize : 10,new Sort(Direction.DESC, "id")));
	 
	// BillVo Vo =  createOrderVo(o,todayArrears,yesterdayArrears,historyArrears);
	 
	 return o;
}

/*public BillVo getBillVo(String userId, String day,
		JSONObject jsons){
	Float	historyArrears = osr.findSumForArrears(userId);
	Float  yesterdayArrears = osr.findSumForArrears(userId, "sysdate-2", "sysdate-1");
	Float todayArrears = osr.findSumForArrears(userId, "sysdate-1", "sysdate");
	return  null;
}*/

/**
 * 组装
 * @param o
 * @return
 */
/*private BillVo createOrderVo(Page<OrderSignfor> o) {
	// TODO Auto-generated method stub
	
	BillVo vo = new BillVo();
	return vo;
}*/

public Map<String, Float> queryArrears(String userId) {
	Map<String, Float> map = new HashMap<String, Float>();
	Float	historyArrears = osr.findSumForArrears(userId);
	Float  yesterdayArrears = osr.findSumForArrears(userId, "sysdate-2", "sysdate-1");
	Float todayArrears = osr.findSumForArrears(userId, "sysdate-1", "sysdate");
	map.put("historyArrears", historyArrears);
	map.put("yesterdayArrears", yesterdayArrears);
	map.put("todayArrears", todayArrears);
	return map;
}


}
