package com.ytc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.comparator.WorkflowStatusComparatorByModifiedDate;
import com.ytc.common.enums.BusinessUnitDescriptionEnum;
import com.ytc.common.model.Employee;
import com.ytc.common.model.ProgramHeader;
import com.ytc.common.model.ProgramWorkflowMatrixDetail;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalEmployee;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalSimEmployeeHierarchy;
import com.ytc.dal.model.DalSimWorkflowMatrix;
import com.ytc.dal.model.DalStatus;
import com.ytc.dal.model.DalWorkflowStatus;
import com.ytc.helper.ProgramServiceWorkflowHelper;
import com.ytc.service.IProgramWorkflowService;

/**
 * Class - ProgramWorkflowServiceImpl
 * Purpose - This class contains method related to workflow updates.
 * @author Cognizant.
 *
 */
public class ProgramWorkflowServiceImpl implements IProgramWorkflowService{

	@Autowired
	private IDataAccessLayer baseDao;
	
	/**
	 * Method to update the WorkFlow details.
	 * @param dalProgramDet dalProgramDet.
	 * @param programHeader programHeader.
	 * @param employee employee.
	 */
	@Override
	public void updateWorkflowDetails(DalProgramDetail dalProgramDet, ProgramHeader programHeader, Employee employee) {
		if(dalProgramDet != null && programHeader != null && employee != null){
			
			List<DalStatus> dalStatusList =  baseDao.getListFromNamedQuery("DalStatus.getAllDetails");
			
			/**Only if the user is submitting, then get the next approver from
			 * WorkFlow matrix table and make an entry in WorkFlow status table. */
			if(!ProgramConstant.IN_PROGRESS_STATUS.equals(dalProgramDet.getStatus().getType())){
				/***
				 * 1. Get WorkFlow Matrix
				 * 2. Get WorkFlow status entries (latest entry with Pending status has to be updated with Approved or Rejected status.)
				 * 3. Update WorkFlow Matrix
				 */
				List<DalWorkflowStatus> dalWorkflowStatusList = dalProgramDet.getDalWorkflowStatusList();
				getWorkflowStatusListInOrder(dalWorkflowStatusList);
				if(dalWorkflowStatusList != null && !dalWorkflowStatusList.isEmpty()){
					/**If we already have any approver list in workflow status list, then
					 * we have to find the next approver from workflow matrix*/
					int size = dalWorkflowStatusList.size();
					DalWorkflowStatus dalWorkflowStatus = dalWorkflowStatusList.get(size-1);
					/**This condition will eliminate the need of updating the existing status during resubmission of request
					 * by the creator.*/
					if(ProgramConstant.PENDING_STATUS.equalsIgnoreCase(dalWorkflowStatus.getApprovalStatus().getType())){
						dalWorkflowStatus.setApprovalComment(programHeader.getUserComments());
						String decision = ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(dalProgramDet.getStatus().getType()) ? ProgramConstant.N : ProgramConstant.Y;
						dalWorkflowStatus.setDecisionMade(decision);
						if(ProgramConstant.Y.equals(decision)){
							for(DalStatus dalStatus : dalStatusList){
								if(ProgramConstant.APPROVED_STATUS.equals(dalStatus.getType())){
									dalWorkflowStatus.setApprovalStatus(dalStatus);
									break;
								}
							}
							
						}
						else if(ProgramConstant.N.equals(decision)){
							/**If it is N, then it will be rejected. Hence directly updating the value from Detail entity.*/
							dalWorkflowStatus.setApprovalStatus(dalProgramDet.getStatus());
						}
						dalWorkflowStatus.setWfStatusDate(Calendar.getInstance());						
					}
				}
				if(!(ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(dalProgramDet.getStatus().getType()))){
					/**Only if it s not rejected, next approver details will be updated.*/
					getNextApproverFromMatrix(dalProgramDet, programHeader, employee, dalStatusList);
				}
			}
		}
	}

