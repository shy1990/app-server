package com.wangge.app.server.service;


import com.wangge.app.constant.OrderShipStatusConstant;
import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.entity.*;
import com.wangge.app.server.pojo.CashPart;
import com.wangge.app.server.pojo.CashShopGroup;
import com.wangge.app.server.pojo.OrderDetailPart;
import com.wangge.app.server.repository.*;
import com.wangge.app.server.repositoryimpl.OrderImpl;
import com.wangge.app.server.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.crsh.console.jline.internal.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CashService {

	private Logger logger = Logger.getLogger(CashService.class);

	@Resource
	private MonthPunishService mps;

	@Resource
	private WaterOrderService wos;
	@Resource
	private OrderSignforService orderSignforService;
	@Resource
	private OrderImpl opl;
	@Resource
	private CashRepository cr;
	@Resource
	private OrderItemRepository oir;
	@Resource
	private WaterOrderCashRepository wocr;
	@Resource
	private OrderSignforRepository orderSignforRepository;
	@Resource
	private WaterOrderDetailRepository wodr;
	@Resource
	private PointService pointService;
	@Resource
	private HttpRequestHandler httpRequestHandler;
	@Value("${mall.url}")
	private String MALL_URl;

	/**
	 * 现金订单购物车
	 *
	 * @param userId
	 * @return
	 */
	public List<CashPart> findByUserId(String userId) {
		logger.info("findByUserId-->userId:" + userId + "查询收现金购物车");
		List<Cash> cashList = null;
		List<CashPart> cashPartList = new ArrayList<>();
		try {
			//优化查询(使用负载图)
			cashList = cr.findByUserIdAndStatus(userId, 0);
			cashList.forEach(cash -> {
//				String orderNo = cash.getOrder().getOrderNo();
//				简化查询，不去查详情（耽误时间）
//				cash.setOrderItem(oir.findByOrderNum(orderNo));
				CashPart part = new CashPart();
				OrderSignfor order = cash.getOrder();
				part.setId(cash.getCashId());
				part.setNum(order.getOrderNo());
				part.setCash(order.getActualPayNum());
//				part.setDetails(disposeOrderItem(cash.getOrderItem()));
				cashPartList.add(part);
			});
		} catch (Exception e) {
			logger.info(e.getMessage());
		}


		return cashPartList;
	}

	/**
	 * 现金订单购物车
	 *
	 * @param userId
	 * @return
	 */
	public Map<String,Object> findByUserId_(String userId) {
		logger.info("findByUserId-->userId:" + userId + "查询收现金购物车");
		List<Cash> cashList = null;
		Map<String, Object> stringListMap = new HashedMap();
		List<CashShopGroup> cashShopGroups = new ArrayList<>();
		Map<String, List<CashPart>> map = new HashedMap();
		try {
			//优化查询(使用负载图)
			cashList = cr.findByUserIdAndStatus(userId, 0);
			cashList.forEach(cash -> {
				//提取ShopName分组
				String shopName = cash.getOrder().getShopName();
				CashPart part = new CashPart();
				OrderSignfor order = cash.getOrder();
				part.setId(cash.getCashId());
				part.setNum(order.getOrderNo());
				part.setCash(order.getActualPayNum());

				if (map.get(shopName) != null) {
					//去除map中的集合加入数据
					List<CashPart> cashParts = map.get(shopName);
					cashParts.add(part);
					//重新放入map（可有可无）
					map.put(shopName, cashParts);

				} else {
					List<CashPart> cashParts = new ArrayList<CashPart>();
					cashParts.add(part);
					map.put(shopName, cashParts);
				}

			});
			//组装分组数据
			for (Map.Entry<String, List<CashPart>> entry : map.entrySet()) {
				CashShopGroup cashShopGroup = new CashShopGroup();
				cashShopGroup.setShopName(entry.getKey());
				cashShopGroup.setCashParts(entry.getValue());
				BigDecimal totalMoney = new BigDecimal(0);
				//计算总金额
				for (CashPart cashPart : entry.getValue()) {
					totalMoney = new BigDecimal(cashPart.getCash().toString()).add(totalMoney);
				}
				cashShopGroup.setTotalMoney(new Float(totalMoney.floatValue()));

				cashShopGroups.add(cashShopGroup);
			}
			System.out.println(map);
		} catch (Exception e) {
			logger.info(e.getMessage());
			stringListMap.put("size", 0);
			stringListMap.put("list", cashShopGroups);
			return stringListMap;
		}
		stringListMap.put("size", cashList.size());
		stringListMap.put("list", cashShopGroups);

		return stringListMap;
	}

	/**
	 * 处理收现金数据
	 *
	 * @param itemList
	 */
	public List<OrderDetailPart> disposeOrderItem(List<OrderItem> itemList) {
		List<OrderDetailPart> detailList = new ArrayList<>();
		itemList.forEach(item -> {
			OrderDetailPart detailPart = new OrderDetailPart();
			String type = item.getType();
			switch (type) {
				case "sku":
					detailPart.setPhoneName(item.getName());
					detailPart.setPhoneNum(item.getNums());
					break;

				default:
					detailPart.setPartsName(item.getName());
					detailPart.setPartsNum(item.getNums());

					break;
			}
			detailList.add(detailPart);

		});
		return detailList;
	}

	/**
	 * 购物车结算
	 * 将所有未结算订单全部结算
	 * <p>
	 * 流程：1.根据userID和cashIds查询所要处理的现金订单cash
	 * 2.生成流水单号-->使用：流水订单+流水订单详情
	 * 3.组装流水单详情数据
	 * 4.组装流水单数据
	 * 5.保存流水单
	 * 6.保存流水单详情列表
	 * 7.修改现金订单列表中状态status改为1（已结算）
	 * 8.返回状态
	 * <p>
	 * 流水单号
	 *
	 * @param userId
	 * @param
	 * @return
	 */
	@Transactional(readOnly = false)
	public String cashToWaterOrder(String userId, String cashIds) {
		String[] cashIdArr = cashIds.split(",");
		Integer[] idsIntegers = new Integer[cashIdArr.length];
		for (int i = 0; i < idsIntegers.length; i++) {
			idsIntegers[i] = Integer.valueOf(cashIdArr[i]);
		}
		String msg = "";
		logger.info("cashToWaterOrder----->userId:" + userId);
		try {

			//查询代数现金列表现金列表
//			List<Cash> cashlist = cr.findByUserIdAndStatus(userId, 0);
			List<Cash> cashlist = cr.findByUserIdAndStatusAndCashIdIn(userId, 0, idsIntegers);

			//流水单号详情
			List<WaterOrderDetail> detailList = new ArrayList<>();

			//查询用户当日流水单个数：若超过2个流水单号已LD，若不超过则是DL(代理商)
			Long count = wos.countByUserIdAndCreateDate(userId, new Date());

			//生成流水单号
			String serialNo = createSerialNo();
			if (count > 1) {
				serialNo = "LD" + serialNo;
			} else {
				serialNo = "DL" + serialNo;
			}

			Float totalPrice = 0.0f;
			if (cashlist.size() > 0) {
				for (Cash cash : cashlist) {
					//计算流水单号收现金金额
					OrderSignfor order = cash.getOrder();
					totalPrice += order.getActualPayNum();//获取订单实际金额

					//组装流水单号详情数据
					WaterOrderDetail detail = new WaterOrderDetail();
					detail.setCashId(cash.getCashId());
					detail.setSerialNo(serialNo);
					detailList.add(detail);

					//修改状态
					cash.setStatus(1);
				}

				//组装流水单数据
				WaterOrderCash woc = new WaterOrderCash();
				woc.setSerialNo(serialNo);
				woc.setUserId(userId);
				woc.setCreateDate(new Date());
				woc.setCashMoney(totalPrice);
				woc.setIsPunish(0);
				woc.setPayStatus(0);

				//查询是否已经处理扣罚
				Map<String, Object> spec = new HashMap<>();
				spec.put("EQ_createDate", DateUtil.date2String(woc.getCreateDate()));
				spec.put("EQ_isPunish", 1);
				spec.put("EQ_userId", userId);
				List<WaterOrderCash> orderCashs = wos.findAll(spec);
				if (orderCashs == null || orderCashs.size() == 0) {
					//检查是否有扣罚
					List<MonthPunish> mpl = mps.findByUserIdAndCreateDate(userId, DateUtil.date2String(DateUtil.moveDate(woc.getCreateDate(), -1)));
					if (mpl.size() > 0) {
						woc.setIsPunish(1);
						//修改扣罚状态确认有对应流水单号。
						mpl.forEach(mp -> {
							mp.setStatus(1);
						});
						mps.save(mpl);
					}

				}
				msg = woc.getSerialNo();
				synchronized (this) {
					//============在保存之前再次查询详情是否存在收现金订单=========
					Long cashNum = wodr.countByCashIdIn(idsIntegers);
					//若不存在则保存
					if (cashNum < 1) {
						//保存流水单
						woc = wocr.save(woc);
						//保存流水单详情列表
						wodr.save(detailList);
						//修改现金列表状态
						cr.save(cashlist);
						//=============推送流水单号到老商城订单 =============
						pushWaterOrderToMall(woc);
					} else {
						return "订单已存在";
					}

				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return msg;
		}

		return msg;
	}

	//推送流水单号到老商城订单
	public void pushWaterOrderToMall(WaterOrderCash woc) {
		try {
			String url = "order/addNewOrder.html?orderNum=" + woc.getSerialNo() + "&totalCost=" + woc.getCashMoney();
			httpRequestHandler.get(MALL_URl + url, HttpMethod.POST);
		} catch (Exception e) {
			logger.info("流水单推送老商城失败!", e);
		}
	}

	/**
	 * 流水单号生成策略：时间戳+4位随机码
	 *
	 * @return serialNo
	 */
	public synchronized String createSerialNo() {
		String serialNo = "";
		Date now = new Date();
		serialNo += now.getTime();
//    int randow=(int)Math.random()*10000+1;
//    serialNo+=randow;
		logger.info("流水单号:serialNo-->" + serialNo);
		return serialNo;
	}


	/**
	 * 已结算流水单号分页记录
	 *
	 * @param userId
	 * @return
	 */
	public Page<WaterOrderCash> findByUserId(String userId, Pageable pageable) {

		return wocr.findByUserId(userId, pageable);
	}

	/**
	 * 保存流水单号
	 *
	 * @param
	 */
	public void saveWaterOrderCash(WaterOrderCash woc) {
		wocr.save(woc);
	}

	public void saveWaterOrderDetail(WaterOrderDetail wod) {
		wodr.save(wod);
	}

	@Transactional
	public Cash save(Cash cash) {
		return cr.save(cash);
	}

	@Transactional
	public List<Cash> save(List<Cash> cashList) {
		return cr.save(cashList);
	}

	//取消收现金订单
	public boolean cancelCashOrder(Cash cash){
		boolean msg = false;
		try {
			OrderSignfor order = cash.getOrder();
			String orderNo = order.getOrderNo();
			String storePhone = order.getMemberPhone();

			//删除数据
			delete(cash);
			//修改原有订单签收状态
			orderSignforRepository.updateOrderForCashCancel(cash.getCashId().toString());
			//更改老商城订单状态(业务揽收)
			opl.updateOrderShipStateByOrderNum(orderNo, OrderShipStatusConstant.SHOP_ORDER_SHIPSTATUS_YWSIGNEDFOR,null,null);
			//冲减积分
			pointService.addPoint((int) -(pointService.findTotalCostByOrderNum(orderNo) / 10), storePhone);
			msg = true;
		}catch (Exception e){
			logger.info("取消收现金订单失败cashId：");
			return msg;
		}

		return msg;
	}

	@Transactional
	public void delete(Cash cash) {
		cr.delete(cash);
	}
}
