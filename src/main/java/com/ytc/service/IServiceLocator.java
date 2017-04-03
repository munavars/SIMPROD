package com.ytc.service;

public interface IServiceLocator {
  
	ICustomerService getCustomerService();
	IPaidBasedOnService getPaidBasedOnService();
	ISecurityService getSecurityService();

}
