package com.ytc.common.model;

import java.math.BigDecimal;
import java.util.Date;

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
	private double accrualAmount;
	private String accrualType;
	private String currentTier;
	private String beginRange;
	private String trueUp;
	private String tierRate;
	private double accruedAmount;
	private double creditAmount;
	private double payables;
	private String glBalance;
	
	
	
	private ProgramDetailsDropDown dropdownList;

	private ProgramPaidOn programPaidOn;
	private ProgramAchieveOn programAchieveOn;
	
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
	public double getAccrualAmount() {
		return accrualAmount;
	}
	public void setAccrualAmount(double accrualAmount) {
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
	public double getAccruedAmount() {
		return accruedAmount;
	}
	public void setAccruedAmount(double accruedAmount) {
		this.accruedAmount = accruedAmount;
	}
	public double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public double getPayables() {
		return payables;
	}
	public void setPayables(double payables) {
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
	
	
	
	
}
