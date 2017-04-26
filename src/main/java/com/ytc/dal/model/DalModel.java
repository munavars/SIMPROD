package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@MappedSuperclass
public abstract class DalModel implements Cloneable {



	private Integer id;

	protected DalModel() {
	}

	protected DalModel(DalModel m) {

	}


	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "YtcIdGenerator")
	@GenericGenerator(name = "YtcIdGenerator", strategy = "com.ytc.dal.jpa.YtcIdGenerator", parameters = { @Parameter(name = "strategy", value = "uuid") })
	public Integer getId() { 
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


}
