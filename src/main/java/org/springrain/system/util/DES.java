package org.springrain.system.util;

import java.security.Key;
import javax.crypto.Cipher;

/**
 * 采用DES算法，对字符串进行加密或者解密操佄1�7
 * @Copyright: Copyright (c) 2010
 * @Company: NUOTER
 * @version: 1.0
 * @author 杜习坄1�7 2010-08-02
 */
public class DES
{
    private Cipher encryptCipher = null;
    private static Cipher decryptCipher = null;
    
    /**
     * 根据密钥初始化DES算法工具
     */
    public DES(String strKey) throws Exception {
        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }
    
    /*public*/
    
    /**
     * 为指定的字符串进行加密操作，并返回加密后的结果字符串
     * @param strIn 霄1�7要加密的字符丄1�7
     * @return strOut 加密后的字符丄1�7
     */
    public String encrypt(String strIn) throws Exception {
    	return byteArr2HexStr(encrypt(strIn.getBytes()));
    }
    
    /**
     * 对指定的字符串进行解密操作，并返回解密后的结果字符串
     * @param strIn 霄1�7要解密的字符丄1�7
     * @return strOut 解密后的字符丄1�7
     */
    public String decrypt(String strIn) throws Exception {
    	return new String(decrypt(hexStr2ByteArr(strIn)));
    }
    
    /*private*/
    
    private byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    private static byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }
    
    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];

        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }
    
    private String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0){
                intTmp = intTmp + 256;
            }
            if (intTmp < 16){
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }
    
    private static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
    
    public static String encryptFrom(String key, String src){
    	try {
			DES des = new DES(key);
			return des.encrypt(src);
		} catch (Exception e) {
			e.printStackTrace();
			return src;
		}
    }
    
    public static String decryptTo(String key, String desc){
    	try {
			DES des = new DES(key);
			return des.decrypt(desc);
		} catch (Exception e) {
			e.printStackTrace();
			return desc;
		}
    }
}
