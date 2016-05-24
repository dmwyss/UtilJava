package com.teamwyss.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceSubstring {
	/**
	 * Replace an array of strings with another array of strings.
	 * If the find-length is more than the replace-length, reuse the last replace item.
	 * @param sSuper
	 * @param asFind
	 * @param asReplace
	 * @return String with the find-strings-array-items substituted with the replace-string-array-items.
	 */
	public String replaceSubstring(String sSuper, String[] asFind, String[] asReplace) {
		String sOut = sSuper;
		for(int iReplaceCounter = 0; iReplaceCounter < asFind.length; iReplaceCounter++) {
			sOut = replaceSubstring(sOut, asFind[iReplaceCounter], asReplace[(Math.min(iReplaceCounter, (asReplace.length - 1)))]);
		}
		return sOut;
	}

	/**
	 * Replace an array of strings with a string.
	 * @param sSuper
	 * @param asFind
	 * @param sReplace
	 * @return String with the find-strings array items substituted with the replace-string.
	 */
	public String replaceSubstring(String sSuper, String[] asFind, String sReplace) {
		return replaceSubstring(sSuper, asFind, (new String[]{sReplace}));
	}

	/**
	 * Replace an array of strings with another array of strings.
	 * If the find-length is more than the replace-length, reuse the last replace item.
	 * @param sSuper
	 * @param asFind
	 * @param asReplace
	 * @return String with the find-strings-array-items substituted with the replace-string-array-items.
	 */
	public String replaceSubstring(String sSuper, ArrayList<String> alFind, ArrayList<String> alReplace) {
		String sOut = sSuper;
		for(int iReplaceCount = 0; iReplaceCount < alFind.size(); iReplaceCount++) {
			sOut = replaceSubstring(sOut, alFind.get(iReplaceCount), alReplace.get((Math.min(iReplaceCount, (alReplace.size() - 1)))));
		}
		return sOut;
	}

	/**
	 * Replace a find-string with a replace-string.
	 * @param sSuper
	 * @param sFind
	 * @param sReplace
	 * @return String with the find-string substituted with the replace-string.
	 */
	public String replaceSubstring(String sSuper, String sFind, String sReplace) {
		if((sSuper == null) || (sSuper.length() == 0)){
			return "";
		}
		if(sSuper.indexOf(sFind) == -1) {
			return sSuper; // sFind string was not found return unchanged.
		}
		StringBuffer sbOut = new StringBuffer();
		int iPos = 0;
		int iPosEnd = sSuper.indexOf(sFind);
		while(iPosEnd != -1) {
			sbOut.append(sSuper.substring(iPos, iPosEnd) + sReplace);
			iPos = iPosEnd + sFind.length();
			iPosEnd = sSuper.indexOf(sFind, iPos);
		}
		sbOut.append(sSuper.substring(iPos));
		String sOut = sbOut.toString();
		if((sReplace.indexOf(sFind) == -1) // Check sReplace does not contain sFind, else do not recurse.
		&& (sOut.indexOf(sFind) != -1)){ // Still contains the find string.
			sOut = replaceSubstring(sOut, sFind, sReplace); // Recurse.
		}
		return sOut;
	}

	/**
	 * Replace a find-string with a replace-string.
	 * @param sSuper
	 * @param sFind
	 * @param sReplace
	 * @return String with the find-string substituted with the replace-string.
	 */
	public String replaceSubstringRecursive(String sSuper, String sFind, String sReplace) {
		if((sSuper == null) || (sSuper.length() == 0)){
			return "";
		} else if(sReplace.indexOf(sFind) != -1){
			// The find string is inside the replace string. This will cause infinite loop.
			return sSuper;
		}
		while(sSuper.indexOf(sFind) != -1) {
			sSuper = replaceSubstring(sSuper, sFind, sReplace); // sFind string was not found return unchanged.
		}
		return sSuper;
	}

	public String replaceUsingRegExp(String sMaster, String regexpFind, String sReplace) {
		Pattern p = Pattern.compile(regexpFind);
		Matcher m = p.matcher(sMaster);
		return m.replaceAll(sReplace);
	}

	public String replaceUsingRegExp(String sMaster, ArrayList<String> alRegExpFind, ArrayList<String> alsReplace) {
		for(int iR = 0; iR < alRegExpFind.size(); iR++){
			sMaster = replaceUsingRegExp(sMaster, alRegExpFind.get(iR), alsReplace.get(iR));
		}
		return sMaster;
	}

	public String replaceUsingRegExp(String sMaster, ArrayList<String> alRegExpFind, String sReplace) {
		for(int iR = 0; iR < alRegExpFind.size(); iR++){
			sMaster = replaceUsingRegExp(sMaster, alRegExpFind.get(iR), sReplace);
		}
		return sMaster;
	}

}
