package com.ytc.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.Customer;
import com.ytc.common.model.CustomerDetail;
import com.ytc.common.model.EmployeeHierarchy;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalCustomer;
import com.ytc.dal.model.DalEmployeeHierarchy;
import com.ytc.service.ICustomerService;

public class CustomerService implements ICustomerService {

	@Autowired
	private IDataAccessLayer baseDao;



	public CustomerService() {

	}


	@Override
	public CustomerDetail getCustomerDetail(Integer customerId) {
		DalCustomer dalCustomer = baseDao.getById(DalCustomer.class, customerId);
		ModelMapper modelMapper = new ModelMapper();
		CustomerDetail customer = modelMapper.map(dalCustomer, CustomerDetail.class);
		DalEmployeeHierarchy dalEmployeeHierarchy = baseDao.getEntityById(DalEmployeeHierarchy.class, Integer.parseInt(dalCustomer.getAccountManager()));
		EmployeeHierarchy employeeHierarchy=modelMapper.map(dalEmployeeHierarchy,EmployeeHierarchy.class);
		customer.setEmployeeHierarchy(employeeHierarchy);
		return customer;

	}
	
	@Override
	public Customer getDetail(Integer customerId) {
		DalCustomer dalCustomer = baseDao.getById(DalCustomer.class, customerId);
		ModelMapper modelMapper = new ModelMapper();
		Customer customer = modelMapper.map(dalCustomer, Customer.class);
		return customer;

	}
}


