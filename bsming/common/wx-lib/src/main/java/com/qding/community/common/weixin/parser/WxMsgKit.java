package com.qding.community.common.weixin.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.qding.community.common.weixin.constant.MessageType;
import com.qding.community.common.weixin.vo.recv.RecvMessage;
import com.qding.community.common.weixin.vo.send.SendMessage;

public final class WxMsgKit {

	private static final Log log = LogFactory.getLog(WxMsgKit.class);

	private static final Map<String, WxRecvMsgParser> recvParserMap = new HashMap<String, WxRecvMsgParser>();

	static {
		// 文本消息解析程序
		recvParserMap.put(MessageType.MESSAGE_TYPE_TEXT, new WxRecvTextMsgParser());
		// 链接消息解析程序
		recvParserMap.put(MessageType.MESSAGE_TYPE_LINK, new WxRecvLinkMsgParser());
		// 地址消息解析程序
		recvParserMap.put(MessageType.MESSAGE_TYPE_LOCATION, new WxRecvGeoMsgParser());
		// 图片消息解析程序
		recvParserMap.put(MessageType.MESSAGE_TYPE_IMAGE, new WxRecvPicMsgParser());
		// 事件消息解析程序
		recvParserMap.put(MessageType.MESSAGE_TYPE_EVENT, new WxRecvEventMsgParser());
	}

	public static RecvMessage parse(InputStream in) throws JDOMException, IOException {
		//log.info(convertStreamToString(in));
		
		Document dom = new SAXBuilder().build(in);
		log.info(dom.toString() + " get message is " + dom);

		Element msgType = dom.getRootElement().getChild("MsgType");
		if (null != msgType) {
			String txt = msgType.getText().toLowerCase();
			WxRecvMsgParser parser = recvParserMap.get(txt);
			if (null != parser) {
				return parser.parser(dom);
			} else {
				System.out.println(txt);
			}
		}
		return null;
	}

	public static Document parse(SendMessage msg) throws JDOMException {
		return msg.toDocument();
	}

	public static void main(String[] args) {
		Document doc = new Document();
		Element root = new Element("xml");
		doc.setRootElement(root);

		createElement(root, "ToUserName", "2");
		createElement(root, "FromUserName", "2");
		createElement(root, "CreateTime", "2");
		createElement(root, "MsgType", "2");
		createElement(root, "FuncFlag", "2");

		log.info(doc);
	}

	static Element createElement(Element parent, String name, String value) {
		Element elem = new Element(name);
		elem.setText(value);
		parent.getChildren().add(elem);
		return elem;
	}

	public static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		StringBuilder sb = new StringBuilder();

		String line = null;

		try {

			while ((line = reader.readLine()) != null) {

				sb.append(line + "/n");

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				is.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		return sb.toString();

	}

}
