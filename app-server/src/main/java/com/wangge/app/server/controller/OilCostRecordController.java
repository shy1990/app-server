package com.wangge.app.server.controller;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.pojo.HistoryDestOilRecord;
import com.wangge.app.server.pojo.MessageCustom;
import com.wangge.app.server.pojo.TodayOilRecord;
import com.wangge.app.server.service.OilCostRecordService;


/**
 * 
* @ClassName: TrackController
* @Description: TODO(油补类)
* @author A18ccms a18ccms_gmail_com
* @date 2016年3月30日 上午10:43:30
*
 */

@RestController
@RequestMapping("/v1/oilCostRecord")
public class OilCostRecordController {
  
  private static final Logger logger =  LoggerFactory.getLogger(OilCostRecordController.class); // NOPMD by Administrator on 16-3-30 下午5:43
  @Resource
  private OilCostRecordService trackService;
  
  
  @RequestMapping(value = "/workCheck", method = RequestMethod.POST)
  public ResponseEntity<MessageCustom> signed(@RequestBody JSONObject jsons){
    
    return trackService.signed(jsons);
  }
  /**
   * 
  * @Title: addHandshake 
  * @Description: TODO(获取昨日油补记录) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<JsonCustom>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/getYesterydayOilRecord",method = RequestMethod.POST)
 public ResponseEntity<MessageCustom> yesterdayOilRecord(@RequestBody JSONObject jsons){
    MessageCustom m = new MessageCustom();
    JSONObject object =  trackService.getYesterydayOilRecord(jsons);
    if(object != null){
       m.setObj(object);
       return new ResponseEntity<MessageCustom>(m,HttpStatus.OK);
    }else{
      m.setMsg("无昨日油补！");
      m.setCode(1);
      return new ResponseEntity<MessageCustom>(m,HttpStatus.BAD_REQUEST);
    }
    
   
 }
  /**
   * 
  * @Title: getHistoryOilRecord 
  * @Description: TODO(历史油补累计) 
  * @param @param jsons    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping(value ="/getHistoryOilRecord", method = RequestMethod.POST)
  public ResponseEntity<MessageCustom>  getHistoryOilRecord(@RequestBody JSONObject jsons){
    return trackService.getHistoryOilRecord(jsons);
  }

  /**
   * 
    * getTodayOilRecord:当日油补记录
    * @author robert 
    * @param jsons
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping(value = "/getTodayOilRecord", method = RequestMethod.POST)
  public ResponseEntity<TodayOilRecord> getTodayOilRecord(@RequestBody JSONObject jsons){
      String isPrimary=jsons.getString("isPrimary");//0-主账号 1-子账号
      String  userId  =jsons.getString("userId");//业务员id
      String childId  =jsons.getString("childId");//子账号id
      
      TodayOilRecord oildRecord=new TodayOilRecord();
      try {
        oildRecord = trackService.getTodayOilRecord(isPrimary,userId,childId);
        return new ResponseEntity<TodayOilRecord>(oildRecord,HttpStatus.OK);
      } catch (Exception e) {
        oildRecord.setCode(1);
        oildRecord.setMsg("服务器异常");
        return new ResponseEntity<TodayOilRecord>(oildRecord,HttpStatus.BAD_REQUEST);
      }
      
  }
  
  
  /**
   * 
    * getHistoryDestOilRecord:历史油补详情
    * @author Administrator 
    * @param jsons
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping(value = "/getHistoryDestOilRecord", method = RequestMethod.POST)
  public ResponseEntity<HistoryDestOilRecord> getHistoryDestOilRecord(@RequestBody JSONObject jsons){
      String  userId  =jsons.getString("userId");//业务员id
      int dateYear    =jsons.getIntValue("dateYear");//日期-年
      int dateMonth   =jsons.getIntValue("dateMonth");//日期-月份
      
      HistoryDestOilRecord historyDestOilRecord=new HistoryDestOilRecord();
      
      try {
        historyDestOilRecord=trackService.getMonthOilRecord(userId, dateYear, dateMonth) ;
        return new ResponseEntity<HistoryDestOilRecord>(historyDestOilRecord,HttpStatus.OK);
      } catch (Exception e) {
        historyDestOilRecord.setCode(1);
        historyDestOilRecord.setMsg("服务器异常");
        return new ResponseEntity<HistoryDestOilRecord>(historyDestOilRecord,HttpStatus.BAD_REQUEST);
      }
      
      
  }
  
}
