package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PGM_DET_ACHIEVED")
public class DalProgramDetAchieved extends DalAuditableModel{

    private DalProgramDetail dalProgramDetail;
   
    private String achMethod;
    
    private Integer achTagId;

	private String achValue;

    @ManyToOne
	@JoinColumn(name = "PGM_DETAIL_ID", referencedColumnName = "ID")
	public DalProgramDetail getDalProgramDetail() {
		return dalProgramDetail;
	}

	public void setDalProgramDetail(DalProgramDetail dalProgramDetail) {
		this.dalProgramDetail = dalProgramDetail;
	}

	@Column(name = "ACH_METHOD")
	public String getAchMethod() {
		return achMethod;
	}

	public void setAchMethod(String achMethod) {
		this.achMethod = achMethod;
	}

    @Column(name = "ACH_VALUE")
	public String getAchValue() {
		return achValue;
	}

	public void setAchValue(String achValue) {
		this.achValue = achValue;
	}
	
	@Column(name = "ACH_TAG_ID")
    public Integer getAchTagId() {
		return achTagId;
	}

	public void setAchTagId(Integer achTagId) {
		this.achTagId = achTagId;
	}
}
