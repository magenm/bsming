package com.qding.community.common.weixin.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.qding.community.common.weixin.vo.recv.RecvLinkMessage;
import com.qding.community.common.weixin.vo.recv.RecvMessage;

public class WxRecvLinkMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected RecvLinkMessage parser(Element root, RecvMessage msg) throws JDOMException {
		
		String title = getElementText(root, "Title");
		String description = getElementText(root, "Description");
		String url = getElementText(root, "Url");
		return new RecvLinkMessage(msg, title, description, url);
	}

}
