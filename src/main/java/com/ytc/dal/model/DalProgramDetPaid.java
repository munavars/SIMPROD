package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PGM_DET_PAID")
public class DalProgramDetPaid extends DalAuditableModel{
	
    /**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	private DalProgramDetail dalProgramDetails;
    
    private String method;

	private Integer tagId;
    
    private String value;

    private String displayValue;
    
	@ManyToOne
	@JoinColumn(name = "PGM_DETAIL_ID", referencedColumnName = "ID")
	public DalProgramDetail getDalProgramDetails() {
		return dalProgramDetails;
	}

	public void setDalProgramDetails(DalProgramDetail dalProgramDetails) {
		this.dalProgramDetails = dalProgramDetails;
	}

	@Column(name = "METHOD")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

    @Column(name = "TAG_ID")
	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	@Column(name = "VALUE")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "DISPLAY_VALUE")
	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
}
