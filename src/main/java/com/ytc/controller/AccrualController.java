/**
 * 
 */
package com.ytc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.node.TextNode;
import com.ytc.common.model.AccrualDropDown;
import com.ytc.common.model.BookList;
import com.ytc.common.result.ListResult;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.model.DalBookList;
import com.ytc.service.IAccrualDataService;
import com.ytc.service.ServiceContext;


@Controller
@RequestMapping(value = "/")
public class AccrualController extends BaseController {

	@Autowired
	private ServiceContext serviceContext;
	

		
	private IAccrualDataService getService(HttpServletRequest request) {
		return getServiceLocator(request).getAccrualDataService();

	}
	
	@RequestMapping(value = "/accrual/v1/getAccrualDropDown", method = RequestMethod.GET)
	public @ResponseBody AccrualDropDown getAccrualDropDown(HttpServletRequest request) {
		AccrualDropDown accrualDropDown = null;
		accrualDropDown = (AccrualDropDown)(getService(request).getAccrualDropDown());
		return accrualDropDown;
	}
	
	@RequestMapping(value = "/accrual/v1/getBookList", method = RequestMethod.GET)
	public @ResponseBody ListResult<BookList> getBookList(HttpServletRequest request) {
		 
		List<BookList> result=getService(request).getBookList();
		return new ListResult<BookList>(result);
		
	}
	
	@RequestMapping(value = "/accrual/v1/createBookList", method = RequestMethod.POST)
	public @ResponseBody String createBookList(HttpServletRequest request,@RequestBody DalBookList dalBookList) {
		String status="";
		String userName="";
		if(serviceContext != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
		}
		dalBookList.setCreatedUser(userName);
		status=getService(request).createBookList(dalBookList);
		return status;
	}
	
	@RequestMapping(value = "/accrual/v1/deleteBookList", method = RequestMethod.POST)
	public @ResponseBody String createBookList(HttpServletRequest request,@RequestBody List<Integer> bookIdList) {
		String status="";		
		status=getService(request).deleteBookList(bookIdList);
		return status;
	}
	
	@RequestMapping(value = "/accrual/v1/calcLiability", method = RequestMethod.POST)
	public @ResponseBody String calculateLiability(HttpServletRequest request,@RequestBody Integer periodId) {
		return getService(request).calculateLiability(periodId);
	}
	
	@RequestMapping(value = "/accrual/v1/reviewLiabilityCCM", method = RequestMethod.POST)
	public @ResponseBody String reviewedLiabilityCCM(HttpServletRequest request,@RequestBody Integer periodId) {
		return getService(request).reviewedLiabilityCCM(periodId);
	}
	
	@RequestMapping(value = "/accrual/v1/reviewLiabilityBook", method = RequestMethod.POST)
	public @ResponseBody String reviewedLiabilityBook(HttpServletRequest request,@RequestBody TextNode bookLabel) {
		return getService(request).reviewedLiabilityBook(bookLabel.asText());
	}
	
	@RequestMapping(value = "/accrual/v1/updatePYTD", method = RequestMethod.POST)
	public @ResponseBody String updatePYTD(HttpServletRequest request) {
		return getService(request).updatePYTD();
	}
	
	@RequestMapping(value = "/accrual/v1/updatePYTDBook", method = RequestMethod.POST)
	public @ResponseBody String updatePYTDBook(HttpServletRequest request, @RequestBody TextNode bookLabel) {
		return getService(request).updatePYTDBook(bookLabel.asText());
	}
	
	@RequestMapping(value = "/accrual/v1/updateCYTD", method = RequestMethod.POST)
	public @ResponseBody String updateCYTD(HttpServletRequest request) {
		return getService(request).updateCYTD();
	}
	
	@RequestMapping(value = "/accrual/v1/updateCYTDBook", method = RequestMethod.POST)
	public @ResponseBody String updateCYTDBook(HttpServletRequest request, @RequestBody String bookLabel) {
		return getService(request).updateCYTDBook(bookLabel);
	}
}
