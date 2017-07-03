package com.ytc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiredex.yoko.utils.LdapUtil;
import com.ytc.common.model.Employee;
import com.ytc.common.model.EmployeeHierarchy;
import com.ytc.common.result.ListResult;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.model.DalEmployee;
import com.ytc.service.IEmployeeService;
import com.ytc.service.ServiceContext;

@Controller
// @RequestMapping(value = "/")
public class UserController extends BaseController {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private ServiceContext serviceContext;
	
	@RequestMapping(value = "/getEmployee/{loginId}", method = RequestMethod.GET)
	public @ResponseBody ListResult<Employee> getDetail(HttpServletRequest request, @PathVariable String loginId) {
		logger.info("UserController /getEmployee/{loginId}");
		return new ListResult<Employee>(getService(request).getDetail(loginId));
	}

	@RequestMapping(value = "/getEmployeeHierarchy/{empId}", method = RequestMethod.GET)
	public @ResponseBody List<String> getEmployeeHierarchy(HttpServletRequest request, @PathVariable Integer empId) {
		logger.info("UserController /getEmployeeHierarchy/{empId}"+empId);
		List<String> aList = getHierarchyQueryResult(request, empId);
		logger.info("UserController getHiearchyQueryResult: ["+aList+"]");
		return aList;
	}
	
	@RequestMapping(value = "/getEmployeeDetail/{empId}", method = RequestMethod.GET)
	public @ResponseBody DalEmployee getEmployeeDetail(HttpServletRequest request, @PathVariable Integer empId) {
		logger.info("UserController /getEmployeeDetail/{empId}"+empId);
		
		return (getService(request).getEmployeeDetail(empId));
		//return aList;
	}
	
	
	@RequestMapping(value = "/isTbpUser", method = RequestMethod.GET)
	public @ResponseBody boolean isTbpUser(HttpServletRequest request) {
				
		return (getService(request).isTbpUser(Integer.parseInt(request.getSession().getAttribute("EMPLOYEE_INFO").toString())));
	}

//	@RequestMapping(value = "/getCustomerNumber/{empId}", method = RequestMethod.GET)
//	public @ResponseBody List getCustomerNumber(HttpServletRequest request, @PathVariable Integer empId) {
//		logger.info("UserController /getCustomerNumber/{empId}"+empId);
//		List aList = getCustomerNumberByAccountManager(request, empId.toString());
//		logger.info("UserController getCustomerNumber: ["+aList+"]");
//		return aList;
//	}

	@RequestMapping(value = { "/", "/login" })
	public String presentHomePage() {
		logger.info("UserController.presentHomePage");
		return "login";
	}
	
	@RequestMapping(value = { "/logout" })
	public String logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("UserController.presentHomePage");
		request.getSession().removeAttribute("EMPLOYEE_INFO");
		request.getSession().removeAttribute("EMPLOYEE_HIERARCHY");
		if(serviceContext != null){
			serviceContext.setEmployee(null);
		}
		request.getSession().invalidate();
        return "login";
	}
	
	@RequestMapping(value = { "/browserclose" })
	public void browserClose(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("UserController.presentHomePage");
		request.getSession().removeAttribute("EMPLOYEE_INFO");
		request.getSession().removeAttribute("EMPLOYEE_HIERARCHY");
		if(serviceContext != null){
			serviceContext.setEmployee(null);
		}
		request.getSession().invalidate();
	}

	@RequestMapping(value = { "/customerprogram" })
	public String presentCustomerProgramPage() {
		logger.info("UserController.presentHomePage");
		return "customer_program";
	}
	
	/**
	 * This method is added for redirecting to Index page after proper login.
	 * Example. In case of pricing form navigation, when cancel button is clicked, then index page has to be redirected.
	 * This method is used for that functionality.
	 * @return String.
	 */
	@RequestMapping(value = { "/indexRedirect" })
	public String indexNavigationAfterLogin(HttpServletRequest request, Model model) {
		logger.info("UserController.presentHomePage");
		String returnModel = null;
		if(serviceContext == null || serviceContext.getEmployee() == null){
			returnModel = "login";
		}
		else{
			List<Employee> employeeList = null;
			String userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			model.addAttribute("loginUserNameValue", userName);
			employeeList = new ArrayList<Employee>();
			employeeList.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employeeList);
			returnModel = "index";
		}
		return returnModel;
	}
	
	@RequestMapping(value = "processLogin", method = { RequestMethod.GET } )
	public String navigateToLoginPageOrDashboard(HttpServletRequest request, Model model) {
		String userName = null;
		String returnModel = null;
		if(request != null){
			String theUserid = request.getParameter("userid");
			if(theUserid != null && !theUserid.isEmpty()){
				return "login";
			}
		}
		if(serviceContext != null && serviceContext.getEmployee() != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			model.addAttribute("loginUserNameValue", userName);
			List<Employee> employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
			returnModel = "index";
		}
		if(serviceContext == null || userName == null){
			returnModel = "login";
		}
		
		return returnModel;
	}

	@RequestMapping(value = "processLogin", method = { RequestMethod.POST } )
	// public String validateLoginPage() {
	public String validateLoginPage(HttpServletRequest request, Model model) {

		logger.info("UserController.processLogin");
		List<Employee> employee = null;
		if((serviceContext.getEmployee() != null)&&("1".equalsIgnoreCase(serviceContext.getEmployee().getACTIVE()))){
			String userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			model.addAttribute("loginUserNameValue", userName);
			employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
			return "index";
		}
		
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
				request.getSession().setAttribute("EMPLOYEE_INFO", employee.get(0).getEMP_ID());
				logger.info("UserController.processLogin, employee employee.get(0).getEMP_ID(): "+employee.get(0).getEMP_ID());
				logger.info("UserController.processLogin, employee employee.get(0).getId(): "+employee.get(0).getId());
				logger.info("UserController.processLogin employee: "+employee.toString());
				model.addAttribute("EmployeeInfo", employee);
				if(serviceContext != null && employee.get(0) != null){
					serviceContext.setEmployee(employee.get(0));
					String userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
					model.addAttribute("loginUserNameValue", userName);
				}

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
						destination = "index";
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
	
	public List<String> getHierarchyQueryResult(HttpServletRequest request,Integer empId) {
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
