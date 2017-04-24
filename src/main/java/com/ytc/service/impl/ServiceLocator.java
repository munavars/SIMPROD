package com.ytc.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ytc.service.ICustomerService;
import com.ytc.service.IEmployeeService;
import com.ytc.service.IPaidBasedOnService;
import com.ytc.service.IProgramUpdateService;
import com.ytc.service.IProgramService;
import com.ytc.service.ISecurityService;
import com.ytc.service.IServiceLocator;



public class ServiceLocator implements IServiceLocator, ApplicationContextAware
{
    //private static final Logger logger = LoggerFactory.getLogger(ServiceLocator.class);

    ApplicationContext appContext;
    
    private ICustomerService customerService;
    
    private IPaidBasedOnService paidBasedOnService;
    
    private ISecurityService securityService;
    
    private IEmployeeService employeeService;

    private IProgramService programService;
    
    private IProgramUpdateService programPersistService;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;

    }


	 @Override
	    public ICustomerService getCustomerService() {
	        if (customerService == null) {
	        	customerService = (ICustomerService) appContext.getBean("customerService");
	        }
	        return customerService;
	    }

	 @Override
	    public IPaidBasedOnService getPaidBasedOnService() {
	        if (paidBasedOnService == null) {
	        	paidBasedOnService = (IPaidBasedOnService) appContext.getBean("paidBasedOnService");
	        }
	        return paidBasedOnService;
	    }
	 
	 @Override
	    public ISecurityService getSecurityService() {
	        if (securityService == null) {
	        	securityService = (ISecurityService) appContext.getBean("securityService");
	        }
	        return securityService;
	    }
	 
	 @Override
	    public IEmployeeService getEmployeeService() {
	        if (employeeService == null) {
	        	employeeService = (IEmployeeService) appContext.getBean("employeeService");
	        }
	        return employeeService;
	    }
	 
	@Override
	public IProgramService getProgramService() {
        if (programService == null) {
        	programService = (IProgramService) appContext.getBean("programService");
        }
        return programService;
	}
	
	@Override
	public IProgramUpdateService getProgramPersistService() {
        if (programPersistService == null) {
        	programPersistService = (IProgramUpdateService) appContext.getBean("programUpdateService");
        }
        return programPersistService;
	}
}
