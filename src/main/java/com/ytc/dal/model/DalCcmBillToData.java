package com.ytc.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQuery(
        name = "sp_CcmBillToCreditMemoForPandT",
        procedureName = "sp_CcmBillToCreditMemoForPandT",
        resultClasses = {DalCcmBillToData.class}
        )
public class DalCcmBillToData {
	private String customerName;
	private int programId;
	private String pgmName;
	private String paidBasedOn;
	private String gurantee;
	private int amount;
	private String type;
	private int corpNo;
	private String corpName;
	private int unitsIncl;
	private int unitsExcl;
	private int bonUnitsIncl;
	private int bonUnitsExcl;
	private int nadUnitsIncl;
	private int nadUnitsExcl;
	private int unitsPlusNadIncl;
	private int unitsPlusNadExcl;
	private int bonUnitsPlusNadIncl;
	private int bonUnitsPlusNadExcl;
	private int invSalesIncl;
	private int invSalesExcl;
	private int bonSaelsIncl;
	private int bonSalesExcl;
	private int nadSalesIncl;
	private int nadSaelsExcl;
	private int invSalesPlusNadIncl;
	private int invSalesPlusNadExcl;
	private int bonSalesPlusNadIncl;
	private int bonSalesPlusNadExcl;
	private int warrantyIncl;
	private int warrantyExcl;
	private int creditAccrued;
	private String bu;
	private String freq;
	private String zoneMgr;
	private String acctMgr;
	private Calendar begDate;
	private Calendar endDate;
	
	@Column (name="PROGRAM_ID" , insertable = false, updatable = false)
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Id
	@Column (name="PROGRAM_ID")
	public int getProgramId() {
		return programId;
	}
	public void setProgramId(int programId) {
		this.programId = programId;
	}
	
	@Column (name="PGM_NAME")
	public String getPgmName() {
		return pgmName;
	}
	public void setPgmName(String pgmName) {
		this.pgmName = pgmName;
	}
	
	@Column (name="PAID_BASED_ON")
	public String getPaidBasedOn() {
		return paidBasedOn;
	}
	public void setPaidBasedOn(String paidBasedOn) {
		this.paidBasedOn = paidBasedOn;
	}
	
	@Column (name="GUARANTEE")
	public String getGurantee() {
		return gurantee;
	}
	public void setGurantee(String gurantee) {
		this.gurantee = gurantee;
	}
	
