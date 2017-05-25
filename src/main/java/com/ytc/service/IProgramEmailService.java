package com.ytc.service;

import com.ytc.common.model.ProgramHeader;
import com.ytc.dal.model.DalProgramDetail;

/**
 * Interface : IProgramEmailService
 * Purpose : Has methods to prepares the email content for program details page.
 * @author Cognizant.
 *
 */
public interface IProgramEmailService {
	
	/**
	 * Method to send email while program details submit, approve or reject.
	 * @param programHeader programHeader
	 * @param dalProgramDetail  dalProgramDetail
	 */ 
	void sendEmailData(ProgramHeader programHeader, DalProgramDetail dalProgramDetail);
}
