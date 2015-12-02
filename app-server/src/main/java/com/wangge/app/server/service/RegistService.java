package com.wangge.app.server.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wangge.app.server.entity.Regist;
import com.wangge.app.server.entity.Regist.RegistStatus;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.Saojie.SaojieStatus;
import com.wangge.app.server.repository.RegistRepository;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.vo.RegionVo;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class RegistService {


	@Autowired
	private SalesmanRepository salesmanRepository;
	@Resource
	private RegistRepository registRepository;
	/**
	 * 
	 * @Description: 业务注册区域(可开发，不可开发，已开发)
	 * @param @param salesman
	 * @param @return
	 * @return Map<String,List<RegionVo>>
	 * @author
	 * @date 2015年12月1日
	 * @version V2.0
	 */
	public Map<String, List<RegionVo>> getRegistRegion(Salesman salesman) {
		Map<String, List<RegionVo>> result = Maps.newHashMap();
		List<RegionVo> dev = Lists.newArrayList();
		List<RegionVo> nodev = Lists.newArrayList();
		List<Regist> registTasks = registRepository.findBySalesman(salesman);
		// TODO 可用java8 stream过滤
		for (Regist taskRegist : registTasks) {
		//	if (SaojieStatus.PENDING.equals(taskSaojie.getStatus())) {
				for(Regist regist : taskRegist.getChildren()){
					if(RegistStatus.PENDING.equals(regist.getStatus())){
						RegionVo r = new RegionVo();
						r.setId(regist.getRegion().getId());
						r.setName(regist.getRegion().getName());
						r.setCoordinates(regist.getRegion().getCoordinates());
						r.setMinValue(regist.getMinValue());
						dev.add(r);
					}else{
						RegionVo r = new RegionVo();
						r.setId(regist.getRegion().getId());
						r.setName(regist.getRegion().getName());
						r.setCoordinates(regist.getRegion().getCoordinates());
						r.setMinValue(regist.getMinValue());
						nodev.add(r);
					}
					
				}
				
		//	}
		}
		result.put("dev", dev);
		result.put("nodev", nodev);
		return result;
	}
}
