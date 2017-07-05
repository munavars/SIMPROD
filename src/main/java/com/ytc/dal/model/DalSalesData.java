package com.ytc.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SALES_DATA")
public class DalSalesData  {

	private int id;
	private Calendar invoiceDate;
	private String docNumber; 
	private String corpNumber; 
	private String partNumber;
	private String ptsNumber;
	private int unitSold;
	private float invoiceSales;
	private int bonusableUnits;
	private int bonusableSales;
	private int nadUnits;
	private float nadCredit;
	private float wrcCredit;
	private int customerNumber;
	private String pcsZipCode;
	private String pcsDirstrict;
	private String reciprocalCust;
	

	public DalSalesData() {

	}

	@Id
	@Column(name="ID")
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	@Column(name="INV_DATE")
	public Calendar getInvoiceDate() {
		return invoiceDate;
	}


	public void setInvoiceDate(Calendar invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	@Column(name="DOC_NO")
	public String getDocNumber() {
		return docNumber;
	}


	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	@Column(name="CORP_NO")
	public String getCorpNumber() {
		return corpNumber;
	}


	public void setCorpNumber(String corpNumber) {
		this.corpNumber = corpNumber;
	}

	@Column(name="PART_NO")
	public String getPartNumber() {
		return partNumber;
	}


	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	@Column(name="PTS_NUM")
	public String getPtsNumber() {
		return ptsNumber;
	}


	public void setPtsNumber(String ptsNumber) {
		this.ptsNumber = ptsNumber;
	}

	@Column(name="UNITS_SOLD")
	public int getUnitSold() {
		return unitSold;
	}


	public void setUnitSold(int unitSold) {
		this.unitSold = unitSold;
	}

	@Column(name="INV_SALES")
	public float getInvoiceSales() {
		return invoiceSales;
	}


	public void setInvoiceSales(float invoiceSales) {
		this.invoiceSales = invoiceSales;
	}

	@Column(name="BONUSABLE_UNITS")
	public int getBonusableUnits() {
		return bonusableUnits;
	}


	public void setBonusableUnits(int bonusableUnits) {
		this.bonusableUnits = bonusableUnits;
	}

	@Column(name="BONUSABLE_SALES")
	public int getBonusableSales() {
		return bonusableSales;
	}


	public void setBonusableSales(int bonusableSales) {
		this.bonusableSales = bonusableSales;
	}

	@Column(name="NAD_UNITS")
	public int getNadUnits() {
		return nadUnits;
	}


	public void setNadUnits(int nadUnits) {
		this.nadUnits = nadUnits;
	}

	@Column(name="NAD_CREDIT")
	public float getNadCredit() {
		return nadCredit;
	}


	public void setNadCredit(float nadCredit) {
		this.nadCredit = nadCredit;
	}

	@Column(name="WRC_CREDIT")
	public float getWrcCredit() {
		return wrcCredit;
	}


	public void setWrcCredit(float wrcCredit) {
		this.wrcCredit = wrcCredit;
	}

	@Column(name="CUST_NO")
	public int getCustomerNumber() {
		return customerNumber;
	}


	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	@Column(name="PCS_ZIPCODE")
	public String getPcsZipCode() {
		return pcsZipCode;
	}


	public void setPcsZipCode(String pcsZipCode) {
		this.pcsZipCode = pcsZipCode;
	}

	@Column(name="PCS_DISTRICT")
	public String getPcsDirstrict() {
		return pcsDirstrict;
	}


	public void setPcsDirstrict(String pcsDirstrict) {
		this.pcsDirstrict = pcsDirstrict;
	}

	@Column(name="RECIPROCAL_CUST")
	public String getReciprocalCust() {
		return reciprocalCust;
	}


	public void setReciprocalCust(String reciprocalCust) {
		this.reciprocalCust = reciprocalCust;
	}	



	}
