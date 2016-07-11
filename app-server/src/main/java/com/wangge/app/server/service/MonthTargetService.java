package com.wangge.app.server.service;

import com.wangge.app.server.entity.MonthTarget;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.repository.MonthTargetRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MonthTargetService {
	@Resource
    private MonthTargetRepository monthTargetRepository;

    public MonthTarget findBySalesmanAndTargetCycleAndPublishStatus(Salesman salesman,String nowDate,int status){
      return monthTargetRepository.findBySalesmanAndTargetCycleAndPublishStatus(salesman,nowDate,status);
    }
}
