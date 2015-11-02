package com.qding.community.common.weixin.vo.recv;

public class RecvTextMessage extends RecvMessage {
	
	/**
	 * 文本消息内容
	 */
	private String content;

	public RecvTextMessage(RecvMessage msg, String content) {
		super(msg);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
