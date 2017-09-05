package org.springrain.system.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {

	/**
	 * 
	 * 将二进制数据编码为BASE64字符串
	 * 
	 * @param binaryData
	 * 
	 * @return
	 */
	public static String encode(byte[] binaryData) {
		try {
			return new String(Base64.encodeBase64(binaryData), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}

	}

	/**
	 * 
	 * 将BASE64字符串恢复为二进制数据
	 * 
	 * @param base64String
	 * 
	 * @return
	 */

	public static byte[] decode(String base64String) {
		try {
			return Base64.decodeBase64(base64String.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
	
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	
	public static void main(String[] args) {
//		System.out.println(Base64Util.encode("zhang%2573".getBytes()));
//		System.out.println(Base64Util.encode("zhang%73".getBytes()));
//		System.out.println(Base64Util.encode(" ".getBytes()));
		
		System.out.println(Base64Util.toHexString("wang&3400"));
		String s = "77616e672633343030";
		System.out.println(Base64Util.toStringHex(s));
	}

}
