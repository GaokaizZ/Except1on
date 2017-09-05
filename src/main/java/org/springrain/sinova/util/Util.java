package org.springrain.sinova.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Util {

	//生产短信序列号
    public static synchronized String getUniqueSuffix(){
         // reset the counter if it is greater than 99999
       return Long.toString(System.currentTimeMillis())  + getrannumber();
    }
    
    //获得当前时间
    public static String getSendTime(){
    	Date date=new Date();
    	SimpleDateFormat s=new SimpleDateFormat("yyyyMMddHHmmss");
    	return s.format(date);
    }
    /**
     * 生产随机数
     * @return
     */
    public static String getrannumber() {
		StringBuffer strbufguess = new StringBuffer();
		String strguess = new String();
		int[] nums = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rannum = new Random();
		int count;
		int i = 0, temp_i = 0;
		for (int j = 10; j > 4; j--) {
			i = 0;
			temp_i = 0;
			count = rannum.nextInt(j);
			while (i <= count) {
				if (nums[temp_i] == -1)
					temp_i++;
				else {
					i++;
					temp_i++;
				}
			}
			strbufguess.append(Integer.toString(nums[temp_i - 1]));
			nums[temp_i - 1] = -1;
		}
		strguess = strbufguess.toString();
		rannum = null;
		strbufguess = null;
		nums = null;
		return strguess;
	}
}
