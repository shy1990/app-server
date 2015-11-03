package com.wangge.app.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.UserMember;
import com.wangge.app.server.repository.UserMemberRepository;
import com.wangge.app.server.service.RegistService;

@Service
public class RegistServiceImpl implements RegistService {
	@Autowired
	private UserMemberRepository registRepository;

	@Override
	public void save(UserMember userMember) {
		// TODO Auto-generated method stub
		registRepository.save(userMember);
	}

	@Override
	public UserMember findById(String id) {
		
		return registRepository.findById(id);
	}


}
