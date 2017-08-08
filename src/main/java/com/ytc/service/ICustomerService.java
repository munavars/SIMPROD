/**
 * 
 */
package com.ytc.service;


import java.util.List;

import com.ytc.common.model.Customer;
import com.ytc.common.model.CustomerDetail;
import com.ytc.common.model.ProgramDetail;

/**
 * Interface : ICustomerService
 * Purpose : this interface provides all the methods related to customer page. 
 * @author Cognizant.
 *
 */
public interface ICustomerService {
	CustomerDetail getCustomerDetail(Integer customerId);
	
	List<ProgramDetail> getCustomerDetailDashboard(Integer customerId);
	
	List<ProgramDetail> getPendingPricingDetails(Integer loginId);

	Customer getDetail(Integer customerId);
}
