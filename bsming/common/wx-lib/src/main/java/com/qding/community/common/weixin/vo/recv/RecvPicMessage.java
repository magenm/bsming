package com.qding.community.common.weixin.vo.recv;

public class RecvPicMessage extends RecvMessage {

	/**
	 * 图片链接
	 */
	private String picUrl;

	public RecvPicMessage(RecvMessage msg, String picUrl) {
		super(msg);
		this.picUrl = picUrl;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
