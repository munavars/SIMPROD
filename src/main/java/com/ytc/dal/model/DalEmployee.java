package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE")
public class DalEmployee extends DalModel {
	
	/**
	 * default serial version.
	 */
	private static final long serialVersionUID = 1L;

	private String FIRST_NAME;
	
	private String LAST_NAME;
	
	private DalEmployeeTitle TITLE;
	
	private String BUSINESS_UNIT;
	
	private String EMAIL;
	
	private String ACTIVE;
	
	private String MANAGER_ID;
	
	private String LOGIN_ID;
		
	private String SECURITY;
	
	private String ROLE_ID;
	
	public DalEmployee() {
		
	}

	public DalEmployee(DalEmployee e) {

		FIRST_NAME = e.FIRST_NAME;
		LAST_NAME = e.LAST_NAME;
		TITLE = e.TITLE;
		BUSINESS_UNIT = e.BUSINESS_UNIT;
		EMAIL = e.EMAIL;
		ACTIVE = e.ACTIVE;
		MANAGER_ID = e.MANAGER_ID;
		LOGIN_ID = e.LOGIN_ID;
	}

	@Column(name="FIRST_NAME")
	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	@Column(name="LAST_NAME")
	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	@OneToOne
	@JoinColumn(name="TITLE_ID", referencedColumnName = "ID")
	public DalEmployeeTitle getTITLE() {
		return TITLE;
	}

	public void setTITLE(DalEmployeeTitle tITLE) {
		TITLE = tITLE;
	}

	@Column(name="BUSINESS_UNIT")
	public String getBUSINESS_UNIT() {
		return BUSINESS_UNIT;
	}

	public void setBUSINESS_UNIT(String bUSINESS_UNIT) {
		BUSINESS_UNIT = bUSINESS_UNIT;
	}

	@Column(name="EMAIL")
	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	@Column(name="ACTIVE")
	public String getACTIVE() {
		return ACTIVE;
	}

	public void setACTIVE(String aCTIVE) {
		ACTIVE = aCTIVE;
	}

	@Column(name="MANAGER_ID")
	public String getMANAGER_ID() {
		return MANAGER_ID;
	}

	public void setMANAGER_ID(String mANAGER_ID) {
		MANAGER_ID = mANAGER_ID;
	}

	@Column(name="LOGIN_ID")
	public String getLOGIN_ID() {
		return LOGIN_ID;
	}

	public void setLOGIN_ID(String lOGIN_ID) {
		LOGIN_ID = lOGIN_ID;
	}

	@Column(name="SECURITY")
	public String getSECURITY() {
		return SECURITY;
	}

	public void setSECURITY(String sECURITY) {
		SECURITY = sECURITY;
	}

	@Column(name="ROLE_ID")
	public String getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}

	

}
