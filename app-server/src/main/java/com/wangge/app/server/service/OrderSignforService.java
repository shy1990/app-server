package com.wangge.app.server.service;


import com.alibaba.fastjson.JSONObject;
import com.wangge.app.constant.OrderShipStatusConstant;
import com.wangge.app.server.entity.Cash;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.Receipt;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.dto.BillHistoryDto;
import com.wangge.app.server.entity.dto.OrderDto;
import com.wangge.app.server.event.afterSalesmanSignforEvent;
import com.wangge.app.server.event.afterSignforEvent;
import com.wangge.app.server.monthTask.service.MonthTaskServive;
import com.wangge.app.server.pojo.QueryResult;
import com.wangge.app.server.repository.OrderSignforRepository;
import com.wangge.app.server.repository.ReceiptRepository;
import com.wangge.app.server.repositoryimpl.OrderImpl;
import com.wangge.app.server.repositoryimpl.OrderSignforImpl;
import com.wangge.app.server.thread.OrderSignforCountDown;
import com.wangge.app.util.DateUtil;
import com.wangge.app.server.vo.BillHistoryVo;
import com.wangge.app.server.vo.BillVo;
import com.wangge.app.server.vo.OrderVo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
		String childId, String accountId, String storePhone){
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
		String userId, String childId, String storePhone){
	
		ctx.publishEvent(new afterSignforEvent( userId, signGeoPoint,  isPrimaryAccount, childId,6,storePhone));
		
	
}
/**
 * 创建月任务
 * @param storePhone
 * @throws Exception
 */
private void createMonthTaskRecord(String storePhone) {

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
	/*if(null!=walletPayNo){
	   RestTemplate restTemplate = new RestTemplate();
	   Map<String, Object> param = new HashMap<String, Object>();
			param.put("status", "SUCCESS");
	   String walletPayNoUrl = walletPayNo+"/status";
	   restTemplate.put(url+walletPayNoUrl, param);
	 }*/
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
	 if(billStatus == 0){
		 amountCollected =  actualPayNum;
	 }else if(billStatus == 1){
		 amountCollected = 0f;
	 }
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
	if(!StringUtils.isEmpty(orderno) && arrears > 0 && !StringUtils.isEmpty(accountId)){
		OrderSignfor orderSignfor = osr.findByOrderNo(orderno);
		 if(orderSignfor != null){
			 orderSignfor.setPayAmount(orderSignfor.getPayAmount()+arrears);
			 orderSignfor.setArrears(0f);//欠款为0
			 orderSignfor.setOverTime(new Date());//款项结清的时间
			 orderSignfor.setBillStatus(0);//收款的状态为已付清
			 osr.save(orderSignfor);
		 }
		 Receipt r = new Receipt(arrears, 0, new Date(), accountId, orderno);
		 receiptService.addReceipt(r);
	}else{
		throw new RuntimeException("参数不完整");
	}
	 
	
	
}


public BillVo getBillList(String userId, String day,
		int pageNo, int pageSize ) {
	int  startDate = 0;
	int endDate = 0;
	 if(day.equals("today")){
		  startDate = 1;
		 //endDate = "0";
	 }else if(day.equals("yesterday")){
		   startDate = 2;
		   endDate = 1;
	 }
	 Page<Object>	o = osr.findByUserIdAndCreatTime(userId, startDate, endDate, new PageRequest(pageNo > 0 ?pageNo-1:0,pageSize > 0 ? pageSize : 10,new Sort(Direction.DESC, "id")));
	 
	 return createBillVo(o);
}
/**
 * 获取某天欠款列表
 * @param userId
 * @param date
 * @param pageNo
 * @param pageSize
 * @return
 */
public BillVo getBillListOneDay(String userId, String date, int pageNo, int pageSize){
	 Page<Object>	o = osr.findByUserIdAndCreatTime(userId, date, new PageRequest(pageNo > 0 ?pageNo-1:0,pageSize,new Sort(Direction.DESC, "id")));
     return createBillVo(o) ;
}

/**
 * 获取欠款历史总汇列表
 * @param userId
 * @param pageNo
 * @param pageSize
 */
