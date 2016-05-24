package com.teamwyss.customdata;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.teamwyss.util.ArrayUtils;
import com.teamwyss.util.DateUtils;
import com.teamwyss.util.FileUtils;
import com.teamwyss.util.PropertiesFileUtils;
import com.teamwyss.util.StringFormatUtils;

public class PropertiesFile {

	private String filePath = "";
	private String fileName = "";
	private String filePathSeparator = "/";
	private boolean persistant = true;
	private LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();
	private LinkedHashMap<String, String[]> propertyComments = new LinkedHashMap<String, String[]>();
	private String[] comments;
	private String commentMarker = "//";
	private String filePathAndFileName;

	public static final String NEWLINE = "\r\n";
	public static final String MULTILINE_MARKER_START = "{{{";
	public static final String MULTILINE_MARKER_END = "}}}";



	public PropertiesFile(String sFilePathAndFileName) {
		setFilePathAndFileName(sFilePathAndFileName);
		init();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String sPath) {
		FileUtils fu = new FileUtils();
		StringFormatUtils sfu = new StringFormatUtils();
		sPath = fu.replaceFilePathSeparator(sPath, this.filePathSeparator);
		this.filePath = sfu.trimEndChar(sPath, '/');
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String name) {
		this.fileName = name;
	}

	public void setFilePathAndFileName(String sFilePathAndFileName) {
		FileUtils fu = new FileUtils();
		this.filePathAndFileName = fu.replaceFilePathSeparator(sFilePathAndFileName, this.filePathSeparator);
		int iPosLast = sFilePathAndFileName.lastIndexOf(this.filePathSeparator);
		if (iPosLast != -1) {
			this.filePath = sFilePathAndFileName.substring(0, iPosLast);
			this.fileName = sFilePathAndFileName.substring(iPosLast + this.filePathSeparator.length());
		} else {
			this.filePath = "";
			this.fileName = sFilePathAndFileName;
		}
	}

	public void setVar(String sKey, String sValue, String sPropertyComment) {
		setVar(sKey, sValue, new String[] { sPropertyComment });
	}

	public void setVar(String sKey, String sValue, String[] asPropertyComment) {
		propertyComments.put(sKey, asPropertyComment);
		setVar(sKey, sValue);
	}

	public void setVar(String sKey, String sValue) {
		properties.put(sKey, sValue);
	}

	public String getVar(String sKey) {
		return properties.get(sKey);
	}

	public String getVar(String sKey, String defaultValue) {
		String sOut = properties.get(sKey);
		return sOut != null ? sOut : defaultValue;
	}

	public String getFilePathSeparator() {
		return filePathSeparator;
	}

	public void setFilePathSeparator(String filePathSeparator) {
		this.filePathSeparator = filePathSeparator;
	}

	public void setPersistant(boolean b) {
		this.persistant = b;
	}

	public boolean isPersistant() {
		return persistant;
	}

	public String toString() {
		StringBuffer sbOut = new StringBuffer();
		ArrayUtils au = new ArrayUtils();
		// if((this.comments != null) && (this.comments.length > 0)){
		// sbOut.append(NEWLINE);
		// }
		if ((this.comments != null) && (this.comments.length > 0)) {
			// sbOut.append(NEWLINE);
			for (int iVarComment = 0; iVarComment < this.comments.length; iVarComment++) {
				sbOut.append(this.commentMarker + this.comments[iVarComment] + NEWLINE);
			}
		}
		Iterator<String> iterProp = this.properties.keySet().iterator();
		sbOut.append("propertiesFile.creation_date=" + ((new DateUtils()).toString(new Date(), "yyyy-MM-dd kk:mm:ss")) + NEWLINE);
		while (iterProp.hasNext()) {
			String sKey = iterProp.next();
			String[] asCommentsTemp = this.propertyComments.get(sKey);
			if ((asCommentsTemp != null) && (asCommentsTemp.length > 0)) {
				sbOut.append(NEWLINE);
				for (int iVarComment = 0; iVarComment < asCommentsTemp.length; iVarComment++) {
					// sbOut.append(this.commentMarker + this.comments[iVarComment] + NEWLINE);
					sbOut.append(this.commentMarker + asCommentsTemp[iVarComment] + NEWLINE);
				}
			}
			sbOut.append(sKey + "=");
			String sValue = this.properties.get(sKey);
			boolean isMultiline = (sValue.indexOf(NEWLINE) != -1);
			if (isMultiline) {
				sbOut.append(MULTILINE_MARKER_START);
			}
			sbOut.append(sValue);
			if (isMultiline) {
				sbOut.append(PropertiesFileUtils.MULTILINE_MARKER_END);
			}
			sbOut.append(NEWLINE);
		}
		return sbOut.toString();
	}

	public String getFilePathAndName() {
		return this.filePath + this.filePathSeparator + this.fileName;
	}

	public void setComments(String[] sComments) {
		this.comments = sComments;
	}

	public void setCommentMarker(String sCommentMarker) {
		this.commentMarker = sCommentMarker;
	}

	public String getCommentMarker() {
		return commentMarker;
	}

	public void setComment(String sKey, String sCommentToSet) {
		setComment(sKey, new String[] { sCommentToSet });
	}

	public void setComment(String sKey, String[] asCommentToSet) {
		this.propertyComments.put(sKey, asCommentToSet);
	}

	public PropertiesFile init() {
		if((this.filePathAndFileName == null) || (this.filePathAndFileName.length() == 0)){
			return null;
		}
		FileUtils fu = new FileUtils();
		String NEWLINE = "\r\n";
		if(fu.exists(this.filePathAndFileName)){
			StringFormatUtils sfu = new StringFormatUtils();
			ArrayList<String> al = fu.getFileContentAsArrayList(this.filePathAndFileName);
			String sEqualsSign = "=";
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
						setVar(sKey, sValue);
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
							setVar(sKey, sValue);
						}
					} else if(sLine.indexOf(sEqualsSign) != -1){
						// We are not inside a multiline, and there is the start of a single line var.
						int iSep = sLine.indexOf(sEqualsSign);
						sKey = sfu.trim(sLine.substring(0, iSep));
						sValue = sLine.substring(iSep + sEqualsSign.length());
						setVar(sKey, sValue);
					}
				}
			}
		}
		return this;
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
