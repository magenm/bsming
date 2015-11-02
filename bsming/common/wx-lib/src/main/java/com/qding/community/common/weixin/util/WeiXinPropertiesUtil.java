package com.qding.community.common.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class WeiXinPropertiesUtil {
    private static Properties props;

    static {
        props = new Properties();
        InputStream fis = WeiXinPropertiesUtil.class.getClassLoader()
                .getResourceAsStream("weixin.properties");
        try {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取某个属性
     */
    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
