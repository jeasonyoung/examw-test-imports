package com.examw.test.imports.support;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * MD5工具类.
 * @author 杨勇.
 * @since 2013-11-29.
 * */
public final class MD5Util {
	public final static String charsetName = "UTF-8";
	/**
	 * 将字符串md5加密. 
	 * @param source
	 * 	字符串。
	 * @return 密文.
	 * */
	public final static String MD5(String source){
		 if(StringUtils.isEmpty(source)) return null;
		 return DigestUtils.md5DigestAsHex(source.getBytes(Charset.forName(MD5Util.charsetName)));
	}
	/**
	 * 对输入流进行md5加密。
	 * @param stream
	 * 	输入流。
	 * @return 密文。
	 * */
	public final static String MD5(InputStream stream){
		if(stream == null) return null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			if(stream.markSupported()){
				stream.reset();
			}
			byte[] buf = new byte[1024];
			int len = -1;
			while((len = stream.read(buf, 0, buf.length)) > 0){
				digest.update(buf, 0, len);
			}
			byte[] result = digest.digest();
			return HexUtil.parseBytesHex(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}