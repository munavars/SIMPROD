package com.ytc.common.model;

import java.util.List;
import java.util.Map;

public class ProgramAchieveOn {

	private List<DropDown> achieveBasedOnList;
	
	private String achieveBasedOn;
	
	private List<DropDown> achieveFrequencyList;
	
	private String achieveFrequency;
	
	private List<DropDown> tagItemList;
	
	private Map<String, List<String>> includedMap;
	
	private Map<String, List<String>> excludedMap;

	public List<DropDown> getAchieveBasedOnList() {
		return achieveBasedOnList;
	}

	public void setAchieveBasedOnList(List<DropDown> achieveBasedOnList) {
		this.achieveBasedOnList = achieveBasedOnList;
	}

	public String getAchieveBasedOn() {
		return achieveBasedOn;
	}

	public void setAchieveBasedOn(String achieveBasedOn) {
		this.achieveBasedOn = achieveBasedOn;
	}

	public List<DropDown> getAchieveFrequencyList() {
		return achieveFrequencyList;
	}

	public void setAchieveFrequencyList(List<DropDown> achieveFrequencyList) {
		this.achieveFrequencyList = achieveFrequencyList;
	}

	public String getAchieveFrequency() {
		return achieveFrequency;
	}

	public void setAchieveFrequency(String achieveFrequency) {
		this.achieveFrequency = achieveFrequency;
	}

	public List<DropDown> getTagItemList() {
		return tagItemList;
	}

	public void setTagItemList(List<DropDown> tagItemList) {
		this.tagItemList = tagItemList;
	}

	public Map<String, List<String>> getIncludedMap() {
		return includedMap;
	}

	public void setIncludedMap(Map<String, List<String>> includedMap) {
		this.includedMap = includedMap;
	}

	public Map<String, List<String>> getExcludedMap() {
		return excludedMap;
	}

	public void setExcludedMap(Map<String, List<String>> excludedMap) {
		this.excludedMap = excludedMap;
	}
}
