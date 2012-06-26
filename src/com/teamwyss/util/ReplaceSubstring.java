package com.teamwyss.util;

import java.util.ArrayList;

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
			sOut = replaceSubstring(sOut, (String)alFind.get(iReplaceCount), (String)alReplace.get((Math.min(iReplaceCount, (alReplace.size() - 1)))));
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

}
