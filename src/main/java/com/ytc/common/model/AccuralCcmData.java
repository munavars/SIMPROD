/**
 * 
 */
package com.ytc.common.model;

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
	
	

}
