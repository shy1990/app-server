package com.wangge.app.server.repositoryimpl;

import com.wangge.app.server.entity.Message;
import com.wangge.app.server.vo.OrderPub;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
public class OrderImpl {

	private static final Logger LOG = Logger.getLogger(OrderImpl.class);
	@PersistenceContext
	private EntityManager em;
	/**
	 *
	 * @Description: 根据订单号查询订单详情
	 * @param @param
	 *            orderNum
	 * @param @return
	 * @return List<OrderPub>
	 * @throws @author
	 *             changjun
	 * @date 2015年11月7日
	 */
	// public List<OrderPub> selOrderDetailByOrderNum(String orderNum){
	// String sql = " select * from YEWU.V_ORDER_DETAIL WHERE
	// ORDER_NUM="+orderNum+"";
	// Query query = em.createNativeQuery(sql);
	// List obj = query.getResultList();
	// //[20151104171825387, 260, 260, 1, Lenovo/联想 联想A2860移动4G手机, 单品, 黑色, 联想,
	// 移动4G, 4GB, 260, 未发货, 2015-11-04 17:18:25.0]
	// List<OrderPub> orderlist = new ArrayList<OrderPub>();
	// if(obj!=null && obj.size()>0){
	// Iterator it = obj.iterator();
	// while(it.hasNext()){
	// Object[] o = (Object[])it.next();
	// OrderPub order = new OrderPub();
	// order.setOrderNum(orderNum);
	//// order.setDealPrice(o[2]+"");
	//// order.setNums(o[3]+"");
	//// order.setName(o[4]+"");
	//// order.setColorName(o[6]+"");
	//// order.setStandard(o[8]+"");
	//// order.setStorage(o[9]+"");
	//// order.setAmount(o[10]+"");
	//// order.setShipStatus(o[11]+"");
	// order.setCreateTime((Date)o[12]);
	// orderlist.add(order);
	// }
	// }
	// return orderlist;
	// }
	/**
	 *
	 * @Description: 根据业务手机号订单号判断该订单是否属于该业务员
	 * @param @param
	 *            ordernum
	 * @param @param
	 *            mobile
	 * @param @return
	 * @return boolean
	 * @throws @author
	 *             changjun
	 * @date 2015年11月12日
	 */
	// public boolean selOrderByOrderNumAndSale(String ordernum,String mobile){
	// String sql = "select m.username from SJZAIXIAN.SJ_TB_ORDER o LEFT JOIN
	// SJZAIXIAN.SJ_TB_MEMBERS m ON o.member_id=m.id LEFT JOIN
	// SJZAIXIAN.SJ_TB_ADMIN a on m.admin_id= a.id where
	// o.order_num="+ordernum+" and a.MOBILEPHONE="+mobile+" and
	// o.sale_signforTime is null";
	// String sql = "select o.id from SJZAIXIAN.SJ_TB_ORDER o where
	// o.order_num="+ordernum+" and o.sale_signforTime is null";
	// Query query = em.createNativeQuery(sql);
	// return query.getResultList().size()>0?true:false;
	// }

	/**
	 *
	 * @Description: 根据订单号判断该订单是否已签收
	 * @param @param
	 *            ordernum
	 * @param @param
	 *            mobile
	 * @param @return
	 * @return boolean
	 * @throws @author
	 *             changjun
	 * @date 2015年11月12日
	 */
	public boolean checkByOrderNum(String ordernum) {
		String sql = "select  o.id  from SJZAIXIAN.SJ_TB_ORDER o  where o.order_num='" + ordernum
				+ "' and (o.YEWU_SIGNFOR_TIME is null or o.ship_status='2')";
		Query query = em.createNativeQuery(sql);
		return query.getResultList().size() > 0 ? true : false;
	}

	/**
	 *
	 * @Description: 根据业务用户名查询所属区域订单的送货状态
	 * @param @param
	 *            mobile
	 * @param @return
	 * @return List<OrderPub>
	 * @throws @author
	 *             changjun
	 * @date 2015年11月11日
	 */
	public List<OrderPub> selOrderSignforStatus(String username, int num, Pageable pageRequest) {
		// o.ship_status !=0 and
		String sql = "select m.username,o.createTime,o.order_num ,o.total_cost as amount,decode(o.ship_status,'0','未收货','1','已发货','2','已送达(地包)','3','已收货','4','待退货','5','已退货','6','卖家拒绝退货') as shipStatus from SJZAIXIAN.SJ_TB_ORDER o LEFT JOIN SJZAIXIAN.SJ_TB_MEMBERS m ON o.member_id=m.id  LEFT JOIN SJZAIXIAN.SJ_TB_ADMIN a on m.admin_id= a.id where  a.username='"
				+ username + "' order by createTime desc";
		Query query = em.createNativeQuery(sql);
		int count = query.getResultList().size();
		// int totalPageNum = (count + pageRequest.getPageSize() - 1) /
		// pageRequest.getPageSize();
		int start = num > 1 ? num - 1 : 0;
		query.setFirstResult(start * pageRequest.getPageSize());
		query.setMaxResults(pageRequest.getPageSize());
		List obj = query.getResultList();
		List<OrderPub> orderlist = new ArrayList<OrderPub>();
		if (obj != null && obj.size() > 0) {
			Iterator it = obj.iterator();
			while (it.hasNext()) {
				Object[] o = (Object[]) it.next();
				OrderPub order = new OrderPub();
				order.setUsername(o[0] + "");
				order.setCreateTime((Date) o[1]);
				order.setOrderNum(o[2] + "");
				order.setTotalCost(Double.parseDouble(o[3] + ""));
				order.setStatus(o[4] + "");
				order.setTotalPage(count);
				orderlist.add(order);
			}
		}
		return orderlist;
	}

