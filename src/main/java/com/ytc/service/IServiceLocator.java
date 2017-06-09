package com.ytc.service;

public interface IServiceLocator {
  
	ICustomerService getCustomerService();
	IPaidBasedOnService getPaidBasedOnService();
	ISecurityService getSecurityService();
	IEmployeeService getEmployeeService();

	IProgramService getProgramService();
	IPricingService getPricingService();
	IProgramUpdateService getProgramPersistService();
	IPricingUpdateService getPricingPersistService();
}
