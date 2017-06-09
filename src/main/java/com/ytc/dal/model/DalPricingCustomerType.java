/**
 * 
 */
package com.ytc.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "PRICING_CUSTOMER_TYPES")
@NamedQueries({
	@NamedQuery(name="DalPricingCustomerType.getCustomerType", query = "select o from DalPricingCustomerType o where o.customerType=:customerType")
})
public class DalPricingCustomerType extends DalAuditableModel {
	private static final long serialVersionUID = 1L;

	private String customerType;

	/**
	 * @return the customerType
	 */
	
	@Column(name = "TYPE")
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
}
