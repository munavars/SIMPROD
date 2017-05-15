package com.ytc.common.model;

public class Employee extends Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer EMP_ID;
	private String FIRST_NAME;
	private String LAST_NAME;
	private String TITLE_ID;
	private String BUSINESS_UNIT;
	private String EMAIL;
	private String ACTIVE;
	private String MANAGER_ID;
	private String LOGIN_ID;
	private String SECURITY;
	
	public Employee() {
		
	}

	public Employee(Employee e) {

		EMP_ID = e.EMP_ID;
		FIRST_NAME = e.FIRST_NAME;
		LAST_NAME = e.LAST_NAME;
		TITLE_ID = e.TITLE_ID;
		BUSINESS_UNIT = e.BUSINESS_UNIT;
		EMAIL = e.EMAIL;
		ACTIVE = e.ACTIVE;
		MANAGER_ID = e.MANAGER_ID;
		LOGIN_ID = e.LOGIN_ID;
	}

	public Integer getEMP_ID() {
		return EMP_ID;
	}

	public void setEMP_ID(Integer eMP_ID) {
		EMP_ID = eMP_ID;
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	public String getTITLE_ID() {
		return TITLE_ID;
	}

	public void setTITLE_ID(String tITLE_ID) {
		TITLE_ID = tITLE_ID;
	}

	public String getBUSINESS_UNIT() {
		return BUSINESS_UNIT;
	}

	public void setBUSINESS_UNIT(String bUSINESS_UNIT) {
		BUSINESS_UNIT = bUSINESS_UNIT;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getACTIVE() {
		return ACTIVE;
	}

	public void setACTIVE(String aCTIVE) {
		ACTIVE = aCTIVE;
	}

	public String getMANAGER_ID() {
		return MANAGER_ID;
	}

	public void setMANAGER_ID(String mANAGER_ID) {
		MANAGER_ID = mANAGER_ID;
	}

	public String getLOGIN_ID() {
		return LOGIN_ID;
	}

	public void setLOGIN_ID(String lOGIN_ID) {
		LOGIN_ID = lOGIN_ID;
	}

	public String getSECURITY() {
		return SECURITY;
	}

	public void setSECURITY(String sECURITY) {
		SECURITY = sECURITY;
	}
	

}
