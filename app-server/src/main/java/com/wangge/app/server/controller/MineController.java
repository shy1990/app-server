package com.wangge.app.server.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.wangge.app.server.entity.*;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.service.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.repository.RegionRepository;
import com.wangge.app.server.repositoryimpl.ExamImpl;
import com.wangge.app.server.repositoryimpl.OrderImpl;
import com.wangge.app.server.util.HttpUtil;
import com.wangge.app.server.util.SortUtil;
import com.wangge.app.server.vo.Apply;
import com.wangge.app.server.vo.Exam;
import com.wangge.app.server.vo.OrderPub;

@RestController
@RequestMapping(value = "/v1/mine")
public class MineController {
	
//	private static final Logger LOG = LoggerFactory.getLogger(MineController.class);
	
	@Resource
	private OrderImpl opl ;
	@Resource
	private MessageService mr;
	@Resource
	private ExamImpl epl;
	
	@Resource
	private OrderService or;
	@Resource
	private RegionRepository rr;
	
	@Resource
	private ApplyPriceService aps;
	@Resource
	private RegistDataService rds;
	@Resource
	private SalesmanService salesmanService;
	/**
	 * 
	 * @Description: 根据业务手机号订单号判断该订单是否属于该业务员并返回订单详情
	 * @param @param json
	 * @param @return   
	 * @return ResponseEntity<List<OrderPub>>  
	 * @throws
	 * @author changjun
	 * @date 2015年11月12日
	 */
	@RequestMapping(value = "/checkByOrderNum",method = RequestMethod.POST)
	public ResponseEntity<JSONObject> checkByOrderNum(@RequestBody  JSONObject json) throws Exception{
//		String mobile = json.getString("mobile");
		String ordernum = json.getString("ordernum");
		String regionId = json.getString("regionId");
		
		JSONObject jo = new JSONObject();
		Order order = or.findOne(ordernum);
		
		 
		
		if(order!=null){
		  StringBuffer sb = new StringBuffer();
      int skuNum = 0;
      for (OrderItem item : order.getItems()) {
        sb.append(item.getName()+" ");
        if("sku".equals(item.getType())){
          skuNum+=1;
        }
      }
      jo.put("username", order.getShopName());
      jo.put("createTime", order.getCreateTime());
      jo.put("orderNum", order.getId());
      jo.put("shipStatus", order.getStatus().getName());
      jo.put("amount", order.getAmount());
      jo.put("mobile", order.getMobile());
      jo.put("skuNum", skuNum);
      jo.put("itemOtherNum", order.getItems().size()-skuNum);
      jo.put("goods", sb);
      jo.put("payType", order.getPayMent().getName());
      RegistData rd = rds.findByMemberId(order.getMemberId());
      if(rd != null){
        String Coordinate =rd.getSaojieData().getCoordinate();
        jo.put("point",Coordinate);
      }else{
        jo.put("point","");
      }
     
			if(regionId.equals(order.getRegion().getId())){
				if(opl.checkByOrderNum(ordernum)){
				  jo.put("state", "正常订单");
				  return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
				}else{
					jo.put("state", "该订单已签收,请核实");
					return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
				}
			}else{

        jo.put("state", "该订单不属于此地区,请核实");
        return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
      }
    }
    jo.put("state", "未查询相关信息,请重试");
    return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);

  }
  
  
  /**
   * 
   * @Description: 根据业务手机号查询所属订单的派送状态
   * @param @param json
   * @param @return   
   * @return ResponseEntity<List<OrderPub>>  
   * @throws
   * @author changjun
   * @date 2015年11月11日
   */
  @RequestMapping(value = "/orderStatusList",method = RequestMethod.POST)
  public ResponseEntity<List<OrderPub>> orderStatusList(@RequestBody  JSONObject json){
//    String regionId = json.getString("regionId");
    String username = json.getString("username");
    PageRequest pageRequest = SortUtil.buildPageRequest(json.getInteger("pageNumber"), json.getInteger("pageSize"),"order");
//    List<OrderPub> list = or.findByRegion(rr.findById(regionId), pageRequest);
    List<OrderPub> list =opl.selOrderSignforStatus(username, json.getInteger("pageNumber"),pageRequest);
    return new ResponseEntity<List<OrderPub>>(list , HttpStatus.OK);
  }
  
  
  /**
   * 
   * @Description: 业务签收后更新订单状态
   * @param @param json
   * @param @return   
   * @return ResponseEntity<JSONObject>  
   * @throws
   * @author changjun
   * @date 2015年11月21日
   */
  @RequestMapping(value = "/updateOrderStatus",method = RequestMethod.POST)
  public ResponseEntity<JSONObject> updateOrderStatus(@RequestBody  JSONObject json){
    String status =  opl.updateOrderShipStateByOrderNum(json.getString("ordernum"), null,null,"2",1);
    JSONObject jo = new JSONObject();
    jo.put("state", status);
    return new ResponseEntity<JSONObject>( jo, HttpStatus.OK);
  }
  
  ///////////////////     V2
  /**
   * 
   * @Description: 客户拒签
   * @param @param json
   * @param @return   
   * @return ResponseEntity<JSONObject>  
   * @throws
   * @author changjun
   * @date 2015年12月1日
   */
  @RequestMapping(value = "/custNotSignFor",method = RequestMethod.POST)
  public ResponseEntity<JSONObject> custNotSignFor(@RequestBody  JSONObject json){
    String orderNum = json.getString("ordernum");
    String reason = json.getString("reason");
    JSONObject jo = new JSONObject();
    
    opl.saveRefuseReason(orderNum, reason);//保存拒签原因
    
    Map map = opl.checkMoneyBack(orderNum);
    boolean flag = false;
    if(map!=null){
      //判断钱包流水号是否为空,若是则不调用退款接口
      if (map.get("payNo") != null && !"".equals(map.get("payNo"))) {
        if ("0".equals(map.get("payMent"))) {
          if (map.get("totalCost").equals(map.get("walletNum"))) {
            flag = true;
          }
        } else {
          flag = true;
        }
      }
    }
    if (flag) {
      try {
        jo.put("state", "success");// 调用接口传参
        String str = or.invokWallet(jo, map.get("payNo").toString());
        jo.clear();
        if (str != null && str.contains("202")) {
          jo.put("status", "退款成功,请核实钱包金额");
        } else {
          jo.put("status", "退款失败,请联系技术人员!");
        }
        return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  		jo.put("status", "拒签成功");
		return new ResponseEntity<JSONObject>( jo, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 客户签收
	 * @param @param json
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年12月1日
	 */
	@RequestMapping(value = "/custSignFor",method = RequestMethod.POST)
	public ResponseEntity<JSONObject> custSignFor(@RequestBody  JSONObject json){
	  String status = "";
	  //收款方式
	    String paytype = json.getString("paytype");
	  //纬度
//	  String latitude = json.getString("latitude");
	  //经度
//	  String longitude = json.getString("longitude");
	  
	    String point = json.getString("longitude") +"-"+  json.getString("latitude");
		  String mobile = json.getString("mobile");
		  String code = json.getString("code");
		  JSONObject jo = new JSONObject();
		  if(code != null && !"".equals(code)){
		    String str = this.validateCode(mobile,code);
	     
	      if("suc".equals(str)){
	         status =  opl.updateOrderShipStateByOrderNum(json.getString("ordernum"),paytype, point,"3",2);
	      }else{
	        status = str;
	      }
		  }else{
		    status =  opl.updateOrderShipStateByOrderNum(json.getString("ordernum"),paytype, point,"3",2);
		  }
			
			jo.put("state", status);
			return new ResponseEntity<JSONObject>( jo, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 发送验证码
	 * @param @param json
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年12月1日
	 */
	@RequestMapping(value = "/sendCode",method = RequestMethod.POST)
	public ResponseEntity<JSONObject> sendCode(@RequestBody  JSONObject json){
		String mobile = json.getString("mobile");
		//192.168.2.252:80
		String msg = HttpUtil.sendPost("http://www.3j1688.com/member/getValidateCode/"+mobile+".html","");
		System.out.println("msg==="+msg);
		JSONObject jo = new JSONObject();
		if(msg!=null && msg.contains("true")){
			jo.put("state", true);
			return new ResponseEntity<JSONObject>( jo, HttpStatus.OK);
		}
		jo.put("state", false);
		return new ResponseEntity<JSONObject>( jo, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 验证码验证
	 * @param @param mobile
	 * @param @param code
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author changjun
	 * @date 2015年12月19日
	 */
	public String validateCode(String mobile,String code){
		String msg = HttpUtil.sendPost("http://www.3j1688.com/member/existMobileCode/"+mobile+"_"+code+".html","");
		if(msg!=null && msg.contains("true")){
			return "suc";
		}else{
			if(msg.contains("手机验证码超时")){
				return "手机验证码超时";
			}else{
				return "手机验证码不正确";
			}
		}
	}
	
	
	/**
	 * 
	 * @Description: 考核状态
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws ParseException 
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/examStatus",method = RequestMethod.POST)
	public ResponseEntity<Exam> examStatus(@RequestBody	JSONObject json) throws ParseException{
		String saleId = json.getString("salesmanId");
		Exam  ex = epl.ExamSalesman(saleId);
		return new ResponseEntity<Exam>(ex, HttpStatus.OK);
	}

	/**
	 * 月指标统计查询
	 * @param json
	 * @return
	 * @throws ParseException
     */
	@RequestMapping(value = "/monthTarget",method = RequestMethod.POST)
	public ResponseEntity<Json> getMonthTarget(@RequestBody	JSONObject json) throws ParseException{
		String saleId = json.getString("salesmanId");
		Salesman salesman = salesmanService.findSalesmanbyId(saleId);
		Json  jsonObject = epl.getMonthTarget(salesman);
		return new ResponseEntity<Json>(jsonObject, HttpStatus.OK);
	}

	/**
	 * 
	 * @Description: 根据区域名查看该区域二次提货商家详情
	 * @param @param json
	 * @param @return   
	 * @return ResponseEntity<Exam>  
	 * @throws
	 * @author changjun
	 * @date 2015年12月7日
	 */
	@RequestMapping(value = "/examDetail",method = RequestMethod.POST)
	public ResponseEntity<Map> examDetail(@RequestBody	JSONObject json){
		String saleId = json.getString("salesmanId");
		String areaName = json.getString("areaName");
		Map map  = epl.examDetail(saleId, areaName);
		return new ResponseEntity<Map>(map, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @Description: 我的收益
	 * @param @param json
	 * @param @return   
	 * @return ResponseEntity<Object>  
	 * @throws
	 * @author changjun
	 * @date 2015年11月3日
	 */
	@RequestMapping(value = "/myEarn",method = RequestMethod.POST)
	public ResponseEntity<Object> myEarn(@RequestBody JSONObject json){
		
		return new ResponseEntity<Object>(null,HttpStatus.OK);
	}
	/**
	 * 
	 * @Description:  任务-收货款
	 * @param @param username
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/takeGoodsMoney",method = RequestMethod.POST)
	public ResponseEntity<List<OrderPub>> takeGoodsMoney(@RequestBody  JSONObject json){
		String username = json.getString("username");
		//dao查询未收款订单
		List<OrderPub> list = new ArrayList<OrderPub>();
//		OrderPub order = new OrderPub();
//		order.setOrderNum("123456789");
//		order.setUsername(username);
//		order.setAddress("大桥镇");
//		order.setCreateTime(new Date());
//		order.setPayState("未支付");
//		list.add(order);
		return new ResponseEntity<List<OrderPub>>(list, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 未收款报备
	 * @param @param username
	 * @param @param orderNum
	 * @param @param reason
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	@RequestMapping(value = "/noTaskMoneyRemark",method = RequestMethod.POST)
	public ResponseEntity<Void> noTaskMoneyRemark(@RequestBody  JSONObject json){
		String username = json.getString("username");
		String orderNum = json.getString("orderNum");
		String reason = json.getString("reason");
		System.out.println(username+":"+orderNum+":"+reason);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 申请调价
	 * @param @param username
	 * @param @param town
	 * @param @param goodsname
	 * @param @param amount
	 * @param @param reason
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	//{"salesmanId":"C37010511230","regionId":"370105","applyReason":"增加竞争力","range":"-100","skuName":"红米2A"}
	 
	@RequestMapping(value = "/applyChangePrice",method = RequestMethod.POST)
	public ResponseEntity<JSONObject> applyChangePrice(@RequestBody  JSONObject json){
		String regionId = json.getString("regionId");
		String salesmanId = json.getString("salesmanId");
		ApplyPrice ap = new ApplyPrice();
		ap.setApplyReason(json.getString("applyReason"));
		ap.setApplyTime(new Date());
		ap.setPriceRange(json.getDouble("range"));
		ap.setProductName(json.getString("skuName"));
		ap.setStatus("0");
		String str = aps.saveApply(ap,salesmanId,regionId);
		JSONObject jo = new JSONObject();
		jo.put("state", str);
		return new ResponseEntity<JSONObject>( jo,HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 申请调价列表
	 * @param @param salesmanId
	 * @param @return   
	 * @return ResponseEntity<JSONObject>  
	 * @throws
	 * @author changjun
	 * @date 2015年10月21日
	 */
	//{"salesmanId":"C37010511230","pageNumber":"1","pageSize":"10"}
	@RequestMapping(value = "/applyPriceList",method = RequestMethod.POST)
	public ResponseEntity<List<Apply>> applyPriceList(@RequestBody  JSONObject json){
		String sid = json.getString("salesmanId");
		PageRequest pageRequest = SortUtil.buildPageRequest(json.getInteger("pageNumber"), json.getInteger("pageSize"),"apply");
		List<Apply> list = aps.getList(sid, pageRequest);
		return new ResponseEntity<List<Apply>>(list, HttpStatus.OK);
	}
	/**
	 * 
	 * @Description: 根据id查看申请详情
	 * @param @param json
	 * @param @return   
	 * @return ResponseEntity<ApplyPrice>  
	 * @throws
	 * @author changjun
	 * @date 2015年12月3日
	 */
	//{"id":"6"}
	@RequestMapping(value = "/findApplyById",method = RequestMethod.POST)
	public ResponseEntity<ApplyPrice> findApplyById(@RequestBody  JSONObject json){
		Long id = json.getLong("id");
		ApplyPrice ap = aps.selApplyById(id);
		return new ResponseEntity<ApplyPrice>(ap, HttpStatus.OK);
	}
}
