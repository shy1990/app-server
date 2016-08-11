package com.wangge.app.server.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.entity.ApplyPrice;
import com.wangge.app.server.entity.Order;
import com.wangge.app.server.entity.OrderItem;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.RegionRepository;
import com.wangge.app.server.repositoryimpl.ExamImpl;
import com.wangge.app.server.repositoryimpl.OrderImpl;
import com.wangge.app.server.service.ApplyPriceService;
import com.wangge.app.server.service.MessageService;
import com.wangge.app.server.service.OrderService;
import com.wangge.app.server.service.RegistDataService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.util.HttpUtil;
import com.wangge.app.server.util.LogUtil;
import com.wangge.app.server.util.SortUtil;
import com.wangge.app.server.vo.Apply;
import com.wangge.app.server.vo.Exam;
import com.wangge.app.server.vo.OrderPub;

@RestController
@RequestMapping(value = "/v1/mine")
public class MineController {
	
//	private static final Logger LOG = LoggerFactory.getLogger(MineController.class);
	
  @Resource
  private OrderImpl opl;
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

  @Value("${app-interface.url}")
  private String interfaceUrl;

  @Resource
  private HttpRequestHandler httpRequestHandler;
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
  @ApiOperation(value="根据业务手机号订单号判断该订单是否属于该业务员并返回订单详情",notes="获取详情")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
	@RequestMapping(value = "/checkByOrderNum",method = RequestMethod.POST)
<<<<<<< HEAD
	public JSONObject checkByOrderNum(@RequestBody  JSONObject json) throws Exception{
	    LogUtil.info("根据业务手机号订单号判断该订单是否属于该业务员并返回订单详情， json="+json.toJSONString());
      return httpRequestHandler.exchange(interfaceUrl+"/checkByOrderNum", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
      });
=======
	public ResponseEntity<JSONObject> checkByOrderNum(@RequestBody  JSONObject json) throws Exception{
//		String mobile = json.getString("mobile");
		String ordernum = json.getString("ordernum");
		String regionId = json.getString("regionId");
		
		JSONObject jo = new JSONObject();
		Order order = or.findOne(ordernum);
		
		
		if(order!=null && order.getStatus().ordinal()>= 2){
		  StringBuffer sb = new StringBuffer();
      int skuNum = 0;  
      int giftNum = 0;
      for (OrderItem item : order.getItems()) {
        sb.append(item.getName()+" ");
        if("sku".equals(item.getType())){
          skuNum+=item.getNums();
        }else if("gift".equals(item.getType()) || "accessories".equals(item.getType())){
          giftNum+=item.getNums();
        }
      }
      jo.put("username", order.getShopName());
      jo.put("createTime", order.getCreateTime());
      jo.put("orderNum", order.getId());
      jo.put("shipStatus", order.getStatus().ordinal());
      jo.put("amount", order.getAmount());
      jo.put("mobile", order.getMobile());
      jo.put("skuNum", skuNum);
      jo.put("itemOtherNum", giftNum);
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
    jo.put("msg", "未查询相关信息或快件未揽收,请重试");
    return new ResponseEntity<JSONObject>(jo, HttpStatus.BAD_REQUEST);

>>>>>>> branch 'dev' of https://git.oschina.net/wgtechnology/app-server.git
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
  @ApiOperation(value="根据业务手机号查询所属订单的派送状态",notes="根据业务手机号查询所属订单的派送状态")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
  @RequestMapping(value = "/orderStatusList",method = RequestMethod.POST)
  public JSONObject orderStatusList(@RequestBody  JSONObject json){
    LogUtil.info("根据业务手机号查询所属订单的派送状态， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/orderStatusList", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
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
  @ApiOperation(value="业务签收后更新订单状态",notes="业务签收后更新订单状态")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
  @RequestMapping(value = "/updateOrderStatus",method = RequestMethod.POST)
  public JSONObject updateOrderStatus(@RequestBody  JSONObject json){
    LogUtil.info("业务签收后更新订单状态， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/orderStatusList", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
  }
  
  /*///////////////////     V2
  *//**
   * 
   * @Description: 客户拒签
   * @param @param json
   * @param @return   
   * @return ResponseEntity<JSONObject>  
   * @throws
   * @author changjun
   * @date 2015年12月1日
<<<<<<< HEAD
   */
  @ApiOperation(value="客户拒签",notes="客户拒签")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
  @RequestMapping(value = "/custNotSignFor",method = RequestMethod.POST)
  public JSONObject custNotSignFor(@RequestBody  JSONObject json){
    LogUtil.info("客户拒签， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/custNotSignFor", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
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
  @ApiOperation(value="客户签收",notes="客户签收")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
	@RequestMapping(value = "/custSignFor",method = RequestMethod.POST)
	public JSONObject custSignFor(@RequestBody  JSONObject json){
    LogUtil.info("客户签收， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/custSignFor", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
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
  @ApiOperation(value="发送验证码",notes="发送验证码")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
	@RequestMapping(value = "/sendCode",method = RequestMethod.POST)
	public JSONObject sendCode(@RequestBody  JSONObject json){
    LogUtil.info("发送验证码， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/sendCode", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
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
  @ApiOperation(value="验证码验证",notes="验证码验证")
  @ApiImplicitParams({
    @ApiImplicitParam(name="mobile",value="mobile",required=true,dataType="String"),
    @ApiImplicitParam(name="code",value="code",required=true,dataType="String")
  })
	public String validateCode(String mobile,String code){
    LogUtil.info("验证码验证， mobile="+mobile+"code"+code);
    JSONObject json = new JSONObject();
    json.put("mobile", mobile);
    json.put("code", code);
    return httpRequestHandler.exchange(interfaceUrl+"/sendCode", HttpMethod.POST, null, json, new ParameterizedTypeReference<String>() {
    });
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
  @ApiOperation(value="考核状态",notes="考核状态")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
	@RequestMapping(value = "/examStatus",method = RequestMethod.POST)
	public JSONObject examStatus(@RequestBody	JSONObject json) throws ParseException{
    LogUtil.info("客户签收， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/examStatus", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
	}

	/**
	 * 月指标统计查询
	 * @param json
	 * @return
	 * @throws ParseException
     */
  @ApiOperation(value="月指标统计查询",notes="月指标统计查询")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
	@RequestMapping(value = "/monthTarget",method = RequestMethod.POST)
	public JSONObject getMonthTarget(@RequestBody	JSONObject json) throws ParseException{
    LogUtil.info("月指标统计查询， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/monthTarget", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
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
  @ApiOperation(value="根据区域名查看该区域二次提货商家详情",notes="根据区域名查看该区域二次提货商家详情")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
	@RequestMapping(value = "/examDetail",method = RequestMethod.POST)
	public JSONObject examDetail(@RequestBody	JSONObject json){
    LogUtil.info("根据区域名查看该区域二次提货商家详情， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/examDetail", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
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
  @ApiOperation(value="我的收益",notes="我的收益")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
	@RequestMapping(value = "/myEarn",method = RequestMethod.POST)
	public JSONObject myEarn(@RequestBody JSONObject json){
    LogUtil.info("我的收益， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/myEarn", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
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
  @ApiOperation(value="任务-收货款",notes="任务-收货款")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
	@RequestMapping(value = "/takeGoodsMoney",method = RequestMethod.POST)
	public JSONObject takeGoodsMoney(@RequestBody  JSONObject json){
    LogUtil.info("我的收益， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/myEarn", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
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
  @ApiOperation(value="未收款报备",notes="未收款报备")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
	@RequestMapping(value = "/noTaskMoneyRemark",method = RequestMethod.POST)
	public JSONObject noTaskMoneyRemark(@RequestBody  JSONObject json){
    LogUtil.info("我的收益， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/myEarn", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
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
  @ApiOperation(value="申请调价",notes="申请调价")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
	@RequestMapping(value = "/applyChangePrice",method = RequestMethod.POST)
	public JSONObject applyChangePrice(@RequestBody  JSONObject json){
    LogUtil.info("我的收益， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/myEarn", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
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
  @ApiOperation(value="申请调价列表",notes="申请调价列表")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
	@RequestMapping(value = "/applyPriceList",method = RequestMethod.POST)
	public JSONObject applyPriceList(@RequestBody  JSONObject json){
    LogUtil.info("我的收益， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/myEarn", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
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
  @ApiOperation(value="根据id查看申请详情",notes="根据id查看申请详情")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
	@RequestMapping(value = "/findApplyById",method = RequestMethod.POST)
	public JSONObject findApplyById(@RequestBody  JSONObject json){
    LogUtil.info("我的收益， json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"/myEarn", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
	}
}