	private void getNextApproverFromMatrix(DalProgramDetail dalProgramDetail,
			ProgramHeader programHeader, Employee employee, List<DalStatus> dalStatusList) {
		
		ProgramWorkflowMatrixDetail programWorkflowMatrixDetail = null;
				
		programWorkflowMatrixDetail = getWorkflowMatrixManipualtedData(dalProgramDetail, employee, dalStatusList, programHeader);
		
		if(programWorkflowMatrixDetail != null){
			
			if(programWorkflowMatrixDetail.getCurrentUserLevel() == programWorkflowMatrixDetail.getTotalLevel()){
				/**There is no level after this. This is the final action , if it approved.*/
				for(DalStatus dalStatus : dalStatusList){
					if(ProgramConstant.APPROVED_STATUS.equals(dalStatus.getType())){
						dalProgramDetail.setStatus(dalStatus);
						dalProgramDetail.getDalProgramHeader().setStatus(dalStatus);
						break;
					}
				}
			}
			else if(programWorkflowMatrixDetail.getCurrentUserLevel()  < programWorkflowMatrixDetail.getTotalLevel()){
				int nextUserLevel = programWorkflowMatrixDetail.getCurrentUserLevel() + 1;
				Integer[] nextApprover = null;
				if(programWorkflowMatrixDetail.getApproverLevelList().get(nextUserLevel) != null){
					nextApprover = programWorkflowMatrixDetail.getApproverLevelList().get(nextUserLevel);
				}
				
				if(nextApprover != null){
					/**new Row has to be updated*/
					DalWorkflowStatus dalWorkflowStatus = new DalWorkflowStatus();
					
					for(DalStatus dalStatus : dalStatusList){
						if(ProgramConstant.PENDING_STATUS.equals(dalStatus.getType())){
							dalWorkflowStatus.setApprovalStatus(dalStatus);
							break;
						}
					}
					dalWorkflowStatus.setApprover(baseDao.getById(DalEmployee.class, nextApprover[0]));
					dalWorkflowStatus.setWfStatusDate(Calendar.getInstance());
					dalWorkflowStatus.setWfMatrixId(nextApprover[1]);
					dalWorkflowStatus.setDalProgramType(dalProgramDetail.getDalProgramType());
					dalWorkflowStatus.setDalProgramDetailWf(dalProgramDetail);
					if(dalProgramDetail.getDalWorkflowStatusList() == null){
						dalProgramDetail.setDalWorkflowStatusList(new ArrayList<DalWorkflowStatus>());
					}
					dalProgramDetail.getDalWorkflowStatusList().add(dalWorkflowStatus);
				}
			}		
		}		

	}

	private ProgramWorkflowMatrixDetail getWorkflowMatrixManipualtedData(DalProgramDetail dalProgramDetail, Employee employee,
																		List<DalStatus> dalStatusList, ProgramHeader programHeader) {
		
		ProgramWorkflowMatrixDetail programWorkflowMatrixDetail = null;
		Map<String, Object> inputParameters = new HashMap<String, Object>();
		inputParameters.put("businessUnit", String.valueOf(BusinessUnitDescriptionEnum.getBUDescription(dalProgramDetail.getDalProgramHeader().getBu())) );
		
		List<DalSimWorkflowMatrix> dalSimWorkflowMatrixList =  baseDao.getListFromNamedQueryWithParameter("DalSimWorkflowMatrix.getMatrixForBU", 
																										inputParameters);
		
		if(dalSimWorkflowMatrixList != null && !dalSimWorkflowMatrixList.isEmpty()){
			programWorkflowMatrixDetail = new ProgramWorkflowMatrixDetail();
			Integer employeeId = null;
			if(dalProgramDetail.getCreatedBy() != null){
				employeeId = dalProgramDetail.getCreatedBy().getId(); 
			}
			else {
				employeeId = employee.getEMP_ID();
			}
			inputParameters.clear();
			inputParameters.put("empId", String.valueOf( employeeId) );
			inputParameters.put("businessUnit", String.valueOf(BusinessUnitDescriptionEnum.getBUDescription(dalProgramDetail.getDalProgramHeader().getBu())) );
			
			/*Here for sure, there should be a record present in employee hierarchy. */
			List<DalSimEmployeeHierarchy> dalSimEmployeeHierarchyList = baseDao.getListFromNamedQueryWithParameter("DalSimEmployeeHierarchy.getHierarchyListBasedOnIdAndBU", 
																											inputParameters);
			
			DalSimEmployeeHierarchy dalSimEmployeeHierarchy = dalSimEmployeeHierarchyList.get(0);
			int currentUserLevel = 0;
			int userLevel = 0;
			Integer[] value = null;
			/*boolean skipRecord = false;*/
			for(DalSimWorkflowMatrix simWorkflowMatrix : dalSimWorkflowMatrixList){
				
				/*if(skipRecord && !ProgramConstant.NO_LIMIT.equalsIgnoreCase(simWorkflowMatrix.getDollarLimit())){
					continue;
				}*/
				if(!ProgramConstant.NO_LIMIT.equalsIgnoreCase(simWorkflowMatrix.getDollarLimit())){
					boolean isMatchingApprover = checkDollarLimit(simWorkflowMatrix, dalProgramDetail);
					if(!isMatchingApprover){
						continue;
					}
					/*else{
						continue;
					}*/
				}
				/*else{
					skipRecord = false;
				}*/
				
				if(simWorkflowMatrix.getHierarchyLevel() != null){
					value = new Integer[2]; 
					Integer currentUserEmployeeId = ProgramServiceWorkflowHelper.getEmployeeIdFromHierachy(dalSimEmployeeHierarchy, simWorkflowMatrix.getHierarchyLevel());
					if (employeeId != null && currentUserEmployeeId != null && employeeId.equals(currentUserEmployeeId)){
						continue;
					}
					else if (currentUserEmployeeId == null){
						continue;
					}
					value[0] = currentUserEmployeeId;
					value[1] = simWorkflowMatrix.getId();
					userLevel++;
					programWorkflowMatrixDetail.getApproverLevelList().put(userLevel, value);
					if(employee.getEMP_ID().equals(currentUserEmployeeId)){
						currentUserLevel = userLevel;
					}
				}
				else if(simWorkflowMatrix.getEmpId() != null){
					value = new Integer[2]; 
					value[0] = simWorkflowMatrix.getEmpId();
					value[1] = simWorkflowMatrix.getId();
					userLevel++;
					programWorkflowMatrixDetail.getApproverLevelList().put(userLevel, value);
					if(employee.getEMP_ID().equals(simWorkflowMatrix.getEmpId())){
						currentUserLevel = userLevel;
					}
				}
			}
			/**
			 * Below condition is added for defect id - YR-196.
			 * Same person can be a approver.
			 */
			if(programHeader.getProgramButton().isCreater()){
				currentUserLevel = 0;
			}
			programWorkflowMatrixDetail.setCurrentUserLevel(currentUserLevel);
			programWorkflowMatrixDetail.setTotalLevel(userLevel);
		}
		return programWorkflowMatrixDetail;
	}
	
