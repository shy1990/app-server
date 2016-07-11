package com.wangge.app.server.repositoryimpl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.MonthTarget;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.service.MonthTargetService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;

import com.wangge.app.server.vo.Exam;
import com.wangge.app.server.vo.Exam.Town;

@Repository
public class ExamImpl {
	@PersistenceContext  
    private EntityManager em;
	@Resource
	private MonthTargetService monthTargetService;
	/**
	 * 
	 * @Description: 根据业务员id查看所属片区二次提货客户情况
	 * @param @param salesId
	 * @param @return   
	 * @return Exam  
	 * @throws ParseException 
	 * @throws
	 * @author changjun
	 * @date 2015年12月7日
	 */
	public Exam ExamSalesman(String salesId) throws ParseException{
	  //查询业务考核信息
	   String sql0 = "select ASSESS_AREA_ZH,ASSESS_STAGE,ASSESS_TIME,ASSESS_CYCLE,ASSESS_ACTIVENUM,ASSESS_ORDERNUM from SJ_BUZMGT.SYS_ASSESS WHERE USER_ID='"+salesId+"'";
      Query query0 =  em.createNativeQuery(sql0);
      List obj0 = query0.getResultList();
      String areas = null;
      Exam exam = new Exam();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                
      
      if(obj0!=null &&  obj0.size()>0){
        Iterator it = obj0.iterator(); 
        while(it.hasNext()){
          Object[] o = (Object[])it.next();
          areas = o[0]+"";
          exam.setStage(o[1]+"");
          exam.setBeginDate(sdf.parse(o[2]+""));
          exam.setCycle(o[3]+"");
          exam.setGoalShopNum(Integer.parseInt(o[4]+""));
          exam.setGoalPhoneNum(Integer.parseInt(o[5]+""));
        }
      }

		exam = getTownInfo(exam,salesId);//获取按地区分组数据

		float pnum= (float)exam.getDonePhoneNum()/exam.getGoalPhoneNum();
		float snum= (float)exam.getDoneShopNum()/exam.getGoalShopNum();
		DecimalFormat df = new DecimalFormat("0.00");
		exam.setPhoneRate( df.format(pnum));
		exam.setShopRate(df.format(snum));
		return exam;
	}

	public Exam getTownInfo(Exam exam,String salesId){
		//指标信息    a.USERNAME=(select USERNAME from SJ_DB.SYS_USER where USER_ID='"+salesId+"' )
		String sql = "select  a.rname as areaName , count(a.rid) as shopNum,sum(a.acount) as count from SJ_BUZMGT.BIZ_EXAMINE a where   a.USERNAME=(select USERNAME from SYS_USER where USER_ID='"+salesId+"' ) AND a.otype='sku'  group by (a.RID,a.rname)";
		Query query =  em.createNativeQuery(sql);
		List obj = query.getResultList();
		int shopNum=0; //商家数
		int phoneNum = 0; //手机数
		if(obj!=null && obj.size()>0){
			Iterator it = obj.iterator();
			Set<Town> set = new HashSet<Town>();
			while(it.hasNext()){
				Object[] o = (Object[])it.next();
				Town town = new Town();
				town.setAreaName(o[0]+"");
				town.setShopNum(o[1]+"");
				town.setCount(o[2]+"");
				if(Integer.parseInt(o[2]+"")>1){
					shopNum += Integer.parseInt(o[1]+"");
				}
				phoneNum += Integer.parseInt(o[2]+"");
				set.add(town);
			}
			exam.setTown(set);
		}
		exam.setDoneShopNum(shopNum);
		exam.setDonePhoneNum(phoneNum);
		return exam;
	}

	/**
	 * 
	 * @Description: 根据区域名查看该区域二次提货商家详情
	 * @param @param salesId
	 * @param @param areaName
	 * @param @return   
	 * @return Map  
	 * @throws
	 * @author changjun
	 * @date 2015年12月7日
	 */
	public Map examDetail(String salesId,String area){
		String sql = "select a.shopname,a.acount from sj_yewu.BIZ_EXAMINE a where a.USERNAME='"+salesId+"' and a.name='"+area+"'";
		Query query =  em.createNativeQuery(sql);
		List obj = query.getResultList();
		Map<Object,Object> map = new HashMap<Object,Object>();
		if(obj!=null && obj.size()>0){
			Iterator it = obj.iterator();
			while(it.hasNext()){
				Object[] o = (Object[])it.next();
				map.put(o[0], o[1]);
			}
		}
		return map;
	}

