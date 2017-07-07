package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.ProgramAchieveOn;
import com.ytc.common.model.ProgramDetail;
import com.ytc.common.model.ProgramHeader;
import com.ytc.common.model.ProgramPaidOn;
import com.ytc.common.model.ProgramTierDetail;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalBaseItems;
import com.ytc.dal.model.DalCustomer;
import com.ytc.dal.model.DalFrequency;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalProgramDetailTier;
import com.ytc.dal.model.DalProgramHeader;
import com.ytc.dal.model.DalProgramMaster;
import com.ytc.dal.model.DalStatus;
import com.ytc.dal.model.DalUserComments;
import com.ytc.dal.model.DalWorkflowStatus;
import com.ytc.helper.ProgramServiceHelper;
import com.ytc.service.IProgramCreateService;
import com.ytc.service.IProgramEmailService;
import com.ytc.service.IProgramUpdateService;
import com.ytc.service.IProgramWorkflowService;
import com.ytc.service.ServiceContext;

public class ProgramUpdateServiceImpl implements IProgramUpdateService{
	
	@Autowired
	private IDataAccessLayer baseDao;
	
	@Autowired
	private IProgramCreateService programCreateService;
	
	@Autowired
	private ServiceContext serviceContext;
	
	@Autowired
	private IProgramEmailService programEmailService;  
	
	@Autowired
	private IProgramWorkflowService programWorkflowService;
	
	@Override
	public ProgramHeader saveProgramDetails(ProgramHeader programHeader) {
		
		if(programHeader.getId() != null){

			
			DalProgramDetail dalProgramDetail = baseDao.getById(DalProgramDetail.class, programHeader.getProgramDetailList().get(0).getId());
			
			if(dalProgramDetail != null && !programHeader.isNewProgram()){
				/** If control is here, then user is editing the existing program details.*/
				updateProgramDetails(programHeader, dalProgramDetail);
			}
			else{
				
				int prgrmId=checkForTheDuplicateRecord(programHeader);
				
				if(prgrmId!=0)
				{
					programHeader.setSuccess(true); 
					programHeader.setDuplicate(true); 
					programHeader.setId(prgrmId); 
			    }
				else
				{	
					/** Only Program detail id will be generated.*/
					programCreateService.createProgramDetails(programHeader);
					programHeader.setDuplicate(false); 
				}
			}
		
		}
		else{
			
			/**Checking for duplicate record based on Customer, Date Range, Amount, Include / Exclude Tags */
			int prgrmId=checkForTheDuplicateRecord(programHeader);
			
			if(prgrmId!=0)
			{
				programHeader.setSuccess(true); 
				programHeader.setDuplicate(true); 
				programHeader.setId(prgrmId); 
		    }
			else
			{	
				/** Both Program header and detail id will be generated.*/
				programCreateService.createProgramDetails(programHeader);
				programHeader.setDuplicate(false); 
			}
		}		
		
		return programHeader;
	}

	private void updateProgramDetails(ProgramHeader programHeader, DalProgramDetail dalProgramDetail) {
		
		List<DalProgramDetAchieved> deletedAchievedEntityList = null;
		List<DalProgramDetPaid> deletedPaidEntityList = null;
		
		if(programHeader.getStatus() != null && dalProgramDetail.getDalProgramHeader() != null){
			dalProgramDetail.getDalProgramHeader().setStatus(baseDao.getById(DalStatus.class, ProgramServiceHelper.convertToInteger(programHeader.getStatus())));
			dalProgramDetail.getDalProgramHeader().setModifiedBy(dalProgramDetail.getModifiedBy());
		}
		/** Save Program Detail section information*/
		updateProgramDetailsData(dalProgramDetail, programHeader);

		/** save Program Paid Based on*/
		deletedPaidEntityList = updateProgramPaidBasedOnData(programHeader, dalProgramDetail);

		if(deletedPaidEntityList != null && !deletedPaidEntityList.isEmpty()){
			for(DalProgramDetPaid dalProgramDetPaid : deletedPaidEntityList){
				dalProgramDetail.getDalProgramDetPaidList().remove(dalProgramDetPaid);
			}
		}
		
		/** save Program Achieved Based on*/
		if(programHeader.isCalculatedProgram()){
			deletedAchievedEntityList = updateProgramAchieveBasedOnData(programHeader, dalProgramDetail);
			
			if(deletedAchievedEntityList != null && !deletedAchievedEntityList.isEmpty()){
				for(DalProgramDetAchieved dalProgramDetAchieved : deletedAchievedEntityList){
					baseDao.delete(DalProgramDetAchieved.class, dalProgramDetAchieved.getId());
					dalProgramDetail.getDalProgramDetAchievedList().remove(dalProgramDetAchieved);
				}
			}
		}
		
		saveUserComments(programHeader, dalProgramDetail);
		
		/**Approver information*/
		updateApproverInformation(dalProgramDetail, programHeader);
		
		baseDao.update(dalProgramDetail);

		/** tier detail is saved seperately as of now.*/
		if(programHeader.isCalculatedProgram()){
			updateProgramTierData(dalProgramDetail, programHeader);	
		}
		
		/**Setting the value to true */
		if(dalProgramDetail.getDalProgramHeader().getStatus() != null 
				&& !ProgramConstant.IN_PROGRESS_STATUS.equals(dalProgramDetail.getDalProgramHeader().getStatus().getType())
				&& !ProgramConstant.REJECTED_STATUS.equals(dalProgramDetail.getDalProgramHeader().getStatus().getType())
				&& "1".equals(programHeader.getProgramButton().getUserLevel())){
			programHeader.setNewProgram(true);
		}
		else{
			programHeader.setNewProgram(false);
		}
		programHeader.setSuccess(true);
		
		if(dalProgramDetail.getCreatedDate() != null){
			programHeader.getProgramDetailList().get(0).setCreatedDate( ProgramServiceHelper.convertDateToString(dalProgramDetail.getCreatedDate().getTime(), ProgramConstant.DATE_FORMAT));			
		}
		
		if(dalProgramDetail.getModifiedDate() != null){
			programHeader.getProgramDetailList().get(0).setModifiedDate(ProgramServiceHelper.convertDateToString(dalProgramDetail.getModifiedDate().getTime(), ProgramConstant.DATE_FORMAT) );
		}
		getUpdatedWorkflowDetails(programHeader, dalProgramDetail);
		programHeader.setStatus(dalProgramDetail.getStatus().getType());
		programEmailService.sendEmailData(programHeader, dalProgramDetail);
	}

