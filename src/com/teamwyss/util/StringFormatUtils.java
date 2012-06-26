package com.teamwyss.util;

import java.util.HashMap;

public class StringFormatUtils {
	private static final String WHITE_CHARACTERS = "\t\r\n ";
	private static final String[] WHITE_CHARACTER_ARRAY = new String[]{"\t","\r","\n"," "};

	/**
	 * The default trim type, removing whitespace from the start and finish.
	 * @param sIn Superstring to be trimmed.
	 * @return A trimmed string.
	 */
	public String trim(String sIn){
		return trimChars(sIn, WHITE_CHARACTERS);
	}

	/**
	 * The default trim type, removing whitespace from the start and finish.
	 * @param sIn Superstring to be trimmed.
	 * @return A trimmed string.
	 */
	public String[] trim(String[] asIn){
		for(int iCounter = 0; iCounter < asIn.length; iCounter++){
			asIn[iCounter] = trim(asIn[iCounter]);
		}
		return asIn;
	}

	/**
	 * Reduces strings with returns, tabs or multiple-spaces etc to just single white spaces.
	 * @param sIn Superstring to be cleaned.
	 * @return The cleaned string.
	 */
	public String cleanWhite(String sIn){
		ReplaceSubstring rs = new ReplaceSubstring();
		sIn = rs.replaceSubstring(sIn, WHITE_CHARACTER_ARRAY, new String[]{" "});
		return rs.replaceSubstring(sIn, "  ", " ");
	}

	/**
	 * Trim a specified char array from the start and end of a string.
	 * @param sIn The superstring to be trimmed.
	 * @param sCharsToRemove Sequence of characters to be trimmed. Each character will be converted to a char and removed separately.
	 * @return The trimmed string.
	 */
	public String trimChars(String sIn, String sCharsToRemove){
		sIn = trimLeadChars(sIn, sCharsToRemove);
		sIn = trimEndChars(sIn, sCharsToRemove);
		return sIn;
	}

	/**
	 * Just trim from the front.
	 * @param sIn The Superstring.
	 * @param cToRemove The character to remove.
	 * @return The trimmed string.
	 */
	public String trimLeadChar(String sIn, char cToRemove){
		return trimLeadChars(sIn, (new Character(cToRemove)).toString());
	}

	/**
	 * Removes any of the characters found in sCharsToRemove from the
	 * start of the main string (sIn).
	 * Note: This works on an array of characters, it does not remove
	 * a String from the main string. For example,
	 * ("ABC And ABC", "ACB") will return " And ABC", as the order of the characters
	 * does not matter.
	 * @param sIn String to have chars removed.
	 * @param sCharsToRemove String (char array) to be used as array of characters.
	 */
	public String trimLeadChars(String sIn, String sCharsToRemove){
		if(isEmpty(sIn)){
			return "";
		}
		if(sCharsToRemove == null){
			return sIn;
		}
		while((sIn.length() > 0) && (sCharsToRemove.indexOf(sIn.substring(0,1)) != -1)){
			sIn = sIn.substring(1);
		}
		return sIn;
	}

	/**
	 * Just trim from the end.
	 * @param sIn The Superstring.
	 * @param cToRemove The character to remove.
	 * @return The trimmed string.
	 */
	public String trimEndChar(String sIn, char cToRemove){
		return trimEndChars(sIn, (new Character(cToRemove)).toString());
	}

	/**
	 * Removes any of the characters found in sCharsToRemove from the
	 * end of the main string (sIn).
	 * Note: This works on an array of characters, it does not remove
	 * a String from the end of the main string. For example,
	 * ("And ABC", "ACB") will return "And ", as the order of the characters
	 * does not matter.
	 * @param sIn String to have chars removed.
	 * @param sCharsToRemove String (char array) to be used as array of characters.
	 */
	public String trimEndChars(String sIn, String sCharsToRemove){
		if(isEmpty(sIn)){
			return "";
		}
		if(sCharsToRemove == null){
			return sIn;
		}
		while((sIn.length() > 0) && (sCharsToRemove.indexOf(sIn.charAt(sIn.length() - 1)) != -1)){
			sIn = sIn.substring(0, sIn.length() - 1);
		}
		return sIn;
	}

	/**
	 * Find out if a String is null or empty.
	 * @param sIn String to check.
	 * @return Is the string null or empty.
	 */
	public boolean isEmpty(String sIn){
		return ((sIn == null) || (sIn.length() == 0));
	}

