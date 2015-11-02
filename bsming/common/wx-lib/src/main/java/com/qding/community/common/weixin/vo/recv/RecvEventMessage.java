package com.qding.community.common.weixin.vo.recv;

public class RecvEventMessage extends RecvMessage {

	/**
	 * 事件类型，subscribe(订阅)、unsubscribe(取消订阅)、CLICK(自定义菜单点击事件)
	 */
	private String event;
	/**
	 * 事件KEY值，与自定义菜单接口中KEY值对应
	 */
	private String eventKey;
	
	/**
	 * 二维码
	 */
    private String ticket;

	public RecvEventMessage(RecvMessage msg, String event, String eventKey,String ticket ) {
		super(msg);
		this.event = event;
		this.eventKey = eventKey;
		this.ticket=ticket;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	
}
