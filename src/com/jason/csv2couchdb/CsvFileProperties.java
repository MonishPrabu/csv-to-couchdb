package com.jason.csv2couchdb;

public class CsvFileProperties {

	private String filePathAndName;
	private boolean hasHeaders;
	private String[] headerNames;
	
	public String getFilePathAndName() {
		return filePathAndName;
	}
	public void setFilePathAndName(String filePathAndName) {
		this.filePathAndName = filePathAndName;
	}
	public boolean getHasHeaders() {
		return hasHeaders;
	}
	public void setHasHeaders(boolean hasHeaders) {
		this.hasHeaders = hasHeaders;
	}
	public String[] getHeaderNames() {
		return headerNames;
	}
	public void setHeaderNames(String[] headerNames) {
		this.headerNames = headerNames;
	}
	
}
