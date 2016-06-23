package com.wangge.app.server.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.app.server.entity.Cash;
import com.wangge.app.server.entity.MonthPunish;
import com.wangge.app.server.entity.OrderItem;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.WaterOrderCash;
import com.wangge.app.server.entity.WaterOrderDetail;
import com.wangge.app.server.pojo.CashPart;
import com.wangge.app.server.pojo.OrderDetailPart;
import com.wangge.app.server.repository.CashRepository;
import com.wangge.app.server.repository.OrderItemRepository;
import com.wangge.app.server.repository.WaterOrderCashRepository;
import com.wangge.app.server.repository.WaterOrderDetailRepository;
import com.wangge.app.server.util.DateUtil;
@Service
public class CashService {
  
  private Logger logger=Logger.getLogger(CashService.class);
  
  @Resource
  private MonthPunishService mps;
  
  @Resource
  private WaterOrderService wos;
  @Resource
  private CashRepository cr;
  @Resource
  private OrderItemRepository oir;
  @Resource
  private WaterOrderCashRepository wocr;
  @Resource
  private WaterOrderDetailRepository wodr;

  
  /**
   * 现金订单购物车
   * @param userId
   * @return
   */
  public List<CashPart> findByUserId(String userId){
    logger.info("findByUserId-->userId:"+userId);
    List<Cash> cashList = null;
    List<CashPart> cashPartList = new ArrayList<>();
    try {
      cashList=cr.findByUserIdAndStatus(userId,0);
      cashList.forEach(cash->{
        String orderNo= cash.getOrder().getOrderNo();
        cash.setOrderItem(oir.findByOrder_Id(orderNo));
        CashPart part = new CashPart();
        OrderSignfor order=cash.getOrder();
        part.setId(cash.getCashId());
        part.setNum(order.getOrderNo());
        part.setCash(order.getOrderPrice());
        part.setDetails(disposeOrderItem(cash.getOrderItem()));
        cashPartList.add(part);
      });
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    
    
    return cashPartList;
  }

  /**
   * 处理收现金数据
   * @param itemList 
   */
  public List<OrderDetailPart> disposeOrderItem(List<OrderItem> itemList){
    List<OrderDetailPart> detailList = new ArrayList<>();
    itemList.forEach(item->{
      OrderDetailPart detailPart=new OrderDetailPart();
      String type = item.getType();
      switch (type) {
      case "sku":
        detailPart.setPhoneName(item.getName());
        detailPart.setPhoneNum(item.getNums());
        break;

      default:
        detailPart.setPartsName(item.getName());
        detailPart.setPartsNum(item.getNums());
        
        break;
      }
      detailList.add(detailPart);
      
    });
    return detailList;
  }
  /**
   * 购物车结算
   * 将所有未结算订单全部结算
   * 
   * 流程：1.根据userID和cashIds查询所要处理的现金订单cash
   * 2.生成流水单号-->使用：流水订单+流水订单详情
   * 3.组装流水单详情数据
   * 4.组装流水单数据
   * 5.保存流水单
   * 6.保存流水单详情列表
   * 7.修改现金订单列表中状态status改为1（已结算）
   * 8.返回状态
   * @param userId
   * @param cashIds
   * @return
   */
  @Transactional(readOnly=false)
  public boolean cashToWaterOrder(String userId){
    boolean msg=false;
    logger.info("cashToWaterOrder----->userId:"+userId);
    try {
      
      //查询代数现金列表现金列表
      List<Cash> cashlist=cr.findByUserIdAndStatus(userId, 0);
      //流水单号详情
      List<WaterOrderDetail> detailList=new ArrayList<>();
      
      //生成流水单号 
      String serialNo=createSerialNo();
      
      Float totalPrice = new Float(0);
      if(cashlist.size()>0){
        for(Cash cash:cashlist){
          //计算流水单号收现金金额
          OrderSignfor order=cash.getOrder();
          totalPrice+=order.getOrderPrice();
          
          //组装流水单号详情数据
          WaterOrderDetail detail=new WaterOrderDetail();
          detail.setCashId(cash.getCashId());
          detail.setSerialNo(serialNo);
          detailList.add(detail);
          
          //修改状态
          cash.setStatus(1);
        };
        
        //组装流水单数据
        WaterOrderCash woc=new WaterOrderCash();
        woc.setSerialNo(serialNo);
        woc.setUserId(userId);
        woc.setCreateDate(new Date());
        woc.setCashMoney(totalPrice);
        woc.setIsPunish(0);
        woc.setPayStatus(0);

        //查询是否已经处理扣罚
        Map<String, Object> spec=new HashMap<>();
        spec.put("EQ_createDate", DateUtil.date2String(woc.getCreateDate()));
        spec.put("EQ_isPunish", 1);
        spec.put("EQ_userId", userId);
        List<WaterOrderCash> orderCashs=wos.findAll(spec);
        if(orderCashs==null||orderCashs.size()==0){
          //检查是否有扣罚
          List<MonthPunish> mpl=mps.findByUserIdAndCreateDate(userId, DateUtil.date2String(DateUtil.moveDate(woc.getCreateDate(),-1)));
          if(mpl.size()>0){
            woc.setIsPunish(1);
            //修改扣罚状态确认有对应流水单号。
            mpl.forEach(mp -> {
              mp.setStatus(1);
            });
            mps.save(mpl);
          }
          
        }
        
        
        //保存流水单
        woc = wocr.save(woc);
        //保存流水单详情列表
        wodr.save(detailList);
        //修改现金列表状态
        cr.save(cashlist);
        msg=true;
      }
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    
    return msg;
  }
  /**
   * 流水单号生成策略：时间戳+4位随机码
   * @return serialNo
   */
  public String createSerialNo(){
    String serialNo="";
    Date now=new Date();
    serialNo+=now.getTime();
    int randow=(int)Math.random()*10000+1;
    serialNo+=randow;
    logger.info("流水单号:serialNo-->"+serialNo);
    return serialNo;
  }
  
  
  /**
   * 已结算流水单号分页记录
   * @param userId
   * @return
   */
  public Page<WaterOrderCash> findByUserId(String userId,Pageable pageable){
    
    return wocr.findByUserId(userId, pageable);
  }
  /**
   * 保存流水单号
   * @param waterOrder
   */
  public void saveWaterOrderCash(WaterOrderCash woc) {
    wocr.save(woc);
  }
  public void saveWaterOrderDetail(WaterOrderDetail wod){
    wodr.save(wod);
  }
//  public List<Cash> findByOrderIdIn()
}
