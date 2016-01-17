package com.wangge.app.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wangge.app.server.entity.ApplyPrice;
import com.wangge.app.server.entity.Salesman;

public interface ApplyPriceRepository extends PagingAndSortingRepository<ApplyPrice, Long>, JpaSpecificationExecutor<ApplyPrice>{
	Page<ApplyPrice> findBySalesman(Salesman sm,Pageable pageRequest);
}
