package com.cvsher.spider.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtil {

	public static String md5(String source){
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(source.getBytes());
			byte[] targetBytes = digest.digest();
			//将字节数组转化为16进制字符串
            StringBuffer targetBuffer = new StringBuffer();
            for(int i=0; i<targetBytes.length; i++){
                //整型参数编码的后8位为targetBytes[i],前面所有位置0
                String tmp = Integer.toHexString(targetBytes[i] & 0xff);
                if(tmp.length() < 2){    //16进制数据小于两位，补全前置0
                    targetBuffer.append("0");
                }
                targetBuffer.append(tmp);
            }
            return targetBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
