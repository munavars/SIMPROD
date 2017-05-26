package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ytc.common.model.Employee;
import com.ytc.common.model.ProgramDetail;
import com.ytc.common.model.ProgramHeader;
import com.ytc.common.model.ProgramTierDetail;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalBaseItems;
import com.ytc.dal.model.DalCustomer;
import com.ytc.dal.model.DalEmployee;
import com.ytc.dal.model.DalEmployeeHierarchy;
import com.ytc.dal.model.DalFrequency;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalProgramDetailTier;
import com.ytc.dal.model.DalProgramHeader;
import com.ytc.dal.model.DalProgramMaster;
import com.ytc.dal.model.DalProgramType;
import com.ytc.dal.model.DalStatus;
import com.ytc.helper.ProgramServiceHelper;
import com.ytc.service.IProgramCreateService;
import com.ytc.service.IProgramEmailService;
import com.ytc.service.ServiceContext;

public class ProgramCreateServiceImpl implements IProgramCreateService {
	
	@Autowired
	private IDataAccessLayer baseDao;

	@Autowired
	private ServiceContext serviceContext;
	
	@Autowired
	private IProgramEmailService programEmailService;  
	
	@Override
	@Transactional
	public ProgramHeader createProgramDetails(ProgramHeader programHeader) {
		DalProgramHeader dalProgramHeader = null;
		if(programHeader != null && programHeader.isNewProgram() && programHeader.getId() == null){
			dalProgramHeader = new DalProgramHeader();	
			dalProgramHeader.setBu(programHeader.getBusinessUnit());
			dalProgramHeader.setCustomer(baseDao.getById(DalCustomer.class, programHeader.getCustomerId()));
			if(programHeader.getStatus() != null){
				dalProgramHeader.setStatus(baseDao.getById(DalStatus.class, ProgramServiceHelper.convertToInteger(programHeader.getStatus())));	
			}
			DalEmployee emp = new DalEmployee();
			emp.setId(programHeader.getRequestId());
			dalProgramHeader.setRequest(emp);	
			if(programHeader.getRequestedDate() != null){
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(programHeader.getRequestedDate().getTime());
				dalProgramHeader.setRequestDate(cal);	
			}
			Random rand = new Random();
			int  n = rand.nextInt(1000) + 1;
			dalProgramHeader.setAccessPgmId(n);
		}
		else if(programHeader != null && programHeader.isNewProgram() && programHeader.getId() != null){
			dalProgramHeader = baseDao.getById(DalProgramHeader.class, programHeader.getId());
			if(programHeader.getStatus() != null){
				dalProgramHeader.setStatus(baseDao.getById(DalStatus.class, ProgramServiceHelper.convertToInteger(programHeader.getStatus())));	
			}
		}

		DalProgramDetail dalProgramDetail =  createProgramDetailsData(programHeader);
		dalProgramDetail.setStatus(dalProgramHeader.getStatus());
		setApproverLevelStatus(dalProgramDetail, programHeader, serviceContext.getEmployee());
		if(dalProgramHeader != null && dalProgramHeader.getDalProgramDetailList() != null){
			dalProgramHeader.getDalProgramDetailList().add(dalProgramDetail);
			dalProgramHeader.setCreatedBy(dalProgramDetail.getCreatedBy());
			dalProgramHeader.setModifiedBy(dalProgramDetail.getModifiedBy());
		}
		dalProgramDetail.setDalProgramHeader(dalProgramHeader);
		

		/** save Program Paid Based on*/
		createProgramPaidBasedOnData(dalProgramHeader, programHeader, dalProgramDetail);
		
		if(programHeader.isCalculatedProgram()){
			/** save Program Achieved Based on*/
			createProgramAchieveBasedOnData(dalProgramHeader, programHeader, dalProgramDetail);	
		}
		
		DalProgramDetail returnEntity = baseDao.create(dalProgramDetail);
		
		if(returnEntity != null && returnEntity.getId() != null){
					
			if(programHeader.isCalculatedProgram()){
				createProgramTierData(returnEntity.getId(), programHeader);	
			}
			/** Reset the value the value again*/
			
			programHeader.setId(returnEntity.getDalProgramHeader().getId());
			programHeader.getProgramDetailList().get(0).setId(returnEntity.getId());
			programHeader.setSuccess(true);
			programHeader.setStatus(returnEntity.getStatus().getType());
			programEmailService.sendEmailData(programHeader, dalProgramDetail);
			if(returnEntity.getDalProgramHeader().getStatus() != null 
					&& !ProgramConstant.IN_PROGRESS_STATUS.equals(returnEntity.getDalProgramHeader().getStatus().getType())
					&& !ProgramConstant.REJECTED_STATUS.equals(returnEntity.getDalProgramHeader().getStatus().getType())
					&& "1".equals(programHeader.getProgramButton().getUserLevel())){
				programHeader.setNewProgram(true);
			}
			else{
				programHeader.setNewProgram(false);
			}
		}
		
		return programHeader;
	}
	
