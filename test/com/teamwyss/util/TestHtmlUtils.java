/**
 *
 */
package com.teamwyss.util;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.teamwyss.customdata.HtmlTag;

/**
 * @author P654446d
 *
 */
public class TestHtmlUtils extends TestCase {

	private static String sHtmlPage = "";
	private static String sTextPage = "";
	private static HtmlUtils hu = new HtmlUtils();
	static {
		FileUtils fu = new FileUtils();
		sHtmlPage = fu.getFileContent("G:\\Data\\Projects_Eclipse\\utilJava\\test\\test\\com\\teamwyss\\util\\TestHtmlUtils_HtmlPage.htm");
		sTextPage = fu.getFileContent("G:\\Data\\Projects_Eclipse\\utilJava\\test\\test\\com\\teamwyss\\util\\TestHtmlUtils_TextPage.txt");
		if ((sTextPage.charAt(sTextPage.length() - 1) == '\r') || (sTextPage.charAt(sTextPage.length() - 1) == '\n')) {
			sTextPage = sTextPage.substring(0, sTextPage.length() - 2) + " ";
		}
	}

	/**
	 * Test method for {@link com.teamwyss.util.HtmlUtils#getFirstTag(java.lang.String, java.lang.String)}.
	 */
	public void testGetFirstTag() {
		// Test 01, get the title tag body.
		HtmlTag ht = hu.getFirstTag(sHtmlPage, "title");
		String sBodyFromFile = ht.getBody();
		assertEquals("Title tag", sBodyFromFile, "Title of the page");
		// Test 02, try to get a non exsitant tag, expect nothing.
		ht = hu.getFirstTag(sHtmlPage, "nonexistanttagname");
		sBodyFromFile = ht.getBody();
		assertEquals("Non existant tag", sBodyFromFile, "");
	}

	/**
	 * Test method for {@link com.teamwyss.util.HtmlUtils#getTag(java.lang.String, java.lang.String)}.
	 */
	public void testGetTag() {
		// Test 01, .
		ArrayList<HtmlTag> ht = hu.getTag(sHtmlPage, "div");
		assertEquals("Title tag", 4, ht.size());
	}

	public void testGetPageText() {
		// Get all the text from a page.
		String sStrippedHtml = hu.getPageText(sHtmlPage);
		System.out.println("SHOULD(FAKE)[" + sTextPage + "]\nRESULT(REAL)[" + sStrippedHtml + "]");
		assertEquals("Stripped Page text", sTextPage, sStrippedHtml);
	}
}
