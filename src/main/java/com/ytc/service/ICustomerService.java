/**
 * 
 */
package com.ytc.service;


import java.util.List;

import com.ytc.common.model.Customer;
import com.ytc.common.model.CustomerDetail;
import com.ytc.common.model.ProgramDetail;

/**
 * @author 164919
 *
 */
public interface ICustomerService {
	CustomerDetail getCustomerDetail(Integer customerId);
	
	List<ProgramDetail> getCustomerDetailDashboard(Integer customerId);

	Customer getDetail(Integer customerId);
}
