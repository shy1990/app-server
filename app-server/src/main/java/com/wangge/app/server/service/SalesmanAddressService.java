package com.wangge.app.server.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.SalesmanAddress;
import com.wangge.app.server.pojo.MessageCustom;
import com.wangge.app.server.pojo.message;
import com.wangge.app.server.repository.SalesmanAddressRepository;
/**
 * 
* @ClassName: SalesmanAddressService
* @Description: TODO(SalesmanAddressService 类)
* @author SongBaozhen
* @date 2016年4月8日 上午11:52:12
*
 */
@Service
public class SalesmanAddressService {
  @Resource
  private SalesmanAddressRepository salesmanAddressRepository;
  
  private  SimpleDateFormat formate = new SimpleDateFormat("yyyy/MM");
  /**
   * 
  * @Title: addSalesmanAddress 
  * @Description: TODO(添加或更新业务员地址) 
  * @param @param s    设定文件 
  * @return void    返回类型 
  * @throws
   */
  public ResponseEntity<MessageCustom> addSalesmanAddress(JSONObject jsons){
    MessageCustom   m = new MessageCustom();
   
    
    String logistics1 = jsons.getString("logisticsPoint1");
    String logistics2 = jsons.getString("logisticsPoint2");
    String logistics3 = jsons.getString("logisticsPoint3");
    String homePoint  = jsons.getString("homePoint");
    String userId = jsons.getString("userId");
    SalesmanAddress sa = salesmanAddressRepository.findByUserId(userId);
   
        try {
          if(sa != null){
            if(!formate.format(sa.getUpdateTime()).equals(formate.format(new Date()))){
              sa.setUpdateTime(new Date());
              sa.setHomePoint(homePoint);
              sa.setLogisticsPoint1(logistics1);
              sa.setLogisticsPoint2(logistics2);
              sa.setLogisticsPoint3(logistics3);
              salesmanAddressRepository.save(sa);
              m.setMsg("更新成功！");
              m.setCode(0);
              return new ResponseEntity<MessageCustom>(m,HttpStatus.OK);
            }
            m.setMsg("更新失败！只允许当月修改一次");
            return new ResponseEntity<MessageCustom>(m,HttpStatus.BAD_REQUEST);
        }else{
         
            sa = new SalesmanAddress();
            sa.setUserId(userId);
            sa.setUpdateTime(new Date());
            sa.setHomePoint(homePoint);
            sa.setLogisticsPoint1(logistics1);
            sa.setLogisticsPoint2(logistics2);
            sa.setLogisticsPoint3(logistics3);
            salesmanAddressRepository.save(sa);
            m.setMsg("添加成功！");
            m.setCode(0);
           return new ResponseEntity<MessageCustom>(m,HttpStatus.OK);
           }
     } catch (Exception e) {
       e.printStackTrace();
       m.setMsg("添加失败！");
       m.setCode(1);
       return new ResponseEntity<MessageCustom>(m,HttpStatus.BAD_REQUEST);
    }
      
     
  }
  /**
   * 
  * @Title: getSalesmanAddress 
  * @Description: TODO(根据业务员id查询业务员住址和物流点) 
  * @param @param userId
  * @param @return    设定文件 
  * @return SalesmanAddress    返回类型 
  * @throws
   */
  public ResponseEntity<MessageCustom> getSalesmanAddress(JSONObject jsons) {
    MessageCustom m = new MessageCustom();
    String userId = jsons.getString("userId");
    SalesmanAddress sa = salesmanAddressRepository.findByUserId(userId);
    if(sa != null){
       m.setObj(sa);
       m.setCode(0);
       if(formate.format(sa.getUpdateTime()).equals(formate.format(new Date()))){
         m.setEnable(false);
       }else{
         m.setEnable(true);
       }
       return new ResponseEntity<MessageCustom>(m,HttpStatus.OK );
      
    }
    m.setCode(1);
    m.setMsg("还没有添加家庭和物流地址！");
    return new ResponseEntity<MessageCustom>(m,HttpStatus.BAD_REQUEST);
   
  }
  
  public String getHomePoint(String userId) {
   SalesmanAddress salesmanAddress =  salesmanAddressRepository.findByUserId(userId);
   if(salesmanAddress != null){
     
     return salesmanAddress.getHomePoint();
   }
    return null;
  }
  public SalesmanAddress getAddress(String userId) {
    
    return salesmanAddressRepository.findByUserId(userId);
  }

}
