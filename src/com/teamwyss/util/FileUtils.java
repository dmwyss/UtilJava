package com.teamwyss.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {

	public static final String NEWLINE = "\r\n";
	public int maxFilesToList = 1000;

	/**
	 * Get the content of a file as a String.
	 * @param sPath Location of the file.
	 * @return Content as a file.
	 */
	public String getFileContent(String sPath) {
		ArrayList<String> alRaw = getFileContentAsArrayList(sPath);
		StringBuffer sbOut = new StringBuffer();
		for(int i = 0; i < alRaw.size(); i++){
			sbOut.append(alRaw.get(i)).append("\r\n");
		}
		return sbOut.toString();
	}

	/**
	 * Return all text from a file.
	 * @param sPath Full or relative path to a file to use.
	 * @return List of lines in the file.
	 */
	public ArrayList<String> getFileContentAsArrayList(String sPath){
		File fileSrc = new File(sPath);
		return getFileContentAsArrayList(fileSrc);
	}

	/**
	 * Return all text from a file.
	 * @param fileSrc The java.util.File to use.
	 * @return List of lines in the file.
	 */
	public ArrayList<String> getFileContentAsArrayList(File fileSrc){
		ArrayList<String> alOut = new ArrayList<String>();
		try {
			FileReader frSrc = new FileReader(fileSrc);
			BufferedReader brPrefs = new BufferedReader(frSrc);
			String sLine;
			while((sLine = brPrefs.readLine()) != null){
				alOut.add(sLine);
			}
		} catch (IOException e) {
			System.out.println("IOException: getFileContentAsArrayList(" + fileSrc.toString() + ") " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception");
		}
		return alOut;
	}

	/**
	 * Return all settings-data-text from a file.
	 * Remove all text from the line after a "//" marker.
	 * Retrieve lines that are:
	 * 1) Not blank
	 * 2) Contain an equals symbol.
	 * @param sPath Path of the file.
	 * @return List of lines in the file.
	 */
	public ArrayList<String> getFileContentAsSettings(String sPath) {
		ArrayList<String> alOut = getFileContentAsArrayList(sPath); //, false, "//");
		StringFormatUtils sfu = new StringFormatUtils();
		for(int i = 0; i < alOut.size(); i++){
			String sLine = alOut.get(i);
			if(sLine.length() > 0){
				int iPos = sLine.indexOf("//");
				if(iPos != -1){
					sLine = sLine.substring(0, iPos);
				}
				sLine = sfu.trim(sLine);
				if((sLine.length() == 0) || (sLine.indexOf('=') == -1)){
					alOut.remove(i);
				}
			}
		}
		return alOut;
	}

	/**
	 * Create a file and fill it with content.
	 * @param sFileLoc The file to be written to - full path and name.
	 * @param sContent Text to be set in the file.
	 */
	public File setFileContent(String sFileLoc, ArrayList<String> asContent) {
		StringBuffer sb = new StringBuffer();
		for (int iLine = 0; iLine < asContent.size(); iLine++) {
			sb.append(asContent.get(iLine)).append("\r\n");
		}
		return setFileContent(sFileLoc, sb.toString());
	}

	/**
	 * Create a file and fill it with contents.
	 * @param sFileLoc The file to be written to - full path and name.
	 * @param sContent Text to be set in the file.
	 */
	public void appendFileContent(String sFileLoc, String sAdditionalContent) {
		try {
			String sContent = this.getFileContent(sFileLoc);
			setFileContent(sFileLoc, sContent + sAdditionalContent);
		} catch (Exception e) {
			System.out.println("Exception when running setFileContent(" + sFileLoc + ")");
		}
	}

	/**
	 * Create a file and fill it with content.
	 * @param sFileLoc The file to be written to - full path and name.
	 * @param sContent Text to be set in the file.
	 */
	public File setFileContent(String sFileLoc, String sContent) {
		/*
		File fileTrg = null;
		try {
			fileTrg = new File(sFileLoc);
			createNewFile(fileTrg);
			java.io.FileWriter fwOut = new java.io.FileWriter(fileTrg);
			java.io.BufferedWriter bwOut = new java.io.BufferedWriter(fwOut);
			bwOut.write(sContent);
			bwOut.close();
		} catch (IOException e) {
			System.out.println("IOException: setFileContent(" + sFileLoc + ")");
			fileTrg = null;
		} catch (Exception e) {
			System.out.println("Exception");
			fileTrg = null;
		}
		return fileTrg;
		 */
		File fileTrg = null;
		fileTrg = new File(sFileLoc);
		createNewFile(fileTrg);
		setFileContent(fileTrg, sContent);
		return fileTrg;
	}

	/**
	 * Create a file and fill it with contents.
	 * @param sFileLoc The file to be written to - full path and name.
	 * @param sContent Text to be set in the file.
	 */
	public void setFileContent(File fileTrg, String sContent) {
		try {
			java.io.FileWriter fwOut = new java.io.FileWriter(fileTrg);
			java.io.BufferedWriter bwOut = new java.io.BufferedWriter(fwOut);
			bwOut.write(sContent);
			bwOut.close();
		} catch (IOException e) {
			System.out.println("IOException: setFileContent(" + fileTrg.getAbsolutePath() + ")");
		} catch (Exception e) {
			System.out.println("Exception");
		}
	}

	public String getFileSuffix(String sFileNameTemp) {
		int iPos = sFileNameTemp.lastIndexOf('.');
		if(iPos == -1){
			return "";
		}
		return sFileNameTemp.substring(iPos + 1);
	}

	public String removeFileSuffix(String sFileNameTemp) {
		int iPos = sFileNameTemp.lastIndexOf('.');
		if(iPos == -1){
			return sFileNameTemp;
		}
		return sFileNameTemp.substring(0, iPos);
	}

	public String[] getAllFiles(String sFileBasePath) {
		return getAllFiles(sFileBasePath, "*");
	}

	public String[] getAllFiles(String sFileBasePath, String sValidSuffixes) {
		File dirPrnt = new File(sFileBasePath);
		if(!dirPrnt.exists()){
			return null;
		}

		if(!sValidSuffixes.equals("*")){
			sValidSuffixes = "," + sValidSuffixes + ",";
		}
		String asFoldersAndFiles[] = getAllFiles(dirPrnt, sValidSuffixes, this.maxFilesToList);
		return asFoldersAndFiles;
	}

	/**
	 * Create a java.io.File.
	 * Not much to it, but a handy interface.
	 */
	public File getFile(String sPathAndFileName){
		File f = new File(sPathAndFileName);
		return f;
	}

	/**
	 * Kick off the process. This is kept simple, because it starts a self-referencing method.
	 * @param dirPrnt Top level parent to be inspected.
	 * @param sValidSuffixes String containing suffixes to be returned. Eg "html,htm,jsp".
	 * @param iMaxFileListLength Maximum total number of files that can be returned.
	 * @return String array of all files in that folder.
	 */
	public String[] getAllFiles(File dirPrnt, String sValidSuffixes, int iMaxFileListLength) {
		return getAllFiles(dirPrnt, sValidSuffixes, iMaxFileListLength, 0);
	}

	//public String[] getAllFiles(String sDirParent) {
	//	File dirParent = new File(sDirParent);
	//	return getAllFiles(dirParent, "*", 500, 0);
	//}

	public String[] getAllFiles(File dirPrnt, String sValidSuffixes, int iMaxFileListLength, int iTotalFilesSoFar) {
		boolean isAnySuffix = sValidSuffixes.equals("*");
		String[] asFilesAndFolders = dirPrnt.list();
		ArrayList<String> alFiles = new ArrayList<String>();
		String sSep = getFilePathSeparator(dirPrnt.getPath());
		for(int iFoF = 0; iFoF < asFilesAndFolders.length; iFoF++){
			File f = new File(dirPrnt.getAbsolutePath() + sSep + asFilesAndFolders[iFoF]);
			if(!f.isDirectory()){
				String sSuffix = "." + f.getName();
				sSuffix = sSuffix.substring(sSuffix.lastIndexOf('.') + 1).toLowerCase();
				if(isAnySuffix || (sValidSuffixes.indexOf("," + sSuffix + ",") != -1)){
					if(iTotalFilesSoFar == iMaxFileListLength){
						System.out.println("-maxfiles count hit, winding up function (" + iTotalFilesSoFar + " files already added)");
					}
					alFiles.add(f.getPath());
					iTotalFilesSoFar++;
					if(iTotalFilesSoFar >= iMaxFileListLength){
						break;
					}
				}
			}
		}
		for(int iFoF = 0; iFoF < asFilesAndFolders.length; iFoF++){
			File f = new File(dirPrnt.getAbsolutePath() + sSep + asFilesAndFolders[iFoF]);
			if(f.isDirectory()){
				//String[] asInSub = getAllFiles(new File(f.getPath()), sValidSuffixes, ((iTotalFilesSoFar < iMaxFileListLength) ? 1 : -1), iTotalFilesSoFar);
				String[] asInSub = getAllFiles(new File(f.getPath()), sValidSuffixes, iMaxFileListLength, iTotalFilesSoFar);
				for(int iSub = 0; iSub < asInSub.length; iSub++){
					alFiles.add(asInSub[iSub]);
				}
				iTotalFilesSoFar += asInSub.length;
			}
		}
		String[] asFilesOut = new String[alFiles.size()];
		for(int iFilesFound = 0; iFilesFound < alFiles.size(); iFilesFound++){
			asFilesOut[iFilesFound] = alFiles.get(iFilesFound);
		}
		return asFilesOut;
	}

	public String getFilePathAndNameRelative(String sBasePath, String sFullPath) {
		ReplaceSubstring rs = new ReplaceSubstring();
		String sSep = getFilePathSeparator(sBasePath);
		if(sSep.equals("/")){
			sBasePath = rs.replaceSubstring(sBasePath, "\\", "/");
			sFullPath = rs.replaceSubstring(sFullPath, "\\", "/");
		} else {
			sBasePath = rs.replaceSubstring(sBasePath, "/", "\\");
			sFullPath = rs.replaceSubstring(sFullPath, "/", "\\");
		}
		String sOut = "";
		if(sFullPath.indexOf(sBasePath) == 0){
			sOut = sFullPath.substring(sBasePath.length());
		} else {
			if(sFullPath.indexOf(sBasePath) == 0){
				sOut = sFullPath.substring(sBasePath.length());
			}
		}
		if(sOut.startsWith(sSep)){
			sOut = sOut.substring(sSep.length());
		}
		return sOut;
	}

	public String getFilePathRelative(String sBasePath, String sFullPath) {
		String sOut = getFilePathAndNameRelative(sBasePath, sFullPath);
		String sSep = getFilePathSeparator(sBasePath);
		int iLastSep = sOut.lastIndexOf(sSep);
		if(iLastSep == -1){
			sOut = "";
		} else {
			sOut = sOut.substring(0, iLastSep);
		}
		return sOut;
	}

	public String getFilePathAbsolute(String sBasePath, String sRelativePath) {
		ReplaceSubstring rs = new ReplaceSubstring();
		ArrayUtils au = new ArrayUtils();
		sBasePath = rs.replaceSubstring(sBasePath, "\\", "/");
		sRelativePath = rs.replaceSubstring(sRelativePath, "\\", "/");

		ArrayList<String> alBaseParts = au.splitToArrayList(sBasePath, "/");
		if(alBaseParts.get(alBaseParts.size() - 1).length() == 0){
			alBaseParts.remove(alBaseParts.size() - 1);
		}
		ArrayList<String> alRelativeParts = au.splitToArrayList(sRelativePath, "/");
		int iShrinkage = 0;
		for(int iDotSearch = 0; iDotSearch < alRelativeParts.size(); iDotSearch++){
			if(alRelativeParts.get(iDotSearch).equals("..")){
				iShrinkage++;
			} else {
				break;
			}
		}
		StringBuffer sbOut = new StringBuffer();
		for(int iBase = 0; iBase < (alBaseParts.size() - iShrinkage); iBase++){
			sbOut.append(alBaseParts.get(iBase) + "/");
		}
		for(int iRelat = iShrinkage; iRelat < alRelativeParts.size(); iRelat++){
			if(iRelat != iShrinkage){
				sbOut.append("/");
			}
			sbOut.append(alRelativeParts.get(iRelat));
		}
		return sbOut.toString();
	}

	public String getFilePathAbsolute(String sRelativePath) {
		ReplaceSubstring rs = new ReplaceSubstring();
		ArrayUtils au = new ArrayUtils();
		sRelativePath = rs.replaceSubstring(sRelativePath, "\\", "/");
		ArrayList<String> alRelativeParts = au.splitToArrayList(sRelativePath, "/");
		StringBuffer sbOut = new StringBuffer();
		for (int iPart = 0; iPart < alRelativeParts.size(); iPart++) {
			if((alRelativeParts.get(iPart).length() > 0) && (!alRelativeParts.get(iPart).equals(".."))){
				if(sbOut.length() > 0){
					sbOut.append("/");
				}
				sbOut.append(alRelativeParts.get(iPart));
			}
		}
		return sbOut.toString();
	}

	public String getFilePathSeparator(String sDirPath) {
		sDirPath = sDirPath + "/\\";
		int iPosForwardSlash = sDirPath.indexOf("/");
		int iPosBackSlash = sDirPath.indexOf("\\");
		return (iPosForwardSlash < iPosBackSlash) ? "/" : "\\";
	}

	public boolean exists(String sFilePathAndName){
		File dirPrnt = new File(sFilePathAndName);
		return dirPrnt.exists();
	}

	public String replaceFilePathSeparator(String sPath, String sFilePathSeparator) {
		ReplaceSubstring rs = new ReplaceSubstring();
		String sFilePathSeparatorOther = (sFilePathSeparator.equals("/") ? "\\" : "/");
		return rs.replaceSubstring(sPath, sFilePathSeparatorOther, sFilePathSeparator);
	}

	public void createNewFile(File fileTemp) {
		try {
			if(!fileTemp.exists()){
				fileTemp.getParentFile().mkdirs();
				fileTemp.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getMaxFilesToList() {
		return this.maxFilesToList;
	}

	public void setMaxFilesToList(int iMaxFilesToList) {
		this.maxFilesToList = iMaxFilesToList;
	}

	public int deleteAllFilesInDir(String sFilePath) {
		String[] asFiles = getAllFiles(sFilePath);
		for (int iF = 0; iF < asFiles.length; iF++) {
			deleteFile(asFiles[iF]);
		}
		return asFiles.length;
	}

	public boolean deleteFile(String sFilePathAndName) {
		try {
			File f = new File(sFilePathAndName);
			f.delete();
		} catch (Exception e){}
		return !this.exists(sFilePathAndName);
	}

}
