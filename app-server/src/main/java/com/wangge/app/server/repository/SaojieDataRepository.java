package com.wangge.app.server.repository;


import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.SaojieData;

public interface SaojieDataRepository extends JpaRepository<SaojieData, Long>{
	//@Query("select sjd.id,sjd.imageUrl,sjd.name,sjd.description,sjd.coordinate from SaojieData sjd left join sjd.region r where r.id = ?")
	List<SaojieData> findByRegionId(String regionId);
	
	@Query("select d.name,d.description,d.imageUrl  from SaojieData d  left join d.saojie ts  where ts.id = ? ")
	List<SaojieData> findBySaojieId(Long taskId);

	List<SaojieData> findBySaojieIn(Collection<Saojie> listSjid);
	
	SaojieData findByRegistData(RegistData id);
}
