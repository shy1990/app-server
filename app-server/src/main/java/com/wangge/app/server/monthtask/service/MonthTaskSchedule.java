package com.wangge.app.server.monthtask.service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wangge.app.server.monthtask.repository.MonthTaskSubRepository;
import com.wangge.app.server.monthtask.repository.MonthshopBasDataRepository;
import com.wangge.app.server.repository.RegistDataRepository;
import com.wangge.app.server.repository.SalesmanRepository;

@Component
@EnableScheduling
public class MonthTaskSchedule {
	@Autowired
	MonthTaskSubRepository montSubrep;
	@PersistenceContext
	private EntityManager em;
	@Autowired
	MonthshopBasDataRepository monthShopDRep;
	@Autowired
	SalesmanRepository salesmanRep;
	@Autowired
	RegistDataRepository registRep;

	/**
	 * 每天凌晨执行,进行提醒
	 */
	@Scheduled(cron = "0 0 1 ? * *")
	public void exsitUnpaymentRemarkStatus() {
		montSubrep.updatebyDelay();
	}

//	@Scheduled(cron = "0 0/1 * ? * *")
//	public  void insertRegist() {
//		String registSql = "select d.registdata_id, d.user_id, d.region_id \n" + "  from sys_registdata d\n"
//				+ " where not exists (select 1 \n" + "          from sys_monthshop_basdata s\n"
//				+ "         where s.registdata_id = d.registdata_id)";
//		Query registQuery = em.createNativeQuery(registSql);
//		List<Object> registList = registQuery.getResultList();
//		if (registList.size() > 0) {
//			List<MonthshopBasData> shopsList = new ArrayList<MonthshopBasData>();
//			String month = DateUtil.getPreMonth(new Date(), 0);
//			for (Object obj : registList) {
//				Object[] sd = (Object[]) obj;
//				String regionId = sd[2].toString();
//				Long registDataId = Long.parseLong(sd[0].toString());
//				String userId = sd[1].toString();
//				MonthshopBasData shop = new MonthshopBasData(regionId, 0, 0, month, 0, registRep.findOne(registDataId),
//						salesmanRep.findOne(userId));
//				shopsList.add(shop);
//			}
//			monthShopDRep.save(shopsList);
//		}
//		System.out.println("*************************"+new Date());
//	}
}
