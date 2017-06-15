/**
 * 
 */
package com.ytc.dal.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRICING_HEADER")
public class DalPricingHeader extends DalAuditableModel{

	
	/**
	 * Default serial version. 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer customerId;
	private DalPricingCustomerType customerType;
	private DalPricingTermCodes termCodes;
	private DalPricingShipRequirements shippingReqs;
	private DalPricingOtherShipRequirements otherShippingreqs;
	private String userComments;
	
	private List<DalPricingDetail> dalPricingDetailList;
	
	/**
	 * @return the customerId
	 */
	@Column(name = "CUSTOMER_ID")
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the customerType
	 */
	@OneToOne
	@JoinColumn(name = "CUSTOMER_TYPE_ID", referencedColumnName ="ID")
	public DalPricingCustomerType getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(DalPricingCustomerType customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return the termCodes
	 */
	@OneToOne
	@JoinColumn(name = "PROGRAM_TERMS_ID", referencedColumnName ="ID")
	public DalPricingTermCodes getTermCodes() {
		return termCodes;
	}
	/**
	 * @param termCodes the termCodes to set
	 */
	public void setTermCodes(DalPricingTermCodes termCodes) {
		this.termCodes = termCodes;
	}
	/**
	 * @return the shippingReqs
	 */
	@OneToOne
	@JoinColumn(name = "SHIPPING_REQS_ID", referencedColumnName ="ID")
	public DalPricingShipRequirements getShippingReqs() {
		return shippingReqs;
	}
	/**
	 * @param shippingReqs the shippingReqs to set
	 */
	public void setShippingReqs(DalPricingShipRequirements shippingReqs) {
		this.shippingReqs = shippingReqs;
	}
	/**
	 * @return the otherShippingreqs
	 */
	@OneToOne
	@JoinColumn(name = "OTHER_SHIPPING_REQS_ID", referencedColumnName ="ID")
	public DalPricingOtherShipRequirements getOtherShippingreqs() {
		return otherShippingreqs;
	}
	/**
	 * @param otherShippingreqs the otherShippingreqs to set
	 */
	public void setOtherShippingreqs(DalPricingOtherShipRequirements otherShippingreqs) {
		this.otherShippingreqs = otherShippingreqs;
	}
	/**
	 * @return the userComments
	 */
	   @Column(name = "USER_COMMENTS")
	public String getUserComments() {
		return userComments;
	}
	/**
	 * @param userComments the userComments to set
	 */
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}
	/**
	 * @return the dalPricingDetailList
	 */
	@OneToMany(mappedBy = "dalPricingHeader", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	
	public List<DalPricingDetail> getDalPricingDetailList() {
		return dalPricingDetailList;
	}
	/**
	 * @param dalPricingDetailList the dalPricingDetailList to set
	 */
	public void setDalPricingDetailList(List<DalPricingDetail> dalPricingDetailList) {
		this.dalPricingDetailList = dalPricingDetailList;
	}
}
