package com.ytc.common.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ytc.dal.model.DalBaseItems;
import com.ytc.dal.model.DalGLCode;

public class ProgramDetail extends Model{

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	private Date beginDate;
	private Date endDate;
	private String displayBeginDate;
	private String displayEndDate;
	private String programName;
	private String paidType;
	private String pricingType;
	private String payTo;
	private BigDecimal amount;
	private String amountType;
	private String BTL;
	private String payoutFrequency;
	private String paidBasedOn;
	private Integer programId;
	private String achievedBasedOn;
	private String isTiered;
	private String accrualAmount;
	private String accrualType;
	private String currentTier;
	private String beginRange;
	private String trueUp;
	private String tierRate;
	private String accruedAmount;
	private String creditAmount;
	private String payables;
	private String glBalance;
	private String customerId;
	private String customerName;
	private String customerNumber;
	private String zmAppStatus;
	private String zmAppDate;
	private String tbpAppStatus;
	private String tbpAppDate;
	private String bu;
	private String submitDate;
	private String longDesc;
	
	
	private ProgramDetailsDropDown dropdownList;

	private ProgramPaidOn programPaidOn;
	private ProgramAchieveOn programAchieveOn;
	private List<ProgramTierDetail> programTierDetailList = null;
	private String amountTypeTier;
	private Integer actualMarker;
	private Integer programTypeId;
	private String programType;
	private String createdDate;
	private String modifiedDate;
	private String statusHistory;
	private String programStatus;
	
	private Integer tbpCheck;
	private String glCode;
	private List<DalGLCode> glCodeList;
	private List<DalBaseItems> baseItemList;
	
	/**Commentary/Special Instructions attribute to hold the user entered comments.*/
	private String comments;
	
