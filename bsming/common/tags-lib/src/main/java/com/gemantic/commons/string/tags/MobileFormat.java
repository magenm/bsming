package com.gemantic.commons.string.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.bsming.common.util.MyMathUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MobileFormat extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -77919510811681606L;

	private static final Log log = LogFactory.getLog(MobileFormat.class);

	/**
	 * 
	 */

	private String value;

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int doStartTag() throws JspException {
		if (StringUtils.isBlank(value) || 0 == MyMathUtil.getLong(value)) {
			return super.doStartTag();
		}
		try {
			pageContext.getOut().write(mobileStyle(value));
		} catch (IOException e) {
			log.info(null, e);
		}
		return super.doStartTag();
	}

	/**
	 * 修改手机号码的格式
	 * 
	 * @param str
	 * 
	 * @return 改编后的手机格式
	 */
	public static String mobileStyle(String mobile) {
		if (StringUtils.isBlank(mobile) || mobile.length() != 11) {
			return "";
		}
		// String regxp = "/^\\d{3}(?<rep>\\d{4})\\d{4}$/";
		StringBuffer sb = new StringBuffer(mobile);
		sb.replace(3, 7, "****");
		return sb.toString();
	}

}