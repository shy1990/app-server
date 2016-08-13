package com.wangge.app.server.customtask.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.app.server.customtask.entity.CustomMessages;
import com.wangge.app.server.customtask.entity.CustomTask;
import com.wangge.app.server.customtask.repository.CustomMessagesRepository;
import com.wangge.app.server.customtask.repository.CustomTaskRepository;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.util.DateUtil;

@Service
public class CustomTaskServiceImpl implements CustomTaskServer {
  public static final String[] TASKTYPEARR = new String[] { "注册", "售后", "扣罚", "拜访", "小米" };
  public static final String[] TASKTYPEDETAILARR = new String[] { "店铺注册", "售后处理", "扣罚通知", "客户拜访", "小米分销" };
  @Autowired
  CustomTaskRepository customRep;
  @Autowired
  SalesmanRepository salesmanRep;
  @Autowired
  CustomMessagesRepository messageRep;
  
  @Override
  public Map<String, Object> findCustomTask(CustomTask customTask, String salesmanId) {
    List<CustomMessages> messageList = messageRep.findByCustomtaskIdAndSalesmanIdOrderByTimeAsc(customTask.getId(),
        salesmanId);
    for (CustomMessages m : messageList) {
      m.setCtime();
    }
    // Salesman salesman=salesmanRep.findOne(salesmanId);
    Map<String, Object> dataMap = new HashMap<String, Object>();
    // dataMap.put("salesman", salesman.getTruename());
    dataMap.put("customTask", customTask);
    dataMap.put("messageList", messageList);
    return dataMap;
  }
  
  @Override
  public List<CustomMessages> findMessageList(CustomTask customTask, String salesmanId) {
    List<CustomMessages> messageList = messageRep.findByCustomtaskIdAndSalesmanIdOrderByTimeAsc(customTask.getId(),
        salesmanId);
    for (CustomMessages m : messageList) {
      m.setCtime();
    }
    return messageList;
  }
  
  @Override
  public Map<String, Object> getList(String salesmanId, Pageable page) {
    Page<CustomTask> cpage = customRep.findBysalesmanSetIdOrderByCreateTimeDesc(salesmanId, page);
    List<CustomTask> cList = cpage.getContent();
    List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    for (CustomTask c : cList) {
      Map<String, Object> cMap = new HashMap<String, Object>();
      cMap.put("type", TASKTYPEARR[c.getType()]);
      cMap.put("title", c.getTitle());
      cMap.put("time", DateUtil.date2String(c.getCreateTime(), "MM-dd HH:mm"));
      cMap.put("id", c.getId());
      dataList.add(cMap);
    }
    Map<String, Object> dataMap = new HashMap<String, Object>();
    dataMap.put("totalPages", cpage.getTotalPages());
    dataMap.put("obj", dataList);
    return dataMap;
  }
  
  @Override
  @Transactional(rollbackForClassName = "Exception")
  public void saveMessage(CustomMessages message) {
    messageRep.save(message);
  }
  

  
  @Override
  @Transactional(rollbackForClassName = "Exception")
  public void updateStatus(CustomTask customTask, String salesmanId) {
    customTask.setStatus(1);
    customRep.save(customTask);
    messageRep.updateBySalesmainId(customTask.getId(), salesmanId);
  }
}