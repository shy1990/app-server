package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wangge.app.server.entity.Assess;

public interface AssessRepository extends PagingAndSortingRepository<Assess, Long>, JpaSpecificationExecutor<Assess>{

}
