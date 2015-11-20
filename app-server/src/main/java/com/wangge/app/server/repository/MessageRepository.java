package com.wangge.app.server.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.app.server.entity.Message;
import com.wangge.app.server.entity.Message.MessageType;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long>, JpaSpecificationExecutor<Message>{
	public Message findById(Long id);
	
	Page<Message> findByReceiverAndType(String  receiver, MessageType type,Pageable pageRequest);
	@Transactional
	@Modifying  
	@Query("update Message m set m.result=?1 where m.id=?2")
	public void updateMessageResult(String resuly,Long id);
}
