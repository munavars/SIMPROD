package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.EmailDetails;
import com.ytc.common.model.ProgramHeader;
import com.ytc.constant.EmailConstant;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.model.DalEmployee;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.mail.intf.IYTCMailConnectorService;
import com.ytc.service.IProgramEmailService;

/**
 * Class : ProgramEmailServiceImpl
 * Purpose : This class prepare the data for the mail to be send and calls the email connector class.
 * @author Cognizant.
 *
 */
public class ProgramEmailServiceImpl implements IProgramEmailService {

	@Autowired
	private IYTCMailConnectorService ytcMailConnectorService;
	
	@Override
	/**
	 * This method is used to prepare the email data and calls the appropriate method to send the details. 
	 */
	public void sendEmailData(ProgramHeader programHeader, DalProgramDetail dalProgramDetail) {
		if(programHeader != null && dalProgramDetail != null && 
				!ProgramConstant.IN_PROGRESS_STATUS.equalsIgnoreCase(dalProgramDetail.getStatus().getType())){
			EmailDetails emailDetails = new EmailDetails();
			
			emailDetails.setFromAddress(dalProgramDetail.getModifiedBy().getEMAIL());
			buildToAndCCAddress(emailDetails, dalProgramDetail, programHeader);
			buildSubject(emailDetails, dalProgramDetail, programHeader);
			buildText(emailDetails, dalProgramDetail, programHeader);
			
			ytcMailConnectorService.sendEmail(emailDetails);
		}
	}

	private void buildText(EmailDetails emailDetails, DalProgramDetail dalProgramDetail, ProgramHeader programHeader) {
		String bodyContent = null;
		if(ProgramConstant.PENDING_STATUS.equalsIgnoreCase(dalProgramDetail.getStatus().getType())){
			bodyContent = buildContentForApprover(emailDetails, dalProgramDetail, programHeader);
		}
		/**Below things has to be modified. For now, gng to test pending status first*/
		else if(ProgramConstant.APPROVED_STATUS.equalsIgnoreCase(dalProgramDetail.getStatus().getType())){
			if(ProgramConstant.USER_LEVEL_2.equals(programHeader.getProgramButton().getUserLevel())){
				bodyContent = buildContentForApprover(emailDetails, dalProgramDetail, programHeader);	
			}
			else{
				/**If control is here, then approval is done by TBP user who is level 3 user.*/
				bodyContent = buildApprovedMailContent(emailDetails, dalProgramDetail, programHeader);
			}
		}
		else if(ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(dalProgramDetail.getStatus().getType())){
			bodyContent = buildRejectedMailContent(emailDetails, dalProgramDetail, programHeader);
		}
		emailDetails.setText(bodyContent);	
	}
 
	private String buildRejectedMailContent(EmailDetails emailDetails, DalProgramDetail dalProgramDetail,
			ProgramHeader programHeader) {
		StringBuilder html = new StringBuilder();
		
		html.append(EmailConstant.HTML_BEGIN);
		html.append(String.format(EmailConstant.HTML_BODY_GREETING, getName(dalProgramDetail.getCreatedBy()) + EmailConstant.COMMA) );
		html.append(String.format(EmailConstant.HTML_BODY_ACTION_TAKEN, 
				getName(dalProgramDetail.getModifiedBy()) + " has rejected the program details."));
		html.append(String.format(EmailConstant.HTML_BODY_PROGRAM_ID, dalProgramDetail.getId()));
		html.append(String.format(EmailConstant.HTML_BODY_ACTION_TO_BE_TAKEN, 
									EmailConstant.RE_SUBMIT));
		/*html.append(EmailConstant.HTML_BODY_LINK_TITLE);*/
		String link = null;
		String url = null;
		if(programHeader.getContextPath() != null && programHeader.getUrl() != null){
			int index = programHeader.getUrl().indexOf(programHeader.getContextPath());
			url = programHeader.getUrl().substring(0, index) + programHeader.getContextPath();
		}
        if(programHeader.isCalculatedProgram()){
        	link = EmailConstant.LINK_BEGIN + url + EmailConstant.LINK_CALCULATED_PGM_1 + 
        			dalProgramDetail.getId() + EmailConstant.LINK_END;

        }
        else {
        	link = EmailConstant.LINK_BEGIN + url + EmailConstant.LINK_DDF_COOP_PGM_1 + 
        			dalProgramDetail.getId() + EmailConstant.LINK_END;
        }
		html.append(link);
		
		return html.toString();
	}

