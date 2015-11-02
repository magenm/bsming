package com.qding.community.common.weixin.vo.template;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CommonTemplate<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4759497113307146067L;

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
