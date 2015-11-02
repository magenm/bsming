package com.bsming.common.util.http.cookie;

/**
 * Created by IntelliJ IDEA.
 * User: QiuMT
 * Date: 11-9-8
 * Time: 下午10:50
 * To change this template use File | Settings | File Templates.
 */

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bsming.common.util.MyMathUtil;

/**
 * use in spring
 * */
public class CookieUtil {

	private static Log log = LogFactory.getLog(CookieUtil.class);

	// 设置cookie有效期是2周，根据需要自定义
	public Integer cookieMaxAge = 60 * 60 * 24 * 14;

	private String home = "www.rxhui.com"; // cookie
	private String domain = ".rxhui.com"; // cookie's domain

	public static final String verifyCode = "verifyCode";// verify code picture
	public static final String USER_CODE = "userCode";
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	public static final String SAVE_TIME = "saveTime";
	public static final String KAPTCHA = "Kaptcha";
	public static final String CURRENT_VISITOR = "currentVisitor";
	public static final String REFERER = "referer";
	public static final Long NONLOGIN_USER_ID = -1L;

	/**
	 * 保存Cookie
	 * 
	 * @param request
	 * @param response
	 * @param userName
	 * @param userId
	 */
	public void setIdentity(HttpServletRequest request, HttpServletResponse response, String userName, Long userId) {
		if (null != cookieMaxAge && 0 != cookieMaxAge) {
			IdentityUtil.setIdentity(request, response, this.home, userName, userId, cookieMaxAge);
		} else {
			IdentityUtil.setIdentity(request, response, this.home, userName, userId);
		}
	}

	/**
	 * 保存Cookie
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @param userId
	 */
	public void setIdentity(HttpServletRequest request, HttpServletResponse response, Map<String, String> params, Long userId) {
		if (null != cookieMaxAge && 0 != cookieMaxAge) {
			IdentityUtil.setIdentity(request, response, this.home, params, userId, cookieMaxAge);
		} else {
			IdentityUtil.setIdentity(request, response, this.home, params, userId);
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Map getIdentity(HttpServletRequest request, HttpServletResponse response) {
		Map userMap = IdentityUtil.getIdentity(request, this.home);
		if (null != userMap) {
			// 判断是否在有效期内,过期就删除Cookie
			// if (userMap.get(SAVE_TIME) != null) {
			// Long validTimeInCookie =
			// MyMathUtil.getLong(userMap.get(CookieUtil.SAVE_TIME).toString());
			// if (isExpire(validTimeInCookie)) {
			// // 删除Cookie
			// clearCookie(response);
			// }
			// }
		}
		return userMap;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, String> getIdentity(HttpServletRequest request) {
		Map<String, String> userMap = IdentityUtil.getMapFromCookie(request, this.home);
		if (null != userMap) {
		}
		return userMap;
	}

	/**
	 * 删除cookie
	 * 
	 * @param request
	 * @param response
	 */
	public void deleteIdentity(HttpServletRequest request, HttpServletResponse response) {
		IdentityUtil.deleteIdentity(request, response, this.home);
	}

	public Long getID(HttpServletRequest request, HttpServletResponse response) {
		Map map = this.getIdentity(request, response);
		if (map == null) {
			return null;
		}
		return (Long) map.get(USER_ID);
	}

	/**
	 * 从cookie里面取userId
	 * 
	 * @param request
	 * @return
	 */
	public Long getID(HttpServletRequest request) {
		Map map = this.getIdentity(request);
		if (null == map || null == map.get(USER_ID)) {
			return null;
		}
		Long userId = MyMathUtil.getLong(map.get(USER_ID).toString());
		return userId;
	}

	public String getName(HttpServletRequest request, HttpServletResponse response) {
		Map map = this.getIdentity(request, response);
		if (map == null || StringUtils.isBlank(map.get(USER_NAME).toString())) {
			return null;
		}
		return (String) map.get(USER_NAME);
	}

	/**
	 * 保存Cookie,图片验证码
	 * 
	 * @param request
	 * @param response
	 * @param userName
	 * @param userId
	 */
	public void setIdentity(HttpServletRequest request, HttpServletResponse response, String kaptcha) {
		String value = null;
		try {
			value = MD5Util.getMd5Sum(kaptcha);
		} catch (NoSuchAlgorithmException e) {
			log.error("got error when getMD5", e);
			return;
		}
		RequestUtil.setCookie(response, verifyCode, value, -1, this.domain, null);

	}

	/**
	 * 得到图片验证码
	 * 
	 * @param request
	 * @return
	 */
	public String getKaptchaIdentity(HttpServletRequest request) {
		String capText = null;
		Cookie c = RequestUtil.getCookie(request, verifyCode);
		if (c != null) {
			capText = c.getValue();
		}

		return capText;
	}

	// 用户注销时,清除Cookie,在需要时可随时调用
	public void clearCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(this.home, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 如果过期就是true
	 * 
	 * 
	 * @param value
	 * @return
	 */
	private Boolean isExpire(Long value) {
		Calendar c = Calendar.getInstance();
		try {
			Long interval = c.getTimeInMillis() - value;
			Integer days = (int) (interval / 1000);
			if (days > cookieMaxAge) {
				return true;
			}
		} catch (Exception e) {
			log.error("got error when determine cookie isExpire", e);
			return true;
		}
		return false;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Integer getCookieMaxAge() {
		return cookieMaxAge;
	}

	public void setCookieMaxAge(Integer cookieMaxAge) {
		this.cookieMaxAge = cookieMaxAge;
	}

}
