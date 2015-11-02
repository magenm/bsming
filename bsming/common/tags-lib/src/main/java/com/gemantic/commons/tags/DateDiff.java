package com.gemantic.commons.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;



public class DateDiff extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1322882290003545950L;
	private String date1;
	private String date2;

	private static final String WEEK = "week";
	private static final String MONTH = "month";
	private static final String SEASON = "season";
	private static final String HALF_YEAR = "halfYear";
	private static final String YEAR = "year";
	private static final String MoreYEAR = "myear";
	

	private static final long WEEK_MILLISECOND = 7 * 24 * 3600 * 1000L;
	private static final long MONTH_MILLISECOND = 30 * 24 * 3600 * 1000L;
	private static final long SEASON_MILLISECOND = 90 * 24 * 3600 * 1000L;
	private static final long HALF_YEAR_MILLISECOND = 183 * 24 * 3600 * 1000L;
	private static final long YEAR_MILLISECOND = 365 * 24 * 3600 * 1000L;

	@Override
	public int doStartTag() throws JspException {
		String s = "";
		if (StringUtils.isBlank(date1) || StringUtils.isBlank(date2)) {

		} else {
			long time1 = Long.valueOf("" + date1);
			long time2 = Long.valueOf("" + date2);

			long time_diff = Math.abs(time1 - time2);

			

			if (time_diff <= WEEK_MILLISECOND) {
				s = WEEK;
			} else if (time_diff <= MONTH_MILLISECOND) {
				s = MONTH;
			} else if (time_diff <= SEASON_MILLISECOND) {
				s = SEASON;
			} else if (time_diff <= HALF_YEAR_MILLISECOND) {
				s = HALF_YEAR;
			} else if (time_diff <= YEAR_MILLISECOND) {
				s = YEAR;
			} else {
				s = MoreYEAR;
			}

		}

		try {
			pageContext.getOut().write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return super.doStartTag();
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}
}