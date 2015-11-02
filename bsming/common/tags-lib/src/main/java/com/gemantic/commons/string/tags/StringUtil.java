package com.gemantic.commons.string.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends TagSupport {
	private static final long serialVersionUID = 5220846990250064253L;
	private String value;

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	/**
	 * 获取最后一个空格之后的数字(展示银行卡账号时使用)
	 */
	public int doStartTag() throws JspException {
		if (StringUtils.isBlank(value)) {
			return super.doStartTag();
		}
		StringBuffer sb = new StringBuffer();
		char[] array = value.toCharArray();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
			if (i % 4 == 3 && i != array.length - 1) {
				sb.append(" ");
			}
		}
		try {
			pageContext.getOut()
					.write(sb.toString().substring(
							sb.toString().lastIndexOf(" ") + 1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
}