package com.wangge.app.server.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.Cash;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.event.afterSalesmanSignforEvent;
import com.wangge.app.server.event.afterSignforEvent;
import com.wangge.app.server.repository.CashRepository;
import com.wangge.app.server.repository.OrderSignforRepository;
import com.wangge.app.server.repositoryimpl.OrderImpl;
@Service
public class CashService {
  @Resource
  private CashRepository cashRepository;

  public void saveCash(Cash cash) {
    cashRepository.save(cash);
  }

}
