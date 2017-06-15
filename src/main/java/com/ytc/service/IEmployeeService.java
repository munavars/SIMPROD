/**
 * 
 */
package com.ytc.service;


import java.util.List;

import com.ytc.common.model.Employee;
import com.ytc.common.model.User;
import com.ytc.dal.model.DalEmployee;

/**
 * @author 164919
 *
 */
public interface IEmployeeService {
	List<Employee> getDetail(String loginId);
	
	List<String> getQueryResult(String empId);

	User authenticateUIUser(String userId);

	DalEmployee getEmployeeDetail(Integer loginId);
}
