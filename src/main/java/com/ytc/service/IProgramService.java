package com.ytc.service;

import java.util.List;

import com.ytc.common.model.DropDown;
import com.ytc.common.model.NewCustomerDetail;
import com.ytc.common.model.ProgramDetail;
import com.ytc.common.model.ProgramHeader;
import com.ytc.common.model.ProgramInputParam;
import com.ytc.common.model.ProgramPaidOn;
import com.ytc.common.model.ProgramTierDetail;
import com.ytc.dal.model.DalProgramDetail;

public interface IProgramService {

	List<DropDown> getTagValueDropDown(Integer tagId, Integer employeeId);
	
	ProgramPaidOn getTagDetails(String tag);

	String updateProgramTier(ProgramTierDetail programTierDetail);

	String deleteProgramTier(String id);

	String addProgramTier(String id);

	List<ProgramDetail> getProgram(String customerId, String status);

	ProgramHeader getProgramDetails(ProgramInputParam inputParam);

	List<ProgramDetail> getProgramDashboard(Integer id);

	byte[] downloadPDF(String id);
	
	List<NewCustomerDetail> getNewCustomerData(Integer employeeId);
	
	void populateTierData(ProgramHeader programHeader, DalProgramDetail dalProgramDetail);
}
