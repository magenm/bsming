package com.bsming.common.constant;

public class TimeDateConstant {
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyy_MM_dd_HH_mm_ss_sss = "yyyy-MM-dd HH:mm:ss:sss";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String yyyyMM = "yyyyMM";
	public static final String yyyy = "yyyy";

	public static final Integer SEASON_ONE = 1;
	public static final Integer SEASON_TWO = 2;
	public static final Integer SEASON_THREE = 3;
	public static final Integer SEASON_FOUR = 4;
	
	public static final long MAX_YYYYMMDD_MILLIS = 253402251057097L;

	/**
	 * 昨天和今天相差的天数
	 */
	public static final int PREVIOUS_DAY_DIFF = 1;
	/**
	 * 一周前和今天相差的天数
	 */
	public static final int DAY_WEEK_AGO_DIFF = 7;
	/**
	 * 一月前和今天相差的天数
	 */
	public static final int DAY_MONTH_AGO_DIFF = 30;
	/**
	 * 一季度前和今天相差的天数
	 */
	public static final int DAY_QUARTER_AGO_DIFF = 90;
}
