package com.qding.community.common.weixin.parser;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.qding.community.common.weixin.vo.recv.RecvMessage;

public abstract class WxRecvMsgBaseParser implements WxRecvMsgParser {

	@Override
	public RecvMessage parser(Document doc) throws JDOMException {
		try {
			new XMLOutputter().output(doc, System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Element root = doc.getRootElement();
		String toUserName = getElementText(root, "ToUserName");
		String fromUserName = getElementText(root, "FromUserName");
		String createTime = getElementText(root, "CreateTime");
		String msgType = getElementText(root, "MsgType");
		String msgId = getElementText(root, "MsgId");
		
		return parser(root,new RecvMessage(toUserName,fromUserName,createTime,msgType,msgId));
	}
	
	protected abstract RecvMessage parser(Element root,RecvMessage msg) throws JDOMException;
	
	protected String getElementText(Element elem,String xpath) throws JDOMException {
		Text text = ((Text)XPath.selectSingleNode(elem, xpath+"/text()"));
		if(null == text) {
			return "";
		}
		return text.getText();
	}

}
