/**
 * 
 */
package com.ytc.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Cognizant
 *
 */
@Entity
@Table(name = "EMPLOYEE_HIERARCHY")
public class DalEmployeeHierarchy {
	
	private Integer rowWid;
	private String baseEmpId;
	private String baseEmpName;
	private String baseTitle;
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
	private String integrationId;
	private Calendar wInsertDt;
	private Calendar wUpdateDt;
	private String dataSource;
	private Integer fixedHierLvl;
	/**
	 * @return the rowWid
	 */
	@Id
	@Column(name = "ROW_WID")
	public Integer getRowWid() {
		return rowWid;
	}
	/**
	 * @param rowWid the rowWid to set
	 */
	public void setRowWid(Integer rowWid) {
		this.rowWid = rowWid;
	}
	/**
	 * @return the baseEmpId
	 */
	@Column(name = "BASE_EMP_ID")
	public String getBaseEmpId() {
		return baseEmpId;
	}
	/**
	 * @param baseEmpId the baseEmpId to set
	 */
	public void setBaseEmpId(String baseEmpId) {
		this.baseEmpId = baseEmpId;
	}
	/**
	 * @return the baseEmpName
	 */
	 @Column(name = "BASE_EMP_NAME")
	public String getBaseEmpName() {
		return baseEmpName;
	}
	/**
	 * @param baseEmpName the baseEmpName to set
	 */
	public void setBaseEmpName(String baseEmpName) {
		this.baseEmpName = baseEmpName;
	}
	/**
	 * @return the baseTitle
	 */
	@Column(name = "BASE_TITLE")
	public String getBaseTitle() {
		return baseTitle;
	}
	/**
	 * @param baseTitle the baseTitle to set
	 */
	public void setBaseTitle(String baseTitle) {
		this.baseTitle = baseTitle;
	}
	/**
	 * @return the lvl1EmpId
	 */
	@Column(name = "LVL1_EMP_ID")
	public String getLvl1EmpId() {
		return lvl1EmpId;
	}
	/**
	 * @param lvl1EmpId the lvl1EmpId to set
	 */
	public void setLvl1EmpId(String lvl1EmpId) {
		this.lvl1EmpId = lvl1EmpId;
	}
	/**
	 * @return the lvl1EmpName
	 */
	@Column(name = "LVL1_EMP_NAME")
	public String getLvl1EmpName() {
		return lvl1EmpName;
	}
	/**
	 * @param lvl1EmpName the lvl1EmpName to set
	 */
	public void setLvl1EmpName(String lvl1EmpName) {
		this.lvl1EmpName = lvl1EmpName;
	}
	/**
	 * @return the lvl1Title
	 */
	@Column(name = "LVL1_TITLE")
	public String getLvl1Title() {
		return lvl1Title;
	}
	/**
	 * @param lvl1Title the lvl1Title to set
	 */
	public void setLvl1Title(String lvl1Title) {
		this.lvl1Title = lvl1Title;
	}
	/**
	 * @return the lvl2EmpId
	 */
	@Column(name = "LVL2_EMP_ID")
	public String getLvl2EmpId() {
		return lvl2EmpId;
	}
	/**
	 * @param lvl2EmpId the lvl2EmpId to set
	 */
	public void setLvl2EmpId(String lvl2EmpId) {
		this.lvl2EmpId = lvl2EmpId;
	}
	/**
	 * @return the lvl2EmpName
	 */
	@Column(name = "LVL2_EMP_NAME")
	public String getLvl2EmpName() {
		return lvl2EmpName;
	}
	/**
	 * @param lvl2EmpName the lvl2EmpName to set
	 */
	public void setLvl2EmpName(String lvl2EmpName) {
		this.lvl2EmpName = lvl2EmpName;
	}
	/**
	 * @return the lvl2Title
	 */
	@Column(name = "LVL2_TITLE")
	public String getLvl2Title() {
		return lvl2Title;
	}
	/**
	 * @param lvl2Title the lvl2Title to set
	 */
	public void setLvl2Title(String lvl2Title) {
		this.lvl2Title = lvl2Title;
	}
	/**
	 * @return the lvl3EmpId
	 */
	@Column(name = "LVL3_EMP_ID")
	public String getLvl3EmpId() {
		return lvl3EmpId;
	}
	/**
	 * @param lvl3EmpId the lvl3EmpId to set
	 */
	public void setLvl3EmpId(String lvl3EmpId) {
		this.lvl3EmpId = lvl3EmpId;
	}
	/**
	 * @return the lvl3EmpName
	 */
	@Column(name = "LVL3_EMP_NAME")
	public String getLvl3EmpName() {
		return lvl3EmpName;
	}
	/**
	 * @param lvl3EmpName the lvl3EmpName to set
	 */
	public void setLvl3EmpName(String lvl3EmpName) {
		this.lvl3EmpName = lvl3EmpName;
	}
	/**
	 * @return the lvl3Title
	 */
	@Column(name = "LVL3_TITLE")
	public String getLvl3Title() {
		return lvl3Title;
	}
	/**
	 * @param lvl3Title the lvl3Title to set
	 */
	public void setLvl3Title(String lvl3Title) {
		this.lvl3Title = lvl3Title;
	}
	/**
	 * @return the lvl4EmpId
	 */
	@Column(name = "LVL4_EMP_ID")
	public String getLvl4EmpId() {
		return lvl4EmpId;
	}
	/**
	 * @param lvl4EmpId the lvl4EmpId to set
	 */
	public void setLvl4EmpId(String lvl4EmpId) {
		this.lvl4EmpId = lvl4EmpId;
	}
	/**
	 * @return the lvl4EmpName
	 */
	@Column(name = "LVL4_EMP_NAME")
	public String getLvl4EmpName() {
		return lvl4EmpName;
	}
	/**
	 * @param lvl4EmpName the lvl4EmpName to set
	 */
	public void setLvl4EmpName(String lvl4EmpName) {
		this.lvl4EmpName = lvl4EmpName;
	}
	/**
	 * @return the lvl4Title
	 */
	@Column(name = "LVL4_TITLE")
	public String getLvl4Title() {
		return lvl4Title;
	}
	/**
	 * @param lvl4Title the lvl4Title to set
	 */
	public void setLvl4Title(String lvl4Title) {
		this.lvl4Title = lvl4Title;
	}
	/**
	 * @return the lvl5EmpId
	 */
	@Column(name = "LVL5_EMP_ID")
	public String getLvl5EmpId() {
		return lvl5EmpId;
	}
	/**
	 * @param lvl5EmpId the lvl5EmpId to set
	 */
	public void setLvl5EmpId(String lvl5EmpId) {
		this.lvl5EmpId = lvl5EmpId;
	}
	/**
	 * @return the lvl5EmpName
	 */
	@Column(name = "LVL5_EMP_NAME")
	public String getLvl5EmpName() {
		return lvl5EmpName;
	}
	/**
	 * @param lvl5EmpName the lvl5EmpName to set
	 */
	public void setLvl5EmpName(String lvl5EmpName) {
		this.lvl5EmpName = lvl5EmpName;
	}
	/**
	 * @return the lvl5Title
	 */
	@Column(name = "LVL5_TITLE")
	public String getLvl5Title() {
		return lvl5Title;
	}
	/**
	 * @param lvl5Title the lvl5Title to set
	 */
	public void setLvl5Title(String lvl5Title) {
		this.lvl5Title = lvl5Title;
	}
	/**
	 * @return the integrationId
	 */
	@Column(name = "INTEGRATION_ID")
	public String getIntegrationId() {
		return integrationId;
	}
	/**
	 * @param integrationId the integrationId to set
	 */
	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}
	/**
	 * @return the wInsertDt
	 */
	@Column(name = "W_INSERT_DT")
	public Calendar getwInsertDt() {
		return wInsertDt;
	}
	/**
	 * @param wInsertDt the wInsertDt to set
	 */
	public void setwInsertDt(Calendar wInsertDt) {
		this.wInsertDt = wInsertDt;
	}
	/**
	 * @return the wUpdateDt
	 */
	@Column(name = "W_UPDATE_DT")
	public Calendar getwUpdateDt() {
		return wUpdateDt;
	}
	/**
	 * @param wUpdateDt the wUpdateDt to set
	 */
	public void setwUpdateDt(Calendar wUpdateDt) {
		this.wUpdateDt = wUpdateDt;
	}
	/**
	 * @return the dataSource
	 */
	@Column(name = "DATA_SOURCE")
	public String getDataSource() {
		return dataSource;
	}
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	/**
	 * @return the fixedHierLvl
	 */
	@Column(name = "FIXED_HIER_LVL")
	public Integer getFixedHierLvl() {
		return fixedHierLvl;
	}
	/**
	 * @param fixedHierLvl the fixedHierLvl to set
	 */
	public void setFixedHierLvl(Integer fixedHierLvl) {
		this.fixedHierLvl = fixedHierLvl;
	}
	
	
	

}
