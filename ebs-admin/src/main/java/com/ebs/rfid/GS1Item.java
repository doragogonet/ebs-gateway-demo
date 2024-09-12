package com.ebs.rfid;

public class GS1Item {

	private String code1 = "";
	
	private String code2 = "";
	
	private String code3 = "";
	
	private String gs1Type = "";
	
	private String filter = "";

	private String extension = "";

	private String jsonStr = "";

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getGs1Type() {
		return gs1Type;
	}

	public void setGs1Type(String gs1Type) {
		this.gs1Type = gs1Type;
	}

	public String getCode1() {
		return code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}

	public String getCode2() {
		return code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	public String getCode3() {
		return code3;
	}

	public void setCode3(String code3) {
		this.code3 = code3;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