	private boolean checkDollarLimit(DalSimWorkflowMatrix dalSimWorkflowMatrix, DalProgramDetail dalProgramDetail) {
		boolean isMatching = false;
		if(dalSimWorkflowMatrix != null && dalProgramDetail != null ){
			String dollerLimit = dalSimWorkflowMatrix.getDollarLimit();
			int numericIndex = 0;
			if(dollerLimit != null){
				/**
				 * Since, we have no clue @ what position amount will start, 
				 * below logic checks for first digit occurrence and based on that, operator and amount is separated. 
				 */
				for(int i = 0; i < dollerLimit.length(); i++){
					if(Character.isDigit(dollerLimit.charAt(i))){
						numericIndex = i;
						break;
					}
				}
				String comparisonOperator = dollerLimit.substring(0, numericIndex).trim();
				BigDecimal amount = new BigDecimal(dollerLimit.substring(numericIndex, dollerLimit.length())); 
				
				if(ProgramConstant.OPERATOR_G.equals(comparisonOperator)){
					BigDecimal userAmount = new BigDecimal(dalProgramDetail.getEstimatedAccrual());
					if( userAmount.compareTo(amount) > 0){
						isMatching = true;
					}
				}
				else if(ProgramConstant.OPERATOR_GE.contains(comparisonOperator)){
					BigDecimal userAmount = new BigDecimal(dalProgramDetail.getEstimatedAccrual());
					if( userAmount.compareTo(amount) >= 0){
						isMatching = true;
					}
				}
				else if(ProgramConstant.OPERATOR_LE.contains(comparisonOperator)){
					BigDecimal userAmount = new BigDecimal(dalProgramDetail.getEstimatedAccrual());
					if( userAmount.compareTo(amount) <= 0){
						isMatching = true;
					}					
				}
			}
		}
		return isMatching;
	}
	
	/*private BigDecimal getPreviousDollarValue(String previousDollarLimitStr){
		int numericIndex = 0;
		BigDecimal amount = new BigDecimal(0);
		if(previousDollarLimitStr != null){
			*//**
			 * Since, we have no clue @ what position amount will start, 
			 * below logic checks for first digit occurrence and based on that, operator and amount is separated. 
			 *//*
			for(int i = 0; i < previousDollarLimitStr.length(); i++){
				if(Character.isDigit(previousDollarLimitStr.charAt(i))){
					numericIndex = i;
					break;
				}
			}
			amount = new BigDecimal(previousDollarLimitStr.substring(numericIndex, previousDollarLimitStr.length())); 
			
		}
		return amount;
	}*/

	/**
	 * Method is used to sort the DalWorkFlowStatus object based on its modified date.
	 * @param dalWorkflowStatusList dalWorkflowStatusList
	 */
	private void getWorkflowStatusListInOrder(List<DalWorkflowStatus> dalWorkflowStatusList) {
		if(dalWorkflowStatusList != null && !dalWorkflowStatusList.isEmpty()){
			Collections.sort(dalWorkflowStatusList, new WorkflowStatusComparatorByModifiedDate());	
		}
		
	}

}
