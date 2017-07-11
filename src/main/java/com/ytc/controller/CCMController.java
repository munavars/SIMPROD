/**
 * 
 */
package com.ytc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytc.common.model.CcmDetails;
import com.ytc.common.model.DropDown;
import com.ytc.common.result.ListResult;
import com.ytc.service.ICcmService;
import com.ytc.service.ServiceContext;


@Controller
@RequestMapping(value = "/")
public class CCMController extends BaseController {

	@Autowired
	private ServiceContext serviceContext;
	

	@RequestMapping(value = "/ccm/v1/getFrequencyDropDown", method = RequestMethod.GET)
	public @ResponseBody List<DropDown> getSelectValueDetails(HttpServletRequest request) {
		List<DropDown> dropdownList = null;
		dropdownList = (List<DropDown>)(getService(request).getFrequencyDropDownList("DalFrequency.getAllDetailsWithSort"));
		return dropdownList;
	}
	private ICcmService getService(HttpServletRequest request) {
		return getServiceLocator(request).getCcmService();
	}

	@RequestMapping(value = "/ccm/v1/createMemoData/{programId}", method = RequestMethod.GET)
	public @ResponseBody boolean createMemoData(HttpServletRequest request, @PathVariable("programId") Integer programId) {
		getService(request).createMemoData(programId);
		return true;
	}
	
	@RequestMapping(value = "/ccm/v1/getCcmDetails/{frequency}/{bu}/{begindate}/{endDate}", method = RequestMethod.GET)
	public @ResponseBody ListResult<CcmDetails> getCCMDetails(HttpServletRequest request, @PathVariable("frequency") String frequency, @PathVariable("bu") String bu, @PathVariable("begindate") String beginDate, @PathVariable("endDate") String endDate) {
		 
		List<CcmDetails> result=getService(request).getCCMDetails(frequency, bu, beginDate, endDate);
		return new ListResult<CcmDetails>(result);
		
	}
}