	private void getUpdatedWorkflowDetails(ProgramHeader programHeader, DalProgramDetail dalProgramDetail) {
		//DalWorkflowStatus.getProgramWorkflowDetails
		Map<String, Object> inputParam = new HashMap<String,Object>();
		inputParam.put("programDetailId", dalProgramDetail.getId());
		List<DalWorkflowStatus> dalWorkflowStatusList = baseDao.getListFromNamedQueryWithParameter("DalWorkflowStatus.getProgramWorkflowDetails", inputParam);
		ProgramServiceHelper.populateWorkflowStatusDataAfterUpdate(programHeader, dalWorkflowStatusList);
	}
	
	private void saveUserComments(ProgramHeader programHeader, DalProgramDetail dalProgramDetail) {
		if(programHeader != null && dalProgramDetail != null && dalProgramDetail.getDalUserComments() == null){
			if(programHeader.getProgramDetailList().get(0).getComments() != null 
					&& !programHeader.getProgramDetailList().get(0).getComments().isEmpty() ){
				DalUserComments dalUserComments = new DalUserComments();
				dalUserComments.setDalProgramDetailForComment(dalProgramDetail);
				dalUserComments.setUserComments(programHeader.getProgramDetailList().get(0).getComments());
				dalUserComments.setCommentedDate(Calendar.getInstance());
				
				dalProgramDetail.setDalUserComments(dalUserComments);
			}
		}
		else if (programHeader != null && dalProgramDetail != null && dalProgramDetail.getDalUserComments() != null){
			DalUserComments dalUserComments = dalProgramDetail.getDalUserComments();
			dalUserComments.setDalProgramDetailForComment(dalProgramDetail);
			dalUserComments.setUserComments(programHeader.getProgramDetailList().get(0).getComments());
			dalUserComments.setCommentedDate(Calendar.getInstance());
		}
		
	}

	private void updateApproverInformation(DalProgramDetail dalProgramDetail, ProgramHeader programHeader) {
		
		programWorkflowService.updateWorkflowDetails(dalProgramDetail, programHeader, serviceContext.getEmployee());
	}

