package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EXCEPTION_WF_MATRIX")
public class DalWorkflowExceptionMatrix extends DalModel{
	
	/**
	 * Default Serial version.
	 */
	private static final long serialVersionUID = 1L;

	private String customerNumbers;
	
	private DalWorkflowMatrix dalWorkflowMatrix;

	@Column(name = "CUSTOMER_NO")
	public String getCustomerNumbers() {
		return customerNumbers;
	}

	public void setCustomerNumbers(String customerNumbers) {
		this.customerNumbers = customerNumbers;
	}
	
	@OneToOne
	@JoinColumn(name = "WF_MATRIX_ID", referencedColumnName = "ID")
	public DalWorkflowMatrix getDalWorkflowMatrix() {
		return dalWorkflowMatrix;
	}

	public void setDalWorkflowMatrix(DalWorkflowMatrix dalWorkflowMatrix) {
		this.dalWorkflowMatrix = dalWorkflowMatrix;
	}
}
