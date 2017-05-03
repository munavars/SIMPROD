package com.ytc.service;

import java.util.List;

import com.ytc.common.model.DropDown;
import com.ytc.common.model.ProgramDetail;
import com.ytc.common.model.ProgramHeader;
import com.ytc.common.model.ProgramTierDetail;

public interface IProgramService {
	
	ProgramHeader getProgramDetails(Integer programDetId);
	
	List<DropDown> getTagValueDropDown(Integer tagId);
	
	String updateProgramTier(ProgramTierDetail programTierDetail);

	String deleteProgramTier(String id);

	String addProgramTier(String id);

	List<ProgramDetail> getProgram(String customerId, String status);

}
