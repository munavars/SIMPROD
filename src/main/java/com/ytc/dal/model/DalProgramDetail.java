package com.ytc.dal.model;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PROGRAM_DETAIL")
@NamedQueries({
@NamedQuery(name="DalProgramDetail.checkDuplicateRecords", query = "select o from DalProgramDetail o where o.programStartDate=:PGM_START_DATE and o.programEndDate=:PGM_END_DATE and o.accrualAmount=:ACCRUAL_AMOUNT and o.dalProgramType.id=:PGM_TYPE_ID")
})
public class DalProgramDetail extends DalAuditableModel {

	/**
	 * serialVersion.
	 */
	private static final long serialVersionUID = 1L;
	private Calendar programStartDate;
	private Calendar programEndDate;
	private String longDesc;
	private String shortDesc;
	private Integer pricingType;
	private String isTiered;
	private double accrualAmount;
	private String accrualType;
	private DalFrequency paidFrequency;
	private DalBaseItems paidBasedOn;
	private DalBaseItems achBasedMetric;
	private DalFrequency achBasedFreq;
	private String payTo;
	private String BTL;
	private int budgetMarker;
	private int forecastMarker;
	//private int actualMarker;
	private Integer schdTierMarker;
	private String trueUp;
	
	/*private DalEmployee zmAppById;
	private Calendar zmAppDate;
	private DalStatus zmAppStatus;
	private DalEmployee tbpAppById;
	private Calendar tbpAppDate;
	private DalStatus tbAppStatus;
	private DalEmployee dirAppById;
	private Calendar dirAppDate;
	private DalEmployee execAppById;
	private Calendar execAppDate*/;

	private DalProgramMaster programMaster;
	private DalProgramHeader dalProgramHeader;
	private Integer paidType;
    private Set<DalProgramDetAchieved> dalProgramDetAchievedList;
    private Set<DalProgramDetPaid> dalProgramDetPaidList;
    private DalProgramDetailTier pgmDetailTier;
    private DalAccrualData accuralData;
    
    private DalStatus status;
    /*private Set<DalProgramDetailTier> dalProgramDetailTierSet;*/
    private DalProgramType dalProgramType;
    private List<DalWorkflowStatus> dalWorkflowStatusList;
    
    private DalUserComments dalUserComments;
	private Integer tbpCheck;
	private String glCode;
	private Integer estimatedAccrual;
    
    @Column(name = "LONG_DESC")
    public String getLongDesc() {
                    return longDesc;
    }
    public void setLongDesc(String longDesc) {
                    this.longDesc = longDesc;
    }
    
    @Column(name = "SHORT_DESC")
    public String getShortDesc() {
                    return shortDesc;
    }
    public void setShortDesc(String shortDesc) {
                    this.shortDesc = shortDesc;
    }
      
    /**
     * @return the progamStartDate
    */
    @Column(name = "PGM_START_DATE")
    public Calendar getProgramStartDate() {
                    return programStartDate;
    }
    /**
    * @param progamStartDate the progamStartDate to set
    */
    public void setProgramStartDate(Calendar progamStartDate) {
                    this.programStartDate = progamStartDate;
    }
    /**
    * @return the programEndDate
    */
    @Column(name = "PGM_END_DATE")
    public Calendar getProgramEndDate() {
                    return programEndDate;
    }
    /**
    * @param programEndDate the programEndDate to set
    */
    public void setProgramEndDate(Calendar programEndDate) {
                    this.programEndDate = programEndDate;
    }
    
    
    /**
    * @return the isTiered
    */
    @Column(name = "IS_TIERED")
    public String getIsTiered() {
                    return isTiered;
    }
    /**
    * @param isTiered the isTiered to set
    */
    public void setIsTiered(String isTiered) {
                    this.isTiered = isTiered;
    }
    
    /**
    * @return the accrualAmount
    */
    @Column(name = "ACCRUAL_AMOUNT")
    public double getAccrualAmount() {
                    return accrualAmount;
    }
    /**
    * @param accrualAmount the accrualAmount to set
    */
    public void setAccrualAmount(double accrualAmount) {
                    this.accrualAmount = accrualAmount;
    }
    /**
    * @return the accrualType
    */
    @Column(name = "ACCRUAL_TYPE")
    public String getAccrualType() {
                    return accrualType;
    }
    /**
    * @param accrualType the accrualType to set
    */
    public void setAccrualType(String accrualType) {
                    this.accrualType = accrualType;
    }
   