	/**
	 * This method is to set the Zone manager and TBP manage status based on the current status or action
	 * taken by the logged in user.
	 * @param dalProgramDet dalProgramDet
	 * @param programHeader programHeader
	 * @param employee Employee type.
	 */
	private void setApproverLevelStatus(DalProgramDetail dalProgramDet, ProgramHeader programHeader, Employee employee) {
		List<DalStatus> dalStatusList =  baseDao.getListFromNamedQuery("DalStatus.getAllDetails");
		if(ProgramConstant.USER_LEVEL_1.equals(programHeader.getProgramButton().getUserLevel())){
			setApproverIds(dalProgramDet, employee);
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
	}

	private void setApproverIds(DalProgramDetail dalProgramDet, Employee employee) {
		if(employee != null && dalProgramDet != null){
			DalEmployeeHierarchy dalEmployeeHierarchy = baseDao.getEntityById(DalEmployeeHierarchy.class, employee.getEMP_ID());
			if(dalEmployeeHierarchy != null){
				if(ProgramConstant.EMP_HIERARCHY_1 == dalEmployeeHierarchy.getFixedHierLvl()){
					dalProgramDet.setZmAppById(baseDao.getById(DalEmployee.class, Integer.valueOf(dalEmployeeHierarchy.getLvl2EmpId())));
					dalProgramDet.setTbpAppById(baseDao.getById(DalEmployee.class, Integer.valueOf(dalEmployeeHierarchy.getLvl3EmpId())));
				}
				else if(ProgramConstant.EMP_HIERARCHY_2 == dalEmployeeHierarchy.getFixedHierLvl()){
					dalProgramDet.setZmAppById(baseDao.getById(DalEmployee.class, Integer.valueOf(dalEmployeeHierarchy.getLvl3EmpId())));
					dalProgramDet.setTbpAppById(baseDao.getById(DalEmployee.class, Integer.valueOf(dalEmployeeHierarchy.getLvl4EmpId())));
				}
				else if(ProgramConstant.EMP_HIERARCHY_3 == dalEmployeeHierarchy.getFixedHierLvl()){
					dalProgramDet.setZmAppById(baseDao.getById(DalEmployee.class, Integer.valueOf(dalEmployeeHierarchy.getLvl4EmpId())));
					dalProgramDet.setTbpAppById(baseDao.getById(DalEmployee.class, Integer.valueOf(dalEmployeeHierarchy.getLvl5EmpId())));
				}
				else if(ProgramConstant.EMP_HIERARCHY_4 == dalEmployeeHierarchy.getFixedHierLvl()){
					dalProgramDet.setZmAppById(baseDao.getById(DalEmployee.class, Integer.valueOf(dalEmployeeHierarchy.getLvl5EmpId())));
					/**We cannot set tbp app id in this case.*/
				}
				/** IF employee's fixed hierarchy is 5, then that request cannot have next level approvers. This is a error case*/
			}
		}
	}

	private void createProgramTierData(Integer id, ProgramHeader programHeader) {
		List<ProgramTierDetail> programTierDetailList = programHeader.getProgramDetailList().get(0).getProgramTierDetailList();
		List<DalProgramDetailTier> dalProgramTierAddedList = null;
		if(programTierDetailList != null){
			for(ProgramTierDetail programTierDetail : programTierDetailList){
				//newly added
				if(programTierDetail.getLevel() != null){ //Even if no row is added in UI, we are getting a blank row in service. 
														  //to avoid the insertion or fatal error of blank row, this condition is added.
					if(dalProgramTierAddedList == null){
						dalProgramTierAddedList = new ArrayList<DalProgramDetailTier>();
					}
					DalProgramDetailTier dalProgramDetailTier = new DalProgramDetailTier();
					dalProgramDetailTier.setAmount(programTierDetail.getAmount().doubleValue());
					dalProgramDetailTier.setBeginRange(programTierDetail.getBeginRange());
					dalProgramDetailTier.setLevel(programTierDetail.getLevel());
					dalProgramDetailTier.setTierType(programHeader.getProgramDetailList().get(0).getAmountTypeTier());
					dalProgramDetailTier.setProgramDetailId(id);
					dalProgramTierAddedList.add(dalProgramDetailTier);	
				}
			}
			
			if(dalProgramTierAddedList != null && !dalProgramTierAddedList.isEmpty()){
				for(DalProgramDetailTier dalProgramDetailTier : dalProgramTierAddedList){
					baseDao.create(dalProgramDetailTier);
				}
			}
		}
	}

	private void createProgramAchieveBasedOnData(DalProgramHeader dalProgramHeader, ProgramHeader programHeader,
			DalProgramDetail dalProgramDetail) {
		
		if(dalProgramDetail != null && programHeader != null && dalProgramHeader != null){
			ProgramDetail programDetail = programHeader.getProgramDetailList().get(0);
			if(programDetail.getProgramAchieveOn() != null ){
				Set<DalProgramDetAchieved> newlyAddedAchieveOnList = new HashSet<DalProgramDetAchieved>();
				if( programDetail.getProgramAchieveOn().getExcludedMap() != null && !programDetail.getProgramAchieveOn().getExcludedMap().isEmpty()){
					getAddedAchieveDetails(programDetail.getProgramAchieveOn().getExcludedMap(), newlyAddedAchieveOnList, dalProgramDetail,
										ProgramConstant.EXCLUDED);
				}
				if( programDetail.getProgramAchieveOn().getIncludedMap() != null && !programDetail.getProgramAchieveOn().getIncludedMap().isEmpty()){
					getAddedAchieveDetails(programDetail.getProgramAchieveOn().getIncludedMap(), newlyAddedAchieveOnList, dalProgramDetail,
										ProgramConstant.INCLUDED);
				}
				if(!newlyAddedAchieveOnList.isEmpty()){
					dalProgramDetail.setDalProgramDetAchievedList(newlyAddedAchieveOnList);
				}			
			}
		}
		
	}

	private void getAddedAchieveDetails(Map<String, List<String>> userAddedMap,
			Set<DalProgramDetAchieved> newlyAddedAchieveOnList, DalProgramDetail dalProgramDetail, String method) {
		if(userAddedMap != null){
			for(Map.Entry<String, List<String>> includeMap : userAddedMap.entrySet()){
				List<String> userIncluded = includeMap.getValue();
				if(userIncluded != null && !userIncluded.isEmpty()){
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
						dalProgramDetAchieved.setAchTagId(Integer.valueOf(includeMap.getKey()));
						dalProgramDetAchieved.setDalProgramDetail(dalProgramDetail);
						dalProgramDetAchieved.setCreatedBy(dalProgramDetail.getCreatedBy());
						dalProgramDetAchieved.setModifiedBy(dalProgramDetail.getModifiedBy());
						newlyAddedAchieveOnList.add(dalProgramDetAchieved);
					}
				}
			}
		}
		
	}

