package com.ytc.common.model;

import java.io.Serializable;

public abstract class Model implements Cloneable, Serializable {
	private static final long serialVersionUID = 2514719982327593095L;

	private String id;

	protected Model() {
	}

	protected Model(String id) {
		this.id = id;
	}

	protected Model(Model m) {
		id = m.id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Model)) {
			// Nothing or not a model -- can't be equal to us
			return false;
		}
		return (id != null) ? id.equals(((Model) obj).id) : super.equals(obj);
	}

	@Override
	public int hashCode() {
		return (id != null) ? id.hashCode() : super.hashCode();
	}



	@Override
	public Model clone() {
		try {
			return (Model) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

}
