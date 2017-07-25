package com.ytc.common.params;

import java.util.Calendar;

public class CreditMemoParams {

	private Integer pgmDetailId;
	private Calendar startDate;
	private Calendar endDate;
	/**
	 * @return the pgmDetailId
	 */
	public Integer getPgmDetailId() {
		return pgmDetailId;
	}
	/**
	 * @param pgmDetailId the pgmDetailId to set
	 */
	public void setPgmDetailId(Integer pgmDetailId) {
		this.pgmDetailId = pgmDetailId;
	}
	/**
	 * @return the startDate
	 */
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
	public Calendar getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	
	
	
}