	private void createProgramPaidBasedOnData(DalProgramHeader dalProgramHeader, ProgramHeader programHeader,
			DalProgramDetail dalProgramDetail) {
		if(dalProgramDetail != null && programHeader != null && dalProgramHeader != null){
			ProgramDetail programDetail = programHeader.getProgramDetailList().get(0);
			if(programDetail.getProgramPaidOn() != null ){
				Set<DalProgramDetPaid> newlyAddedPaidOnList = new HashSet<DalProgramDetPaid>();
				if( programDetail.getProgramPaidOn().getExcludedMap() != null && !programDetail.getProgramPaidOn().getExcludedMap().isEmpty()){
					getAddedPaidDetails(programDetail.getProgramPaidOn().getExcludedMap(), newlyAddedPaidOnList, dalProgramDetail,
										ProgramConstant.EXCLUDED);
				}
				if( programDetail.getProgramPaidOn().getIncludedMap() != null && !programDetail.getProgramPaidOn().getIncludedMap().isEmpty()){
					getAddedPaidDetails(programDetail.getProgramPaidOn().getIncludedMap(), newlyAddedPaidOnList, dalProgramDetail,
										ProgramConstant.INCLUDED);
				}
				if(!newlyAddedPaidOnList.isEmpty()){
					dalProgramDetail.setDalProgramDetPaidList(newlyAddedPaidOnList);
				}	
						
			}
		}
	}

