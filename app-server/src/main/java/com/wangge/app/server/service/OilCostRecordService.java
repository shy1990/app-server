package com.wangge.app.server.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.OilCostRecord;
import com.wangge.app.server.entity.SalesmanAddress;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.pojo.MessageCustom;
import com.wangge.app.server.pojo.message;
import com.wangge.app.server.repository.ChildAccountRepostory;
import com.wangge.app.server.repository.OilCostRecordRepository;
import com.wangge.app.server.repositoryimpl.OilRecordImpl;
import com.wangge.app.server.util.JWtoAdrssUtil;
import com.wangge.app.util.ChainageUtil;

@Service
public class OilCostRecordService {
  @Resource
  private OilCostRecordRepository trackRepository;
 // @Resource
 // private RegionRepository regionRepository;
  @Resource
  private ChildAccountRepostory accountRepostory;
  @Resource
  private SalesmanAddressService addressService;
  @Resource
  private SalesmanService salesmanService;
  @Resource
  private RegionService regionService;
  @Resource
  private RegistDataService registDataService;
  @Resource
  private ApplicationContext cxt;
  @Resource
  private OilRecordImpl oilRecordImpl;
  
  private JSONArray j = null;
  private JSONObject obj = null;
  
  
  public OilCostRecord getYesterydayOilRecord(JSONObject jsons) {
    int isPrimaryAccount  = jsons.getIntValue("isPrimary");
    String userId = jsons.getString("userId");
    String childId = jsons.getString("childId");
    
     if(isPrimaryAccount == 0){
       OilCostRecord o = oilRecordImpl.getYesterydayOilRecord(userId);
       if(o != null){
         List<OilCostRecord> orList = oilRecordImpl.getChildYesterydayOilRecord(userId);
         o.setOilRecord(ChainageUtil.createPrimaryYesterydayOilRecord(o, orList));
         return o;
       }
     }else{
       OilCostRecord chilId = oilRecordImpl.getYesterydayOilRecord(childId);
       if(chilId != null){
         OilCostRecord Primary = oilRecordImpl.getYesterydayOilRecord(userId);
         chilId.setOilRecord(ChainageUtil.createChildYesterydayOilRecord(chilId, Primary));
         return chilId;
       }
     }
    
     
      
    return null;
  }
  /**
   * 
  * @Title: addHandshake 
  * @Description: TODO(业务揽收添加握手) 
  * @param @param userId
  * @param @param coordinates
  * @param @param isPrimaryAccount
  * @param @param childId
  * @param @param type    设定文件 
  * @return void    返回类型 
  * @throws
   */
  public void addHandshake(String userId, String coordinates,
      int isPrimaryAccount, String childId, int type) {
    String id = null;
   
  //  if(!isVisited(isPrimaryAccount, childId, userId, regionId)){
      SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
      
      try {
        if(isPrimaryAccount == 1){
          id = childId;
        }else{
          id = userId;
        }
        Date   dateTime = format.parse(format.format(new Date()));
        OilCostRecord track = trackRepository.findByDateTimeAndUserId(dateTime,id);
        if(track != null){
        JSONArray jsonArray = JSONArray.parseArray(track.getOilRecord());
        
        track.setIsPrimaryAccount(isPrimaryAccount);
       // track.setOilRecord( getOilRecord( coordinates,  type, regionId, shopName, storePhone));
        if(isPrimaryAccount == 1){
          track.setUserId(childId);
          track.setParentId(userId);
        }else{
          track.setUserId(userId);
        }
        Long mileage =  getDistance(coordinates,null,track.getDistance(),jsonArray);
        track.setDistance(mileage);
        j  = getOilRecord( coordinates,  type, userId);
        jsonArray.add(j.get(0));
        track.setOilRecord(jsonArray.toString());
        track.setOilCost(mileage*1.5f);
        trackRepository.save(track);
        }else{
            OilCostRecord ocr = new OilCostRecord();
             ocr.setDateTime(dateTime);
             ocr.setIsPrimaryAccount(isPrimaryAccount);
             if(isPrimaryAccount == 1){
               ocr.setUserId(childId);
               ocr.setParentId(userId);
             }else{
               ocr.setUserId(userId);
             }
             j = getOilRecord( coordinates,  type, userId);
             j.add(j);
             ocr.setOilRecord(j.toString());
             trackRepository.save(track);
         
        }
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
     
   // }
  } 
  /**
   * 
  * @Title: addHandshake 
  * @Description: TODO(揽收签收拒收添加握手) 
  * @param @param regionId
  * @param @param userId
  * @param @param coordinates
  * @param @param isPrimaryAccount
  * @param @param childId
  * @param @param type  
  * @param @param phone 商城用户手机号 设定文件 
  * @return void    返回类型 
  * @throws
   */
  public void addHandshake(String userId,String coordinates,int isPrimaryAccount,String childId,int type,String storePhone){
    String id = null;
    String regionId = null;
    String shopName = null;
    String regionName = null;
    Map<String, String> map = registDataService.getMap(storePhone);
    if(map != null){
      regionId = map.get("regionId");
      shopName = map.get("shopName");
      regionName = map.get("regionName");
    }
    if(!isVisited(isPrimaryAccount, childId, userId, regionId)){
      SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
      
      try {
        if(isPrimaryAccount == 1){
          id = childId;
        }else{
          id = userId;
        }
        Date   dateTime = format.parse(format.format(new Date()));
        OilCostRecord track = trackRepository.findByDateTimeAndUserId(dateTime,id);
        if(track != null){
        JSONArray jsonArray = JSONArray.parseArray(track.getOilRecord());
        
        track.setIsPrimaryAccount(isPrimaryAccount);
       // track.setOilRecord( getOilRecord( coordinates,  type, regionId, shopName, storePhone));
        if(isPrimaryAccount == 1){
          track.setUserId(childId);
          track.setParentId(userId);
        }else{
          track.setUserId(userId);
        }
        Long mileage =  getDistance(coordinates,regionId,track.getDistance(),jsonArray);
        track.setDistance(mileage);
        JSONObject object = getOilRecord(coordinates, type, null, shopName,regionName);
        jsonArray.add(object);
        track.setOilRecord(jsonArray.toString());
        track.setOilCost(mileage*1.5f);
        String regionIds =  track.getRegionIds();
        regionIds = regionIds +","+regionId;
        track.setRegionIds(regionIds);
        trackRepository.save(track);
        }else{
            OilCostRecord ocr = new OilCostRecord();
             ocr.setDateTime(dateTime);
             ocr.setIsPrimaryAccount(isPrimaryAccount);
             if(isPrimaryAccount == 1){
               ocr.setUserId(childId);
               ocr.setParentId(userId);
             }else{
               ocr.setUserId(userId);
             }
             j = new JSONArray();
             JSONObject object = getOilRecord(coordinates, type, null, shopName,regionName);
             j.add(object);
             ocr.setOilRecord(j.toString());
             trackRepository.save(track);
         
        }
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
     
    }
  }
  /**
   * 
  * @Title: addHandshake 
  * @Description: TODO(扫街，注册，拜访，增加握手) 
  * @param @param jsons    设定文件 
  * @return void    返回类型 
  * @throws
   */
   public void addHandshake(String regionId,String userId,String shopName,String coordinates, int isPrimaryAccount,String childId,int type) {
       /*coordinates = jsons.getString("coordinates");
       isPrimaryAccount = jsons.getIntValue("isPrimaryAccount");
      regionId = jsons.getString("regionId");
      userId = jsons.getString("userId");
      shopName = jsons.getString("shopName");
      type = jsons.getIntValue("type");*/
      String id = null;
      
      if(!isVisited(isPrimaryAccount, childId, userId, regionId)){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        
        try {
          if(isPrimaryAccount == 1){
            id = childId;
          }else{
            id = userId;
          }
          Date   dateTime = format.parse(format.format(new Date()));
          OilCostRecord track = trackRepository.findByDateTimeAndUserId(dateTime,id);
          if(track != null){
          JSONArray jsonArray = JSONArray.parseArray(track.getOilRecord());
          
          track.setIsPrimaryAccount(isPrimaryAccount);
         // track.setOilRecord( getOilRecord( coordinates,  type, regionId, shopName, storePhone));
          if(isPrimaryAccount == 1){
            track.setUserId(childId);
            track.setParentId(userId);
          }else{
            track.setUserId(userId);
          }
          Long mileage =  getDistance(coordinates,regionId,track.getDistance(),jsonArray);
          track.setDistance(mileage);
          JSONObject object = getOilRecord(coordinates, type, regionId, shopName,null);
          jsonArray.add(object);
          track.setOilRecord(jsonArray.toString());
          track.setOilCost(mileage*1.5f);
          String regionIds =  track.getRegionIds();
          regionIds = regionIds +","+regionId;
          track.setRegionIds(regionIds);
          trackRepository.save(track);
          }else{
              OilCostRecord ocr = new OilCostRecord();
               ocr.setDateTime(dateTime);
               ocr.setIsPrimaryAccount(isPrimaryAccount);
               if(isPrimaryAccount == 1){
                 ocr.setUserId(childId);
                 ocr.setParentId(userId);
               }else{
                 ocr.setUserId(userId);
               }
               j = new JSONArray();
               JSONObject object = getOilRecord(coordinates, type, regionId, shopName,null);
               j.add(object);
               ocr.setOilRecord(j.toString());
               trackRepository.save(track);
           
          }
        } catch (ParseException e) {
          e.printStackTrace();
        }
       
      }
      
   
  
  }
  
  
  
/**
 * 
* @Title: signed 
* @Description: TODO(上下班签到) 
* @param @param jsons    设定文件 
* @return void    返回类型 
* @throws
 */
  public  ResponseEntity<MessageCustom> signed(JSONObject jsons) {
   int isPrimaryAccount = jsons.getIntValue("isPrimary");
   String coordinates = jsons.getString("coordinate");
   String userId = jsons.getString("userId");
    String childId = jsons.getString("childId");
    int type = jsons.getIntValue("apiType");
    OilCostRecord ocr = new OilCostRecord();
    MessageCustom m = new MessageCustom();
    
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    
     try {
       Date   dateTime = format.parse(format.format(new Date()));
       if(type == 1){
         ocr.setUserId(userId);
         ocr.setDateTime(dateTime);
         ocr.setOilRecord(getOilRecord(coordinates,type,userId).toString());
         ocr.setIsPrimaryAccount(isPrimaryAccount);
           trackRepository.save(ocr);
           m.setMsg("签到成功！");
           return new ResponseEntity<MessageCustom>(m,HttpStatus.OK);
       }else if(type ==8){
        // Date   dateTime = format.parse(format.format(new Date()));
       
         OilCostRecord track = trackRepository.findByDateTimeAndUserId(format.parse(format.format(new Date())),userId);
         if(track != null){
         JSONArray jsonArray = JSONArray.parseArray(track.getOilRecord());
         // String str = getOilRecord( coordinates,  type);
         jsonArray.add(getOilRecord(coordinates,type,userId).get(0));
         track.setDateTime(dateTime);
         track.setIsPrimaryAccount(isPrimaryAccount);
         track.setParentId(userId);
         track.setOilRecord(jsonArray.toString());
         Long mileage =  getDistance(coordinates,null,track.getDistance(),jsonArray);
         track.setDistance(mileage);
         track.setOilCost(mileage*1.5f);
         trackRepository.save(track);
         m.setMsg("签到成功！");
         return new ResponseEntity<MessageCustom>(m,HttpStatus.OK);
         }
       }      
    } catch (Exception e) {
      e.printStackTrace();
      m.setMsg("签到失败!");
    }
     return new ResponseEntity<MessageCustom>(m,HttpStatus.BAD_REQUEST);
  }
  /**
   * 
  * @Title: isError 
  * @Description: TODO(判断上下班是否异常) 
  * @param @param coordinates
  * @param @param userId
  * @param @return    设定文件 
  * @return boolean    返回类型 
  * @throws
   */
  private boolean isError(String coordinates,String userId){
   String[] coordinates1 = coordinates.split("-");
   String homePont = addressService.getHomePoint(userId);
   String[] coordinates2 = homePont.split("-");
   /*String regionName = salesmanService.getSalesman(userId);
   
   
   
   String param = "&origin='"+coordinates1[1]+"','"+coordinates1[0]+"'&destination='"+coordinates2[1]+"','"+coordinates2[0]+"'&origin_region='"+regionName+"'&destination_region='"+regionName+"'";
   Double d = ChainageUtil.createDistance(param);*/
   Double d = ChainageUtil.GetShortDistance(Double.parseDouble(coordinates1[0]) , Double.parseDouble(coordinates1[1]), Double.parseDouble(coordinates2[0]), Double.parseDouble(coordinates2[1]));
   if(d < Double.parseDouble("50")){
     return false;
   }
   
    return true;
  }
  /**
   * 
  * @Title: getOilRecord 
  * @Description: TODO(扫街，注册，拜访，返回油补记录json串) 
  * @param @param coordinates
  * @param @param type
  * @param @param regionId
  * @param @param shopName
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  
  private JSONObject getOilRecord(String coordinates, int type,String regionId,String shopName,String regionName){
   if(type == 2){
      String typeName  = "扫街";
      int regionType = 2;
       regionName = regionService.getRegionName(regionId);
      obj = ChainageUtil.createOilRecord( regionName, shopName, coordinates, typeName, regionType);
      
    }else if(type == 3){
      String typeName  = "注册";
      int regionType = 2;
       regionName = regionService.getRegionName(regionId);
      obj = ChainageUtil.createOilRecord(regionName, shopName, coordinates, typeName, regionType);
      
    }else if(type == 4){
      String typeName  = "拜访";
      int regionType = 2;
      regionName = regionService.getRegionName(regionId);
      obj = ChainageUtil.createOilRecord(regionName, shopName, coordinates, typeName, regionType);
      
    }else if(type == 6){
      String typeName  = "客户签收";
      int regionType = 2;
        obj = ChainageUtil.createOilRecord(regionName,shopName,coordinates,typeName,regionType);
    }else if(type == 7){
      String typeName  = "客户拒收";
      int regionType = 2;
        obj = ChainageUtil.createOilRecord(regionName,shopName,coordinates,typeName,regionType);
      
    }
    return obj;
  }
  
  /**
   * 
  * @Title: getOilRecord 
  * @Description: TODO(上下班，物流点json串) 
  * @param @param coordinates
  * @param @param type
  * @param @param userId
  * @param @return    设定文件 
  * @return JSONArray    返回类型 
  * @throws
   */
  private JSONArray  getOilRecord(String coordinates, int type,String userId){
    String  regionName = null;
    int  regionType;
    if (isError(coordinates, userId)) {
      regionName = "异常 ";
    }else{
      if(type == 1 || type == 8){
        regionName = "家";
      }else{
        regionName = getLogistics( coordinates, userId);
      }
    } 
    if(type == 1 ){
     String typeName  = "上班";
    
     regionType = 3;
    j  = new JSONArray();
    
     obj = ChainageUtil.createOilRecord( coordinates, typeName, regionName,regionType);
   
     j.add(obj);
   // return j;
  }else if(type == 8){
    String typeName  = "下班";
    regionType = 3;
    j  = new JSONArray();
    obj = ChainageUtil.createOilRecord(coordinates, typeName, regionName,regionType);
   
    j.add(obj);
   // return j;
  }else if(type == 5){
    regionType = 1;
    String  typeName  = "业务揽收";
    j  = new JSONArray();
    obj = ChainageUtil.createOilRecord(coordinates, typeName, regionName,regionType);
    j.add(obj);
  }
    return j;
  }
  /**
   * 
  * @Title: getDistance 
  * @Description: TODO(业务进行扫街，考核，拜访时的距离) 
  * @param @param coordinates
  * @param @param regionId
  * @param @param distance
  * @param @param jsonArray
  * @param @return    设定文件 
  * @return Long    返回类型 
  * @throws
   */
  private Long getDistance(String coordinates,String regionId,Long mileage, JSONArray jsonArray){
    String[] coordinates1 = coordinates.split("-");
    String[] coordinates2  = null;
    String regionName = null;
    if(jsonArray != null){
     // index = 
      JSONObject  s =  (JSONObject)jsonArray.get(jsonArray.size()-1);
       coordinates2 = s.getString("coordinates").split("-");
    }
    if(regionId != null){
      regionName = regionService.getRegionName(regionId);
    }else{
       regionName = getRegionName(coordinates);
    }
    
    
    String param = "&origin='"+coordinates1[1]+"','"+coordinates1[0]+"'&destination='"+coordinates2[1]+"','"+coordinates2[0]+"'&origin_region='"+regionName+"'&destination_region='"+regionName+"'";
    Long d = ChainageUtil.createDistance(param);//百度地图返回的json中解析出两点之间的导航出的距离单位米
    Long distance = d/1000;//转换成公里
     mileage = mileage != null ? distance + mileage : distance;//将区域之间的距离叠加起来
    return mileage;
  }
  
  /**
   * @param userId2 
   * @param coordinates2 
   * 
  * @Title: getLogistics 
  * @Description: TODO(根据当前坐标判断物流点) 
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  private String getLogistics(String coordinates, String userId){
    String[] coordinates2 = null;
    String[] coordinates3 = null;
    String[] coordinates4 = null;
    Double d1 = null;
    Double d2 = null;
    Double d3 = null;
    String Logistics = null;
    String[] coordinates1 = coordinates.split("-");
   // String x =    getLogistics(coordinates, userId);
      SalesmanAddress address = addressService.getAddress(userId);
      if(address != null){
        String Logistics1 = address.getLogisticsPoint1();
        String Logistics2 = address.getLogisticsPoint2();
        String Logistics3 = address.getLogisticsPoint3();
        if(Logistics1 != null && "".equals(Logistics1)){
          coordinates2 = Logistics1.split("-");
           d1 = ChainageUtil.GetShortDistance(Double.parseDouble(coordinates1[0]) , Double.parseDouble(coordinates1[1]), Double.parseDouble(coordinates2[0]), Double.parseDouble(coordinates2[1]));
        }else if(Logistics2 != null && "".equals(Logistics2)){
          coordinates3 = Logistics2.split("-");
           d2 = ChainageUtil.GetShortDistance(Double.parseDouble(coordinates1[0]) , Double.parseDouble(coordinates1[1]), Double.parseDouble(coordinates3[0]), Double.parseDouble(coordinates3[1]));
        }else if(Logistics3 != null && "".equals(Logistics3)){
          coordinates4 = Logistics3.split("-");
           d3 = ChainageUtil.GetShortDistance(Double.parseDouble(coordinates1[0]) , Double.parseDouble(coordinates1[1]), Double.parseDouble(coordinates4[0]), Double.parseDouble(coordinates4[1]));
        }
      }
     
      if(d1 < Double.parseDouble("50")){
        Logistics =  "物流点一";
      }else if(d2 <  Double.parseDouble("50")){
        Logistics = "物流点二";
      }else if(d3 <  Double.parseDouble("50")){
        Logistics = "物流点三";
      }
      return  Logistics ;
  }
  /**
   * 
  * @Title: isVisited 
  * @Description: TODO(是否已经拜访过，扫过街，注册等) 
  * @param @param isPrimaryAccount
  * @param @param childId
  * @param @param userId
  * @param @param regionId
  * @param @return    设定文件 
  * @return boolean    返回类型 
  * @throws
   */
  private boolean  isVisited(int isPrimaryAccount,String childId,String userId,String regionId){
    if(isPrimaryAccount == 0){
      List<OilCostRecord> orList = trackRepository.findByParentId(userId);
       for(OilCostRecord  oilCostRecord : orList){
        if( oilCostRecord.getRegionIds().contains(regionId)){
          return false;
        }
       }
       return true;
    }else{
      OilCostRecord or = trackRepository.findByUserId(userId);
      if(or.getRegionIds().contains(regionId)){
        return false;
      }
      return true;
    }
     
  }
   
  /**
   * 
  * @Title: getRegionName 
  * @Description: TODO(通过坐标点调用百度接口，获取行政区域市) 
  * @param @param coordinates
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  private String getRegionName(String coordinates){
     String[] city =  coordinates.split("-");
     String lng = city[1];
     String lat = city[0];
    String url="http://api.map.baidu.com/geocoder/v2/?ak=702632E1add3d4953d0f105f27c294b9&callback=renderReverse&location="+lng+","+lat+"&output=json&pois=1";
     String jsonString = JWtoAdrssUtil.getdata(url);
    String jsonstr=jsonString.substring(0,jsonString.length()-1);
    String address = jsonstr.substring(jsonstr.indexOf("city")+6,jsonstr.indexOf("country")-2);
    return address;
  }
  
  
 
}
