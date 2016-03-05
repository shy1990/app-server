package com.wangge.app.server.controller;


import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.UnpaymentRemark;
import com.wangge.app.server.pojo.message;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.service.UnpaymentRemarkService;

@RestController
@RequestMapping("/v1/ur")
public class UnpaymentRemarkController {
  private static Logger logger = Logger.getLogger("UnpaymentRemarkController");
  @Resource
  private UnpaymentRemarkService urs;
  @Resource
  private SalesmanService ss;
  
  /**
   * 
  * @Title: createUnpaymentRemark 
  * @Description: TODO(创建未收款报备) 
  * @param @param ur
  * @param @param salesmanId    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping(value = "/createRemark",method = RequestMethod.POST)
  public  ResponseEntity<message> createUnpaymentRemark(@RequestBody JSONObject jsons){
    message message = new message();
   try {
     UnpaymentRemark ur = new UnpaymentRemark();
     ur.setAboveImgUrl(jsons.getString("aboveImgUrl"));
     ur.setFrontImgUrl(jsons.getString("frontImgUrl"));
     ur.setSideImgUrl(jsons.getString("sideImgUrl"));
     ur.setOrderno(jsons.getString("orderno"));
     ur.setShopName(jsons.getString("shopName"));
     ur.setRemark(jsons.getString("remark"));
      ur.setStatus(0);
      ur.setSalesmanId(jsons.getString("salesmanId"));
      urs.saveUnpaymentRemark(ur);
      message.setMsg("保存成功！");
      return new ResponseEntity<message>(message,HttpStatus.CREATED);
  } catch (Exception e) {
     e.printStackTrace();
     logger.error("createUnpaymentRemark eeror .",e);
    message.setMsg("保存失败！");
    return new ResponseEntity<message>(message,HttpStatus.INTERNAL_SERVER_ERROR);
  }
   
  }
  
 
  /**
   * 
  * @Title: getRemarkList 
  * @Description: TODO(根据业务员id查询此业务员的报备情况) 
  * @param @param pageable
  * @param @param salesmanId
  * @param @return    设定文件 
  * @return ResponseEntity<Page<UnpaymentRemark>>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/getRemarkList",method = RequestMethod.POST)
  @ResponseBody
  public  ResponseEntity<Page<UnpaymentRemark>> getRemarkListBySalesmanId(@RequestBody JSONObject jsons){
    String pageNo = jsons.getString("pageNum");
    String pageSize = jsons.getString("pageSize") ;
    
   Page<UnpaymentRemark> pages = urs.findListBySalesmanId(jsons.getString("salesmanId"), new PageRequest(pageNo != null ?Integer.parseInt(pageNo)-1:0,pageSize != null ? Integer.parseInt(pageSize) : 10,new Sort(Direction.DESC, "id")));
    return  new ResponseEntity<Page<UnpaymentRemark>>(pages,HttpStatus.OK);
  }

}
