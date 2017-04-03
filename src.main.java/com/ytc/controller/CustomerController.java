package com.ytc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytc.common.model.Customer;
import com.ytc.common.result.ModelResult;
import com.ytc.service.ICustomerService;




@Controller
@RequestMapping(value = "/v1/customer")
public class CustomerController extends BaseController  {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody ModelResult<Customer> getDetail(HttpServletRequest request, @PathVariable String id) {
		return new ModelResult<Customer>(getService(request).getDetail(id));
	}
	

	private ICustomerService getService(HttpServletRequest request) {
		return getServiceLocator(request).getCustomerService();
	}

}
