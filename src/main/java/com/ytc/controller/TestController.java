package com.ytc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ytc.common.model.Employee;
import com.ytc.constant.ProgramConstant;
import com.ytc.service.IAccrualDataService;
import com.ytc.service.ServiceContext;
@Controller
@RequestMapping("/")
public class TestController  extends BaseController {

	@Autowired
	private ServiceContext serviceContext;

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request, Model model) {
		//Need to call service implementation here
		String userName = null;
		String returnModel = null;
		List<Employee> employee = null;
		if(serviceContext != null && serviceContext.getEmployee() != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			model.addAttribute("loginUserNameValue", userName);
			employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
			returnModel= "customer_details_dealertire_latest";
		}
		if(serviceContext == null || userName == null){
			returnModel = "login";
		}
		return returnModel;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		//Need to call service implementation here
		String userName = null;
		String returnModel = null;
		List<Employee> employee = null;
		if(serviceContext != null && serviceContext.getEmployee() != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			model.addAttribute("loginUserNameValue", userName);
			employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
			returnModel= "index";
		}
		if(serviceContext == null || userName == null){
			returnModel = "login";
		}
		return returnModel;
	}

	@RequestMapping(value = "/programddfcoop", method = RequestMethod.GET)
	public String gotoProgramDetailDDF(HttpServletRequest request, Model model) {
		String userName = null;
		String returnModel = null;
		List<Employee> employee = null;
		if(serviceContext != null && serviceContext.getEmployee() != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			returnModel = "program_details_coop_ddf";
			model.addAttribute("loginUserNameValue", userName);
			employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
		}

		if(serviceContext == null || userName == null){
			returnModel = "login";
		}

		return returnModel;
	}


	@RequestMapping(value = { "/", "/programDetail" })
	public String gotoProgramDetail(HttpServletRequest request, Model model) {
		String userName =  null;
		String returnModel = null;
		List<Employee> employee = null;
		if(serviceContext != null && serviceContext.getEmployee() != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			returnModel = "program_details2";
			model.addAttribute("loginUserNameValue", userName);
			employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
		}

		if(serviceContext == null || userName == null){
			returnModel = "login";
		}

		return returnModel;
	}

	@RequestMapping(value = "/pricing", method = RequestMethod.GET)
	public String pricingForm(HttpServletRequest request, Model model) {
		String returnModel = null;
		returnModel ="pricing_request";
		return returnModel;

	}

	@RequestMapping(value = "/ccm", method = RequestMethod.GET)
	public String ccm(HttpServletRequest request, Model model) {
		String userName =  null;
		String returnModel = null;
		List<Employee> employee = null;
		if(serviceContext != null && serviceContext.getEmployee() != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			returnModel = "ccm";
			model.addAttribute("loginUserNameValue", userName);
			employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
		}

		if(serviceContext == null || userName == null){
			returnModel = "login";
		}

		return returnModel;

	}

	@RequestMapping(value = "/accrual", method = RequestMethod.GET)
	public String calculateAccrual(HttpServletRequest request, Model model) {
		String userName =  null;
		String returnModel = null;
		List<Employee> employee = null;
		if(serviceContext != null && serviceContext.getEmployee() != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			returnModel = "accrual_calculation";
			model.addAttribute("loginUserNameValue", userName);
			employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
		}

		if(serviceContext == null || userName == null){
			returnModel = "login";
		}

		return returnModel;


	}

	@RequestMapping(value = "/booklist", method = RequestMethod.GET)
	public String getBookList(HttpServletRequest request, Model model) {
		String userName =  null;
		String returnModel = null;
		List<Employee> employee = null;
		if(serviceContext != null && serviceContext.getEmployee() != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			returnModel = "book_list";
			model.addAttribute("loginUserNameValue", userName);
			employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
		}

		if(serviceContext == null || userName == null){
			returnModel = "login";
		}

		return returnModel;

	}

	

	private IAccrualDataService getService(HttpServletRequest request) {
		return getServiceLocator(request).getAccrualDataService();

	}


}

