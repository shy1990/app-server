package com.wangge.app.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.Saojie.SaojieStatus;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.repository.SaojieRepository;
import com.wangge.app.server.vo.RegionVo;
import com.wangge.app.server.vo.TreeVo;
import com.wangge.common.entity.Region;
import com.wangge.common.repository.RegionRepository;

@Service
public class RegionService {
	@Autowired
	private SaojieRepository taskSaojieRepository;
	@Autowired
	private RegionRepository regionRepository;
	@Resource
	private SalesmanRepository salesmanRepository;

	/**
	 * 获取扫街区域信息
	 * 
	 * @param salesman
	 * @return Map<String, List<Region>> ,其中 key 为 can,cant.
	 */
	public Map<String, List<RegionVo>> getSaojie(Salesman salesman) {
		Map<String, List<RegionVo>> result = Maps.newHashMap();
		List<RegionVo> can = Lists.newArrayList();
		List<RegionVo> cant = Lists.newArrayList();
		List<Saojie> saojieTasks = taskSaojieRepository.findBySalesman(salesman);
		// TODO 可用java8 stream过滤
		for (Saojie taskSaojie : saojieTasks) {
		//	if (SaojieStatus.PENDING.equals(taskSaojie.getStatus())) {
				for(Saojie Saojie : taskSaojie.getChildren()){
					if(SaojieStatus.PENDING.equals(Saojie.getStatus()) || SaojieStatus.AGREE.equals(Saojie.getStatus())){
						RegionVo r = new RegionVo();
						r.setId(Saojie.getRegion().getId());
						r.setName(Saojie.getRegion().getName());
						r.setCoordinates(Saojie.getRegion().getCoordinates());
						r.setMinValue(Saojie.getMinValue());
						can.add(r);
					}else{
						RegionVo r = new RegionVo();
						r.setId(Saojie.getRegion().getId());
						r.setName(Saojie.getRegion().getName());
						r.setCoordinates(Saojie.getRegion().getCoordinates());
						r.setMinValue(Saojie.getMinValue());
						cant.add(r);
					}
					
				}
				
		//	}
		}
		result.put("open", can);
		result.put("nopen", cant);
		return result;
	}


	/**
	 * 
	 * 功能: 查询区域树型接口 详细： 作者： jiabin 版本： 1.0 日期： 2015年11月6日下午3:05:14
	 *
	 */
	public List<TreeVo> findTreeRegion(String id) {
		List<TreeVo> listTreeVo = new ArrayList<TreeVo>();
		if("0".equals(id)){
			id="370000";
		}
		System.out.println(regionRepository.findOne(id).getChildren());
		for (Region region : regionRepository.findOne(id).getChildren()) {
			System.out.println(region.getId());
			TreeVo treevo=new TreeVo();
			treevo.setId(region.getId());
			treevo.setName(region.getName());
			//treevo.setIcon("zTree/css/zTreeStyle/img/diy/10.png");
			treevo.setOpen(true);
			if(region.getChildren().size()>0){
				treevo.setIsParent(true);
			}else{
				treevo.setIsParent(false);
			}
			treevo.setpId(region.getParent().getId());
			listTreeVo.add(treevo);
		}
		return listTreeVo;
	}
	
	/**
	 * 
	 * 功能: 
	 * 详细： 	
	 * 作者： 	jiabin
	 * 版本：  1.0
	 * 日期：  2015年11月20日下午4:34:21
	 *
	 */
	public List<Region> findRegiondbyParentid(String id){
		System.out.println(regionRepository.findOne(id).getChildren());
		List<Region> listRegion=new ArrayList<Region>();
		for(Region region:regionRepository.findOne(id).getChildren()){
			listRegion.add(region);
		}
		return  listRegion;
	}
	
	
	

	/**
	 * 
	 * 功能: 根据父id查询，用在添加自定义区域id增长
	 * 详细： 	
	 * 作者： 	jiabin
	 * 版本：  1.0
	 * 日期：  2015年11月6日下午3:50:54
	 *
	 */
	public List<Region> findRegionSort(String id){
		List<Region> listRegion= regionRepository.findByParentId(id);
		return listRegion;
	}
	
	
	
	public String findMaxIdByParent(Region region){
		
		return    regionRepository.findMaxIdByParent(region);
	}
	/**
	 * 
	 * 功能: 查询Region
	 * 详细： 	
	 * 作者： 	jiabin
	 * 版本：  1.0
	 * 日期：  2015年11月20日下午4:44:53
	 *
	 */
	public Region findRegion(String id){
		return regionRepository.findOne(id);
	}
	
	/**
	 * 
	 * 功能: 保存
	 * 详细： 	
	 * 作者： 	jiabin
	 * 版本：  1.0
	 * 日期：  2015年11月20日下午4:48:27
	 *
	 */
	public void saveRegion(Region region){
		 
		regionRepository.save(region); 
	}
}
