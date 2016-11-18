//package com.vjinke.uitls;
//
//import java.security.SecureRandom;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//
///**
// * AES 数据加密解密方法 加密算法密匙长度为256 现今最高的
// * 
// * @author Administrator
// * 
// */
//public class AESUtil {
//	public static final String AES = "AES";
//	public static final String SHA1 = "SHA1PRNG";
//	public static final String CRYPTO = "Crypto";
//	public static final String CIPHER = "AES/ECB/ZeroBytePadding";
//
//	/**
//	 * 加密
//	 * 
//	 * @param content
//	 *            ：文本
//	 * @param password
//	 *            ：密码
//	 * @return
//	 */
//	public static String encrypt(String content, String password) {
//		try {
//			KeyGenerator kgen = KeyGenerator.getInstance(AES);
//			// SecureRandom sr = new SecureRandom();// java pc版加密设置
//			SecureRandom sr = SecureRandom.getInstance(SHA1, CRYPTO);//
//			// android版加密设置
//			byte[] seed = password.getBytes();
//			sr.setSeed(seed);
//			kgen.init(128, sr); // 192 and 256 bits may not be available
//			SecretKey secretKey = kgen.generateKey();
//			byte[] enCodeFormat = secretKey.getEncoded();
//			SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
//			// Cipher cipher = Cipher.getInstance(AES);// java pc版加密设置
//			Cipher cipher = Cipher.getInstance(CIPHER);// Android版加密设置
//			byte[] byteContent = content.getBytes();
//			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
//			return parseByte2HexStr(cipher.doFinal(byteContent));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 解密
//	 * 
//	 * @param content
//	 *            待解密内容
//	 * @param password
//	 *            解密密钥
//	 * @return
//	 */
//	public static String decrypt(String content, String password) {
//		try {
//
//			KeyGenerator kgen = KeyGenerator.getInstance(AES);
//			// SecureRandom sr = new SecureRandom();// java pc版解密设置
//			SecureRandom sr = SecureRandom.getInstance(SHA1, CRYPTO);//
//			// Android
//			byte[] seed = password.getBytes();
//			sr.setSeed(seed);
////			sr.setSeed(password.getBytes());
//			kgen.init(128, sr);
//			SecretKey secretKey = kgen.generateKey();
//			byte[] enCodeFormat = secretKey.getEncoded();
//			SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
//			// Cipher cipher = Cipher.getInstance(AES);// java pc版设置
//			Cipher cipher = Cipher.getInstance(CIPHER);// Android版设置
//			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
//			String s =  new String(cipher.doFinal(parseHexStr2Byte(content)));
//			return s;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 将二进制转换成16进制
//	 * 
//	 * @param buf
//	 * @return
//	 */
//	public static String parseByte2HexStr(byte buf[]) {
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < buf.length; i++) {
//			String hex = Integer.toHexString(buf[i] & 0xFF);
//			if (hex.length() == 1) {
//				hex = '0' + hex;
//			}
//			sb.append(hex.toUpperCase());
//		}
//		return sb.toString();
//	}
//
//	/**
//	 * 将16进制转换为二进制
//	 * 
//	 * @param hexStr
//	 * @return
//	 */
//	public static byte[] parseHexStr2Byte(String hexStr) {
//		if (hexStr.length() < 1)
//			return null;
//		byte[] result = new byte[hexStr.length() / 2];
//		for (int i = 0; i < hexStr.length() / 2; i++) {
//			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
//			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
//					16);
//			result[i] = (byte) (high * 16 + low);
//		}
//		return result;
//	}
//}

package com.jdhui.uitls;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	static final public byte[] KEY_VI = { 1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4,
			5, 6, 7, 8 };
	public static final String bm = "UTF-8";

	public static String encrypt(String dataPassword, String cleartext)
			throws Exception {
		// 对密钥进行处理-S
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom;
		// for Android
		secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
		// for Java
		// secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(dataPassword.getBytes("UTF-8"));
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		// 对密钥进行处理-E
		IvParameterSpec zeroIv = new IvParameterSpec(KEY_VI);
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(cleartext.getBytes(bm));
		return new String(parseByte2HexStr(encryptedData));
	}

	public static String decrypt(String dataPassword, String encrypted)
			throws Exception {
		// 对密钥进行处理-S
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom;
		// for Android
		secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
		// for Java
		// secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(dataPassword.getBytes("UTF-8"));
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		// 对密钥进行处理-E
		byte[] byteMi = parseHexStr2Byte(encrypted);
		IvParameterSpec zeroIv = new IvParameterSpec(KEY_VI);
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte[] decryptedData = cipher.doFinal(byteMi);

		return new String(decryptedData, bm);
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
}