	private void updateProgramTierData(DalProgramDetail dalProgramDetail, ProgramHeader programHeader) {
		/** Need to handle the below scenarios 
		 * 1. New record
		 *  2. Modifying existing record
		 *  3. Deleting existing record*/
		Set<Integer> existingTierIdSet = null;
		Set<Integer> UserTierIdSet = null;
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("programDetailId", dalProgramDetail.getId());
		List<DalProgramDetailTier> dalProgramTierAddedList = null;
		List<DalProgramDetailTier> dalProgramTierList = baseDao.getListFromNamedQueryWithParameter("DalProgramDetailTier.getAllTierForProgramId", 
														parameters);
		
		if(programHeader.getProgramDetailList().get(0).getProgramTierDetailList() != null && dalProgramTierList != null){
			List<ProgramTierDetail> programTierDetailList = programHeader.getProgramDetailList().get(0).getProgramTierDetailList();
			existingTierIdSet = new HashSet<Integer>();
			 UserTierIdSet = new HashSet<Integer>();
			for(DalProgramDetailTier dalProgramDetailTier : dalProgramTierList){
				existingTierIdSet.add(dalProgramDetailTier.getId());
			}
			for(ProgramTierDetail programTierDetail : programTierDetailList){
				if(programTierDetail.getLevel() != null){
					if(existingTierIdSet.contains(programTierDetail.getId())){
						UserTierIdSet.add(programTierDetail.getId());
						//modification
						for(DalProgramDetailTier dalProgramDetailTier : dalProgramTierList){
							if(programTierDetail.getId().equals(dalProgramDetailTier.getId())){
								dalProgramDetailTier.setAmount(programTierDetail.getAmount().doubleValue());
								dalProgramDetailTier.setBeginRange(programTierDetail.getBeginRange());
								dalProgramDetailTier.setLevel(programTierDetail.getLevel());
								dalProgramDetailTier.setTierType(programHeader.getProgramDetailList().get(0).getAmountTypeTier());
								dalProgramDetailTier.setProgramDetailId(dalProgramDetail.getId());
								break;
							}
						}
					}
					else{
						//newly added
						if(dalProgramTierAddedList == null){
							dalProgramTierAddedList = new ArrayList<DalProgramDetailTier>();
						}
						DalProgramDetailTier dalProgramDetailTier = new DalProgramDetailTier();
						dalProgramDetailTier.setAmount(programTierDetail.getAmount().doubleValue());
						dalProgramDetailTier.setBeginRange(programTierDetail.getBeginRange());
						dalProgramDetailTier.setLevel(programTierDetail.getLevel());
						dalProgramDetailTier.setTierType(programHeader.getProgramDetailList().get(0).getAmountTypeTier());
						dalProgramDetailTier.setProgramDetailId(dalProgramDetail.getId());
						dalProgramTierAddedList.add(dalProgramDetailTier);
					}
				}					
			}
			
			if(dalProgramTierList != null && !dalProgramTierList.isEmpty()){
				for(DalProgramDetailTier dalProgramDetailTier : dalProgramTierList){
					baseDao.update(dalProgramDetailTier);		
				}
			}
			if(dalProgramTierAddedList != null){
				for(DalProgramDetailTier dalProgramDetailTier : dalProgramTierAddedList){
					baseDao.create(dalProgramDetailTier);
				}
			}
			
		}
		else{
			if(dalProgramTierList != null && !dalProgramTierList.isEmpty()){
				for(DalProgramDetailTier dalProgramDetailTier : dalProgramTierList){
					baseDao.delete(DalProgramDetailTier.class, dalProgramDetailTier.getId());		
				}
			}
		}
		
	}

	private List<DalProgramDetAchieved> updateProgramAchieveBasedOnData(ProgramHeader programHeader,
			DalProgramDetail dalProgramDetail) {
		Map<String, Set<String>> existingIncludedMap = null;
		Map<String, Set<String>> existingExcludedMap = null;
		List<DalProgramDetAchieved> deletedEntityList = null;
		if( programHeader != null && dalProgramDetail.getDalProgramDetPaidList() != null){
			existingIncludedMap = new HashMap<String, Set<String>>();
			existingExcludedMap = new HashMap<String, Set<String>>();
			Set<DalProgramDetAchieved> newlyAddedAchieveOnList = new HashSet<DalProgramDetAchieved>();
			/** Get the existing achieved details.*/
			ProgramServiceHelper.getExistingAchievedDetails(existingIncludedMap, existingExcludedMap, dalProgramDetail.getDalProgramDetAchievedList());
			/** Add the new achieve details updated by user*/
			getNewlyAddedAchieveDetails(existingIncludedMap, existingExcludedMap, programHeader.getProgramDetailList().get(0).getProgramAchieveOn(),
					newlyAddedAchieveOnList, dalProgramDetail);
			/** find the list of deleted values.*/
			Map<String, Set<String>> deletedList = ProgramServiceHelper.deleteAchieveOnDetails(existingIncludedMap, 
													existingExcludedMap, 
													programHeader.getProgramDetailList().get(0).getProgramAchieveOn());
			deletedEntityList = updateDeletedAchievedOnDetails(deletedList, dalProgramDetail.getDalProgramDetAchievedList() );
			
			/** add newly updated values.*/
			if(!newlyAddedAchieveOnList.isEmpty()){
				dalProgramDetail.getDalProgramDetAchievedList().addAll(newlyAddedAchieveOnList);
			}
		}
		
		return deletedEntityList;
	}

