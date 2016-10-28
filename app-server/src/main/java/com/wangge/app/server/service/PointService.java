package com.wangge.app.server.service;


import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * 业务员增加积分
 * Created by joe on 16-10-27.
 */
@Service
public class PointService {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据订单号查询付款金额
     * @param orderNum
     * @return
     */
    public Double findTotalCostByOrderNum(String orderNum){
        Double o = 0.0;
        try{
            String sql = "select r.TOTAL_COST from SJZAIXIAN.SJ_TB_ORDER r where r.ORDER_NUM = " + "'" + orderNum + "'";
            Query query = entityManager.createNativeQuery(sql);
            o = (Double) query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }
        return o;
    }

    /**
     * 增加积分
     * @param point
     * @param mobilePhone
     */
    @Transactional
    public void addPoint(Integer point, String mobilePhone) {
        try {
            String sql = "update SJZAIXIAN.SJ_TB_MEMBERS m set m.point = m.point + " + point + " where m.mobile = " + "'"+ mobilePhone + "'";
            Query query = entityManager.createNativeQuery(sql);
            int a = query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
