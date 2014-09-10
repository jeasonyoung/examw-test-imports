package com.examw.test.imports.tests;

import org.junit.Test;

public class StringTrim {

	@Test
	public void test(){
		String str = "　　A.生理需要 B.安全需要 C.归属需要 D.尊重需要";
		System.out.println(str);
		System.out.println(String.format("%1$d",((int)str.charAt(0))));
		System.out.println(str.trim());
	}
}