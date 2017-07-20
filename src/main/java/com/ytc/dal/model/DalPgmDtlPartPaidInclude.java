package com.ytc.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TMP_PGM_DTL_PART_PAID_INCLUDE")
@NamedStoredProcedureQuery(
        name = "sp_PgmDetPartPaidInclude",
        procedureName = "sp_PgmDetPartPaidInclude",
        resultClasses = {DalPgmDtlPartPaidInclude.class}
        )
public class DalPgmDtlPartPaidInclude {
	

	private int Id;
	private Calendar createSysDate;
	private String createUser;
	private int pgmDtlId;
	private String partNumber;
	private String PartIncExclFlag;
	
	@javax.persistence.Id
	@Column (name="ID")
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}	
	
	@Column(name="CREATE_SYS_DATE")
	public Calendar getCreateSysDate() {
		return createSysDate;
	}
	public void setCreateSysDate(Calendar createSysDate) {
		this.createSysDate = createSysDate;
	}
	
	@Column(name="CREATE_USER")
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Column(name="PGM_DETAIL_ID")
	public int getPgmDtlId() {
		return pgmDtlId;
	}
	public void setPgmDtlId(int pgmDtlId) {
		this.pgmDtlId = pgmDtlId;
	}
	
	@Column(name="PART_NUMBER")
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	@Column(name="PART_INCL_EXCL_FLAG")
	public String getPartIncExclFlag() {
		return PartIncExclFlag;
	}
	public void setPartIncExclFlag(String partIncExclFlag) {
		PartIncExclFlag = partIncExclFlag;
	}
	
	
}
