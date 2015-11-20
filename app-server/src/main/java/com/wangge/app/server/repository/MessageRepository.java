package com.wangge.app.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Message;
import com.wangge.app.server.entity.Message.MessageType;
import com.wangge.app.server.entity.Message.SendChannel;

public interface MessageRepository extends JpaRepository<Message, Long> {
	Page<Message> findByTypeAndChannelAndReceiverContaining(MessageType type,SendChannel channel,String receiver,Pageable page);
}
