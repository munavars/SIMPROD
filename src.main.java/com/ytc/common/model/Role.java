package com.ytc.common.model;

import com.ytc.common.type.Status;

public class Role extends Model {


	private static final long serialVersionUID = 1L;


	private String roleDescription;
	private Status status;


	

	public Role(Role r) {
		this.roleDescription = r.roleDescription;
		this.status = r.status;

	}

	public String getRoleDescription() {
		return roleDescription;
	}


	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
