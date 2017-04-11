/**
 * 
 */
package com.ytc.dal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ArunP
 *
 */
@Entity
@Table(name = "PROGRAM_MASTER")
public class DalProgramMaster extends DalModel{

private String ProgramId;
private String Program;
private String CreatedBy;
private Date CreatedDate;
private String ModifiedBy;
private Date ModifiedDate;

/**
 * @return the programId
 */
@Id
@Column(name = "PGM_ID")
public String getProgramId() {
	return ProgramId;
}
/**
 * @param programId the programId to set
 */
public void setProgramId(String programId) {
	ProgramId = programId;
}
/**
 * @return the program
 */
@Column(name = "PROGRAM")
public String getProgram() {
	return Program;
}
/**
 * @param program the program to set
 */
public void setProgram(String program) {
	Program = program;
}
/**
 * @return the createdBy
 */
@Column(name = "CREATED_BY")
public String getCreatedBy() {
	return CreatedBy;
}
/**
 * @param createdBy the createdBy to set
 */
public void setCreatedBy(String createdBy) {
	CreatedBy = createdBy;
}
/**
 * @return the createdDate
 */
@Column(name = "CREATED_DATE")
public Date getCreatedDate() {
	return CreatedDate;
}
/**
 * @param createdDate the createdDate to set
 */
public void setCreatedDate(Date createdDate) {
	CreatedDate = createdDate;
}
/**
 * @return the modifiedBy
 */
@Column(name = "MODIFIED_BY")
public String getModifiedBy() {
	return ModifiedBy;
}
/**
 * @param modifiedBy the modifiedBy to set
 */
public void setModifiedBy(String modifiedBy) {
	ModifiedBy = modifiedBy;
}
/**
 * @return the modifiedDate
 */
@Column(name = "MODIFIED_DATE")
public Date getModifiedDate() {
	return ModifiedDate;
}
/**
 * @param modifiedDate the modifiedDate to set
 */
public void setModifiedDate(Date modifiedDate) {
	ModifiedDate = modifiedDate;
}

}
