package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long>  {
	public Token findByAccessToken(String accessToken);

}
