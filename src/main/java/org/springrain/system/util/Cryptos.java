package org.springrain.system.util;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cryptos {
	private static final String AES = "AES";
	private static final String AES_CBC = "AES/CBC/PKCS5Padding";
	private static final String HMACSHA1 = "HmacSHA1";

	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160; // RFC2401
	private static final int DEFAULT_AES_KEYSIZE = 128;
	private static final int DEFAULT_IVSIZE = 16;

	private static SecureRandom random = new SecureRandom();

	public static byte[] aesEncrypt(byte[] input, byte[] key) {
		return aes(input, key, Cipher.ENCRYPT_MODE);
	}

	public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
		return aes(input, key, iv, Cipher.ENCRYPT_MODE);
	}

	public static String aesDecrypt(byte[] input, byte[] key) {
		byte[] decryptResult = aes(input, key, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) {
		byte[] decryptResult = aes(input, key, iv, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	private static byte[] aes(byte[] input, byte[] key, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(mode, secretKey);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			// throw Exceptions.unchecked(e);
			return null;
		}
	}

	private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(AES_CBC);
			cipher.init(mode, secretKey, ivSpec);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			// throw Exceptions.unchecked(e);
			return null;
		}
	}

	public static byte[] generateAesKey() {
		return generateAesKey(DEFAULT_AES_KEYSIZE);
	}

	public static byte[] generateAesKey(int keysize) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			keyGenerator.init(keysize);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			// throw Exceptions.unchecked(e);
			return null;
		}
	}

	public static byte[] generateIV() {
		byte[] bytes = new byte[DEFAULT_IVSIZE];
		random.nextBytes(bytes);
		return bytes;
	}

	public static void main(String[] args) {
		// 加密
		System.out.println(EncodeUtils.hexEncode(aesEncrypt(
				"18610676365".getBytes(),
				EncodeUtils.hexDecode("f6b0d3f905bf02939b4f6d29f257c2ab"),
				EncodeUtils.hexDecode("1a42eb4565be8628a807403d67dce78d"))));
		// 解密
		// System.out.println(aesEncrypt("18610676365".getBytes(),
		// "f6b0d3f905bf02939b4f6d29f257c2ab".getBytes(),
		// "1a42eb4565be8628a807403d67dce78d".getBytes()));
		System.out.println(aesDecrypt(
				EncodeUtils.hexDecode("62542ddfbf62702cdac6c27c67a423af"),
				EncodeUtils.hexDecode("f6b0d3f905bf02939b4f6d29f257c2ab"),
				EncodeUtils.hexDecode("1a42eb4565be8628a807403d67dce78d")));

		// System.out.println(EncodeUtils.hexEncode(aesEncrypt(
		// "连上6天班".getBytes(),
		// EncodeUtils.hexDecode("f6b0d3f905bf02939b4f6d29f257c2ab"),
		// EncodeUtils.hexDecode("1a42eb4565be8628a807403d67dce78d"))));
	}
}