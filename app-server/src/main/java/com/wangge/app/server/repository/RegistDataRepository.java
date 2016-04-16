package com.wangge.app.server.repository;



import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Regist;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Salesman;

public interface RegistDataRepository extends JpaRepository<RegistData, Long>{
	
//	List<RegistData> findByRegistIn(Collection<Regist> listReid);
	
	/*@Query("select r.id,r.shopName,r.phoneNum,r.consignee  from RegistData r  left join r.regist  rs  where rs.id = ?")
	List<RegistData> findByRegistId(Long registId);*/
	
	@Query("select count(*) from RegistData r where r.salesman=?1")
	public int findNumById(Salesman salesman);
	
	@Query("select count(*) from RegistData r where r.salesman=?1 and r.createtime>=trunc(sysdate) and r.createtime<trunc(sysdate)+1")
	public int findDayNum(Salesman salesman);

  public RegistData findByMemberId(String memberId);

  public Double countByRegionId(String area);
  
  
  public List<RegistData> findByLoginAccount(String loginAccount);
  
	
//	List<RegistData> findByRegist(Regist regist);
}
