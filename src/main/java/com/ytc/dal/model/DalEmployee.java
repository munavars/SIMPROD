package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE")
public class DalEmployee {
	
	@Id
	@Column(name="EMP_ID")
	private Integer EMP_ID;
	
	@Column(name="FIRST_NAME")
	private String FIRST_NAME;
	
	@Column(name="LAST_NAME")
	private String LAST_NAME;
	
	@Column(name="TITLE_ID")
	private String TITLE_ID;
	
	@Column(name="BUSINESS_UNIT")
	private String BUSINESS_UNIT;
	
	@Column(name="EMAIL")
	private String EMAIL;
	
	@Column(name="ACTIVE")
	private String ACTIVE;
	
	@Column(name="MANAGER_ID")
	private String MANAGER_ID;
	
	@Column(name="LOGIN_ID")
	private String LOGIN_ID;
	
	public DalEmployee() {
		
	}

	public DalEmployee(DalEmployee e) {

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

	public void setTITLE_id(String tITLE_ID) {
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

	

}