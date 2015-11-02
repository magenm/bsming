package com.qding.community.common.weixin.vo.send;

import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

public class SendNewsMessage extends SendMessage {

	/**
	 * 图文消息个数，限制为10条以内
	 */
	private Integer articleCount;
	/**
	 * 多条图文消息信息，默认第一个item为大图
	 */
	private List<SendNewsMessageItem> items = new LinkedList<SendNewsMessageItem>();

	public SendNewsMessage(SendMessage msg) {
		super(msg);
		setMsgType("news");
	}

	public void setItems(List<SendNewsMessageItem> items) {
		this.items = items;
	}

	public SendNewsMessage addItem(String title, String description, String picUrl, String url) {
		if (items.size() >= 10) {
			throw new IllegalArgumentException("只能接受最多10个item...");
		}
		items.add(new SendNewsMessageItem(title, description, picUrl, url));
		return this;
	}

	@Override
	public Document toDocument() {
		Document doc = super.toDocument();
		Element root = doc.getRootElement();
		createElement(root, "ArticleCount", String.valueOf(items.size()));
		Element articles = createElement(root, "Articles", "");
		for (SendNewsMessageItem item : items) {
			Element i = createElement(articles, "item", "");
			createElement(i, "Title", item.getTitle());
			createElement(i, "Description", item.getDescription());
			createElement(i, "PicUrl", item.getPicUrl());
			createElement(i, "Url", item.getUrl());
		}
		return doc;
	}
}