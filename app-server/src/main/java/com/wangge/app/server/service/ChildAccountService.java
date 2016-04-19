package com.wangge.app.server.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.ChildAccount;
import com.wangge.app.server.repository.ChildAccountRepostory;
/**
 * 
* @ClassName: ChildAccountService
* @Description: TODO(子账号service类)
* @author SongBaoZhen
* @date 2016年3月25日 下午5:28:24
*
 */
@Service
public class ChildAccountService {
  @Resource
  private ChildAccountRepostory childAccountRepostory;

  public ChildAccount getChildAccountBySimId(String simId) {
    
    return  childAccountRepostory.findBySimId(simId);
  }

  public List<ChildAccount> getChildAccountByParentId(String id) {
    // TODO Auto-generated method stub
    return childAccountRepostory.findByParentId(id);
  }

  public void save(ChildAccount chil) {
    childAccountRepostory.save(chil);
    
  }

 

}
