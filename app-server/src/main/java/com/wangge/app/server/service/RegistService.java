package com.wangge.app.server.service;

import org.springframework.stereotype.Service;
import com.wangge.app.server.entity.UserMember;

@Service
public interface RegistService {
	
	public void save(UserMember userMember);
	
	public UserMember findById(String id);
}