	/**
	 *
	 * @Description: 修改订单状态
	 * @param @param
	 *            ordernum
	 * @param @param
	 *            status
	 * @param @return
	 * @return boolean
	 * @throws @author
	 *             changjun
	 * @date 2015年11月12日
	 */
	@Transactional
	public String updateOrderShipStateByOrderNum(String ordernum, String paytype, String point, String status,
																							 Integer type) {
		// if("已送达".equals(status)){
		// status="2";
		// }
		String sql = "";
		if (1 == type) {
			sql = "update SJZAIXIAN.SJ_TB_ORDER set ship_status='" + status
					+ "' ,yewu_signfor_time=sysdate where order_num='" + ordernum + "'";
		} else {
			sql = "update SJZAIXIAN.SJ_TB_ORDER set PAY_MENT='" + paytype + "',CUSTOM_SIGNFOR_ADDRESS='" + point
					+ "',ship_status='" + status + "' ,custom_signfor_time=sysdate where order_num='" + ordernum + "'";
		}

		Query query = em.createNativeQuery(sql);
		return query.executeUpdate() > 0 ? "suc" : "false";
	}

	/**
	 *
	 *
	 * @Description: 修改订单状态及客户签收时间
	 * @param @param
	 *            ordernum
	 * @param @param
	 *            status
	 * @param @return
	 * @return boolean
	 * @throws @author
	 *             changjun
	 * @date 2015年11月12日
	 */

	@Transactional
	public void updateOrderShipStateByOrderNum(String ordernum, String status,String payStatus,String dealType) {
		String paySql = !StringUtils.isEmpty(payStatus) ? " ,PAY_STATUS='"
				+ payStatus + "'" : "";
		String dealTypeSql = !StringUtils.isEmpty(dealType) ? " ,deal_type='"
				+ dealType + "'" : "";
		String sql = "update SJZAIXIAN.SJ_TB_ORDER set ship_status='" + status
				+ "' ,custom_signfor_time=sysdate,signfortime = sysdate"
				+ paySql + dealTypeSql + " where order_num='" + ordernum + "'";
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
	}

	/**
	 *
	 * @Description: 客户拒收原因保存
	 * @param @param
	 *            ordernum
	 * @param @param
	 *            reason
	 * @param @return
	 * @return String
	 * @throws @author
	 *             changjun
	 * @date 2015年12月1日
	 */
	@Transactional
	public void saveRefuseReason(String ordernum, String reason) {
		String sql = "update SJZAIXIAN.SJ_TB_ORDER set ship_status=4,refuse_reason='" + reason + "' where order_num='"
				+ ordernum + "'";
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
	}

	/**
	 *
	 * @Description: 客户取消订单修改信息表类型
	 * @param @param
	 *            MessageType mt
	 * @param @param
	 *            String orderNum
	 * @param @return
	 * @return String
	 * @throws @author
	 *             changjun
	 * @date 2015年12月1日
	 */
	@Transactional
	public String updateMessageType(String state, String orderNum) {
		String sql = "update SJ_YEWU.sys_message set message_type= '" + state + "' where content like '%" + orderNum
				+ "%'";
		Query query = em.createNativeQuery(sql);
		return query.executeUpdate() > 0 ? "suc" : "false";
	}

	/**
	 *
	 * @Description: 退款
	 * @param @param
	 *            orderNum
	 * @param @return
	 * @return Map
	 * @throws @author
	 *             changjun
	 * @date 2016年1月18日
	 */
	public Map checkMoneyBack(String orderNum) {
		String sql = "select PAY_MENT,TOTAL_COST,WALLET_PAY_NO,WALLET_NUM from SJZAIXIAN.sj_tb_order where ORDER_NUM='"
				+ orderNum + "'";
		Query query = em.createNativeQuery(sql);
		Map<String, String> map = new HashMap<String, String>();
		List obj = query.getResultList();
		if (obj != null && obj.size() > 0) {
			Iterator it = obj.iterator();
			while (it.hasNext()) {
				Object[] o = (Object[]) it.next();
				map.put("payMent", o[0] + "");
				map.put("totalCost", o[1] + "");
				map.put("payNo", o[2] + "");
				map.put("walletNum", o[3] + "");
			}
		}
		return map;
	}

	/**
	 *
	 * @Description: 保存活动通知记录
	 * @param @param
	 *            list
	 * @param @return
	 * @return boolean
	 * @throws @author
	 *             changjun
	 * @date 2016年1月19日
	 */
	@Transactional
	public Long addActivityMsg(List<Message> list) {
		try {
			Long id = null;
			for (int i = 0; i < list.size(); i++) {
				em.persist(list.get(i));
				if (i % 20 == 0) {
					em.flush();
					em.clear();
				}
				id = list.get(list.size() - 1).getId();
			}
			return id;
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
			return 0l;
		}
	}

	public Map findOrderPayStatusByOrderNum(String orderno) {
		String sql = "select PAY_STATUS from SJZAIXIAN.sj_tb_order where ORDER_NUM='" + orderno + "'";
		Query query = em.createNativeQuery(sql);
		Map<String, String> map = new HashMap<String, String>();
		try {
			Object o = query.getSingleResult();
			map.put("payStatus", o + "");
		} catch (Exception e) {
			LOG.info(e);
		}

		return map;
	}
}