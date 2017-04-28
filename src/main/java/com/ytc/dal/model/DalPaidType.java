package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PAID_TYPE")
@NamedQueries({
	@NamedQuery(name="DalPaidType.getAllDetails", query = "select o from DalPaidType o order by o.type")
})
public class DalPaidType extends DalModel{
	
	@Column(name="TYPE")
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
