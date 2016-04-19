package com.wangge.app.server.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.ApplyPrice;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.SalesmanAddress;
import com.wangge.app.server.entity.UnpaymentRemark;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class UnpaymentRemarkRepositoryTest{
@Resource
private UnpaymentRemarkRepository urr;
@Resource
private SalesmanRepository sr;
@Resource
private ApplyPriceRepository ar;
@Resource
private SalesmanAddressRepository sar;

@Test
public void testAdd(){
  UnpaymentRemark ur = new UnpaymentRemark();
  ur.setAboveImgUrl("http://image.3j1688.com/uploadfile/2016/01/29/11/b6e2ccdf-8116-4c94-9a4b-82d4514c7b2e.jpg");
  ur.setFrontImgUrl("http://image.3j1688.com/uploadfile/2016/01/29/11/b6e2ccdf-8116-4c94-9a4b-82d4514c7b2e.jpg");
  ur.setSideImgUrl("http://image.3j1688.com/uploadfile/2016/01/29/11/b6e2ccdf-8116-4c94-9a4b-82d4514c7b2e.jpg");
  ur.setShopName("测试");
  ur.setOrderno("123456789");
  ur.setCreateTime(new Date());
  ur.setStatus(0);
  /*Salesman s =new Salesman();
  s.setId("A37010511250");*/
  ur.setSalesmanId("A37010511250");
  urr.save(ur);
}

@Test
public void testQuery(){
  
 Page<UnpaymentRemark> list =  urr.findBySalesmanIdOrderByIdDesc("A37010511250", new PageRequest(1, 20));
// Page<UnpaymentRemark> list =  urr.findAll(new PageRequest(0, 20));
//  List<UnpaymentRemark> list = urr.findAll();
 for(UnpaymentRemark ur : list.getContent()){
   System.out.println("========店铺名========="+ur.getShopName());
   System.out.println("========业务员========="+ur.getOrderno());
 }
}
@Test
public void test(){
  List<UnpaymentRemark> uList = urr.findByCreateTime(new Date());
  for(UnpaymentRemark u : uList){
    System.out.println("================"+u.getShopName());
  }
} 
@Test
public void testAddress(){                    
  SalesmanAddress address = sar.findByUserId("A37010211250");
  System.out.println("==================="+address.getId());
};

}
