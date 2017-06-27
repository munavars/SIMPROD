package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.comparator.WorkflowStatusComparatorByModifiedDate;
import com.ytc.common.model.EmailDetails;
import com.ytc.common.model.PricingHeader;
import com.ytc.constant.EmailConstant;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.model.DalEmployee;
import com.ytc.dal.model.DalPricingHeader;
import com.ytc.dal.model.DalWorkflowStatus;
import com.ytc.helper.ProgramServiceHelper;
import com.ytc.mail.intf.IYTCMailConnectorService;
import com.ytc.service.IPricingEmailService;

/**
 * Class : PricingEmailServiceImpl
 * Purpose : This class prepare the data for the mail to be send and calls the email connector class.
 * @author Cognizant.
 *
 */
public class PricingEmailServiceImpl implements IPricingEmailService{

	@Autowired
	private IYTCMailConnectorService ytcMailConnectorService;
	
	@Override
	public void sendEmailData(PricingHeader pricingHeader, DalPricingHeader dalPricingHeader) {
		if(pricingHeader != null && dalPricingHeader != null && 
				!ProgramConstant.IN_PROGRESS_STATUS.equalsIgnoreCase(dalPricingHeader.getDalStatus().getType())){
			EmailDetails emailDetails = new EmailDetails();
			
			emailDetails.setFromAddress(dalPricingHeader.getModifiedBy().getEMAIL());
			buildToAndCCAddress(emailDetails, dalPricingHeader, pricingHeader);
			buildSubject(emailDetails, dalPricingHeader, pricingHeader);
			buildText(emailDetails, dalPricingHeader, pricingHeader);
			
			ytcMailConnectorService.sendEmail(emailDetails);
		}		
	}

	
	private void buildText(EmailDetails emailDetails, DalPricingHeader dalPricingHeader, PricingHeader pricingHeader) {
		String bodyContent = null;
		if(ProgramConstant.PENDING_STATUS.equalsIgnoreCase(dalPricingHeader.getDalStatus().getType())){
			bodyContent = buildContentForApprover(emailDetails, dalPricingHeader, pricingHeader);
		}
		/**Below things has to be modified. For now, gng to test pending status first*/
		else if(ProgramConstant.APPROVED_STATUS.equalsIgnoreCase(dalPricingHeader.getDalStatus().getType())){
/*			if(ProgramConstant.USER_LEVEL_2.equals(programHeader.getProgramButton().getUserLevel())){
				bodyContent = buildContentForApprover(emailDetails, dalProgramDetail, programHeader);	
			}
			else{*/
				/**If control is here, then approval is done by TBP user who is level 3 user.*/
				bodyContent = buildApprovedMailContent(emailDetails, dalPricingHeader, pricingHeader);
			/*}*/
		}
		else if(ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(dalPricingHeader.getDalStatus().getType())){
			bodyContent = buildRejectedMailContent(emailDetails, dalPricingHeader, pricingHeader);
		}
		emailDetails.setText(bodyContent);	
	}
 
	private String buildRejectedMailContent(EmailDetails emailDetails, DalPricingHeader dalPricingHeader,
			PricingHeader pricingHeader) {
		StringBuilder html = new StringBuilder();
		
		html.append(EmailConstant.HTML_BEGIN);
		html.append(String.format(EmailConstant.HTML_BODY_GREETING, emailDetails.getToNames() + EmailConstant.COMMA));
		html.append(String.format(EmailConstant.HTML_BODY_ACTION_TAKEN, 
				ProgramServiceHelper.getName(dalPricingHeader.getModifiedBy()) + " has rejected the Pricing details."));
		html.append(String.format(EmailConstant.PRICING_HTML_BODY_ID, dalPricingHeader.getId()));
		html.append(String.format(EmailConstant.HTML_BODY_ACTION_TO_BE_TAKEN, 
									EmailConstant.RE_SUBMIT));
		/*html.append(EmailConstant.HTML_BODY_LINK_TITLE);*/
		String link = null;
		String url = null;
		if(pricingHeader.getContextPath() != null && pricingHeader.getUrl() != null){
			int index = pricingHeader.getUrl().indexOf(pricingHeader.getContextPath());
			url = pricingHeader.getUrl().substring(0, index) + pricingHeader.getContextPath();
		}
		link = EmailConstant.LINK_BEGIN + url + EmailConstant.PRICING_LINK_PAGE + 
    			dalPricingHeader.getId() + EmailConstant.PRICING_LINK_END;
		html.append(link);
		
		return html.toString();
	}