	private List<DalProgramDetAchieved> updateDeletedAchievedOnDetails(Map<String, Set<String>> deletedList,
											Set<DalProgramDetAchieved> dalProgramDetAchievedList) {
		List<DalProgramDetAchieved> deletedEntityList = new ArrayList<DalProgramDetAchieved>();
		if(dalProgramDetAchievedList != null){
			for(DalProgramDetAchieved dalProgramDetAchieved : dalProgramDetAchievedList){
				if(deletedList != null && deletedList.get(String.valueOf(dalProgramDetAchieved.getAchTagId())) != null){
					Set<String> deletedValue = deletedList.get(String.valueOf(dalProgramDetAchieved.getAchTagId()));
					if(deletedValue.contains(dalProgramDetAchieved.getDisplayValue())){
						deletedEntityList.add(dalProgramDetAchieved);
					}
				}
			}
		}
		
		return deletedEntityList;
		
	}
	
	private List<DalProgramDetPaid> updateProgramPaidBasedOnData(ProgramHeader programHeader,DalProgramDetail dalProgramDetail) {
		Map<String, Set<String>> existingIncludedMap = null;
		Map<String, Set<String>> existingExcludedMap = null;
		List<DalProgramDetPaid> deletedEntityList = null;
		if( programHeader != null && dalProgramDetail.getDalProgramDetPaidList() != null){
			existingIncludedMap = new HashMap<String, Set<String>>();
			existingExcludedMap = new HashMap<String, Set<String>>();
			Set<DalProgramDetPaid> newlyAddedPaidOnList = new HashSet<DalProgramDetPaid>();
			/**Existing details from table values.*/
			ProgramServiceHelper.getExistingPaidDetails(existingIncludedMap, existingExcludedMap, dalProgramDetail.getDalProgramDetPaidList());
			/** Newly added values by user.*/
			getNewlyAddedPaidDetails(existingIncludedMap, existingExcludedMap, programHeader.getProgramDetailList().get(0).getProgramPaidOn(),
															newlyAddedPaidOnList, dalProgramDetail);
			/** Identify the deleted values by user.*/
			Map<String, Set<String>> deletedList = ProgramServiceHelper.deletePaidOnDetails(existingIncludedMap, 
													existingExcludedMap, 
													dalProgramDetail.getDalProgramDetPaidList(),
													programHeader.getProgramDetailList().get(0).getProgramPaidOn());
			
			/**Update the details.*/
			deletedEntityList = updateDeletedPaidOnDetails(deletedList, dalProgramDetail.getDalProgramDetPaidList() );
			
			/** add newly updated values.*/
			if(!newlyAddedPaidOnList.isEmpty()){
				dalProgramDetail.getDalProgramDetPaidList().addAll(newlyAddedPaidOnList);
			}
		}
		return deletedEntityList;
	}

	private List<DalProgramDetPaid> updateDeletedPaidOnDetails(Map<String, Set<String>> deletedList,
			Set<DalProgramDetPaid> dalProgramDetPaidList) {
		List<DalProgramDetPaid> deletedEntityList = new ArrayList<DalProgramDetPaid>();
		if(dalProgramDetPaidList != null){
			for(DalProgramDetPaid dalProgramDetPaid : dalProgramDetPaidList){
				if(deletedList != null && deletedList.get(String.valueOf(dalProgramDetPaid.getTagId())) != null){
					Set<String> deletedValue = deletedList.get(String.valueOf(dalProgramDetPaid.getTagId()));
					if(deletedValue.contains(dalProgramDetPaid.getDisplayValue())){
						deletedEntityList.add(dalProgramDetPaid);
					}
				}
			}
		}
		return deletedEntityList;
		
	}

	public void getNewlyAddedPaidDetails(Map<String, Set<String>> existingIncludedMap,
			Map<String, Set<String>> existingExcludedMap, ProgramPaidOn programPaidOn,
			Set<DalProgramDetPaid> newlyAddedPaidOnList, DalProgramDetail dalProgramDetail) {
		if(programPaidOn != null){
			getAddedDetails(existingIncludedMap, programPaidOn, newlyAddedPaidOnList, dalProgramDetail, ProgramConstant.INCLUDED);
			getAddedDetails(existingExcludedMap, programPaidOn, newlyAddedPaidOnList, dalProgramDetail, ProgramConstant.EXCLUDED);
		}
		
	}