	private String buildApprovedMailContent(EmailDetails emailDetails, DalProgramDetail dalProgramDetail,
			ProgramHeader programHeader) {
		StringBuilder html = new StringBuilder();
		
		html.append(EmailConstant.HTML_BEGIN);
		html.append(String.format(EmailConstant.HTML_BODY_GREETING, (getName(dalProgramDetail.getCreatedBy())+ EmailConstant.L_AND + 
																	getName(dalProgramDetail.getZmAppById())) + EmailConstant.COMMA));
		html.append(String.format(EmailConstant.HTML_BODY_ACTION_TAKEN, 
				getName(dalProgramDetail.getModifiedBy()) + " has approved the program details."));
		html.append(String.format(EmailConstant.HTML_BODY_PROGRAM_ID, dalProgramDetail.getId()));
		/*html.append(String.format(EmailConstant.HTML_BODY_ACTION_TO_BE_TAKEN, 
				EmailConstant.APPROVAL_OR_REJECT));*/
		/*html.append(EmailConstant.HTML_BODY_LINK_TITLE);*/
		String link = null;
		String url = null;
		if(programHeader.getContextPath() != null && programHeader.getUrl() != null){
			int index = programHeader.getUrl().indexOf(programHeader.getContextPath());
			url = programHeader.getUrl().substring(0, index) + programHeader.getContextPath();
		}
        if(programHeader.isCalculatedProgram()){
        	link = EmailConstant.LINK_BEGIN + url + EmailConstant.LINK_CALCULATED_PGM_1 + 
        			dalProgramDetail.getId() + EmailConstant.LINK_END;

        }
        else {
        	link = EmailConstant.LINK_BEGIN + url + EmailConstant.LINK_DDF_COOP_PGM_1 + 
        			dalProgramDetail.getId() + EmailConstant.LINK_END;
        }
		html.append(link);
		html.append(EmailConstant.HTML_END);
		
		return html.toString();
	}

	private String buildContentForApprover(EmailDetails emailDetails, DalProgramDetail dalProgramDetail, ProgramHeader programHeader) {
		StringBuilder html = new StringBuilder();
		
		html.append(EmailConstant.HTML_BEGIN);
		if(ProgramConstant.USER_LEVEL_1.equals(programHeader.getProgramButton().getUserLevel())){
			html.append(String.format(EmailConstant.HTML_BODY_GREETING, getName(dalProgramDetail.getZmAppById()) + EmailConstant.COMMA) );	
		}
		else if(ProgramConstant.USER_LEVEL_2.equals(programHeader.getProgramButton().getUserLevel())){
			html.append(String.format(EmailConstant.HTML_BODY_GREETING, getName(dalProgramDetail.getTbpAppById()) + EmailConstant.COMMA) );
		}
		html.append(String.format(EmailConstant.HTML_BODY_ACTION_TAKEN, 
				getName(dalProgramDetail.getModifiedBy()) + " has submitted the program details for your approval."));
		html.append(String.format(EmailConstant.HTML_BODY_PROGRAM_ID, dalProgramDetail.getId()));
		html.append(String.format(EmailConstant.HTML_BODY_ACTION_TO_BE_TAKEN, 
				EmailConstant.APPROVAL_OR_REJECT));
		/*html.append(EmailConstant.HTML_BODY_LINK_TITLE);*/
		String link = null;
		String url = null;
		if(programHeader.getContextPath() != null && programHeader.getUrl() != null){
			int index = programHeader.getUrl().indexOf(programHeader.getContextPath());
			url = programHeader.getUrl().substring(0, index) + programHeader.getContextPath();
		}
        if(programHeader.isCalculatedProgram()){
        	link = EmailConstant.LINK_BEGIN + url + EmailConstant.LINK_CALCULATED_PGM_1 + 
        			dalProgramDetail.getId() + EmailConstant.LINK_END;

        }
        else {
        	link = EmailConstant.LINK_BEGIN + url + EmailConstant.LINK_DDF_COOP_PGM_1 + 
        			dalProgramDetail.getId() + EmailConstant.LINK_END;
        }
		html.append(link);
		html.append(EmailConstant.HTML_END);
		
		return html.toString();
	}
	
