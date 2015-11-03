package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.UserMember;

public interface UserMemberRepository extends JpaRepository<UserMember, Long>  {
	
	public UserMember findById(String id);
}
