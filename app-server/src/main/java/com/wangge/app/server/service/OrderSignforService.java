package com.wangge.app.server.service;

import com.wangge.app.constant.OrderShipStatusConstant;
import com.wangge.app.server.entity.Cash;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.event.afterSalesmanSignforEvent;
import com.wangge.app.server.event.afterSignforEvent;
import com.wangge.app.server.monthTask.service.MonthTaskServive;
import com.wangge.app.server.repository.OrderSignforRepository;
import com.wangge.app.server.repositoryimpl.OrderImpl;
import com.wangge.app.server.repositoryimpl.OrderSignforImpl;
import com.wangge.app.server.thread.OrderSignforCountDown;
import com.wangge.app.server.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
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
  
  private static final String url = "http://115.28.87.182:58081/v1/";

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
	private OrderService oderService;

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
   * 
  * @Title: updateOrderSignfor 
  * @Description: TODO(客户签收) 
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
  public void updateOrderSignfor(String orderNo, String userPhone,
      String signGeoPoint, int payType, String smsCode,int isPrimaryAccount,String userId,String childId,String  storePhone, String walletPayNo) throws Exception {
      OrderSignfor orderSignFor =  findOrderSignFor(orderNo,userPhone);
      String dealType = "";
      String payStatus = "";
       if(orderSignFor!= null){
           String accountId = null;
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
           if(isPrimaryAccount==0){
             accountId = userId;
           }else{
             accountId = childId;
           }
           orderSignFor.setAccountId(accountId);
           orderSignFor = osr.save(orderSignFor);
           //收现金
           try {
             if(2 == payType){
               dealType = "现金支付";
               payStatus = OrderShipStatusConstant.SHOP_ORDER_PAYSTATUS_HAVETOPAY;
               Cash cash= new Cash(orderSignFor.getId(),userId);
               cs.save(cash);
               System.out.println("walletPayNo"+walletPayNo);
               if(null!=walletPayNo&&!walletPayNo.equals("null")){
            	   RestTemplate restTemplate = new RestTemplate();
            	   Map<String, Object> param = new HashMap<String, Object>();
                 param.put("status", "SUCCESS");
            	   String walletPayNoUrl = walletPayNo+"/status";
            	  // String param = "SUCCESS";
            	   restTemplate.put(url+walletPayNoUrl, param);
               }
              
             }
          } catch (Exception e) {
            logger.info("客户签收---->收现金--->bug:"+e.getMessage());
            throw new  IOException("收现金异常!"+e.getMessage());
          }
            opl.updateOrderShipStateByOrderNum(orderNo,OrderShipStatusConstant.SHOP_ORDER_SHIPSTATUS_KHSIGNEDFOR,payStatus,dealType);
            startCountDown(orderNo,oderService);
            RegistData registData = registDataService.findByPhoneNum(storePhone);
           if(registData != null){
             monthTaskServive.saveExecution(registData.getId(), "客户签收");
           }
          
             ctx.publishEvent(new afterSignforEvent( userId, signGeoPoint,  isPrimaryAccount, childId,6,storePhone));
             
       }else{
         throw new  RuntimeException("订单不存在!");
       }
         
        
     
  }

  
private void startCountDown(String orderNo, OrderService oderService){
		Thread cd = new Thread(new OrderSignforCountDown(new Date(),
				orderNo, oderService));
		cd.start(); 
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
      opl.updateOrderShipStateByOrderNum(orderNo,OrderShipStatusConstant.SHOP_ORDER_SHIPSTATUS_KHUNSIGNEDFOR,null,null);
      RegistData registData = registDataService.findByPhoneNum(storePhone);
      if(registData != null){
        monthTaskServive.saveExecution(registData.getId(), "客户拒签");
      }
      ctx.publishEvent(new afterSignforEvent( userId, signGeoPoint,  isPrimaryAccount, childId,7,storePhone));

    }else{
      throw new  RuntimeException("订单不存在!");
    }

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

  public List<OrderSignfor> findByMemberPhoneAndCreatTime(String memberPhone){
    List<OrderSignfor> orderSignfors = osr.findByMemberPhoneAndCreatTime(memberPhone);
    return orderSignfors;
  }
}
