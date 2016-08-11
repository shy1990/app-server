package com.wangge.app.server.rejection.service;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.rejection.entity.Rejection;
import com.wangge.app.server.util.JsonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RejectionServive {

    Rejection save(Rejection rejection);

    ResponseEntity<JsonResponse<String>> save(Rejection rejection, JSONObject jsonObject);

    Rejection findByOrderno(String orderno);

    Page<Rejection> findAllBySalesmanId(String salesmanId, Pageable pageRequest);
}
