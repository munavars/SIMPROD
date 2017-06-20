/**
 * 
 */
package com.ytc.service;

import java.util.List;

import com.ytc.common.model.DropDown;
import com.ytc.common.model.NetPricing;
import com.ytc.common.model.PricingHeader;

/**
 * @author ArunP
 *
 */
public interface IPricingService {

	
	public List<DropDown> getTagValueDropDown(Integer tagId);
	public PricingHeader getPricingDetails();
	public List<NetPricing> getCustomerPricingDetails(Integer empId, String bu);
	
	/**
	 * Interface method. This method is used to get the pricing heade details based on the pricing header id passed as input.
	 * @param pricingHeaderId pricingHeaderId
	 * @return PricingHeader pricingHeader.
	 */
	PricingHeader getPricingDetail(Integer pricingHeaderId);
}
