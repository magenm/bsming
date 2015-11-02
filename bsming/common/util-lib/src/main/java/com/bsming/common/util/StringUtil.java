package com.bsming.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by magenm on 2015/11/2.
 */
public class StringUtil {

    private static final String TRIM_REGEX = "^[　 ]+|[　 ]+$";
    private static final String TRIM_REGEX_ALL = "[　 ]+|[　 ]";
    public static final String EMPTY = "";

    /**
     * 去字符串前后全角和半角空格
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (StringUtils.isBlank(str)) {
            return EMPTY;
        }
        return str.replaceAll(TRIM_REGEX, EMPTY);
    }


}
