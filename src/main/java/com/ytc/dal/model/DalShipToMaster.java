package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SHIP_TO_MASTER")
@NamedQueries({
	@NamedQuery(name="DalShipToMaster.getShipToName", query = "select DISTINCT(shipToName) from DalShipToMaster"),
	@NamedQuery(name="DalShipToMaster.getBillToName", query = "select DISTINCT(billToName) from DalShipToMaster")
})
public class DalShipToMaster {
	private String shipToNumber;
	private String shipToName;
	private String billToNumber;
	private String billToName;
	private String corpNumber;
	private String corpName;
	private String channelCode;
	private String channelName;
	private String channelGroup;
	private String channelGroupName;
	private String businessUnit;
	private String businessUnitName;

	@Id
	@Column(name = "SHIP_TO_NUMBER")
	public String getShipToNumber() {
		return shipToNumber;
	}
	public void setShipToNumber(String shipToNumber) {
		this.shipToNumber = shipToNumber;
	}
	
	@Column(name = "SHIP_TO_NAME")
	public String getShipToName() {
		return shipToName;
	}
	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}
	
	@Column(name = "BILL_TO_NUMBER")
	public String getBillToNumber() {
		return billToNumber;
	}
	public void setBillToNumber(String billToNumber) {
		this.billToNumber = billToNumber;
	}
	
	@Column(name = "BILL_TO_NAME")
	public String getBillToName() {
		return billToName;
	}
	public void setBillToName(String billToName) {
		this.billToName = billToName;
	}
	
	@Column(name = "CORP_NUMBER")
	public String getCorpNumber() {
		return corpNumber;
	}
	public void setCorpNumber(String corpNumber) {
		this.corpNumber = corpNumber;
	}
	
	@Column(name = "CORP_NAME")
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	
	@Column(name = "CHANNEL_CODE")
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Column(name = "CHANNEL_NAME")
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@Column(name = "CHANNEL_GROUP")
	public String getChannelGroup() {
		return channelGroup;
	}
	public void setChannelGroup(String channelGroup) {
		this.channelGroup = channelGroup;
	}
	
	@Column(name = "CHANNEL_GROUP_NAME")
	public String getChannelGroupName() {
		return channelGroupName;
	}
	public void setChannelGroupName(String channelGroupName) {
		this.channelGroupName = channelGroupName;
	}
	
	@Column(name = "BUSINESS_UNIT")
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	
	@Column(name = "BUSINESS_UNIT_NAME")
	public String getBusinessUnitName() {
		return businessUnitName;
	}
	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}		  
}