	private void getAddedDetails(Map<String, Set<String>> existingMap, ProgramPaidOn programPaidOn,
			Set<DalProgramDetPaid> newlyAddedPaidOnList, DalProgramDetail dalProgramDetail, String method) {
		Map<String, List<String>> newMap = null;
		if(ProgramConstant.INCLUDED.equals(method)){
			newMap = programPaidOn.getIncludedMap();
		}
		else{
			newMap = programPaidOn.getExcludedMap();
		}
		if(newMap != null){
			for(Map.Entry<String, List<String>> includeMap : newMap.entrySet()){
				List<String> userIncluded = includeMap.getValue();
				Set<String> existingInc = existingMap.get(includeMap.getKey());
				if(existingInc != null && !existingInc.isEmpty()){
					for(String value : userIncluded){
						if(!existingInc.contains(value)){
							//add
							DalProgramDetPaid dalProgramDetPaid = new DalProgramDetPaid();
							dalProgramDetPaid.setMethod(method);
							if(value.contains(ProgramConstant.TAG_VALUE_DELIMITER)){
								String delimitedValue[] = value.split(ProgramConstant.TAG_VALUE_DELIMITER);
								dalProgramDetPaid.setValue(delimitedValue[0].trim());
							}
							else{
								dalProgramDetPaid.setValue(value);
							}
							dalProgramDetPaid.setDisplayValue(value);
							dalProgramDetPaid.setTagId(Integer.valueOf(includeMap.getKey()));
							dalProgramDetPaid.setDalProgramDetails(dalProgramDetail);
							newlyAddedPaidOnList.add(dalProgramDetPaid);
						}
					}
				}
				else{
					addToPaidEntityList(userIncluded, includeMap.getKey(), method, dalProgramDetail, newlyAddedPaidOnList);
				}
			}
		}
	}
	
	private void addToPaidEntityList(List<String> userIncluded, String tagId, String method, DalProgramDetail dalProgramDetail,
			Set<DalProgramDetPaid> newlyAddedPaidOnList) {
		if(userIncluded != null){
			for(String value : userIncluded){
				DalProgramDetPaid dalProgramDetPaid = new DalProgramDetPaid();
				dalProgramDetPaid.setMethod(method);
				if(value.contains(ProgramConstant.TAG_VALUE_DELIMITER)){
					String delimitedValue[] = value.split(ProgramConstant.TAG_VALUE_DELIMITER);
					dalProgramDetPaid.setValue(delimitedValue[0].trim());
				}
				else{
					dalProgramDetPaid.setValue(value);
				}
				dalProgramDetPaid.setDisplayValue(value);
				dalProgramDetPaid.setTagId(Integer.valueOf(tagId));
				dalProgramDetPaid.setDalProgramDetails(dalProgramDetail);
				newlyAddedPaidOnList.add(dalProgramDetPaid);
			}
		}
	}

	private DalProgramDetail updateProgramDetailsData(DalProgramDetail dalProgramDet, ProgramHeader programHeader) {
		ProgramDetail programDetail = programHeader.getProgramDetailList().get(0);
		if(programDetail != null && dalProgramDet != null){
			if(programDetail.getPaidBasedOn() != null){
				dalProgramDet.setPaidBasedOn(baseDao.getById(DalBaseItems.class, Integer.valueOf(programDetail.getPaidBasedOn())));
			}
			if(programDetail.getPayoutFrequency() != null){
				dalProgramDet.setPaidFrequency(baseDao.getById(DalFrequency.class, Integer.valueOf(programDetail.getPayoutFrequency())));
			}
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(programDetail.getBeginDate().getTime());
			dalProgramDet.setProgramStartDate(cal);
			Calendar cal1 = Calendar.getInstance();
			cal1.setTimeInMillis(programDetail.getEndDate().getTime());
			dalProgramDet.setProgramEndDate(cal1);
			dalProgramDet.setBTL( ("Yes".equals(programDetail.getBTL()) ? "Y" : "N"));
			if(programDetail.getPricingType() != null){
				dalProgramDet.setPricingType(Integer.valueOf(programDetail.getPricingType()));
			
			}
			dalProgramDet.setProgramMaster(baseDao.getById(DalProgramMaster.class, Integer.valueOf(programDetail.getProgramName())));
			if(programDetail.getAmount() != null){
				dalProgramDet.setAccrualAmount(programDetail.getAmount().doubleValue());	
			}
			dalProgramDet.setAccrualType(programDetail.getAmountType());
			dalProgramDet.setPayTo(programDetail.getPayTo());
			if(programDetail.getPaidType()!= null){
				dalProgramDet.setPaidType(Integer.valueOf(programDetail.getPaidType()));
			}
			if(programHeader.isCalculatedProgram() && programDetail.getProgramPaidOn() != null){
				dalProgramDet.setIsTiered(programDetail.getProgramPaidOn().getIsTiered() == true ? "1" : "0");
				dalProgramDet.setTrueUp(programDetail.getProgramPaidOn().getIsTrueUp() == true ? "Y" : "N");
			}
			
			/*if(programDetail.getProgramPaidOn() != null){
				dalProgramDet.setLongDesc(programDetail.getProgramPaidOn().getProgramDescription());	
			}*/
			dalProgramDet.setLongDesc(programDetail.getLongDesc());
			
			if(programDetail.getProgramAchieveOn().getAchieveBasedOn() != null){
				dalProgramDet.setAchBasedMetric(baseDao.getById(DalBaseItems.class, Integer.valueOf(programDetail.getProgramAchieveOn().getAchieveBasedOn())));				
			}
			if(programDetail.getProgramAchieveOn().getAchieveFrequency()!= null){
				dalProgramDet.setAchBasedFreq(baseDao.getById(DalFrequency.class, Integer.valueOf(programDetail.getProgramAchieveOn().getAchieveFrequency())));				
			}
			if(programDetail.getActualMarker() != null){
				dalProgramDet.setActualMarker(programDetail.getActualMarker());	
			}
			dalProgramDet.setStatus(dalProgramDet.getDalProgramHeader().getStatus());
			dalProgramDet.setTbpCheck(programDetail.getTbpCheck());
			dalProgramDet.setGlCode(programDetail.getGlCode());
			dalProgramDet.setEstimatedAccrual(programDetail.getEstimatedAccrual());
			//setApproverLevelStatus(dalProgramDet, programHeader);
		}

		return dalProgramDet;
	}