    /**
    * @return the achBasedMetricId
    */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACH_BASED_METRIC_ID", referencedColumnName = "ID")
    public DalBaseItems getAchBasedMetric() {
                    return achBasedMetric;
    }
    /**
    * @param achBasedMetricId the achBasedMetricId to set
    */
    public void setAchBasedMetric(DalBaseItems achBasedMetric) {
                    this.achBasedMetric = achBasedMetric;
    }
    /**
    * @return the achBasedFreqId
    */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACH_BASED_FREQ_ID", referencedColumnName = "ID")
    public DalFrequency getAchBasedFreq() {
        return achBasedFreq;
    }
    /**
    * @param achBasedFreqId the achBasedFreqId to set
    */
    public void setAchBasedFreq(DalFrequency achBasedFreq) {
        this.achBasedFreq = achBasedFreq;
    }
    /**
    * @return the payTo
    */
    @Column(name = "PAY_TO")
    public String getPayTo() {
                    return payTo;
    }
    /**
    * @param payTo the payTo to set
    */
    public void setPayTo(String payTo) {
                    this.payTo = payTo;
    }
    /**
    * @return the bTL
    */
    @Column(name = "BTL")
    public String getBTL() {
                    return BTL;
    }
    /**
    * @param bTL the bTL to set
    */
    public void setBTL(String bTL) {
                    BTL = bTL;
    }
    /**
    * @return the budgetMarker
    */
    @Column(name = "BUDGET_MARKER")
    public int getBudgetMarker() {
                    return budgetMarker;
    }
    /**
    * @param budgetMarker the budgetMarker to set
    */
    public void setBudgetMarker(int budgetMarker) {
                    this.budgetMarker = budgetMarker;
    }
    /**
    * @return the forecastMarker
    */
    @Column(name = "FORECAST_MARKER")
    public int getForecastMarker() {
                    return forecastMarker;
    }
    /**
    * @param forecastMarker the forecastMarker to set
    */
    public void setForecastMarker(int forecastMarker) {
                    this.forecastMarker = forecastMarker;
    }
    @Column(name = "SCHD_TIER_MARKER")
    public Integer getSchdTierMarker() {
		return schdTierMarker;
	}
	public void setSchdTierMarker(Integer schdTierMarker) {
		this.schdTierMarker = schdTierMarker;
	}
	/* *//**
    * @return the actualMarker
    *//*
    @Column(name = "ACTUAL_MARKER")
    public int getActualMarker() {
                    return schdTierMarker;
    }
    *//**
    * @param actualMarker the actualMarker to set
    *//*
    public void setActualMarker(int actualMarker) {
                    this.schdTierMarker = actualMarker;
    }*/
    /**
    * @return the trueUp
    */
    @Column(name = "TRUE_UP")
    public String getTrueUp() {
                    return trueUp;
    }
    /**
    * @param trueUp the trueUp to set
    */
    public void setTrueUp(String trueUp) {
                    this.trueUp = trueUp;
    }

