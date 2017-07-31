package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SIM_EMPLOYEE_HIERARCHY")
@NamedQueries({
	@NamedQuery(name="DalSimEmployeeHierarchy.getHierarchyListBasedOnIdAndBU", query = "select o from DalSimEmployeeHierarchy o "
																				+ "where o.lvl1EmpId= :empId and o.empBusinessUnit = :businessUnit")
})
public class DalSimEmployeeHierarchy extends DalModel {

	/**
	 * Default Serial version
	 */
	private static final long serialVersionUID = 1L;

	private String lvl1EmpId;
	private String lvl1EmpName;
	private String lvl1Title;
	
	private String lvl2EmpId;
	private String lvl2EmpName;
	private String lvl2Title;
	
	private String lvl3EmpId;
	private String lvl3EmpName;
	private String lvl3Title;
	
	private String lvl4EmpId;
	private String lvl4EmpName;
	private String lvl4Title;
	
	private String lvl5EmpId;
	private String lvl5EmpName;
	private String lvl5Title;

	private String empBusinessUnit;

	@Column(name="LVL1_EMP_ID")
	public String getLvl1EmpId() {
		return lvl1EmpId;
	}

	public void setLvl1EmpId(String lvl1EmpId) {
		this.lvl1EmpId = lvl1EmpId;
	}

	@Column(name="LVL1_EMP_NAME")
	public String getLvl1EmpName() {
		return lvl1EmpName;
	}

	public void setLvl1EmpName(String lvl1EmpName) {
		this.lvl1EmpName = lvl1EmpName;
	}

	@Column(name="LVL1_TITLE")
	public String getLvl1Title() {
		return lvl1Title;
	}

	public void setLvl1Title(String lvl1Title) {
		this.lvl1Title = lvl1Title;
	}

	@Column(name="LVL2_EMP_ID")
	public String getLvl2EmpId() {
		return lvl2EmpId;
	}

	public void setLvl2EmpId(String lvl2EmpId) {
		this.lvl2EmpId = lvl2EmpId;
	}

	@Column(name="LVL2_EMP_NAME")
	public String getLvl2EmpName() {
		return lvl2EmpName;
	}

	public void setLvl2EmpName(String lvl2EmpName) {
		this.lvl2EmpName = lvl2EmpName;
	}

	@Column(name="LVL2_TITLE")
	public String getLvl2Title() {
		return lvl2Title;
	}

	public void setLvl2Title(String lvl2Title) {
		this.lvl2Title = lvl2Title;
	}

	@Column(name="LVL3_EMP_ID")
	public String getLvl3EmpId() {
		return lvl3EmpId;
	}

	public void setLvl3EmpId(String lvl3EmpId) {
		this.lvl3EmpId = lvl3EmpId;
	}

	@Column(name="LVL3_EMP_NAME")
	public String getLvl3EmpName() {
		return lvl3EmpName;
	}

	public void setLvl3EmpName(String lvl3EmpName) {
		this.lvl3EmpName = lvl3EmpName;
	}

	@Column(name="LVL3_TITLE")
	public String getLvl3Title() {
		return lvl3Title;
	}

	public void setLvl3Title(String lvl3Title) {
		this.lvl3Title = lvl3Title;
	}

	@Column(name="LVL4_EMP_ID")
	public String getLvl4EmpId() {
		return lvl4EmpId;
	}

	public void setLvl4EmpId(String lvl4EmpId) {
		this.lvl4EmpId = lvl4EmpId;
	}

	@Column(name="LVL4_EMP_NAME")
	public String getLvl4EmpName() {
		return lvl4EmpName;
	}

	public void setLvl4EmpName(String lvl4EmpName) {
		this.lvl4EmpName = lvl4EmpName;
	}

	@Column(name="LVL4_TITLE")
	public String getLvl4Title() {
		return lvl4Title;
	}

	public void setLvl4Title(String lvl4Title) {
		this.lvl4Title = lvl4Title;
	}

	@Column(name="LVL5_EMP_ID")
	public String getLvl5EmpId() {
		return lvl5EmpId;
	}

	public void setLvl5EmpId(String lvl5EmpId) {
		this.lvl5EmpId = lvl5EmpId;
	}

	@Column(name="LVL5_EMP_NAME")
	public String getLvl5EmpName() {
		return lvl5EmpName;
	}

	public void setLvl5EmpName(String lvl5EmpName) {
		this.lvl5EmpName = lvl5EmpName;
	}

	@Column(name="LVL5_TITLE")
	public String getLvl5Title() {
		return lvl5Title;
	}

	public void setLvl5Title(String lvl5Title) {
		this.lvl5Title = lvl5Title;
	}
	
	@Column(name="EMP_BUSINESS_UNIT")
	public String getEmpBusinessUnit() {
		return empBusinessUnit;
	}

	public void setEmpBusinessUnit(String empBusinessUnit) {
		this.empBusinessUnit = empBusinessUnit;
	}
}