public BillHistoryVo queryBillHistory(String userId,int pageNo, int pageSize) {
	 
	 List<Object>	o = osr.findBillHistoryConfluenceList(userId,pageNo,pageSize);
	 Long	count  = osr.findBillHistoryConfluenceCount(userId);
     QueryResult<BillHistoryVo> re = new QueryResult<BillHistoryVo>();
     re.setTotalPages(count,Long.parseLong(String.valueOf(pageSize)));
    return createBillHistoryVo(o,Integer.valueOf(String.valueOf(re.getTotalPages())));
}

public BillVo getBillList(String userId, String createTime, int pageNumer, int pageSize,int isPrimary,int billStatus, int orderStatus) {
	 Page<OrderSignfor> orderPage = osr.findAll(new Specification<OrderSignfor>() {

	      public Predicate toPredicate(Root<OrderSignfor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	        List<Predicate> predicates = new ArrayList<Predicate>();
	        if(!StringUtils.isEmpty(userId)){
	        	  Predicate p1 = cb.equal(root.get("userId").as(String.class), userId );
		          predicates.add(p1);
	        }
	        
	        if (isPrimary  > 0 ) {
	        	int isPrimaryAccount = 0;
	        	if(isPrimary == 1){
	        		isPrimaryAccount = 1;
	        	}else if(isPrimary == 2){
	        		isPrimaryAccount = 0;
	        	}
	          Predicate p2 = cb.equal(root.get("isPrimaryAccount").as(int.class), isPrimaryAccount );
	          predicates.add(p2);
	        }
	        
	        if(billStatus>0){
	        	 Predicate p3 = cb.equal(root.get("billStatus").as(Integer.class), billStatus);
	        	 predicates.add(p3);
	        }
	        
	       
	        	if(orderStatus == 3){
	        		Predicate p4 = cb.equal(root.get("orderStatus").as(Integer.class), orderStatus);
		        	 predicates.add(p4);
	        	}else{
	        		Predicate p4 = cb.equal(root.get("orderStatus").as(Integer.class), 0);
	        		Predicate p5 = cb.equal(root.get("orderStatus").as(Integer.class), 2);
		        	 predicates.add(cb.or(p4,p5));
	        	}
	        	
	      
	        
	        if(!StringUtils.isEmpty(createTime)){
	        	Predicate p4 = cb.isNotNull(root.get("fastmailNo").as(String.class));
	        	Predicate p5 = cb.between(root.get("creatTime").as(Date.class),DateUtil.getYesterdayDate2(createTime), DateUtil.getTodayDate2(createTime));
	        	predicates.add(cb.and(p4,p5));
	        }
	        
	       
	        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	      }

	    }, new PageRequest(pageNumer > 0 ? pageNumer-1:0, 20, new Sort(Sort.Direction.DESC,"creatTime")));
	  return createBillVoByOrderSignfor(orderPage);
	  }


 private BillVo createBillVoByOrderSignfor(Page<OrderSignfor> orderPage){
	 BillVo bvo = new BillVo();
	 Float totalArrears = 0f;
	 List<OrderVo> list = createOrderVoByOrderSignfor(orderPage);
	 for(OrderVo vo : list){
		 totalArrears+=vo.getTotalArreas();
	 }
	 bvo.setContent(list);
	 bvo.setTotalArrears(totalArrears);
	 bvo.setTotalPages(orderPage.getTotalPages());
	 return bvo;
 }
/**
 * 按条件查询后组装
 * @param orderPage
 * @return
 */
private List<OrderVo> createOrderVoByOrderSignfor(Page<OrderSignfor> orderPage) {
	List<OrderVo> volist = new ArrayList<OrderVo>();
    List<OrderSignfor> list = new ArrayList<OrderSignfor>(orderPage.getContent());
    List<OrderSignfor> list2 = new ArrayList<OrderSignfor>(orderPage.getContent());
	for(int i = 0;i<list.size();){
		List<OrderDto> dtoList = new ArrayList<OrderDto>();
		Float totalArrear = 0f;
		OrderVo bvo = new OrderVo();
		OrderSignfor o = list.get(i);
		bvo.setShopName(o.getShopName());
		
		bvo.setContent(dtoList);
		for(OrderSignfor os : list2){
			
			if(o.getShopName().toString().equals(os.getShopName().toString())){
				OrderDto dto = new OrderDto();
				dto.setOrderNo(os.getOrderNo());
				dto.setPayTyp(os.getOrderPayType() != null ? os.getOrderPayType() : 3);
				dto.setOrderPrice(os.getOrderPrice());
				dto.setCreateTime(os.getCreatTime());
				dto.setArrear(os.getOrderStatus() == 3 ? os.getArrears() : os.getActualPayNum());
				dtoList.add(dto);
				totalArrear += os.getArrears();//os.getOrderStatus() == 3 ? os.getArrears() : os.getActualPayNum()
				bvo.setTotalArreas(totalArrear);
				list.remove(os);
			}
		}
		volist.add(bvo);
	}
	return volist;
}

