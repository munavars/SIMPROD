package com.ytc.dal.model;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PROGRAM_DETAIL")
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
	private int actualMarker;
	private String trueUp;

	private DalEmployee zmAppById;
	private Calendar zmAppDate;
	private DalEmployee dirAppById;
	private Calendar dirAppDate;
	private DalEmployee execAppById;
	private Calendar execAppDate;
	private DalEmployee tbpAppById;
	private Calendar tbpAppDate;

	private int statusId;
	private DalProgramMaster programMaster;
	private DalProgramHeader dalProgramHeader;
	private Integer paidType;
    private Set<DalProgramDetAchieved> dalProgramDetAchievedList;
    private Set<DalProgramDetPaid> dalProgramDetPaidList;
    private DalProgramDetailTier pgmDetailTier;
    private DalAccrualData accuralData;
    /*private Set<DalProgramDetailTier> dalProgramDetailTierSet;*/
    
    
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
    /**
    * @return the actualMarker
    */
    @Column(name = "ACTUAL_MARKER")
    public int getActualMarker() {
                    return actualMarker;
    }
    /**
    * @param actualMarker the actualMarker to set
    */
    public void setActualMarker(int actualMarker) {
                    this.actualMarker = actualMarker;
    }
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
    * @return the zmAppById
    */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ZM_APP_BY_ID", referencedColumnName = "ID")
    public DalEmployee getZmAppById() {
                    return zmAppById;
    }
    /**
    * @param zmAppById the zmAppById to set
    */
    public void setZmAppById(DalEmployee zmAppById) {
                    this.zmAppById = zmAppById;
    }
    /**
    * @return the zmAppDate
    */
    @Column(name = "ZM_APP_DATE")
    public Calendar getZmAppDate() {
                    return zmAppDate;
    }
    /**
    * @param zmAppDate the zmAppDate to set
    */
    public void setZmAppDate(Calendar zmAppDate) {
                    this.zmAppDate = zmAppDate;
    }
    /**
    * @return the dirAppById
    */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="DIR_APP_BY_ID", referencedColumnName = "ID")
    public DalEmployee getDirAppById() {
    	return dirAppById;
    }
    /**
    * @param dirAppById the dirAppById to set
    */
    public void setDirAppById(DalEmployee dirAppById) {
                    this.dirAppById = dirAppById;
    }
    /**
    * @return the dirAppDate
    */
    @Column(name = "DIR_APP_DATE")
    public Calendar getDirAppDate() {
                    return dirAppDate;
    }
    /**
    * @param dirAppDate the dirAppDate to set
    */
    public void setDirAppDate(Calendar dirAppDate) {
                    this.dirAppDate = dirAppDate;
    }
    /**
    * @return the execAppById
    */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="EXEC_APP_BY_ID", referencedColumnName = "ID")
    public DalEmployee getExecAppById() {
                    return execAppById;
    }
    /**
    * @param execAppById the execAppById to set
    */
    public void setExecAppById(DalEmployee execAppById) {
                    this.execAppById = execAppById;
    }
    /**
    * @return the execAppDate
    */
    @Column(name = "EXEC_APP_DATE")
    public Calendar getExecAppDate() {
                    return execAppDate;
    }
    /**
    * @param execAppDate the execAppDate to set
    */
    public void setExecAppDate(Calendar execAppDate) {
                    this.execAppDate = execAppDate;
    }
    /**
    * @return the tbpAppById
    */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="TBP_APP_BY_ID", referencedColumnName = "ID")
    public DalEmployee getTbpAppById() {
    	return tbpAppById;
    }
    /**
    * @param tbpAppById the tbpAppById to set
    */
    public void setTbpAppById(DalEmployee tbpAppById) {
                    this.tbpAppById = tbpAppById;
    }
    /**
    * @return the tbpAppDate
    */
    @Column(name = "TBP_APP_DATE")
    public Calendar getTbpAppDate() {
                    return tbpAppDate;
    }
    /**
    * @param tbpAppDate the tbpAppDate to set
    */
    public void setTbpAppDate(Calendar tbpAppDate) {
                    this.tbpAppDate = tbpAppDate;
    }
    /**
    * @return the statusId
    */
    @Column(name = "STATUS_ID")
    public int getStatusId() {
                    return statusId;
    }
    /**
    * @param statusId the statusId to set
    */
    public void setStatusId(int statusId) {
                    this.statusId = statusId;
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
    
    @OneToMany(mappedBy="dalProgramDetail", cascade=CascadeType.ALL, fetch =FetchType.EAGER)
    public Set<DalProgramDetAchieved> getDalProgramDetAchievedList() {
                    return dalProgramDetAchievedList;
    }
    public void setDalProgramDetAchievedList(Set<DalProgramDetAchieved> dalProgramDetAchievedList) {
                    this.dalProgramDetAchievedList = dalProgramDetAchievedList;
    }
    
    @OneToMany(mappedBy="dalProgramDetails", cascade=CascadeType.ALL, fetch =FetchType.EAGER)
    public Set<DalProgramDetPaid> getDalProgramDetPaidList() {
                    return dalProgramDetPaidList;
    }
    public void setDalProgramDetPaidList(Set<DalProgramDetPaid> dalProgramDetPaidList) {
                    this.dalProgramDetPaidList = dalProgramDetPaidList;
    }
                
    /**
>>>>>>> Stashed changes
	 * @return the pgmDetailTier
	 */

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "ID", referencedColumnName = "PGM_DETAIL_ID", insertable=false, updatable=false),
		@JoinColumn(name = "ACTUAL_MARKER", referencedColumnName = "LEVEL",  insertable=false, updatable=false)})
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

}
