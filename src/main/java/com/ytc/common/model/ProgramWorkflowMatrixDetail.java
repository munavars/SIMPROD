package com.ytc.common.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProgramWorkflowMatrixDetail {
	private Integer totalLevel;
	private Integer currentUserLevel;
	private Map<Integer, Integer[]> approverLevelList = new LinkedHashMap<Integer,Integer[]>();
	
	public Integer getTotalLevel() {
		return totalLevel;
	}
	public void setTotalLevel(Integer totalLevel) {
		this.totalLevel = totalLevel;
	}
	public Integer getCurrentUserLevel() {
		return currentUserLevel;
	}
	public void setCurrentUserLevel(Integer currentUserLevel) {
		this.currentUserLevel = currentUserLevel;
	}
	public Map<Integer, Integer[]> getApproverLevelList() {
		return approverLevelList;
	}
	public void setApproverLevelList(Map<Integer, Integer[]> approverLevelList) {
		this.approverLevelList = approverLevelList;
	}
}
