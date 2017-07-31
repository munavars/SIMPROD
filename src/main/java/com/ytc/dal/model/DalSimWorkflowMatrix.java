package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SIM_WORKFLOW_MATRIX")
@NamedQueries({
	@NamedQuery(name="DalSimWorkflowMatrix.getMatrixForBU", query = "select o from DalSimWorkflowMatrix o "
																+ "where o.businessUnit = :businessUnit order by id")
})
public class DalSimWorkflowMatrix extends DalModel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String businessUnit;
	
	private Integer hierarchyLevel;
	
	private Integer empId;
	
	private String dollarLimit;
	
	private String comments;

    @Column(name="BUSINESS_UNIT")
	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	@Column(name="HIERARCHY_LEVEL")
	public Integer getHierarchyLevel() {
		return hierarchyLevel;
	}

	public void setHierarchyLevel(Integer hierarchyLevel) {
		this.hierarchyLevel = hierarchyLevel;
	}

	@Column(name="EMP_ID")
	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	@Column(name="DOLLAR_LIMIT")
	public String getDollarLimit() {
		return dollarLimit;
	}

	public void setDollarLimit(String dollarLimit) {
		this.dollarLimit = dollarLimit;
	}

	@Column(name="COMMENTS")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
