package com.teamwyss.util;

import java.io.File;
import java.util.ArrayList;

import com.teamwyss.customdata.PropertiesFile;

public class PropertiesFileUtils {

	public static final String NEWLINE = "\r\n";
	public static final String MULTILINE_MARKER_START = "{{{";
	public static final String MULTILINE_MARKER_END = "}}}";

	public PropertiesFile instanciatePropertiesFile(String sFilePathAndName) {
		if((sFilePathAndName == null) || (sFilePathAndName.length() == 0)){
			return null;
		}
		FileUtils fu = new FileUtils();
		String NEWLINE = "\r\n";
		if(fu.exists(sFilePathAndName)){
			StringFormatUtils sfu = new StringFormatUtils();
			ArrayList<String> al = fu.getFileContentAsArrayList(sFilePathAndName);
			String sEqualsSign = "=";
			PropertiesFile pfOut = new PropertiesFile(sFilePathAndName);
			al = removeComments(al);
			boolean isInsideMultiline = false;
			String sKey = "";
			String sValue = "";
			for(int iLine = 0; iLine < al.size(); iLine++){
				String sLine = al.get(iLine);
				if(isInsideMultiline){
					int iEnd = sLine.indexOf(MULTILINE_MARKER_END);
					if(iEnd == -1){
						// No end to the multiline found, keep collecting.
						sValue = sValue + NEWLINE + sLine;
					} else {
						sValue = sValue + NEWLINE + sLine.substring(0, iEnd);
						pfOut.setVar(sKey, sValue);
						isInsideMultiline = false;
					}
				} else {
					//sLine = sfu.trimLeadChars(al.get(iLine), " \t" );
					if((sLine.indexOf(sEqualsSign) != -1) && (sLine.indexOf(MULTILINE_MARKER_START) != -1)){
						// set up collection of data for a multiline var.
						sKey = sfu.trim(sLine.substring(0, sLine.indexOf(sEqualsSign)));
						sValue = sLine.substring(sLine.indexOf(MULTILINE_MARKER_START) + MULTILINE_MARKER_START.length());
						int iEnd = sValue.indexOf(MULTILINE_MARKER_END);
						if(iEnd == -1){
							// Line contains start of multiline, but no end. Next line will need to append.
							isInsideMultiline = true;
						} else {
							// Line contains both start and end of multiline var.
							sValue = sValue.substring(0, iEnd);
							pfOut.setVar(sKey, sValue);
						}
					} else if(sLine.indexOf(sEqualsSign) != -1){
						// We are not inside a multiline, and there is the start of a single line var.
						int iSep = sLine.indexOf(sEqualsSign);
						sKey = sfu.trim(sLine.substring(0, iSep));
						sValue = sLine.substring(iSep + sEqualsSign.length());
						pfOut.setVar(sKey, sValue);
					}
				}
			}
			return pfOut;
		}
		return null;
	}

	public PropertiesFile SIMPLEinstanciatePropertiesFile(String sFilePathAndName) {
		if((sFilePathAndName == null) || (sFilePathAndName.length() == 0)){
			return null;
		}
		FileUtils fu = new FileUtils();
		if(fu.exists(sFilePathAndName)){
			StringFormatUtils sfu = new StringFormatUtils();
			ArrayList<String> al = fu.getFileContentAsArrayList(sFilePathAndName);
			String sEqualsSign = "=";
			PropertiesFile pfOut = new PropertiesFile(sFilePathAndName);
			al = removeComments(al);
			for(int iLine = 0; iLine < al.size(); iLine++){
				String sLine = al.get(iLine);
				sLine = sfu.trimLeadChars(sLine, " \t");
				int iSep = sLine.indexOf(sEqualsSign);
				if((sLine.indexOf("#") == 0) || (sLine.length() == 0)){
					continue; // It is a Comment.
				} else if(iSep == -1){
					continue; // It is blank.
				}
				String sKey = sfu.trim(sLine.substring(0, iSep));
				String sValue = sLine.substring(iSep + sEqualsSign.length());
				pfOut.setVar(sKey, sValue);
			}
			return pfOut;
		}
		return null;
	}

	/**
	 * Get the array of lines in, and remove comments.
	 * @param al ArrayList of lines in a file.
	 * @return Filtered ArrayList of lines.
	 */
	private ArrayList<String> removeComments(ArrayList<String> al) {
		for(int iLine = 0; iLine < al.size(); iLine++){
			String sLine = al.get(iLine);
			int iCPos = sLine.indexOf("#");
			if(iCPos != -1){
				al.set(iLine, sLine.substring(0, iCPos));
			}
		}
		return al;
	}

	public boolean save(PropertiesFile pf) {
		FileUtils fu = new FileUtils();
		if(fu.exists(pf.getFilePath() + pf.getFileName())){
			System.out.println("ERROR: tried to set " + pf.getFilePath() + pf.getFileName());
			return false;
		}
		File fileOut = fu.setFileContent(fu.replaceFilePathSeparator(pf.getFilePathAndName(), "\\"), pf.toString());
		System.out.println("Created file \"" + fileOut.getAbsolutePath() + "\"");
		return true;
	}

}
