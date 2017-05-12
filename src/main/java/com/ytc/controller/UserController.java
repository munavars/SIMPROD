package com.ytc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiredex.yoko.utils.LdapUtil;
import com.ytc.common.model.Employee;
import com.ytc.common.model.ProgramDetail;
import com.ytc.common.result.ListResult;
import com.ytc.common.result.ModelResult;
import com.ytc.service.IEmployeeService;

@Controller
// @RequestMapping(value = "/")
public class UserController extends BaseController {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/getEmployee/{loginId}", method = RequestMethod.GET)
	public @ResponseBody ListResult<Employee> getDetail(HttpServletRequest request, @PathVariable String loginId) {
		logger.info("UserController /getEmployee/{loginId}");
		return new ListResult<Employee>(getService(request).getDetail(loginId));
	}

	@RequestMapping(value = "/getEmployeeHierarchy/{empId}", method = RequestMethod.GET)
	public @ResponseBody List getEmployeeHierarchy(HttpServletRequest request, @PathVariable Integer empId) {
		logger.info("UserController /getEmployeeHierarchy/{empId}"+empId);
		List aList = getHierarchyQueryResult(request, empId.toString());
		logger.info("UserController getHiearchyQueryResult: ["+aList+"]");
		return aList;
	}

//	@RequestMapping(value = "/getCustomerNumber/{empId}", method = RequestMethod.GET)
//	public @ResponseBody List getCustomerNumber(HttpServletRequest request, @PathVariable Integer empId) {
//		logger.info("UserController /getCustomerNumber/{empId}"+empId);
//		List aList = getCustomerNumberByAccountManager(request, empId.toString());
//		logger.info("UserController getCustomerNumber: ["+aList+"]");
//		return aList;
//	}

	@RequestMapping(value = { "/programDetail" })
	public String gotoProgramDetail() {
		logger.info("UserController.ProgramDetailsPage");
		return "program_details2";
	}
	
	@RequestMapping(value = { "/", "/login" })
	public String presentHomePage() {
		logger.info("UserController.presentHomePage");
		return "login";
	}

	@RequestMapping(value = { "/customerprogram" })
	public String presentCustomerProgramPage() {
		logger.info("UserController.presentHomePage");
		return "customer_program";
	}

	@RequestMapping(value = "processLogin", method = { RequestMethod.GET, RequestMethod.POST })
	// public String validateLoginPage() {
	public String validateLoginPage(HttpServletRequest request, Model model) {

		logger.info("UserController.processLogin");
		List<Employee> employee = null;
		String destination = "loginError";
		String theUserid = request.getParameter("userid");
		logger.info("UserController.processLogin userid: " + theUserid);
		String thePassword = request.getParameter("userpassword");
		boolean isValidUser = false;
		boolean isTestUser =  thePassword.isEmpty();
		//logger.info("UserController.processLogin password: " + thePassword);

		try {

//		validate user via LDAP
		isValidUser = isValidLDAPLogin(theUserid, thePassword);

		//logger.info("UserController.processLogin LDAPUtil.isValidYtcUser user: " + theUserid + " password: "
			//	+ thePassword + ", isValidUser = " + isValidUser);
		
		if (isValidUser) {
						
//			valid user
//			Employee employeeInfo = null;
//				Remove domain info from the userid input
				String loginID = theUserid.substring(theUserid.indexOf("\\")+1);
				logger.info("UserController.processLogin loginID: "+loginID);
				
//              get all data from employee table that matches the loginID
				employee = getService(request).getDetail(loginID);
				request.getSession().setAttribute("EMPLOYEE_INFO", employee.get(0).getId());
				logger.info("UserController.processLogin, employee employee.get(0).getEMP_ID(): "+employee.get(0).getEMP_ID());
				logger.info("UserController.processLogin, employee employee.get(0).getId(): "+employee.get(0).getId());
				logger.info("UserController.processLogin employee: "+employee.toString());
				model.addAttribute("EmployeeInfo", employee);

//				get employee hierarchy that matches the employee id
				List<String> list = getHierarchyQueryResult(request, employee.get(0).getEMP_ID());
				logger.info("UserController.processLogin getEmployeeHierarchy: "+list.toString());
				request.getSession().setAttribute("EMPLOYEE_HIERARCHY", employee);
				model.addAttribute("EmployeeHierarchyInfo", list);
			
		} else {
			model.addAttribute("LoginErrorMessage",
					"*** Your id " + theUserid + " or your password is not valid. Please check and try again.");
			request.getSession().setAttribute("LoginErrorMessage", "*** Your id " + theUserid + " or your password is not valid. Please check and try again.");
		}

		} catch(Exception e) {
			logger.info("Error occured while retrieving employee info: "+e);
		}
		finally {
			try {
			if (employee == null) { 
				logger.info("UserController.processLogin finally, employee is null");
				model.addAttribute("ErrorMessage", "Login failed");
			} else {
				if (employee.size() > 0) {
					logger.info("UserController.processLogin finally, employee is not null");
					String activeStatus = employee.get(0).getACTIVE();
					logger.info("UserController.processLogin finally, employee activeStatus: "+activeStatus);
					if (activeStatus.equalsIgnoreCase("1")) {
						destination = "redirect:http://caecodev03:9081/SIMS/dashboard?accMgrID="+employee.get(0).getEMP_ID();
					}
				}
			} 
			} catch (Exception e) {
				logger.info("Error occured while retrieving employee info: "+e);
			}
		}			
		 return destination;
	}


	boolean isValidLDAPLogin(String theUserid, String thePassword) {

		logger.info("UserController.isValidLDAPLogin userid: " + theUserid);
		logger.info("UserController.isValidLDAPLogin password: " + thePassword);

		LdapUtil ldapUtil = new LdapUtil();
		boolean isValidUser = ldapUtil.isValidYtcUser(theUserid, thePassword);

		logger.info("UserController.isValidLDAPLogin isValidUser = " + isValidUser);

		return isValidUser;
	}

//	public List<String> getCustomerNumberByAccountManager(HttpServletRequest request, String empId) {
//		List<String> resultList = null;
//		String queryString = "SELECT CUSTOMER_NUMBER FROM CUSTOMER where ACCOUNT_MANAGER = '"+ empId+"'";
//		resultList = getService(request).getQueryResult(queryString);
//		return resultList;
//	}
	
	public List<String> getHierarchyQueryResult(HttpServletRequest request, String empId) {
		List<String> resultList = null;
		String queryString = "SELECT * FROM EMPLOYEE_HIERARCHY where BASE_EMP_ID = '"+ empId+"' or LVL1_EMP_ID = '"+empId+"' or LVL2_EMP_ID = '"+empId+
				"' or LVL3_EMP_ID = '"+empId+"' or LVL4_EMP_ID = '"+empId+"' or LVL5_EMP_ID = '"+empId+"'";
		resultList = getService(request).getQueryResult(queryString);
		return resultList;
	}
	/*public Employee getEmployeeDetail(String loginId) {
		logger.info("UserController.getEmployeeDetail loginId: "+loginId);

		EmployeeService employeeService = new EmployeeService();
		
		Employee employeeInfo = employeeService.getDetail(loginId);
		
		return employeeInfo;
	}*/
	
	IEmployeeService getService(HttpServletRequest request) {
		return getServiceLocator(request).getEmployeeService();
	}


}
