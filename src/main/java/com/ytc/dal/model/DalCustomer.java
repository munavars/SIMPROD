package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name = "CUSTOMER")
@NamedQueries({
	@NamedQuery(name="DalCustomer.getCustomerNumber", query = "select o from DalCustomer o where o.id=:id"),
	@NamedQuery(name="DalCustomer.getCustomerId", query = "select customerNumber from DalCustomer o where o.customerNumber=:customerNumber"),
	@NamedQuery(name="DalCustomer.getGroup", query = "select id,customerName from DalCustomer o where o.groupFlag=:groupFlag and o.id=:customerId"),
	@NamedQuery(name="DalCustomer.getCustomerName", query = "select o.id,o.customerName from DalCustomer o where o.id=:customerId"),
	@NamedQuery(name="DalCustomer.getCustomerFromCustomerNumber", query = "select o from DalCustomer o where o.customerNumber=:customerNumber")
})
public class DalCustomer extends DalAuditableModel {

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
	
	@Column(name = "CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "CUSTOMER_NUMBER")
	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	@Column(name = "ACCOUNT_MANAGER")
	public String getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}

	@Column(name = "BU")
	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	@Column(name = "PAY_TO")
	public String getPayTo() {
		return payTo;
	}

	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}

	@Column(name = "GROUP_FLAG")
	public String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}


	@Column(name = "DOC_NO")
	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
}
