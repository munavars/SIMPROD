package com.ytc.common.model;

import java.math.BigDecimal;

public class ProgramTierDetail extends Model {

	/**
	 * Serial value. default.
	 */
	private static final long serialVersionUID = 1L;

	private Integer programDetailId;
	private Integer level;
	private BigDecimal amount;
	private String tierType;
	private Integer beginRange;
	
	public Integer getProgramDetailId() {
		return programDetailId;
	}
	public void setProgramDetailId(Integer programDetailId) {
		this.programDetailId = programDetailId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getTierType() {
		return tierType;
	}
	public void setTierType(String tierType) {
		this.tierType = tierType;
	}
	public Integer getBeginRange() {
		return beginRange;
	}
	public void setBeginRange(Integer beginRange) {
		this.beginRange = beginRange;
	}
}
