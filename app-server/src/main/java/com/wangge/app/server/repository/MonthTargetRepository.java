package com.wangge.app.server.repository;

import com.wangge.app.server.entity.MonthTarget;
import com.wangge.app.server.entity.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthTargetRepository extends JpaRepository<MonthTarget, Long> {

  MonthTarget findBySalesmanAndTargetCycleAndPublishStatus(Salesman salesman, String nowDate, int status);
}
