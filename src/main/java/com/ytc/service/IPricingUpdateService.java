/**
 * 
 */
package com.ytc.service;

import com.ytc.common.model.PricingHeader;

/**
 * @author ArunP
 *
 */
public interface IPricingUpdateService {

	
	PricingHeader savePricingHeaderDetails(PricingHeader pricingHeader); 
	
	PricingHeader validatePricingInvoiceDetails(PricingHeader pricingHeader);
}
