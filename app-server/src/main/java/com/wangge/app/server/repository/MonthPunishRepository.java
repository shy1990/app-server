package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.app.server.entity.MonthPunish;

public interface MonthPunishRepository extends JpaRepository<MonthPunish, Integer>,
JpaSpecificationExecutor<MonthPunish> {
  

}
