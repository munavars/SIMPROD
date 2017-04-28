/**
 * 
 */
package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ytc.common.model.AuditableModel;

/**
 * @author ArunP
 *
 */
@Entity
@Table(name = "ACCRUAL_DATA")
public class DalAccrualData extends DalAuditableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4482936117713996795L;
	
	//private String id;
	private Integer customerId;
	private Integer programDetailId;
	private Integer programHeaderId;
	private Integer programDetailTierId;
	private Integer programDetailPaidId;
	private Integer marGlId;
	private Integer userCommentsId;
	private String salesType;
	private double totalAccuredAmount;
	private double totalPaidAmount;
	private String balance;
	private Integer statusId;
	private Integer pgmdetailAchdId;

	/**
	 * @return the customerId
	 */
	@Column(name = "CUSTOMER_ID")
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the programDetailId
	 */
	@Column(name = "PGM_DET_ID")
	public Integer getProgramDetailId() {
		return programDetailId;
	}
	/**
	 * @param programDetailId the programDetailId to set
	 */
	public void setProgramDetailId(Integer programDetailId) {
		this.programDetailId = programDetailId;
	}
	/**
	 * @return the programHeaderId
	 */
	@Column(name = "PGM_HDR_ID")
	public Integer getProgramHeaderId() {
		return programHeaderId;
	}
	/**
	 * @param programHeaderId the programHeaderId to set
	 */
	public void setProgramHeaderId(Integer programHeaderId) {
		this.programHeaderId = programHeaderId;
	}
	/**
	 * @return the programDetailTierId
	 */
	@Column(name = "PGM_DET_TIER_ID")
	public Integer getProgramDetailTierId() {
		return programDetailTierId;
	}
	/**
	 * @param programDetailTierId the programDetailTierId to set
	 */
	public void setProgramDetailTierId(Integer programDetailTierId) {
		this.programDetailTierId = programDetailTierId;
	}
	/**
	 * @return the programDetailPaidId
	 */
	@Column(name = "PGM_DET_PAID_ID")
	public Integer getProgramDetailPaidId() {
		return programDetailPaidId;
	}
	/**
	 * @param programDetailPaidId the programDetailPaidId to set
	 */
	public void setProgramDetailPaidId(Integer programDetailPaidId) {
		this.programDetailPaidId = programDetailPaidId;
	}
	/**
	 * @return the marGlId
	 */
	@Column(name = "MAR_GL_ID")
	public Integer getMarGlId() {
		return marGlId;
	}
	/**
	 * @param marGlId the marGlId to set
	 */
	public void setMarGlId(Integer marGlId) {
		this.marGlId = marGlId;
	}
	/**
	 * @return the userCommentsId
	 */
	@Column(name = "USER_COMMENTS_ID")
	public Integer getUserCommentsId() {
		return userCommentsId;
	}
	/**
	 * @param userCommentsId the userCommentsId to set
	 */
	public void setUserCommentsId(Integer userCommentsId) {
		this.userCommentsId = userCommentsId;
	}
	/**
	 * @return the salesType
	 */
	@Column(name = "SALES_TYPE")
	public String getSalesType() {
		return salesType;
	}
	/**
	 * @param salesType the salesType to set
	 */
	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	/**
	 * @return the totalAccuredAmount
	 */
	@Column(name = "TOT_ACC_AMT")
	public double getTotalAccuredAmount() {
		return totalAccuredAmount;
	}
	/**
	 * @param totalAccuredAmount the totalAccuredAmount to set
	 */
	public void setTotalAccuredAmount(double totalAccuredAmount) {
		this.totalAccuredAmount = totalAccuredAmount;
	}
	/**
	 * @return the totalPaidAmount
	 */
	@Column(name = "TOT_PAID_AMT")
	public double getTotalPaidAmount() {
		return totalPaidAmount;
	}
	/**
	 * @param totalPaidAmount the totalPaidAmount to set
	 */
	public void setTotalPaidAmount(double totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}
	/**
	 * @return the balance
	 */
	@Column(name = "BALANCE")
	public String getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(String balance) {
		this.balance = balance;
	}
	/**
	 * @return the statusId
	 */
	@Column(name = "STATUS_ID")
	public Integer getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	/**
	 * @return the pgmdetailAchdId
	 */
	@Column(name = "PGM_DET_ACH_ID")
	public Integer getPgmdetailAchdId() {
		return pgmdetailAchdId;
	}
	/**
	 * @param pgmdetailAchdId the pgmdetailAchdId to set
	 */
	public void setPgmdetailAchdId(Integer pgmdetailAchdId) {
		this.pgmdetailAchdId = pgmdetailAchdId;
	}
	
	
	

}
