package com.qding.community.common.weixin.vo.recv;

import com.qding.community.common.weixin.vo.Message;

public class RecvMessage extends Message {

	/**
	 * 消息id，64位整型
	 */
	private String msgId;

	public RecvMessage(String toUserName, String fromUserName, String createTime, String msgType, String msgId) {
		super(toUserName, fromUserName, createTime, msgType);
		this.msgId = msgId;
	}

	public RecvMessage(RecvMessage msg) {
		this(msg.getToUserName(), msg.getFromUserName(), msg.getCreateTime(), msg.getMsgType(), msg.getMsgId());
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
}
