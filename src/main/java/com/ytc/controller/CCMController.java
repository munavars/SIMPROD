/**
 * 
 */
package com.ytc.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytc.common.model.AccuralCcmData;
import com.ytc.common.model.CcmDetails;
import com.ytc.common.model.DropDown;
import com.ytc.common.result.ListResult;
import com.ytc.constant.ProgramConstant;
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
	@RequestMapping(value = "/ccm/v1/getPeriodDropDown", method = RequestMethod.GET)
	public @ResponseBody List<DropDown> getPeriodValueDetails(HttpServletRequest request) {
		List<DropDown> dropdownList = null;
		dropdownList = (List<DropDown>)(getService(request).getPeriodDropDownList());
		return dropdownList;
	}

	@RequestMapping(value = "/ccm/v1/createMemoData/{programId}", method = RequestMethod.GET)
	public @ResponseBody boolean createMemoData(HttpServletRequest request, @PathVariable("programId") Integer programId) {
		getService(request).createMemoData(programId);
		return true;
	}
	
	@RequestMapping(value = "/ccm/v1/getCcmDetails/{frequency}/{bu}/{period}/{status}", method = RequestMethod.GET)
	public @ResponseBody ListResult<CcmDetails> getCCMDetails(HttpServletRequest request, @PathVariable("frequency") String frequency, @PathVariable("bu") String bu, @PathVariable("period") Integer period, @PathVariable("status") String status) {
		 
		List<CcmDetails> result=getService(request).getCCMDetails(frequency, bu, period, status);
		return new ListResult<CcmDetails>(result);
		
	}
	
	/*@RequestMapping(value = "/ccm/v1/saveCCMDetails/{id}/{adjustedAmount}/{adjustedCredit}", method = RequestMethod.GET)
	public @ResponseBody int saveCCMDetails(HttpServletRequest request, @PathVariable("id") Integer id, @PathVariable("adjustedAmount") double adjustedAmount, @PathVariable("adjustedCredit") double adjustedCredit) {
		//String userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
		int result=getService(request).saveCCMDetails(id, adjustedAmount, adjustedCredit,"Test");
		return result;
		
	}*/
	
	@RequestMapping(value = "/ccm/v1/saveCCMDetails", method = RequestMethod.POST)
	public @ResponseBody int saveCCMDetails(HttpServletRequest request, @RequestBody List<AccuralCcmData> accuralCcmDataList) {
		int count=0;
		String userName="";
		if(serviceContext != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
		}
		for (Iterator<AccuralCcmData> iterator = accuralCcmDataList.iterator(); iterator.hasNext();) {
			AccuralCcmData accuralCcmData = (AccuralCcmData) iterator.next();
			count=getService(request).saveCCMDetails(accuralCcmData.getId(), accuralCcmData.getAdjustedAmount(), accuralCcmData.getAdjustedCredit(),userName);
		}
		return count;
		
	}
	
	@RequestMapping(value = "/ccm/v1/submitCCMDetails", method = RequestMethod.POST)
	public @ResponseBody int submitCcmForApproval(HttpServletRequest request, @RequestBody List<Integer> approvalList) {
		int count=0;	

			count=getService(request).submitCcmForApproval(approvalList);
		
		return count;
		
	}
	
	@RequestMapping(value = "/ccm/v1/updateCCMStatus", method = RequestMethod.POST)
	public @ResponseBody int updateCcmStatus(HttpServletRequest request, @RequestBody Integer id) {
		int count=0;	

			count=getService(request).updateCcmStatus(id);
		
		return count;
		
	}
}
