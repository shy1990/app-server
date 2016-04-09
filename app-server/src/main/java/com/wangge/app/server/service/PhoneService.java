package com.wangge.app.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Phone;
import com.wangge.app.server.repository.PhoneRepository;

@Service
public class PhoneService {
	@Autowired
	private PhoneRepository phoneRepository;

	public String addPhone(Phone phone) {

		phoneRepository.save(phone);
		return "OK";
	}
}
