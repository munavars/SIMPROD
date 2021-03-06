package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "STATUS")
@NamedQueries({
	@NamedQuery(name="DalStatus.getAllDetails", query = "select o from DalStatus o order by o.type")
})
public class DalStatus extends DalModel {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	private String type;

	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
