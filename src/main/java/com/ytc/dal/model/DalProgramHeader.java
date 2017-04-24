package com.ytc.dal.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PROGRAM_HEADER")
public class DalProgramHeader  extends DalAuditableModel{
	
	
	private DalCustomer customer;
	
	private Integer accessPgmId;
	
	private String bu;
	
	private Integer requestTypeId;
	
	private Integer requestId;
	
	private Calendar requestDate;
	
	private String closedBy;
	
	private Calendar closedDate;
	
	private Integer statusId;
	
	private List<DalProgramDetail> dalProgramDetailList;

	@OneToOne
	@JoinColumn(name = "CUSTOMER_ID", referencedColumnName ="ID")
	public DalCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(DalCustomer customer) {
		this.customer = customer;
	}
	
	@OneToMany(mappedBy = "dalProgramHeader", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public List<DalProgramDetail> getDalProgramDetailList() {
		return dalProgramDetailList;
	}

	public void setDalProgramDetailList(List<DalProgramDetail> dalProgramDetailList) {
		this.dalProgramDetailList = dalProgramDetailList;
	}

	@Column(name = "ACCESS_PGM_ID")
	public Integer getAccessPgmId() {
		return accessPgmId;
	}

	public void setAccessPgmId(Integer accessPgmId) {
		this.accessPgmId = accessPgmId;
	}

	@Column(name = "BU")
	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	@Column(name = "REQUEST_TYPE_ID")
	public Integer getRequestTypeId() {
		return requestTypeId;
	}

	public void setRequestTypeId(Integer requestTypeId) {
		this.requestTypeId = requestTypeId;
	}

/*	@OneToOne
	@JoinColumn(name = "REQUEST_ID", referencedColumnName = "EMP_ID")*/
	@Column(name = "REQUEST_ID")
	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	@Column(name = "REQUEST_DATE")
	public Calendar getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Calendar requestDate) {
		this.requestDate = requestDate;
	}

	@Column(name = "CLOSED_BY")
	public String getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}

	@Column(name = "CLOSED_DATE")
	public Calendar getClosedDate() {
		return closedDate;
	}

	
	public void setClosedDate(Calendar closedDate) {
		this.closedDate = closedDate;
	}

	@Column(name = "STATUS_ID")
	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
}
