package com.wangge.app.server.repositoryimpl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.wangge.app.server.vo.Exam;
import com.wangge.app.server.vo.Exam.Town;

@Repository
public class ExamImpl {
	@PersistenceContext  
    private EntityManager em; 
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
	   String sql0 = "select ASSESS_AREA_ZH,ASSESS_STAGE,ASSESS_TIME,ASSESS_CYCLE,ASSESS_ACTIVENUM,ASSESS_ORDERNUM from sj_yewu.BIZ_ASSESS WHERE USER_ID='"+salesId+"'";
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
	    //指标信息    a.USERNAME=(select USERNAME from SJ_DB.SYS_USER where USER_ID='"+salesId+"' ) 
		String sql = "select a.rname as areaName , count(a.rid) as shopNum,sum(a.count) as count from sj_yewu.BIZ_EXAMINE a where   a.USERNAME=(select USERNAME from SJ_DB.SYS_USER where USER_ID='"+salesId+"' )  and a.rname in("+areas+")  AND a.otype='sku'  group by (a.RID,a.rname)";
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
				shopNum += Integer.parseInt(o[1]+"");
				phoneNum += Integer.parseInt(o[2]+"");
				set.add(town);
			}
			exam.setTown(set);
		}
    exam.setDoneShopNum(shopNum);
    exam.setDonePhoneNum(phoneNum);
    float pnum= (float)phoneNum/exam.getGoalPhoneNum();  
    float snum= (float)shopNum/exam.getGoalShopNum(); 
    DecimalFormat df = new DecimalFormat("0.00");
    exam.setPhoneRate( df.format(pnum));
    exam.setShopRate(df.format(snum));
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
}
