package com.ytc.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.Customer;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalCustomer;
import com.ytc.service.ICustomerService;

public class CustomerService implements ICustomerService {

	@Autowired
	private IDataAccessLayer baseDao;



	public CustomerService() {

	}


	@Override
	public Customer getDetail(Integer customerId) {
		DalCustomer dalCustomer = baseDao.getById(DalCustomer.class, customerId);
		ModelMapper modelMapper = new ModelMapper();
		Customer customer = modelMapper.map(dalCustomer, Customer.class);
		return customer;

	}
}


