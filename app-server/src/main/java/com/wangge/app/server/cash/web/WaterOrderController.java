package com.wangge.app.server.cash.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.cash.entity.Cash;
import com.wangge.app.server.cash.entity.MonthPunish;
import com.wangge.app.server.cash.entity.WaterOrderCash;
import com.wangge.app.server.cash.entity.WaterOrderDetail;
import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.pojo.OrderDetailPart;
import com.wangge.app.server.pojo.WaterOrderPart;
import com.wangge.app.server.service.WaterOrderService;
import com.wangge.app.server.util.DateUtil;
import com.wangge.app.server.util.JsonResponse;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/waterOrder")
public class WaterOrderController {
  private static final Logger logger = Logger.getLogger(WaterOrderController.class);

  @Resource
  private WaterOrderService waterOrderService;
  @Autowired
  private HttpRequestHandler hrh;
  @Value("${app-interface.url}")
  private String APP_INTERFACE_URL;

  /**
   * 结算后流水单号列表
   * 
   * @param request
   * @param userId
   * @return
   */
  @ApiOperation(value = "流水单号分页查询接口", notes = "流水单号列表,固定排序方式：payStatus(ASC),createDate(DESC)")
  @ApiImplicitParams(value = { @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String"),
      @ApiImplicitParam(name = "page", value = "0", required = false, dataType = "int"),
      @ApiImplicitParam(name = "size", value = "10", required = false, dataType = "int") })
  @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
  public ResponseEntity<JsonResponse<Page<WaterOrderPart>>> getWaterOrderList(@PathVariable("userId") String userId,
      @PageableDefault(page = 0, size = 10, sort = { "payStatus",
          "createDate" }, direction = Direction.DESC) Pageable pageable,
      HttpServletRequest request) {
    //
    JsonResponse<Page<WaterOrderPart>> waterOrderJson = new JsonResponse<>();
    try {
      List<WaterOrderCash> cashlist = hrh.get(APP_INTERFACE_URL + "waterOrder/{userId}?page="+pageable.getPageNumber()+
          "&size="+pageable.getPageSize(), new ParameterizedTypeReference<List<WaterOrderCash>>() {}, userId);

      List<WaterOrderPart> list = new ArrayList<>();
      cashlist.forEach(order -> {
        WaterOrderPart part = disposeWop(order, false);
        list.add(part);
      });
      Page<WaterOrderPart> partPage = new PageImpl<>(list, pageable, cashlist.get(0).getTotal());
      if (CollectionUtils.isNotEmpty(cashlist)) {
        waterOrderJson.setResult(partPage);
        waterOrderJson.setSuccessMsg("操作成功");
      } else {
        waterOrderJson.setResult(null);
        waterOrderJson.setSuccessMsg("未查到相关记录");
      }
    } catch (Exception e) {
      waterOrderJson.setResult(null);
      waterOrderJson.setErrorMsg("网络异常，稍后重试！");
      waterOrderJson.setErrorCode("exception");
      e.printStackTrace();
      logger.info(e.getMessage());
    }
    return new ResponseEntity<>(waterOrderJson, HttpStatus.OK);
  }

  /**
   * 流水单详情
   * 
   * @param request
   * @param userId
   * @return
   */
  @ApiOperation(value = "流水单号详情", notes = "根据流水号查询详情")
  @ApiImplicitParam(name = "seriaNo", value = "流水单号", required = true, dataType = "String")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<JsonResponse<WaterOrderPart>> getCsahList(
      @RequestParam(value = "seriaNo", required = true) String seriaNo, HttpServletRequest request) {
    JsonResponse<WaterOrderPart> waterOrderJson = new JsonResponse<>();
    try {

      WaterOrderPart wop = waterOrderService.findBySerailNo(seriaNo);
      if (wop != null) {
        waterOrderJson.setResult(wop);
        waterOrderJson.setSuccessMsg("操作成功");
      } else {
        waterOrderJson.setResult(null);
        waterOrderJson.setSuccessMsg("未查到相关记录");
      }
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return new ResponseEntity<>(waterOrderJson, HttpStatus.OK);
  }

  /**
   * 将waterOrderCash数据包装成需要数据
   * 
   * @param order
   * @param isDetail
   *          是否是查询顶订单详情
   * @return
   */
  public WaterOrderPart disposeWop(WaterOrderCash order, boolean isDetail) {
    WaterOrderPart part = null;
    if (!ObjectUtils.equals(null, order)) {
      part = new WaterOrderPart();
      part.setSeriaNo(order.getSerialNo());
      part.setCash(order.getCashMoney());
      part.setStatus(order.getPayStatus());
      part.setPaid(order.getPaymentMoney());
      part.setTime(DateUtil.date2String(order.getCreateDate(), "yyyy-MM-dd HH:mm"));

      // 判断昨日是否有扣罚
      if (order.getIsPunish() == 1) {
        MonthPunish monthPunish = order.getMonthPunish();
        Float debt = 0.0f;
        Float amerce = 0.0f;
        if (!ObjectUtils.equals(null, monthPunish)) {
          debt = monthPunish.getDebt();
          amerce = monthPunish.getAmerce();
        }
        part.setDebt(debt + amerce);// 拖欠
        part.setUnpaid(debt);// 未付
        part.setAmerce(amerce);// 扣罚

      }
      if (isDetail) {
        // 组装数据详情列表
        List<OrderDetailPart> orderItem = new ArrayList<>();
        // 获取流水单号详情列表
        List<WaterOrderDetail> wodL = order.getOrderDetailList();

        wodL.forEach(wod -> {
          OrderDetailPart odp = new OrderDetailPart();
          OrderSignfor osf = wod.getCash().getOrder();
          odp.setNum(osf.getOrderNo());
          odp.setAmount(osf.getOrderPrice());

          orderItem.add(odp);
        });
        part.setOrder(orderItem);

      }

    }
    return part;
  }
}
