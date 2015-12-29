package com.wangge.app.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wangge.app.server.entity.Message;
import com.wangge.app.server.entity.Message.MessageType;
import com.wangge.app.server.entity.Message.SendChannel;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long>, JpaSpecificationExecutor<Message>{
	
	
	@Query("select a from Message a where (a.receiver=?1 or a.receiver='all')  and MESSAGE_TYPE=1  and Channel=2") 
	Page<Message> findMessage(String receiver,Pageable pageRequest);
	
	
	Page<Message> findByChannelAndTypeAndReceiverContaining(SendChannel Channel,MessageType type,String receiver,Pageable pageRequest);
	
	@Modifying
	@Query("update Message m set m.result=?1 where m.id=?2")
	public void updateMessageResult(String result,Long id);
	
	
}
