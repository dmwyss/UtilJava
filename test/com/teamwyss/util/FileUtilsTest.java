package com.teamwyss.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileUtilsTest {

	private static final String TEST_FILE_PATH = "/Users/Shared/Data/Temp/FilesForFileUtilsTest";
	private static final String TEST_FILE_TITLE = "FileUtilsTestFile";
	private static final String TEST_FILE_SUFFIX = "txt";
	private static final String TEST_FILE_NAME = TEST_FILE_TITLE + "." + TEST_FILE_SUFFIX;
	private static final String TEST_FILE_PAN = TEST_FILE_PATH + "/" + TEST_FILE_NAME;
	private static ArrayList<String> TEST_CONTENT_AL;
	private static String TEST_CONTENT_STRING;

	public static FileUtils fu = new FileUtils();
	private static ArrayUtils au = new ArrayUtils();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TEST_CONTENT_AL = new ArrayList<String>();
		TEST_CONTENT_AL.add("This is line one");
		TEST_CONTENT_AL.add("Line two");
		TEST_CONTENT_AL.add("Third line in the file (next line is blank)");
		TEST_CONTENT_AL.add("");
		TEST_CONTENT_AL.add("Fifth line (last line is blank)");
		TEST_CONTENT_AL.add("");
		TEST_CONTENT_STRING = au.join(TEST_CONTENT_AL, "\n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		fu.deleteFile(TEST_FILE_PAN);
	}
	@After
	public void tearDownAfterEach() throws Exception {
		fu.deleteFile(TEST_FILE_PAN);
	}

	@Before
	public void resetTestEnv(){
		//S ystem.out.println("Resetting between tests");
		fu.deleteAllFilesInDir(TEST_FILE_PATH);
	}

	private void createStandardTestFile() {
		createStandardTestFile("");
	}

	private void createStandardTestFile(String sExtraBit) {
		String sPan = TEST_FILE_PATH + "/" + TEST_FILE_TITLE + sExtraBit + "." + TEST_FILE_SUFFIX;
		try {
			fu.setFileContent(sPan, TEST_CONTENT_AL);
		} catch (Exception e){}
	}

	private void addStandardTestFile() {

		if (!fu.exists(TEST_FILE_PAN)) {
			try {
				fu.setFileContent(TEST_FILE_PAN, TEST_CONTENT_AL);
				return;
			} catch (Exception e){}
		}

		for (int iF = 0; iF < 1000; iF++) {
			String sPAN = TEST_FILE_PATH + "/" + TEST_FILE_TITLE + iF + "." + TEST_FILE_SUFFIX;
			if (!fu.exists(sPAN)) {
				try {
					fu.setFileContent(sPAN, TEST_CONTENT_AL);
					return;
				} catch (Exception e){}
			}
		}
	}

	@Test
	public void testAppendFileContent_String_String() {
		createStandardTestFile();
		String sFilePan = TEST_FILE_PAN;
		String sCont = "THIS IS NEW";
		fu.appendFileContent(sFilePan, sCont);
		ArrayList<String> alGot = fu.getFileContentAsArrayList(TEST_FILE_PAN);
		String got = au.join(alGot, "\n");
		String expected = TEST_CONTENT_STRING + "\nTHIS IS NEW";
		//S ystem.out.println("got[" + got + "]");
		//S ystem.out.println("exp[" + expected + "]");
		assertEquals(got, expected);
	}
	@Test
	public void testCreateNewFile_testExists() {
		assertTrue(!fu.exists(TEST_FILE_PAN));
		File f = new java.io.File(TEST_FILE_PAN);
		fu.createNewFile(f);
		assertTrue(fu.exists(TEST_FILE_PAN));
		fu.deleteFile(TEST_FILE_PAN);
		assertTrue(!fu.exists(TEST_FILE_PAN));
	}
	@Test
	public void testGetAllFiles_File_String_int() {
		createStandardTestFile();
		File f = new File(TEST_FILE_PATH);
		String s = "*";
		int i = 10;
		String[] got = fu.getAllFiles(f, s, i);
		int expected = 1;
		assertEquals(got.length, expected);
	}
	@Test
	public void testGetAllFiles_String_String() {
		createStandardTestFile();
		String[] got = fu.getAllFiles(TEST_FILE_PATH, "txt");
		int expected = 1;
		assertEquals(got.length, expected);
	}
	@Test
	public void testGetFile() {
		String s = TEST_FILE_PAN;
		File fGot = fu.getFile(s);
		String got = fGot.getName();
		String expected = TEST_FILE_NAME;
		assertEquals(got, expected);
	}
	@Test
	public void testGetFileContent() {
		createStandardTestFile();
		ArrayList<String> alGot = fu.getFileContentAsArrayList(TEST_FILE_PAN);
		String got = au.join(alGot, "\n");
		String expected = TEST_CONTENT_STRING;
		assertEquals(got, expected);
	}
	@Test
	public void testGetFileContentAsArrayList_File() {
		createStandardTestFile();
		File f = new File(TEST_FILE_PAN);
		ArrayList<String> got = fu.getFileContentAsArrayList(f);
		String expected = TEST_CONTENT_AL.get(1);
		assertEquals(got.get(1), expected);
	}
	@Test
	public void testGetFileContentAsArrayList_String() {
		File f = new File("");
		ArrayList<String> got = fu.getFileContentAsArrayList(f);
		String expected = "";
		assertEquals(got.get(1), expected);
	}
	@Test
	public void testGetFileContentAsSettings() {
		ArrayList<String> got = fu.getFileContentAsSettings("sPath");
		String expected = "";
		assertEquals(got.get(1), expected);
	}
	@Test
	public void testGetFilePathAbsolute_String() {
		String s = "";
		String got = fu.getFilePathAbsolute(s);
		String expected = "";
		assertEquals(got, expected);
	}
	@Test
	public void testGetFilePathAbsolute_String_String() {
		String sBase = "";
		String sRelative = "";
		String got = fu.getFilePathAbsolute(sBase, sRelative);
		String expected = "";
		assertEquals(got, expected);
	}
	@Test
	public void testGetFilePathAndNameRelative() {
		String sBase = "";
		String sRelative = "";
		String got = fu.getFilePathAndNameRelative(sBase, sRelative);
		String expected = "";
		assertEquals(got, expected);
	}
	@Test
	public void testGetFilePathRelative() {
		String sBase = "";
		String sRelative = "";
		String got = fu.getFilePathRelative(sBase, sRelative);
		String expected = "";
		assertEquals(got, expected);
	}
	@Test
	public void testGetFilePathSeparator() {
		String s = "";
		String got = fu.getFilePathSeparator(s);
		String expected = "";
		assertEquals(got, expected);
	}
	@Test
	public void testGetFileSuffix() {
		String s = "";
		String got = fu.getFileSuffix(s);
		String expected = "";
		assertEquals(got, expected);
	}
	@Test
	public void testGetMaxFilesToList() {

		int got = fu.getMaxFilesToList();
		int expected = 0;
		assertEquals(got, expected);
	}
	@Test
	public void testRemoveFileSuffix() {
		String s = "";
		String got = fu.removeFileSuffix(s);
		String expected = "";
		assertEquals(got, expected);
	}
	@Test
	public void testReplaceFilePathSeparator() {
		String sPath = "";
		String sSep = "";
		String got = fu.replaceFilePathSeparator(sPath, sSep);
		String expected = "";
		assertEquals(got, expected);
	}
	@Test
	public void testSetFileContent_File_String() {
		File f = new File("");
		String s = "";
		String got = "";
		fu.setFileContent(f, s);
		String expected = "";
		assertEquals(got, expected);
	}
	@Test
	public void testSetFileContent_String_ArrayList() {
		String s = "";
		ArrayList<String> als = new ArrayList<String>();
		fu.setFileContent(s, als);
		String got = als.get(0);
		String expected = "";
		assertEquals(got, expected);
	}
	@Test
	public void testSetFileContent_String_String() {
		String s = "";
		ArrayList<String> als = new ArrayList<String>();
		fu.setFileContent(s, als);
		String got = als.get(0);
		String expected = "";
		assertEquals(got, expected);
	}
	@Test
	public void testSetMaxFilesToList_int() {
		int expected = 0;
		fu.setMaxFilesToList(expected);
		int got = fu.getMaxFilesToList();
		assertEquals(got, expected);
	}

}
