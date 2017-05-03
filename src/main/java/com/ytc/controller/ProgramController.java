package com.ytc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytc.common.model.DropDown;
import com.ytc.common.model.ProgramDetail;
import com.ytc.common.model.ProgramHeader;
import com.ytc.common.model.ProgramTierDetail;
import com.ytc.common.result.DataResult;
import com.ytc.common.result.ListResult;
import com.ytc.common.result.ModelResult;
import com.ytc.service.IProgramService;
import com.ytc.service.IProgramUpdateService;

@Controller
@RequestMapping(value = "/program/")
public class ProgramController extends BaseController {
	@RequestMapping(value = "v1/getProgramDetail/{programId}", method = RequestMethod.GET)
	public @ResponseBody ModelResult<ProgramHeader> getDetail(HttpServletRequest request, @PathVariable("programId") Integer programId) {
		ModelResult<ProgramHeader> returnData = null;
		/**
		 * Assumption, if program id is null, then it is create request.
		 * If Program id is not null, then user is viewing the program id in edit mode. In this case, we need ot fetch all the
		 * details related to program id and need to show it to user.
		 * In both cases, initializing dropdown values are same.
		 * */
		returnData = new ModelResult<ProgramHeader>(getService(request).getProgramDetails(programId));
		
		return returnData;
	}
	
	@RequestMapping(value = "v1/getProgramDetail/", method = RequestMethod.GET)
	public @ResponseBody ModelResult<ProgramHeader> getDetailNew(HttpServletRequest request) {
		ModelResult<ProgramHeader> returnData = null;
		/**
		 * Assumption, if program id is null, then it is create request.
		 * If Program id is not null, then user is viewing the program id in edit mode. In this case, we need ot fetch all the
		 * details related to program id and need to show it to user.
		 * In both cases, initializing dropdown values are same.
		 * */
		returnData = new ModelResult<ProgramHeader>(getService(request).getProgramDetails(null));
		
		return returnData;
	}

	private IProgramService getService(HttpServletRequest request) {
		return getServiceLocator(request).getProgramService();
	}
	
	private IProgramUpdateService getPersistService() {
		return getServiceLocator().getProgramPersistService();
	}
	
	@RequestMapping(value = "v1/saveProgramDetails", method = RequestMethod.POST)
	public @ResponseBody Boolean saveProgramDetails(@RequestBody ProgramHeader programHeader) {
		Boolean returnValue = Boolean.FALSE;
		returnValue = (Boolean)(getPersistService().saveProgramDetails(programHeader));
		return returnValue;
	}

	@RequestMapping(value = "v1/getTagValueDropDown/{tagId}", method = RequestMethod.GET)
	public @ResponseBody List<DropDown> getTagValueDetails(HttpServletRequest request, @PathVariable("tagId") Integer tagId) {
		List<DropDown> dropdownList = null;
		dropdownList = (List<DropDown>)(getService(request).getTagValueDropDown(tagId));
		return dropdownList;
	}


/*	private ProgramMaster populateProgramMaster() {
		ProgramMaster programMaster = new ProgramMaster();
		
		ProgramHeader programHeader = new ProgramHeader();
		programHeader.setCustomerName("YOKO 1");
		programHeader.setBusinessUnit("YOKO TEST");
//		programHeader.setProgramId(programId);
		
		ProgramDetail programDetail = new ProgramDetail();
		programDetail.setProgramName("1234");
		programDetail.setPaidBasedOn(String.valueOf(6));
		programDetail.setPayoutFrequency(String.valueOf(7));
		programDetail.setBeginDate(new Date());
		programDetail.setEndDate(new Date());
		programDetail.setBtl(true);
		programDetail.setPricingType(String.valueOf(2));
		programDetail.setAmount(new BigDecimal(1234));
		programDetail.setAmountType("$");
		programDetail.setPayTo("Yaaro");
		programDetail.setPaidType(String.valueOf(1));
		
		ProgramPaidOn programPaidOn = new ProgramPaidOn();
		programPaidOn.setIsTiered(true);
		programPaidOn.setIsTrueUp(true);
		programPaidOn.setProgramDescription("Bossu");
		Map<String, List<String>> includedMap = new HashMap<String, List<String>>();
		List<String> list1 = Arrays.asList("19611","17354", "19611");
		List<String> list2 = Arrays.asList("REFRIGERATED TRANS., INC.","BRISK TRANSPORTATION","DRAKE REFRIGERATION");
		includedMap.put(String.valueOf(1), list1);
		includedMap.put(String.valueOf(10), list2);
		Map<String, List<String>> excludedMap = new HashMap<String, List<String>>();
		List<String> list3 = Arrays.asList("CLARKSVILLE REFRIG LINES");
		excludedMap.put(String.valueOf(1), list3);
		
		programPaidOn.setExcludedMap(excludedMap);
		programPaidOn.setIncludedMap(includedMap);
		
		ProgramAchieveOn programAchieveOn = new ProgramAchieveOn();
		Map<String, List<String>> includedMap1 = new HashMap<String, List<String>>();
		List<String> list4 = Arrays.asList("19611","17354", "19611");
		List<String> list5 = Arrays.asList("REFRIGERATED TRANS., INC.","BRISK TRANSPORTATION","DRAKE REFRIGERATION");
		includedMap1.put(String.valueOf(1), list4);
		includedMap1.put(String.valueOf(10), list5);
		Map<String, List<String>> excludedMap1 = new HashMap<String, List<String>>();
		List<String> list6 = Arrays.asList("CLARKSVILLE REFRIG LINES");
		excludedMap1.put(String.valueOf(1), list6);
		
		programAchieveOn.setExcludedMap(excludedMap1);
		programAchieveOn.setIncludedMap(includedMap1);
		
		programMaster.setProgramAchieveOn(programAchieveOn);
		programMaster.setProgramPaidOn(programPaidOn);
		programMaster.setProgramDetail(programDetail);
		programMaster.setProgramHeader(programHeader);
		
		return programMaster;
	}*/
	
	
	@RequestMapping(value = "/{id}/{status}", method = RequestMethod.GET)
	public @ResponseBody ListResult<ProgramDetail> getProgram(HttpServletRequest request, @PathVariable String id,  @PathVariable String status) {
		
		return new ListResult<ProgramDetail>( getService(request).getProgram(id, status));
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
	
}
