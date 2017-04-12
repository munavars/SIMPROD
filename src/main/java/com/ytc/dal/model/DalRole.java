package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ytc.common.type.Status;

@Entity
@Table(name = "ROLE")
public class DalRole extends DalAuditableModel {


	private String roleDescription;
	private DalUser user;
	private Status status;



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

	
	public void setUser(DalUser user) {
		this.user = user;
	}
	
	@ManyToOne(targetEntity = com.ytc.dal.model.DalUser.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_NAME", referencedColumnName = "ID", nullable = false)
	public DalUser getUser() {
		return user;
	}


}
