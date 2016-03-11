package com.wangge.app.server.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wangge.app.server.entity.UnpaymentRemark;

public interface UnpaymentRemarkRepository extends PagingAndSortingRepository<UnpaymentRemark, Long>, JpaSpecificationExecutor<UnpaymentRemark> {
  
 


  @Query("select ur from UnpaymentRemark ur where ur.salesmanId=?1")
  Page<UnpaymentRemark> findBySalesmanIdOrderByIdDesc(String salesmanId, Pageable pageRequest);
  



}
