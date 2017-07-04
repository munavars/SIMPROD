/**
 * 
 */
package com.ytc.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PRICING_DETAIL")
@NamedQueries({
	@NamedQuery(name="DalPricingDetail.getAllRecordsForPricingHeaderId", query = "select o from DalPricingDetail o where o.dalPricingHeader.id = :pricingHeaderId order by o.id")
})
public class DalPricingDetail extends DalAuditableModel{

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	private String addChgDel;
	private Calendar startDate;
	private Calendar endDate;
	private String prodLine;
	private String prodTread;
	private String partNumber;
	private double netPrice;
	private double invoiceDisc;
	private String discType; 
	private String isCommissionable;
	private String isBonusableUnits;
	private String comments;
	private DalPricingHeader dalPricingHeader;
	/**
	 * @return the addChgDel
	 */
	@Column(name = "ADD_CHG_DEL")
	public String getAddChgDel() {
		return addChgDel;
	}
	/**
	 * @param addChgDel the addChgDel to set
	 */
	public void setAddChgDel(String addChgDel) {
		this.addChgDel = addChgDel;
	}
	/**
	 * @return the startDate
	 */
	@Column(name = "START_DATE")
	public Calendar getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	@Column(name = "END_DATE")
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
	 * @return the prodLine
	 */
	@Column(name = "PROD_LINE")
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
	 * @return the prodTread
	 */
	 @Column(name = "PROD_TREAD")
	public String getProdTread() {
		return prodTread;
	}
	/**
	 * @param prodTread the prodTread to set
	 */
	public void setProdTread(String prodTread) {
		this.prodTread = prodTread;
	}
	/**
	 * @return the partNumber
	 */
	@Column(name = "PART_NUMBER")
	public String getPartNumber() {
		return partNumber;
	}
	/**
	 * @param partNumber the partNumber to set
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	/**
	 * @return the netPrice
	 */
	@Column(name = "NET_PRICE")
	public double getNetPrice() {
		return netPrice;
	}
	/**
	 * @param netPrice the netPrice to set
	 */
	public void setNetPrice(double netPrice) {
		this.netPrice = netPrice;
	}
	/**
	 * @return the invoiceDisc
	 */
	@Column(name = "INVOICE_DISC")
	public double getInvoiceDisc() {
		return invoiceDisc;
	}
	/**
	 * @param invoiceDisc the invoiceDisc to set
	 */
	public void setInvoiceDisc(double invoiceDisc) {
		this.invoiceDisc = invoiceDisc;
	}
	/**
	 * @return the discType
	 */
	@Column(name = "DISCOUNT_TYPE")
	public String getDiscType() {
		return discType;
	}
	/**
	 * @param discType the discType to set
	 */
	public void setDiscType(String discType) {
		this.discType = discType;
	}
	/**
	 * @return the isCommissionable
	 */
	
	@Column(name = "IS_COMMISSIONABLE")
	public String getIsCommissionable() {
		return isCommissionable;
	}
	/**
	 * @param isCommissionable the isCommissionable to set
	 */
	public void setIsCommissionable(String isCommissionable) {
		this.isCommissionable = isCommissionable;
	}
	/**
	 * @return the isBonusUnits
	 */
	@Column(name = "IS_BONUSABLE_UNITS")
	public String getIsBonusableUnits() {
		return isBonusableUnits;
	}
	/**
	 * @param isBonusUnits the isBonusUnits to set
	 */
	public void setIsBonusableUnits(String isBonusableUnits) {
		this.isBonusableUnits = isBonusableUnits;
	}
	/**
	 * @return the dalPricingHeader
	 */
	
	@ManyToOne
	@JoinColumn(name = "PRICING_HDR_ID", referencedColumnName = "ID")
	public DalPricingHeader getDalPricingHeader() {
		return dalPricingHeader;
	}
	/**
	 * @param dalPricingHeader the dalPricingHeader to set
	 */
	public void setDalPricingHeader(DalPricingHeader dalPricingHeader) {
		this.dalPricingHeader = dalPricingHeader;
	}
	
	@Column(name = "COMMENTS")
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
