package org.springrain.sinova.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class LotteryUtils {

	static Random r = new Random();
	static int common=0;
	static TreeMap<String, Integer> prizeMap = null;
	static TreeMap<String,Integer> lotteryMap = null;
	
	/**
	 * 奖品/概率map
	 * @return
	 */
	public static Map<String, Integer> getPrizeMap() {
		//各个奖项的中奖概率的分母
		prizeMap = new TreeMap<String, Integer>();
		prizeMap.put("1_移动数据流量10MB_BCAZ2192", 25);//4%
		prizeMap.put("2_移动数据流量50MB_BCAZ2191", 33);//3%
		prizeMap.put("3_移动数据流量100MB_BCAZ2190", 25);//4%
		prizeMap.put("4_移动数据流量500MB_BCAZ2189", 100);//1%
		prizeMap.put("5_本地通话分钟数10分钟_BCAZ2188", 33);//3%
		prizeMap.put("6_本地通话分钟数50分钟_BCAZ2187", 50);//2%
		prizeMap.put("7_本地通话分钟数100分钟_BCAZ2186", 50);//2%
		prizeMap.put("8_本地通话分钟数500分钟_BCAZ2185", 100);//1%
		return prizeMap;
	}
	
	public static Map<String,Integer> getLotteryMap() {
		if (prizeMap==null)  getPrizeMap();
		lotteryMap = new TreeMap<String, Integer>();
		List<String> keyList = new ArrayList<String>(prizeMap.keySet());
		
		List<Integer> list = new ArrayList<Integer>();
		for(String key: keyList) {
			if (prizeMap.get(key)!=0) {
				list.add(prizeMap.get(key));
			}
			
		}
		//计算最小公倍数
		common = getN(list);
		System.out.println("最小公倍数:"+common);
		int gailv = 0;
		for(int i=0; i<keyList.size(); i++) {
			String key = keyList.get(i);
			if (i==0) {
				if (prizeMap.get(key)!=0) {
					gailv =  common/prizeMap.get(key);
				}
			} else {
				if (prizeMap.get(key)!=0) {
					gailv = gailv + common/prizeMap.get(key);
				} 
			}
			lotteryMap.put(key, gailv);
		}
		
		return lotteryMap;
	}
	
	public static String lottery() {
		if (lotteryMap==null) getLotteryMap();
		int ri = getRandom(common);
		List<String> keyList = new ArrayList<String>(lotteryMap.keySet());
		int lastGl = 0;
		for(int i=0; i<keyList.size(); i++) {
			String key = keyList.get(i);
			
			if (ri >= lastGl && ri < lotteryMap.get(key)) {
				return key;
			} else {
				if (i!=keyList.size()-1) {
					lastGl = lotteryMap.get(key);
				} else {
					return "0";
				}
			}
		}
		return null;
	}
	
	/**
     * 求最大公约数
    */
    public static int gcd(int m, int n){
        while (true){
            if ((m = m % n) == 0)
            return n;
            if ((n = n % m) == 0)
            return m;
        }
    }
    
    /**
    * 求最小公倍数
    */
    public static int gys(int z, int y){
        int t = 0;
        int c = 0;
        c = gcd(z,y);
        t = z * y / c;
        
        return t;
    }
    
    /**
     * 求几个数的最小公倍数
    */
    public static int getN(List<Integer> list){
        
        int t = 1;
        
        for(int i = 0;i<list.size();i++){
            Integer temp = (Integer)list.get(i);
            t = gys(t,temp.intValue());
        }
        return t; 
    }
    
    /**
     * 产生随机数
    */
    public static int getRandom(int y){
        int result = r.nextInt(y);
        
        return result;
    }
    
    public static void main(String[] args) {
    	int a = 0;int b = 0;int c = 0;int d = 0;int e = 0;int f = 0;int g = 0;int h=0;int k=0;
		for (int i=0;i<30000;i++) {
			String key = lottery();
			if ("1_移动数据流量10MB_BCAZ2192".equals(key)) {
				a++;
			}
			if ("2_移动数据流量50MB_BCAZ2191".equals(key)) {
				b++;
			}
			if ("3_移动数据流量100MB_BCAZ2190".equals(key)) {
				c++;
			}
			if ("4_移动数据流量500MB_BCAZ2189".equals(key)) {
				d++;
			}
			if ("5_本地通话分钟数10分钟_BCAZ2188".equals(key)) {
				e++;
			}
			if ("6_本地通话分钟数50分钟_BCAZ2187".equals(key)) {
				f++;
			}
			if ("7_本地通话分钟数100分钟_BCAZ2186".equals(key)) {
				h++;
			}
			if ("8_本地通话分钟数500分钟_BCAZ2185".equals(key)) {
				k++;
			}
			if ("0".equals(key)) {
				g++;
			}
		}
		 System.out.println("移动数据流量10MB:" + a + " 移动数据流量50MB:" + b + " 移动数据流量100MB:" + c + " 移动数据流量500MB:" + d + " 本地通话分钟数10分钟:" + e 
				 + " 本地通话分钟数50分钟:" + f + " 本地通话分钟数100分钟:" + h + " 本地通话分钟数500分钟:" + k +" 谢谢参与:" + g);
	}
}
