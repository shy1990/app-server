package com.wangge.app.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.app.server.entity.Message;
import com.wangge.app.server.entity.Message.MessageType;
import com.wangge.app.server.entity.Message.SendChannel;
import com.wangge.app.server.repository.MessageRepository;
@Service
public class MessageService {
	@Autowired
	private MessageRepository mr;
	/**
	 * 
	 * @Description: 查询一个业务收到的所有信息
	 * @param @param receiver
	 * @param @param type
	 * @param @param pageRequest
	 * @param @return   
	 * @return Page<Message>  
	 * @throws
	 * @author changjun
	 * @date 2015年11月20日
	 */
	public Page<Message> findMessageByReceiver(String receiver,Pageable pageRequest){
		return mr.findMessage(receiver, pageRequest);
	}
	/**
	 * 
	 * @Description: 根据渠道类型包含的接收者查询
	 * @param @param Channel
	 * @param @param type
	 * @param @param receiver
	 * @param @param pageRequest
	 * @param @return   
	 * @return Page<Message>  
	 * @throws
	 * @author changjun
	 * @date 2015年11月20日
	 */
	public Page<Message> findByChannelAndTypeAndReceiverContaining(SendChannel Channel,MessageType type,String receiver,Pageable pageRequest){
		return  mr.findByChannelAndTypeAndReceiverContaining(Channel, type, receiver, pageRequest);
	}
	/**
	 * 
	 * @Description: 推送返回失败  把错误信息保存
	 * @param @param result
	 * @param @param id
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author changjun
	 * @date 2015年11月20日
	 */
	@Transactional
	public void updateMessageResult(String result,Long id){
		 mr.updateMessageResult(result, id);
	}
	
	public void save(Message mes) {
		mr.save(mes);
	}
	public Message findOne(Long long1) {
		return mr.findOne(long1);
	}
	
}
