package com.teamwyss.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RegExpUtilsTest {

	private static String sMaster = "one two twentyone xonex -one- one one34";

	@Test
	public void testMatchIntegerUsingRegExp(){
		RegExpUtils rexu = new RegExpUtils();

		// Test word by itself. Can have digits next to it.
		assertEquals(rexu.isInteger("1"), true);
		assertEquals(rexu.isInteger("1234"), true);
		assertEquals(rexu.isInteger("123456789"), true);
		assertEquals(rexu.isInteger("1234.002"), false);
		assertEquals(rexu.isInteger("1234 "), false);
		assertEquals(rexu.isInteger(" 1234"), false);
		assertEquals(rexu.isInteger("one"), false);

	}

	@Test
	public void testMatchNumberUsingRegExp(){
		RegExpUtils rexu = new RegExpUtils();
		/*
		 */
		// Test word by itself. Can have digits next to it.
		assertEquals(rexu.isNumber("1"), true);
		assertEquals(rexu.isNumber("1234"), true);
		assertEquals(rexu.isNumber("123456789"), true);
		assertEquals(rexu.isNumber("1234.002"), true);
		assertEquals(rexu.isNumber("1.2"), true);
		assertEquals(rexu.isNumber("1.2345678"), true);
		assertEquals(rexu.isNumber("1234 "), false);
		assertEquals(rexu.isNumber(" 1234"), false);
		assertEquals(rexu.isNumber("one"), false);
	}
	
}
