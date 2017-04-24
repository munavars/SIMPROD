package com.ytc.common.model;

import java.util.List;
import java.util.Map;

public class ProgramPaidOn {
	
	private List<DropDown> tagItemList;
	
	private List<DropDown> tagValueList;
	
	private Map<String, List<String>> includedMap;
	
	private Map<String, List<String>> excludedMap;
	
	private Boolean isTiered;
	
	private Boolean isTrueUp;
	
	private String programDescription;
	
	public String getProgramDescription() {
		return programDescription;
	}
	public void setProgramDescription(String programDescription) {
		this.programDescription = programDescription;
	}
	public List<DropDown> getTagItemList() {
		return tagItemList;
	}
	public void setTagItemList(List<DropDown> tagItemList) {
		this.tagItemList = tagItemList;
	}
	public List<DropDown> getTagValueList() {
		return tagValueList;
	}
	public void setTagValueList(List<DropDown> tagValueList) {
		this.tagValueList = tagValueList;
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
	public Boolean getIsTiered() {
		return isTiered;
	}
	public void setIsTiered(Boolean isTiered) {
		this.isTiered = isTiered;
	}
	public Boolean getIsTrueUp() {
		return isTrueUp;
	}
	public void setIsTrueUp(Boolean isTrueUp) {
		this.isTrueUp = isTrueUp;
	}
	
}
