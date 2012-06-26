package com.teamwyss.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.teamwyss.customdata.HtmlTag;

public class HtmlUtils {

	public HtmlTag getFirstTag(String sSuper, String sTagName){
		ArrayList<HtmlTag> alHtmlT = this.getTag(sSuper, sTagName);
		if(alHtmlT.size() == 0){
			HtmlTag ht = new HtmlTag();
			return ht;
		}
		return alHtmlT.get(0);
	}

	public ArrayList<HtmlTag> getTag(String sSuper, String sTagName){
		ArrayList<HtmlTag> alOut = new ArrayList<HtmlTag>();
		ArrayUtils au = new ArrayUtils();
		ReplaceSubstring rs = new ReplaceSubstring();
		String[] asDocSections = au.split(sSuper, "<" + sTagName);
		for(int iTag = 1; iTag < asDocSections.length; iTag++){
			String sSect = asDocSections[iTag];
			char cCharAfterName = sSect.charAt(0);
			if(" \t>".indexOf(cCharAfterName) == -1){
				continue;
			}
			int iPosBodyStart = sSect.indexOf(">");
			HashMap<String, String> alParamsKeyValuePairs = new HashMap<String, String>();
			String sTagHead = sSect.substring(0, iPosBodyStart);
			if(sTagHead.length() > 2){
				String[] asKvp = au.splitIgnoreInsideQuotes(sTagHead, " ");
				for(int iKvp = 0; iKvp < asKvp.length; iKvp++){
					String[] asKvpTemp = au.split(asKvp[iKvp], "=");
					if(asKvpTemp.length > 1){
						String sValueTemp = rs.replaceSubstring(asKvpTemp[1], "\"", "");
						alParamsKeyValuePairs.put(asKvpTemp[0], sValueTemp);
					}
				}
			}
			String sTagBody = "";
			if(iPosBodyStart != -1){
				char cCharBeforeFirstCloseTag = '#';
				if(iPosBodyStart > 0){
					cCharBeforeFirstCloseTag = sSect.charAt(iPosBodyStart - 1);
				}
				if(cCharBeforeFirstCloseTag == '/'){
					// the tag has no body
				} else {
					sTagBody = sSect.substring(iPosBodyStart + 1);
					int iPosBodyEnd = sTagBody.indexOf("</" + sTagName + ">");
					if(iPosBodyEnd != -1){
						sTagBody = sTagBody.substring(0, iPosBodyEnd);
					} else {
						sTagBody = "";
					}
				}
			}
			alOut.add(new HtmlTag(sTagName, alParamsKeyValuePairs, sTagBody));
		}
		return alOut;
	}

}
