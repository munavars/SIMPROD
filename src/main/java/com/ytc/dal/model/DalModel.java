package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DalModel implements Cloneable {



	private Integer id;

	protected DalModel() {
	}

	protected DalModel(DalModel m) {

	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() { 
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


}
