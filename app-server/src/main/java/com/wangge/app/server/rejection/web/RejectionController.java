package com.wangge.app.server.rejection.web;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.rejection.entity.Rejection;
import com.wangge.app.server.rejection.service.RejectionServive;
import com.wangge.app.server.util.JsonResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    /**
     * 客户拒收提交保存
     *
     * @param jsonObject
     * @return
     */
    @ApiOperation(value = "拒收保存接口", notes = "保存一条提交的拒收信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "shopName", value = "店铺名字", required = true, dataType = "String"),
            @ApiImplicitParam(name = "orderno", value = "订单号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "salesmanId", value = "业务Id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "frontImgUrl", value = "正面照片url", required = true, dataType = "String"),
            @ApiImplicitParam(name = "trackingno", value = "物流单号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "拒收原因", required = false, dataType = "String"),
            @ApiImplicitParam(name = "createTime", value = "拒收时间", required = false, dataType = "Date")})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JsonResponse<String>> save(@RequestBody JSONObject jsonObject) {
        Rejection rejection = jsonObject.getObject("rejection",Rejection.class);
        ResponseEntity<JsonResponse<String>> jsonResponse = rejectionServive.save(rejection,jsonObject);
        return jsonResponse;
    }

    /**
     * 追加物流单号
     *
     * @param orderNo
     * @return
     */
    @ApiOperation(value = "追加物流单号", notes = "根据订单号添加物流单号")
    @ApiImplicitParams({@ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "String")})
    @RequestMapping(value = "/update/{orderNo}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JsonResponse<String>> update(@PathVariable("orderNo") String orderNo, @RequestBody JSONObject jsons) {
        String trackingNo = jsons.getString("trackingno");
        JsonResponse<String> json = new JsonResponse<>();
        try {
            Rejection rejection = rejectionServive.findByOrderno(orderNo);
            if (ObjectUtils.notEqual(rejection,null)) {
                rejection.setTrackingno(trackingNo);
                rejectionServive.save(rejection);
                json.setSuccessMsg("追加成功!");
            }
            return new ResponseEntity<JsonResponse<String>>(json, HttpStatus.OK);
        } catch (Exception e) {
            json.setErrorMsg("系统异常,请稍候重试!");
            json.setStatus(JsonResponse.Status.ERROR);
            return new ResponseEntity<>(json, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取拒收详情
     *
     * @param rejection
     * @return
     */
    /*@ApiOperation(value = "获取拒收详情", notes = "根据拒收ID查询拒收详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "rejectId", value = "拒收Id", required = true, dataType = "Long")})
    @RequestMapping(value = "/{rejectId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JsonResponse<Rejection>> getRejection(@PathVariable("rejectId") Rejection rejection) {
        JsonResponse<Rejection> json = new JsonResponse<>();
        try {
            json.setResult(rejection);
            return new ResponseEntity<JsonResponse<Rejection>>(json, HttpStatus.OK);
        } catch (Exception e) {
            json.setErrorMsg("系统异常,请稍候重试!");
            json.setStatus(JsonResponse.Status.ERROR);
            return new ResponseEntity<>(json, HttpStatus.UNAUTHORIZED);
        }
    }*/

    /**
     * 获取拒收列表
     *
     * @param salesmanId
     * @return
     */
    @ApiOperation(value = "获取拒收列表", notes = "根据业务ID查询拒收列表并按时间倒序")
    @ApiImplicitParams({@ApiImplicitParam(name = "salesmanId", value = "业务Id", required = true, dataType = "String")})
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JsonResponse<Page<Rejection>>> findAll(@RequestParam(value = "salesmanId", required = true) String salesmanId,
                                                                 @PageableDefault(page = 0, size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageRequest) {
        JsonResponse<Page<Rejection>> json = new JsonResponse<>();
        Page<Rejection> page = rejectionServive.findAllBySalesmanId(salesmanId, pageRequest);
        if (ObjectUtils.notEqual(page,null) && page.getSize() > 0) {
            json.setResult(page);
            json.setSuccessMsg("操作成功!");
        } else {
            json.setSuccessMsg("未查询到相关记录!");
        }
        return new ResponseEntity<JsonResponse<Page<Rejection>>>(json, HttpStatus.OK);
    }
}