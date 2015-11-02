package com.qding.community.common.weixin.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Message {
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String msgType;
	

	public Message(String toUserName, String fromUserName, String createTime, String msgType) {
		/**
		 * 开发者微信号
		 */
		this.toUserName = toUserName;
		/**
		 * 发送方帐号（一个OpenID）
		 */
		this.fromUserName = fromUserName;
		/**
		 * 消息创建时间 （整型）
		 */
		this.createTime = createTime;
		/**
		 * 消息类型：text/image/location/link/event/music/news
		 */
		this.msgType = msgType;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
