package com.ytc.dal.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity
@Table(name = "USER")
public class DalUser extends DalAuditableModel {


	public DalUser() {

	}	

	private String userName;
	private String status;
	private final List<DalRole> roles = new ArrayList<DalRole>();

	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@ManyToOne(targetEntity = com.ytc.dal.model.DalRole.class)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = true)
	public List<DalRole> getRoles() {
		return roles;
	}



}
