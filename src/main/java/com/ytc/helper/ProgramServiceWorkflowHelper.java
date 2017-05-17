package com.ytc.helper;

import com.ytc.common.enums.ConsumerProgramStatusEnum;
import com.ytc.common.enums.ConsumerUserLevelEnum;
import com.ytc.common.model.ProgramHeader;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.model.DalEmployeeTitle;
import com.ytc.dal.model.DalProgramDetail;

public class ProgramServiceWorkflowHelper {

	public static void setProgramButtonProperties(DalEmployeeTitle dalEmployeeTitle, ProgramHeader programHeader,
			DalProgramDetail dalProgramDetail) {
		if(dalEmployeeTitle != null && programHeader != null && dalProgramDetail != null){
			/**
			 * 1.Current status of the program.
			 * 2.Current user level.
			 * 3.Set button enable/disable value accordingly
			 * */
			Integer loggedInUserLevel = null;
			String programStatus = dalProgramDetail.getStatus().getType();
			ConsumerUserLevelEnum userLevelEnum = ConsumerUserLevelEnum.getUserLevel(dalEmployeeTitle.getTitle());
			if(userLevelEnum != null){
				programHeader.getProgramButton().setUserLevel(String.valueOf(userLevelEnum.getLevel()));
				loggedInUserLevel = userLevelEnum.getLevel();
			}
			ConsumerProgramStatusEnum consumerProgramStatusEnum = ConsumerProgramStatusEnum.getProgramStatus(programStatus);
			if(consumerProgramStatusEnum != null && loggedInUserLevel != null){
				if(ConsumerProgramStatusEnum.INPROGRESS.getProgramStatus().equalsIgnoreCase(programStatus)){
					setInProgressBehaviour(programHeader, consumerProgramStatusEnum, loggedInUserLevel);
				}
				else if(ConsumerProgramStatusEnum.PENDING.getProgramStatus().equalsIgnoreCase(programStatus)){
					setPendingManagerApprovalBehaviour(programHeader, consumerProgramStatusEnum, loggedInUserLevel, dalProgramDetail);
				}
/*				else if(ConsumerProgramStatusEnum.PENDING_TBP_APPROVAL.getProgramStatus().equalsIgnoreCase(programStatus)){
					setPendingTBPApprovalBehaviour(programHeader, consumerProgramStatusEnum, loggedInUserLevel);
				}*/
				else if(ConsumerProgramStatusEnum.ACTIVE.getProgramStatus().equalsIgnoreCase(programStatus)){
					setApprovedBehaviour(programHeader, consumerProgramStatusEnum, loggedInUserLevel);
					programHeader.setNewProgram(true);
				}
				else if(ConsumerProgramStatusEnum.REJECTED_BY_MANAGER.getProgramStatus().equalsIgnoreCase(programStatus)){
					setRejectedBehaviour(programHeader, consumerProgramStatusEnum, loggedInUserLevel);
				}
				else if(ConsumerProgramStatusEnum.REJECTED_BY_TBP.getProgramStatus().equalsIgnoreCase(programStatus)){
					setRejectedBehaviour(programHeader, consumerProgramStatusEnum, loggedInUserLevel);
				}
			}
		}
	}

	private static void setRejectedBehaviour(ProgramHeader programHeader,
			ConsumerProgramStatusEnum consumerProgramStatusEnum, Integer loggedInUserLevel) {
		programHeader.getProgramButton().setCancel(true);
		programHeader.getProgramButton().setApprove(false);
		programHeader.getProgramButton().setReject(false);
		if(loggedInUserLevel.equals(consumerProgramStatusEnum.getUserLevel())){
			programHeader.getProgramButton().setEdit(true);
			programHeader.getProgramButton().setSave(true);
			programHeader.getProgramButton().setSubmit(true);	
		}
		else{
			programHeader.getProgramButton().setEdit(false);
			programHeader.getProgramButton().setSave(false);
			programHeader.getProgramButton().setSubmit(false);
		}
	}

