/**
 * 
 */
package com.ytc.common.model;

import java.util.Date;

/**
 * @author ArunP
 *
 */
public class PricingDetail extends Model{

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	private String addChangeDel;
	private Date beginDate;
	private Date endDate;
	private String prodLine;
	private String tread;
	private String part;
	private String businessUnit;
	private String netPrice;
	private String invoiceDisc;
	private String bonusUnits;
	private String commissionable;
	private String comments;
	private Integer programId;
	/**
	 * @return the addChangeDel
	 */
	public String getAddChangeDel() {
		return addChangeDel;
	}
	/**
	 * @param addChangeDel the addChangeDel to set
	 */
	public void setAddChangeDel(String addChangeDel) {
		this.addChangeDel = addChangeDel;
	}
	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the prodLine
	 */
	public String getProdLine() {
		return prodLine;
	}
	/**
	 * @param prodLine the prodLine to set
	 */
	public void setProdLine(String prodLine) {
		this.prodLine = prodLine;
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
	 * @return the part
	 */
	public String getPart() {
		return part;
	}
	/**
	 * @param part the part to set
	 */
	public void setPart(String part) {
		this.part = part;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
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
	 * @return the invoiceDisc
	 */
	public String getInvoiceDisc() {
		return invoiceDisc;
	}
	/**
	 * @param invoiceDisc the invoiceDisc to set
	 */
	public void setInvoiceDisc(String invoiceDisc) {
		this.invoiceDisc = invoiceDisc;
	}
	/**
	 * @return the bonusUnits
	 */
	public String getBonusUnits() {
		return bonusUnits;
	}
	/**
	 * @param bonusUnits the bonusUnits to set
	 */
	public void setBonusUnits(String bonusUnits) {
		this.bonusUnits = bonusUnits;
	}
	/**
	 * @return the commissionable
	 */
	public String getCommissionable() {
		return commissionable;
	}
	/**
	 * @param commissionable the commissionable to set
	 */
	public void setCommissionable(String commissionable) {
		this.commissionable = commissionable;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the programId
	 */
	public Integer getProgramId() {
		return programId;
	}
	/**
	 * @param programId the programId to set
	 */
	public void setProgramId(Integer programId) {
		this.programId = programId;
	}
	
}
