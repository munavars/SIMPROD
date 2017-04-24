package com.ytc.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytc.common.model.ProgramAchieveOn;
import com.ytc.common.model.ProgramDetail;
import com.ytc.common.model.ProgramHeader;
import com.ytc.common.model.ProgramMaster;
import com.ytc.common.model.ProgramPaidOn;
import com.ytc.common.result.ModelResult;
import com.ytc.dal.model.DalPaidType;
import com.ytc.service.IProgramUpdateService;
import com.ytc.service.IProgramService;

@Controller
@RequestMapping(value = "/index/")
public class ProgramController extends BaseController {
	@RequestMapping(value = "/getAllProgram/{programId}/{customerId}", method = RequestMethod.GET)
	public @ResponseBody ModelResult<ProgramMaster> getDetail(HttpServletRequest request, @PathVariable("programId") Integer programId, @PathVariable("customerId") String customerId) {
		ModelResult<ProgramMaster> returnData = null;
		/**
		 * Assumption, if program id is null, then it is create request.
		 * If Program id is not null, then user is view the program id in edit mode. In this case, we need ot fetch all the
		 * details related to program id and need to show it to user.
		 * In both cases, initializing dropdown values are same.
		 * */
/*		if(programId == null){
			returnData = new ModelResult<ProgramMaster>(getService(request).getProgramDetails(programId,customerId));
		}
		else{*/
			returnData = new ModelResult<ProgramMaster>(getService(request).getProgramDetails(programId,customerId));
		/*}*/
		return returnData;
	}
	

	private IProgramService getService(HttpServletRequest request) {
		return getServiceLocator(request).getProgramService();
	}
	
	private IProgramUpdateService getPersistService() {
		return getServiceLocator().getProgramPersistService();
	}
	
	@RequestMapping(value = "/saveProgramDetails", method = RequestMethod.POST)
	public @ResponseBody Boolean saveProgramDetails(/*HttpServletRequest request, *//*@RequestBody ProgramMaster programMaster */) {
		Boolean returnValue = Boolean.FALSE;
		ProgramMaster programMaster = populateProgramMaster();
		returnValue = (Boolean)(getPersistService().saveProgramDetails(programMaster));
		return returnValue;
	}


	private ProgramMaster populateProgramMaster() {
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
	}
	
}