	/**
	 * This method is to set the Zone manager and TBP manage status based on the current status or action
	 * taken by the logged in user.
	 * @param dalProgramDet dalProgramDet
	 * @param programHeader programHeader
	 */
/*	private void setApproverLevelStatus(DalProgramDetail dalProgramDet, ProgramHeader programHeader) {
		List<DalStatus> dalStatusList =  baseDao.getListFromNamedQuery("DalStatus.getAllDetails");
		if(ProgramConstant.USER_LEVEL_1.equals(programHeader.getProgramButton().getUserLevel())){
			if(ProgramConstant.PENDING_STATUS.equals(dalProgramDet.getStatus().getType())){
				if(dalStatusList != null && !dalStatusList.isEmpty()){
					for(DalStatus dalStatus : dalStatusList){
						if(ProgramConstant.WAITING_STATUS.equals(dalStatus.getType())){
							dalProgramDet.setTbAppStatus(dalStatus);
							break;
						}
					}
				}
				dalProgramDet.setZmAppStatus(dalProgramDet.getStatus());
			}

		}
		if(ProgramConstant.USER_LEVEL_2.equals(programHeader.getProgramButton().getUserLevel())){
			if(serviceContext != null && serviceContext.getEmployee() != null){
				dalProgramDet.setZmAppById(baseDao.getById(DalEmployee.class, serviceContext.getEmployee().getEMP_ID()));
				dalProgramDet.setZmAppDate(Calendar.getInstance());
				if(dalStatusList != null && !dalStatusList.isEmpty() && ProgramConstant.PENDING_STATUS.equals(dalProgramDet.getDalProgramHeader().getStatus().getType())){
					dalProgramDet.setTbAppStatus(dalProgramDet.getDalProgramHeader().getStatus());
					for(DalStatus dalStatus : dalStatusList){
						if(ProgramConstant.APPROVED_STATUS.equals(dalStatus.getType())){
							dalProgramDet.setZmAppStatus(dalStatus);
							break;
						}
					}
				}
				else{
					dalProgramDet.setZmAppStatus(dalProgramDet.getDalProgramHeader().getStatus());
				}
			}
		}
		else if(ProgramConstant.USER_LEVEL_3.equals(programHeader.getProgramButton().getUserLevel())){
			if(serviceContext != null && serviceContext.getEmployee() != null){
				dalProgramDet.setTbpAppById(baseDao.getById(DalEmployee.class, serviceContext.getEmployee().getEMP_ID()));
				dalProgramDet.setTbpAppDate(Calendar.getInstance());
				dalProgramDet.setTbAppStatus(dalProgramDet.getDalProgramHeader().getStatus());
			}
		}
	}
	*/
	public void getNewlyAddedAchieveDetails(Map<String, Set<String>> existingIncludedMap,
			Map<String, Set<String>> existingExcludedMap, ProgramAchieveOn programAchieveOn,
			Set<DalProgramDetAchieved> newlyAddedAchieveOnList, DalProgramDetail dalProgramDetail) {
		if(programAchieveOn != null){
			getAddedDetails(existingIncludedMap, programAchieveOn, newlyAddedAchieveOnList, dalProgramDetail, ProgramConstant.INCLUDED);
			getAddedDetails(existingExcludedMap, programAchieveOn, newlyAddedAchieveOnList, dalProgramDetail, ProgramConstant.EXCLUDED);
		}
		
	}

