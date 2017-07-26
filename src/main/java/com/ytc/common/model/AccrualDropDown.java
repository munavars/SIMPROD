/**
 * 
 */
package com.ytc.common.model;

import java.util.List;

/**
 * @author ArunP
 *
 */
public class AccrualDropDown {
	private List<DropDown> periodList;
	private List<DropDown> bookLabelList;
	/**
	 * @return the periodList
	 */
	public List<DropDown> getPeriodList() {
		return periodList;
	}
	/**
	 * @param periodList the periodList to set
	 */
	public void setPeriodList(List<DropDown> periodList) {
		this.periodList = periodList;
	}
	/**
	 * @return the bookLabelList
	 */
	public List<DropDown> getBookLabelList() {
		return bookLabelList;
	}
	/**
	 * @param bookLabelList the bookLabelList to set
	 */
	public void setBookLabelList(List<DropDown> bookLabelList) {
		this.bookLabelList = bookLabelList;
	}

}
