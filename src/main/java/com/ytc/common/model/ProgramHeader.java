package com.ytc.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	private String userComments;
	private Map<Integer, List<Integer>> tagItemsMap ;
	
	/** For email link*/
	private String contextPath;
	private String url;
	private boolean isDuplicate;
	private String errorMessage;
	/** For email link*/
	
	private ProgramButton programButton = new ProgramButton();
	private List<ProgramDetail> programDetailList = new ArrayList<ProgramDetail>();
	
	private List<ProgramWorkflowStatus> programWorkflowStatusList;
		
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
	public ProgramButton getProgramButton() {
		return programButton;
	}
	public void setProgramButton(ProgramButton programButton) {
		this.programButton = programButton;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserComments() {
		return userComments;
	}
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}
	public List<ProgramWorkflowStatus> getProgramWorkflowStatusList() {
		return programWorkflowStatusList;
	}
	public void setProgramWorkflowStatusList(List<ProgramWorkflowStatus> programWorkflowStatusList) {
		this.programWorkflowStatusList = programWorkflowStatusList;
	}
	public boolean isDuplicate() {
		return isDuplicate;
	}
	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}
	public Map<Integer, List<Integer>> getTagItemsMap() {
		return tagItemsMap;
	}
	public void setTagItemsMap(Map<Integer, List<Integer>> tagItemsMap) {
		this.tagItemsMap = tagItemsMap;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
