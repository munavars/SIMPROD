package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PRICING_TYPE")
@NamedQueries({
	@NamedQuery(name="DalPricingType.getAllDetails", query = "select o from DalPricingType o")
})
public class DalPricingType {

	private Integer id;

	private String type;
	
	@Id
	@Column(name="ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