	/**
	 * Replace all whitespace with single spaces.
	 * Tabs, returns and new-lines are all replaced with single spaces.
	 * For example, "[return][tab][tab]hellow[space]" will return "[space]hellow[space]".
	 * @param sSuper
	 * @return
	 */
	public String normalizeWhitespace(String sSuper){
		if(isEmpty(sSuper)){
			return "";
		}
		sSuper = sSuper.replace('\t', ' ').replace('\n', ' ').replace('\r', ' ');
		StringBuffer sbOut = new StringBuffer();
		boolean thisWhite = true;
		boolean lastWhite = true;
		for(int iCharCounter = 0; iCharCounter < sSuper.length(); iCharCounter++){
			thisWhite = (sSuper.charAt(iCharCounter) == ' ');
			if(!(thisWhite && lastWhite)){
				sbOut.append(sSuper.charAt(iCharCounter));
			}
			lastWhite = thisWhite;
		}
		return sbOut.toString();
	}

	/**
	 * Check to see if an email address is valid.
	 * @param stringToTest String purporting to be an email addresss.
	 * @return String describing if is is valid. If the String is empty, it is valid.
	 */
	public String validateEmailAddress(String stringToTest) {
		if((stringToTest == null) || (stringToTest.length() == 0)){
			return "empty string";
		}
		String sIllegalChars = " !#$%^&*()}{][\"\':;<>,/?";
		for(int iChar = 0; iChar < sIllegalChars.length(); iChar++){
			if(stringToTest.indexOf(sIllegalChars.charAt(iChar)) != -1){
				return "illegal characters found";
			}
		}
		int iPosAt = stringToTest.indexOf("@");
		if(iPosAt < 1){
			return "too few characters before at symbol";
		}
		if(stringToTest.indexOf(".", iPosAt) < 1){
			return "too few characters between at-symbol and dot in domain name";
		}
		if(stringToTest.lastIndexOf(".") == (stringToTest.length() - 1)){
			return "dot at the end";
		}
		if(iPosAt != stringToTest.lastIndexOf("@")){
			return "multiple at symbols";
		}
		return "";
	}

	/**
	 * Returns a string to a guaranteed length.
	 * Filling the missing chars with zero.
	 * @param sIn Original string to begin with.
	 * @param iFinalLength How long should the returned string be?
	 * @return Formatted string.
	 */
	public String getWithLeadingZeros(String sIn, int iFinalLength){
		if(sIn == null){
			sIn = "";
		}
		for(int iCounter = sIn.length(); iCounter < iFinalLength; iCounter++){
			sIn = "0" + sIn;
		}
		return sIn;
	}

	/**
	 * Returns a string to a guaranteed length.
	 * Filling the missing chars with zero.
	 * @param sIn Original string to begin with.
	 * @param iFinalLength How long should the returned string be?
	 * @return Formatted string.
	 */
	public String getWithLeadingZeros(int iIn, int iFinalLength){
		return getWithLeadingZeros(Integer.toString(iIn), iFinalLength);
	}

	/**
	 * Returns a string to a guaranteed length.
	 * Filling the missing chars with zero.
	 * @param sIn Original string to begin with.
	 * @param iFinalLength How long should the returned string be?
	 * @return Formatted string.
	 */
	public String getWithLeadingZeros(long lngIn, int iFinalLength){
		return getWithLeadingZeros(Long.toString(lngIn), iFinalLength);
	}

	/**
	 * Manipulates Strings, reducing multiple characters together.
	 * For example ("ABCCCCCCCC", 3) returns "ABCCC".
	 * @param sIn String to be stripped back.
	 * @param iMaxRepeats Maximum number of repeated chars in a row.
	 * @return Manipulated string. Will be either same length of the orginal or shorter.
	 */
	public String reduceRepeatedChars(String sIn, int iMaxRepeats) {
		if(sIn == null){
			return "";
		}
		char cLast = 0;
		char cThis = 0;
		StringBuffer sbOut = new StringBuffer(sIn.length());
		int iFoundInARow = 0;
		for(int iChar = 0; iChar < sIn.length(); iChar++){
			cThis = sIn.charAt(iChar);
			if(cThis == cLast){
				iFoundInARow++;
			} else {
				iFoundInARow = 0;
				cLast = cThis;
			}
			if(iFoundInARow < iMaxRepeats){
				sbOut.append(cThis);
			}
		}
		return sbOut.toString();
	}

	/**
	 * When given a url, reduce it to the base url.
	 * For example, "http://www.here.com/dir" becomes "http://www.here.com".
	 * For example, "http://www.here.com" stays "http://www.here.com".
	 * For example, "www.here.com" stays "www.here.com".
	 * @param sUrl Sting to be manipulated.
	 * @return Url supplied, up to its first single slash.
	 */
	public String getUrlUntilFirstSingleSlash(String sUrl) {
		if((sUrl == null) || (sUrl.length() == 0)){
			return "";
		}
		int iPosMinEnd = sUrl.indexOf("//");
		if(iPosMinEnd > -1){
			iPosMinEnd += 2;
		}
		int iEnd = sUrl.indexOf("/", iPosMinEnd);
		if(iEnd != -1){
			sUrl = sUrl.substring(0, iEnd);
		}
		return sUrl;
	}

