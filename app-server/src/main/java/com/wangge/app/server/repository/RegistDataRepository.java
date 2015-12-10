package com.wangge.app.server.repository;



import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Regist;
import com.wangge.app.server.entity.RegistData;

public interface RegistDataRepository extends JpaRepository<RegistData, Long>{
	
	List<RegistData> findByRegistIn(Collection<Regist> listReid);
	
	@Query("select d.name,d.description,d.imageUrl  from RegistData d  left join d.regist ts  where ts.id = ? ")
	List<RegistData> findByRegistId(Long registId);
}
