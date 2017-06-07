package com.ytc.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum BusinessUnitDescriptionEnum {
	COMMERCIAL("T", "COMMERCIAL"),
	CONSUMER("P", "CONSUMER"),
	OTR("O", "OTR");
	
	private String businessUnitCode;
	private String businessUnitDescription;
	
	private static final Map<String, BusinessUnitDescriptionEnum> map = new HashMap<>(values().length, 0.75f);
	
	private BusinessUnitDescriptionEnum(String businessUnitCode, String businessUnitDescription) {
		this.businessUnitCode = businessUnitCode;
		this.businessUnitDescription = businessUnitDescription;
	}

	public String getBusinessUnitCode() {
		return businessUnitCode;
	}

	public String getBusinessUnitDescription() {
		return businessUnitDescription;
	}
	
	static {
		for (BusinessUnitDescriptionEnum c : values()) map.put(c.getBusinessUnitCode(), c);
	}

	
	/**
	 * Method to get business unit description based on the business unit code passed.
	 * @param code code.
	 * @return BusinessUnitDescriptionEnum i.e, enum instance of that particular code value.
	 */
	public static BusinessUnitDescriptionEnum getBUDescription(String code) {
/*		BusinessUnitDescriptionEnum result = map.get(code);
	    if (result == null) {
	      throw new IllegalArgumentException("Invalid tag id: " + code);
	    }*/
	    return map.get(code);
	  }
}
