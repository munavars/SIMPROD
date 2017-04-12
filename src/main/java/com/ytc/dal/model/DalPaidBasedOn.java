package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "[vwBILL_TO_ALL]")
public class DalPaidBasedOn extends DalAuditableModel {


	private int Bill_To_No;
	private String Sales_Channel;
	private String Sales_Channel_Group; 
	
	public DalPaidBasedOn() {

	}	
	//@Id
	@Column(name = "BILL_TO_NO")
	public int getBill_To_No() {
		return Bill_To_No;
	}
	public void setBill_To_No(int bill_To_No) {
		Bill_To_No = bill_To_No;
	}
	
	@Column(name = "[SALES_CHANNEL")
	public String getSales_Channel() {
		return Sales_Channel;
	}
	public void setSales_Channel(String sales_Channel) {
		Sales_Channel = sales_Channel;
	}
	
	@Column(name = "[[SALES_CHANNEL_GROUP]")
	public String getSales_Channel_Group() {
		return Sales_Channel_Group;
	}
	public void setSales_Channel_Group(String sales_Channel_Group) {
		Sales_Channel_Group = sales_Channel_Group;
	}
	
}
