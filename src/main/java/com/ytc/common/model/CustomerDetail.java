package com.ytc.common.model;

public class CustomerDetail extends Model {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String customerNumber;
	
	private String customerName;
	
	private String accountManager;
	
	private String bu;
	
	private String payTo;
	
	private String groupFlag;
	
	private String docNo;
	
	private EmployeeHierarchy employeeHierarchy;
	
	public CustomerDetail() {

	}	

	/**
	 * @return the customerNumber
	 */
	public String getCustomerNumber() {
		return customerNumber;
	}




	/**
	 * @param customerNumber the customerNumber to set
	 */
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}




	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}




	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}




	/**
	 * @return the accountManager
	 */
	public String getAccountManager() {
		return accountManager;
	}




	/**
	 * @param accountManager the accountManager to set
	 */
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}




	/**
	 * @return the bu
	 */
	public String getBu() {
		
		return ("P".equalsIgnoreCase(bu)?"Consumer":"Customer");
		//return bu;
	}




	/**
	 * @param bu the bu to set
	 */
	public void setBu(String bu) {
		this.bu = bu;
	}




	/**
	 * @return the payTo
	 */
	public String getPayTo() {
		return payTo;
	}




	/**
	 * @param payTo the payTo to set
	 */
	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}




	/**
	 * @return the groupFlag
	 */
	public String getGroupFlag() {
		return groupFlag;
	}




	/**
	 * @param groupFlag the groupFlag to set
	 */
	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}




	/**
	 * @return the docNo
	 */
	public String getDocNo() {
		return docNo;
	}




	/**
	 * @param docNo the docNo to set
	 */
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}




	/**
	 * @return the employeeHierarchy
	 */
	public EmployeeHierarchy getEmployeeHierarchy() {
		return employeeHierarchy;
	}




	/**
	 * @param employeeHierarchy the employeeHierarchy to set
	 */
	public void setEmployeeHierarchy(EmployeeHierarchy employeeHierarchy) {
		this.employeeHierarchy = employeeHierarchy;
	}
	
	


}
