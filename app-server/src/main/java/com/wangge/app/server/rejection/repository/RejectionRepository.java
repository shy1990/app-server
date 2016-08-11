package com.wangge.app.server.rejection.repository;

import com.wangge.app.server.rejection.entity.Rejection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectionRepository extends JpaRepository<Rejection, Long> {

    Rejection findByOrderno(String orderno);

    Page<Rejection> findAllBySalesmanId(String salesmanId, Pageable pageRequest);
}
