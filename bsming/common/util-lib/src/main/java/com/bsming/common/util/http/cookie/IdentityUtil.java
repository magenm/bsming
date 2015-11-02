package com.bsming.common.util.http.cookie;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bsming.common.constant.CookieConstants;

/**
 * @author
 */

public class IdentityUtil {

	private static final Log log = LogFactory.getLog(IdentityUtil.class);
	public static String SPLITTER = "|";
	public static String SPLITTER_REGEX = "\\|";

	/**
	 * 设置cookie
	 * 
	 * @param response
	 * @param maxAge
	 */
	@Deprecated
	public static void setIdentity(HttpServletRequest request, HttpServletResponse response, String userName, Long userId, int maxAge) {
		if (!isValid(userName, userId)) {
			deleteIdentity(request, response);
			return;
		}
		String value = encodeCookie(userName, userId);
		RequestUtil.setCookie(response, CookieConstants.COOKIE_HOME_NAME, value, maxAge, null, null);
		Map userMap = getUserIdentity(userName, userId);
		request.setAttribute(CookieConstants.CURRENT_VISITOR, userMap);
	}

	/**
	 * 设置cookie
	 * 
	 */
	@Deprecated
	public static void setIdentity(HttpServletRequest request, HttpServletResponse response, String userName, Long usrId) {

		setIdentity(request, response, userName, usrId, -1);

	}

	/**
	 * 取用户信息(chinaren跨域处理)
	 * 
	 * @param request
	 * @return
	 */
	@Deprecated
	public static Map getIdentity(HttpServletRequest request) {
		Map userMap = null;
		Cookie c = RequestUtil.getCookie(request, CookieConstants.COOKIE_HOME_NAME);
		if (c != null) {
			userMap = decodeCookie(c.getValue());
		}
		return userMap;
	}

	/**
	 * 清除cookie 信息
	 * 
	 * @param request
	 *            .
	 * @param response
	 *            .
	 */
	@Deprecated
	public static void deleteIdentity(HttpServletRequest request, HttpServletResponse response) {
		log.debug("deleteIdentity: " + CookieConstants.COOKIE_HOME_NAME);
		Cookie c = RequestUtil.getCookie(request, CookieConstants.COOKIE_HOME_NAME);
		if (null != c) {
			c.setValue("");
			log.debug("deleteCookie: " + c.getName());
			RequestUtil.deleteCookie(response, c, null, null);
		}
	}

	/**
	 * 判断用户是否登录过
	 * 
	 * @param request
	 * @return .
	 */
	@Deprecated
	public static boolean checkIdentity(HttpServletRequest request) {
		Map userMap = getIdentity(request);
		if (userMap != null)
			request.setAttribute(CookieConstants.CURRENT_VISITOR, userMap);
		return userMap != null ? true : false;
	}

	/**
	 * 判断用户是否登录过， 如果已经登录，附加在返回中
	 * 
	 * @param request
	 * @return [0] : true / false [1] : null / user
	 */
	@Deprecated
	public static Map checkAndReturnIdentity(HttpServletRequest request) {
		Map userMap = getIdentity(request);
		if (userMap != null)
			request.setAttribute(CookieConstants.CURRENT_VISITOR, userMap);
		return userMap;
	}

	/**
	 * 设置cookie
	 * 
	 * @param request
	 * @param response
	 * @param homeName
	 * @param userName
	 * @param userId
	 * @param maxAge
	 */
	public static void setIdentity(HttpServletRequest request, HttpServletResponse response, String homeName, String userName, Long userId, int maxAge) {
		if (!isValid(userName, userId)) {
			deleteIdentity(request, response, homeName);
			return;
		}
		String value = encodeCookie(userName, userId);
		RequestUtil.setCookie(response, homeName, value, maxAge, null, null);
		Map userMap = getUserIdentity(userName, userId);
		request.setAttribute(CookieUtil.CURRENT_VISITOR, userMap);
	}
	
	/**
	 * 设置cookie
	 * 
	 * @param request
	 * @param response
	 * @param homeName
	 * @param params
	 * @param userId
	 * @param maxAge
	 */
	public static void setIdentity(HttpServletRequest request, HttpServletResponse response, String homeName, Map<String, String> params,
			Long userId, int maxAge) {
		if (MapUtils.isEmpty(params) || StringUtils.isBlank(homeName) || null == userId) {
			return;
		}
		String value = encodeCookie(params, userId);
		RequestUtil.setCookie(response, homeName, value, maxAge, null, null);
		params.put(CookieUtil.USER_ID, ObjectUtils.toString(userId));
		request.setAttribute(CookieUtil.CURRENT_VISITOR, params);
	}

	/**
	 * 设置cookie,无cookie超时时间
	 * 
	 * @param request
	 * @param response
	 * @param homeName
	 * @param userName
	 * @param usrId
	 */
	public static void setIdentity(HttpServletRequest request, HttpServletResponse response, String homeName, String userName, Long usrId) {
		setIdentity(request, response, homeName, userName, usrId, -1);
	}
	
	/**
	 * 设置cookie,无cookie超时时间
	 * 
	 * @param request
	 * @param response
	 * @param homeName
	 * @param params
	 * @param usrId
	 */
	public static void setIdentity(HttpServletRequest request, HttpServletResponse response, String homeName, Map<String, String> params, Long usrId) {
		setIdentity(request, response, homeName, params, usrId, -1);
	}

	public static Map getUserIdentity(String userName, Long userId) {
		Map map = new HashMap();
		map.put(CookieUtil.USER_NAME, userName);
		map.put(CookieUtil.USER_ID, userId);
		return map;
	}

