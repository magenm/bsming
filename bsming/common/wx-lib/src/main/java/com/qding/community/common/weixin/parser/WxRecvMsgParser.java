package com.qding.community.common.weixin.parser;

import org.jdom.Document;
import org.jdom.JDOMException;

import com.qding.community.common.weixin.vo.recv.RecvMessage;

public interface WxRecvMsgParser {
	RecvMessage parser(Document doc) throws JDOMException;
}
