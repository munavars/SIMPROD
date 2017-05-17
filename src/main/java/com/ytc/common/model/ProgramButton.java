package com.ytc.common.model;

/**
 * Class : ProgramButton.class
 * Purpose : This class will be used to render the button in program details page.
 * @author Cognizant.
 *
 */
public class ProgramButton {

	private String userLevel;
	private boolean edit;
	private boolean cancel;
	private boolean save;
	private boolean submit;
	private boolean reject;
	private boolean approve;
	private String[] disableButton = null;
	
	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public boolean isCancel() {
		return cancel;
	}
	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}
	public boolean isSave() {
		return save;
	}
	public void setSave(boolean save) {
		this.save = save;
	}
	public boolean isSubmit() {
		return submit;
	}
	public void setSubmit(boolean submit) {
		this.submit = submit;
	}
	public boolean isReject() {
		return reject;
	}
	public void setReject(boolean reject) {
		this.reject = reject;
	}
	public boolean isApprove() {
		return approve;
	}
	public void setApprove(boolean approve) {
		this.approve = approve;
	}
	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	public String[] getDisableButton() {
		return disableButton;
	}
	public void setDisableButton(String[] disableButton) {
		this.disableButton = disableButton;
	}
}
