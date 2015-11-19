package com.wangge.app.server.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.repository.SaojieDataRepository;
import com.wangge.app.server.repository.SaojieRepository;
import com.wangge.common.entity.Region;
import com.wangge.common.repository.RegionRepository;

@Service
public class DataSaojieService {
	@Resource
	private SaojieDataRepository dataSaojieRepository;

	@Autowired
	private SaojieRepository taskSaojieRepository;

	@Autowired
	private RegionRepository regionRepository;

	public void addDataSaojie(SaojieData dataSaojie) {

		dataSaojieRepository.save(dataSaojie);
	}

	public List<SaojieData> getSaojieDataByregion(Region region) {


		return dataSaojieRepository.findByRegion(region);
	}

}
