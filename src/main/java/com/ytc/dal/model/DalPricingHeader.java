/**
 * 
 */
package com.ytc.dal.model;


import java.util.List;
import java.util.Set;

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
	
	private DalCustomer customer;
	private DalPricingCustomerType customerType;
	private Integer customerGroup;
	private DalPricingTermCodes termCodes;
	private DalPricingShipRequirements shippingReqs;
	private DalPricingOtherShipRequirements otherShippingreqs;
	private String userComments;
	private DalStatus dalStatus;
	private DalProgramType dalProgramType;
	
	private Set<DalPricingDetail> dalPricingDetailList;
	
	private List<DalWorkflowStatus> dalWorkflowStatusForPricingList;
	
	/**
	 * @return the customerId
	 */
	@OneToOne
	@JoinColumn(name = "CUSTOMER_ID", referencedColumnName ="ID")
	public DalCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(DalCustomer customer) {
		this.customer = customer;
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
	@OneToMany(mappedBy = "dalPricingHeader", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval = true)
	public Set<DalPricingDetail> getDalPricingDetailList() {
		return dalPricingDetailList;
	}
	/**
	 * @param dalPricingDetailList the dalPricingDetailList to set
	 */
	public void setDalPricingDetailList(Set<DalPricingDetail> dalPricingDetailList) {
		this.dalPricingDetailList = dalPricingDetailList;
	}
	
	@Override
	public void setCreatedBy(DalEmployee createdBy) {
		if(dalPricingDetailList != null && !dalPricingDetailList.isEmpty()){
			for(DalPricingDetail dalPricingDetail : dalPricingDetailList){
				dalPricingDetail.setCreatedBy(createdBy);
			}
		}
		super.setCreatedBy(createdBy);
	}
	
	@Override
	public void setModifiedBy(DalEmployee modifiedBy) {
		if(dalPricingDetailList != null && !dalPricingDetailList.isEmpty()){
			for(DalPricingDetail dalPricingDetail : dalPricingDetailList){
				dalPricingDetail.setModifiedBy(modifiedBy);
			}
		}
		if(dalWorkflowStatusForPricingList != null && !dalWorkflowStatusForPricingList.isEmpty()){
			for(DalWorkflowStatus dalWorkflowStatus : dalWorkflowStatusForPricingList){
				if(dalWorkflowStatus.getId() == null){
					/**Updating created by here is because, for already existing program detail,
					 * approver will add comments, in such case, createdBy setter will not get called.
					 * only modified by setter will be called, since the person will be same in 
					 * all the three fields, populating all these from here is the correct approach.*/
					if(dalWorkflowStatus.getCreatedBy() == null){
						dalWorkflowStatus.setCreatedBy(modifiedBy);	
					}
					if(dalWorkflowStatus.getApprover() == null){
						dalWorkflowStatus.setApprover(modifiedBy);	
					}
				}
				dalWorkflowStatus.setModifiedBy(modifiedBy);
			}
		}
		super.setModifiedBy(modifiedBy);
	}
	
	@OneToOne
	@JoinColumn(name = "STATUS", referencedColumnName ="ID")
	public DalStatus getDalStatus() {
		return dalStatus;
	}
	
	public void setDalStatus(DalStatus dalStatus) {
		this.dalStatus = dalStatus;
	}
	
	@OneToOne
	@JoinColumn(name = "PGM_TYPE_ID", referencedColumnName ="ID")
	public DalProgramType getDalProgramType() {
		return dalProgramType;
	}
	
	public void setDalProgramType(DalProgramType dalProgramType) {
		this.dalProgramType = dalProgramType;
	}
	
	@Column(name = "CUSTOMER_GROUP")
	public Integer getCustomerGroup() {
		return customerGroup;
	}
	public void setCustomerGroup(Integer customerGroup) {
		this.customerGroup = customerGroup;
	}
	
	@OneToMany(mappedBy = "dalPricingHeaderWf", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<DalWorkflowStatus> getDalWorkflowStatusForPricingList() {
		return dalWorkflowStatusForPricingList;
	}
	
	public void setDalWorkflowStatusForPricingList(List<DalWorkflowStatus> dalWorkflowStatusForPricingList) {
		this.dalWorkflowStatusForPricingList = dalWorkflowStatusForPricingList;
	}
}

