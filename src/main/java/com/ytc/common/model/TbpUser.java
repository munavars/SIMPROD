package com.ytc.common.model;

public class TbpUser extends Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private Integer empId;

private String loginId;

private String email;

private String ddf;

private String coop;

/**
 * @return the empId
 */
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
