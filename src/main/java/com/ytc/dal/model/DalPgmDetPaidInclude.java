package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TMP_PGM_DTL_PART_PAID_INCLUDE")
public class DalPgmDetPaidInclude {
	private String id;
	private String createdDate;
	private String createdUser;
	private String pgmDetailId;
	private String partNumber;
	
	@Id
	@Column(name="ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	@Column(name="CREATE_SYSDATE")
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
	@Column(name="CREATE_USER")
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	
	
	@Column(name="PGM_DETAIL_ID")
	public String getPgmDetailId() {
		return pgmDetailId;
	}
	public void setPgmDetailId(String pgmDetailId) {
		this.pgmDetailId = pgmDetailId;
	}
	
	@Column(name="PART_NUMBER")
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	
	

}
