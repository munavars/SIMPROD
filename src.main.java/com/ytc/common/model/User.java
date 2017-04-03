package com.ytc.common.model;

import java.util.ArrayList;
import java.util.List;

public class User extends AuditableModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public User() {

	}	

	private String userName;
	private String status;
	private final List<Role> roles = new ArrayList<Role>();


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public List<Role> getRoles() {
		return roles;
	}



}
