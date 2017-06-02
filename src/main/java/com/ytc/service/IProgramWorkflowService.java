package com.ytc.service;

import com.ytc.common.model.Employee;
import com.ytc.common.model.ProgramHeader;
import com.ytc.dal.model.DalProgramDetail;

/**
 * Interface - IProgramWorkflowService.java
 * Purpose - I/f class exposes methods related to workflow updated for the program details.
 * @author Cognizant.
 *
 */
public interface IProgramWorkflowService {
	
	/**
	 * Method to update the workflow details.
	 * @param dalProgramDet dalProgramDet
	 * @param programHeader programHeader
	 * @param employee employee
	 */
	void updateWorkflowDetails(DalProgramDetail dalProgramDet, ProgramHeader programHeader, Employee employee);

}
