package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

public abstract class DalModel implements Cloneable {

	
	private String id;

	protected DalModel() {
	}

	protected DalModel(DalModel m) {
		
	}

	@Id
	@Column(name = "ID")
	@Size(max = 5)
	@GeneratedValue(strategy=GenerationType.AUTO)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null && id.length() == 0) {
			id = null;
		}
		this.id = id;
	}

	
}
