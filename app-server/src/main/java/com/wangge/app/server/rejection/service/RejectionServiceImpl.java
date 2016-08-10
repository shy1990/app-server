package com.wangge.app.server.rejection.service;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.event.afterSignforEvent;
import com.wangge.app.server.rejection.entity.RejectStatusEnum;
import com.wangge.app.server.rejection.entity.Rejection;
import com.wangge.app.server.rejection.repository.RejectionRepository;
import com.wangge.app.server.repositoryimpl.OrderImpl;
import com.wangge.app.server.service.OrderService;
import com.wangge.app.server.service.OrderSignforService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.util.DateUtil;
import com.wangge.app.util.JsonResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class RejectionServiceImpl implements RejectionServive {
    @Resource
    private RejectionRepository rejectionRepository;
    @Resource
    private SalesmanService salesmanService;
    @Resource
    private OrderSignforService orderSignforService;
    @Resource
    private ApplicationContext ctx;
    @Resource
    private OrderImpl opl;
    @Resource
    private OrderService or;


    @Override
    public Rejection save(Rejection rejection) {
        return rejectionRepository.save(rejection);
    }

    @Override
    public ResponseEntity<JsonResponse<String>> save(Rejection rejection, JSONObject jsonObject) {
        JsonResponse<String> json = new JsonResponse<>();
        String orderno = rejection.getOrderno();
        try {
            Rejection rj = rejectionRepository.findByOrderno(orderno);
            if (!ObjectUtils.notEqual(rj, null)) {
                rejection.setArriveTime(DateUtil.moveDate(rejection.getCreateTime(), 2));
                Salesman salesman = salesmanService.findSalesmanbyId(rejection.getSalesmanId());
                rejection.setSalesMan(salesman);
                rejection.setStatus(RejectStatusEnum.sendBack);
                rejectionRepository.save(rejection);

                //更改订单拒收状态
                OrderSignfor orderSignfor = orderSignforService.findbyOrderNum(orderno);
                orderSignfor.setOrderStatus(4);//更改订单签收表的订单状态为已拒收
                orderSignforService.saveOrderSignfor(orderSignfor);

                //计算油补
                String rejectPoint = jsonObject.getString("signGeoPoint");//坐标点
                String childId = jsonObject.getString("childId");//子帐号
                String storePhone = jsonObject.getString("storePhone");//店铺手机号
                int isPrimary = Integer.parseInt(jsonObject.getString("isPrimary"));//是否主账号
                ctx.publishEvent(new afterSignforEvent(rejection.getSalesmanId(), rejectPoint, isPrimary, childId, 7, storePhone));

                //钱包调用处理
                Map<String, String> map = opl.checkMoneyBack(orderno);
                boolean flag = false;
                if (map != null) {
                    //判断钱包流水号是否为空,若是则不调用退款接口
                    if (StringUtils.isNotBlank(map.get("payNo"))) {
                        if ("0".equals(map.get("payMent"))) {
                            if (map.get("totalCost").equals(map.get("walletNum"))) {
                                flag = true;
                            }
                        } else {
                            flag = true;
                        }
                    }
                }
                if (flag) {
                    String str = or.invokWallet(map.get("payNo").toString());
                    if (str != null && str.contains("202")) {
                        json.setSuccessMsg("退款成功,请核实钱包金额!");
                    } else {
                        json.setErrorMsg("退款失败,请联系技术人员!");
                    }
                    return new ResponseEntity<JsonResponse<String>>(json, HttpStatus.OK);
                }

                json.setSuccessMsg("提交成功!");
                return new ResponseEntity<JsonResponse<String>>(json, HttpStatus.OK);
            } else {
                json.setSuccessMsg("该订单已拒收!");
                return new ResponseEntity<JsonResponse<String>>(json, HttpStatus.OK);
            }
        } catch (Exception e) {
            json.setErrorMsg("系统异常,请稍候重试!");
            json.setStatus(JsonResponse.Status.ERROR);
            return new ResponseEntity<>(json, HttpStatus.UNAUTHORIZED);
        }
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