    /**
    * @return the paidFrequency
    */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="PAID_BASED_FREQ_ID")
    public DalFrequency getPaidFrequency() {
                    return paidFrequency;
    }
    /**
    * @param paidFrequency the paidFrequency to set
    */
    public void setPaidFrequency(DalFrequency paidFrequency) {
                    this.paidFrequency = paidFrequency;
    }
   /**
    * @return the paidBase
    */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="PAID_BASED_METRIC_ID")
    public DalBaseItems getPaidBasedOn() {
                    return paidBasedOn;
    }
    /**
    * @param paidBase the paidBase to set
    */
    public void setPaidBasedOn(DalBaseItems paidBase) {
                    this.paidBasedOn = paidBase;
    }
    
    /**
    * @return the programMaster
    */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="PGM_ID")
    public DalProgramMaster getProgramMaster() {
                return programMaster;
    }
    /**
    * @param programMaster the programMaster to set
    */
    public void setProgramMaster(DalProgramMaster programMaster) {
        this.programMaster = programMaster;
    }
                
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "PGM_HDR_ID", referencedColumnName = "ID")
    public DalProgramHeader getDalProgramHeader() {
                    return dalProgramHeader;
    }

    public void setDalProgramHeader(DalProgramHeader dalProgramHeader) {
                    this.dalProgramHeader = dalProgramHeader;
    }
    
    @OneToMany(mappedBy="dalProgramDetail", cascade=CascadeType.ALL, fetch =FetchType.EAGER, orphanRemoval = true)
    public Set<DalProgramDetAchieved> getDalProgramDetAchievedList() {
                    return dalProgramDetAchievedList;
    }
    public void setDalProgramDetAchievedList(Set<DalProgramDetAchieved> dalProgramDetAchievedList) {
                    this.dalProgramDetAchievedList = dalProgramDetAchievedList;
    }
    
    @OneToMany(mappedBy="dalProgramDetails", cascade=CascadeType.ALL, fetch =FetchType.EAGER, orphanRemoval = true)
    public Set<DalProgramDetPaid> getDalProgramDetPaidList() {
                    return dalProgramDetPaidList;
    }
    public void setDalProgramDetPaidList(Set<DalProgramDetPaid> dalProgramDetPaidList) {
                    this.dalProgramDetPaidList = dalProgramDetPaidList;
    }
                
    /**
	 * @return the pgmDetailTier
	 */

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "ID", referencedColumnName = "PGM_DETAIL_ID", insertable=false, updatable=false),
		@JoinColumn(name = "SCHD_TIER_MARKER", referencedColumnName = "LEVEL",  insertable=false, updatable=false)})
		//@JoinColumn(name = "ACTUAL_MARKER", referencedColumnName = "LEVEL",  insertable=false, updatable=false)})
	public DalProgramDetailTier getPgmDetailTier() {
		return pgmDetailTier;
	}
	/**
	 * @param pgmDetailTier the pgmDetailTier to set
	 */
	public void setPgmDetailTier(DalProgramDetailTier pgmDetailTier) {
		this.pgmDetailTier = pgmDetailTier;
	}

	/**
	 * @return the accuralData
	 */
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ID", referencedColumnName = "PGM_DET_ID", insertable=false, updatable=false)
	public DalAccrualData getAccuralData() {
		return accuralData;
	}
	/**
	 * @param accuralData the accuralData to set
	 */
	public void setAccuralData(DalAccrualData accuralData) {
		this.accuralData = accuralData;
	}

	@Column(name = "PRICING_TYPE_ID")
	public Integer getPricingType() {
		return pricingType;
	}
	public void setPricingType(Integer pricingType) {
		this.pricingType = pricingType;
	}

	@Column(name = "PAID_TYPE_ID")
	public Integer getPaidType() {
		return paidType;
	}
	public void setPaidType(Integer paidType) {
		this.paidType = paidType;
	}

