package com.teamwyss.util;

import java.util.Date;

import junit.framework.TestCase;

import com.teamwyss.customdata.PropertiesFile;

public class TestPropertiesFileUtils extends TestCase {

	FileUtils fu = new FileUtils();
	PropertiesFileUtils pfu = new PropertiesFileUtils();
	String sRoot = "C:\\Temp\\PropertiesFileTests";
	String sFileSep = "\\";
	String sFileName = "testOfproperties.properties";
	String sFullFilePath = sRoot + sFileSep + sFileName;
	String sTimeNow = "";

	protected void setUp() throws Exception {
		DateUtils du = new DateUtils();
		sTimeNow = du.toString(new Date(), "yyyy-MM-dd kk:mm:ss");
	}

	protected void tearDown() throws Exception {
	}

	public void testCreatePropertiesFile() {
		// Create a file, and test that it is there.
		/*
		 * Stuff for multiline vars has been shelved.
		 */
		String sMultilineVarOriginal = FileUtils.NEWLINE
			+ "" + FileUtils.NEWLINE
			+ "this is a bit of text on a new line..." + FileUtils.NEWLINE
			+ "" + FileUtils.NEWLINE;
		String sMultilineVarOnSingleLine = "multiline var on one line...";
		String sMultilineVarOnSingleLineVerySpaced = "multiline var on one lineXXX...";
		String sMultilineVarOnSingleLineAfterSpace = "multiline var on one line...";
		String sFileContent = FileUtils.NEWLINE
		+ "time_now=" + sTimeNow + FileUtils.NEWLINE
		+ "" + FileUtils.NEWLINE
		+ "# This is a comment in the middle..."
		+ "int_var=12345 # This has comments after it." + FileUtils.NEWLINE
		+ ""
		+ "multi_line_var={{{" + sMultilineVarOriginal + "}}}"
		+ ""
		+ "" + FileUtils.NEWLINE
		+ "" + FileUtils.NEWLINE
		+ "multi_line_single_var={{{" + sMultilineVarOnSingleLine + "}}}"
		+ "" + FileUtils.NEWLINE
		+ "multi_line_single_spaced_var = {{{" + sMultilineVarOnSingleLine + "}}}"
		+ "" + FileUtils.NEWLINE
		+ " multi_line_single_very_spaced_var    = {{{" + sMultilineVarOnSingleLine + "}}}"
		+ "";
		fu.setFileContent(sFullFilePath, sFileContent);
		assertTrue("Does it exist?", fu.exists(sFullFilePath));


		// Open the file and convert to a var.
		PropertiesFile pf = pfu.instanciatePropertiesFile(sFullFilePath);
		pf.setCommentMarker("#");
		pf.setComments(new String[]{"Test file", "Using single and multi-lines."});
		assertNotNull("Get the file back.", pf);

		// Get a single-line-var
		String sTimeNowFromFile = pf.getVar("time_now");
		assertEquals("time_equals back from prop file.", sTimeNow, sTimeNowFromFile);

		// Get a multi-line-var
		String sFormattedStuff = pf.getVar("multi_line_var");
		pf.setComment("multi_line_var", "This is very spacy!!!");
		assertEquals("multi_line_var back from prop file.", sMultilineVarOriginal, sFormattedStuff);

		// Get a multi-line-on-single-linevar
		String sMutliSingleStuff = pf.getVar("multi_line_single_var");
		assertEquals("multi_line_var back from prop file.", sMultilineVarOnSingleLine, sMutliSingleStuff);

		// Get a multi-line-on-single-linevar with spaces
		String sMutliSingleStuffWithSpace = pf.getVar("multi_line_single_spaced_var");
		assertEquals("multi_line_var back from prop file.", sMultilineVarOnSingleLineAfterSpace, sMutliSingleStuffWithSpace);

		// Get a multi-line-on-single-linevar with spaces
		String sMutliSingleStuffWithVerySpace = pf.getVar("multi_line_single_very_spaced_var");
		pf.setComment("multi_line_single_very_spaced_var", "This is very spacy!!!");
		assertEquals("multi_line_var back from prop file.", sMultilineVarOnSingleLineAfterSpace, sMutliSingleStuffWithVerySpace);

		System.out.println("TestPropertiesFileUtils" + pf.toString());

		// Save to the file automagically, and retest.
		//pfu.instanciatePropertiesFile(sFullFilePath);
		pfu.save(pf);

		sTimeNowFromFile = pf.getVar("time_now");
		assertEquals("time_equals back from prop file.", sTimeNow, sTimeNowFromFile);


		// Get a multi-line-var
		String sFormattedStuffSecondRun = pf.getVar("multi_line_var");
		assertEquals("multi_line_var back from prop file.", sMultilineVarOriginal, sFormattedStuffSecondRun);
	}

	public void testSave() {
		String sNewFileName = "saveTest.properties";
		String sFilePathAndFileName = sRoot + sFileSep + sNewFileName;
		PropertiesFile pf = pfu.instanciatePropertiesFile(sFilePathAndFileName);
		pf = new PropertiesFile(sFilePathAndFileName);
		//pf.setFilePathAndFileName(sFilePathAndFileName);
		pf.setVar("new_var", "yes it works", "These are the comments");
		pfu.save(pf);

		PropertiesFile pfSaved = pfu.instanciatePropertiesFile(sFilePathAndFileName);
		assertEquals("Getting new_var", "yes it works", pfSaved.getVar("new_var"));
	}

}
