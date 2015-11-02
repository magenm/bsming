package com.qding.community.common.weixin.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.qding.commnunity.common.weixin.service.impl.WeiXinServiceImpl;
import com.qding.community.common.weixin.service.WeiXinService;
import com.qding.community.common.weixin.vo.template.TemplateEntry;
import com.qding.community.common.weixin.vo.template.TemplateMessage;
import com.qding.community.common.weixin.vo.template.message.CSNotificationsChanges;

public class TemplateUtil {

	private static final Log log = LogFactory.getLog(TemplateUtil.class);

	static public String convertCSNotificationsChanges2Jsons(TemplateMessage<CSNotificationsChanges> templateMessage) {

		Gson gson = new GsonBuilder().create();

		return gson.toJson(templateMessage, new TypeToken<TemplateMessage<CSNotificationsChanges>>() {
		}.getType());

	};

	static public TemplateMessage<CSNotificationsChanges> convertJsons2CSNotificationsChanges(String content) {

		Gson gson = new GsonBuilder().create();
		return gson.fromJson(content, new TypeToken<TemplateMessage<CSNotificationsChanges>>() {
		}.getType());

	};

	public static void main(String[] args) {
		TemplateMessage templateMessage = new TemplateMessage();
		CSNotificationsChanges cs = new CSNotificationsChanges();

		cs.setDetail(new TemplateEntry("test"));

		cs.setLocation(new TemplateEntry("test"));
		cs.setReason(new TemplateEntry("test"));
		cs.setRemark(new TemplateEntry("test"));
		cs.setService(new TemplateEntry("test"));
		cs.setTime(new TemplateEntry("test"));
		cs.setTitle(new TemplateEntry("test"));

		templateMessage.setData(cs);
		// templateMessage.setTemplate_id("0SNrqK4TEdVmkE-N3gSA9IMsUr8WGgCc5uuxkvzLGK8");
		templateMessage.setTemplate_id("3333");
		templateMessage.setTopcolor("#FF0000");
		templateMessage.setTouser("ovK7IjpU1x1hXEVd57DpTfYSPBqM");
		templateMessage.setUrl("");

		String result = TemplateUtil.convertCSNotificationsChanges2Jsons(templateMessage);
		log.info("result is " + result);
	}

}
