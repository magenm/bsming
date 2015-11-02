package com.bsming.common.util.http.cookie;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;


/**
 *
 * @author hongtaozhang
 *
 */
public class RequestUtil {

    public static final String ENCODING = "UTF-8";
    private transient static Log log = LogFactory.getLog(RequestUtil.class);



    /**
     *
     * @param response
     * @param cacheAge
     */
    public static void setCacheHeader(HttpServletResponse response, int cacheAge) {
        response.setHeader("Pragma", "Public");
        // HTTP 1.1
        response.setHeader("Cache-Control", "max-age=" + cacheAge);
        response.setDateHeader("Expires", System.currentTimeMillis() + cacheAge * 1000L);
    }

    /**
     *
     * @param response
     */
    public static void setNoCacheHeader(HttpServletResponse response) {
        // HTTP 1.0
        response.setHeader("Pragma", "No-cache");
        // HTTP 1.1
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    /**
     *
     * @param request
     * @return
     */
    public static String dump(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();

        Enumeration names = request.getAttributeNames();
        sb.append("\nrequest attributes: \n");
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            sb.append("name = [" + name + "] value = [" + request.getAttribute(name) + "]\n");
        }

        names = request.getParameterNames();
        sb.append("\nrequest parameter: \n");
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            sb.append("name = [" + name + "] value = [" + request.getParameter(name) + "]\n");
        }
        return sb.toString();
    }

    /**
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String xReq = request.getHeader("X-Requested-With");
        return (xReq != null);
    }

    /**
     * @param request
     * @return
     */
    public static String getRefer(HttpServletRequest request) {
        return request.getHeader("REFERER");
    }

    /**
     *
     * @param url
     * @return
     */
    public static String encodeURL(String url) {
        try {
            return java.net.URLEncoder.encode(url, ENCODING);
        } catch (UnsupportedEncodingException e) {
            // do nothing
            return null;
        }
    }

    /**
     *
     * @param url
     * @return
     */
    public static String decodeURL(String url) {
        try {
            return java.net.URLDecoder.decode(url, ENCODING);
        } catch (UnsupportedEncodingException e) {
            // do nothing
            return null;
        }
    }

    /**
     *
     * @param request
     * @return
     */
    public static String getRequestCompleteURL(HttpServletRequest request) {
        return request.getRequestURL().append("?").append(request.getQueryString()).toString();
    }
/**
 *
 * @param response
 * @param name
 * @param value
 * @param expiry
 * @param domain
 * @param path .
 */
    public static void setCookie(HttpServletResponse response, String name, String value, int expiry, String domain, String path) {
        if (log.isDebugEnabled()) {
            log.debug("Setting cookie '" + name + " [ " + value + " ] ' on path '" + path + "'");
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(false);
        if (domain != null)
            cookie.setDomain(domain);
        cookie.setPath(path != null ? path : "/");
        cookie.setMaxAge(expiry); // 30 days
        response.addCookie(cookie);
    }
/**
 *
 * @param response
 * @param name
 * @param domain
 * @param path .
 */
    public static void deleteCookie(HttpServletResponse response, String name, String domain, String path) {
        Cookie cookie = new Cookie(name , "");
        deleteCookie(response,cookie,  domain,  path);
    }


    /**
     * 批量删除某个域名下的cookie
     * @param response
     * @param names
     * @param domain
     * @param path
     */
    public static void deleteCookies(HttpServletResponse response, String[] names, String domain, String path) {
        if(null == names){
            return;
        }
        for(int i=0; i< names.length; i++){
            Cookie cookie = new Cookie(names[i] , "");
            deleteCookie(response,cookie,  domain,  path);
        }

    }


    /**
     *
     * @param response
     * @param cookie
     * @param domain
     * @param path .
     */
    public static void deleteCookie(HttpServletResponse response, Cookie cookie, String domain, String path) {
        if (cookie != null) {
            if (log.isDebugEnabled()) {
                log.debug("Deleting cookie '" + cookie.getName() + "' on domain '"+cookie.getDomain() + "' path '" + path + "'");
            }
            // Delete the cookie by setting its maximum age to zero
            cookie.setMaxAge(0);
            if (domain != null)
                cookie.setDomain(domain);
            cookie.setPath(path != null ? path : "/");
            response.addCookie(cookie);
        }
    }
/**
 *
 * @param request
 * @param name
 * @return .
 */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        Cookie returnCookie = null;

        if (cookies == null) {
            return returnCookie;
        }

        for (int i = 0; i < cookies.length; i++) {
            Cookie thisCookie = cookies[i];

            if (thisCookie.getName().equals(name)) {
                // cookies with no value do me no good!
                if (StringUtils.isNotBlank(thisCookie.getValue())) {
                    returnCookie = thisCookie;

                    break;
                }
            }
        }

        return returnCookie;
    }
    /**
     * 获取Client IP : 此方法能够穿透squid 和 proxy
     * @param request
     * @return .
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
          ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
          ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
          ip = request.getRemoteAddr();
        }
        if(ip.indexOf(",") >  0 )
          ip = ip.substring(0, ip.indexOf(","));
        return ip;
      }
}
