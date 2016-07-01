package com.wangge.app.server.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS_MESSAGE")
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum SendChannel {
		WX("微信"), SMS("短信"), PUSH("极光推送");
		private String name;

		private SendChannel(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public enum MessageType {
		ORDER("下单通知"), SYSTEM("提交审核"), ACTIVE("活动"),CANCELORDER("取消订单通知"),SHOUHOU("售后");
		private String name;

		private MessageType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "MESSAGE_ID")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date sendTime;
	private String content;
	private String receiver;
	private String result;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "MESSAGE_TYPE")
	private MessageType type;
	@Enumerated(EnumType.ORDINAL)
	private SendChannel channel;

	public Message() {
		super();
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public SendChannel getChannel() {
		return channel;
	}

	public void setChannel(SendChannel channel) {
		this.channel = channel;
	}

	public void setType(MessageType type) {
		this.type = type;
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

}