	/**
	 * Convert XML text to renderable text.
	 * @param sIn, XML format string to be returned.
	 */
	public String encodeXmlChars(String sIn){
		ReplaceSubstring rs = new ReplaceSubstring();
		String[] asFind = new String[]{"&", "<", ">", "\""};
		String[] asReplace = new String[]{"&amp;", "&lt;", "&gt;", "\\\""};
		return rs.replaceSubstring(sIn, asFind, asReplace);
	}

	/**
	 * Convert XML text to renderable text.
	 * @param sIn, XML format string to be returned.
	 */
	public String removeHtmlChars(String sIn){
		ReplaceSubstring rs = new ReplaceSubstring();
		String[] asFind = new String[]{"<", ">", "\""};
		String[] asReplace = new String[]{";", "", "", ""};
		return rs.replaceSubstring(sIn, asFind, asReplace);
	}

	/**
	 * Strip non alpha chars out of a String
	 * @param sIn Data in.
	 * @return Numeric only string.
	 */
	public String removeNonAlpha(String sIn) {
		String sLower = sIn.toLowerCase();
		StringBuffer sbOut = new StringBuffer(sIn.length());
		char cThis = 0;
		for(int iChar = 0; iChar < sIn.length(); iChar++){
			cThis = sLower.charAt(iChar);
			if((cThis >= 97) && (cThis <= 122)){
				sbOut.append(sIn.charAt(iChar));
			}
		}
		return sbOut.toString();
	}

	/**
	 * Determine if a String indicates true or false.
	 * Defaults to false if not able to be resolved.
	 * @param sIn String to inspect.
	 * @return boolean indicating true or false.
	 */
	public boolean toBoolean(String sIn) {
		if(isEmpty(sIn)){
			return false;
		}
		String sLower = sIn.toLowerCase();
		if("yes,true,on".indexOf(sLower) != -1){
			return true;
		}
		return false;
	}

	/**
	 * Determine if a String indicates true or false.
	 * Defaults to false if not able to be resolved.
	 * @param sIn String to inspect.
	 * @return boolean indicating true or false.
	 */
	public int toSafeInt(String sIn) {
		return toSafeInt(sIn, 0);
	}

	/**
	 * Determine if a String indicates true or false.
	 * Defaults to false if not able to be resolved.
	 * @param sIn String to inspect.
	 * @return boolean indicating true or false.
	 */
	public int toSafeInt(String sIn, int iDefault) {
		if(isEmpty(sIn)){
			return iDefault;
		}
		try {
			return Integer.parseInt(sIn);
		} catch (NumberFormatException nfe){
			sIn = removeNonAlpha(sIn);
			try {
				int iOut = Integer.parseInt(sIn);
				return iOut;
			} catch (Exception e){
				return iDefault;
			}
		}
	}

	/**
	 * Determine if a String indicates true or false.
	 * Defaults to false if not able to be resolved.
	 * @param sIn String to inspect.
	 * @return boolean indicating true or false.
	 */
	public String toSafeString(String sIn) {
		return toSafeString(sIn, "");
	}

	/**
	 * Determine if a String indicates true or false.
	 * Defaults to false if not able to be resolved.
	 * @param sIn String to inspect.
	 * @return boolean indicating true or false.
	 */
	public String toSafeString(String sIn, String sDefault) {
		if(isEmpty(sIn)){
			return sDefault;
		}
		return sIn;
	}

	/**
	 * Determine if a String indicates true or false.
	 * Defaults to false if not able to be resolved.
	 * @param sIn String to inspect.
	 * @return boolean indicating true or false.
	 */
	public boolean safeEquals(String sValueOne, String sValueTwo) {
		if(isEmpty(sValueOne)){
			sValueOne = "";
		}
		if(isEmpty(sValueTwo)){
			sValueTwo = "";
		}
		return sValueOne.toLowerCase().equals(sValueTwo.toLowerCase());
	}


