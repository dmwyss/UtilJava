package com.teamwyss.util;

import java.util.HashMap;

public class Pooties {

	private static final HashMap<String, String> data = new HashMap<String, String>();
	{
		data.put("project.pathRoot.pc",  "E:/Data/Projects/Webume");
		data.put("project.pathRoot.mac", "/Users/Shared/Data/Projects/Webume/");
		data.put("file.parentHtml.path", "WebRoot/htm/");
		data.put("file.parentHtml.name", "DavidWyss_Webume_Master.htm");
	}

	public String getProp(String sName){
		String sOut = data.get(sName);
		return sOut == null ? "" : sOut;
	}

}
