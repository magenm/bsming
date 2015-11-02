package com.qding.community.common.weixin.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.qding.community.common.weixin.vo.recv.RecvGeoMessage;
import com.qding.community.common.weixin.vo.recv.RecvMessage;

public class WxRecvGeoMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected RecvGeoMessage parser(Element root, RecvMessage msg) throws JDOMException {
		String locationX = getElementText(root, "Location_X");
		String locationY = getElementText(root, "Location_Y");
		int scale = parseInt(getElementText(root, "Scale"),0);
		String label = getElementText(root, "Label");
		
		double latitude = parseDouble(locationX, 0.0);
		double longitude = parseDouble(locationY, 0.0);
		
		return new RecvGeoMessage(msg, latitude, longitude, scale, label);
	}
	
	private double parseDouble(String val,double def) {
		try {
			return Double.parseDouble(val);
		}catch(Exception e) {
			return def;
		}
	}
	
	private int parseInt(String val,int def) {
		try {
			return Integer.parseInt(val);
		}catch(Exception e) {
			return def;
		}
	}
	

}
