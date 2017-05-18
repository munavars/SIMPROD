package com.ytc.common.model;

/**
 * Class : ProgramButton.class
 * Purpose : This class will be used to render the button in program details page.
 * @author Cognizant.
 *
 */
public class ProgramButton {

	private String userLevel;
	private boolean approver;
	private boolean creater;
	
	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	public boolean isApprover() {
		return approver;
	}
	public void setApprover(boolean approver) {
		this.approver = approver;
	}
	public boolean isCreater() {
		return creater;
	}
	public void setCreater(boolean creater) {
		this.creater = creater;
	}
}
