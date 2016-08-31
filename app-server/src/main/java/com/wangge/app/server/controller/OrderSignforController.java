package com.wangge.app.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.pojo.MessageCustom;
import com.wangge.app.server.pojo.QueryResult;
import com.wangge.app.server.repositoryimpl.OrderImpl;
import com.wangge.app.server.repositoryimpl.OrderSignforImpl;
import com.wangge.app.server.service.OrderService;
import com.wangge.app.server.service.OrderSignforService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.util.HttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/v1/remind")
public class OrderSignforController {


  //private static final Logger logger = LoggerFactory.getLogger(OrderSignforController.class);
  @Resource
  private OrderSignforService orderSignforService;
  @Resource
  private SalesmanService salesmanService;
  @Resource
  private OrderImpl opl ;
  
  
  @Resource
  private OrderService or;
  
  /*private String userPhone ;
  private String orderNo ;
  private String smsCode;
  private int payType;
  private String signGeoPoint;
  private String storePhone ;
  private int isPrimaryAccount;
  private String  remark;
  private String fastMailNo;*/
  
  @Resource
  private OrderSignforImpl osi;
 
  /**
   * @throws
   * @throws NumberFormatException 
   * @throws
   * @throws NumberFormatException 
   * 
  * @Title: getOrderSignforList 
  * @Description: TODO(获取物流单号列表) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<Page<OrderSignfor>>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/getBussOrderList" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<QueryResult<OrderSignfor>> getOrderSignforList(@RequestBody JSONObject jsons){
	  int pageNo = jsons.getIntValue("pageNumber");
	  int pageSize = jsons.getIntValue("pageSize");
    String userPhone = jsons.getString("userPhone") ;
    QueryResult<OrderSignfor> qr = osi.getOrderSignforList(userPhone, pageNo > 0 ? pageNo-1 : 0,pageSize > 0 ? pageSize : 10);   
    return new ResponseEntity<QueryResult<OrderSignfor>>(qr,HttpStatus.OK);
  }
  /**
   * 
   * 
  * @Title: bussOrderSignFor 
  * @Description: TODO(业务签收) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<message>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/bussOrderSignFor", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<MessageCustom> bussOrderSignFor(@RequestBody JSONObject jsons){
      String fastMailNo = jsons.getString("fastmailNo");
      String userPhone = jsons.getString("userPhone");
      String signGeoPoint = jsons.getString("signGeoPoint");
      int isPrimaryAccount = jsons.getIntValue("isPrimary");
       String userId = jsons.getString("userId");
       String childId = jsons.getString("childId");
       
      MessageCustom m = new MessageCustom();
      try {
        
        Date signTime = orderSignforService.updateOrderSignforList(fastMailNo,userPhone,signGeoPoint,isPrimaryAccount,userId,childId);
        if(signTime != null){
          m.setMsg("success");
          m.setCode(0);
          m.setSignTime(signTime);
        }else{
          m.setMsg("false");
          m.setCode(1);
        }
        return new ResponseEntity<MessageCustom>(m,HttpStatus.OK);
        
      } catch (Exception e) {
        e.printStackTrace();
        m.setMsg("false");
        m.setCode(1);
        return new ResponseEntity<MessageCustom>(m,HttpStatus.OK);
      }
  }
  /**
   * @throws
   * @throws NumberFormatException 
   * 
  * @Title: getOrderList 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<List<OrderSignfor>>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<QueryResult<OrderSignfor>> getOrderList(@RequestBody JSONObject jsons){
                String userPhone = jsons.getString("userPhone");
                String type = jsons.getString("type");
                int pageNo = jsons.getIntValue("pageNumber");
                int pageSize = jsons.getIntValue("pageSize");
               QueryResult<OrderSignfor> qr = osi.getOrderList(userPhone, type, pageNo > 0 ? pageNo-1 : 0,pageSize > 0 ? pageSize : 10);
         //       QueryResult<OrderSignfor> qr = osi.getOrderList(userPhone, type, pageNo-1,pageSize != 0 ? pageSize: 10);
    return new ResponseEntity<QueryResult<OrderSignfor>>(qr,HttpStatus.OK);
  }
  /**
   * 
  * @Title: customOrderSign 
  * @Description: TODO(客户签收) 
  * @param @param jsons
  * @param @param orderSignfor
  * @param @return    设定文件 
  * @return ResponseEntity<MessageCustom>    返回类型 
  * @throws
   */
  @RequestMapping(value ="/customOrderSign", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<MessageCustom> customOrderSign(@RequestBody JSONObject jsons){
     String userPhone = jsons.getString("userPhone");
     String orderNo = jsons.getString("orderNo");
     String smsCode = jsons.getString("smsCode");
     int payType =  jsons.getIntValue("payType");
     String signGeoPoint = jsons.getString("signGeoPoint");
     String storePhone = jsons.getString("storePhone");
     int isPrimaryAccount = jsons.getIntValue("isPrimary");
    String userId =  jsons.getString("userId");
    String childId =  jsons.getString("childId");
    MessageCustom m = new MessageCustom();
    try {
      if(smsCode != null && !"".equals(smsCode) && storePhone != null && !"".equals(storePhone)){
        String msg = HttpUtil.sendPost("http://www.3j1688.com/member/existMobileCode/"+storePhone+"_"+smsCode+".html","");
        if(msg!=null && msg.contains("true")){
            orderSignforService.updateOrderSignfor(orderNo, userPhone, signGeoPoint,payType,smsCode,isPrimaryAccount,userId,childId,storePhone);
            m.setMsg("success");
            m.setCode(0);
        }else{
            m.setMsg("短信验证码不存在！");
        }
      }else{
        orderSignforService.updateOrderSignfor(orderNo, userPhone, signGeoPoint,payType,smsCode,isPrimaryAccount,userId,childId,storePhone);
        m.setMsg("success");
        m.setCode(0);
      }
      
    } catch (Exception e) {
      m.setMsg(e.getMessage());
      m.setCode(1);
     /* logger.error("OrderSignforController updateOrderSignfor error :"+e);*/
    }
    
   return  new ResponseEntity<MessageCustom>(m, HttpStatus.OK);
  }
 
  /**
   * 
  * @Title: customOrderUnSign 
  * @Description: TODO(客户拒签) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<MessageCustom>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/customOrderUnSign", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<MessageCustom> customOrderUnSign(@RequestBody JSONObject jsons){
    String userPhone = jsons.getString("userPhone");
     String orderNo = jsons.getString("orderNo");
     String  remark = jsons.getString("remark");
    String signGeoPoint = jsons.getString("signGeoPoint");
    int isPrimaryAccount = jsons.getIntValue("isPrimary");
    String storePhone = jsons.getString("storePhone");
    String userId =  jsons.getString("userId");
    String childId =  jsons.getString("childId");
    MessageCustom m = new MessageCustom();
    try {
        orderSignforService.updateOrderSignfor(orderNo, userPhone, remark,signGeoPoint,isPrimaryAccount,userId,childId,storePhone);
//        m = refund(orderNo,m);
        m.setMsg("success");
        m.setCode(0);
    } catch (Exception e) {
      m.setMsg(e.getMessage());
      m.setCode(1);
     /* logger.error("OrderSignforController customOrderUnSign() error :"+e);*/
    }
    return new ResponseEntity<MessageCustom>(m,HttpStatus.OK);
    
  }
  
  /**
   * 
  * @Title: refund 
  * @Description: TODO(拒簽调用退款接口) 
  * @param @param orderNo
  * @param @param m
  * @param @return    设定文件 
  * @return MessageCustom    返回类型 
  * @throws
   */
  /*private MessageCustom  refund (String orderNo,MessageCustom m){
 JSONObject jo = new JSONObject();
    
    Map map = opl.checkMoneyBack(orderNo);
    boolean flag = false;
    if(map!=null){
      //判断钱包流水号是否为空,若是则不调用退款接口
          if(map.get("payNo")!=null && !"".equals(map.get("payNo"))){
            if("0".equals(map.get("payMent")) ){
              if(map.get("totalCost").equals(map.get("walletNum"))){
                 flag = true;
              }
            }else{
                flag = true;
            }
          }
    }
      if(flag){
         try {
           jo.put("state", "success");//调用接口传参
           String str = or.invokWallet(jo, map.get("payNo").toString());
           jo.clear();
           if(str!=null && str.contains("202")){
             m.setStatus("退款成功,请核实钱包金额");
           }else{
             m.setStatus("退款失败,请联系技术人员!");
           }
           return m;
         } catch (Exception e) {
           e.printStackTrace();
           m.setStatus(e.getMessage());
         }
       }
    return m;
  }*/
  
  
  /**
   * 
  * @Title: getOrdersByMailNo 
  * @Description: TODO(根据物流单号获取订单列表) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<List<OrderSignfor>>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/getOrdersByMailNo", method = RequestMethod.POST)
  public ResponseEntity<QueryResult<OrderSignfor>> getOrdersByMailNo(@RequestBody JSONObject jsons){
        String fastmailNo = jsons.getString("fastmailNo");
        String userPhone = jsons.getString("userPhone");
        int pageNo = jsons.getIntValue("pageNumber");
        int pageSize = jsons.getIntValue("pageSize");
        QueryResult<OrderSignfor> qr = osi.getOrdersByMailNo(fastmailNo,userPhone, pageNo > 0 ? pageNo-1 : 0,pageSize > 0 ? pageSize : 10);   
        return new ResponseEntity<QueryResult<OrderSignfor>>(qr,HttpStatus.OK);
  }
  

}

