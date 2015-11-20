package com.wangge.app.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
