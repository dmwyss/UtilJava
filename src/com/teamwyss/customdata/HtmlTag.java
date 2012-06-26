package com.teamwyss.customdata;

import java.util.HashMap;

public class HtmlTag {

	private String name = "";
	private HashMap<String, String> parameters = new HashMap<String, String>();
	private String body = "";

	/**
	 * Create a representation of a tag in an HTML document.
	 * @param sTagName
	 * @param alParams
	 * @param sTagBody
	 */
	public HtmlTag(String sTagName, HashMap<String, String> alParams, String sTagBody) {
		this.name = sTagName;
		this.parameters = alParams;
		this.body  = sTagBody;
	}

	public HtmlTag() {
		this.name = "";
		HashMap<String, String> alParamsTemp = new HashMap<String, String>();
		this.parameters = alParamsTemp;
		this.body  = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getValueForParameter(String sKey) {
		//for(int iParamCounter = 0; iParamCounter < this.parameters.size(); i)
		return parameters.get(sKey);
	}


}
