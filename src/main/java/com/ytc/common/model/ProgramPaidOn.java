package com.ytc.common.model;

import java.util.List;
import java.util.Map;

public class ProgramPaidOn {
	
	private List<DropDown> tagItemList;
	
	private Map<String, List<String>> includedMap;
	
	private Map<String, List<String>> excludedMap;
	
	private Boolean isTiered;
	
	private Boolean isTrueUp;

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
