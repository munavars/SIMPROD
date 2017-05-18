package com.ytc.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration class for maintaining the user level dynamically.
 * Usage : If there is change in the hierachy in the work flow levels, then inserting a new enum to the list should
 * not make major code changes.
 * @author Cognizant.
 *
 */
public enum ConsumerUserLevelEnum {
	FIRST_1("Account Manager",1),
	SECOND_1("Zone Manager",2),
	SECOND_2("DM",2),
	THIRD_1("Sr. Director, Consumer Sales",3);
	
	private static final Map<String, ConsumerUserLevelEnum> map = new HashMap<>(values().length, 0.75f);
	private final Integer level;
	private final String role;
	
	private ConsumerUserLevelEnum(String role, Integer level) {
		this.role = role;
		this.level = level;
	}
	
	static {
		for (ConsumerUserLevelEnum c : values()) map.put(c.getRole(), c);
	}
	
	public Integer getLevel() {
		return level;
	}
	
	public String getRole() {
		return role;
	}
	
	/**
	 * Method to get the user level based on role.
	 * @param role role.
	 * @return ConsumerUserLevelEnum i.e, enum instance of that particular role value.
	 */
	public static ConsumerUserLevelEnum getUserLevel(String role) {
		ConsumerUserLevelEnum result = map.get(role);
	    if (result == null) {
	      throw new IllegalArgumentException("Invalid tag id: " + role);
	    }
	    return result;
	  }
}
