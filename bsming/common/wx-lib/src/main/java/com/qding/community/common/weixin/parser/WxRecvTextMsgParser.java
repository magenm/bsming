package com.qding.community.common.weixin.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.qding.community.common.weixin.vo.recv.RecvMessage;
import com.qding.community.common.weixin.vo.recv.RecvTextMessage;

public class WxRecvTextMsgParser extends WxRecvMsgBaseParser{

	@Override
	protected RecvTextMessage parser(Element root, RecvMessage msg) throws JDOMException {
		return new RecvTextMessage(msg, getElementText(root, "Content"));
	}

	
}
