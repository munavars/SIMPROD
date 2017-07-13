/**
 * 
 */
package com.ytc.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @author Cognizant
 *
 */
@Entity
@Table(name = "ACCRUAL_DATA_PGM_DTL_CORP_WITH_ADJUSTMENTS")
public class DalCcmAccrualData extends DalModel{
	
	/**
	 * 
	 */
private static final long serialVersionUID = 4482936117713996795L;
	
private Calendar createdDate;
private String createdUser;
private Integer periodId;
private Calendar accrualStartDate;
private Calendar accrualEndDate;
private String bu;
private String accountManager;
private String zoneManager;
private String frequency;
private Integer programId;
private String programName;
private String description;
private String paidBasedOn;
private String guarantee;
private double amount;
private String amountType;
private double creditAccured;
private double earned;
private double creditEarned;
private String programStatus;
private String billToNo;
private String billToName;
private Integer units;
private Integer bonusableUnits;
private Integer nadUnits;
private Integer unitsplusNad;
private Integer bonusableUnitsplusNad;
private double invSales;
private double bonusableSales;
private double nadSales;
private double invSalesplusNad;
private double bonusableSalesplusNad;
private double warranty;
private String payTo;
private Calendar beginDate;
private Calendar endDate;
private String glCode;
private String paymentMethod;
private Integer baseItemId;


/**
 * @return the createdDate
 */
@Column(name = "CREATE_SYSDATE")
public Calendar getCreatedDate() {
	return createdDate;
}
/**
 * @param createdDate the createdDate to set
 */
public void setCreatedDate(Calendar createdDate) {
	this.createdDate = createdDate;
}
/**
 * @return the createdUser
 */
@Column(name = "CREATE_USER")
public String getCreatedUser() {
	return createdUser;
}
/**
 * @param createdUser the createdUser to set
 */
public void setCreatedUser(String createdUser) {
	this.createdUser = createdUser;
}
/**
 * @return the periodId
 */
@Column(name = "PERIOD_ID")
public Integer getPeriodId() {
	return periodId;
}
/**
 * @param periodId the periodId to set
 */
public void setPeriodId(Integer periodId) {
	this.periodId = periodId;
}
/**
 * @return the accrualStartDate
 */
@Column(name = "ACCRUAL_START_DATE")
public Calendar getAccrualStartDate() {
	return accrualStartDate;
}
/**
 * @param accrualStartDate the accrualStartDate to set
 */
public void setAccrualStartDate(Calendar accrualStartDate) {
	this.accrualStartDate = accrualStartDate;
}
/**
 * @return the accrualEndDate
 */
@Column(name = "ACCRUAL_END_DATE")
public Calendar getAccrualEndDate() {
	return accrualEndDate;
}
/**
 * @param accrualEndDate the accrualEndDate to set
 */
public void setAccrualEndDate(Calendar accrualEndDate) {
	this.accrualEndDate = accrualEndDate;
}
/**
 * @return the bu
 */
@Column(name = "BUSINESS_UNIT")
public String getBu() {
	return bu;
}
/**
 * @param bu the bu to set
 */
public void setBu(String bu) {
	this.bu = bu;
}
/**
 * @return the accountManager
 */
@Column(name = "ACCT_MGR")
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
 * @return the zoneManager
 */
@Column(name = "ZONE_MGR")
public String getZoneManager() {
	return zoneManager;
}
/**
 * @param zoneManager the zoneManager to set
 */
public void setZoneManager(String zoneManager) {
	this.zoneManager = zoneManager;
}
/**
 * @return the frequency
 */
@Column(name = "FREQENCY")
public String getFrequency() {
	return frequency;
}
/**
 * @param frequency the frequency to set
 */
public void setFrequency(String frequency) {
	this.frequency = frequency;
}
/**
 * @return the programId
 */
@Column(name = "PGM_ID")
public Integer getProgramId() {
	return programId;
}
/**
 * @param programId the programId to set
 */
public void setProgramId(Integer programId) {
	this.programId = programId;
}
/**
 * @return the programName
 */
@Column(name = "PGM_NAME")
public String getProgramName() {
	return programName;
}
/**
 * @param programName the programName to set
 */
public void setProgramName(String programName) {
	this.programName = programName;
}
/**
 * @return the description
 */
@Column(name = "PGM_DESC")
public String getDescription() {
	return description;
}
/**
 * @param description the description to set
 */
public void setDescription(String description) {
	this.description = description;
}
/**
 * @return the paidBasedOn
 */
@Column(name = "PAID_BASED_ON")
public String getPaidBasedOn() {
	return paidBasedOn;
}
/**
 * @param paidBasedOn the paidBasedOn to set
 */
public void setPaidBasedOn(String paidBasedOn) {
	this.paidBasedOn = paidBasedOn;
}
/**
 * @return the guarantee
 */
@Column(name = "GUARANTEE")
public String getGuarantee() {
	return guarantee;
}
/**
 * @param guarantee the guarantee to set
 */
public void setGuarantee(String guarantee) {
	this.guarantee = guarantee;
}
/**
 * @return the amount
 */
@Column(name = "AMOUNT")
public double getAmount() {
	return amount;
}
/**
 * @param amount the amount to set
 */
public void setAmount(double amount) {
	this.amount = amount;
}
/**
 * @return the amountType
 */
@Column(name = "TYPE")
public String getAmountType() {
	return amountType;
}
/**
 * @param amountType the amountType to set
 */
public void setAmountType(String amountType) {
	this.amountType = amountType;
}
/**
 * @return the creditAccured
 */
@Column(name = "CREDIT_ACCRUED")
public double getCreditAccured() {
	return creditAccured;
}
/**
 * @param creditAccured the creditAccured to set
 */
public void setCreditAccured(double creditAccured) {
	this.creditAccured = creditAccured;
}
/**
 * @return the earned
 */
@Column(name = "ADJUSTED_AMOUNT")
public double getEarned() {
	return earned;
}
/**
 * @param earned the earned to set
 */
public void setEarned(double earned) {
	this.earned = earned;
}
/**
 * @return the creditEarned
 */
@Column(name = "ADJUSTED_CREDIT")
public double getCreditEarned() {
	return creditEarned;
}
/**
 * @param creditEarned the creditEarned to set
 */
public void setCreditEarned(double creditEarned) {
	this.creditEarned = creditEarned;
}
/**
 * @return the review
 */
/*@Column(name = "ADJUSTED_CREDIT")
public String getReview() {
	return review;
}
*//**
 * @param review the review to set
 *//*
public void setReview(String review) {
	this.review = review;
}*/
/**
 * @return the programStatus
 */
@Column(name = "STATUS_FLAG")
public String getProgramStatus() {
	return programStatus;
}
/**
 * @param programStatus the programStatus to set
 */
public void setProgramStatus(String programStatus) {
	this.programStatus = programStatus;
}
/**
 * @return the billToNo
 */
@Column(name = "CORP_NO")
public String getBillToNo() {
	return billToNo;
}
/**
 * @param billToNo the billToNo to set
 */
public void setBillToNo(String billToNo) {
	this.billToNo = billToNo;
}
/**
 * @return the billToName
 */
@Column(name = "CORP_NAME")
public String getBillToName() {
	return billToName;
}
/**
 * @param billToName the billToName to set
 */
public void setBillToName(String billToName) {
	this.billToName = billToName;
}
/**
 * @return the units
 */
@Column(name = "UNITS")
public Integer getUnits() {
	return units;
}
/**
 * @param units the units to set
 */
public void setUnits(Integer units) {
	this.units = units;
}
/**
 * @return the bonusableUnits
 */
@Column(name = "BONUSABLE_UNITS")
public Integer getBonusableUnits() {
	return bonusableUnits;
}
/**
 * @param bonusableUnits the bonusableUnits to set
 */
public void setBonusableUnits(Integer bonusableUnits) {
	this.bonusableUnits = bonusableUnits;
}
/**
 * @return the nadUnits
 */
@Column(name = "NAD_UNITS")
public Integer getNadUnits() {
	return nadUnits;
}
/**
 * @param nadUnits the nadUnits to set
 */
public void setNadUnits(Integer nadUnits) {
	this.nadUnits = nadUnits;
}
/**
 * @return the unitsplusNad
 */
@Column(name = "UNITS_PLUS_NAD")
public Integer getUnitsplusNad() {
	return unitsplusNad;
}
/**
 * @param unitsplusNad the unitsplusNad to set
 */
public void setUnitsplusNad(Integer unitsplusNad) {
	this.unitsplusNad = unitsplusNad;
}
/**
 * @return the bonusableplusNad
 */
@Column(name = "BONUSABLE_UNITS_PLUS_NAD")
public Integer getBonusableUnitsplusNad() {
	return bonusableUnitsplusNad;
}
/**
 * @param bonusableplusNad the bonusableplusNad to set
 */
public void setBonusableUnitsplusNad(Integer bonusableUnitsplusNad) {
	this.bonusableUnitsplusNad = bonusableUnitsplusNad;
}
/**
 * @return the invSales
 */
@Column(name = "INV_SALES")
public double getInvSales() {
	return invSales;
}
/**
 * @param invSales the invSales to set
 */
public void setInvSales(double invSales) {
	this.invSales = invSales;
}
/**
 * @return the bonusableSales
 */
@Column(name = "BONUSABLE_SALES")
public double getBonusableSales() {
	return bonusableSales;
}
/**
 * @param bonusableSales the bonusableSales to set
 */
public void setBonusableSales(double bonusableSales) {
	this.bonusableSales = bonusableSales;
}
/**
 * @return the nadSales
 */
@Column(name = "NAD_SALES")
public double getNadSales() {
	return nadSales;
}
/**
 * @param nadSales the nadSales to set
 */
public void setNadSales(double nadSales) {
	this.nadSales = nadSales;
}
/**
 * @return the invSalesplusNad
 */
@Column(name = "INV_SALES_PLUS_NAD")
public double getInvSalesplusNad() {
	return invSalesplusNad;
}
/**
 * @param invSalesplusNad the invSalesplusNad to set
 */
public void setInvSalesplusNad(double invSalesplusNad) {
	this.invSalesplusNad = invSalesplusNad;
}
/**
 * @return the bonusableSalesplusNad
 */
@Column(name = "BONUSABLE_SALES_PLUS_NAD")
public double getBonusableSalesplusNad() {
	return bonusableSalesplusNad;
}
/**
 * @param bonusableSalesplusNad the bonusableSalesplusNad to set
 */
public void setBonusableSalesplusNad(double bonusableSalesplusNad) {
	this.bonusableSalesplusNad = bonusableSalesplusNad;
}
/**
 * @return the warranty
 */
@Column(name = "WARRANTY")
public double getWarranty() {
	return warranty;
}
/**
 * @param warranty the warranty to set
 */
public void setWarranty(double warranty) {
	this.warranty = warranty;
}
/**
 * @return the payTo
 */
@Column(name = "PAY_TO")
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
 * @return the beginDate
 */
@Column(name = "PGM_BEGIN_DATE")
public Calendar getBeginDate() {
	return beginDate;
}
/**
 * @param beginDate the beginDate to set
 */
public void setBeginDate(Calendar beginDate) {
	this.beginDate = beginDate;
}
/**
 * @return the endDate
 */
@Column(name = "PGM_END_DATE")
public Calendar getEndDate() {
	return endDate;
}
/**
 * @param endDate the endDate to set
 */
public void setEndDate(Calendar endDate) {
	this.endDate = endDate;
}
/**
 * @return the glCode
 */
@Column(name = "GL_NUMBER")
public String getGlCode() {
	return glCode;
}
/**
 * @param glCode the glCode to set
 */
public void setGlCode(String glCode) {
	this.glCode = glCode;
}
/**
 * @return the paymentMethod
 */
@Column(name = "PAYMENT_METHOD")
public String getPaymentMethod() {
	return paymentMethod;
}
/**
 * @param paymentMethod the paymentMethod to set
 */
public void setPaymentMethod(String paymentMethod) {
	this.paymentMethod = paymentMethod;
}
/**
 * @return the baseItemId
 */
@Column(name = "BASE_ITEM_ID")
public Integer getBaseItemId() {
	return baseItemId;
}
/**
 * @param baseItemId the baseItemId to set
 */
public void setBaseItemId(Integer baseItemId) {
	this.baseItemId = baseItemId;
}
	

}