	public HashMap<String, String> getQueryStringAsHashMap(String sIn){
		HashMap<String, String> smOut = new HashMap<String, String>();
		if(sIn.indexOf("?") != -1){
			sIn = sIn.substring(sIn.indexOf("?"));
		}
		String[] asIn = sIn.split("&");
		for(int iE = 0; iE < asIn.length; iE++){
			String sKey = "key" + iE;
			String sValue = "true";
			if(asIn[iE].indexOf("=") != -1){
				String[] asE = asIn[iE].split("=");
				sKey = trim(normalizeWhitespace(asE[0]));
				if(asE.length > 1){
					sValue = trim(normalizeWhitespace(asE[1]));
				} else {
					sValue = "";
				}
			} else {
				sKey = trim(normalizeWhitespace(asIn[iE]));
			}
			smOut.put(sKey, sValue);
		}
		return smOut;
	}

	/**
	 * Get the text inside two markers.
	 * For example, ("oneTWOthree", "one", "three") returns "TWO".
	 * @param sMaster Main string to search.
	 * @param sStart Start marker.
	 * @param sEnd End marker.
	 * @return String between the start and end strings.
	 */
	public String getStringBetween(String sMaster, String sStart, String sEnd){
		if(isEmpty(sMaster) || isEmpty(sStart) || isEmpty(sEnd)){
			return "";
		}
		int iPosStart = sMaster.indexOf(sStart);
		if(iPosStart == -1){
			return "";
		}
		int iPosEnd = sMaster.indexOf(sEnd, iPosStart);
		if(iPosEnd == -1){
			return "";
		}
		return sMaster.substring(iPosStart + (sStart.length()), iPosEnd);
	}

	/**
	 * Get the text outside two markers.
	 * For example, ("ZEROoneTWOthreeFOUR", "one", "three") returns "ZEROFOUR".
	 * @param sMaster Main string to search.
	 * @param sStart Start marker - where to start omitting text.
	 * @param sEnd End marker - where to end omitting text.
	 * @return String outside the start and end strings.
	 */
	public String getStringExcludingBetween(String sMaster, String sStart, String sEnd){
		if(isEmpty(sMaster)){
			return "";
		}
		if(isEmpty(sStart) || isEmpty(sEnd)){
			return sMaster;
		}
		int iPosStart = sMaster.indexOf(sStart);
		if(iPosStart == -1){
			return sMaster;
		}
		int iPosEnd = sMaster.indexOf(sEnd, iPosStart);
		if(iPosEnd == -1){
			return sMaster;
		}
		return sMaster.substring(0, iPosStart) + sMaster.substring(iPosEnd + sEnd.length());
	}

	public String camelToChar(String pageName, String sSep) {
		StringBuffer sbOut = new StringBuffer();
		boolean isPreviousCharASep = false;
		for (int iChar = 0; iChar < pageName.length(); iChar++) {
			char c = pageName.charAt(iChar);
			if((c >= 'A') && (c <= 'Z')){
				if(!isPreviousCharASep){
					sbOut.append(sSep);
				}
				sbOut.append(c);
				isPreviousCharASep = true;
			} else {
				sbOut.append(c);
				isPreviousCharASep = false;
			}
		}
		return sbOut.toString();
	}

	public String toLeadUpperCase(String sIn) {
		if(sIn == null || sIn.length() < 2){
			return sIn;
		}
		return sIn.substring(0, 1).toUpperCase() + sIn.substring(1);
	}

	public String replaceCharactersInsideQuotes(String sSuper, char charReplacer) {
		boolean isInsideQuotes = false;
		StringBuffer sbOut = new StringBuffer();
		for(int iC = 0; iC < sSuper.length(); iC++){
			char charTemp = sSuper.charAt(iC);
			if(charTemp == '"'){
				// Begin adding as quote
				isInsideQuotes = !isInsideQuotes;
			}
			if(isInsideQuotes){
				if(charTemp == '"'){
					sbOut.append('"'); // Case where we have just started quotes.
				} else {
					sbOut.append(charReplacer);
				}
			} else {
				sbOut.append(charTemp);
			}
		}
		return sbOut.toString();
	}
	
	public String escapeMultiLineTextForJson(String sSuper) {
		String sFind = "\r\n";
		String sReplace = "\\n";
		return (new ReplaceSubstring()).replaceSubstring(sSuper, sFind, sReplace);
	}

	public String removeMultiLineComments(String sSuper) {
		ArrayUtils au = new ArrayUtils();
		if(sSuper.indexOf("/*") != -1){
			sSuper = au.join(au.splitAtTwoPartSeparator(sSuper, "/*", "*/"), "");
		}
		return sSuper;
	}

	public String removeSingleLineComments(String sSuper) {
		ArrayUtils au = new ArrayUtils();
		if(sSuper.indexOf("//") != -1){
			sSuper = au.join(au.splitAtTwoPartSeparator(sSuper, "//", "\r\n"), "\r\n");
		}
		return sSuper;
	}
	
}