	/**
	 * 查询统计该业务当前的月指标数据
	 * @param salesman
	 * @return Exam
     */
	public Json getMonthTarget(Salesman salesman){
		Json jsonObject = new Json();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String nowDate = sdf.format(new Date());
		//根据业务Id和当前月份和已发布的状态查询
		MonthTarget monthTarget = monthTargetService.findBySalesmanAndTargetCycleAndPublishStatus(salesman,nowDate,1);
		if (monthTarget != null && !"".equals(monthTarget)){
			Exam exam = new Exam();
			exam.setCycle(nowDate);
			exam.setGoalPhoneNum(monthTarget.getOrderNum());
			exam.setGoalMerchantNum(monthTarget.getMerchantNum());
			exam.setGoalShopNum(monthTarget.getActiveNum());
			exam.setGoalMatureNum(monthTarget.getMatureNum());

//		exam = getTownInfo(exam,salesman.getId());//获取按区域分组统计的数据

			//根据业务员获取获取所有商家的提货量
			String sql = "select nvl(sum(m.NUMS),0) nums from " +
					"MOTHTARGETDATA m " +
					"where to_char(CREATETIME,'YYYY-MM') like ? and PARENTID like ?  ";
			//根据业务员获取获取活跃商家
			String sql1 = "select nvl(count(1),0) from (select count(t.shopname) from (" +
					"select m.shopname,m.createTime,count(to_char(CREATETIME,'YYYY-MM-DD')) " +
					"FROM mothtargetdata m " +
					"where to_char(CREATETIME,'YYYY-MM') like ? and PARENTID like ? " +
					"group by to_char(CREATETIME,'YYYY-MM-DD'),m.shopname,m.createTime ) t " +
					"group by t.shopname " +
					"having count(t.shopname)>=2)";
			//根据业务员获取成熟商家
			String sql2 = "select nvl(count(1),0) from (select count(t.shopname) from (" +
					"select m.shopname,m.createTime,count(to_char(CREATETIME,'YYYY-MM-DD')) " +
					"FROM mothtargetdata m " +
					"where to_char(CREATETIME,'YYYY-MM') like ? and PARENTID like ? " +
					"group by to_char(CREATETIME,'YYYY-MM-DD'),m.shopname,m.createTime ) t " +
					"group by t.shopname " +
					"having count(t.shopname)>=5)";

			//根据业务员获取提货商家
			String sql3 = "select nvl(count(1),0) from (select count(t.shopname) from (" +
					"select m.shopname,m.createTime,count(to_char(CREATETIME,'YYYY-MM-DD')) " +
					"FROM mothtargetdata m " +
					"where to_char(CREATETIME,'YYYY-MM') like ? and PARENTID like ? " +
					"group by to_char(CREATETIME,'YYYY-MM-DD'),m.shopname,m.createTime ) t " +
					"group by t.shopname " +
					")";
			String regionId = salesman.getRegion().getId();
			Query query = em.createNativeQuery(sql);
			int a = 1;
			query.setParameter(a,"%" + nowDate + "%");
			int b = 2;
			query.setParameter(b,"%" + regionId + "%");
			Query query1 = em.createNativeQuery(sql1);
			query1.setParameter(a,"%" + nowDate + "%");
			query1.setParameter(b,"%" + regionId + "%");
			Query query2 = em.createNativeQuery(sql2);
			query2.setParameter(a,"%" + nowDate + "%");
			query2.setParameter(b,"%" + regionId + "%");
			Query query3 = em.createNativeQuery(sql3);
			query3.setParameter(a,"%" + nowDate + "%");
			query3.setParameter(b,"%" + regionId + "%");
			List<Object> list1 = query.getResultList();
			List<Object> list2 = query1.getResultList();
			List<Object> list3 = query2.getResultList();
			List<Object> list4 = query3.getResultList();
			if(CollectionUtils.isNotEmpty(list1)){
				exam.setDonePhoneNum(((BigDecimal)list1.get(0)).intValue());//插入实际的提货量
			}
			if(CollectionUtils.isNotEmpty(list2)){
				exam.setDoneShopNum(((BigDecimal)list2.get(0)).intValue());//插入活跃商家
			}
			if(CollectionUtils.isNotEmpty(list3)){
				exam.setDoneMatureNum(((BigDecimal)list3.get(0)).intValue());//插入成熟商家数量
			}
			if(CollectionUtils.isNotEmpty(list4)){
				exam.setDoneMerchantNum(((BigDecimal)list4.get(0)).intValue());//插入提货商家数量
			}
			jsonObject.setObj(exam);
			jsonObject.setSuccess(true);
			return jsonObject;
		}else{
			jsonObject.setSuccess(false);
			jsonObject.setMsg("您当前没有月指标!");
			return jsonObject;
		}

	}
}
