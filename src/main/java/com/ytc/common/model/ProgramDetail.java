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
	private String programName;
	private String paidType;
	private String pricingType;
	private String payTo;
	private BigDecimal amount;
	private String amountType;
	private Boolean btl;
	private String payoutFrequency;
	private String paidBasedOn;
	private ProgramDetailsDropDown dropdownList;
	
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
	public Boolean getBtl() {
		return btl;
	}
	public void setBtl(Boolean btl) {
		this.btl = btl;
	}
}
