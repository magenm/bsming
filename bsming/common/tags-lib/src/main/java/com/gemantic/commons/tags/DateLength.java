package com.gemantic.commons.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.bsming.common.util.MyTimeUtil;

public class DateLength extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1322882290003545950L;
	private String date;

	private static final long Second_MILLISECOND = 1000L;
	private static final long Minute_MILLISECOND = 60 * 1000L;
	private static final long Hour_MILLISECOND = 3600 * 1000L;
	private static final long Day_MILLISECOND = 24 * 3600 * 1000L;
	private static final long WEEK_MILLISECOND = 7 * 24 * 3600 * 1000L;
	private static final long MONTH_MILLISECOND = 30 * 24 * 3600 * 1000L;
	private static final long YEAR_MILLISECOND = 365 * 24 * 3600 * 1000L;

	@Override
	public int doStartTag() throws JspException {
		String s = "";
		if (StringUtils.isBlank(date)) {

		} else {
			long time1 = Long.valueOf("" + date);


			if (time1 <= Minute_MILLISECOND) {
				s = time1 / Second_MILLISECOND + "秒";
			} else if (time1 <= Hour_MILLISECOND) {
				s = time1 / Minute_MILLISECOND + "分钟";

			} else if (time1 <= Day_MILLISECOND) {
				s = time1 / Hour_MILLISECOND + "小时";
			} else if (time1 <= WEEK_MILLISECOND) {
				s = time1 / Day_MILLISECOND + "日";

			} else if (time1 <= MONTH_MILLISECOND) {
				s = time1 / WEEK_MILLISECOND + "周";
			} else if (time1 <= YEAR_MILLISECOND) {
				s = time1 / MONTH_MILLISECOND + "月";
			} else {
				s = MyTimeUtil.convertLong2String(time1, "yyyy-MM-dd HH:mm:ss");
			}

		}

		try {
			pageContext.getOut().write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return super.doStartTag();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}