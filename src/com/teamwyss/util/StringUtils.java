package com.teamwyss.util;

public class StringUtils {

	public String escapeMultiLineTextForJson(String sSuper) {
		String sFind = "\r\n";
		String sReplace = "\\n";
		return replaceSubstring(sSuper, sFind, sReplace);
	}


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
