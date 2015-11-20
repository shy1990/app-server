package com.wangge.app.server.entity;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table( name = "T_MESSAGE")
@SequenceGenerator(schema="SANJI",sequenceName="SEQ_SAOJIE_DATA",name="seq")
public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq")
	private Long id;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private String content;
	private String receiver;
	private String result;
	@Enumerated(EnumType.STRING)
	private MessageType type;
	//消息状态 0未读 1已读
	public Message() {
		super();
	}
	


	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	

	public MessageType getType() {
		return type;
	}



	public void setType(MessageType type) {
		this.type = type;
	}


	public enum MessageType {
		 JIGUANGPUSH_ORDER,JIGUANGPUSH_SIMPLE
	}
}