	private void getAddedDetails(Map<String, Set<String>> existingMap, ProgramAchieveOn programAchieveOn,
			Set<DalProgramDetAchieved> newlyAddedAchieveOnList, DalProgramDetail dalProgramDetail, String method) {
		Map<String, List<String>> newMap = null;
		if(ProgramConstant.INCLUDED.equals(method)){
			newMap = programAchieveOn.getIncludedMap();
		}
		else{
			newMap = programAchieveOn.getExcludedMap();
		}
		if(newMap != null){
			for(Map.Entry<String, List<String>> includeMap : newMap.entrySet()){
				List<String> userIncluded = includeMap.getValue();
				Set<String> existingInc = existingMap.get(includeMap.getKey());
				if(existingInc != null && !existingInc.isEmpty()){
					for(String value : userIncluded){
						if(!existingInc.contains(value)){
							//add
							DalProgramDetAchieved dalProgramDetAchieved = new DalProgramDetAchieved();
							if(value.contains(ProgramConstant.TAG_VALUE_DELIMITER)){
								String delimitedValue[] = value.split(ProgramConstant.TAG_VALUE_DELIMITER);
								dalProgramDetAchieved.setAchValue(delimitedValue[0].trim());
							}
							else{
								dalProgramDetAchieved.setAchValue(value);	
							}
							dalProgramDetAchieved.setDisplayValue(value);
							dalProgramDetAchieved.setAchTagId(Integer.valueOf(includeMap.getKey()));
							dalProgramDetAchieved.setDalProgramDetail(dalProgramDetail);
							newlyAddedAchieveOnList.add(dalProgramDetAchieved);
						}
					}
				}
				else{
					addToAchieveEntityList(userIncluded, includeMap.getKey(), method, dalProgramDetail, newlyAddedAchieveOnList);
				}
			}
		}
	}
	
