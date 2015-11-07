package com.wangge.app.server.repositoryimpl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.wangge.app.server.entity.Salesman;

//@Repository
public class UtilImpl {
	@PersistenceContext  
    private EntityManager em; 
	 public List<Salesman> findByPMobile(String mobile){
		   String hql = "select username from T_SALESMAN where phone = :mobile";  
	       Query q = em.createQuery(hql);
	       q.setParameter("mobile", mobile);	
		   return  q.getResultList();
	   }
}
