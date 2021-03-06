/**
 * 
 */
package com.ytc.common.model;

import java.util.Date;

/**
 * @author Cognizant
 *
 */
public class AccuralCcmData {
	
	private Integer id;
	private double adjustedAmount;
	private double adjustedCredit;
	private String reviewFlag;
	private String comments;
	private String docNumber;
	private Date docDate;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the adjustedAmount
	 */
	public double getAdjustedAmount() {
		return adjustedAmount;
	}
	/**
	 * @param adjustedAmount the adjustedAmount to set
	 */
	public void setAdjustedAmount(double adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}
	/**
	 * @return the adjustedCredit
	 */
	public double getAdjustedCredit() {
		return adjustedCredit;
	}
	/**
	 * @param adjustedCredit the adjustedCredit to set
	 */
	public void setAdjustedCredit(double adjustedCredit) {
		this.adjustedCredit = adjustedCredit;
	}
	/**
	 * @return the reviewFlag
	 */
	public String getReviewFlag() {
		return reviewFlag;
	}
	/**
	 * @param reviewFlag the reviewFlag to set
	 */
	public void setReviewFlag(String reviewFlag) {
		this.reviewFlag = reviewFlag;
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
	 * @return the docNumber
	 */
	public String getDocNumber() {
		return docNumber;
	}
	/**
	 * @param docNumber the docNumber to set
	 */
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	/**
	 * @return the docDate
	 */
	public Date getDocDate() {
		return docDate;
	}
	/**
	 * @param docDate the docDate to set
	 */
	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}
	
	

}
