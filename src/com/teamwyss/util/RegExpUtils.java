package com.teamwyss.util;

import java.util.regex.Pattern;
//import java.util.regex.Matcher;

public class RegExpUtils {

	public boolean isInteger(String sValue) {
		Pattern pat = Pattern.compile("^\\d{1,}$");
		return pat.matcher(sValue).find();
	}

	public boolean isNumber(String sValue) {
		Pattern pat = Pattern.compile("^(\\d+(\\.\\d+)?)$");
		return pat.matcher(sValue).find();
	}

}
