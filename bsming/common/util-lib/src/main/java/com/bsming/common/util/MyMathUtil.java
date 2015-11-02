package com.bsming.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MyMathUtil {

	public static int moveRight(BigDecimal value, int offset) {
		if (null == value) {
			return 0;
		}
		return value.movePointRight(offset).intValue();
	}

	public static void main(String[] args) {
		List<BigDecimal> datas = new ArrayList();
		datas.add(new BigDecimal("20"));
		datas.add(new BigDecimal("20"));
		BigDecimal sigma = MyMathUtil.sigma(datas);

	}

	public static BigDecimal sigma(List<BigDecimal> datas) {
		BigDecimal sum = new BigDecimal("0");
		for (BigDecimal value : datas) {
			sum = sum.add(value);
		}
		return sum;
	}

	/**
	 * 解析Double，如果发生异常则返回0.0
	 * 
	 * @param value
	 * @return
	 */
	public static Double getDouble(String value) {
		Double ret = 0.0;

		try {
			ret = Double.valueOf(value);
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * 解析Integer，如果发生异常则返回0
	 * 
	 * @param value
	 * @return
	 */
	public static Integer getInteger(String value) {
		Integer ret = 0;

		try {
			ret = Integer.valueOf(value);
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Long getLong(String value) {
		Long ret = null;
		try {
			ret = Long.valueOf(value);
		} catch (Exception e) {
			return 0l;
		}
		return ret;
	}

}
