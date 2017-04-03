package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 * Base POJO class for all objects that can be persisted (both top-level objects and child objects).
 */

@MappedSuperclass
public abstract class DalModel implements Cloneable {
	/*private String id;

	protected DalModel() {
	}

	protected DalModel(DalModel m) {
		
	}

	//@Id
	//@Column(name = "ID")
	@Size(max = 36)
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@GeneratedValue(generator = "AmpIdGenerator")
	//@GenericGenerator(name = "AmpIdGenerator", strategy = "com.ympact.amp.dal.jpa.AmpIdGenerator", parameters = { @Parameter(name = "strategy", value = "uuid") })
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null && id.length() == 0) {
			id = null;
		}
		this.id = id;
	}

	@Transient
	public boolean isPersisted() {
		return id != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof DalModel)) {
			// Nothing or not a model -- can't be equal to us
			return false;
		}
		return (id != null) ? id.equals(((DalModel) obj).id) : super.equals(obj);
	}

	@Override
	public int hashCode() {
		return (id != null) ? id.hashCode() : super.hashCode();
	}

	@Override
	public String toString() {
		// FIXME: enum properties and print them in string (
		return super.toString();
	}

	@Override
	public DalModel clone() {
		try {
			return (DalModel) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	*//**
	 * Override to load fields at one place for each subclass
	 *//*
	public void initFields(){

	}*/
}
