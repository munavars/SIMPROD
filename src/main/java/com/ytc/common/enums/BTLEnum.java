package com.ytc.common.enums;

public enum BTLEnum {
	YES("Yes"),
	NO("No");
	
	private String btlOption;
	
	BTLEnum(String btlOption){
		this.btlOption = btlOption;
	}

	public String getBtlOption() {
		return btlOption;
	}

	public void setBtlOption(String btlOption) {
		this.btlOption = btlOption;
	}
}
