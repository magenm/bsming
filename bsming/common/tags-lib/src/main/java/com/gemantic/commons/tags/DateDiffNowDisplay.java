package com.gemantic.commons.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

public class DateDiffNowDisplay extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1322882290003545950L;
	private String date;

	public static final long Second_MILLISECOND = 1000L;
	public static final long Minute_MILLISECOND = 60 * 1000L;
	public static final long Hour_MILLISECOND = 3600 * 1000L;
	public static final long Day_MILLISECOND = 24 * 3600 * 1000L;
	public static final long WEEK_MILLISECOND = 7 * 24 * 3600 * 1000L;
	public static final long MONTH_MILLISECOND = 30 * 24 * 3600 * 1000L;
	public static final long YEAR_MILLISECOND = 365 * 24 * 3600 * 1000L;

	@Override
	public int doStartTag() throws JspException {
		StringBuilder s = new StringBuilder();
		if(StringUtils.isNotBlank(date)){
			long time1 = Long.valueOf("" + date);
			long time2 = System.currentTimeMillis();
            long time_diff = Math.abs(time2 - time1);

			if (time_diff <= Minute_MILLISECOND) {
				s.append(time_diff / Second_MILLISECOND).append("秒");
			} else if (time_diff <= Hour_MILLISECOND) {
				s.append(time_diff / Minute_MILLISECOND ).append( "分钟");
				s.append((time_diff % Minute_MILLISECOND)/Second_MILLISECOND).append( "秒");

			} else if (time_diff <= Day_MILLISECOND) {
				s.append(time_diff / Hour_MILLISECOND ).append( "小时");
				long minute = time_diff % Hour_MILLISECOND;
				s.append(minute / Minute_MILLISECOND ).append( "分钟");
				s.append((minute % Minute_MILLISECOND )/Second_MILLISECOND).append("秒");
				
			} else {
				s.append(time_diff / Day_MILLISECOND ).append( "日");
                long hour = time_diff % Day_MILLISECOND;
				long minute = hour % Hour_MILLISECOND;
                s.append(hour/Hour_MILLISECOND).append("小时");
                s.append(minute/Minute_MILLISECOND).append("分钟");
                s.append((minute % Minute_MILLISECOND)/Second_MILLISECOND).append("秒");
                
			}

		}

		try {
			pageContext.getOut().write(s.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return super.doStartTag();
	}

//	public static void main(String[] args) throws JspException {
//		DateDiffNowDisplay dd = new DateDiffNowDisplay();
//		dd.setDate(String.valueOf(DateUtils.addSeconds(new java.util.Date(), 3).getTime()));
////		dd.setDate(String.valueOf(CalendarUtil.dayEndTimeMillis(DateUtils.addHours(new java.util.Date(), 3).getTime())));
//		dd.doStartTag();
//	}
	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}