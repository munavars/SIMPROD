package com.ytc.helper;

import java.util.Collections;
import java.util.List;

import com.ytc.common.comparator.WorkflowStatusComparatorByModifiedDate;
import com.ytc.common.enums.ConsumerProgramStatusEnum;
import com.ytc.common.model.Employee;
import com.ytc.common.model.ProgramHeader;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.model.DalEmployeeHierarchy;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalSimEmployeeHierarchy;
import com.ytc.dal.model.DalWorkflowStatus;

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
			
			if(dalProgramDetail.getDalWorkflowStatusList() != null && !dalProgramDetail.getDalWorkflowStatusList().isEmpty()){
				List<DalWorkflowStatus> dalWorkflowStatusList =  dalProgramDetail.getDalWorkflowStatusList(); 
				Collections.sort(dalWorkflowStatusList, new WorkflowStatusComparatorByModifiedDate());
				int size = dalWorkflowStatusList.size();
				DalWorkflowStatus dalWorkflowStatus = dalWorkflowStatusList.get(size-1);
				
				if(dalWorkflowStatus.getApprover() != null && (employee.getEMP_ID().equals(dalWorkflowStatus.getApprover().getId()))){
					programHeader.getProgramButton().setApprover(true);
				}
			}
			
/*			if(dalProgramDetail.getZmAppById() != null && (employee.getEMP_ID().equals(dalProgramDetail.getZmAppById().getId())) ){
				programHeader.getProgramButton().setUserLevel(ProgramConstant.USER_LEVEL_2);
				if(dalProgramDetail.getZmAppDate() == null){
					programHeader.getProgramButton().setApprover(true);	
				}
				*//** Hard coded below value is not correct. This should be dynamic. For now, hard coding this value.*//*
				else if(dalProgramDetail.getZmAppStatus() != null && ProgramConstant.PENDING_STATUS.equalsIgnoreCase(dalProgramDetail.getZmAppStatus().getType())){//PENDING
					programHeader.getProgramButton().setApprover(true);
				}
			}
			else if(dalProgramDetail.getTbpAppById() != null && employee.getEMP_ID().equals(dalProgramDetail.getTbpAppById().getId())){
				programHeader.getProgramButton().setUserLevel(ProgramConstant.USER_LEVEL_3);
				if(dalProgramDetail.getZmAppStatus() != null && ProgramConstant.APPROVED_STATUS.equalsIgnoreCase(dalProgramDetail.getZmAppStatus().getType())){//APPROVED
					programHeader.getProgramButton().setApprover(true);
				}
			}*/
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
	
	public static Integer getEmployeeIdFromHierachy(DalEmployeeHierarchy dalEmployeeHierarchy, Integer hierarchyLevel){
		Integer employeeId = null;
		
		if(dalEmployeeHierarchy != null){
			if(ProgramConstant.EMP_HIERARCHY_1 == hierarchyLevel){
				employeeId = Integer.valueOf(dalEmployeeHierarchy.getLvl1EmpId());
			}
			else if(ProgramConstant.EMP_HIERARCHY_2 == hierarchyLevel){
				employeeId = Integer.valueOf(dalEmployeeHierarchy.getLvl2EmpId());
			}
			else if(ProgramConstant.EMP_HIERARCHY_3 == hierarchyLevel){
				employeeId = Integer.valueOf(dalEmployeeHierarchy.getLvl3EmpId());
			}
			else if(ProgramConstant.EMP_HIERARCHY_4 == hierarchyLevel){
				employeeId = Integer.valueOf(dalEmployeeHierarchy.getLvl4EmpId());
			}
			else if(ProgramConstant.EMP_HIERARCHY_5 == hierarchyLevel){
				employeeId = Integer.valueOf(dalEmployeeHierarchy.getLvl5EmpId());
			}
		}
		
		return employeeId;
	}
	
	public static Integer getEmployeeIdFromHierachy(DalSimEmployeeHierarchy dalEmployeeHierarchy, Integer hierarchyLevel){
		Integer employeeId = null;
		
		if(dalEmployeeHierarchy != null){
			if(ProgramConstant.EMP_HIERARCHY_1 == hierarchyLevel){
				employeeId = Integer.valueOf(dalEmployeeHierarchy.getLvl1EmpId());
			}
			else if(ProgramConstant.EMP_HIERARCHY_2 == hierarchyLevel){
				employeeId = Integer.valueOf(dalEmployeeHierarchy.getLvl2EmpId());
			}
			else if(ProgramConstant.EMP_HIERARCHY_3 == hierarchyLevel){
				employeeId = Integer.valueOf(dalEmployeeHierarchy.getLvl3EmpId());
			}
			else if(ProgramConstant.EMP_HIERARCHY_4 == hierarchyLevel){
				employeeId = Integer.valueOf(dalEmployeeHierarchy.getLvl4EmpId());
			}
			else if(ProgramConstant.EMP_HIERARCHY_5 == hierarchyLevel){
				employeeId = Integer.valueOf(dalEmployeeHierarchy.getLvl5EmpId());
			}
		}
		
		return employeeId;
	}
}
