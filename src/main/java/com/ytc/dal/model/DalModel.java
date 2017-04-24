package com.ytc.dal.model;

import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@javax.persistence.Access(AccessType.PROPERTY)
public abstract class DalModel implements Cloneable {



	private Integer id;

	protected DalModel() {
	}

	protected DalModel(DalModel m) {
		
	}
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
/*	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

		
}
