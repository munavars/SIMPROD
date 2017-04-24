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
    
    private DalTagItems achTagItems;
    
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

	@OneToOne
    @JoinColumn(name = "ACH_TAG_ID", referencedColumnName = "ITEM_ID")
	public DalTagItems getAchTagItems() {
		return achTagItems;
	}

	public void setAchTagItems(DalTagItems achTagItems) {
		this.achTagItems = achTagItems;
	}

    @Column(name = "ACH_VALUE")
	public String getAchValue() {
		return achValue;
	}

	public void setAchValue(String achValue) {
		this.achValue = achValue;
	}
}