	@Column (name="AMOUNT")
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Column (name="TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column (name="CORP_NO")
	public int getCorpNo() {
		return corpNo;
	}
	public void setCorpNo(int corpNo) {
		this.corpNo = corpNo;
	}
	
	@Column (name="CORP_NAME")
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	
	@Column (name="UNITS_INCLUDED")
	public int getUnitsIncl() {
		return unitsIncl;
	}
	public void setUnitsIncl(int unitsIncl) {
		this.unitsIncl = unitsIncl;
	}
	
	@Column (name="UNITS_EXCLUDED")
	public int getUnitsExcl() {
		return unitsExcl;
	}
	public void setUnitsExcl(int unitsExcl) {
		this.unitsExcl = unitsExcl;
	}
	
	@Column (name="BONUSABLE_UNITS_INCLUDED")
	public int getBonUnitsIncl() {
		return bonUnitsIncl;
	}
	public void setBonUnitsIncl(int bonUnitsIncl) {
		this.bonUnitsIncl = bonUnitsIncl;
	}
	
	@Column (name="BONUSABLE_UNITS_EXCLUDED")
	public int getBonUnitsExcl() {
		return bonUnitsExcl;
	}
	public void setBonUnitsExcl(int bonUnitsExcl) {
		this.bonUnitsExcl = bonUnitsExcl;
	}
	
	@Column (name="NAD_UNITS_INLCUDED")
	public int getNadUnitsIncl() {
		return nadUnitsIncl;
	}
	public void setNadUnitsIncl(int nadUnitsIncl) {
		this.nadUnitsIncl = nadUnitsIncl;
	}
	
	@Column (name="NAD_UNITS_EXCLUDED")
	public int getNadUnitsExcl() {
		return nadUnitsExcl;
	}
	public void setNadUnitsExcl(int nadUnitsExcl) {
		this.nadUnitsExcl = nadUnitsExcl;
	}
	
	@Column (name="UNITS_PLUS_NAD_INLCUDED")
	public int getUnitsPlusNadIncl() {
		return unitsPlusNadIncl;
	}
	public void setUnitsPlusNadIncl(int unitsPlusNadIncl) {
		this.unitsPlusNadIncl = unitsPlusNadIncl;
	}
	
	@Column (name="UNITS_PLUS_NAD_EXCLUDED")
	public int getUnitsPlusNadExcl() {
		return unitsPlusNadExcl;
	}
	public void setUnitsPlusNadExcl(int unitsPlusNadExcl) {
		this.unitsPlusNadExcl = unitsPlusNadExcl;
	}
	
	@Column (name="BONUSABLE_UNITS_PLUS_NAD_INCLUDED")
	public int getBonUnitsPlusNadIncl() {
		return bonUnitsPlusNadIncl;
	}
	public void setBonUnitsPlusNadIncl(int bonUnitsPlusNadIncl) {
		this.bonUnitsPlusNadIncl = bonUnitsPlusNadIncl;
	}
	
	@Column (name="BONUSABLE_UNITS_PLUS_NAD_EXCLUDED")
	public int getBonUnitsPlusNadExcl() {
		return bonUnitsPlusNadExcl;
	}
	public void setBonUnitsPlusNadExcl(int bonUnitsPlusNadExcl) {
		this.bonUnitsPlusNadExcl = bonUnitsPlusNadExcl;
	}
	
	@Column (name="INV_SALES_INCLUDED")
	public int getInvSalesIncl() {
		return invSalesIncl;
	}
	public void setInvSalesIncl(int invSalesIncl) {
		this.invSalesIncl = invSalesIncl;
	}
	
	@Column (name="INV_SALES_EXCLUDED")
	public int getInvSalesExcl() {
		return invSalesExcl;
	}
	public void setInvSalesExcl(int invSalesExcl) {
		this.invSalesExcl = invSalesExcl;
	}
	
	@Column (name="BONUSABLE_SALES_INCLUDED")
	public int getBonSaelsIncl() {
		return bonSaelsIncl;
	}
	public void setBonSaelsIncl(int bonSaelsIncl) {
		this.bonSaelsIncl = bonSaelsIncl;
	}
	
	@Column (name="BONUSABLE_SALES_EXCLUDED")
	public int getBonSalesExcl() {
		return bonSalesExcl;
	}
	public void setBonSalesExcl(int bonSalesExcl) {
		this.bonSalesExcl = bonSalesExcl;
	}
	
	@Column (name="NAD_SALES_INCLUDED")
	public int getNadSalesIncl() {
		return nadSalesIncl;
	}
	public void setNadSalesIncl(int nadSalesIncl) {
		this.nadSalesIncl = nadSalesIncl;
	}
	
	@Column (name="NAD_SALES_EXCLUDED")
	public int getNadSaelsExcl() {
		return nadSaelsExcl;
	}
	
	public void setNadSaelsExcl(int nadSaelsExcl) {
		this.nadSaelsExcl = nadSaelsExcl;
	}
	
	@Column (name="INV_SALES_PLUS_NAD_INCLUDED")
	public int getInvSalesPlusNadIncl() {
		return invSalesPlusNadIncl;
	}
	public void setInvSalesPlusNadIncl(int invSalesPlusNadIncl) {
		this.invSalesPlusNadIncl = invSalesPlusNadIncl;
	}
	
	@Column (name="INV_SALES_PLUS_NAD_EXCLUDED")
	public int getInvSalesPlusNadExcl() {
		return invSalesPlusNadExcl;
	}
	public void setInvSalesPlusNadExcl(int invSalesPlusNadExcl) {
		this.invSalesPlusNadExcl = invSalesPlusNadExcl;
	}
	
	@Column (name="BONUSABLE_SALES_PLUS_NAD_INCLUDED")
	public int getBonSalesPlusNadIncl() {
		return bonSalesPlusNadIncl;
	}
	public void setBonSalesPlusNadIncl(int bonSalesPlusNadIncl) {
		this.bonSalesPlusNadIncl = bonSalesPlusNadIncl;
	}
	
	@Column (name="BONUSABLE_SALES_PLUS_NAD_EXCLUDED")
	public int getBonSalesPlusNadExcl() {
		return bonSalesPlusNadExcl;
	}
	public void setBonSalesPlusNadExcl(int bonSalesPlusNadExcl) {
		this.bonSalesPlusNadExcl = bonSalesPlusNadExcl;
	}
	
	@Column (name="WARRANTY_INCLUDED")
	public int getWarrantyIncl() {
		return warrantyIncl;
	}
	public void setWarrantyIncl(int warrantyIncl) {
		this.warrantyIncl = warrantyIncl;
	}
	
	@Column (name="WARRANTY_EXCLUDED")
	public int getWarrantyExcl() {
		return warrantyExcl;
	}
	public void setWarrantyExcl(int warrantyExcl) {
		this.warrantyExcl = warrantyExcl;
	}
	
	@Column (name="CREDIT_ACCRUED")
	public int getCreditAccrued() {
		return creditAccrued;
	}
	public void setCreditAccrued(int creditAccrued) {
		this.creditAccrued = creditAccrued;
	}
	
	@Column (name="BUSINESS_UNIT")
	public String getBu() {
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	
	@Column (name="FRQENCY")
	public String getFreq() {
		return freq;
	}
	public void setFreq(String freq) {
		this.freq = freq;
	}
	
	@Column (name="ZONE_MGR")
	public String getZoneMgr() {
		return zoneMgr;
	}
	public void setZoneMgr(String zoneMgr) {
		this.zoneMgr = zoneMgr;
	}
	
	@Column (name="ACCT_MGR")
	public String getAcctMgr() {
		return acctMgr;
	}
	public void setAcctMgr(String acctMgr) {
		this.acctMgr = acctMgr;
	}
	
	@Column (name="BEGIN_DATE")
	public Calendar getBegDate() {
		return begDate;
	}
	public void setBegDate(Calendar begDate) {
		this.begDate = begDate;
	}
	
	@Column (name="END_DATE")
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	
	
	
	
}
