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
	
}
