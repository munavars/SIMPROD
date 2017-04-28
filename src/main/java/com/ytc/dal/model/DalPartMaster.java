package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PART_MASTER")
public class DalPartMaster {
	private String partNumber;
	private String partDesc;
	private String treadDesc;
	private String productLine;
	private String brand;
	private String businessUnit;
	
	@Id
	@Column(name = "PART_NUMBER")
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	@Column(name = "PART_DESC")
	public String getPartDesc() {
		return partDesc;
	}
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	
	@Column(name = "TREAD_DESC")
	public String getTreadDesc() {
		return treadDesc;
	}
	public void setTreadDesc(String treadDesc) {
		this.treadDesc = treadDesc;
	}
	
	@Column(name = "PRODUCT_LINE")
	public String getProductLine() {
		return productLine;
	}
	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}
	
	@Column(name = "BRAND")
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Column(name = "BUSINESS_UNIT")
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}	    	
}
