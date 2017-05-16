package com.ytc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ytc.constant.ProgramConstant;
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
			if(serviceContext != null && serviceContext.getEmployee() != null){
				userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
				model.addAttribute("loginUserNameValue", userName);
			}
			return "customer_details_dealertire_latest";
		}
		
		@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
		public String index(HttpServletRequest request, Model model) {
			//Need to call service implementation here
			String userName = null;
			if(serviceContext != null && serviceContext.getEmployee() != null){
				userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
				model.addAttribute("loginUserNameValue", userName);
			}
			return "index";
		}
		
	@RequestMapping(value = "/programddfcoop", method = RequestMethod.GET)
	public String gotoProgramDetailDDF(HttpServletRequest request, Model model) {
		String userName = null;
		String returnModel = null;
		if(serviceContext != null && serviceContext.getEmployee() != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			returnModel = "program_details_coop_ddf";
			model.addAttribute("loginUserNameValue", userName);
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
		if(serviceContext != null && serviceContext.getEmployee() != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			returnModel = "program_details2";
			model.addAttribute("loginUserNameValue", userName);
		}
		
		if(serviceContext == null || userName == null){
			returnModel = "login";
		}
		
		return returnModel;
	}
	
}
