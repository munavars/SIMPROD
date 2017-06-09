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
@Table(name = "PRICING_OTHER_SHIPPING_REQS")
@NamedQueries({
	@NamedQuery(name="DalPricingOtherShipRequirements.getOtherReqs", query = "select DISTINCT(otherReqs) from DalPricingOtherShipRequirements"),
	@NamedQuery(name="DalPricingOtherShipRequirements.getOtherReq", query = "select o from DalPricingOtherShipRequirements o where o.otherReqs=:otherReqs")
})
public class DalPricingOtherShipRequirements extends DalAuditableModel{
	private static final long serialVersionUID = 1L;
	
	private String otherReqs;

	/**
	 * @return the otherReqs
	 */
	@Column(name = "OTHER_REQUIREMENT")
	public String getOtherReqs() {
		return otherReqs;
	}

	/**
	 * @param otherReqs the otherReqs to set
	 */
	public void setOtherReqs(String otherReqs) {
		this.otherReqs = otherReqs;
	}
}