	private void addToAchieveEntityList(List<String> userIncluded, String tagId, String method, DalProgramDetail dalProgramDetail,
										Set<DalProgramDetAchieved> newlyAddedAchieveOnList) {
		if(userIncluded != null){
			for(String value : userIncluded){
				DalProgramDetAchieved dalProgramDetAchieved = new DalProgramDetAchieved();
				dalProgramDetAchieved.setAchMethod(method);
				if(value.contains(ProgramConstant.TAG_VALUE_DELIMITER)){
					String delimitedValue[] = value.split(ProgramConstant.TAG_VALUE_DELIMITER);
					dalProgramDetAchieved.setAchValue(delimitedValue[0].trim());
				}
				else{
					dalProgramDetAchieved.setAchValue(value);	
				}
				dalProgramDetAchieved.setDisplayValue(value);
				dalProgramDetAchieved.setAchTagId(Integer.valueOf(tagId));
				dalProgramDetAchieved.setDalProgramDetail(dalProgramDetail);
				newlyAddedAchieveOnList.add(dalProgramDetAchieved);
			}
		}
	}
	private int checkForTheDuplicateRecord(ProgramHeader programHeader) {
		int prgrmId=0;
		Map<String,Object> map=new HashMap<String,Object>();
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		DalProgramDetail dalProgramDet = new DalProgramDetail();
		DalProgramHeader dalProgramHeader = new DalProgramHeader();	
		cal.setTimeInMillis(programHeader.getProgramDetailList().get(0).getBeginDate().getTime());
		dalProgramDet.setProgramStartDate(cal);
		cal1.setTimeInMillis(programHeader.getProgramDetailList().get(0).getEndDate().getTime());
		dalProgramDet.setProgramEndDate(cal1);
		if(programHeader.getProgramDetailList().get(0).getAmount() != null){
			dalProgramDet.setAccrualAmount(programHeader.getProgramDetailList().get(0).getAmount().doubleValue());	
		}
		dalProgramHeader.setCustomer(baseDao.getById(DalCustomer.class, programHeader.getCustomerId()));
		String customerNo=dalProgramHeader.getCustomer().getCustomerNumber();
		Map<String,List<String>> paidInputInclude=programHeader.getProgramDetailList().get(0).getProgramPaidOn().getIncludedMap();
		Map<String,List<String>> paidInputExclude=programHeader.getProgramDetailList().get(0).getProgramPaidOn().getExcludedMap();
		Map<String,List<String>> achieveInputInclude=programHeader.getProgramDetailList().get(0).getProgramAchieveOn().getIncludedMap();
		Map<String,List<String>> achieveInputExclude=programHeader.getProgramDetailList().get(0).getProgramAchieveOn().getExcludedMap();
		map.put("PGM_START_DATE", dalProgramDet.getProgramStartDate());
		map.put("PGM_END_DATE", dalProgramDet.getProgramEndDate());
		map.put("ACCRUAL_AMOUNT", dalProgramDet.getAccrualAmount());
		map.put("PGM_TYPE_ID", programHeader.getProgramDetailList().get(0).getProgramTypeId());
		List<DalProgramDetail> list=baseDao.getListFromNamedQueryWithParameter("DalProgramDetail.checkDuplicateRecords", map);
		
		if(!list.isEmpty())
		{
			for(DalProgramDetail dalList:list)
			{
				if(customerNo.equals(dalList.getDalProgramHeader().getCustomer().getCustomerNumber()))			
				{
					Set<DalProgramDetPaid> paidSet = dalList.getDalProgramDetPaidList();
					Set<DalProgramDetAchieved> achiveSet = dalList.getDalProgramDetAchievedList();
					Map<Integer,List<String>> paidDBIncludeMap=new HashMap<Integer,List<String>>();
				    Map<Integer,List<String>> paidDBExcludeMap=new HashMap<Integer,List<String>>();
				    Map<Integer,List<String>> achiveDBIncludeMap=new HashMap<Integer,List<String>>();
				    Map<Integer,List<String>> achiveDBExcludeMap=new HashMap<Integer,List<String>>();
				    for(DalProgramDetPaid paidSet1 : paidSet)
					{    
					    if("1".equals(paidSet1.getMethod()))
					    {
					    	if(paidDBIncludeMap.get(paidSet1.getTagId())==null)
					    	{
					    		List<String> tempList=new ArrayList<String>();
					    		tempList.add(paidSet1.getDisplayValue());
					    		paidDBIncludeMap.put(paidSet1.getTagId(),tempList);
					    	}
					    	else
					    	{
					    		List<String> tempList=paidDBIncludeMap.get(paidSet1.getTagId());
					    		tempList.add(paidSet1.getDisplayValue());
					    		paidDBIncludeMap.put(paidSet1.getTagId(),tempList);
					    	}
					    }
						else
						{
					    	if(paidDBExcludeMap.get(paidSet1.getTagId())==null)
					    	{
					    		List<String> tempList=new ArrayList<String>();
					    		tempList.add(paidSet1.getDisplayValue());
					    		paidDBExcludeMap.put(paidSet1.getTagId(),tempList);
					    	}
					    	else
					    	{
					    		List<String> tempList=paidDBExcludeMap.get(paidSet1.getTagId());
					    		tempList.add(paidSet1.getDisplayValue());
					    		paidDBExcludeMap.put(paidSet1.getTagId(),tempList);
					    	}
					    }
					}
					for(DalProgramDetAchieved achiveSet1 : achiveSet)
					{    
					    if("1".equals(achiveSet1.getAchMethod()))
					    {
					    	if(achiveDBIncludeMap.get(achiveSet1.getAchTagId())==null)
					    	{
					    		List<String> tempList=new ArrayList<String>();
					    		tempList.add(achiveSet1.getDisplayValue());
					    		achiveDBIncludeMap.put(achiveSet1.getAchTagId(),tempList);
					    	}
					    	else
					    	{
					    		List<String> tempList=achiveDBIncludeMap.get(achiveSet1.getAchTagId());
					    		tempList.add(achiveSet1.getDisplayValue());
					    		achiveDBIncludeMap.put(achiveSet1.getAchTagId(),tempList);
					    	}
					    }
						else
						{
							if(achiveDBExcludeMap.get(achiveSet1.getAchTagId())==null)
					    	{
					    		List<String> tempList=new ArrayList<String>();
					    		tempList.add(achiveSet1.getDisplayValue());
					    		achiveDBExcludeMap.put(achiveSet1.getAchTagId(),tempList);
					    	}
					    	else
					    	{
					    		List<String> tempList=achiveDBExcludeMap.get(achiveSet1.getAchTagId());
					    		tempList.add(achiveSet1.getDisplayValue());
					    		achiveDBExcludeMap.put(achiveSet1.getAchTagId(),tempList);
					    	}
						}
					}
					if(comapreMaps(paidDBIncludeMap,paidInputInclude) && comapreMaps(paidDBExcludeMap,paidInputExclude) && comapreMaps(achiveDBIncludeMap,achieveInputInclude) && comapreMaps(achiveDBExcludeMap,achieveInputExclude) )
					{
						prgrmId=dalList.getId();
						return prgrmId;
					}
			    }
     	     }
		}
		
	     return prgrmId;
	}
	private boolean comapreMaps(Map<Integer,List<String>> includeDBMap,Map<String,List<String>> includeInputMap)
	{
		boolean flag=false;
		if(includeInputMap==null)
		includeInputMap=new HashMap<String,List<String>>();
		if(includeInputMap.size()==0 && includeDBMap.size()==0)
		{
			return true;
		}
		if(includeDBMap.size()==includeInputMap.size())
		{
			for (final String key : includeInputMap.keySet())
			{
			    if (includeDBMap.containsKey(Integer.parseInt(key))) {
			    	List<String> list1=includeDBMap.get(Integer.parseInt(key));
			    	List<String> list2=includeInputMap.get(key);
			    	if(list1.size()==list2.size())
			    	{
				    	list1.removeAll(list2);
				    	if(list1.size()==0)
					    flag=true;
			    	}
			        else
			        {
			        	return false;
			        }
			    }
			}
		}
			
		return flag;
	}
}
