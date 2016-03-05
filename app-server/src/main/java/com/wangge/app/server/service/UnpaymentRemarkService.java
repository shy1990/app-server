package com.wangge.app.server.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.UnpaymentRemark;
import com.wangge.app.server.repository.UnpaymentRemarkRepository;

@Service
public class UnpaymentRemarkService {
  @Resource
  private UnpaymentRemarkRepository upr;
  /**
   * 
  * @Title: saveUnpaymentRemark 
  * @Description: TODO(添加报备) 
  * @param @param unpaymentRemark    设定文件 
  * @return void    返回类型 
  * @throws
   */
  public void saveUnpaymentRemark(UnpaymentRemark unpaymentRemark){
    upr.save(unpaymentRemark);
  }
  /**
   * @param desc 
   * @return 
   * 
  * @Title: findAllList 
  * @Description: TODO(获取报备列表) 
  * @param @return    设定文件 
  * @return List<UnpaymentRemark>    返回类型 
  * @throws
   */
  public Page<UnpaymentRemark> findListBySalesmanId(String salesmanId,Pageable pageable) {
    return upr.findBySalesmanIdOrderByIdDesc(salesmanId,pageable);
  }

}
