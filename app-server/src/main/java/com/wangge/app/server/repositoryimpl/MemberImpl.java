package com.wangge.app.server.repositoryimpl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository
public class MemberImpl {
	@PersistenceContext  
    private EntityManager em; 
	
	public Map<String,String> findMemberInfo(String loginAccount){
		Query query = em.createNativeQuery("select m.id memberId,m.truename consignee,m.address address,m.username shopname,m.mobile mobile from SJZAIXIAN.SJ_TB_MEMBERS m where m.mobile='"+loginAccount+"'");
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List rows = query.getResultList();
		Map<String,String> result = null;
		for(Object obj : rows){
			result = (Map)obj;
		}
		return result;
	}
}
