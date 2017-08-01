package com.ytc.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ytc.common.comparator.WorkflowStatusComparatorByModifiedDate;
import com.ytc.common.enums.ConsumerProgramStatusEnum;
import com.ytc.common.model.Employee;
import com.ytc.common.model.PricingHeader;
import com.ytc.common.model.ProgramWorkflowStatus;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.model.DalEmployeeHierarchy;
import com.ytc.dal.model.DalPricingHeader;
import com.ytc.dal.model.DalWorkflowStatus;

public class PricingWorkflowServiceHelper {

	public static void setProgramButtonProperties(Employee employee, PricingHeader pricingHeader,
			DalPricingHeader dalPricingHeader) {
		if(employee != null && pricingHeader != null && dalPricingHeader != null){
			/**
			 * 1.Current status of the program.
			 * 2.Current user level.
			 * 3.Set button enable/disable value accordingly
			 * */
			String programStatus = dalPricingHeader.getDalStatus().getType();
			ConsumerProgramStatusEnum consumerProgramStatusEnum = ConsumerProgramStatusEnum.getProgramStatus(programStatus);
			if(consumerProgramStatusEnum != null){
				pricingHeader.getProgramButton().setUserLevel(String.valueOf(consumerProgramStatusEnum.getUserLevel()));
				
				if(ConsumerProgramStatusEnum.INPROGRESS.getProgramStatus().equalsIgnoreCase(programStatus)){
					setInProgressBehaviour(pricingHeader, dalPricingHeader, employee);
					
					pricingHeader.setNewProgram(false);
				}
				else if(ConsumerProgramStatusEnum.PENDING.getProgramStatus().equalsIgnoreCase(programStatus)){
					setPendingManagerApprovalBehaviour(pricingHeader, dalPricingHeader, employee);
					pricingHeader.setNewProgram(false);
				}
				else if(ConsumerProgramStatusEnum.ACTIVE.getProgramStatus().equalsIgnoreCase(programStatus) ||  
						ConsumerProgramStatusEnum.APPROVED.getProgramStatus().equalsIgnoreCase(programStatus)){
					setApprovedBehaviour(pricingHeader, dalPricingHeader, employee);
					pricingHeader.setNewProgram(true);
				}
				else if(ConsumerProgramStatusEnum.REJECTED.getProgramStatus().equalsIgnoreCase(programStatus)){
					setRejectedBehaviour(pricingHeader, dalPricingHeader, employee);
					pricingHeader.setNewProgram(false);
				}
			}
		}
	}

	private static void setRejectedBehaviour(PricingHeader pricingHeader,
												DalPricingHeader dalPricingHeader,
												Employee employee) {
		pricingHeader.getProgramButton().setApprover(false);
		if(employee.getEMP_ID() != null && employee.getEMP_ID().equals(dalPricingHeader.getCreatedBy().getId()) ){
			pricingHeader.getProgramButton().setCreater(true);	
		}
		else{
			pricingHeader.getProgramButton().setCreater(false);
		}
	}

	private static void setApprovedBehaviour(PricingHeader pricingHeader,
												DalPricingHeader dalPricingHeader,
												Employee employee) {
		pricingHeader.getProgramButton().setApprover(false);
		if(employee.getEMP_ID() != null && employee.getEMP_ID().equals(dalPricingHeader.getCreatedBy().getId()) ){
			pricingHeader.getProgramButton().setCreater(true);	
		}
		else{
			pricingHeader.getProgramButton().setCreater(false);	
		}
	}

	private static void setPendingManagerApprovalBehaviour(PricingHeader pricingHeader,
															DalPricingHeader dalProgramHeader,
															Employee employee) {
		pricingHeader.getProgramButton().setCreater(false);	
		pricingHeader.getProgramButton().setApprover(false);
		
		if(employee.getEMP_ID() != null){
			
			if(dalProgramHeader.getDalWorkflowStatusForPricingList() != null && !dalProgramHeader.getDalWorkflowStatusForPricingList().isEmpty()){
				List<DalWorkflowStatus> dalWorkflowStatusList =  dalProgramHeader.getDalWorkflowStatusForPricingList(); 
				Collections.sort(dalWorkflowStatusList, new WorkflowStatusComparatorByModifiedDate());
				int size = dalWorkflowStatusList.size();
				DalWorkflowStatus dalWorkflowStatus = dalWorkflowStatusList.get(size-1);
				
				if(dalWorkflowStatus.getApprover() != null && (employee.getEMP_ID().equals(dalWorkflowStatus.getApprover().getId()))){
					pricingHeader.getProgramButton().setApprover(true);
				}
			}
		}
		
	}

	private static void setInProgressBehaviour(PricingHeader pricingHeader,
												DalPricingHeader dalPricingHeader,
												Employee employee) {
		pricingHeader.getProgramButton().setApprover(false);
		if(employee.getEMP_ID() != null && employee.getEMP_ID().equals(dalPricingHeader.getCreatedBy().getId()) ){
			pricingHeader.getProgramButton().setCreater(true);		
		}
		else{
			pricingHeader.getProgramButton().setCreater(false);	
		}
	}