	private String buildApprovedMailContent(EmailDetails emailDetails, DalPricingHeader dalPricingHeader,
			PricingHeader pricingHeader) {
		StringBuilder html = new StringBuilder();
		
		html.append(EmailConstant.HTML_BEGIN);
		html.append(String.format(EmailConstant.HTML_BODY_GREETING, emailDetails.getToNames() + EmailConstant.COMMA) );
		html.append(String.format(EmailConstant.HTML_BODY_ACTION_TAKEN, 
				ProgramServiceHelper.getName(dalPricingHeader.getModifiedBy()) + " has approved the pricing details."));
		html.append(String.format(EmailConstant.PRICING_HTML_BODY_ID, dalPricingHeader.getId()));
		/*html.append(String.format(EmailConstant.HTML_BODY_ACTION_TO_BE_TAKEN, 
				EmailConstant.APPROVAL_OR_REJECT));*/
		/*html.append(EmailConstant.HTML_BODY_LINK_TITLE);*/
		String link = null;
		String url = null;
		if(pricingHeader.getContextPath() != null && pricingHeader.getUrl() != null){
			int index = pricingHeader.getUrl().indexOf(pricingHeader.getContextPath());
			url = pricingHeader.getUrl().substring(0, index) + pricingHeader.getContextPath();
		}
		link = EmailConstant.LINK_BEGIN + url + EmailConstant.PRICING_LINK_PAGE + 
    			dalPricingHeader.getId() + EmailConstant.PRICING_LINK_END;
		html.append(link);
		html.append(EmailConstant.HTML_END);
		
		return html.toString();
	}

	private String buildContentForApprover(EmailDetails emailDetails, DalPricingHeader dalPricingHeader, PricingHeader pricingHeader) {
		StringBuilder html = new StringBuilder();
		
		html.append(EmailConstant.HTML_BEGIN);
		html.append(String.format(EmailConstant.HTML_BODY_GREETING, emailDetails.getToNames() + EmailConstant.COMMA) );
		html.append(String.format(EmailConstant.HTML_BODY_ACTION_TAKEN, 
				ProgramServiceHelper.getName(dalPricingHeader.getModifiedBy()) + " has submitted the pricing details for your approval."));
		html.append(String.format(EmailConstant.PRICING_HTML_BODY_ID, dalPricingHeader.getId()));
		html.append(String.format(EmailConstant.HTML_BODY_ACTION_TO_BE_TAKEN, 
				EmailConstant.APPROVAL_OR_REJECT));
		/*html.append(EmailConstant.HTML_BODY_LINK_TITLE);*/
		String link = null;
		String url = null;
		if(pricingHeader.getContextPath() != null && pricingHeader.getUrl() != null){
			int index = pricingHeader.getUrl().indexOf(pricingHeader.getContextPath());
			url = pricingHeader.getUrl().substring(0, index) + pricingHeader.getContextPath();
		}
    	link = EmailConstant.LINK_BEGIN + url + EmailConstant.PRICING_LINK_PAGE + 
    			dalPricingHeader.getId() + EmailConstant.PRICING_LINK_END;
    	
		html.append(link);
		html.append(EmailConstant.HTML_END);
		
		return html.toString();
	}

	private void buildSubject(EmailDetails emailDetails, DalPricingHeader dalPricingHeader, PricingHeader pricingHeader) {
		String subject = null;
		if(ProgramConstant.PENDING_STATUS.equalsIgnoreCase(dalPricingHeader.getDalStatus().getType())){
			subject = String.format(EmailConstant.PRICING_SUBJECT_PENDING, dalPricingHeader.getId());
		}
		else if(ProgramConstant.APPROVED_STATUS.equalsIgnoreCase(dalPricingHeader.getDalStatus().getType())){
			/**If control is here, then approval is done by TBP user who is level 3 user.*/
			subject = String.format(EmailConstant.PRICING_SUBJECT_APPROVAL, dalPricingHeader.getId());
		}
		else if(ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(dalPricingHeader.getDalStatus().getType())){
			subject = String.format(EmailConstant.PRICING_SUBJECT_REJECTED, dalPricingHeader.getId());
		}
		emailDetails.setSubject(subject);
	}

