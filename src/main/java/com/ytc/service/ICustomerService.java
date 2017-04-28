/**
 * 
 */
package com.ytc.service;


import com.ytc.common.model.Customer;
import com.ytc.common.model.CustomerDetail;

/**
 * @author 164919
 *
 */
public interface ICustomerService {
	CustomerDetail getCustomerDetail(Integer customerId);

	Customer getDetail(Integer customerId);
}
