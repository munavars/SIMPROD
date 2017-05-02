package com.ytc.test.service;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ytc.common.model.Customer;
import com.ytc.common.model.CustomerDetail;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.service.ICustomerService;

public class CustomerImplTest {


	private static ICustomerService customerService;


	private static IDataAccessLayer baseDao;



	protected static String rand = CommonBase.generateRandomAscii(10);

	private static Logger logger = Logger.getLogger(CustomerImplTest.class);

	public CustomerImplTest() {

	}


	@BeforeClass
	public static void setup() throws Exception {
		ClassPathXmlApplicationContext context = CommonBase.createContext(rand, CustomerImplTest.class, "ytc-unit-test.properties", "ytc-service-context-hsqldb.xml", new String[] {});
		customerService = (ICustomerService) context.getBean("customerService");
		baseDao = (IDataAccessLayer) context.getBean("baseDao");

	}



	@Test
	public void testDummy() {
		
	}
	
	@Test
	public void testGetCustomer(){
		CustomerDetail returnCustomer = customerService.getCustomerDetail(23528);
		Assert.assertNotNull(returnCustomer.getId());
		
	}
}
