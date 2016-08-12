package com.wangge.app.server.rejection.web;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.rejection.entity.Rejection;
import com.wangge.app.server.rejection.service.RejectionServive;
import com.wangge.app.server.util.JsonResponse;
import com.wangge.app.server.util.LogUtil;
import com.wangge.app.server.util.RestTemplateUtil;
import com.wangge.app.server.util.StringUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述: TODO:拒收接口
 * 包名: com.wangge.app.rejection.web.
 * 作者: pt.
 * 日期: 16-7-26.
 * 项目名称: app-interface
 * 版本: 1.0
 * JDK: since 1.8
 */
@RestController
@RequestMapping("/v1/rejection")
@Profile("dev")
public class RejectionController {
    @Resource
    private RejectionServive rejectionServive;
    @Resource
    private HttpRequestHandler httpRequestHandler;
    @Value("${app-interface.url}")
    private String url;
    @Autowired
    RestTemplate restTemplate;
    /**
     * 客户拒收提交保存
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Object> save(@RequestBody JSONObject jsonObject) {
        return httpRequestHandler.exchange(url + "/rejection/save", HttpMethod.POST, null, jsonObject);
    }

    /**
     * 追加物流单号
     *
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "/update/{orderNo}", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@PathVariable("orderNo") String orderNo, @RequestBody JSONObject jsons) {
        return httpRequestHandler.exchange(url + "/rejection/update/{orderNo}", HttpMethod.POST, null, jsons, orderNo);
    }

    /**
     * 获取拒收列表
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        Map<String, Object> params = WebUtils.getParametersStartingWith(request, "");
//        return RestTemplateUtil.sendRestForObject(restTemplate,"GET",url + "/rejection" ,searchParams);
        return httpRequestHandler.exchange(url + "/rejection" + "?" + StringUtil.joinMap(params, "&"), HttpMethod.GET, null, null);
    }
}
