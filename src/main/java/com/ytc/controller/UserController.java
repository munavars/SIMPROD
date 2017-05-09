package com.ytc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiredex.yoko.utils.LdapUtil;
import com.ytc.common.model.Employee;
import com.ytc.common.result.ModelResult;
import com.ytc.service.IEmployeeService;

@Controller
// @RequestMapping(value = "/")
public class UserController extends BaseController {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/getEmployee/{loginId}", method = RequestMethod.GET)
	public @ResponseBody ModelResult<Employee> getDetail(HttpServletRequest request, @PathVariable String loginId) {
		logger.info("UserController /getEmployee/{loginId}");
		return new ModelResult<Employee>(getService(request).getDetail(loginId));
	}

	@RequestMapping(value = "/getEmployeeHierarchy/{empId}", method = RequestMethod.GET)
	public @ResponseBody List getEmployeeHierarchy(HttpServletRequest request, @PathVariable Integer empId) {
		logger.info("UserController /getEmployeeHierarchy/{empId}");
		List aList = getHierarchyQueryResult(request, empId);
		logger.info("UserController getHiearchyQueryResult: ["+aList+"]");
		return aList;
	}

	@RequestMapping(value = { "/", "/programDetail" })
	public String gotoProgramDetail() {
		logger.info("UserController.ProgramDetailsPage");
		return "program_details2";
	}
	
	@RequestMapping(value = { "/", "/login" })
	public String presentHomePage() {
		logger.info("UserController.presentHomePage");
		return "login";
	}

	@RequestMapping(value = "processLogin", method = RequestMethod.GET)
	// public String validateLoginPage() {
	public String validateLoginPage(HttpServletRequest request, Model model) {


		logger.info("UserController.processLogin");
		String theUserid = request.getParameter("userid");
		logger.info("UserController.processLogin userid: " + theUserid);
		String thePassword = request.getParameter("userpassword");
		//logger.info("UserController.processLogin password: " + thePassword);

//		validate user via LDAP
		boolean isValidUser = isValidLDAPLogin(theUserid, thePassword);

		//logger.info("UserController.processLogin LDAPUtil.isValidYtcUser user: " + theUserid + " password: "
			//	+ thePassword + ", isValidUser = " + isValidUser);
		
		if (isValidUser) {
						
//			valid user
			Employee employeeInfo = null;
			try {
//				Remove domain info from the userid input
				String loginID = theUserid.substring(theUserid.indexOf("\\")+1);
				logger.info("UserController.processLogin loginID: "+loginID);
				
//              get all data from employee table that matches the loginID
				Employee employee = getService(request).getDetail(loginID);
				request.getSession().setAttribute("EMPLOYEE_INFO", employee);
				logger.info("UserController.processLogin employee: "+employee.toString());

//				get employee hierarchy that matches the employee id
				List<String> list = getHierarchyQueryResult(request, employee.getId());
				logger.info("UserController.processLogin getEmployeeHierarchy: "+list.toString());
				request.getSession().setAttribute("EMPLOYEE_HIERARCHY", employee);
			} catch(Exception e) {
				logger.info("Error occured while retrieving employee info: "+e);
			}
			return "index";
		} else {
			model.addAttribute("LoginErrorMessage",
					"*** Your id " + theUserid + " or your password is not valid. Please check and try again.");
			request.getSession().setAttribute("LoginErrorMessage", "*** Your id " + theUserid + " or your password is not valid. Please check and try again.");
			return "login";
		}

		// return "index";
	}


	boolean isValidLDAPLogin(String theUserid, String thePassword) {

		logger.info("UserController.isValidLDAPLogin userid: " + theUserid);
		logger.info("UserController.isValidLDAPLogin password: " + thePassword);

		LdapUtil ldapUtil = new LdapUtil();
		boolean isValidUser = ldapUtil.isValidYtcUser(theUserid, thePassword);

		logger.info("UserController.isValidLDAPLogin isValidUser = " + isValidUser);

		return isValidUser;
	}
	
	public List<String> getHierarchyQueryResult(HttpServletRequest request, Integer empId) {
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
