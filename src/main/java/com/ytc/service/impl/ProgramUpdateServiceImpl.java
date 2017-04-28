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
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalBaseItems;
import com.ytc.dal.model.DalFrequency;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalProgramHeader;
import com.ytc.dal.model.DalProgramMaster;
import com.ytc.helper.ProgramServiceHelper;
import com.ytc.service.IProgramCreateService;
import com.ytc.service.IProgramUpdateService;

public class ProgramUpdateServiceImpl implements IProgramUpdateService{
	
	@Autowired
	private IDataAccessLayer baseDao;
	
	@Autowired
	private IProgramCreateService programCreateService;
	
	@Override
	public Boolean saveProgramDetails(ProgramHeader programHeader) {
		
		Boolean isSuccess = Boolean.FALSE;
		List<DalProgramDetAchieved> deletedAchievedEntityList = null;
		List<DalProgramDetPaid> deletedPaidEntityList = null;
		if(programHeader.getId() != null){
			DalProgramHeader dalProgramHeader =  baseDao.getById(DalProgramHeader.class, programHeader.getId());
			
			if(dalProgramHeader != null){
				/** If control is here, then user is editing the existing program details.*/
			
				/** Save Program Detail section information*/
				DalProgramDetail dalProgramDetail =  updateProgramDetailsData(dalProgramHeader, programHeader);

				/** save Program Paid Based on*/
				deletedPaidEntityList = updateProgramPaidBasedOnData(dalProgramHeader, programHeader, dalProgramDetail);
								
				/** save Program Achieved Based on*/
				deletedAchievedEntityList = updateProgramAchieveBasedOnData(dalProgramHeader, programHeader, dalProgramDetail);
				
				baseDao.update(dalProgramHeader);
				
				if(deletedPaidEntityList != null && !deletedPaidEntityList.isEmpty()){
					for(DalProgramDetPaid dalProgramDetPaid : deletedPaidEntityList){
						dalProgramDetPaid.getDalProgramDetails().getDalProgramDetPaidList().remove(dalProgramDetPaid);
						baseDao.delete(DalProgramDetPaid.class, dalProgramDetPaid.getId());
					}
				}
				if(deletedAchievedEntityList != null && !deletedAchievedEntityList.isEmpty()){
					for(DalProgramDetAchieved dalProgramDetAchieved : deletedAchievedEntityList){
						baseDao.delete(DalProgramDetAchieved.class, dalProgramDetAchieved.getId());
					}
				}
				
				isSuccess = Boolean.TRUE;
			}
			else{
				isSuccess = programCreateService.createProgramDetails(programHeader);
			}
		}
		else{
			isSuccess = programCreateService.createProgramDetails(programHeader);
		}

						
		return isSuccess;
	}

