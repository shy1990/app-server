package com.wangge.app.server.service;

import com.wangge.app.server.entity.MonthPunish;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.WaterOrderCash;
import com.wangge.app.server.entity.WaterOrderDetail;
import com.wangge.app.server.pojo.OrderDetailPart;
import com.wangge.app.server.pojo.WaterOrderPart;
import com.wangge.app.server.repository.CashRepository;
import com.wangge.app.server.repository.OrderItemRepository;
import com.wangge.app.server.repository.WaterOrderCashRepository;
import com.wangge.app.server.repository.WaterOrderDetailRepository;
import com.wangge.app.server.util.DateUtil;
import com.wangge.app.util.SearchFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WaterOrderService {

	private Logger logger = Logger.getLogger(WaterOrderService.class);

	@Resource
	private MonthPunishService mps;
	@Resource
	private CashRepository cr;
	@Resource
	private OrderItemRepository oir;
	@Resource
	private WaterOrderCashRepository wocr;
	@Resource
	private WaterOrderDetailRepository wodr;

	/**
	 * 已结算流水单号分页记录
	 *
	 * @param userId
	 * @return
	 */
	public Page<WaterOrderPart> findByUserId(String userId, Integer status, Pageable pageable) {
		Page<WaterOrderPart> orderPartPage = null;
		List<WaterOrderPart> orderList = new ArrayList<>();
		try {
			Page<WaterOrderCash> orderPage = null;
			orderPage = wocr.findByUserIdAndPayStatus(userId, status, pageable);

			orderPage.getContent().forEach(order -> {
				//处理数据
				WaterOrderPart part = disposeWop(order, false);
				orderList.add(part);
			});


			orderPartPage = new PageImpl<WaterOrderPart>(orderList, pageable, orderPage.getTotalElements());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return orderPartPage;
	}

	/**
	 * 查询流水单号详情
	 *
	 * @param serailNo
	 * @return
	 */
	public WaterOrderPart findBySerailNo(String serailNo) {
		WaterOrderCash order = wocr.findOne(serailNo);
		return disposeWop(order, true);
	}

	/**
	 * 将waterOrderCash数据包装成需要数据
	 *
	 * @param order
	 * @param isDetail 是否是查询顶订单详情
	 * @return
	 */
	public WaterOrderPart disposeWop(WaterOrderCash order, boolean isDetail) {
		WaterOrderPart part = null;
		if (order != null) {
			part = new WaterOrderPart();
			part.setSeriaNo(order.getSerialNo());
			part.setCash(order.getCashMoney());
			part.setStatus(order.getPayStatus());
			part.setPaid(order.getPaymentMoney());
			part.setTime(DateUtil.date2String(order.getCreateDate(), "yyyy-MM-dd HH:mm"));

			//判断昨日是否有扣罚
			if (order.getIsPunish() == 1) {
				//TODO去查询扣罚记录
				List<MonthPunish> mpl = mps.findByUserIdAndCreateDate(order.getUserId(), DateUtil.date2String(DateUtil.moveDate(order.getCreateDate(), -1)));
				if (mpl.size() > 0) {
					Float debt = 0.0f;
					Float amerce = 0.0f;
					for (MonthPunish monthPunish : mpl) {
						debt += monthPunish.getDebt();
						amerce += monthPunish.getAmerce();
					}
					part.setDebt(debt + amerce);//拖欠
					part.setUnpaid(debt);//未付
					part.setAmerce(amerce);//扣罚
				}


			}
			if (isDetail) {
				//组装数据详情列表
				List<OrderDetailPart> orderItem = new ArrayList<>();
				//获取流水单号详情列表
				List<WaterOrderDetail> wodL = order.getOrderDetailList();

				wodL.forEach(wod -> {
					OrderDetailPart odp = new OrderDetailPart();
					OrderSignfor osf = wod.getCash().getOrder();
					odp.setNum(osf.getOrderNo());
					odp.setAmount(osf.getActualPayNum());//实际金额
//          odp.setAmount(osf.getOrderPrice());

					orderItem.add(odp);
				});
				part.setOrder(orderItem);

			}

		}
		return part;
	}

	/**
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<WaterOrderPart> findAll(Map<String, Object> searchParams, Pageable pageable) {
		Page<WaterOrderPart> orderPartPage = null;
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<WaterOrderCash> spec = oilCostSearchFilter(filters.values(), WaterOrderCash.class);
		try {

			Page<WaterOrderCash> orderPage = wocr.findAll(spec, pageable);
			List<WaterOrderPart> orderList = new ArrayList<>();
			orderPartPage = new PageImpl<WaterOrderPart>(orderList, pageable, orderPage.getTotalElements());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return orderPartPage;
	}

	/**
	 * 查询用户流水单号在某天的条数
	 *
	 * @param userId
	 * @param createDate
	 * @return
	 */
	public Long countByUserIdAndCreateDate(String userId, Date createDate) {
		String startDate = DateUtil.date2String(createDate) + " 00:00:00";
		String endDate = DateUtil.date2String(createDate) + " 23:59:59";
		return wocr.countByUserIdAndCreateDate(userId, startDate, endDate);
	}

	/**
	 * @param searchParams
	 * @return
	 */
	public List<WaterOrderCash> findAll(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<WaterOrderCash> spec = oilCostSearchFilter(filters.values(), WaterOrderCash.class);
		return wocr.findAll(spec);
	}

	/**
	 * 保存数据
	 */
	public WaterOrderCash save(WaterOrderCash waterOrderCash) {
		return wocr.save(waterOrderCash);
	}

	private static <T> Specification<T> oilCostSearchFilter(final Collection<SearchFilter> filters,
	                                                        final Class<T> entityClazz) {

		return new Specification<T>() {

			private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

			private final static String TIME_MIN = " 00:00:00 000";

			private final static String TIME_MAX = " 23:59:59 999";

			private final static String TYPE_DATE = "java.util.Date";

			@SuppressWarnings({"rawtypes", "unchecked"})
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (CollectionUtils.isNotEmpty(filters)) {
					List<Predicate> predicates = new ArrayList<Predicate>();
					for (SearchFilter filter : filters) {
						// nested path translate, 如Task的名为"user.name"的filedName,
						// 转换为Task.user.name属性
						String[] names = StringUtils.split(filter.fieldName, ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						String javaTypeName = expression.getJavaType().getName();

						// logic operator
						switch (filter.operator) {
							case EQ:
								// 日期相等,小于等于最大值,大于等于最小值
								if (javaTypeName.equals(TYPE_DATE)) {
									try {
										predicates.add(cb.greaterThanOrEqualTo(expression,
														new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
										predicates.add(cb.lessThanOrEqualTo(expression,
														new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
									} catch (ParseException e) {
										throw new RuntimeException("日期格式化失败!");
									}
								} else {
									predicates.add(cb.equal(expression, filter.value));
								}

								break;
							case LIKE:
								predicates.add(cb.like(expression, "%" + filter.value + "%"));

								break;
							case GT:
								if (javaTypeName.equals(TYPE_DATE)) {
									try {
										// 大于最大值
										predicates.add(cb.greaterThan(expression,
														new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
									} catch (ParseException e) {
										throw new RuntimeException("日期格式化失败!");
									}
								} else {
									predicates.add(cb.greaterThan(expression, (Comparable) filter.value));
								}

								break;
							case LT:
								if (javaTypeName.equals(TYPE_DATE)) {
									try {
										// 小于最小值
										predicates.add(cb.lessThan(expression,
														new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
									} catch (ParseException e) {
										throw new RuntimeException("日期格式化失败!");
									}
								} else {
									predicates.add(cb.lessThan(expression, (Comparable) filter.value));
								}

								break;
							case GTE:
								if (javaTypeName.equals(TYPE_DATE)) {
									try {
										// 大于等于最小值
										predicates.add(cb.greaterThanOrEqualTo(expression,
														new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
									} catch (ParseException e) {
										e.printStackTrace();
									}
								} else {
									predicates.add(cb.greaterThanOrEqualTo(expression, (Comparable) filter.value));
								}

								break;
							case LTE:
								if (javaTypeName.equals(TYPE_DATE)) {
									try {
										// 小于等于最大值
										predicates.add(cb.lessThanOrEqualTo(expression,
														new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
									} catch (ParseException e) {
										throw new RuntimeException("日期格式化失败!");
									}
								} else {
									predicates.add(cb.lessThanOrEqualTo(expression, (Comparable) filter.value));
								}

								break;
							case NOTEQ:
								predicates.add(cb.notEqual(expression, filter.value));

								break;
							case ISNULL:

								predicates.add(cb.isNull(expression));

								break;
							case NOTNULL:

								predicates.add(cb.isNotNull(expression));

								break;
							case ORE:
								/**
								 * sc_OR_userId = praentId_A37010506130
								 */
								String[] parameter = ((String) filter.value).split("_");
								Path expression_ = root.get(parameter[0]);
								String value_ = parameter[1];
								Predicate p = cb.or(cb.equal(expression_, value_), cb.equal(expression, value_));
								predicates.add(p);

								break;
							case ORLK:
								/**
								 * sc_ORLK_userId = praentId_A37010506130
								 */
								String[] parameter_LK = ((String) filter.value).split("_");
								Path expression_LK = root.get(parameter_LK[0]);
								String value_LK = parameter_LK[1];
								Predicate p_LK = cb.or(cb.like(expression_LK, value_LK), cb.like(expression, value_LK));
								predicates.add(p_LK);

								break;
							case ORMLK:
								/**
								 * sc_ORMLK_userId = 370105,3701050,3701051
								 */
								String[] parameterValue = ((String) filter.value).split(",");
								Predicate[] pl = new Predicate[parameterValue.length];

								for (int n = 0; n < parameterValue.length; n++) {
									pl[n] = (cb.like(expression, "%" + parameterValue[n] + "%"));
								}

								Predicate p_ = cb.or(pl);
								predicates.add(p_);

								break;
							default:
								break;

						}
					}

					// 将所有条件用 and 联合起来
					if (!predicates.isEmpty()) {
						return cb.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return cb.conjunction();
			}
		};
	}


}
