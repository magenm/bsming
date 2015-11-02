package com.gemantic.commons.string.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

public class StrPointReplace extends TagSupport {
	private static final long serialVersionUID = -5288071092803942157L;
	private String string;// 字符串
	private int startCnt;// 星号的个数

	public void setString(String string) {
		this.string = string;
	}

	public void setStartCnt(int startCnt) {
		this.startCnt = startCnt;
	}

	@Override
	public int doStartTag() throws JspException {
		if (StringUtils.isBlank(string)) {
			return super.doStartTag();
		}
		int length = string.length();
		try {
			if (length <= 2) {
				pageContext.getOut().write(string.substring(0, 1) + "*");
			}
			StringBuffer cmd = new StringBuffer();
			for (int i = 0; i < startCnt; i++) {
				cmd.append("*");
			}
			pageContext.getOut().write(
					string.substring(0, 1) + cmd.toString()
							+ string.substring(startCnt + 1, length));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

}
