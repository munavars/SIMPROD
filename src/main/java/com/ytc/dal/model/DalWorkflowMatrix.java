package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "WORKFLOW_MATRIX")
@NamedQueries({
	@NamedQuery(name="DalWorkflowMatrix.getMatrixForBU", query = "select o from DalWorkflowMatrix o "
																+ "where o.subjectArea like :programType and o.businessUnit = :businessUnit order by id")
})
public class DalWorkflowMatrix extends DalModel {

	/**
	 * Default Serial version.
	 */
	private static final long serialVersionUID = 1L;

    private String subjectArea;
    
    private String businessUnit;
    
    private Integer hierarchyLevel;
    
    private Integer empId;
    
    private String dollerLimit;
    
    private String comments;

    @Column(name="SUBJECT_AREA")
	public String getSubjectArea() {
		return subjectArea;
	}

	public void setSubjectArea(String subjectArea) {
		this.subjectArea = subjectArea;
	}

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
	public String getDollerLimit() {
		return dollerLimit;
	}

	public void setDollerLimit(String dollerLimit) {
		this.dollerLimit = dollerLimit;
	}

	@Column(name="COMMENTS")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}