package com.ytc.common.model;

public class NetPricing extends Model{

	
	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	private String billToNumber;
	private String billToName;
	private String customerType;
	private String salesChannel;
	private String brandGroup;
	private String prodLn;
	private String tread;
	private String partNo;
	private String partDesc;
	private String basePrice;
	private String quarterlyDisc;
	private String specialDisc;
	private String exceptionDisc;
	private String netPrice;
	private String quantityDisc;
	private String cashDiscPercent;
	private String anticDiscPercent;
	private double FET;
	private String support;
	private String volBonus;
	private String runDate;
	private String futureDate;
	/**
	 * @return the billToNumber
	 */
	public String getBillToNumber() {
		return billToNumber;
	}
	/**
	 * @param billToNumber the billToNumber to set
	 */
	public void setBillToNumber(String billToNumber) {
		this.billToNumber = billToNumber;
	}
	/**
	 * @return the billToName
	 */
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
	 * @return the salesChannel
	 */
	public String getSalesChannel() {
		return salesChannel;
	}
	/**
	 * @param salesChannel the salesChannel to set
	 */
	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}
	/**
	 * @return the brandGroup
	 */
	public String getBrandGroup() {
		return brandGroup;
	}
	/**
	 * @param brandGroup the brandGroup to set
	 */
	public void setBrandGroup(String brandGroup) {
		this.brandGroup = brandGroup;
	}
	/**
	 * @return the prodLn
	 */
	public String getProdLn() {
		return prodLn;
	}
	/**
	 * @param prodLn the prodLn to set
	 */
	public void setProdLn(String prodLn) {
		this.prodLn = prodLn;
	}
	/**
	 * @return the tread
	 */
	public String getTread() {
		return tread;
	}
	/**
	 * @param tread the tread to set
	 */
	public void setTread(String tread) {
		this.tread = tread;
	}
	/**
	 * @return the partNo
	 */
	public String getPartNo() {
		return partNo;
	}
	/**
	 * @param partNo the partNo to set
	 */
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	/**
	 * @return the partDesc
	 */
	public String getPartDesc() {
		return partDesc;
	}
	/**
	 * @param partDesc the partDesc to set
	 */
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	/**
	 * @return the basePrice
	 */
	public String getBasePrice() {
		return basePrice;
	}
	/**
	 * @param basePrice the basePrice to set
	 */
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}
	/**
	 * @return the quarterlyDisc
	 */
	public String getQuarterlyDisc() {
		return quarterlyDisc;
	}
	/**
	 * @param quarterlyDisc the quarterlyDisc to set
	 */
	public void setQuarterlyDisc(String quarterlyDisc) {
		this.quarterlyDisc = quarterlyDisc;
	}
	/**
	 * @return the specialDisc
	 */
	public String getSpecialDisc() {
		return specialDisc;
	}
	/**
	 * @param specialDisc the specialDisc to set
	 */
	public void setSpecialDisc(String specialDisc) {
		this.specialDisc = specialDisc;
	}
	
	/**
	 * @return the netPrice
	 */
	public String getNetPrice() {
		return netPrice;
	}
	/**
	 * @param netPrice the netPrice to set
	 */
	public void setNetPrice(String netPrice) {
		this.netPrice = netPrice;
	}
	
	/**
	 * @return the cashDiscPercent
	 */
	public String getCashDiscPercent() {
		return cashDiscPercent;
	}
	/**
	 * @param cashDiscPercent the cashDiscPercent to set
	 */
	public void setCashDiscPercent(String cashDiscPercent) {
		this.cashDiscPercent = cashDiscPercent;
	}
	
	/**
	 * @return the fET
	 */
	public double getFET() {
		return FET;
	}
	/**
	 * @param fET the fET to set
	 */
	public void setFET(double fET) {
		FET = fET;
	}
	/**
	 * @return the support
	 */
	public String getSupport() {
		return support;
	}
	/**
	 * @param support the support to set
	 */
	public void setSupport(String support) {
		this.support = support;
	}
	/**
	 * @return the volBonus
	 */
	public String getVolBonus() {
		return volBonus;
	}
	/**
	 * @param volBonus the volBonus to set
	 */
	public void setVolBonus(String volBonus) {
		this.volBonus = volBonus;
	}
	/**
	 * @return the runDate
	 */
	public String getRunDate() {
		return runDate;
	}
	/**
	 * @param runDate the runDate to set
	 */
	public void setRunDate(String runDate) {
		this.runDate = runDate;
	}
	/**
	 * @return the futureDate
	 */
	public String getFutureDate() {
		return futureDate;
	}
	/**
	 * @param futureDate the futureDate to set
	 */
	public void setFutureDate(String futureDate) {
		this.futureDate = futureDate;
	}
	/**
	 * @return the exceptionDisc
	 */
	public String getExceptionDisc() {
		return exceptionDisc;
	}
	/**
	 * @param exceptionDisc the exceptionDisc to set
	 */
	public void setExceptionDisc(String exceptionDisc) {
		this.exceptionDisc = exceptionDisc;
	}
	/**
	 * @return the quantityDisc
	 */
	public String getQuantityDisc() {
		return quantityDisc;
	}
	/**
	 * @param quantityDisc the quantityDisc to set
	 */
	public void setQuantityDisc(String quantityDisc) {
		this.quantityDisc = quantityDisc;
	}
	/**
	 * @return the anticDiscPercent
	 */
	public String getAnticDiscPercent() {
		return anticDiscPercent;
	}
	/**
	 * @param anticDiscPercent the anticDiscPercent to set
	 */
	public void setAnticDiscPercent(String anticDiscPercent) {
		this.anticDiscPercent = anticDiscPercent;
	}
	
	
}
