package com.wangge.app.server.monthTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wangge.app.server.monthTask.repository.MonthTaskSubRepository;

@Component
@EnableScheduling
public class MonthTaskSchedule {
	@Autowired
	MonthTaskSubRepository montSubrep;

	@Scheduled(cron = "0 0 20 ? * *")
	public void exsitUnpaymentRemarkStatus() {
		montSubrep.updatebyDelay();
	}
}
