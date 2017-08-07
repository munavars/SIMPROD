/**
 * 
 */
package com.ytc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytc.common.model.DropDown;
import com.ytc.common.model.Employee;
import com.ytc.common.model.NetPricing;
import com.ytc.common.model.PricingHeader;
import com.ytc.common.result.ListResult;
import com.ytc.common.result.ModelResult;
import com.ytc.constant.ProgramConstant;
import com.ytc.service.IPricingService;
import com.ytc.service.IPricingUpdateService;
import com.ytc.service.ServiceContext;

@Controller
@RequestMapping(value = "/")
public class PricingController extends BaseController {

	@Autowired
	private ServiceContext serviceContext;
	

	@RequestMapping(value = "/pricing/v1/getTagValueDropDown/{tagId}", method = RequestMethod.GET)
	public @ResponseBody List<DropDown> getTagValueDetails(HttpServletRequest request, @PathVariable("tagId") Integer tagId) {
		List<DropDown> dropdownList = null;
		dropdownList = (List<DropDown>)(getService(request).getTagValueDropDown(tagId));
		return dropdownList;
	}
	private IPricingService getService(HttpServletRequest request) {
		return getServiceLocator(request).getPricingService();
	}
	private IPricingUpdateService getPersistService() {
		return getServiceLocator().getPricingPersistService();
	}
	
	@RequestMapping(value = "/pricingRequest",method = RequestMethod.GET)
	public String pricingForm(HttpServletRequest request, Model model) {
		String returnModel = null;
		List<Employee> employee = null;
		if(serviceContext != null && serviceContext.getEmployee() != null){
			String userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			/*ModelResult<PricingHeader> returnData = null;
			returnData = new ModelResult<PricingHeader>(getService(request).getPricingDetails());*/
			model.addAttribute("loginUserNameValue", userName);
			employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
			returnModel ="pricing_request";
		}else{
			returnModel = "login";
		}
		
		return returnModel;
		
	}
	
	@RequestMapping(value = "/pricingOverview",method = RequestMethod.GET)
	public String pricingOverview(HttpServletRequest request, Model model) {
		String returnModel = null;
		List<Employee> employee = null;
		if(serviceContext != null && serviceContext.getEmployee() != null){
			String userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			/*ModelResult<PricingHeader> returnData = null;
			returnData = new ModelResult<PricingHeader>(getService(request).getPricingDetails());*/
			model.addAttribute("loginUserNameValue", userName);
			employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
			returnModel ="pricing_overview";
		}else{
			returnModel = "login";
		}

		return returnModel;
		
	}
	
	@RequestMapping(value = "/pricing/v1/getPricingDetails/{customerId}",method = RequestMethod.GET)
	public @ResponseBody ModelResult<PricingHeader> getPricingDetails(HttpServletRequest request, @PathVariable("customerId") Integer customerId) {
		ModelResult<PricingHeader> returnData = null;
		returnData = new ModelResult<PricingHeader>(getService(request).getPricingDetails(customerId));
		return returnData;
		
	}
	
	/**
	 * Method to get the pricing detail based on the pricing id passed as input.
	 * @param request request
	 * @param pricingHeaderId pricingHeaderId
	 * @return ProgramHeader i., program header model class.
	 */
	@RequestMapping(value = "/pricing/v1/getPricingDetail/{id}",method = RequestMethod.GET)
	public @ResponseBody ModelResult<PricingHeader> getPricingDetail(HttpServletRequest request, @PathVariable("id") Integer pricingHeaderId) {
		ModelResult<PricingHeader> returnData = null;
		returnData = new ModelResult<PricingHeader>(getService(request).getPricingDetail(pricingHeaderId));
		return returnData;
		
	}
	
	@RequestMapping(value = "/pricing/v1/getCustomerPricingDetails/{bu}/{custId}",method = RequestMethod.GET)
	public @ResponseBody ListResult<NetPricing> getCustomerPricingDetails(HttpServletRequest request, @PathVariable("bu") String bu, @PathVariable("custId") Integer custId) {
		ListResult<NetPricing> returnData = null;
		returnData = new ListResult<NetPricing>(getService(request).getCustomerPricingDetails(serviceContext.getEmployee().getEMP_ID(),bu, custId));
		//returnData = new ListResult<NetPricing>(getService(request).getCustomerPricingDetails(98));
		return returnData;
		
	}
	
	
	
	@RequestMapping(value = "/pricing/v1/savePricingDetails", method = RequestMethod.POST)
	public @ResponseBody ModelResult<PricingHeader> savePricingDetails(HttpServletRequest request, @RequestBody PricingHeader pricingHeader) {
		ModelResult<PricingHeader> returnData = null;
		if(pricingHeader != null && request != null){
			pricingHeader.setContextPath(request.getContextPath());
		}
		returnData =  new ModelResult<PricingHeader>(getPersistService().savePricingHeaderDetails(pricingHeader));
		return returnData;
	}
	
	@RequestMapping(value = "/pricing/v1/validatePricingInvoiceDetails", method = RequestMethod.POST)
	public @ResponseBody ModelResult<PricingHeader> validatePricingInvoiceDetails(HttpServletRequest request, @RequestBody PricingHeader pricingHeader) {
		ModelResult<PricingHeader> returnData = null;
		if(pricingHeader != null && request != null){
			pricingHeader.setContextPath(request.getContextPath());
		}
		returnData =  new ModelResult<PricingHeader>(getPersistService().validatePricingInvoiceDetails(pricingHeader));
		return returnData;
	}
	
}
