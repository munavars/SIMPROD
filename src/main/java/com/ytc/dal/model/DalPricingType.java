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
	@NamedQuery(name="DalPricingType.getAllDetails", query = "select o from DalPricingType o order by o.type")
})
public class DalPricingType extends DalModel{

	private String type;

	@Column(name="TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
