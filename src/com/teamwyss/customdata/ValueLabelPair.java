package com.teamwyss.customdata;

public class ValueLabelPair {

	String value = "";
	String label = "";

	public ValueLabelPair(String sValue, String sLabel){
		this.value = sValue;
		this.label = sLabel;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
