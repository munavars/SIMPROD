package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.comparator.WorkflowStatusComparatorByModifiedDate;
import com.ytc.common.model.Employee;
import com.ytc.common.model.PricingHeader;
import com.ytc.common.model.ProgramWorkflowMatrixDetail;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalEmployee;
import com.ytc.dal.model.DalEmployeeHierarchy;
import com.ytc.dal.model.DalPricingHeader;
import com.ytc.dal.model.DalStatus;
import com.ytc.dal.model.DalWorkflowMatrix;
import com.ytc.dal.model.DalWorkflowStatus;
import com.ytc.helper.ProgramServiceWorkflowHelper;
import com.ytc.service.IPricingWorkflowService;

/**
 * Class - PricingWorkflowServiceImpl Purpose - This class contains method
 * related to work low updates.
 * 
 * @author Cognizant.
 *
 */
public class PricingWorkflowServiceImpl implements IPricingWorkflowService {

	@Autowired
	private IDataAccessLayer baseDao;

	@Override
	public void updateWorkflowDetails(DalPricingHeader dalPricingHeader, PricingHeader pricingHeader,
			Employee employee) {
		List<DalStatus> dalStatusList = baseDao.getListFromNamedQuery("DalStatus.getAllDetails");

		/**
		 * Only if the user is submitting, then get the next approver from
		 * WorkFlow matrix table and make an entry in WorkFlow status table.
		 */
		if (!ProgramConstant.IN_PROGRESS_STATUS.equals(dalPricingHeader.getDalStatus().getType())) {
			/***
			 * 1. Get WorkFlow Matrix 2. Get WorkFlow status entries (latest
			 * entry with Pending status has to be updated with Approved or
			 * Rejected status.) 3. Update WorkFlow Matrix
			 */
			List<DalWorkflowStatus> dalWorkflowStatusList = dalPricingHeader.getDalWorkflowStatusForPricingList();
			getWorkflowStatusListInOrder(dalWorkflowStatusList);
			if (dalWorkflowStatusList != null && !dalWorkflowStatusList.isEmpty()) {
				/**
				 * If we already have any approver list in workflow status list,
				 * then we have to find the next approver from workflow matrix
				 */
				int size = dalWorkflowStatusList.size();
				DalWorkflowStatus dalWorkflowStatus = dalWorkflowStatusList.get(size - 1);
				/**
				 * This condition will eliminate the need of updating the
				 * existing status during re submission of request by the
				 * creator.
				 */
				if (ProgramConstant.PENDING_STATUS.equalsIgnoreCase(dalWorkflowStatus.getApprovalStatus().getType())) {
					dalWorkflowStatus.setApprovalComment(pricingHeader.getApproverComments());
					String decision = ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(
							dalPricingHeader.getDalStatus().getType()) ? ProgramConstant.N : ProgramConstant.Y;
					dalWorkflowStatus.setDecisionMade(decision);
					if (ProgramConstant.Y.equals(decision)) {
						for (DalStatus dalStatus : dalStatusList) {
							if (ProgramConstant.APPROVED_STATUS.equals(dalStatus.getType())) {
								dalWorkflowStatus.setApprovalStatus(dalStatus);
								break;
							}
						}

					} else if (ProgramConstant.N.equals(decision)) {
						/**
						 * If it is N, then it will be rejected. Hence directly
						 * updating the value from Detail entity.
						 */
						dalWorkflowStatus.setApprovalStatus(dalPricingHeader.getDalStatus());
					}
					dalWorkflowStatus.setWfStatusDate(Calendar.getInstance());
				}
			}
			if (!(ProgramConstant.REJECTED_STATUS.equalsIgnoreCase(dalPricingHeader.getDalStatus().getType()))) {
				/**
				 * Only if it s not rejected, next approver details will be
				 * updated.
				 */
				getNextApproverFromMatrix(dalPricingHeader, pricingHeader, employee, dalStatusList);
			}
		}
	}

	private void getNextApproverFromMatrix(DalPricingHeader dalPricingHeader, PricingHeader pricingHeader,
			Employee employee, List<DalStatus> dalStatusList) {

		ProgramWorkflowMatrixDetail programWorkflowMatrixDetail = null;

		programWorkflowMatrixDetail = getWorkflowMatrixManipulatedData(dalPricingHeader, employee, dalStatusList, pricingHeader);

		if (programWorkflowMatrixDetail != null) {

			if (programWorkflowMatrixDetail.getCurrentUserLevel() == programWorkflowMatrixDetail.getTotalLevel()) {
				/**
				 * There is no level after this. This is the final action , if
				 * it approved.
				 */
				for (DalStatus dalStatus : dalStatusList) {
					if (ProgramConstant.APPROVED_STATUS.equals(dalStatus.getType())) {
						dalPricingHeader.setDalStatus(dalStatus);
						break;
					}
				}
			} else if (programWorkflowMatrixDetail.getCurrentUserLevel() < programWorkflowMatrixDetail
					.getTotalLevel()) {
				int nextUserLevel = programWorkflowMatrixDetail.getCurrentUserLevel() + 1;
				Integer[] nextApprover = null;
				if (programWorkflowMatrixDetail.getApproverLevelList().get(nextUserLevel) != null) {
					nextApprover = programWorkflowMatrixDetail.getApproverLevelList().get(nextUserLevel);
				}

				if (nextApprover != null) {
					/** new Row has to be updated */
					DalWorkflowStatus dalWorkflowStatus = new DalWorkflowStatus();

					for (DalStatus dalStatus : dalStatusList) {
						if (ProgramConstant.PENDING_STATUS.equals(dalStatus.getType())) {
							dalWorkflowStatus.setApprovalStatus(dalStatus);
							break;
						}
					}
					dalWorkflowStatus.setApprover(baseDao.getById(DalEmployee.class, nextApprover[0]));
					dalWorkflowStatus.setWfStatusDate(Calendar.getInstance());
					dalWorkflowStatus.setWfMatrixId(nextApprover[1]);
					dalWorkflowStatus.setDalProgramType(dalPricingHeader.getDalProgramType());
					dalWorkflowStatus.setDalPricingHeaderWf(dalPricingHeader);
					if (dalPricingHeader.getDalWorkflowStatusForPricingList() == null) {
						dalPricingHeader.setDalWorkflowStatusForPricingList(new ArrayList<DalWorkflowStatus>());
					}
					dalPricingHeader.getDalWorkflowStatusForPricingList().add(dalWorkflowStatus);
				}
			}
		}

	}

