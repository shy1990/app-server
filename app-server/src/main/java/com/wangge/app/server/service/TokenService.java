package com.wangge.app.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Token;
import com.wangge.app.server.entity.User;
import com.wangge.app.server.repository.TokenRepository;

@Service
public class TokenService {
	@Autowired
	private TokenRepository tokenRepository;
	/**
	 * 生成唯一token
	 * @param user
	 * @return
	 */
	public Token generateToken(User user) {
		//TODO 保证token唯一
		Token token = new Token();
		token.setAccessToken("111111");
		token.setExpiresIn(10);
		token.setUser(user);
		return tokenRepository.save(token);
	}

}
