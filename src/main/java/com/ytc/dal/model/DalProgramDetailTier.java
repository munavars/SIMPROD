/**
 * 
 */
package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author ArunP
 *
 */
@Entity
@Table(name = "PGM_DET_TIER")
public class DalProgramDetailTier extends DalAuditableModel{

	
	/**
	 * 
	 */
	//private String id;
	private Integer programDetailId;
	private Integer level;
	private double amount;
	private String tierType;
	private Integer beginRange;
	/**
	 * @return the id
	 */
	
	/**
	 * @return the programDetailId
	 */
	@Column(name = "PGM_DETAIL_ID")
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
	 * @return the level
	 */
	@Column(name = "LEVEL")
	public Integer getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * @return the amount
	 */
	@Column(name = "AMOUNT")
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return the tierType
	 */
	@Column(name = "TIER_TYPE")
	public String getTierType() {
		return tierType;
	}
	/**
	 * @param tierType the tierType to set
	 */
	public void setTierType(String tierType) {
		this.tierType = tierType;
	}
	/**
	 * @return the beginRange
	 */
	@Column(name = "BEG_RANGE")
	public Integer getBeginRange() {
		return beginRange;
	}
	/**
	 * @param beginRange the beginRange to set
	 */
	public void setBeginRange(Integer beginRange) {
		this.beginRange = beginRange;
	}
	
	
	
}
