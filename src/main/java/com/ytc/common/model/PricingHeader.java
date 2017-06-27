/**
 * 
 */
package com.ytc.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ArunP
 *
 */
public class PricingHeader extends Model{

	
	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	/**Header information - Begin*/
	private String businessUnitDescription;
	private String businessUnit;
	private String requestedByName;
	private String requestedByTitle;
	private String requestedByDate;
	private String approvedByName;
	private String approvedByTitle;
	private String approvedByDate;
	/**Header information - End*/
	
	private String customerType;
	private String customerGroup;
	private String customerId;
	private String customerGroupValue;
	private String userComments;
	private String termCode;
	private String definition;
	private String dueDates;
	private String cash;
	private String shippingReqs;
	private String otherShippingReqs;
	private List<PricingDetail> pricingDetailList = new ArrayList<PricingDetail>();
	private PricingDetailsDropDown dropdownList;
	private boolean isSuccess;
	private String validationMessage;
	private String status;
	
	private ProgramButton programButton = new ProgramButton();
	private boolean isNewProgram;
	private List<ProgramWorkflowStatus> programWorkflowStatusList;
	private String approverComments;
	/** For email link*/
	private String url;
	private String contextPath;
	/** For email link*/
	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return the dropdownList
	 */
	public PricingDetailsDropDown getDropdownList() {
		return dropdownList;
	}
	/**
	 * @param dropdownList the dropdownList to set
	 */
	public void setDropdownList(PricingDetailsDropDown dropdownList) {
		this.dropdownList = dropdownList;
	}
	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the userComments
	 */
	public String getUserComments() {
		return userComments;
	}
	/**
	 * @param userComments the userComments to set
	 */
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}
	/**
	 * @return the termCode
	 */
	public String getTermCode() {
		return termCode;
	}
	/**
	 * @param termCode the termCode to set
	 */
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	/**
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}
	/**
	 * @param definition the definition to set
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	/**
	 * @return the dueDates
	 */
	public String getDueDates() {
		return dueDates;
	}
	/**
	 * @param dueDates the dueDates to set
	 */
	public void setDueDates(String dueDates) {
		this.dueDates = dueDates;
	}
	/**
	 * @return the cash
	 */
	public String getCash() {
		return cash;
	}
	/**
	 * @param cash the cash to set
	 */
	public void setCash(String cash) {
		this.cash = cash;
	}
	/**
	 * @return the shippingReqs
	 */
	public String getShippingReqs() {
		return shippingReqs;
	}
	/**
	 * @param shippingReqs the shippingReqs to set
	 */
	public void setShippingReqs(String shippingReqs) {
		this.shippingReqs = shippingReqs;
	}
	/**
	 * @return the otherShippingReqs
	 */
	public String getOtherShippingReqs() {
		return otherShippingReqs;
	}
	/**
	 * @param otherShippingReqs the otherShippingReqs to set
	 */
	public void setOtherShippingReqs(String otherShippingReqs) {
		this.otherShippingReqs = otherShippingReqs;
	}
	/**
	 * @return the pricingDetailList
	 */
	public List<PricingDetail> getPricingDetailList() {
		return pricingDetailList;
	}
	/**
	 * @param pricingDetailList the pricingDetailList to set
	 */
	public void setPricingDetailList(List<PricingDetail> pricingDetailList) {
		this.pricingDetailList = pricingDetailList;
	}
	/**
	 * @return the customerGroup
	 */
	public String getCustomerGroup() {
		return customerGroup;
	}
	/**
	 * @param customerGroup the customerGroup to set
	 */
	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}
	/**
	 * @return the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}
	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	/**
	 * @return the validationMessage
	 */
	public String getValidationMessage() {
		return validationMessage;
	}
	/**
	 * @param validationMessage the validationMessage to set
	 */
	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}
	public String getBusinessUnitDescription() {
		return businessUnitDescription;
	}
	public void setBusinessUnitDescription(String businessUnitDescription) {
		this.businessUnitDescription = businessUnitDescription;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public String getRequestedByName() {
		return requestedByName;
	}
	public void setRequestedByName(String requestedByName) {
		this.requestedByName = requestedByName;
	}
	public String getRequestedByTitle() {
		return requestedByTitle;
	}
	public void setRequestedByTitle(String requestedByTitle) {
		this.requestedByTitle = requestedByTitle;
	}
	public String getRequestedByDate() {
		return requestedByDate;
	}
	public void setRequestedByDate(String requestedByDate) {
		this.requestedByDate = requestedByDate;
	}
	public String getApprovedByName() {
		return approvedByName;
	}
	public void setApprovedByName(String approvedByName) {
		this.approvedByName = approvedByName;
	}
	public String getApprovedByTitle() {
		return approvedByTitle;
	}
	public void setApprovedByTitle(String approvedByTitle) {
		this.approvedByTitle = approvedByTitle;
	}
	public String getApprovedByDate() {
		return approvedByDate;
	}
	public void setApprovedByDate(String approvedByDate) {
		this.approvedByDate = approvedByDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ProgramButton getProgramButton() {
		return programButton;
	}
	public void setProgramButton(ProgramButton programButton) {
		this.programButton = programButton;
	}
	public boolean isNewProgram() {
		return isNewProgram;
	}
	public void setNewProgram(boolean isNewProgram) {
		this.isNewProgram = isNewProgram;
	}
	public List<ProgramWorkflowStatus> getProgramWorkflowStatusList() {
		return programWorkflowStatusList;
	}
	public void setProgramWorkflowStatusList(List<ProgramWorkflowStatus> programWorkflowStatusList) {
		this.programWorkflowStatusList = programWorkflowStatusList;
	}
	public String getApproverComments() {
		return approverComments;
	}
	public void setApproverComments(String approverComments) {
		this.approverComments = approverComments;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getCustomerGroupValue() {
		return customerGroupValue;
	}
	public void setCustomerGroupValue(String customerGroupValue) {
		this.customerGroupValue = customerGroupValue;
	}
}
