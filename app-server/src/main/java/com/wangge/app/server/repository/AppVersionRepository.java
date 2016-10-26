package com.wangge.app.server.repository;

import com.wangge.app.server.entity.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {
	

	
}
