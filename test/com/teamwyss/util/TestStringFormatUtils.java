package com.teamwyss.util;

import junit.framework.TestCase;

public class TestStringFormatUtils extends TestCase {

	public void testGetStringExcludingBetween() {
		StringFormatUtils sfu = new StringFormatUtils();
		String sRaw = "this <b>Stuff inside</b> and that, so <span id=\"nothing\"> some more text.</span>";
		String sShould = "this Stuff inside and that, so  some more text.";
		System.out.println(sShould + "\n\n" + sfu.getStringExcludingBetween(sRaw, "<", ">"));
		assertEquals("getStringExcludingBetween", sShould, sfu.getStringExcludingBetween(sRaw, "<", ">"));
	}

}
