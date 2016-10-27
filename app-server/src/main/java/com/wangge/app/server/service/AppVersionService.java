package com.wangge.app.server.service;


import com.wangge.app.server.entity.AppVersion;
import com.wangge.app.server.repository.AppVersionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppVersionService {
	@Resource
	private AppVersionRepository avr;

	public List<AppVersion> findAppVersion(){
		return  avr.findAll();
	}

}
