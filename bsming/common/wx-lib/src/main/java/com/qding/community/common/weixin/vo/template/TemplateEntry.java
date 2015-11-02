package com.qding.community.common.weixin.vo.template;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TemplateEntry  implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6641720966460803041L;

	private String value;
	private String color="#173177";
	
	
	

	public TemplateEntry(String value) {
		this.value=value;
	}

	public TemplateEntry(String value,String color) {
		this.value=value;
		this.color=color;
	}
	




	public TemplateEntry() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}




	public void setValue(String value) {
		this.value = value;
	}




	public String getColor() {
		return color;
	}




	public void setColor(String color) {
		this.color = color;
	}




	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
