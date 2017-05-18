package com.ytc.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration class for maintaining Program status for consumer program type.
 * @author Cognizant.
 *
 */
public enum ConsumerProgramStatusEnum {

	INPROGRESS("IN PROGRESS", 1),
	PENDING("PENDING",2),
	ACTIVE("ACTIVE", 1),
	APPROVED("APPROVED",1),
	WAITING("WAITING",3),
	REJECTED("REJECTED", 1);
	
	private final String programStatus;
	private final Integer userLevel;

	private static final Map<String, ConsumerProgramStatusEnum> map = new HashMap<>(values().length, 0.75f);
	
	private ConsumerProgramStatusEnum(String programStatus, Integer userLevel) {
		this.programStatus = programStatus;
		this.userLevel = userLevel;
	}
	
	static {
		for (ConsumerProgramStatusEnum c : values()) map.put(c.getProgramStatus(), c);
	}

	public String getProgramStatus() {
		return programStatus;
	}
	
	public Integer getUserLevel() {
		return userLevel;
	}
	
	/**
	 * Method to get the program details based on current status.
	 * @param status status.
	 * @return ConsumerProgramStatusEnum i.e, enum instance of that particular status value.
	 */
	public static ConsumerProgramStatusEnum getProgramStatus(String status) {
		ConsumerProgramStatusEnum result = map.get(status);
	    if (result == null) {
	      throw new IllegalArgumentException("Invalid tag id: " + status);
	    }
	    return result;
	  }
}
