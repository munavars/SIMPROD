package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PAID_TYPE")
@NamedQueries({
	@NamedQuery(name="DalPaidType.getAllDetails", query = "select o from DalPaidType o order by o.type")
})
public class DalPaidType extends DalModel{
	
	/**
	 * Serial Version default.
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="TYPE")
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
