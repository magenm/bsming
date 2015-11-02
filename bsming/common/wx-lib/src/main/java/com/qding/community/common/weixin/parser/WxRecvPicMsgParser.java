package com.qding.community.common.weixin.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.qding.community.common.weixin.vo.recv.RecvMessage;
import com.qding.community.common.weixin.vo.recv.RecvPicMessage;

public class WxRecvPicMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected RecvPicMessage parser(Element root, RecvMessage msg) throws JDOMException {
		return new RecvPicMessage(msg, getElementText(root, "PicUrl"));
	}

}
