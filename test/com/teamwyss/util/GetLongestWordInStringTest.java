package com.teamwyss.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class GetLongestWordInStringTest {

	public static String sRoot = "AaZz is the]&Longest&StrinGG I can make";
	public static String sUnderAndNum = " again1213 _another_one_with_underscores_ 23 *32 23px little";

	@Test
	public void test() {
		GetLongestWordInString glw = new GetLongestWordInString();

		ArrayList<String> got = glw.getLongestWordsInString(sRoot, 5);
		String sShould = glw.joinArrayList(got, ",");
		assertEquals("Longest,StrinGG,AaZz,make,the", sShould);

		got = glw.getLongestWordsInString(sRoot, 3);
		sShould = glw.joinArrayList(got, ",");
		assertEquals("Longest,StrinGG,AaZz", sShould);

		got = glw.getLongestWordsInString(sRoot + sUnderAndNum, 3);
		sShould = glw.joinArrayList(got, ",");
		assertEquals("_another_one_with_underscores_,again1213,StrinGG", sShould);

		got = glw.getLongestWordsInString(sRoot + sUnderAndNum, 30);
		sShould = glw.joinArrayList(got, ",");
		assertEquals("_another_one_with_underscores_,again1213,Longest,StrinGG,little,AaZz,make,can,the,is,I", sShould);
	}

}
