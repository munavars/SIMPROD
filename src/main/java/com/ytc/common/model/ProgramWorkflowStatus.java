package com.ytc.common.model;

import java.util.Date;

/**
 * Class : ProgramWorkflowStatus
 * Purpose : workflow status model used to hold the statuses of the workflow happened for the program till now.
 * @author Cognizant.
 *
 */
public class ProgramWorkflowStatus {
	private String approverName;
	private String approverRole;
	private String status;
	private Date approvalDate;
	
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getApproverRole() {
		return approverRole;
	}
	public void setApproverRole(String approverRole) {
		this.approverRole = approverRole;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
}
