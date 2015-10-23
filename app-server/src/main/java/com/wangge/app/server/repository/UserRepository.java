package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.User;

public interface UserRepository extends JpaRepository<User, Long>  {
	public User findByUsername(String username);
	
	@Query("from User a where a.username =? and password=?") 
	public User checkLogin(String name,String pass);
	
	@Query("select username from User  ") 
	List<User> findAllUser();
}
