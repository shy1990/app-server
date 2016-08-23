package com.wangge.app.constant;
/**
 * 订单物态常量
 */
public class OrderShipStatusConstant {
  //--------------------app订单物流状态常量----------------------//
  /**
   * 业务揽收
   */
  public static final  Integer ORDER_SHIPSTATUS_YWSIGNEDFOR = 2;
  /**
   * 客户签收
   */
  public static final  Integer ORDER_SHIPSTATUS_KHSIGNEDFOR = 3;
  /**
   * 客户拒收
   */
  public static final  Integer ORDER_SHIPSTATUS_KHUNSIGNEDFOR = 4;
  
//--------------------商城订单支付状态常量----------------------//
  /**
   * 已支付
   */
  public static final  String SHOP_ORDER_PAYSTATUS_HAVETOPAY = "1";
  
//--------------------商城订单支付方式常量----------------------//
  /**
   * 现金支付
   */
  public static final  Integer SHOP_ORDER_PAYMENT_CASH = 2;
  
//--------------------商城订物流状态常量----------------------//
  
  /**
   * 业务揽收
   */
  public static final  String SHOP_ORDER_SHIPSTATUS_YWSIGNEDFOR = "2";
  /**
   * 客户签收
   */
  public static final  String SHOP_ORDER_SHIPSTATUS_KHSIGNEDFOR = "3";
  /**
   * 客户拒收
   */
  public static final  String SHOP_ORDER_SHIPSTATUS_KHUNSIGNEDFOR = "7";
  
  
}
