/**
 * 
 */
package com.ytc.common.model;

import java.util.Date;
import java.util.List;

/**
 * @author ArunP
 *
 */
public class PricingCustomerGroup {
	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	private List<DropDown> groupList;
	private List<DropDown> cusByBillToList;
	private List<DropDown> cusByNameList;
	private List<DropDown> shipToList;
	/**
	 * @return the groupList
	 */
	public List<DropDown> getGroupList() {
		return groupList;
	}
	/**
	 * @param groupList the groupList to set
	 */
	public void setGroupList(List<DropDown> groupList) {
		this.groupList = groupList;
	}
	/**
	 * @return the cusByBillToList
	 */
	public List<DropDown> getCusByBillToList() {
		return cusByBillToList;
	}
	/**
	 * @param cusByBillToList the cusByBillToList to set
	 */
	public void setCusByBillToList(List<DropDown> cusByBillToList) {
		this.cusByBillToList = cusByBillToList;
	}
	/**
	 * @return the cusByNameList
	 */
	public List<DropDown> getCusByNameList() {
		return cusByNameList;
	}
	/**
	 * @param cusByNameList the cusByNameList to set
	 */
	public void setCusByNameList(List<DropDown> cusByNameList) {
		this.cusByNameList = cusByNameList;
	}
	/**
	 * @return the shipToList
	 */
	public List<DropDown> getShipToList() {
		return shipToList;
	}
	/**
	 * @param shipToList the shipToList to set
	 */
	public void setShipToList(List<DropDown> shipToList) {
		this.shipToList = shipToList;
	}
	
}
