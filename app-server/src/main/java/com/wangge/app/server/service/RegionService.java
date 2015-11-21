package com.wangge.app.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.repository.SaojieRepository;
import com.wangge.app.server.vo.TreeVo;
import com.wangge.common.entity.Region;
import com.wangge.common.repository.RegionRepository;

@Service
public class RegionService {
	@Autowired
	private SaojieRepository taskSaojieRepository;
	@Autowired
	private RegionRepository regionRepository;

	/**
	 * 获取扫街区域信息
	 * 
	 * @param salesman
	 * @return Map<String, List<Region>> ,其中 key 为 can,cant.
	 */
	public Map<String, List<Region>> getSaojie(Salesman salesman) {
		Map<String, List<Region>> result = Maps.newHashMap();
		List<Region> can = Lists.newArrayList();
		List<Region> cant = Lists.newArrayList();
		List<Saojie> saojieTasks = taskSaojieRepository.findBySalesman(salesman);
		// TODO 可用java8 stream过滤
		/*for (Saojie taskSaojie : saojieTasks) {
			if (TaskStatus.PENDING.equals(taskSaojie.getStatus())) {
				can.addAll(taskSaojie.getRegions());
			} else {
				cant.addAll(taskSaojie.getRegions());
			}
		}*/
		result.put("can", can);
		result.put("cant", cant);
		return result;
	}

	/**
	 * 
	 * 功能: 查询区域树型接口 详细： 作者： jiabin 版本： 1.0 日期： 2015年11月6日下午3:05:14
	 *
	 */
	public List<TreeVo> findTreeRegion(String id) {
		List<Region> regionList = new ArrayList<Region>();
		List<TreeVo> listTreeVo = new ArrayList<TreeVo>();
		regionList = (List<Region>) regionRepository.findOne(id);
		for (Region region : regionList) {
			System.out.println(region.getId());
			TreeVo treevo = new TreeVo();
			treevo.setId(region.getId());
			treevo.setName(region.getName());
			// treevo.setIcon("zTree/css/zTreeStyle/img/diy/10.png");
			treevo.setOpen(true);
			treevo.setIsParent(true);
			treevo.setpId(region.getParent().getId());
			listTreeVo.add(treevo);
		}
		return listTreeVo;
	}

	/**
	 * 
	 * 功能: 根据父id查询，用在添加自定义区域id增长 详细： 作者： jiabin 版本： 1.0 日期： 2015年11月6日下午3:50:54
	 *
	 */
	/*public List<Object> findRegionSort(String id) {
		List<Object> listRegion = (List<Object>) regionRepository.findByParentId(id);
		return listRegion;
	}*/

	/*public List<Region> findCustomRegiond(String id) {
		List<Region> listCustomRegion = (List<Region>) regionRepository.findCustomRegiond(id);
		return listCustomRegion;
	}*/

	/**
	 * 
	 * 功能: 查询 详细： 作者： jiabin 版本： 1.0 日期： 2015年11月6日下午5:31:22
	 *
	 */
	public Region findRegion(String id) {
		return regionRepository.findOne(id);
	}

	public void saveRegion(Region region) {
		regionRepository.save(region);
	}
}
