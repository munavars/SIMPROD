package com.ytc.dal.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PROGRAM_DETAIL")
public class DalProgramDetail extends DalAuditableModel {

	private Calendar programStartDate;
    private Calendar programEndDate;
    private String longDesc;
    private String shortDesc;
    private DalPricingType dalPricingType;
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
    private String zmAppById;
    private Calendar zmAppDate;
    private String dirAppById;
    private Calendar dirAppDate;
    private String execAppById;
    private Calendar execAppDate;
    private String tbpAppById;
    private Calendar tbpAppDate;
    private int statusId;
    private DalProgramMaster programMaster;
	private DalProgramHeader dalProgramHeader;
	private DalPaidType dalPaidType;
	private List<DalProgramDetAchieved> dalProgramDetAchievedList;
	private List<DalProgramDetPaid> dalProgramDetPaidList;
	
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
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PAID_TYPE_ID", referencedColumnName = "ID")
	public DalPaidType getDalPaidType() {
		return dalPaidType;
	}
	public void setDalPaidType(DalPaidType dalPaidType) {
		this.dalPaidType = dalPaidType;
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
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "ACH_BASED_METRIC_ID", referencedColumnName = "BASE_ITEM_ID")
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
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "ACH_BASED_FREQ_ID", referencedColumnName = "FREQ_ID")
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
    @Column(name = "ZM_APP_BY_ID")
    public String getZmAppById() {
                    return zmAppById;
    }
    /**
    * @param zmAppById the zmAppById to set
    */
    public void setZmAppById(String zmAppById) {
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
    @Column(name = "DIR_APP_BY_ID")
    public String getDirAppById() {
                    return dirAppById;
    }
    /**
    * @param dirAppById the dirAppById to set
    */
    public void setDirAppById(String dirAppById) {
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
    @Column(name = "EXEC_APP_BY_ID")
    public String getExecAppById() {
                    return execAppById;
    }
    /**
    * @param execAppById the execAppById to set
    */
    public void setExecAppById(String execAppById) {
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
    @Column(name = "TBP_APP_BY_ID")
    public String getTbpAppById() {
                    return tbpAppById;
    }
    /**
    * @param tbpAppById the tbpAppById to set
    */
    public void setTbpAppById(String tbpAppById) {
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
    @OneToOne(cascade=CascadeType.ALL)
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
    @OneToOne(cascade=CascadeType.ALL)
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
    * @return the dalPricingType
    */
    
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PRICING_TYPE_ID", referencedColumnName = "ID")
    public DalPricingType getDalPricingType() {
		return dalPricingType;
	}
	
    /**
    * @param dalPricingType the pricingType to set
    */
	public void setDalPricingType(DalPricingType dalPricingType) {
		this.dalPricingType = dalPricingType;
	}
    
    /**
    * @return the programMaster
    */
    @ManyToOne(cascade=CascadeType.ALL)
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
	public List<DalProgramDetAchieved> getDalProgramDetAchievedList() {
		return dalProgramDetAchievedList;
	}
	public void setDalProgramDetAchievedList(List<DalProgramDetAchieved> dalProgramDetAchievedList) {
		this.dalProgramDetAchievedList = dalProgramDetAchievedList;
	}
	
	@OneToMany(mappedBy="dalProgramDetails", cascade=CascadeType.ALL, fetch =FetchType.EAGER)
	public List<DalProgramDetPaid> getDalProgramDetPaidList() {
		return dalProgramDetPaidList;
	}
	public void setDalProgramDetPaidList(List<DalProgramDetPaid> dalProgramDetPaidList) {
		this.dalProgramDetPaidList = dalProgramDetPaidList;
	}
}
