package com.qding.community.common.weixin.parser;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Element;
import org.jdom.JDOMException;

import com.qding.community.common.weixin.vo.recv.RecvEventMessage;
import com.qding.community.common.weixin.vo.recv.RecvMessage;

public class WxRecvEventMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected RecvEventMessage parser(Element root, RecvMessage msg) throws JDOMException {
		String event = getElementText(root, "Event");
		String eventKey = getElementText(root, "EventKey");
		String ticket = getElementText(root, "Ticket");
		if(StringUtils.isBlank(ticket)){
			ticket="";
		}
		
		return new RecvEventMessage(msg, event,eventKey,ticket);
	}

}
