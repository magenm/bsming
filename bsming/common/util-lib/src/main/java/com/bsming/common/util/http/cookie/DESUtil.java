package com.bsming.common.util.http.cookie;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESUtil {
	private static Cipher encrypt = null;
	private static Cipher decrypt = null;
	private static boolean isEncryptInit = false;
	private static boolean isDecryptInit = false;
	private static String KEYSTR="-7-*d@#5EdxBvrTRe-#5CtUs";

	private static Cipher getEncrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		if (encrypt == null && !isEncryptInit) {
			synchronized (DESUtil.class) {
				if (encrypt == null && !isEncryptInit) {
					encrypt = Cipher.getInstance("TripleDES");
					encrypt.init(Cipher.ENCRYPT_MODE, getKey());
					isEncryptInit = true;
				}
			}

		}
		return encrypt;
	}


	private static Cipher getDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		if (decrypt == null && !isDecryptInit) {
			synchronized (DESUtil.class) {
				if (decrypt == null && !isDecryptInit) {
					decrypt = Cipher.getInstance("TripleDES");
					decrypt.init(Cipher.DECRYPT_MODE, getKey());
					isDecryptInit = true;
				}
			}

		}
		return decrypt;
	}

	private static Key getKey() {
		SecretKey key = new SecretKeySpec(KEYSTR.getBytes(), "TripleDES");
		return key;
	}

	public static String encrypt(byte[] inputByte) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		byte[] ciperByte = getEncrypt().doFinal(inputByte);
		String encodeStr = new String(XBase64.encodeBase64(ciperByte));
		return encodeStr;
	}

	public static String decrypt(byte[] inputByte) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		byte[] encodeStr = XBase64.decodeBase64(inputByte);
		byte[] ciperByte = getDecrypt().doFinal(encodeStr);
		return new String(ciperByte);
	}

	public static void main(String[] args) {
//		passport|userId|systeTime
		String myInfo = "9601";
		try {
			String enStr = encrypt(myInfo.getBytes());
			System.out.println(enStr);
			String deStr = decrypt(enStr.getBytes());
			System.out.println(deStr);

		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
