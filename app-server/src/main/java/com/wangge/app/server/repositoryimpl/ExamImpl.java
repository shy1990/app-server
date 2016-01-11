package com.wangge.app.server.repositoryimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.wangge.app.server.vo.Exam;

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
	 * @throws
	 * @author changjun
	 * @date 2015年12月7日
	 */
	public Exam ExamSalesman(String salesId){
		String sql = "select a.rname areaName , count(a.rid) as shopNum from sj_yewu.BIZ_EXAMINE a where a.USERNAME='"+salesId+"'  group by (a.RID,a.rname)";
		Exam exam = new Exam();
		Query query =  em.createNativeQuery(sql);
		List obj = query.getResultList();
		if(obj!=null && obj.size()>0){
			Map<Object,Object> map = new HashMap<Object,Object>();
			int count=0;
			Iterator it = obj.iterator();
			while(it.hasNext()){
				Object[] o = (Object[])it.next();
				map.put(o[0], o[1]);
				count += Integer.parseInt(o[0]+"");
			}
			exam.setMap(map);
			exam.setDoneNum(count);
		}
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
	public Map examDetail(String salesId,String areaName){
		String sql = "select a.shopname,a.acount from sj_yewu.BIZ_EXAMINE a where a.USERNAME='"+salesId+"' and a.rname='"+areaName+"'";
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
