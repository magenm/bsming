package com.qding.community.common.weixin.vo.template.message;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.qding.community.common.weixin.vo.template.CommonTemplate;
import com.qding.community.common.weixin.vo.template.TemplateEntry;

public class CSNotificationsChanges implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6641720966460803041L;

	private TemplateEntry title;	
	private TemplateEntry service;
	private TemplateEntry detail;
	private TemplateEntry time;
	private TemplateEntry location;
	private TemplateEntry reason;
	private TemplateEntry remark;
	
	
	
	
	



	public TemplateEntry getTitle() {
		return title;
	}








	public void setTitle(TemplateEntry title) {
		this.title = title;
	}















	public TemplateEntry getService() {
		return service;
	}








	public void setService(TemplateEntry service) {
		this.service = service;
	}








	public TemplateEntry getDetail() {
		return detail;
	}








	public void setDetail(TemplateEntry detail) {
		this.detail = detail;
	}








	public TemplateEntry getTime() {
		return time;
	}








	public void setTime(TemplateEntry time) {
		this.time = time;
	}








	public TemplateEntry getLocation() {
		return location;
	}








	public void setLocation(TemplateEntry location) {
		this.location = location;
	}








	public TemplateEntry getReason() {
		return reason;
	}








	public void setReason(TemplateEntry reason) {
		this.reason = reason;
	}








	public TemplateEntry getRemark() {
		return remark;
	}








	public void setRemark(TemplateEntry remark) {
		this.remark = remark;
	}








	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
