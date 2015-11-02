package com.bsming.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bsming.common.constant.CommonConstant;
import com.bsming.common.constant.NumberConstants;

public class MyTimeUtil {
	private static final Log log = LogFactory.getLog(MyTimeUtil.class);
	private static final String dateFormat1 = "yyyy-MM-dd HH:mm:ss";

	public static Date getDatebyString(String day, String dataFormat) {
		if (org.apache.commons.lang3.StringUtils.isBlank(day)) {
			return null;
		}
		if (org.apache.commons.lang3.StringUtils.isBlank(dataFormat)) {
			dataFormat = dateFormat1;
		}
		DateFormat format = new SimpleDateFormat(dataFormat);
		Date date = null;
		try {
			date = format.parse(day);
		} catch (ParseException e) {
			log.error("error@parse date", e);
			e.printStackTrace();
		}
		return date;
	}

	public static Long getTodayZeroTimeMillions() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 0);
		long todayZeroTime = calendar.getTimeInMillis();
		return todayZeroTime;

	}

	public static Date getTodayZeroTimeDate() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 0);
		Date todayZeroTime = calendar.getTime();
		return todayZeroTime;

	}

	public static void main(String[] args) throws ParseException {
		// Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.HOUR_OF_DAY, 0);
		// calendar.set(Calendar.MINUTE, 0);
		// calendar.set(Calendar.SECOND, 0);
		// calendar.set(Calendar.MILLISECOND, 0);
		// calendar.add(Calendar.DAY_OF_MONTH, 0);
		// System.out.println(calendar.getTime());
		//
		// int hour = MyTimeUtil.convertHour(System.currentTimeMillis());
		// System.out.println(hour);
		// log.info(hour);
		//
		// Long t = MyTimeUtil.convertString2Long("2010-03-24 00:00:00",
		// TimeDateConstant.yyyy_MM_dd_HH_mm_ss);
		// System.out.println(t);
		// t = 1272431913562L;
		// log.info(t);
		//
		// String time = MyTimeUtil.convertLong2String(t,
		// TimeDateConstant.yyyy_MM_dd_HH_mm_ss);
		// System.out.println(time);
		// log.info(time);
		//
		// Long p = 1236649274000L;
		// Long s = 1242057600000L;
		// log.info(p);
		// time = MyTimeUtil.convertLong2String(p,
		// TimeDateConstant.yyyy_MM_dd_HH_mm_ss);
		// log.info(time);
		//
		// log.info(s);
		// time = MyTimeUtil.convertLong2String(s,
		// TimeDateConstant.yyyy_MM_dd_HH_mm_ss);
		// log.info(time);
		// List<Long> timeList = new ArrayList<Long>();
		// Long date = new Date().getTime();
		// timeList.add(date);
		// timeList.add(CalendarUtil.getTimeBefore(date, 10));
		// timeList.add(CalendarUtil.getTimeBefore(date, 360));
		// timeList.add(CalendarUtil.getTimeBefore(date, 370));
		// timeList.add(CalendarUtil.getTimeBefore(date, 720));
		// timeList.add(CalendarUtil.getTimeBefore(date, 730));
		// List<Long> returnTime = getLastestTimeAtEachYear(timeList,0);
		// System.out.println(returnTime);
		// System.out.println(timeList.subList(0, 0));
		// System.out.println(timeList.subList(0, 1));

		List<Long> sourceTimeList = new ArrayList<Long>();
		for (Long i = 1L; i < 11L; i++) {
			sourceTimeList.add(i);
		}
		List<Long> refTimeList = new ArrayList<Long>();
		refTimeList.add(3L);
		refTimeList.add(5L);
		refTimeList.add(7L);
		refTimeList.add(8L);
		// Map<Long,Long> timeMap =
		// getMostEqualsTime(sourceTimeList,refTimeList,CommonConstant.LARGER);
		Map<Long, Long> timeMap = getMostEqualsTime(sourceTimeList, refTimeList, CommonConstant.SMALLER);
		for (Entry<Long, Long> entry : timeMap.entrySet()) {
			System.out.println(entry.getKey() + " == " + entry.getValue());
		}

		log.info(MyTimeUtil.convertHHHour(System.currentTimeMillis()));

	}

	public static String convertLong2String(Long time, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date myDate = new Date(time);
		return formatter.format(myDate);
	}

	public static Integer convertHour(Long createAt) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(createAt);

		return cal.get(Calendar.HOUR);
	}

	public static Integer convertHHHour(Long createAt) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(createAt);

		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static Long convertString2Long(String time, String format) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = formatter.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	public static Long convertDate2Long(Date time) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		return calendar.getTimeInMillis();

	}

	public static int getUnit(String time, String format, int circle) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = formatter.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(circle);

	}

	public static Date convertLong2Date(Long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.getTime();
	}

	/**
	 * 默认为0000
	 * 
	 * @return
	 */
	public static String getYearByCurrentTime() {
		String lastYear = "0000";

		lastYear = MyTimeUtil.convertLong2String(System.currentTimeMillis(), "yyyy");

		return lastYear;
	}

	/**
	 * 获取相对于当前时间提前固定天数的0时的时间毫秒值
	 * 
	 * @param preday
	 *            提前的天数
	 * @return
	 */
	public static Long getPreZeroTimeMillions(int preday) {
		Calendar cal = Calendar.getInstance();
		// MOD BY Lijun begin
		// cal.setTimeInMillis(System.currentTimeMillis() - 86400000*preday) ;
		cal.add(Calendar.DAY_OF_YEAR, -preday);
		// MOD BY Lijun end
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 获取相对于当前时间提前固定天数的23时59:59的时间毫秒值
	 * 
	 * @param preday
	 *            提前的天数
	 * @return
	 */
	public static Long getPreEndTimeMillions(int preday) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -preday);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTimeInMillis();
	}

	/**
	 * 根据传入的Millions时间，得到0点的Millions
	 * 
	 * @param begin
	 * @return
	 */
	public static Date getDateZeroTimeMillions(Long begin) {
		Calendar calendar = Calendar.getInstance();
		Date value = new Date(begin);
		try {
			calendar.setTime(value);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} catch (Exception e) {
			log.error("getDateZeroTimeMillions error", e);
		}
		return calendar.getTime();
	}

	/**
	 * 得到传入时间，年底12月31日的Date,0点0分0秒的Date
	 * 
	 * @param time
	 * @return
	 */
	public static Date getYearEndDate(Long time) {
		if (null == time || time.equals(0)) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		Date value = new Date(time);
		try {
			calendar.setTime(value);
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} catch (Exception e) {
			log.error("getYearEndDate error", e);
		}
		return calendar.getTime();
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static Integer getYearEndInteger(Long time) {
		if (null == time || time.equals(0)) {
			return null;
		}
		SimpleDateFormat formate = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date value = new Date(time);
		try {
			calendar.setTime(value);
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 12);
		} catch (Exception e) {
			log.error("getYearEndInteger error", e);
		}
		return MyMathUtil.getInteger(formate.format(calendar.getTime()));
	}

	public static Date get2YearsAgoDate(Long time) {
		String day = MyTimeUtil.convertLong2String(time, "yyyy") + "0101";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		try {
			date = formatter.parse(day);
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, -2);
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} catch (ParseException e) {
			log.error("get2YearsAgoDate error", e);
		}
		return calendar.getTime();
	}

	/**
	 * 根据传入的时间，得到这个时间所在年的1月1日0点0分0秒的日期类型
	 * 
	 * @param time
	 * @return
	 */
	public static Date getYearBeginDayZeroTimeDate(Long time) {
		if (null == time || time.equals(0)) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		Date value = new Date(time);
		try {
			calendar.setTime(value);
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} catch (Exception e) {
			log.error("getDateZeroTimeMillions error", e);
		}
		return calendar.getTime();
	}

	/**
	 * 根据传入的时间，得到这个时间所在年的12月31日0点0分0秒的日期类型
	 * 
	 * @param time
	 * @return
	 */
	public static Date getYearEndDayZeroTimeDate(Long time) {
		if (null == time || time.equals(0)) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		Date value = new Date(time);
		try {
			calendar.setTime(value);
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} catch (Exception e) {
			log.error("getDateZeroTimeMillions error", e);
		}
		return calendar.getTime();
	}

	/**
	 * 根据传入的Millions时间，得到23点59分59秒的Millions
	 * 
	 * @param end
	 * @return
	 */
	public static Date getDateEndTimeMillions(Long end) {
		Calendar calendar = Calendar.getInstance();
		Date value = new Date(end);
		try {
			calendar.setTime(value);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
		} catch (Exception e) {
			log.error("getLongEndTimeMillions error", e);
		}
		return calendar.getTime();
	}

	public static String convertDate2String(Date date, String pattern) {

		if (date == null) {
			log.error("date is null; pattern = " + pattern);
			return StringUtils.EMPTY;
		}

		return convertLong2String(date.getTime(), pattern);
	}

	/**
	 * 根据给出的范围截取日期,暂时只支持月日年，如果需要其他的，需要再修改 比如 2010-10-10
	 * 截取到年为2010,截取到月为2010-10,截取到日为2010-10-10....
	 * 由于format的类型不一定，所以需要借助Calendar来去各个字段
	 * 
	 * @param time
	 *            ：真实的时间值
	 * @param format
	 *            ：:现有的时间格式；
	 * @param circle
	 *            ：要取的范围
	 * @return
	 * @throws ParseException
	 */
	public static String cutDate(String time, String format, int circle) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = formatter.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String month;
		switch (circle) {
		case Calendar.YEAR: // 1
			return String.valueOf(calendar.get(Calendar.YEAR));
		case Calendar.MONTH: // 2
			month = calendar.get(Calendar.MONTH) < 9 ? "0" + (calendar.get(Calendar.MONTH) + 1) : "" + (calendar.get(Calendar.MONTH) + 1);
			return calendar.get(Calendar.YEAR) + month;
		case Calendar.DAY_OF_MONTH: // 5
			month = calendar.get(Calendar.MONTH) < 9 ? "0" + (calendar.get(Calendar.MONTH) + 1) : "" + (calendar.get(Calendar.MONTH) + 1);
			String day = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : ""
					+ calendar.get(Calendar.DAY_OF_MONTH);
			return calendar.get(Calendar.YEAR) + month + day;
		default:
			return null;
		}
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
	public static Integer diffDays(Long after, Long before) {
		if (after == null || before == null)
			return -1;

		Calendar af = Calendar.getInstance();
		af.setTime(new Date(after));
		CalendarUtil.trimCalendar(af);

		Calendar bf = Calendar.getInstance();
		bf.setTime(new Date(before));
		CalendarUtil.trimCalendar(bf);

		Long diff = (af.getTimeInMillis() - bf.getTimeInMillis()) / (24 * 60 * 60 * 1000);
		return new Integer(diff.toString());
	}

	/**
	 * 按照yyyy-MM-dd格式生成Date对象
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateZeroTime(String date) {
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Date value = new Date();
		try {
			value = formate.parse(date);
		} catch (ParseException e) {
			log.error("got error when parse dateString:", e);
		}
		return value;
	}

	/**
	 * 按照yyyy-MM-dd格式生成Date对象，23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateEndTime(String date) {
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date value = new Date();
		try {
			value = formate.parse(date);
			calendar.setTime(value);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
		} catch (ParseException e) {
			log.error("got error when parse dateString:", e);
		}
		return calendar.getTime();
	}

	/**
	 * 根据yyyyMMddHHmmss格式，得到日期的长整型
	 * 
	 * @return
	 */
	public static Long getTimeInyyyyMMddHHmmss() {
		SimpleDateFormat fullDate = new SimpleDateFormat("yyyyMMddHHmmss");
		Long updateTime = MyMathUtil.getLong(fullDate.format(new Date()));

		return updateTime;
	}

	/**
	 * 根据yyyyMMdd格式，得到日期的长整型
	 * 
	 * @return
	 */
	public static Long getTimeInyyyyMMdd() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Long updateTime = MyMathUtil.getLong(dateFormat.format(date));
		return updateTime;
	}

	/**
	 * 从time list 中获取每个年份中的最近时间
	 * 
	 * @param timeList
	 *            时间列表
	 * @param yearCount
	 *            获取的年份个数，如果传值则按数量取，如果传值为null或者零，则取所有的；
	 * @param sortType
	 *            排序方式
	 */
	public static List<Long> getLastestTimeAtEachYear(List<Long> timeList, Integer yearCount, String sortType) {
		if (CollectionUtils.isEmpty(timeList)) {
			return new ArrayList<Long>();
		}
		if (null == yearCount || yearCount.compareTo(NumberConstants.INTEGER_ZERO) <= 0) {
			yearCount = Integer.MAX_VALUE;
		}
		List<Long> returnTimeList = new ArrayList<Long>();
		// 1:排序
		List<Long> notNullTimeList = MyListUtil.removeAllNullValue(timeList);
		if (sortType.equals(CommonConstant.SORT_CRITERIA_ASC)) {
			Collections.sort(notNullTimeList);
		} else {
			Collections.sort(notNullTimeList, Collections.reverseOrder());
		}
		// 2：取不同的年份的最新的时间点
		Integer recordCount = NumberConstants.INTEGER_ZERO;
		Integer currentYear = NumberConstants.INTEGER_ZERO;
		for (Long time : notNullTimeList) {
			Integer year = CalendarUtil.getYearOfTime(time);
			if (currentYear.compareTo(year) == NumberConstants.INTEGER_ZERO) {
				continue;
			}
			currentYear = year;
			returnTimeList.add(time);
			recordCount++;
			if (recordCount.compareTo(yearCount) >= 0) {
				break;
			}
		}

		return returnTimeList;
	}

	/**
	 * 
	 * @param dateList
	 *            日期
	 * @param count
	 * @param sortType
	 * @return
	 */
	public static List<Long> getCountOrderTimeList(List<Long> dateList, Integer count, String sortType) {
		if (CollectionUtils.isEmpty(dateList) || null == count || StringUtils.isBlank(sortType)) {
			return new ArrayList<Long>();
		}
		List<Long> notNullTimeList = MyListUtil.removeAllNullValue(dateList);
		if (sortType.equals(CommonConstant.SORT_CRITERIA_DESC)) {
			Collections.sort(notNullTimeList, Collections.reverseOrder());
		}
		if (sortType.equals(CommonConstant.SORT_CRITERIA_ASC)) {
			Collections.sort(notNullTimeList, Collections.reverseOrder());
		}
		Integer size = dateList.size();
		if (size.compareTo(count) <= 0) {
			return notNullTimeList;
		}
		List<Long> returnTimeList = new ArrayList<Long>(notNullTimeList.subList(NumberConstants.INTEGER_ZERO, count));
		return returnTimeList;
	}

	/**
	 * TODO:将来可以移动到commonUtil，用泛型来做 排序后，按照顺序来判断
	 * 取较大的值,两个队列都用升序，取较小的值，两个队列都用降序,这样一次循环就能完成所有比较。
	 * 
	 * @param sourceTimeList
	 *            源时间列表
	 * @param referenceTimeList
	 * @param largerOrSmaller
	 * @return 不包含参照时间点本身
	 * 
	 */
	public static <T extends Comparable> Map<T, T> getMostEqualsTime(List<T> sourceTimeList, List<T> referenceTimeList, String largerOrSmaller) {
		if (CollectionUtils.isEmpty(sourceTimeList) || CollectionUtils.isEmpty(referenceTimeList)) {
			return new HashMap<T, T>();
		}
		Map<T, T> timeMap = new HashMap<T, T>();
		boolean flag = false;
		if (largerOrSmaller.equals(CommonConstant.SMALLER)) {
			Collections.sort(sourceTimeList, Collections.reverseOrder());
			Collections.sort(referenceTimeList, Collections.reverseOrder());
			flag = true;
		}
		if (largerOrSmaller.equals(CommonConstant.LARGER)) {
			Collections.sort(sourceTimeList);
			Collections.sort(referenceTimeList);
		}

		int index = 0;
		int totalRefSize = referenceTimeList.size();
		int totalSorSize = sourceTimeList.size();
		for (int j = 0; j < totalSorSize; j++) {// TODO 将来用下标可以处理包含参照时间点本身
			if (index >= totalRefSize) {
				break;
			}
			T sourceTime = sourceTimeList.get(j);
			T referenceTime = referenceTimeList.get(index);

			if (flag) {
				if (referenceTime.compareTo(sourceTime) > NumberConstants.INTEGER_ZERO) {
					timeMap.put(referenceTime, sourceTime);
					++index;
					if (index >= totalRefSize) {
						break;
					}
					T nextReferenTime = referenceTimeList.get(index);
					while (index < totalRefSize && nextReferenTime.compareTo(sourceTime) > NumberConstants.INTEGER_ZERO) {
						timeMap.put(referenceTime, sourceTime);
						++index;
					}
				}
			} else {
				if (referenceTime.compareTo(sourceTime) < NumberConstants.INTEGER_ZERO) {
					timeMap.put(referenceTime, sourceTime);
					++index;
					if (index >= totalRefSize) {
						break;
					}
					T nextReferenTime = referenceTimeList.get(index);
					while (index < totalRefSize && nextReferenTime.compareTo(sourceTime) < NumberConstants.INTEGER_ZERO) {
						timeMap.put(referenceTime, sourceTime);
						++index;
					}
				}
			}

		}

		return timeMap;
	}

	/**
	 * 取最后某天的一毫秒时间
	 * 
	 * @param time
	 * @return
	 */
	public static Long getEndTime(Long time) {
		Date end = getDateEndTimeMillions(time);
		return end.getTime();
	}

	public static Long getStartTime(Long time) {
		Date start = getDateZeroTimeMillions(time);
		return start.getTime();
	}

	/**
	 * 取某个时间段之间月末时间列表（23：59：59）
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Long> getEndTimeOfMonthBetweenTime(Long start, Long end) {
		List<Long> timeList = new ArrayList<Long>();

		List<Long> dayList = CalendarUtil.getEndDayOfMonthBetweenTime(start, end);
		for (Long day : dayList) {
			timeList.add(MyTimeUtil.getEndTime(day));
		}

		return timeList;
	}

	/**
	 * 取某个时间段之间月末时间列表（00：00：00）
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Long> getStartTimeOfEndDayOfMonthBetweenTime(Long start, Long end) {
		List<Long> timeList = new ArrayList<Long>();

		List<Long> dayList = CalendarUtil.getEndDayOfMonthBetweenTime(start, end);
		for (Long day : dayList) {
			timeList.add(MyTimeUtil.getStartTime(day));
		}

		return timeList;
	}
}
