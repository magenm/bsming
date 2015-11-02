package com.bsming.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import com.bsming.common.constant.SignConstant;
import com.bsming.common.constant.TimeDateConstant;

public class CalendarUtil {
	public static DateFormat yyyyMMddFormat = new SimpleDateFormat("yyyyMMdd");

	public final static String defaultTimeFormate = "yyyy-MM-dd HH:mm:ss";

	public static Integer getDayOfDate(Date date) {
		return Integer.valueOf(yyyyMMddFormat.format(date));
	}

	public static Integer getDayOfDate(Long date) {
		Date dt = new Date();
		dt.setTime(date);
		return Integer.valueOf(yyyyMMddFormat.format(dt));
	}

	public static List<Integer> getDayListOfDateList(List<Long> dateList) {
		List<Integer> dayList = new ArrayList<Integer>();
		for (Long date : dateList) {
			dayList.add(CalendarUtil.convertTimeMillis2yyyyMMdd(date));
		}
		return dayList;
	}

	public static void trimCalendar(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}

	public static long trimTimeMillis(long timeMillis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeMillis);
		trimCalendar(c);
		return c.getTimeInMillis();
	}

	/**
	 * 精确到小时，分钟去掉
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static long trimTimeMinute(long timeMillis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeMillis);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

	/**
	 * 获取若干天之前的时间
	 * 
	 * @param time
	 *            ：时间点
	 * @param days
	 *            ：天数
	 * 
	 * @return 时间点前几天时间
	 */
	public static Long getTimeBefore(Long time, Integer days) {

		if (null == time || days == null) {
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		c.add(Calendar.DAY_OF_MONTH, -days);

		return c.getTimeInMillis();
	}

	/**
	 * 获取当日的最末毫秒数
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static long dayEndTimeMillis(long timeMillis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeMillis);
		c.add(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis() - 1);
	}

	private static Calendar standardizeDate(Calendar c) {
		trimCalendar(c);
		c.add(Calendar.DATE, 1);
		c.add(Calendar.MILLISECOND, -1);
		return c;
	}

	public static Calendar standardizeDate(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return standardizeDate(c);
	}

	public static Calendar standardizeDate(Date date) {
		return standardizeDate(date.getTime());
	}

	public static int convertTimeMillis2yyyyMMdd(long timeMillis) {
		if (TimeDateConstant.MAX_YYYYMMDD_MILLIS < timeMillis) {
			timeMillis = TimeDateConstant.MAX_YYYYMMDD_MILLIS;
		}
		String str = DateFormatUtils.format(timeMillis,
				TimeDateConstant.yyyyMMdd);
		return Integer.valueOf(str);
	}

	public static Date convertYyyyMMdd2Date(int yyyyMMdd) {
		try {
			return DateUtils.parseDate(String.valueOf(yyyyMMdd),
					new String[] { TimeDateConstant.yyyyMMdd });
		} catch (ParseException e) {
			return null;
		}
	}

	public static Long convertYyyyMMdd2TimeMillis(Integer yyyyMMdd) {
		Date date = convertYyyyMMdd2Date(yyyyMMdd);
		if (null == date) {
			return null;
		}
		return date.getTime();

	}

	public static List<Long> convertYyyyMMdd2TimeMillis(List<Integer> yyyyMMdd) {
		List<Long> result = new ArrayList<Long>();
		if (null == yyyyMMdd) {
			return result;
		}
		for (Integer day : yyyyMMdd) {
			if (null == day) {
				continue;
			}
			Date date = convertYyyyMMdd2Date(day);
			if (null == date) {
				continue;
			}
			result.add(date.getTime());
		}

		return result;

	}

	public static List<Integer> convertTimeMillis2YyyyMMdd(List<Long> dateList) {
		List<Integer> dayList = new ArrayList<Integer>();
		for (Long date : dateList) {
			Date dt = new Date();
			dt.setTime(date);
			dayList.add(Integer.valueOf(yyyyMMddFormat.format(dt)));
		}
		return dayList;
	}

	/**
	 * format Long to String date
	 * 
	 * @param dates
	 * @return
	 */
	public static Map<Long, Integer> formatStringDate(Long[] dates) {

		Map<Long, Integer> stringDates = new HashMap<Long, Integer>();
		if (null == dates || 0 >= dates.length) {
			return stringDates;
		}

		for (int i = 0; i < dates.length; i++) {
			stringDates.put(dates[i], convertTimeMillis2yyyyMMdd(dates[i]));
		}

		return stringDates;
	}

	/**
	 * 两个日期天数差（间隔）
	 * 
	 * @param after
	 *            : 后一个日期 （如: 2010-12-12）
	 * @param before
	 *            : 前一个日期 （如: 2010-12-10）
	 * 
	 * @return 两个日期间的天数差（2天）
	 */
	public static Long diffDays(Long after, Long before) {
		if (null == after || null == before) {
			return null;
		}

		return (trimTimeMillis(after) - trimTimeMillis(before))
				/ DateUtils.MILLIS_PER_DAY;
	}

	/**
	 * 某年是否闰年
	 * 
	 * @param year
	 *            年
	 * 
	 * @return
	 */
	public static final boolean isLeapYear(int year) {
		if (0 == year % 400 || (0 == year % 4 && 0 != year % 100)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 某日期是属于哪一季度的
	 * 
	 * @param date
	 *            日期
	 * 
	 * @return 季度
	 */
	public static Integer getSeasonOfDate(Date date) {
		if (null == date) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) / 3 + 1;
	}

	/**
	 * 
	 * 获取距离当前时间指定周的一周开始时间
	 * 
	 * @param weekNum
	 *            指定周数
	 * @param current
	 *            当前时间
	 * 
	 * @return 距离当前时间指定周的一周开始时间
	 */
	public static long getWeekStartDateBeforeCurrent(int weekNum, Date current) {
		// delete by lijun begin
		// return getLatestStartOfWeek(current) -
		// ((long)weekNum*7*24*60*60*1000);
		// delete by lijun end

		// add by lijun begin
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		calendar.add(Calendar.DAY_OF_MONTH, -weekNum * 7);

		return calendar.getTimeInMillis();
		// add by lijun end
	}

	/**
	 * 获取距离当前时间指定月的月开始时间
	 * 
	 * @param monthNum
	 *            指定月数
	 * @param current
	 *            当前时间
	 * 
	 * @return 距离当前时间指定月的月开始时间
	 */
	public static long getMonthStartDateBeforeCurrent(int monthNum, Date current) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);

		// calendar.set(Calendar.MONTH, -monthNum + 2);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		calendar.add(Calendar.MONTH, -monthNum);

		return calendar.getTimeInMillis();
	}

	/**
	 * 获取距离当前时间指定季度的季度开始时间
	 * 
	 * @param num
	 *            指定季度数
	 * @param current
	 *            当前时间
	 * 
	 * @return 距离当前时间指定季度的季度开始时间
	 */
	public static long getQuarterStartDateBeforeCurrent(int num, Date current) {
		Calendar calCurrent = Calendar.getInstance();
		calCurrent.setTime(current);

		int currentMonth = calCurrent.get(Calendar.MONTH) + 1;

		Calendar latestQuarter = Calendar.getInstance();

		switch (currentMonth) {
		// delete by lijun begin
		// case 1 :
		// case 2 :
		// case 3 : {latestQuarter.set(Calendar.YEAR,
		// calCurrent.get(Calendar.YEAR) - 1);
		// latestQuarter.set(Calendar.MONTH, Calendar.OCTOBER);
		// break;}
		// case 4 :
		// case 5 :
		// case 6 : {latestQuarter.set(Calendar.YEAR,
		// calCurrent.get(Calendar.YEAR));
		// latestQuarter.set(Calendar.MONTH, Calendar.JANUARY);
		// break;}
		// case 7 :
		// case 8 :
		// case 9 : {latestQuarter.set(Calendar.YEAR,
		// calCurrent.get(Calendar.YEAR));
		// latestQuarter.set(Calendar.MONTH, Calendar.APRIL);
		// break;}
		// case 10 :
		// case 11 :
		// case 12 : {latestQuarter.set(Calendar.YEAR,
		// calCurrent.get(Calendar.YEAR));
		// latestQuarter.set(Calendar.MONTH, Calendar.JULY);
		// break;}
		// delete by lijun end

		// add by lijun begin
		case 1:
		case 2:
		case 3: {
			latestQuarter.set(Calendar.YEAR, calCurrent.get(Calendar.YEAR));
			latestQuarter.set(Calendar.MONTH, Calendar.JANUARY);
			break;
		}
		case 4:
		case 5:
		case 6: {
			latestQuarter.set(Calendar.YEAR, calCurrent.get(Calendar.YEAR));
			latestQuarter.set(Calendar.MONTH, Calendar.APRIL);
			break;
		}
		case 7:
		case 8:
		case 9: {
			latestQuarter.set(Calendar.YEAR, calCurrent.get(Calendar.YEAR));
			latestQuarter.set(Calendar.MONTH, Calendar.JULY);
			break;
		}
		case 10:
		case 11:
		case 12: {
			latestQuarter.set(Calendar.YEAR, calCurrent.get(Calendar.YEAR));
			latestQuarter.set(Calendar.MONTH, Calendar.OCTOBER);
			break;
		}
		// add by lijun end
		}

		latestQuarter.set(Calendar.DAY_OF_MONTH, 1);
		latestQuarter.set(Calendar.HOUR_OF_DAY, 0);
		latestQuarter.set(Calendar.MINUTE, 0);
		latestQuarter.set(Calendar.SECOND, 0);
		latestQuarter.set(Calendar.MILLISECOND, 0);

		// delete by lijun begin
		// Calendar calQuarter = Calendar.getInstance();
		// calQuarter.set(Calendar.YEAR, latestQuarter.get(Calendar.YEAR) - (num
		// - 1)/4);
		//
		// if ((latestQuarter.get(Calendar.MONTH) + 1 - (num - 1)%4*3) > 0) {
		// calQuarter.set(Calendar.MONTH, latestQuarter.get(Calendar.MONTH) -
		// (num - 1)%4*3);
		// } else {
		// calQuarter.set(Calendar.MONTH, Calendar.JANUARY);
		// }
		//
		// calQuarter.set(Calendar.DAY_OF_MONTH, 1);
		// calQuarter.set(Calendar.HOUR_OF_DAY, 0);
		// calQuarter.set(Calendar.MINUTE, 0);
		// calQuarter.set(Calendar.SECOND, 0);
		//
		//
		// return calQuarter.getTimeInMillis();
		// delete by lijun end

		// add by lijun begin
		int subtractMonthNum = num * 3;

		latestQuarter.add(Calendar.MONTH, -subtractMonthNum);

		return latestQuarter.getTimeInMillis();
		// add by lijun end
	}

	/**
	 * 返回两个时间间隔的季度数
	 * 
	 * @param latestDate
	 *            比较时间
	 * @param current
	 *            当前时间
	 * 
	 * @return 两个时间间隔的季度数
	 */
	public static int getDiffBetweenQuarter(Date latestDate, Date current) {
		Calendar calCurrent = Calendar.getInstance();
		calCurrent.setTime(current);

		Calendar calLatest = Calendar.getInstance();
		calLatest.setTime(latestDate);

		int latestQuarterNum = getQuarterNum(latestDate);
		int currentQuarterNum = getQuarterNum(current);

		if (calCurrent.get(Calendar.YEAR) - calLatest.get(Calendar.YEAR) > 0) {
			int diff = calCurrent.get(Calendar.YEAR)
					- calLatest.get(Calendar.YEAR);
			return (diff - 1) * 4 + (currentQuarterNum - 1)
					+ (4 - latestQuarterNum + 1);
		} else {
			return currentQuarterNum - latestQuarterNum;
		}
	}

	private static int getQuarterNum(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int currentMonth = cal.get(Calendar.MONTH) + 1;
		switch (currentMonth) {
		case 1:
		case 2:
		case 3:
			return 1;
		case 4:
		case 5:
		case 6:
			return 2;
		case 7:
		case 8:
		case 9:
			return 3;
		case 10:
		case 11:
		case 12:
			return 4;
		}

		return 0;
	}

	/**
	 * 返回两个时间点间隔的月的数量
	 * 
	 * @param latestDate
	 *            比较时间
	 * @param current
	 *            当前时间
	 * 
	 * @return 两个时间点间隔的月的数量
	 */
	public static int getDiffBetweenMonth(Date latestDate, Date current) {
		Calendar calCurrent = Calendar.getInstance();
		calCurrent.setTime(current);

		Calendar calLatest = Calendar.getInstance();
		calLatest.setTime(latestDate);

		if (calCurrent.get(Calendar.YEAR) - calLatest.get(Calendar.YEAR) > 0) {
			int diff = calCurrent.get(Calendar.YEAR)
					- calLatest.get(Calendar.YEAR);
			return (12 - (calLatest.get(Calendar.MONTH) + 1) + 1)
					+ calCurrent.get(Calendar.MONTH) + 12 * (diff - 1);
		} else {
			return calCurrent.get(Calendar.MONTH)
					- calLatest.get(Calendar.MONTH);
		}
	}

	public static Calendar getCalendarByDay(String dayStr)
			throws ParseException {
		Calendar calendar = Calendar.getInstance();
		Date date = yyyyMMddFormat.parse(dayStr);
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 获取计算日，计算日的前一日，计算日的一周前日，一月前日，一季前日的yyyyMMdd
	 * 
	 * @param  checkPointTime
	 * 
	 * @return
	 */
	public static List<Integer> get5ConsensusDays(Long checkPointTime) {
		List<Integer> result = new ArrayList<Integer>(5);

		if (null == checkPointTime) {
			return result;
		}

		// this day
		Calendar cal = CalendarUtil.standardizeDate(checkPointTime);
		result.add(CalendarUtil.convertTimeMillis2yyyyMMdd(cal
				.getTimeInMillis()));

		// the previous day
		cal.add(Calendar.DATE, -TimeDateConstant.PREVIOUS_DAY_DIFF);
		result.add(CalendarUtil.convertTimeMillis2yyyyMMdd(cal
				.getTimeInMillis()));

		// the day one week before this day
		cal.add(Calendar.DATE,
				-(TimeDateConstant.DAY_WEEK_AGO_DIFF - TimeDateConstant.PREVIOUS_DAY_DIFF));
		result.add(CalendarUtil.convertTimeMillis2yyyyMMdd(cal
				.getTimeInMillis()));

		// the day one month before this day
		cal.add(Calendar.DATE,
				-(TimeDateConstant.DAY_MONTH_AGO_DIFF - TimeDateConstant.DAY_WEEK_AGO_DIFF));
		result.add(CalendarUtil.convertTimeMillis2yyyyMMdd(cal
				.getTimeInMillis()));

		// the day one season before this day
		cal.add(Calendar.DATE,
				-(TimeDateConstant.DAY_QUARTER_AGO_DIFF - TimeDateConstant.DAY_MONTH_AGO_DIFF));
		result.add(CalendarUtil.convertTimeMillis2yyyyMMdd(cal
				.getTimeInMillis()));

		return result;
	}

	public static Integer getYearOfTime(Long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);

		return cal.get(Calendar.YEAR);
	}

	/**
	 * 
	 * @param time
	 *            传入时间
	 * @param yearFormNow
	 *            这个值可正可负，某个年份的今日；
	 * @return
	 */
	public static Long getSameDayByAddParamAsYear(Long time, int yearFormNow) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + yearFormNow);

		return cal.getTimeInMillis();
	}

	public static long getEndTimeOfYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(year + 1, Calendar.JANUARY, 1, 23, 59, 59);

		return cal.getTimeInMillis() - 24 * 60 * 60 * 1000;
	}

	public static long getStartTimeOfYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, Calendar.JANUARY, 1, 0, 0, 0);

		return cal.getTimeInMillis();
	}

	public static Long getYearEndTime(Integer year) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, 11, 31, 23, 59, 59);

		return cal.getTimeInMillis();
	}

	/**
	 * 获取所有的在指定月份的数据
	 * 
	 * @param sourceTimeList
	 * @param monthList
	 * @return
	 */
	public static List<Long> getTimeListByMonths(List<Long> sourceTimeList,
			List<Integer> monthList) {
		List<Long> returnList = new ArrayList<Long>();
		for (Long sourceTime : sourceTimeList) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(sourceTime);
			if (monthList.contains(cal.get(Calendar.MONTH))) {
				returnList.add(sourceTime);
			}
		}
		return returnList;
	}

	/**
	 * 获取季度的时间点
	 * 
	 * @param startYear
	 * @param endYear
	 * @param isDesc
	 * @return
	 */
	public static Map<Integer, List<Long>> getSeasonEndTime(int startYear,
			int endYear, boolean isDesc) {
		if (endYear < startYear) {
			return new TreeMap<Integer, List<Long>>();
		}
		Comparator comp = isDesc ? Collections.reverseOrder() : null;
		Map<Integer, List<Long>> result = new TreeMap<Integer, List<Long>>(comp);

		Calendar cal = Calendar.getInstance();
		for (int year = startYear; year <= endYear; year++) {
			List<Long> timeList = new ArrayList<Long>();
			for (int j = 0; j < 4; j++) {
				switch (j) {
				case 0:
					cal.set(Calendar.YEAR, year);
					cal.set(Calendar.MONTH, Calendar.MARCH);
					cal.set(Calendar.DAY_OF_MONTH, 31);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					break;
				case 1:
					cal.set(Calendar.YEAR, year);
					cal.set(Calendar.MONTH, Calendar.JUNE);
					cal.set(Calendar.DAY_OF_MONTH, 30);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					break;
				case 2:
					cal.set(Calendar.YEAR, year);
					cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
					cal.set(Calendar.DAY_OF_MONTH, 30);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					break;
				case 3:
					cal.set(Calendar.YEAR, year);
					cal.set(Calendar.MONTH, Calendar.DECEMBER);
					cal.set(Calendar.DAY_OF_MONTH, 31);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					break;

				}
				timeList.add(cal.getTimeInMillis());
			}
			if (isDesc) {
				Collections.sort(timeList, Collections.reverseOrder());
			}
			result.put(year, timeList);
		}

		return result;
	}

	/**
	 * 获取传入的时间点的上个季度末尾的时间
	 * 
	 * @param currentSeasonEndDay
	 * @return
	 */
	public static Integer getLastSeasonEndDayYyyyMMdd(
			Integer currentSeasonEndDay) {
		if (null == currentSeasonEndDay) {
			return null;
		}
		Integer year = currentSeasonEndDay / 10000;
		Integer date = currentSeasonEndDay % 10000;
		switch (date) {
		case SignConstant.SEASON_1_MONTHDAY:
			return (year - 1) * 10000 + SignConstant.SEASON_4_MONTHDAY;
		case SignConstant.SEASON_2_MONTHDAY:
			return year * 10000 + SignConstant.SEASON_1_MONTHDAY;
		case SignConstant.SEASON_3_MONTHDAY:
			return year * 10000 + SignConstant.SEASON_2_MONTHDAY;
		case SignConstant.SEASON_4_MONTHDAY:
			return year * 10000 + SignConstant.SEASON_3_MONTHDAY;
		}
		return null;
	}

	/**
	 * 取上一年的时间
	 * 
	 * @param currentSeasonEndDay
	 * @return
	 */
	public static Integer getLastYearDay(Integer currentSeasonEndDay) {
		if (null == currentSeasonEndDay) {
			return null;
		}
		Integer year = currentSeasonEndDay / 10000;
		Integer date = currentSeasonEndDay % 10000;
		return (year - 1) * 10000 + date;
	}

	/**
	 * 取某个时间段之间各月末最后一天
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Long> getEndDayOfMonthBetweenTime(Long start, Long end) {
		List<Long> timeList = new ArrayList<Long>();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(start);

		int lastDate = cal.getActualMaximum(Calendar.DATE);
		cal.set(Calendar.DATE, lastDate);

		while (end.compareTo(cal.getTimeInMillis()) >= 0) {
			timeList.add(cal.getTimeInMillis());

			cal.add(Calendar.MONTH, 1);
		}

		return timeList;
	}

	/**
	 * 取某个时间点所在月的第一天
	 * 
	 * @param day
	 * @return
	 */
	public static Long getMonthStartDate(Long day) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(day);

		int start = cal.getActualMinimum(Calendar.DATE);
		cal.set(Calendar.DATE, start);

		return cal.getTimeInMillis();
	}

	/**
	 * 取某个时间点所在月的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Long getMonthEndDate(Long day) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(day);

		int end = cal.getActualMaximum(Calendar.DATE);
		cal.set(Calendar.DATE, end);

		return cal.getTimeInMillis();
	}

	public static String formateDate(Long time, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(null == time ? 0L : new Date(time));
	}

	// 获得系统当前时间
	public static int getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minute = calendar.get(Calendar.MINUTE);
		Integer second = calendar.get(Calendar.SECOND);
		String hms = hour.toString() + minute.toString() + second.toString();
		return Integer.parseInt(hms);
	}
}
