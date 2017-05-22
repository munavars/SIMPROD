package com.ytc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytc.common.model.DropDown;
import com.ytc.common.model.Employee;
import com.ytc.common.model.ProgramDetail;
import com.ytc.common.model.ProgramHeader;
import com.ytc.common.model.ProgramInputParam;
import com.ytc.common.model.ProgramTierDetail;
import com.ytc.common.result.DataResult;
import com.ytc.common.result.ListResult;
import com.ytc.common.result.ModelResult;
import com.ytc.service.IProgramService;
import com.ytc.service.IProgramUpdateService;
import com.ytc.service.ServiceContext;

@Controller
@RequestMapping(value = "/program/")
public class ProgramController extends BaseController {
	
	@Autowired
	private ServiceContext serviceContext;
	
	@RequestMapping(value = "v1/getProgramDetail/{programId}", method = RequestMethod.GET)
	public @ResponseBody ModelResult<ProgramHeader> getDetail(HttpServletRequest request, @PathVariable("programId") Integer programId) {
		ModelResult<ProgramHeader> returnData = null;
		/**
		 * Assumption, if program id is null, then it is create request.
		 * If Program id is not null, then user is viewing the program id in edit mode. In this case, we need ot fetch all the
		 * details related to program id and need to show it to user.
		 * In both cases, initializing dropdown values are same.
		 * */
		Employee employee = null;
		if(serviceContext != null){
			employee = serviceContext.getEmployee();
		}
		ProgramInputParam inputParam = new ProgramInputParam();
		inputParam.setProgramDetailId(programId);
		inputParam.setEmployee(employee);
		inputParam.setExistingDetail(true);
		returnData = new ModelResult<ProgramHeader>(getService(request).getProgramDetails(inputParam));
		
		return returnData;
	}
	
	@RequestMapping(value = "v1/getProgramDetailCreate/{programType}/{custId}", method = RequestMethod.GET)
	public @ResponseBody ModelResult<ProgramHeader> getDetailNew(HttpServletRequest request, @PathVariable String programType, @PathVariable Integer custId) {
		ModelResult<ProgramHeader> returnData = null;
		/**
		 * Assumption, if program id is null, then it is create request.
		 * If Program id is not null, then user is viewing the program id in edit mode. In this case, we need ot fetch all the
		 * details related to program id and need to show it to user.
		 * In both cases, initializing dropdown values are same.
		 * */
		Employee employee = null;
		if(serviceContext != null){
			employee = serviceContext.getEmployee();
		}
		ProgramInputParam inputParam = new ProgramInputParam();
		inputParam.setCustomerId(custId);
		inputParam.setEmployee(employee);
		inputParam.setProgramType(programType);
		returnData = new ModelResult<ProgramHeader>(getService(request).getProgramDetails(inputParam));
		
		return returnData;
	}
	
	private IProgramService getService(HttpServletRequest request) {
		return getServiceLocator(request).getProgramService();
	}
	
	private IProgramUpdateService getPersistService() {
		return getServiceLocator().getProgramPersistService();
	}
	
	@RequestMapping(value = "v1/saveProgramDetails", method = RequestMethod.POST)
	public @ResponseBody ModelResult<ProgramHeader> saveProgramDetails(@RequestBody ProgramHeader programHeader) {
		ModelResult<ProgramHeader> returnData = null;
		returnData =  new ModelResult<ProgramHeader>(getPersistService().saveProgramDetails(programHeader));
		return returnData;
	}

	@RequestMapping(value = "v1/getTagValueDropDown/{tagId}", method = RequestMethod.GET)
	public @ResponseBody List<DropDown> getTagValueDetails(HttpServletRequest request, @PathVariable("tagId") Integer tagId) {
		List<DropDown> dropdownList = null;
		dropdownList = (List<DropDown>)(getService(request).getTagValueDropDown(tagId));
		return dropdownList;
	}
	
	@RequestMapping(value = "/{id}/{status}", method = RequestMethod.GET)
	public @ResponseBody ListResult<ProgramDetail> getProgram(HttpServletRequest request, @PathVariable String id,  @PathVariable String status) {
		
		return new ListResult<ProgramDetail>( getService(request).getProgram(id, status));
	}
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public @ResponseBody ListResult<ProgramDetail> getProgramDashboard(HttpServletRequest request) {
		Employee employee = null;
		if(serviceContext != null){
			employee = serviceContext.getEmployee();
		}
		Integer employeeId=employee.getEMP_ID();
		if("N".equalsIgnoreCase(employee.getSECURITY())){
			employeeId=0;
		}
		return new ListResult<ProgramDetail>( getService(request).getProgramDashboard(employeeId));
	}
	
	@RequestMapping(value = "v1/addTier", method = RequestMethod.GET)
	public @ResponseBody DataResult<String> addProgramTier(HttpServletRequest request) {
		
		
		return new DataResult<String>(getService(request).addProgramTier(""));
	}
	
	@RequestMapping(value = "v1/updateTier", method = RequestMethod.POST)
	public @ResponseBody DataResult<String> updateProgramTier(HttpServletRequest request,@RequestBody ProgramTierDetail programTierDetail) {
		
		return new DataResult<String>(getService(request).updateProgramTier(programTierDetail));
	}
	
	@RequestMapping(value = "v1/removeTier/{id}", method = RequestMethod.GET)
	public @ResponseBody DataResult<String> removeProgramTier(HttpServletRequest request,@PathVariable String id) {
		
		return new DataResult<String>(getService(request).deleteProgramTier(id));
		
	}
	
	@Produces({ "application/pdf" })
	@RequestMapping(value = "v1/downloadPDF/{id}",  method = RequestMethod.GET, produces = "application/pdf")
	public @ResponseBody  byte[] downloadPDF(HttpServletRequest request,@PathVariable String id) {
		
		return getService(request).downloadPDF(id);
	}
	
}
