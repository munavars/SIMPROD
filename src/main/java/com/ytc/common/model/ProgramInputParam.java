package com.ytc.common.model;

/**
 * Class : ProgramInputParam
 * Purpose : This class is used to capture the input parameter for program detail page during get details or create program methods.
 * @author Cognizant.
 *
 */
public class ProgramInputParam {
	
	private Integer programDetailId;
	private Integer customerId;
	private Employee employee;
	private Integer programTypeId;
	private String programType;
	
	public Integer getProgramDetailId() {
		return programDetailId;
	}
	public void setProgramDetailId(Integer programDetailId) {
		this.programDetailId = programDetailId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Integer getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(Integer programTypeId) {
		this.programTypeId = programTypeId;
	}
	public String getProgramType() {
		return programType;
	}
	public void setProgramType(String programType) {
		this.programType = programType;
	}
}
