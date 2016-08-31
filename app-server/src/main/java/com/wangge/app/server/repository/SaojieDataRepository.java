package com.wangge.app.server.repository;


import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Region;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.SaojieData;

public interface SaojieDataRepository extends JpaRepository<SaojieData, Long>{
	//@Query("select sjd.id,sjd.imageUrl,sjd.name,sjd.description,sjd.coordinate from SaojieData sjd left join sjd.region r where r.id = ?")
	List<SaojieData> findByRegionId(String regionId, Sort sort);
	
	@Query("select d.name,d.description,d.imageUrl  from SaojieData d  left join d.saojie ts  where ts.id = ? ")
	List<SaojieData> findBySaojieId(Long taskId);

	List<SaojieData> findByRegionIn(Collection<Region> listSjid);
	
	SaojieData findByRegistData(RegistData id);
	
	List<SaojieData> findByRegion(Region region);
	
	@Query("select d.id,d.coordinate,d.name,d.description,d.imageUrl,d.registData.id,d.registData.memberId from SaojieData d  left join d.registData  rd  where d.name like ?1% or  (d.region.parent.id=?2 and rd.phoneNum like ?1% )")
	List<Object[]> findByParam(String param,String regionId);
}
