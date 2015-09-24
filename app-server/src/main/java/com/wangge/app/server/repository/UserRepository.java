package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.User;

public interface UserRepository extends JpaRepository<User, Long>  {
	public User findByUsername(String username);
}
