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
@Table(name = "PRICING_SHIPPING_REQS")
@NamedQueries({
	@NamedQuery(name="DalPricingShipRequirements.getShipRqs", query = "select o from DalPricingShipRequirements o where o.shipRqs=:shipRqs")
})
public class DalPricingShipRequirements extends DalAuditableModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String shipRqs;
	/**
	 * @return the shipRqs
	 */
	@Column(name = "REQUIREMENT")
	public String getShipRqs() {
		return shipRqs;
	}
	/**
	 * @param shipRqs the shipRqs to set
	 */
	public void setShipRqs(String shipRqs) {
		this.shipRqs = shipRqs;
	}
}
