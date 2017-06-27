package com.ytc.service;

import com.ytc.common.model.PricingHeader;
import com.ytc.dal.model.DalPricingHeader;

public interface IPricingEmailService {
	/**
	 * Method to send email while program details submit, approve or reject.
	 * @param pricingHeader pricingHeader
	 * @param dalPricingHeader  dalPricingHeader
	 */ 
	void sendEmailData(PricingHeader pricingHeader, DalPricingHeader dalPricingHeader);
}
