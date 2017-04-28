package com.ytc.dal.model;


import java.io.Serializable;

import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@MappedSuperclass
@javax.persistence.Access(AccessType.PROPERTY)
public abstract class DalModel implements Cloneable, Serializable {

	/**
	 * default value.
	 */
	private static final long serialVersionUID = 1L;
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
