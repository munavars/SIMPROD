package com.ytc.dal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TABLE")
public class DalUser extends DalAuditableModel {

	private String userName;
	private String status;
	private Set<DalRole> roles = new HashSet<DalRole>();

	public DalUser() {

	}	

	public DalUser(int id) {
		setId(id);
	}
	
	 public DalUser(DalUser u ) {
	        super(u);
	        this.userName = u.userName;
	        this.status = u.status;
	        this.roles.addAll(u.roles);	      
	    }

	

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

	 public void setRoles(Set<DalRole> roles) {
	        this.roles = roles;
	    }

	

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<DalRole> getRoles() {
        return roles;
    }



}
