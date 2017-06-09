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
@Table(name = "PRICING_TERM_CODES")
@NamedQueries({
	@NamedQuery(name="DalPricingTermCodes.getCode", query = "select o from DalPricingTermCodes o where o.code=:code"),
	@NamedQuery(name="DalPricingTermCodes.getTermCodes", query = "select DISTINCT(code) from DalPricingTermCodes"),
	@NamedQuery(name="DalPricingTermCodes.getDefnsDue", query = "select DISTINCT(code),definition,dueDates,cash from DalPricingTermCodes")
})
public class DalPricingTermCodes extends DalAuditableModel {
	private static final long serialVersionUID = 1L;

	private String code;
	
	private String definition;
	
	private String dueDates;
	
	private String cash;
	/**
	 * @return the code
	 */
	@Column(name = "CODE")
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the definition
	 */
	@Column(name = "DEFINITION")
	public String getDefinition() {
		return definition;
	}
	/**
	 * @param definition the definition to set
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	/**
	 * @return the dueDates
	 */
	@Column(name = "DUE_DATES")
	public String getDueDates() {
		return dueDates;
	}
	/**
	 * @param dueDates the dueDates to set
	 */
	public void setDueDates(String dueDates) {
		this.dueDates = dueDates;
	}
	/**
	 * @return the cash
	 */
	@Column(name = "CASH")
	public String getCash() {
		return cash;
	}
	/**
	 * @param cash the cash to set
	 */
	public void setCash(String cash) {
		this.cash = cash;
	}
}