/**
 * 获取欠款总和(今日，昨日，历史)
 * @param userId
 * @return
 */
public Map<String, BigDecimal> queryArrears(String userId) {
	
	Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
	List<BigDecimal> arrears =  osr.findSumForArrears(userId);
	for(int i=0;i<arrears.size();i++){
		map.put("historyArrears", arrears.get(0));
		map.put("todayArrears", arrears.get(1));
		map.put("yesterdayArrears", arrears.get(2));
	}
	return map;
}

public Date checkOrderOverTime(String ordernum) {
	Object object = osr.findOverTimeByOrderNo(ordernum);
	return (Date)object;
}

private BillHistoryVo createBillHistoryVo(List<Object> o,int totalPages) {
	BillHistoryVo historyVo = new BillHistoryVo();
	List<BillHistoryDto> dtoList = new ArrayList<BillHistoryDto>();
	 //List<Object> list = o.getContent();
	Float totalArrears = 0f;
	for(int i=0;i< o.size();i++){
		Object[] ob = (Object[]) o.get(i);
		BillHistoryDto dto = new BillHistoryDto();
		dto.setOrderNumber((BigDecimal)ob[1]);
		dto.setArrears((BigDecimal)ob[2]);
		dto.setShopNumber((BigDecimal)ob[4]);
		dto.setDateYearMonth((Date)ob[3]);
		dto.setDateDay((Date)ob[3]);
		dtoList.add(dto);
		totalArrears +=  new BigDecimal(ob[2]+"").floatValue();
		
	}
	historyVo.setTotalArrears(totalArrears);
	historyVo.setContent(dtoList);
	historyVo.setTotalPages(totalPages);
	return historyVo;
	
}

private BillVo createBillVo(Page<Object> o){
	BillVo bvo = new BillVo();
	Float totalArrears = 0f;
	List<OrderVo> voList = createOrderVo(o);
	for(OrderVo vo : voList){
		totalArrears += vo.getTotalArreas();
	}
	bvo.setTotalPages(o.getTotalPages());
	bvo.setTotalArrears(totalArrears);
	bvo.setContent(voList);
	return bvo;
}

/**
 * 组装
 * @param o
 * @return
 */
private List<OrderVo> createOrderVo(Page<Object> o) {
	List<OrderVo> volist = new ArrayList<OrderVo>();
    List<Object> list = new ArrayList<Object>(o.getContent());
    List<Object> list2 = new ArrayList<Object>(o.getContent());
	for(int i=0;i<list.size();){
		Object[] ob = (Object[])list.get(0);
		List<OrderDto> dtoList = new ArrayList<OrderDto>();
		Float totalArrear = 0f;
		OrderVo bvo = new OrderVo();
		bvo.setShopName(ob[0]+"");
		
		bvo.setContent(dtoList);
		
		for(int j = 0;j<list2.size();j++){
			
			Object[] obj = (Object[])list2.get(j);
			
			if(String.valueOf(ob[0]).equals(String.valueOf(obj[0]))){
				OrderDto dto = new OrderDto();
				dto.setOrderNo(obj[1]+"");
				dto.setPayTyp(obj[2] != null ?Integer.parseInt(obj[2]+""):3);
				dto.setOrderPrice((Float)obj[3]);
				dto.setCreateTime((Date)obj[4]);
				dto.setArrear((int)obj[8]==3?(Float)obj[5]:(Float)obj[9]);
				dto.setBillStatus(obj[6]!=null?(int)obj[6]:1);
				dto.setPayee((int)obj[7]);
				dto.setOrderStatus((int)obj[8]);
				dtoList.add(dto);
				totalArrear += (Float)obj[5];//(int)obj[8]==3?(Float)obj[5]:(Float)obj[9]
				bvo.setTotalArreas(totalArrear);
				list.remove(obj);
			}
			
		}
		
		volist.add(bvo);
	}
	return volist;
}




/*public void getBillList(OrderSignfor orderSignfor, int pageNumer, int pageSize) {
	// TODO Auto-generated method stub
	
}
	*/
}


