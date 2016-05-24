package com.teamwyss.util.persistance;

import com.teamwyss.util.FileUtils;

public class Clob {

	private String filePathAndName = "";
	private String content = "";

	public Clob(String sFilePathAndName) {
		this.filePathAndName  = sFilePathAndName;
	}

	public String getFilePathAndName() {
		return this.filePathAndName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Object getContent() {
		return this.content;
	}

	public String[] save(String sFileContent) {
		FileUtils fu = new FileUtils();
		fu.setFileContent(this.filePathAndName, sFileContent);
		return new String[]{};
	}

	public String get(String sClobContent) {
		FileUtils fu = new FileUtils();
		return fu.getFileContent(this.filePathAndName);
	}
}