	public ProgramPaidOn getProgramPaidOn() {
		return programPaidOn;
	}
	public void setProgramPaidOn(ProgramPaidOn programPaidOn) {
		this.programPaidOn = programPaidOn;
	}
	public ProgramAchieveOn getProgramAchieveOn() {
		return programAchieveOn;
	}
	public void setProgramAchieveOn(ProgramAchieveOn programAchieveOn) {
		this.programAchieveOn = programAchieveOn;
	}
	public String getPayoutFrequency() {
		return payoutFrequency;
	}
	public void setPayoutFrequency(String payoutFrequency) {
		this.payoutFrequency = payoutFrequency;
	}
	public String getPaidBasedOn() {
		return paidBasedOn;
	}
	public void setPaidBasedOn(String paidBasedOn) {
		this.paidBasedOn = paidBasedOn;
	}
	public String getPricingType() {
		return pricingType;
	}
	public void setPricingType(String pricingType) {
		this.pricingType = pricingType;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public ProgramDetailsDropDown getDropdownList() {
		return dropdownList;
	}
	public void setDropdownList(ProgramDetailsDropDown dropdownList) {
		this.dropdownList = dropdownList;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getPaidType() {
		return paidType;
	}
	public void setPaidType(String paidType) {
		this.paidType = paidType;
	}
	public String getPayTo() {
		return payTo;
	}
	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getAmountType() {
		return amountType;
	}
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	public String getBTL() {
		return BTL;
	}
	public void setBTL(String bTL) {
		BTL = bTL;
	}
	public Integer getProgramId() {
		return programId;
	}
	public void setProgramId(Integer programId) {
		this.programId = programId;
	}
	public String getAchievedBasedOn() {
		return achievedBasedOn;
	}
	public void setAchievedBasedOn(String achievedBasedOn) {
		this.achievedBasedOn = achievedBasedOn;
	}
	public String getIsTiered() {
		return isTiered;
	}
	public void setIsTiered(String isTiered) {
		this.isTiered = isTiered;
	}
	public String getAccrualAmount() {
		return accrualAmount;
	}
	public void setAccrualAmount(String accrualAmount) {
		this.accrualAmount = accrualAmount;
	}
	public String getAccrualType() {
		return accrualType;
	}
	public void setAccrualType(String accrualType) {
		this.accrualType = accrualType;
	}
	public String getCurrentTier() {
		return currentTier;
	}
	public void setCurrentTier(String currentTier) {
		this.currentTier = currentTier;
	}
	public String getBeginRange() {
		return beginRange;
	}
	public void setBeginRange(String beginRange) {
		this.beginRange = beginRange;
	}
	public String getTrueUp() {
		return trueUp;
	}
	public void setTrueUp(String trueUp) {
		this.trueUp = trueUp;
	}
	public String getTierRate() {
		return tierRate;
	}
	public void setTierRate(String tierRate) {
		this.tierRate = tierRate;
	}
	public String getAccruedAmount() {
		return accruedAmount;
	}
	public void setAccruedAmount(String accruedAmount) {
		this.accruedAmount = accruedAmount;
	}
	public String getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	public String getPayables() {
		return payables;
	}
	public void setPayables(String payables) {
		this.payables = payables;
	}
	public String getGlBalance() {
		return glBalance;
	}
	public void setGlBalance(String glBalance) {
		this.glBalance = glBalance;
	}

	public String getDisplayBeginDate() {
		return displayBeginDate;
	}
	
	public void setDisplayBeginDate(String displayBeginDate) {
		this.displayBeginDate = displayBeginDate;
	}
	
	public String getDisplayEndDate() {
		return displayEndDate;
	}
	
	public void setDisplayEndDate(String displayEndDate) {
		this.displayEndDate = displayEndDate;
	}
	
	public List<ProgramTierDetail> getProgramTierDetailList() {
		return programTierDetailList;
	}
	
	public void setProgramTierDetailList(List<ProgramTierDetail> programTierDetailList) {
		this.programTierDetailList = programTierDetailList;
	}
	
	public String getAmountTypeTier() {
		return amountTypeTier;
	}
	
	public void setAmountTypeTier(String amountTypeTier) {
		this.amountTypeTier = amountTypeTier;
	}
	
	public Integer getActualMarker() {
		return actualMarker;
	}
	
	public void setActualMarker(Integer actualMarker) {
		this.actualMarker = actualMarker;
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
	 * @return the zmAppStatus
	 */
	public String getZmAppStatus() {
		return zmAppStatus;
	}
	/**
	 * @param zmAppStatus the zmAppStatus to set
	 */
	public void setZmAppStatus(String zmAppStatus) {
		this.zmAppStatus = zmAppStatus;
	}
	/**
	 * @return the zmAppDate
	 */
	public String getZmAppDate() {
		return zmAppDate;
	}
	/**
	 * @param zmAppDate the zmAppDate to set
	 */
	public void setZmAppDate(String zmAppDate) {
		this.zmAppDate = zmAppDate;
	}
	/**
	 * @return the tbpAppStatus
	 */
	public String getTbpAppStatus() {
		return tbpAppStatus;
	}
	/**
	 * @param tbpAppStatus the tbpAppStatus to set
	 */
	public void setTbpAppStatus(String tbpAppStatus) {
		this.tbpAppStatus = tbpAppStatus;
	}
	/**
	 * @return the tbpAppDate
	 */
	public String getTbpAppDate() {
		return tbpAppDate;
	}
	/**
	 * @param tbpAppDate the tbpAppDate to set
	 */
	public void setTbpAppDate(String tbpAppDate) {
		this.tbpAppDate = tbpAppDate;
	}
	/**
	 * @return the bu
	 */
	public String getBu() {
		//return bu;
		return ("P".equalsIgnoreCase(bu)?"CONSUMER":"COMMERCIAL");
	}
	/**
	 * @param bu the bu to set
	 */
	public void setBu(String bu) {
		this.bu = bu;
	}
	/**
	 * @return the submitDate
	 */
	public String getSubmitDate() {
		return submitDate;
	}
	/**
	 * @param submitDate the submitDate to set
	 */
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	/**
	 * @return the longDesc
	 */
	public String getLongDesc() {
		return longDesc;
	}
	/**
	 * @param longDesc the longDesc to set
	 */
	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getStatusHistory() {
		return statusHistory;
	}
	public void setStatusHistory(String statusHistory) {
		this.statusHistory = statusHistory;
	}
	public String getProgramStatus() {
		return programStatus;
	}
	public void setProgramStatus(String programStatus) {
		this.programStatus = programStatus;
	}
	
	public Integer getTbpCheck() {
		return tbpCheck;
	}
	public void setTbpcheck(Integer tbpCheck) {
		this.tbpCheck = tbpCheck;
	}
	public String getGlCode() {
		return glCode;
	}
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}
	public List<DalGLCode> getGlCodeList() {
		return glCodeList;
	}
	public void setGlCodeList(List<DalGLCode> glCodeList) {
		this.glCodeList = glCodeList;
	}
	/**
	 * @return the baseItemList
	 */
	public List<DalBaseItems> getBaseItemList() {
		return baseItemList;
	}
	/**
	 * @param baseItemList the baseItemList to set
	 */
	public void setBaseItemList(List<DalBaseItems> baseItemList) {
		this.baseItemList = baseItemList;
	}
	
	
}
