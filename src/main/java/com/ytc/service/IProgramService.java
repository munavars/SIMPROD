package com.ytc.service;

import com.ytc.common.model.ProgramMaster;

public interface IProgramService {
	
	ProgramMaster getProgramDetails(Integer programId, String customerId);

}