	private ProgramWorkflowMatrixDetail getWorkflowMatrixManipulatedData(DalPricingHeader dalPricingHeader,
			Employee employee, List<DalStatus> dalStatusList, PricingHeader pricingHeader) {

		ProgramWorkflowMatrixDetail programWorkflowMatrixDetail = null;
		Map<String, Object> inputParameters = new HashMap<String, Object>();
		inputParameters.put("programType", "%" + dalPricingHeader.getDalProgramType().getType() + "%");
		inputParameters.put("businessUnit", dalPricingHeader.getCustomer().getBu());

		List<DalWorkflowMatrix> dalWorkflowMatrixList = baseDao
				.getListFromNamedQueryWithParameter("DalWorkflowMatrix.getMatrixForBU", inputParameters);

		if (dalWorkflowMatrixList != null && !dalWorkflowMatrixList.isEmpty()) {
			programWorkflowMatrixDetail = new ProgramWorkflowMatrixDetail();
			Integer employeeId = null;
			if (dalPricingHeader.getCreatedBy() != null) {
				employeeId = dalPricingHeader.getCreatedBy().getId();
			} else {
				employeeId = employee.getEMP_ID();
			}
			DalEmployeeHierarchy dalEmployeeHierarchy = baseDao.getEntityById(DalEmployeeHierarchy.class, employeeId);
			int currentUserLevel = 0;
			int userLevel = 0;
			Integer[] value = null;
			boolean skipRecord = false;
			for (DalWorkflowMatrix workflowMatrix : dalWorkflowMatrixList) {
				
				skipRecord = checkForException(workflowMatrix, dalPricingHeader);
				if(skipRecord){
					continue;
				}
				userLevel++;
				if (workflowMatrix.getHierarchyLevel() != null) {
					value = new Integer[2];
					Integer currentUserEmployeeId = ProgramServiceWorkflowHelper
							.getEmployeeIdFromHierachy(dalEmployeeHierarchy, workflowMatrix.getHierarchyLevel());
					value[0] = currentUserEmployeeId;
					value[1] = workflowMatrix.getId();
					programWorkflowMatrixDetail.getApproverLevelList().put(userLevel, value);
					if (employee.getEMP_ID().equals(currentUserEmployeeId)) {
						currentUserLevel = userLevel;
					}
				} else if (workflowMatrix.getEmpId() != null) {
					value = new Integer[2];
					value[0] = workflowMatrix.getEmpId();
					value[1] = workflowMatrix.getId();
					programWorkflowMatrixDetail.getApproverLevelList().put(userLevel, value);
					if (employee.getEMP_ID().equals(workflowMatrix.getEmpId())) {
						currentUserLevel = userLevel;
					}
				}
			}
			/**
			 * Below condition is added for defect id - YR-196.
			 * Same person can be a approver.
			 */
			if(pricingHeader.getProgramButton().isCreater()){
				currentUserLevel = 0;
			}
			programWorkflowMatrixDetail.setCurrentUserLevel(currentUserLevel);
			programWorkflowMatrixDetail.setTotalLevel(userLevel);
		}
		return programWorkflowMatrixDetail;
	}

	private boolean checkForException(DalWorkflowMatrix workflowMatrix, DalPricingHeader dalPricingHeader) {
		boolean skipRecord = false;
		
		if(workflowMatrix != null && workflowMatrix.getDalWorkflowExceptionMatrix() != null && dalPricingHeader != null){
			if(!workflowMatrix.getDalWorkflowExceptionMatrix().getCustomerNumbers().contains(dalPricingHeader.getCustomer().getCustomerNumber())){
				skipRecord = true;
			}
		}
		return skipRecord;
	}

	/**
	 * Method is used to sort the DalWorkFlowStatus object based on its modified
	 * date.
	 * 
	 * @param dalWorkflowStatusList
	 *            dalWorkflowStatusList
	 */
	private void getWorkflowStatusListInOrder(List<DalWorkflowStatus> dalWorkflowStatusList) {
		if (dalWorkflowStatusList != null && !dalWorkflowStatusList.isEmpty()) {
			Collections.sort(dalWorkflowStatusList, new WorkflowStatusComparatorByModifiedDate());
		}

	}

}
