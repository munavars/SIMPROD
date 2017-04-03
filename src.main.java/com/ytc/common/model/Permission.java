package com.ytc.common.model;

import com.ytc.common.type.Status;

public class Permission extends Model {


	private static final long serialVersionUID = 1L;

	private String name;
	private Status status;


	public Permission(Permission r) {
		this.name = r.name;
		this.status = r.status;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
