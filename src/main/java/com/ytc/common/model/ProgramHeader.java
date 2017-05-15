package com.ytc.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgramHeader extends Model{
	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/** common fields used in Create and Edit program - Begin*/
	private String customerName;
	private Integer customerId;
	private String businessUnit;
	private Integer requestId;
	private String requestedBy;
	private Date requestedDate;
	private String createdBy;
	private Date createdDate;
	private String status;
	
	private String zoneManagerApprovedBy;
	private Date zoneManagerApprovedDate;
	private String directorApprovedBy;
	private Date directorApprovedDate;
	private String executiveApprovedBy;
	private Date executiveApprovedDate;
	private String tbpApprovedBy;
	private Date tbpApprovedDate;
	
	private BigDecimal accrualAmount;
	private BigDecimal paidAmount;
	private BigDecimal balance;
	/** common fields used in Create and Edit program - End*/
	
	/** For save/submit logic checks*/
	private boolean isNewProgram;
	private String action;
	private boolean isSuccess;
	private String authorizedUser;
	private boolean calculatedProgram;
	
	private List<ProgramDetail> programDetailList = new ArrayList<ProgramDetail>();
	
	public List<ProgramDetail> getProgramDetailList() {
		return programDetailList;
	}
	public void setProgramDetailList(List<ProgramDetail> programDetailList) {
		this.programDetailList = programDetailList;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public Date getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getZoneManagerApprovedBy() {
		return zoneManagerApprovedBy;
	}
	public void setZoneManagerApprovedBy(String zoneManagerApprovedBy) {
		this.zoneManagerApprovedBy = zoneManagerApprovedBy;
	}
	public Date getZoneManagerApprovedDate() {
		return zoneManagerApprovedDate;
	}
	public void setZoneManagerApprovedDate(Date zoneManagerApprovedDate) {
		this.zoneManagerApprovedDate = zoneManagerApprovedDate;
	}
	public String getDirectorApprovedBy() {
		return directorApprovedBy;
	}
	public void setDirectorApprovedBy(String directorApprovedBy) {
		this.directorApprovedBy = directorApprovedBy;
	}
	public Date getDirectorApprovedDate() {
		return directorApprovedDate;
	}
	public void setDirectorApprovedDate(Date directorApprovedDate) {
		this.directorApprovedDate = directorApprovedDate;
	}
	public String getExecutiveApprovedBy() {
		return executiveApprovedBy;
	}
	public void setExecutiveApprovedBy(String executiveApprovedBy) {
		this.executiveApprovedBy = executiveApprovedBy;
	}
	public Date getExecutiveApprovedDate() {
		return executiveApprovedDate;
	}
	public void setExecutiveApprovedDate(Date executiveApprovedDate) {
		this.executiveApprovedDate = executiveApprovedDate;
	}
	public String getTbpApprovedBy() {
		return tbpApprovedBy;
	}
	public void setTbpApprovedBy(String tbpApprovedBy) {
		this.tbpApprovedBy = tbpApprovedBy;
	}
	public Date getTbpApprovedDate() {
		return tbpApprovedDate;
	}
	public void setTbpApprovedDate(Date tbpApprovedDate) {
		this.tbpApprovedDate = tbpApprovedDate;
	}
	public BigDecimal getAccrualAmount() {
		return accrualAmount;
	}
	public void setAccrualAmount(BigDecimal accrualAmount) {
		this.accrualAmount = accrualAmount;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public boolean isNewProgram() {
		return isNewProgram;
	}
	public void setNewProgram(boolean isNewProgram) {
		this.isNewProgram = isNewProgram;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getAuthorizedUser() {
		return authorizedUser;
	}
	public void setAuthorizedUser(String authorizedUser) {
		this.authorizedUser = authorizedUser;
	}
	public boolean isCalculatedProgram() {
		return calculatedProgram;
	}
	public void setCalculatedProgram(boolean calculatedProgram) {
		this.calculatedProgram = calculatedProgram;
	}
}
