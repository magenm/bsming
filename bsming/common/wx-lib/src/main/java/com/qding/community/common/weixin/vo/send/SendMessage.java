package com.qding.community.common.weixin.vo.send;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;

import com.qding.community.common.weixin.parser.WxMsgKit;
import com.qding.community.common.weixin.vo.Message;

public class SendMessage extends Message {
	private static final Log log = LogFactory.getLog(WxMsgKit.class);
	// FuncFlag 位0x0001被标志时，星标刚收到的消息。
	private boolean star;

	public SendMessage(String toUserName, String fromUserName, String createTime, String msgType, boolean star) {
		super(toUserName, fromUserName, createTime, msgType);
		this.star = star;
	}

	public SendMessage(Message msg) {
		this(msg.getToUserName(), msg.getFromUserName(), msg.getCreateTime(), msg.getMsgType(), false);
	}

	public SendMessage(SendMessage msg) {
		this(msg.getToUserName(), msg.getFromUserName(), msg.getCreateTime(), msg.getMsgType(), msg.isStar());
	}

	public boolean isStar() {
		return star;
	}

	public void setStar(boolean star) {
		this.star = star;
	}

	public Document toDocument() {
		Document doc = new Document();
		Element root = new Element("xml");
		doc.setRootElement(root);

		createElement(root, "ToUserName", getToUserName());
		createElement(root, "FromUserName", getFromUserName());
		createElement(root, "CreateTime", getCreateTime());
		createElement(root, "MsgType", getMsgType());
		createElement(root, "FuncFlag", isStar() ? "1" : "0");

		return doc;
	}

	@SuppressWarnings("unchecked")
	protected static Element createElement(Element parent, String name, String value) {
		Element elem = new Element(name);
		elem.setText(value);
		parent.getChildren().add(elem);
		return elem;
	}

	public static void main(String[] args) throws TransformerException {

		Document doc = new Document();
		Element root = new Element("xml");
		doc.setRootElement(root);

		createElement(root, "ToUserName", "2");
		createElement(root, "FromUserName", "2");
		createElement(root, "CreateTime", "2");
		createElement(root, "MsgType", "2");
		createElement(root, "FuncFlag", "2");

		log.info(doc.toString());
	}

	

}
