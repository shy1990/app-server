package com.wangge.app.server.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.MonthPunish;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.WaterOrderCash;
import com.wangge.app.server.entity.WaterOrderDetail;
import com.wangge.app.server.pojo.OrderDetailPart;
import com.wangge.app.server.pojo.WaterOrderPart;
import com.wangge.app.server.repository.CashRepository;
import com.wangge.app.server.repository.MonthPunishRepository;
import com.wangge.app.server.repository.OrderItemRepository;
import com.wangge.app.server.repository.WaterOrderCashRepository;
import com.wangge.app.server.repository.WaterOrderDetailRepository;
import com.wangge.app.server.util.DateUtil;
import com.wangge.app.util.SearchFilter;

@Service
public class MonthPunishService {

  private Logger logger = Logger.getLogger(MonthPunishService.class);

  @Resource
  private MonthPunishRepository mpr;

  public void save(List<MonthPunish> monthPunishs){
    mpr.save(monthPunishs);
  }
  
  /**
   * 
   * @param searchParams
   * @param pageable
   * @return
   */
  public List<MonthPunish> findByUserIdAndCreateDate(String userId,String createDate) {
    Map<String, Object> spec= new HashMap<>();
    spec.put("EQ_userId", userId);
    spec.put("GTE_createDate", createDate);
    spec.put("LTE_createDate", createDate);
    
    List<MonthPunish> monthPunishList=null;
    try {
      
      monthPunishList = this.findAll(spec);
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return monthPunishList;
  }
  /**
   * 
   * @param searchParams
   * @param pageable
   * @return
   */
  public List<MonthPunish> findAll(Map<String, Object> searchParams) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<MonthPunish> spec = oilCostSearchFilter(filters.values(), MonthPunish.class);
    List<MonthPunish> monthPunishList=null;
    try {
      
      monthPunishList = mpr.findAll(spec);
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return monthPunishList;
  }

  private static <T> Specification<T> oilCostSearchFilter(final Collection<SearchFilter> filters,
      final Class<T> entityClazz) {

    return new Specification<T>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({ "rawtypes", "unchecked" })
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
