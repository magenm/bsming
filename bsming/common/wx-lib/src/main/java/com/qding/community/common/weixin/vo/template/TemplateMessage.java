package com.qding.community.common.weixin.vo.template;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TemplateMessage<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6641720966460803041L;

	private String template_id;
	private String url;
	private String topcolor = "#FF0000";
	private String touser;
	private Long publicsID;

	private T data;

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Long getPublicsID() {
		return publicsID;
	}

	public void setPublicsID(Long publicsID) {
		this.publicsID = publicsID;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