	public static void setNewProgramButtonProperties(PricingHeader pricingHeader) {
		pricingHeader.getProgramButton().setUserLevel(ConsumerProgramStatusEnum.INPROGRESS.getUserLevel().toString());
		pricingHeader.getProgramButton().setApprover(false);
		pricingHeader.getProgramButton().setCreater(true);	
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
	
	/**
	 * populateWorkflowStatusData method is used to get the latest workflow status details.
	 * @param programHeader programHeader
	 * @param dalProgramDetail dalProgramDetail
	 */
	public static void populateWorkflowStatusData(PricingHeader pricingHeader, DalPricingHeader dalPricingHeader) {
		if(pricingHeader != null && dalPricingHeader != null){
			List<DalWorkflowStatus> dalWorkflowStatusList = dalPricingHeader.getDalWorkflowStatusForPricingList();
			if(dalWorkflowStatusList != null && !dalWorkflowStatusList.isEmpty()){
				
				if(pricingHeader.getProgramWorkflowStatusList() == null){
					pricingHeader.setProgramWorkflowStatusList(new ArrayList<ProgramWorkflowStatus>());
				}
				else{
					pricingHeader.getProgramWorkflowStatusList().clear();
				}
								
				/**Sorting*/
				Collections.sort(dalWorkflowStatusList, new Comparator<DalWorkflowStatus>() {
		
					@Override
					public int compare(DalWorkflowStatus one, DalWorkflowStatus two) {
						int val = 0;
						if(one.getId() == null){
							if(two.getId() != null){
								val = -1;
							}
							else{
								val = 0;
							}
						}
						else if(two.getId() == null){
							val = 1;
						}
						else{
							val = one.getId().compareTo(two.getId());
						}
						return val;
					}
					
				});
				Set<Integer> workflowIdSet = new HashSet<Integer>();
				for(DalWorkflowStatus dalWorkflowStatus : dalWorkflowStatusList){
					/**@ times, workflow status list from hibernate is having duplicate rows. 
					 * Below condition is to added to handle the duplicate rows.*/
					boolean isUnique = workflowIdSet.add(dalWorkflowStatus.getId());
					if(!isUnique){
						continue;
					}
					ProgramWorkflowStatus programWorkflowStatus = new ProgramWorkflowStatus();
					
					programWorkflowStatus.setApprovalDate(dalWorkflowStatus.getModifiedDate().getTime());
					programWorkflowStatus.setApproverName(ProgramServiceHelper.getName(dalWorkflowStatus.getApprover()));
					programWorkflowStatus.setApproverRole(dalWorkflowStatus.getApprover().getTITLE().getTitle());
					programWorkflowStatus.setStatus(dalWorkflowStatus.getApprovalStatus().getType());
					programWorkflowStatus.setComments(dalWorkflowStatus.getApprovalComment());
					
					pricingHeader.getProgramWorkflowStatusList().add(programWorkflowStatus);
				}
			}
		}
	}
	
	
	/**
	 * populateWorkflowStatusData method is used to get the latest workflow status details.
	 * @param programHeader programHeader
	 * @param dalProgramDetail dalProgramDetail
	 */
	public static void populateWorkflowStatusDataAfterUpdate(PricingHeader pricingHeader, List<DalWorkflowStatus> dalWorkflowStatusList) {
		if(pricingHeader != null && dalWorkflowStatusList != null && !dalWorkflowStatusList.isEmpty()){
			Set<Integer> workflowIdSet = new HashSet<Integer>();
			if(pricingHeader.getProgramWorkflowStatusList() == null){
				pricingHeader.setProgramWorkflowStatusList(new ArrayList<ProgramWorkflowStatus>());
			}
			else{
				pricingHeader.getProgramWorkflowStatusList().clear();
			}
			for(DalWorkflowStatus dalWorkflowStatus : dalWorkflowStatusList){
				/**@ times, workflow status list from hibernate is having duplicate rows. 
				 * Below condition is to added to handle the duplicate rows.*/
				boolean isUnique = workflowIdSet.add(dalWorkflowStatus.getId());
				if(!isUnique){
					continue;
				}
				ProgramWorkflowStatus programWorkflowStatus = new ProgramWorkflowStatus();
				
				programWorkflowStatus.setApprovalDate(dalWorkflowStatus.getModifiedDate().getTime());
				programWorkflowStatus.setApproverName(ProgramServiceHelper.getName(dalWorkflowStatus.getApprover()));
				programWorkflowStatus.setApproverRole(dalWorkflowStatus.getApprover().getTITLE().getTitle());
				programWorkflowStatus.setStatus(dalWorkflowStatus.getApprovalStatus().getType());
				programWorkflowStatus.setComments(dalWorkflowStatus.getApprovalComment());
				
				pricingHeader.getProgramWorkflowStatusList().add(programWorkflowStatus);
			}
		}
	}
}
