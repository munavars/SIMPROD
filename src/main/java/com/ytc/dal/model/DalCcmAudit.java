/**
 * 
 */
package com.ytc.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Cognizant
 *
 */
@Entity
@Table(name = "CCM_AUDIT_TABLE")
public class DalCcmAudit extends DalModel{
	
	private static final long serialVersionUID = 4482936117713996795L;	
	private Integer ccmId;
	private double adjustedAmount;
	private double adjustedCredit;
	private String statusFlag;
	private String adjustedUser;
	private Calendar adjustedDate;
	private String Comments;
	/**
	 * @return the ccmId
	 */
	@Column(name = "CCM_ID")
	public Integer getCcmId() {
		return ccmId;
	}
	/**
	 * @param ccmId the ccmId to set
	 */
	public void setCcmId(Integer ccmId) {
		this.ccmId = ccmId;
	}
	/**
	 * @return the adjustedAmount
	 */
	@Column(name = "ADJUSTED_AMOUNT")
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
	@Column(name = "ADJUSTED_CREDIT")
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
	 * @return the statusFlag
	 */
	@Column(name = "STATUS_FLAG")
	public String getStatusFlag() {
		return statusFlag;
	}
	/**
	 * @param statusFlag the statusFlag to set
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	/**
	 * @return the adjustedUser
	 */
	@Column(name = "ADJUSTED_USER")
	public String getAdjustedUser() {
		return adjustedUser;
	}
	/**
	 * @param adjustedUser the adjustedUser to set
	 */
	public void setAdjustedUser(String adjustedUser) {
		this.adjustedUser = adjustedUser;
	}
	/**
	 * @return the adjustedDate
	 */
	@Column(name = "ADJUSTED_SYSDATE")
	public Calendar getAdjustedDate() {
		return adjustedDate;
	}
	/**
	 * @param adjustedDate the adjustedDate to set
	 */
	public void setAdjustedDate(Calendar adjustedDate) {
		this.adjustedDate = adjustedDate;
	}
	/**
	 * @return the comments
	 */
	@Column(name = "COMMENTS")
	public String getComments() {
		return Comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		Comments = comments;
	}
	
	

}
