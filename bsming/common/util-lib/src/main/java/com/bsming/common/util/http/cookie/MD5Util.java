package com.bsming.common.util.http.cookie;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static char[] Digit = { '0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f' };
	public static String getMd5Sum(String inputStr) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] inputStrByte = inputStr.getBytes();
		digest.update(inputStrByte, 0, inputStrByte.length);


		byte[] md5sum = digest.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16;i++) {
                char [] ob = new char[2];
                ob[0] = Digit[md5sum[i] >> 4 & 0x0F];
                ob[1] = Digit[md5sum[i] & 0x0F];
                String s = new String(ob);
                sb.append(s);
		}

		return sb.toString();
//		BigInteger bigInt = new BigInteger(1, md5sum);
//		String output = bigInt.toString(16);
//		return output;
	}


	public static void main(String[] args) throws NoSuchAlgorithmException {
		String a = getMd5Sum("syh010@hotmail.com*16201*1239452120404*");
		System.out.println(a);
		System.out.println(System.currentTimeMillis());
	}



}
