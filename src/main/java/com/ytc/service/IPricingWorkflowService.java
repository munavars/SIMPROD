package com.ytc.service;

import com.ytc.common.model.Employee;
import com.ytc.common.model.PricingHeader;
import com.ytc.dal.model.DalPricingHeader;

public interface IPricingWorkflowService {

	/**
	 * Method to update the work flow details.
	 * @param dalPricingHeader dalPricingHeader
	 * @param pricingHeader pricingHeader
	 * @param employee employee
	 */
	void updateWorkflowDetails(DalPricingHeader dalPricingHeader, PricingHeader pricingHeader, Employee employee);
	
}