	private static void setApprovedBehaviour(ProgramHeader programHeader,
			ConsumerProgramStatusEnum consumerProgramStatusEnum, Integer loggedInUserLevel) {
		programHeader.getProgramButton().setCancel(true);
		programHeader.getProgramButton().setApprove(false);
		programHeader.getProgramButton().setReject(false);
		if(loggedInUserLevel.equals(consumerProgramStatusEnum.getUserLevel())){
			programHeader.getProgramButton().setEdit(true);
			programHeader.getProgramButton().setSave(true);
			programHeader.getProgramButton().setSubmit(true);	
		}
		else{
			programHeader.getProgramButton().setEdit(false);
			programHeader.getProgramButton().setSave(false);
			programHeader.getProgramButton().setSubmit(false);
		}
	}

/*	private static void setPendingTBPApprovalBehaviour(ProgramHeader programHeader,
			ConsumerProgramStatusEnum consumerProgramStatusEnum, Integer loggedInUserLevel) {
		programHeader.getProgramButton().setCancel(true);
		programHeader.getProgramButton().setEdit(false);
		programHeader.getProgramButton().setSave(false);
		programHeader.getProgramButton().setSubmit(false);
		if(loggedInUserLevel.equals(consumerProgramStatusEnum.getUserLevel())){
			programHeader.getProgramButton().setApprove(true);
			programHeader.getProgramButton().setReject(true);
		}
		else{
			programHeader.getProgramButton().setApprove(false);
			programHeader.getProgramButton().setReject(false);
		}
	}
*/
	private static void setPendingManagerApprovalBehaviour(ProgramHeader programHeader,
			ConsumerProgramStatusEnum consumerProgramStatusEnum, Integer loggedInUserLevel,
			DalProgramDetail dalProgramDetail) {
		programHeader.getProgramButton().setCancel(true);
		programHeader.getProgramButton().setEdit(false);
		programHeader.getProgramButton().setSave(false);
		programHeader.getProgramButton().setSubmit(false);
		if(loggedInUserLevel.equals(consumerProgramStatusEnum.getUserLevel()) && dalProgramDetail.getZmAppById() == null){
			programHeader.getProgramButton().setApprove(true);
			programHeader.getProgramButton().setReject(true);
		}
		else if(dalProgramDetail.getZmAppById() != null && ProgramConstant.USER_LEVEL_3.equals(loggedInUserLevel) && dalProgramDetail.getTbpAppById() == null) {
			programHeader.getProgramButton().setApprove(true);
			programHeader.getProgramButton().setReject(true);
		}
		else{
			programHeader.getProgramButton().setApprove(false);
			programHeader.getProgramButton().setReject(false);
		}
		
	}

	private static void setInProgressBehaviour(ProgramHeader programHeader,
			ConsumerProgramStatusEnum consumerProgramStatusEnum, Integer loggedInUserLevel) {
		programHeader.getProgramButton().setCancel(true);
		programHeader.getProgramButton().setApprove(false);
		programHeader.getProgramButton().setReject(false);
		if(loggedInUserLevel.equals(consumerProgramStatusEnum.getUserLevel())){
			programHeader.getProgramButton().setEdit(true);
			programHeader.getProgramButton().setSave(true);
			programHeader.getProgramButton().setSubmit(true);	
		}
		else{
			programHeader.getProgramButton().setEdit(false);
			programHeader.getProgramButton().setSave(false);
			programHeader.getProgramButton().setSubmit(false);
		}
	}

	public static void setNewProgramButtonProperties(ProgramHeader programHeader) {
		programHeader.getProgramButton().setUserLevel(ConsumerProgramStatusEnum.INPROGRESS.getUserLevel().toString());
		programHeader.getProgramButton().setCancel(true);
		programHeader.getProgramButton().setApprove(false);
		programHeader.getProgramButton().setReject(false);
		programHeader.getProgramButton().setEdit(true);
		programHeader.getProgramButton().setSave(true);
		programHeader.getProgramButton().setSubmit(true);
	}

}