	private List<DalProgramDetAchieved> updateProgramAchieveBasedOnData(DalProgramHeader dalProgramHeader, ProgramHeader programHeader,
			DalProgramDetail dalProgramDetail) {
		Map<String, Set<String>> existingIncludedMap = null;
		Map<String, Set<String>> existingExcludedMap = null;
		List<DalProgramDetAchieved> deletedEntityList = null;
		if(dalProgramHeader != null && programHeader != null && dalProgramDetail.getDalProgramDetPaidList() != null){
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
					if(deletedValue.contains(dalProgramDetAchieved.getAchValue())){
						deletedEntityList.add(dalProgramDetAchieved);
					}
				}
			}
		}
		
		return deletedEntityList;
		
	}
	
	private List<DalProgramDetPaid> updateProgramPaidBasedOnData(DalProgramHeader dalProgramHeader, ProgramHeader programHeader,DalProgramDetail dalProgramDetail) {
		Map<String, Set<String>> existingIncludedMap = null;
		Map<String, Set<String>> existingExcludedMap = null;
		List<DalProgramDetPaid> deletedEntityList = null;
		if(dalProgramHeader != null && programHeader != null && dalProgramDetail.getDalProgramDetPaidList() != null){
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
					if(deletedValue.contains(dalProgramDetPaid.getValue())){
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
							dalProgramDetPaid.setValue(value);
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
				dalProgramDetPaid.setValue(value);
				dalProgramDetPaid.setTagId(Integer.valueOf(tagId));
				dalProgramDetPaid.setDalProgramDetails(dalProgramDetail);
				newlyAddedPaidOnList.add(dalProgramDetPaid);
			}
		}
	}

	private DalProgramDetail updateProgramDetailsData(DalProgramHeader dalProgramHeader, ProgramHeader programHeader) {
		DalProgramDetail dalProgramDetail = null;
		for(DalProgramDetail dalProgramDet : dalProgramHeader.getDalProgramDetailList()){
			if(programHeader.getProgramDetailList().get(0).getId() != null &&
					programHeader.getProgramDetailList().get(0).getId().equals(dalProgramDet.getId()) ){
				ProgramDetail programDetail = programHeader.getProgramDetailList().get(0);
				if(programDetail.getPaidBasedOn() != null){
					DalBaseItems baseItems = baseDao.getById(DalBaseItems.class, Integer.valueOf(programDetail.getPaidBasedOn()));
					if(dalProgramDet.getPaidBasedOn() != null ){
						dalProgramDet.getPaidBasedOn().setId(baseItems.getId());
						dalProgramDet.getPaidBasedOn().setBase(baseItems.getBase());
						dalProgramDet.getPaidBasedOn().setBaseItem(baseItems.getBaseItem());
					}	
					else{
						dalProgramDet.setPaidBasedOn(baseItems);
					}
				}
				if(programDetail.getPayoutFrequency() != null){
					DalFrequency dalfreq = baseDao.getById(DalFrequency.class, Integer.valueOf(programDetail.getPayoutFrequency()));
					if(dalProgramDet.getPaidFrequency() != null){
						dalProgramDet.getPaidFrequency().setId(dalfreq.getId());
						dalProgramDet.getPaidFrequency().setFrequency(dalfreq.getFrequency());
						dalProgramDet.getPaidFrequency().setSortSequence(dalfreq.getSortSequence());	
					}
					else{
						dalProgramDet.setPaidFrequency(dalfreq);
					}
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
/*					DalPricingType dalPricingType = baseDao.getById(DalPricingType.class, Integer.valueOf(programDetail.getPricingType()));
					if(dalProgramDet.getDalPricingType() != null){
						dalProgramDet.getDalPricingType().setId(dalPricingType.getId());
						dalProgramDet.getDalPricingType().setType(dalPricingType.getType());
					}
					else{
						dalProgramDet.setDalPricingType(dalPricingType);		
					}*/
				}
				dalProgramDet.setProgramMaster(baseDao.getById(DalProgramMaster.class, Integer.valueOf(programDetail.getProgramName())));
				dalProgramDet.setAccrualAmount(programDetail.getAmount().doubleValue());
				dalProgramDet.setAccrualType(programDetail.getAmountType());
				dalProgramDet.setPayTo(programDetail.getPayTo());
				if(programDetail.getPaidType()!= null){
					dalProgramDet.setPaidType(Integer.valueOf(programDetail.getPaidType()));
					/*DalPaidType dalPaidType = baseDao.getById(DalPaidType.class, Integer.valueOf(programDetail.getPaidType()));*/
					/*if(dalProgramDet.getDalPaidType() != null){
						dalProgramDet.getDalPaidType().setId(dalPaidType.getId());
						dalProgramDet.getDalPaidType().setType(dalPaidType.getType());
					}
					else{
						dalProgramDet.setDalPaidType(dalPaidType);
					}*/
				}				
				dalProgramDet.setIsTiered(programDetail.getProgramPaidOn().getIsTiered() == true ? "0" : "1");
				dalProgramDet.setTrueUp(programDetail.getProgramPaidOn().getIsTrueUp() == true ? "Y" : "N");
				dalProgramDet.setLongDesc(programDetail.getProgramPaidOn().getProgramDescription());
				
				if(programDetail.getProgramAchieveOn().getAchieveBasedOn() != null){
					DalBaseItems baseItems = baseDao.getById(DalBaseItems.class, Integer.valueOf(programDetail.getProgramAchieveOn().getAchieveBasedOn() ));
					if(dalProgramDet.getAchBasedMetric() != null ){
						dalProgramDet.getAchBasedMetric().setId(baseItems.getId());
						dalProgramDet.getAchBasedMetric().setBase(baseItems.getBase());
						dalProgramDet.getAchBasedMetric().setBaseItem(baseItems.getBaseItem());
					}	
					else{
						dalProgramDet.setAchBasedMetric(baseItems);
					}
				}
				if(programDetail.getProgramAchieveOn().getAchieveFrequency()!= null){
					DalFrequency dalfreq = baseDao.getById(DalFrequency.class, Integer.valueOf(programDetail.getProgramAchieveOn().getAchieveFrequency()));
					if(dalProgramDet.getAchBasedFreq() != null){
						dalProgramDet.getAchBasedFreq().setId(dalfreq.getId());
						dalProgramDet.getAchBasedFreq().setFrequency(dalfreq.getFrequency());
						dalProgramDet.getAchBasedFreq().setSortSequence(dalfreq.getSortSequence());	
					}
					else{
						dalProgramDet.setAchBasedFreq(dalfreq);
					}
				}
				
				dalProgramDetail = dalProgramDet;
				break;
			}
		}
		return dalProgramDetail;
	}
	
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
							dalProgramDetAchieved.setAchMethod(method);
							dalProgramDetAchieved.setAchValue(value);
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
				dalProgramDetAchieved.setAchValue(value);
				dalProgramDetAchieved.setAchTagId(Integer.valueOf(tagId));
				dalProgramDetAchieved.setDalProgramDetail(dalProgramDetail);
				newlyAddedAchieveOnList.add(dalProgramDetAchieved);
			}
		}
	}
}