/*	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name = "ID")
	public Set<DalProgramDetailTier> getDalProgramDetailTierSet() {
		return dalProgramDetailTierSet;
	}
	public void setDalProgramDetailTierSet(Set<DalProgramDetailTier> dalProgramDetailTierSet) {
		this.dalProgramDetailTierSet = dalProgramDetailTierSet;
	}*/
	
	@OneToOne
	@JoinColumn(name = "STATUS_ID", referencedColumnName = "ID")
	public DalStatus getStatus() {
		return status;
	}

	public void setStatus(DalStatus status) {
		this.status = status;
	}
	
	@OneToOne
	@JoinColumn(name = "PGM_TYPE_ID", referencedColumnName = "ID")
	public DalProgramType getDalProgramType() {
		return dalProgramType;
	}
	
	public void setDalProgramType(DalProgramType dalProgramType) {
		this.dalProgramType = dalProgramType;
	}

	@Override
	public void setCreatedBy(DalEmployee createdBy) {
		if(dalProgramHeader != null && dalProgramHeader.getId() == null){
			dalProgramHeader.setCreatedBy(createdBy);
		}
		if(dalProgramDetPaidList != null && !dalProgramDetPaidList.isEmpty()){
			for(DalProgramDetPaid dalProgramDetPaid : dalProgramDetPaidList){
				if(dalProgramDetPaid.getId() == null){
					dalProgramDetPaid.setCreatedBy(createdBy);
				}
			}
		}
		if(dalProgramDetAchievedList != null && !dalProgramDetAchievedList.isEmpty()){
			for(DalProgramDetAchieved dalProgramDetAchieved  : dalProgramDetAchievedList){
				if(dalProgramDetAchieved.getId() == null){
					dalProgramDetAchieved.setCreatedBy(createdBy);
				}
			}
		}
		if(dalUserComments != null){
			if(dalUserComments.getId() == null){
				dalUserComments.setCreatedBy(createdBy);
				dalUserComments.setUser(createdBy);
			}
		}
/*		if(dalWorkflowStatusList != null && !dalWorkflowStatusList.isEmpty()){
			for(DalWorkflowStatus dalWorkflowStatus : dalWorkflowStatusList){
				if(dalWorkflowStatus.getId() == null){
					dalWorkflowStatus.setCreatedBy(createdBy);
					dalWorkflowStatus.setApprover(createdBy);
				}
			}
		}*/
		super.setCreatedBy(createdBy);
	}
	
	@Override
	public void setModifiedBy(DalEmployee modifiedBy) {
		if(dalProgramHeader != null){
			dalProgramHeader.setModifiedBy(modifiedBy);
		}
		if(dalProgramDetPaidList != null && !dalProgramDetPaidList.isEmpty()){
			for(DalProgramDetPaid dalProgramDetPaid : dalProgramDetPaidList){
				dalProgramDetPaid.setModifiedBy(modifiedBy);
			}
		}
		if(dalProgramDetAchievedList != null && !dalProgramDetAchievedList.isEmpty()){
			for(DalProgramDetAchieved dalProgramDetAchieved : dalProgramDetAchievedList){
				dalProgramDetAchieved.setModifiedBy(modifiedBy);
			}
		}
		if(dalWorkflowStatusList != null && !dalWorkflowStatusList.isEmpty()){
			for(DalWorkflowStatus dalWorkflowStatus : dalWorkflowStatusList){
				if(dalWorkflowStatus.getId() == null){
					/**Updating created by here is because, for already existing program detail,
					 * approver will add comments, in such case, createdBy setter will not get called.
					 * only modified by setter will be called, since the person will be same in 
					 * all the three fields, populating all these from here is the correct approach.*/
					if(dalWorkflowStatus.getCreatedBy() == null){
						dalWorkflowStatus.setCreatedBy(modifiedBy);	
					}
					if(dalWorkflowStatus.getApprover() == null){
						dalWorkflowStatus.setApprover(modifiedBy);	
					}
					dalWorkflowStatus.setModifiedBy(modifiedBy);
				}
			}
		}
		if(dalUserComments != null){
			if(dalUserComments.getId() == null){
				dalUserComments.setCreatedBy(modifiedBy);
				dalUserComments.setUser(modifiedBy);
			}
			dalUserComments.setModifiedBy(modifiedBy);
		}
		super.setModifiedBy(modifiedBy);
	}

	@OneToMany(mappedBy = "dalProgramDetailWf", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<DalWorkflowStatus> getDalWorkflowStatusList() {
		return dalWorkflowStatusList;
	}
	
	public void setDalWorkflowStatusList(List<DalWorkflowStatus> dalWorkflowStatusList) {
		this.dalWorkflowStatusList = dalWorkflowStatusList;
	}
	
	@OneToOne(mappedBy="dalProgramDetailForComment", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
	public DalUserComments getDalUserComments() {
		return dalUserComments;
	}
	
	public void setDalUserComments(DalUserComments dalUserComments) {
		this.dalUserComments = dalUserComments;
	}
	
	/**
     * @return the tbpCheck
     */
     @Column(name = "TBP")
     public Integer getTbpCheck() {
                     return tbpCheck;
     }
     /**
     * @param tbpCheck the tbpCheck to set
     */
     public void setTbpCheck(Integer tbpCheck) {
                     this.tbpCheck = tbpCheck;
     }
     /**
      * @return the glCode
      */
      @Column(name = "GL_CODE")
      public String getGlCode() {
                      return glCode;
      }
      /**
      * @param getGlCode the getGlCode to set
      */
      public void setGlCode(String glCode) {
                      this.glCode = glCode;
      }
	/**
	 * @return the estimatedAccrual
	 */
    @Column(name = "EST_ACCRUAL")
	public Integer getEstimatedAccrual() {
		return estimatedAccrual;
	}
	/**
	 * @param estimatedAccrual the estimatedAccrual to set
	 */
	public void setEstimatedAccrual(Integer estimatedAccrual) {
		this.estimatedAccrual = estimatedAccrual;
	}
      
      
}
