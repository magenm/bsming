package com.qding.community.common.weixin.vo.send;

import org.jdom.Document;

public class SendTextMessage extends SendMessage {

	/**
	 * 回复的消息内容,长度不超过2048字节
	 */
	private String content;

	public SendTextMessage(SendMessage msg, String content) {
		super(msg);
		setMsgType("text");
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public Document toDocument() {
		Document doc = super.toDocument();
		createElement(doc.getRootElement(), "Content", getContent());
		return doc;
	}
}
