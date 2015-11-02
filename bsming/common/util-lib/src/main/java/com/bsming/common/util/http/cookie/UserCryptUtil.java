package com.bsming.common.util.http.cookie;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.LRUMap;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Encrypt Decrypt client
 * 
 * @author allenshen Copyright 2009 Sohu.com Inc. All Rights Reserved. date: Apr
 *         2, 2009 10:32:23 AM
 */
public final class UserCryptUtil {
	private static Log log = LogFactory.getLog(UserCryptUtil.class);
	private static String SEPARATAOR_STRING = "*";
	private static String SEPARATAOR_STR_L3 = ",";
	private static String TIME_KEY = "loginTime";

	private static Map<String, String> ciphertextMap = Collections.synchronizedMap(new LRUMap(5000));

	/**
	 * result : md5|(encrypt info(passport|userId|createtime))
	 * 
	 * @param passport
	 * @param userId
	 * @return
	 */
	public static String userEncrypt(String passport, Long userId) {
		String userInfo = createUserInfo(passport, userId);
		StringBuffer sb = new StringBuffer();
		try {
			String md5UserInfo = MD5Util.getMd5Sum(userInfo);
			String encrStr = DESUtil.encrypt(userInfo.getBytes());
			encrStr = XBase64.enReplace(encrStr);
			sb.append(md5UserInfo).append(SEPARATAOR_STRING).append(encrStr);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return null;

	}

	/**
	 * result : md5|(encrypt info(passport|userId|createtime))
	 * 
	 * @param params
	 * @param userId
	 * @return
	 */
	public static String userEncrypt(Map<String, String> params, Long userId) {
		String userInfo = createUserInfo(params, userId);
		StringBuffer sb = new StringBuffer();
		try {
			String md5UserInfo = MD5Util.getMd5Sum(userInfo);
			String encrStr = DESUtil.encrypt(userInfo.getBytes());
			encrStr = XBase64.enReplace(encrStr);
			sb.append(md5UserInfo).append(SEPARATAOR_STRING).append(encrStr);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return null;

	}

	/**
	 * create user info passport|userId|createtime
	 * 
	 * @param passport
	 * @param userId
	 * @return
	 */
	private static String createUserInfo(String passport, Long userId) {
		StringBuffer sb = new StringBuffer();
		sb.append(StringUtils.defaultIfEmpty(passport, "")).append(SEPARATAOR_STRING).append(ObjectUtils.toString(userId, "")).append(
				SEPARATAOR_STRING).append(System.currentTimeMillis()).append(SEPARATAOR_STRING);
		log.debug("userInfo: " + sb.toString());
		return sb.toString();
	}

	/**
	 * create user info params|userId|createtime
	 * 
	 * @param params
	 * @param userId
	 * @return
	 */
	private static String createUserInfo(Map<String, String> params, Long userId) {
		StringBuffer sb = new StringBuffer();
		if (MapUtils.isNotEmpty(params)) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
					sb.append(entry.getKey()).append(SEPARATAOR_STR_L3).append(entry.getValue()).append(SEPARATAOR_STRING);
				}
			}
		}
		sb.append(TIME_KEY).append(SEPARATAOR_STR_L3).append(System.currentTimeMillis()).append(SEPARATAOR_STRING);
		log.debug("userInfo: " + sb.toString());
		return sb.toString();
	}

	/**
	 * 
	 * @param enUserInfo
	 * @return
	 */
	public static String userDecrypt(String enUserInfo) {
		if (StringUtils.isEmpty(enUserInfo)) {
			return null;
		} else {
			String plainStr = ciphertextMap.get(enUserInfo);
			if (StringUtils.isNotBlank(plainStr)) {
				return plainStr;
			}
			String md5 = StringUtils.substringBefore(enUserInfo, SEPARATAOR_STRING);
			String encrStr = StringUtils.substringAfter(enUserInfo, SEPARATAOR_STRING);
			if (StringUtils.isNotEmpty(md5) && StringUtils.isNotEmpty(encrStr)) {
				try {
					encrStr = XBase64.deReplace(encrStr);
					plainStr = DESUtil.decrypt(encrStr.getBytes());
					if (StringUtils.isNotEmpty(plainStr)) {
						String md5UserInfo = MD5Util.getMd5Sum(plainStr);
						if (ObjectUtils.equals(md5UserInfo, md5)) {
							ciphertextMap.put(enUserInfo, plainStr);
							return plainStr;
						} else {
							return null;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * get passport from plaintext
	 * 
	 * @param plainUserInfo
	 * @return
	 */
	public static String getUserNameFromPlain(String plainUserInfo) {
		if (StringUtils.isNotEmpty(plainUserInfo) && isformat(plainUserInfo)) {
			return StringUtils.substringBefore(plainUserInfo, SEPARATAOR_STRING);
		}
		return null;
	}

	/**
	 * get passport from plaintext
	 * 
	 * @param plainUserInfo
	 * @return
	 */
	public static Map<String, String> getMapFromPlain(String plainUserInfo) {
		Map<String, String> map = null;
		if (StringUtils.isNotEmpty(plainUserInfo) && isformat(plainUserInfo)) {
			String[] strs = StringUtils.split(plainUserInfo, SEPARATAOR_STRING);
			if (null != strs) {
				map = new HashMap<String, String>();
				for (int i = 0; i < strs.length; i++) {
					if (StringUtils.isNotBlank(strs[i])) {
						String[] value = StringUtils.split(strs[i], SEPARATAOR_STR_L3);
						if (null != value) {
							map.put(value[0], value[1]);
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 
	 * @param plainUserInfo
	 * @return
	 */
	public static Long getUserIdFromPlain(String plainUserInfo) {
		if (StringUtils.isNotEmpty(plainUserInfo) && isformat(plainUserInfo)) {
			String[] infos = StringUtils.split(plainUserInfo, SEPARATAOR_STRING);
			String userId = infos != null && infos.length >= 2 ? infos[1] : null;
			return StringUtils.isNotBlank(userId) && StringUtils.isNumeric(userId) ? NumberUtils.toLong(userId) : null;
		}
		return null;
	}

	public static Long getUserIdFromCipher(String cipUserInfo) {
		String plainUserInfo = userDecrypt(cipUserInfo);
		return getUserIdFromPlain(plainUserInfo);
	}

	/**
	 * 检查格式
	 * 
	 * @param plainUserInfo
	 * @return
	 */
	private static boolean isformat(String plainUserInfo) {
		return true;
	}

	public static void main(String[] args) {

		String enUserInfo = userEncrypt("wywy_1208@hotmail.com", 67789L);

		System.out.println(enUserInfo);
		String orgUserInfo = userDecrypt(enUserInfo);
		System.out.println(orgUserInfo);
		System.out.println(getUserIdFromPlain(orgUserInfo));
	}
}
