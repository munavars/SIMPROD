package com.ytc.common.model;

public class PaidBasedOn extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int Bill_To_No;
	private String Sales_Channel;
	private String Sales_Channel_Group; 
	
	public PaidBasedOn() {

	}	
	
	public int getBill_To_No() {
		return Bill_To_No;
	}
	public void setBill_To_No(int bill_To_No) {
		Bill_To_No = bill_To_No;
	}
	public String getSales_Channel() {
		return Sales_Channel;
	}
	public void setSales_Channel(String sales_Channel) {
		Sales_Channel = sales_Channel;
	}
	public String getSales_Channel_Group() {
		return Sales_Channel_Group;
	}
	public void setSales_Channel_Group(String sales_Channel_Group) {
		Sales_Channel_Group = sales_Channel_Group;
	}
	
}
