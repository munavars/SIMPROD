package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TBP_USER")
@NamedQueries({
	@NamedQuery(name="DalTbpUser.getAllDetails", query = "select o from DalTbpUser o")
})
public class DalTbpUser extends DalModel {
private static final long serialVersionUID = 1L;
	
private Integer empId;

private String loginId;

private String email;

private String ddf;

private String coop;

/**
 * @return the empId
 */
@Column(name="EMP_ID")
public Integer getEmpId() {
	return empId;
}

/**
 * @param empId the empId to set
 */
public void setEmpId(Integer empId) {
	this.empId = empId;
}

/**
 * @return the loginId
 */
@Column(name="LOGIN_ID")
public String getLoginId() {
	return loginId;
}

/**
 * @param loginId the loginId to set
 */
public void setLoginId(String loginId) {
	this.loginId = loginId;
}

/**
 * @return the email
 */
@Column(name="EMAIL")
public String getEmail() {
	return email;
}

/**
 * @param email the email to set
 */
public void setEmail(String email) {
	this.email = email;
}

/**
 * @return the ddf
 */
@Column(name="DDF")
public String getDdf() {
	return ddf;
}

/**
 * @param ddf the ddf to set
 */
public void setDdf(String ddf) {
	this.ddf = ddf;
}

/**
 * @return the coop
 */
@Column(name="COOP")
public String getCoop() {
	return coop;
}

/**
 * @param coop the coop to set
 */
public void setCoop(String coop) {
	this.coop = coop;
}
	
	
}
