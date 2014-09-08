package com.examw.test.imports.support;

import org.springframework.util.StringUtils;

/**
 * 16进制工具类。
 * @author yangyong.
 * @since 2014-05-09.
 */
public final class HexUtil {
	/**
	 * 16进制数组。
	 * */
	public final static char HexDigits [] ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	/**
	 * 将字节数组转换为16进制字符串。
	 * @param data
	 * 	字节数组。
	 * @return 16进制字符串。
	 * */
	public final static String parseBytesHex(byte[] data){
		if(data == null || data.length == 0) return null;
		int len = data.length;
		char[] results = new char[len * 2];
		int k = 0;
		for(int i = 0; i < len; i++){
			results[k++] = HexDigits[(data[i] >>> 4) & 0xf];
			results[k++] = HexDigits[data[i] & 0xf];
		}
		return new String(results);
	}
	/**
	 * 将16进制字符串转换为字节数组。
	 * @param hex
	 * 16进制字符串。
	 * @return
	 * 字节数组。
	 */
	public final static byte[] parseHexBytes(String hex){
		int len = 0;
		if(StringUtils.isEmpty(hex) || (len = hex.length()) < 1) return null;
		if(len % 2 !=0) throw new RuntimeException("16进制的长度应为偶数！[" +len + "]");
		byte[] result = new byte[len / 2];
		for(int i = 0; i < result.length; i++){
			int high = Integer.parseInt(hex.substring(i*2,i*2+1), 16),
			     low   = Integer.parseInt(hex.substring(i*2+1, i*2+2), 16);
			result[i] = (byte)(high * 16 + low);
		}
		return result;
	}
}