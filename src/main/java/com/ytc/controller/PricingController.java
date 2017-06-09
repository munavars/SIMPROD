/**
 * 
 */
package com.ytc.controller;

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
import com.ytc.common.model.PricingHeader;
import com.ytc.common.model.ProgramHeader;
import com.ytc.common.model.ProgramInputParam;
import com.ytc.common.result.ModelResult;
import com.ytc.service.IPricingService;
import com.ytc.service.IPricingUpdateService;
import com.ytc.service.ServiceContext;

@Controller
@RequestMapping(value = "/pricing")

	
public class PricingController extends BaseController {

	@Autowired
	private ServiceContext serviceContext;
	

	@RequestMapping(value = "/v1/getTagValueDropDown/{tagId}", method = RequestMethod.GET)
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
	
	@RequestMapping(method = RequestMethod.GET)
	public String pricingForm(HttpServletRequest request, Model model) {
		String returnModel = null;
		/*ModelResult<PricingHeader> returnData = null;
		returnData = new ModelResult<PricingHeader>(getService(request).getPricingDetails());*/
		returnModel ="pricing_request";
		return returnModel;
		
	}
	@RequestMapping(value = "/v1/getPricingDetails",method = RequestMethod.GET)
	public @ResponseBody ModelResult<PricingHeader> getPricingDetails(HttpServletRequest request) {
		ModelResult<PricingHeader> returnData = null;
		returnData = new ModelResult<PricingHeader>(getService(request).getPricingDetails());
		return returnData;
		
	}
	
	
	
	@RequestMapping(value = "/v1/savePricingDetails", method = RequestMethod.POST)
	public @ResponseBody ModelResult<PricingHeader> savePricingDetails(HttpServletRequest request, @RequestBody PricingHeader pricingHeader) {
		ModelResult<PricingHeader> returnData = null;
		returnData =  new ModelResult<PricingHeader>(getPersistService().savePricingHeaderDetails(pricingHeader));
		return returnData;
	}
	
}
