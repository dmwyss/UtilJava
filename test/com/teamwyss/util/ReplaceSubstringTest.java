package com.teamwyss.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReplaceSubstringTest {

	private static String sMaster = "one two twentyone xonex -one- one one34";

	@Test
	public void testReplaceUsingRegExp(){
		ReplaceSubstring rs = new ReplaceSubstring();

		// Test word by itself. Can have digits next to it.
		String sActual = rs.replaceUsingRegExp(sMaster, "\\bone\\b", "EINS");
		assertEquals("EINS two twentyone xonex -EINS- EINS one34", sActual);

		// Test word with anything in front, but ends after.
		sActual = rs.replaceUsingRegExp(sMaster, "one\\b", "EINS");
		assertEquals("EINS two twentyEINS xonex -EINS- EINS one34", sActual);

		// Negative lookahead. Will only find one strings that do NOT have digits after.
		sActual = rs.replaceUsingRegExp(sMaster, "one(?!\\d)", "EINS");
		assertEquals("EINS two twentyEINS xEINSx -EINS- EINS one34", sActual);

		// Positive lookahead. Finds only ones followed by digits, but does not replace digits.
		sActual = rs.replaceUsingRegExp(sMaster, "\\bone(?=\\d)", "EINS");
		assertEquals("one two twentyone xonex -one- one EINS34", sActual);
	}

}
