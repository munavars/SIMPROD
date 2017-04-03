/**
 * 
 */
package com.ytc.service;


import com.ytc.common.model.Customer;

/**
 * @author 164919
 *
 */
public interface ICustomerService {
	Customer getDetail(String customerId);
}