	/**
	 * 取用户信息(chinaren跨域处理)
	 * 
	 * @param request
	 * @param homeName
	 * @return
	 */
	public static Map getIdentity(HttpServletRequest request, String homeName) {
		Map userMap = null;
		Cookie c = RequestUtil.getCookie(request, homeName);
		if (c != null) {
			userMap = decodeCookie(c.getValue());
		}
		return userMap;
	}

	/**
	 * 
	 * @param request
	 * @param homeName
	 * @return
	 */
	public static Map<String, String> getMapFromCookie(HttpServletRequest request, String homeName) {
		Map<String, String> userMap = null;
		Cookie c = RequestUtil.getCookie(request, homeName);
		if (c != null) {
			userMap = decodeCookie(c);
		}
		return userMap;
	}

	/**
	 * 清除cookie
	 * 
	 * @param request
	 * @param response
	 * @param homeName
	 */
	public static void deleteIdentity(HttpServletRequest request, HttpServletResponse response, String homeName) {
		log.debug("deleteIdentity: " + homeName);
		Cookie c = RequestUtil.getCookie(request, homeName);
		if (null != c) {
			c.setValue("");
			log.debug("deleteCookie: " + c.getName());
			RequestUtil.deleteCookie(response, c, null, null);
		}
	}

	/**
	 * 判断用户是否登录过
	 * 
	 * @param request
	 * @param homeName
	 * @return
	 */
	public static boolean checkIdentity(HttpServletRequest request, String homeName) {
		Map userMap = getIdentity(request, homeName);
		if (userMap != null)
			request.setAttribute(CookieUtil.CURRENT_VISITOR, userMap);
		return userMap != null ? true : false;
	}

	/**
	 * 判断用户是否登录过， 如果已经登录，附加在返回中
	 * 
	 * @param request
	 * @param homeName
	 * @return
	 */
	public static Map checkAndReturnIdentity(HttpServletRequest request, String homeName) {
		Map userMap = getIdentity(request, homeName);
		if (userMap != null)
			request.setAttribute(CookieUtil.CURRENT_VISITOR, userMap);
		return userMap;
	}

	/**
	 * 判断用户是否正确 .
	 */
	private static boolean isValid(String userName, Long userId) {
		if (userName != null && StringUtils.isNotEmpty(userName) && null != userId)
			return true;
		return false;
	}

	/**
	 * @param cookie
	 * @return
	 * @Transient
	 */
	private static long getPassportExpireTime(Cookie cookie) {
		long expireTime = -1l;

		String value = cookie.getValue();
		if (value == null)
			return expireTime;

		String[] array = StringUtils.split(value, '|');
		if (array.length < 5)
			return expireTime;

		expireTime = ParamUtil.getLong(array[2], 0);
		expireTime = expireTime <= 0l ? -1 : expireTime;

		return expireTime;
	}

	/**
	 * cookie编码处理
	 */
	private static String encodeCookie(String userName, Long userId) {
		StringBuffer sb = new StringBuffer();
		sb.append(userId);
		try {
			String crypt = UserCryptUtil.userEncrypt(userName, userId);
			sb.append(SPLITTER + crypt);
		} catch (Exception e) {
			log.error(" UserCryptUtil.userEncrypt Exception : " + e.getMessage());
		}
		return sb.toString();
	}

	/**
	 * cookie编码处理
	 * 
	 * @param params
	 * @param userId
	 * @return
	 */
	private static String encodeCookie(Map<String, String> params, Long userId) {
		StringBuffer sb = new StringBuffer();
		sb.append(userId);
		try {
			String crypt = UserCryptUtil.userEncrypt(params, userId);
			sb.append(SPLITTER + crypt);
		} catch (Exception e) {
			log.error(" UserCryptUtil.userEncrypt Exception : " + e.getMessage());
		}
		return sb.toString();
	}

	/**
	 * @param value
	 * @return
	 */
	private static Map decodeCookie(String value) {
		if (StringUtils.isBlank(value))
			return null;
		try {
			String[] p = value.split(SPLITTER_REGEX);
			Long userId = new Long(p[0]);
			String crypt = p[1];
			String plainUserInfo = UserCryptUtil.userDecrypt(crypt);
			String userName = UserCryptUtil.getUserNameFromPlain(plainUserInfo);
			Long uid = UserCryptUtil.getUserIdFromPlain(plainUserInfo);
			if (userId.equals(uid)) {
				return getUserIdentity(userName, userId);
			}
		} catch (Exception e) {
			log.error("Wrong cookie format: [" + value + "] message is:" + e.getMessage());
			return null;
		}

		return null;
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	private static Map<String, String> decodeCookie(Cookie c) {
		if (null == c) {
			return null;
		}
		String value = c.getValue();
		Map<String, String> map = null;
		if (StringUtils.isBlank(value))
			return null;
		try {
			String[] p = value.split(SPLITTER_REGEX);
			Long userId = new Long(p[0]);
			String crypt = p[1];
			String plainUserInfo = UserCryptUtil.userDecrypt(crypt);
			map = UserCryptUtil.getMapFromPlain(plainUserInfo);
		} catch (Exception e) {
			log.error("Wrong cookie format: [" + value + "] message is:" + e.getMessage());
			return null;
		}

		return map;
	}

	/**
	 * @return
	 */
	private static String getSplitter() {
		byte[] bytes = "|".getBytes();
		String spitter = new String(bytes);
		return "@" + spitter;
	}

}