	/**
	 * Method to build the to addresses.
	 * @param emailDetails emailDetails
	 * @param dalProgramDetail dalProgramDetail
	 * @param programHeader programHeader
	 */
	private void buildToAndCCAddress(EmailDetails emailDetails, DalPricingHeader dalPricingHeader,
			PricingHeader pricingHeader) {
		List<String> toEmailIdList = new ArrayList<String>();
		List<String> ccEmailIdList = null;
		
		if(dalPricingHeader != null && pricingHeader != null){
			List<DalWorkflowStatus> dalWorkflowStatusList = dalPricingHeader.getDalWorkflowStatusForPricingList();
			Collections.sort(dalWorkflowStatusList, new WorkflowStatusComparatorByModifiedDate());
			if(dalWorkflowStatusList != null){
				if(ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(dalPricingHeader.getDalStatus().getType())){
					/**To and CC list are possible.*/
					toEmailIdList.add(dalPricingHeader.getCreatedBy().getEMAIL());
					appendToName(emailDetails, dalPricingHeader.getCreatedBy());
					for(DalWorkflowStatus workflowStatus : dalWorkflowStatusList){
						if(ProgramConstant.APPROVED_STATUS.equalsIgnoreCase(workflowStatus.getApprovalStatus().getType())){
							if(ccEmailIdList == null){
								ccEmailIdList = new ArrayList<String>();
							}
							if(!ccEmailIdList.contains(workflowStatus.getApprover().getEMAIL())){
								ccEmailIdList.add(workflowStatus.getApprover().getEMAIL());
							}
						
						}
					}
					
				}
				else if(ProgramConstant.PENDING_STATUS.equalsIgnoreCase(dalPricingHeader.getDalStatus().getType())){
					int size = dalWorkflowStatusList.size();
					DalWorkflowStatus dalWorkflowStatus = dalWorkflowStatusList.get(size-1);
					toEmailIdList.add(dalWorkflowStatus.getApprover().getEMAIL());
					appendToName(emailDetails, dalWorkflowStatus.getApprover());
				}
				else if(ProgramConstant.APPROVED_STATUS.equalsIgnoreCase(dalPricingHeader.getDalStatus().getType())){
					toEmailIdList.add(dalPricingHeader.getCreatedBy().getEMAIL());
					appendToName(emailDetails, dalPricingHeader.getCreatedBy());
					for(DalWorkflowStatus workflowStatus : dalWorkflowStatusList){
						/**Last updated person mail id should not be appended to the To list, so not equal to check is added in the below 
						 * condition. */
						if(ProgramConstant.APPROVED_STATUS.equalsIgnoreCase(workflowStatus.getApprovalStatus().getType()) &&
								!dalPricingHeader.getModifiedBy().getId().equals(workflowStatus.getApprover().getId())){
							if(!toEmailIdList.contains(workflowStatus.getApprover().getEMAIL())){
								toEmailIdList.add(workflowStatus.getApprover().getEMAIL());
								appendToName(emailDetails, workflowStatus.getApprover());
							}
						}
					}
				}
			}
			emailDetails.setToAddress(toEmailIdList);
			emailDetails.setCcAddress(ccEmailIdList);
		}
	}

	/**
	 * Method to get the cc name appended for the required user.	
	 * @param emailDetails  emailDetails
	 * @param approver approver
	 */
	private void appendToName(EmailDetails emailDetails, DalEmployee employee) {
		if(emailDetails != null && employee != null){
			if(emailDetails.getToNames() == null){
				emailDetails.setToNames(new StringBuilder());
			}
			if(!emailDetails.getToNames().toString().isEmpty()){
				emailDetails.getToNames().append(ProgramConstant.FORWARD_SLASH);
			}
			emailDetails.getToNames().append(ProgramServiceHelper.getName(employee));
		}
	}
}
