package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ytc.common.type.Status;

//@Entity
@Table(name = "ROLE")
public class DalRole extends DalAuditableModel {


	private String roleDescription;
	private Status status;




	public DalRole(DalRole r) {
		this.roleDescription = r.roleDescription;
		this.status = r.status;
	}


	@Column(name="ROLE_DESCRIPTION")
	public String getRoleDescription() {
		return roleDescription;
	}


	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}


	@Column(name="STATUS")
	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


}
