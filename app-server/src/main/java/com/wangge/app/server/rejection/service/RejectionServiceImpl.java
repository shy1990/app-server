package com.wangge.app.server.rejection.service;

import com.wangge.app.server.rejection.entity.Rejection;
import com.wangge.app.server.rejection.repository.RejectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RejectionServiceImpl implements RejectionServive {
    @Resource
    private RejectionRepository rejectionRepository;

    @Override
    public Rejection save(Rejection rejection) {
        return rejectionRepository.save(rejection);
    }

    @Override
    public Rejection findByOrderno(String orderno) {
        return rejectionRepository.findByOrderno(orderno);
    }

    @Override
    public Page<Rejection> findAllBySalesmanId(String salesmanId, Pageable pageRequest) {
        return rejectionRepository.findAllBySalesmanId(salesmanId, pageRequest);
    }
}
