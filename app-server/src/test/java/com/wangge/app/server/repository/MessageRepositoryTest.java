package com.wangge.app.server.repository;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.Message;
import com.wangge.app.server.entity.Message.MessageType;
import com.wangge.app.server.entity.Message.SendChannel;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class MessageRepositoryTest {
	@Autowired
	private MessageRepository mr;

	@Test
	public void testAdd() {
		Message entity =new Message();
		entity.setChannel(SendChannel.PUSH);
		entity.setContent("特大活动通知，手机免费送，不要钱！！！！");
		entity.setReceiver("18615696354");
		entity.setSendTime(new Date());
		entity.setType(MessageType.ACTIVE);
		entity.setResult("sucess");
		mr.save(entity);
	}
//	@Test
//	public void testQuery() {
//		Page<Message> messages = mr.findByTypeAndChannelAndReceiverContaining(MessageType.ORDER, SendChannel.PUSH, "15069046916",null);
//		for (Message message : messages) {
//			System.out.println(message.getContent());
//		}
//		
//	}

}
