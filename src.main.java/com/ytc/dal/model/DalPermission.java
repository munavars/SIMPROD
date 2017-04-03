package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ytc.common.type.Status;

//@Entity
@Table(name = "PERMISSION")
public class DalPermission extends DalAuditableModel {


	private String name;
	private Status status;




	public DalPermission(DalPermission p) {
		this.name = p.name;
		this.status = p.status;
	}


	@Column(name="NAME")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Column(name="STATUS")
	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


}