	private String getName(DalEmployee dalEmployee){
		String name = null;
		if(dalEmployee != null){
			name = dalEmployee.getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + dalEmployee.getLAST_NAME();
		}
		return name;
	}

	private void buildSubject(EmailDetails emailDetails, DalProgramDetail dalProgramDetail, ProgramHeader programHeader) {
		String subject = null;
		if(ProgramConstant.PENDING_STATUS.equalsIgnoreCase(dalProgramDetail.getStatus().getType())){
			subject = String.format(EmailConstant.SUBJECT_PENDING, dalProgramDetail.getId());
		}
		else if(ProgramConstant.APPROVED_STATUS.equalsIgnoreCase(dalProgramDetail.getStatus().getType())){
			if(ProgramConstant.USER_LEVEL_2.equals(programHeader.getProgramButton().getUserLevel())){
				subject = String.format(EmailConstant.SUBJECT_PENDING, dalProgramDetail.getId());	
			}
			else{
				/**If control is here, then approval is done by TBP user who is level 3 user.*/
				subject = String.format(EmailConstant.SUBJECT_APPROVAL, dalProgramDetail.getId());
			}
		}
		else if(ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(dalProgramDetail.getStatus().getType())){
			subject = String.format(EmailConstant.SUBJECT_REJECTED, dalProgramDetail.getId());
		}
		emailDetails.setSubject(subject);
	}

	/**
	 * Method to build the to addresses.
	 * @param emailDetails emailDetails
	 * @param dalProgramDetail dalProgramDetail
	 * @param programHeader programHeader
	 */
	private void buildToAndCCAddress(EmailDetails emailDetails, DalProgramDetail dalProgramDetail,
			ProgramHeader programHeader) {
		List<String> toEmailIdList = new ArrayList<String>();
		List<String> ccEmailIdList = null;
		if(programHeader != null){
			if(ProgramConstant.USER_LEVEL_1.equals(programHeader.getProgramButton().getUserLevel())){
				toEmailIdList.add(dalProgramDetail.getZmAppById().getEMAIL());
			}
			else if(ProgramConstant.USER_LEVEL_2.equals(programHeader.getProgramButton().getUserLevel())){
				if(ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(dalProgramDetail.getStatus().getType())){
					toEmailIdList.add(dalProgramDetail.getCreatedBy().getEMAIL());
				}
				else{
					toEmailIdList.add(dalProgramDetail.getTbpAppById().getEMAIL());
				}
				
			}
			else if(ProgramConstant.USER_LEVEL_3.equals(programHeader.getProgramButton().getUserLevel())){
				if(ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(dalProgramDetail.getStatus().getType())){
					toEmailIdList.add(dalProgramDetail.getCreatedBy().getEMAIL());
					if(ccEmailIdList == null){
						ccEmailIdList = new ArrayList<String>();
					}
					ccEmailIdList.add(dalProgramDetail.getZmAppById().getEMAIL());
				}
				else{
					toEmailIdList.add(dalProgramDetail.getCreatedBy().getEMAIL());
					toEmailIdList.add(dalProgramDetail.getZmAppById().getEMAIL());
				}
			}
			emailDetails.setToAddress(toEmailIdList);
			emailDetails.setCcAddress(ccEmailIdList);
		}
	}

}