	private void getAddedPaidDetails(Map<String, List<String>> userAddedMap, 
			Set<DalProgramDetPaid> newlyAddedPaidOnList, DalProgramDetail dalProgramDetail, String method) {
		if(userAddedMap != null){
			for(Map.Entry<String, List<String>> includeMap : userAddedMap.entrySet()){
				List<String> userIncluded = includeMap.getValue();
				if(userIncluded != null && !userIncluded.isEmpty()){
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
						dalProgramDetPaid.setTagId(Integer.valueOf(includeMap.getKey()));
						dalProgramDetPaid.setDalProgramDetails(dalProgramDetail);
						dalProgramDetPaid.setCreatedBy(dalProgramDetail.getCreatedBy());
						dalProgramDetPaid.setModifiedBy(dalProgramDetail.getModifiedBy());
						newlyAddedPaidOnList.add(dalProgramDetPaid);
					}
				}
			}
		}
	}
	
	private DalProgramDetail createProgramDetailsData(ProgramHeader programHeader) {
		DalProgramDetail dalProgramDet = new DalProgramDetail();
		ProgramDetail programDetail = programHeader.getProgramDetailList().get(0);
		dalProgramDet.setPaidBasedOn(baseDao.getById(DalBaseItems.class, Integer.valueOf(programDetail.getPaidBasedOn())));
		dalProgramDet.setPaidFrequency(baseDao.getById(DalFrequency.class, Integer.valueOf(programDetail.getPayoutFrequency())));		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(programDetail.getBeginDate().getTime());
		dalProgramDet.setProgramStartDate(cal);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(programDetail.getEndDate().getTime());
		dalProgramDet.setProgramEndDate(cal1);
		if(programHeader.getProgramDetailList().get(0).getProgramTypeId() != null){
			dalProgramDet.setDalProgramType(baseDao.getById(DalProgramType.class, programHeader.getProgramDetailList().get(0).getProgramTypeId() ));
		}
	
		dalProgramDet.setAccrualAmount(programDetail.getAmount().doubleValue());
		dalProgramDet.setAccrualType(programDetail.getAmountType());
		dalProgramDet.setPayTo(programDetail.getPayTo());
		dalProgramDet.setPaidType(Integer.valueOf(programDetail.getPaidType()));
		
		//dalProgramDet.setLongDesc(programDetail.getProgramPaidOn().getProgramDescription());
		dalProgramDet.setLongDesc(programDetail.getLongDesc());
		dalProgramDet.setProgramMaster(baseDao.getById(DalProgramMaster.class, Integer.valueOf(programDetail.getProgramName())));
		
		if(programHeader.isCalculatedProgram()){
			dalProgramDet.setIsTiered(programDetail.getProgramPaidOn().getIsTiered() == true ? "1" : "0");
			dalProgramDet.setTrueUp(programDetail.getProgramPaidOn().getIsTrueUp() == true ? "Y" : "N");
			dalProgramDet.setBTL( ("Yes".equals(programDetail.getBTL()) ? "Y" : "N"));
			dalProgramDet.setPricingType(Integer.valueOf(programDetail.getPricingType()));
			if(programDetail.getProgramAchieveOn().getAchieveBasedOn() != null){
					dalProgramDet.setAchBasedMetric(baseDao.getById(DalBaseItems.class, Integer.valueOf(programDetail.getProgramAchieveOn().getAchieveBasedOn())));
			}
			if(programDetail.getProgramAchieveOn().getAchieveFrequency()!= null){
				dalProgramDet.setAchBasedFreq(baseDao.getById(DalFrequency.class, Integer.valueOf(programDetail.getProgramAchieveOn().getAchieveFrequency())));
			}	
		}
		
		return dalProgramDet;
	}

}
