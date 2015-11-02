package com.bsming.common.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据处理工具类
 * 
 * @author magenm
 * 
 */
public class DataUtils {

    /**
     * 判断是否为中文
     *
     * @param str
     * @return
     */
    public static String null2str(String str) {
       if(str != null){
           return str;
       }
       return "";
    }


    /**
	 * 判断是否为中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		boolean result = false;
		for (int i = 0; i < str.length(); i++) {
			int chr1 = (char) str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
				result = true;
			}
		}
		return result;
	}

	/**
	 * 把中文转成Unicode码
	 * 
	 * @param str
	 * @return
	 */
	public static String chinaToUnicode(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int chr1 = (char) str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
				result += "\\u" + Integer.toHexString(chr1);
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (null == obj) {
			return true;
		}
		if ("".equals(obj)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotNullOrEmpty(Object obj) {
		return !isNullOrEmpty(obj);
	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static String getUniqueName(String filename) {
		return getUUID() + filename.substring(filename.lastIndexOf("."));
	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static String getSimpleUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 32位FNV算法
	 * 
	 * @param String data
	 *            字符串
	 * @return String hash
	 */
	public static String hash(String data) {

		return data.hashCode() + "";
	}

	/**
	 * 首字母转小写
	 * 
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	/**
	 * 首字母转大写
	 * 
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	/**
	 * 首字母转大写
	 * 
	 * @param s
	 * @return
	 */
	public static String changeUrl(String url, String[] param) {
		if (param != null) {
			for (int i = 0; i < param.length; i++) {
				url = url.replace("{" + (i + 1) + "}", param[i]);
			}
		}
		return url;
	}

	public static String md5(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return str;
		}

		md.update(str.getBytes());
		byte[] bs = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bs.length; i++) { // 字节数组转换成十六进制字符串，形成最终的密文
			int v = bs[i] & 0xff;
			if (v < 16) {
				sb.append(0);
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param filename
	 * @return
	 */
	public static String getSuffix(String filename) {
		String suffix = "";
		int pos = filename.lastIndexOf('.');
		if (pos > 0 && pos < filename.length() - 1) {
			suffix = filename.substring(pos + 1);
		}
		return suffix;
	}

	/*
	 * 生成随机数
	 */
	public static String getRandomString(int length) {
		String seed = "ABCDEFGHJKMNPQRSTUVWXYY23456789";
		int subIndex = 0;
		String retString = "";
		for (int i = 0; i < length; i++) {
			subIndex = (int) (Math.random() * 100 % seed.length());
			retString += seed.substring(subIndex, subIndex + 1);
		}
		return retString;

	}

	/*
	 * 生成随机数
	 */
	public static String getRandomIntString(int length) {
		String seed = "0123456789";
		int subIndex = 0;
		String retString = "";
		for (int i = 0; i < length; i++) {
			subIndex = (int) (Math.random() * 100 % seed.length());
			retString += seed.substring(subIndex, subIndex + 1);
		}
		return retString;

	}

	/**
	 * 计算两个字符串最长相同子串
	 * 
	 * @Title: search
	 * @Description: TODO
	 * @param @param s1
	 * @param @param s2
	 * @param @return
	 * @return String
	 * @throws
	 * @Date 2014年3月17日 下午1:44:33
	 */
	public static String search(String s1, String s2) {
		String max = "";
		for (int i = 0; i < s1.length(); i++) {
			for (int j = i; j <= s1.length(); j++) {
				String sub = s1.substring(i, j);
				// System.out.println(sub);
				if ((s2.indexOf(sub) != -1) && sub.length() > max.length()) {
					max = sub;
				}
			}
		}
		return max;
	}

	/**
	 * 检测是否为手机号码
	 * 
	 * @Title: checkTelephone
	 * @Description: TODO
	 * @param @param tel
	 * @param @return
	 * @return boolean
	 * @throws
	 * @Date 2014年3月20日 下午2:46:47
	 */
	public static boolean checkTelephone(String tel) {
		String regExp = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(tel.trim());
		return m.find();
	}
	
	
	/**
	 * 
	 * @param strLength
	 * @return
	 */
	public static String getFixLenthString(int strLength) {

		Random rm = new Random();

		// 获得随机数
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

		// 将获得的获得随机数转化为字符串
		String fixLenthString = String.valueOf(pross);

		// 返回固定的长度的随机数
		return fixLenthString.substring(1, strLength + 1);
	}

    /**
     * 将金额（1.00）转为100
     * 即单位从元转为分
     * @param price
     * @return
     */
    public static String priceTofen(String price){
        BigDecimal bd = new BigDecimal(price);
        BigDecimal bd2 = new BigDecimal(100);
        BigInteger re = bd.multiply(bd2).toBigInteger();
        return re.toString();
    }

    /**
     * 将金额（1）转为1.00
     * @param price
     * @return
     */
    public static String priceToString(String price){
        int idx = price.indexOf(".");
        if(idx == -1){
           return price+".00";
        }
        int  pos = price.substring(idx+1).length();
        if(pos == 1){
            return price+"0";
        }
        if(pos> 2){
            return price.substring(0,idx+3);
        }
        return price;
    }
	
}
