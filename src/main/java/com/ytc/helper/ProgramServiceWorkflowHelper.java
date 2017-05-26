package com.ytc.helper;

import com.ytc.common.enums.ConsumerProgramStatusEnum;
import com.ytc.common.model.Employee;
import com.ytc.common.model.ProgramHeader;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.model.DalProgramDetail;

public class ProgramServiceWorkflowHelper {

	public static void setProgramButtonProperties(Employee employee, ProgramHeader programHeader,
			DalProgramDetail dalProgramDetail) {
		if(employee != null && programHeader != null && dalProgramDetail != null){
			/**
			 * 1.Current status of the program.
			 * 2.Current user level.
			 * 3.Set button enable/disable value accordingly
			 * */
			String programStatus = dalProgramDetail.getStatus().getType();
			ConsumerProgramStatusEnum consumerProgramStatusEnum = ConsumerProgramStatusEnum.getProgramStatus(programStatus);
			if(consumerProgramStatusEnum != null){
				programHeader.getProgramButton().setUserLevel(String.valueOf(consumerProgramStatusEnum.getUserLevel()));
				
				if(ConsumerProgramStatusEnum.INPROGRESS.getProgramStatus().equalsIgnoreCase(programStatus)){
					setInProgressBehaviour(programHeader, dalProgramDetail, employee);
					
					programHeader.setNewProgram(false);
				}
				else if(ConsumerProgramStatusEnum.PENDING.getProgramStatus().equalsIgnoreCase(programStatus)){
					setPendingManagerApprovalBehaviour(programHeader, dalProgramDetail, employee);
					programHeader.setNewProgram(false);
				}
				else if(ConsumerProgramStatusEnum.ACTIVE.getProgramStatus().equalsIgnoreCase(programStatus) ||  
						ConsumerProgramStatusEnum.APPROVED.getProgramStatus().equalsIgnoreCase(programStatus)){
					setApprovedBehaviour(programHeader, dalProgramDetail, employee);
					programHeader.setNewProgram(true);
				}
				else if(ConsumerProgramStatusEnum.REJECTED.getProgramStatus().equalsIgnoreCase(programStatus)){
					setRejectedBehaviour(programHeader, dalProgramDetail, employee);
					programHeader.setNewProgram(false);
				}
			}
		}
	}

	private static void setRejectedBehaviour(ProgramHeader programHeader,
												DalProgramDetail dalProgramDetail,
												Employee employee) {
		programHeader.getProgramButton().setApprover(false);
		if(employee.getEMP_ID() != null && employee.getEMP_ID().equals(dalProgramDetail.getCreatedBy().getId()) ){
			programHeader.getProgramButton().setCreater(true);	
		}
		else{
			programHeader.getProgramButton().setCreater(false);
		}
	}

	private static void setApprovedBehaviour(ProgramHeader programHeader,
												DalProgramDetail dalProgramDetail,
												Employee employee) {
		programHeader.getProgramButton().setApprover(false);
		if(employee.getEMP_ID() != null && employee.getEMP_ID().equals(dalProgramDetail.getCreatedBy().getId()) ){
			programHeader.getProgramButton().setCreater(true);	
		}
		else{
			programHeader.getProgramButton().setCreater(false);	
		}
	}

	private static void setPendingManagerApprovalBehaviour(ProgramHeader programHeader,
															DalProgramDetail dalProgramDetail,
															Employee employee) {
		programHeader.getProgramButton().setCreater(false);	
		programHeader.getProgramButton().setApprover(false);
		
		if(employee.getEMP_ID() != null){
			if(employee.getEMP_ID().equals(dalProgramDetail.getZmAppById().getId())){
				programHeader.getProgramButton().setUserLevel(ProgramConstant.USER_LEVEL_2);
				if(dalProgramDetail.getZmAppDate() == null){
					programHeader.getProgramButton().setApprover(true);	
				}
				/** Hard coded below value is not correct. This should be dynamic. For now, hard coding this value.*/
				else if(ProgramConstant.PENDING_STATUS.equalsIgnoreCase(dalProgramDetail.getZmAppStatus().getType())){//PENDING
					programHeader.getProgramButton().setApprover(true);
				}
			}
			else if(employee.getEMP_ID().equals(dalProgramDetail.getTbpAppById().getId())){
				programHeader.getProgramButton().setUserLevel(ProgramConstant.USER_LEVEL_3);
				if(ProgramConstant.APPROVED_STATUS.equalsIgnoreCase(dalProgramDetail.getZmAppStatus().getType())){//APPROVED
					programHeader.getProgramButton().setApprover(true);
				}
			}
		}
		
	}

	private static void setInProgressBehaviour(ProgramHeader programHeader,
												DalProgramDetail dalProgramDetail,
												Employee employee) {
		programHeader.getProgramButton().setApprover(false);
		if(employee.getEMP_ID() != null && employee.getEMP_ID().equals(dalProgramDetail.getCreatedBy().getId()) ){
			programHeader.getProgramButton().setCreater(true);		
		}
		else{
			programHeader.getProgramButton().setCreater(false);	
		}
	}

	public static void setNewProgramButtonProperties(ProgramHeader programHeader) {
		programHeader.getProgramButton().setUserLevel(ConsumerProgramStatusEnum.INPROGRESS.getUserLevel().toString());
		programHeader.getProgramButton().setApprover(false);
		programHeader.getProgramButton().setCreater(true);	
	}

}
