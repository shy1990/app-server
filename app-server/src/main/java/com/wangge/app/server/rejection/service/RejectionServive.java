package com.wangge.app.server.rejection.service;

import com.wangge.app.server.rejection.entity.Rejection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RejectionServive {

    Rejection save(Rejection rejection);

    Rejection findByOrderno(String orderno);

    Page<Rejection> findAllBySalesmanId(String salesmanId, Pageable pageRequest);
}
