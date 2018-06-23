package com.funcy.g01.base.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringUtil {

	public static String intListToStr(List<Integer> list) {
		StringBuffer sb = new StringBuffer();
		for(Integer enemyRoleId : list) {
			sb.append(enemyRoleId).append(",");
		}
		return sb.substring(0, sb.length() - 1);
	}
	
	public static String longListToStr(List<Long> list) {
		StringBuffer sb = new StringBuffer();
		for(Long enemyRoleId : list) {
			sb.append(enemyRoleId).append(",");
		}
		return sb.substring(0, sb.length() - 1);
	}
	
	public static List<Integer> strToIntList(String str) {
		if(str == null) {
			return Collections.emptyList();
		}
		List<Integer> result = new ArrayList<Integer>();
		String[] temp = str.split(",");
		for(String string : temp) {
			if("".equals(string)) {
				continue;
			}
			result.add(Integer.parseInt(string));
		}
		return result;
	}
	
	public static List<Long> strToLongList(String str) {
		if(str == null) {
			return Collections.emptyList();
		}
		List<Long> result = new ArrayList<Long>();
		String[] temp = str.split(",");
		for(String string : temp) {
			if("".equals(string)) {
				continue;
			}
			result.add(Long.parseLong(string));
		}
		return result;
	}
	
    public static String encryptToMd5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }
    
    /**
	 * 判断字符串是否为空 null or "" return true; "  " return true; " pop" return false;
	 * "  pop  " return false;
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isEmpty(String x) {
		int len;
		if (x == null || (len = x.length()) == 0)
			return true;

		while (len-- > 0) {
			if (!Character.isWhitespace(x.charAt(len)))
				return false;
		}

		return true;
	}
    
}