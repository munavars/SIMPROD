package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.ProgramAchieveOn;
import com.ytc.common.model.ProgramMaster;
import com.ytc.common.model.ProgramPaidOn;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalBaseItems;
import com.ytc.dal.model.DalFrequency;
import com.ytc.dal.model.DalPaidType;
import com.ytc.dal.model.DalPricingType;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalProgramHeader;
import com.ytc.dal.model.DalTagItems;
import com.ytc.helper.ProgramServiceHelper;
import com.ytc.service.IProgramCreateService;
import com.ytc.service.IProgramUpdateService;

public class ProgramUpdateServiceImpl implements IProgramUpdateService{
	
	@Autowired
	private IDataAccessLayer baseDao;
	
	@Autowired
	private IProgramCreateService programCreateService;
	
	@Override
	public Boolean saveProgramDetails(ProgramMaster programMaster) {
		
		Boolean isSuccess = Boolean.FALSE;
		
		if(programMaster.getProgramHeader().getId() != null){
			DalProgramHeader dalProgramHeader =  baseDao.getById(DalProgramHeader.class, programMaster.getProgramHeader().getId());
			
			if(dalProgramHeader != null){
				/** If control is here, then user is editing the existing program details.*/
			
				/** Save Program Detail section information*/
				DalProgramDetail dalProgramDetail =  updateProgramDetailsData(dalProgramHeader, programMaster);

				/** save Program Paid Based on*/
				updateProgramPaidBasedOnData(dalProgramHeader, programMaster, dalProgramDetail);
				
				/** save Program Achieved Based on*/
				updateProgramAchieveBasedOnData(dalProgramHeader, programMaster, dalProgramDetail);
				
				isSuccess = Boolean.TRUE;
			}
			else{
				isSuccess = programCreateService.createProgramDetails(programMaster);
			}
		}
		else{
			isSuccess = programCreateService.createProgramDetails(programMaster);
		}

						
		return isSuccess;
	}

	private void updateProgramAchieveBasedOnData(DalProgramHeader dalProgramHeader, ProgramMaster programMaster,
			DalProgramDetail dalProgramDetail) {
		Map<String, Set<String>> existingIncludedMap = null;
		Map<String, Set<String>> existingExcludedMap = null;
		if(dalProgramHeader != null && programMaster != null && dalProgramDetail.getDalProgramDetPaidList() != null){
			existingIncludedMap = new HashMap<String, Set<String>>();
			existingExcludedMap = new HashMap<String, Set<String>>();
			List<DalProgramDetAchieved> newlyAddedAchieveOnList = new ArrayList<DalProgramDetAchieved>();
			ProgramServiceHelper.getExistingAchievedDetails(existingIncludedMap, existingExcludedMap, dalProgramDetail.getDalProgramDetAchievedList());
			getNewlyAddedAchieveDetails(existingIncludedMap, existingExcludedMap, programMaster.getProgramAchieveOn(),
					newlyAddedAchieveOnList, dalProgramDetail);
			Map<String, Set<String>> deletedList = ProgramServiceHelper.deleteAchieveOnDetails(existingIncludedMap, 
													existingExcludedMap, 
													programMaster.getProgramAchieveOn());
			updateDeletedAchievedOnDetails(deletedList, dalProgramDetail.getDalProgramDetAchievedList() );
		}
		
	}

	private void updateDeletedAchievedOnDetails(Map<String, Set<String>> deletedList,
											List<DalProgramDetAchieved> dalProgramDetAchievedList) {
		List<DalProgramDetAchieved> deletedEntityList = new ArrayList<DalProgramDetAchieved>();
		if(dalProgramDetAchievedList != null){
			for(DalProgramDetAchieved dalProgramDetAchieved : dalProgramDetAchievedList){
				if(deletedList != null && deletedList.get(String.valueOf(dalProgramDetAchieved.getAchTagItems().getItemId())) != null){
					Set<String> deletedValue = deletedList.get(String.valueOf(dalProgramDetAchieved.getAchTagItems().getItemId()));
					if(deletedValue.contains(dalProgramDetAchieved.getAchValue())){
						deletedEntityList.add(dalProgramDetAchieved);
					}
				}
			}
			dalProgramDetAchievedList.removeAll(deletedEntityList);
		}
		
	}
	
	private void updateProgramPaidBasedOnData(DalProgramHeader dalProgramHeader, ProgramMaster programMaster,DalProgramDetail dalProgramDetail) {
		Map<String, Set<String>> existingIncludedMap = null;
		Map<String, Set<String>> existingExcludedMap = null;
		if(dalProgramHeader != null && programMaster != null && dalProgramDetail.getDalProgramDetPaidList() != null){
			existingIncludedMap = new HashMap<String, Set<String>>();
			existingExcludedMap = new HashMap<String, Set<String>>();
			List<DalProgramDetPaid> newlyAddedPaidOnList = new ArrayList<DalProgramDetPaid>();
			ProgramServiceHelper.getExistingPaidDetails(existingIncludedMap, existingExcludedMap, dalProgramDetail.getDalProgramDetPaidList());
			getNewlyAddedPaidDetails(existingIncludedMap, existingExcludedMap, programMaster.getProgramPaidOn(),
															newlyAddedPaidOnList, dalProgramDetail);
			Map<String, Set<String>> deletedList = ProgramServiceHelper.deletePaidOnDetails(existingIncludedMap, 
													existingExcludedMap, 
													dalProgramDetail.getDalProgramDetPaidList(),
													programMaster.getProgramPaidOn());
			updateDeletedPaidOnDetails(deletedList, dalProgramDetail.getDalProgramDetPaidList() );
		}
	}

	private void updateDeletedPaidOnDetails(Map<String, Set<String>> deletedList,
			List<DalProgramDetPaid> dalProgramDetPaidList) {
		List<DalProgramDetPaid> deletedEntityList = new ArrayList<DalProgramDetPaid>();
		if(dalProgramDetPaidList != null){
			for(DalProgramDetPaid dalProgramDetPaid : dalProgramDetPaidList){
				if(deletedList != null && deletedList.get(String.valueOf(dalProgramDetPaid.getTagId().getItemId())) != null){
					Set<String> deletedValue = deletedList.get(String.valueOf(dalProgramDetPaid.getTagId().getItemId()));
					if(deletedValue.contains(dalProgramDetPaid.getValue())){
						deletedEntityList.add(dalProgramDetPaid);
					}
				}
			}
			dalProgramDetPaidList.removeAll(deletedEntityList);
		}
		
	}

	public void getNewlyAddedPaidDetails(Map<String, Set<String>> existingIncludedMap,
			Map<String, Set<String>> existingExcludedMap, ProgramPaidOn programPaidOn,
			List<DalProgramDetPaid> newlyAddedPaidOnList, DalProgramDetail dalProgramDetail) {
		if(programPaidOn != null){
			getAddedDetails(existingIncludedMap, programPaidOn, newlyAddedPaidOnList, dalProgramDetail, ProgramConstant.INCLUDED);
			getAddedDetails(existingExcludedMap, programPaidOn, newlyAddedPaidOnList, dalProgramDetail, ProgramConstant.EXCLUDED);
		}
		
	}

	private void getAddedDetails(Map<String, Set<String>> existingMap, ProgramPaidOn programPaidOn,
			List<DalProgramDetPaid> newlyAddedPaidOnList, DalProgramDetail dalProgramDetail, String method) {
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
				if(existingInc != null && existingInc.isEmpty()){
					for(String value : userIncluded){
						DalTagItems dalTagItems = null;
						if(!existingInc.contains(value)){
							//add
							DalProgramDetPaid dalProgramDetPaid = new DalProgramDetPaid();
							dalProgramDetPaid.setMethod(method);
							dalProgramDetPaid.setValue(value);
							if(dalTagItems == null){
								dalTagItems = baseDao.getEntityById(DalTagItems.class, Integer.valueOf(includeMap.getKey()));
							}
							dalProgramDetPaid.setTagId(dalTagItems);
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
			List<DalProgramDetPaid> newlyAddedPaidOnList) {
		if(userIncluded != null){
			for(String value : userIncluded){
				DalTagItems dalTagItems = null;
				DalProgramDetPaid dalProgramDetPaid = new DalProgramDetPaid();
				dalProgramDetPaid.setMethod(method);
				dalProgramDetPaid.setValue(value);
				if(dalTagItems == null){
					dalTagItems = baseDao.getEntityById(DalTagItems.class, Integer.valueOf(tagId));
				}
				dalProgramDetPaid.setTagId(dalTagItems);
				dalProgramDetPaid.setDalProgramDetails(dalProgramDetail);
				newlyAddedPaidOnList.add(dalProgramDetPaid);
			}
		}
	}

	private DalProgramDetail updateProgramDetailsData(DalProgramHeader dalProgramHeader, ProgramMaster programMaster) {
		DalProgramDetail dalProgramDetail = null;
		for(DalProgramDetail dalProgramDet : dalProgramHeader.getDalProgramDetailList()){
			if(programMaster.getProgramDetail().getId() != null &&
					programMaster.getProgramDetail().getId().equals(dalProgramDet.getId()) ){
				dalProgramDet.getDalProgramHeader().setAccessPgmId(Integer.valueOf(programMaster.getProgramDetail().getProgramName()));
				dalProgramDet.setPaidBasedOn(baseDao.getEntityById(DalBaseItems.class, Integer.valueOf(programMaster.getProgramDetail().getPaidBasedOn())));
				dalProgramDet.setPaidFrequency(baseDao.getEntityById(DalFrequency.class, Integer.valueOf(programMaster.getProgramDetail().getPayoutFrequency())));
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(programMaster.getProgramDetail().getBeginDate().getTime());
				dalProgramDet.setProgramStartDate(cal);
				Calendar cal1 = Calendar.getInstance();
				cal1.setTimeInMillis(programMaster.getProgramDetail().getEndDate().getTime());
				dalProgramDet.setProgramEndDate(cal1);
				dalProgramDet.setBTL( (programMaster.getProgramDetail().getBtl() == true) ? "Y" : "N");
				dalProgramDet.setDalPricingType(baseDao.getEntityById(DalPricingType.class, Integer.valueOf(programMaster.getProgramDetail().getPricingType())));
				dalProgramDet.setAccrualAmount(programMaster.getProgramDetail().getAmount().doubleValue());
				dalProgramDet.setAccrualType(programMaster.getProgramDetail().getAmountType());
				dalProgramDet.setPayTo(programMaster.getProgramDetail().getPayTo());
				dalProgramDet.setDalPaidType(baseDao.getEntityById(DalPaidType.class, Integer.valueOf(programMaster.getProgramDetail().getPaidType())));
				dalProgramDet.setIsTiered(programMaster.getProgramPaidOn().getIsTiered() == true ? "Y" : "N");
				dalProgramDet.setTrueUp(programMaster.getProgramPaidOn().getIsTrueUp() == true ? "Y" : "N");
				dalProgramDet.setLongDesc(programMaster.getProgramPaidOn().getProgramDescription());
				dalProgramDetail = dalProgramDet;
			}
		}
		return dalProgramDetail;
	}
	
	public void getNewlyAddedAchieveDetails(Map<String, Set<String>> existingIncludedMap,
			Map<String, Set<String>> existingExcludedMap, ProgramAchieveOn programAchieveOn,
			List<DalProgramDetAchieved> newlyAddedAchieveOnList, DalProgramDetail dalProgramDetail) {
		if(programAchieveOn != null){
			getAddedDetails(existingIncludedMap, programAchieveOn, newlyAddedAchieveOnList, dalProgramDetail, ProgramConstant.INCLUDED);
			getAddedDetails(existingExcludedMap, programAchieveOn, newlyAddedAchieveOnList, dalProgramDetail, ProgramConstant.EXCLUDED);
		}
		
	}

	private void getAddedDetails(Map<String, Set<String>> existingMap, ProgramAchieveOn programAchieveOn,
			List<DalProgramDetAchieved> newlyAddedAchieveOnList, DalProgramDetail dalProgramDetail, String method) {
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
				if(existingInc != null && existingInc.isEmpty()){
					for(String value : userIncluded){
						DalTagItems dalTagItems = null;
						if(!existingInc.contains(value)){
							//add
							DalProgramDetAchieved dalProgramDetAchieved = new DalProgramDetAchieved();
							dalProgramDetAchieved.setAchMethod(method);
							dalProgramDetAchieved.setAchValue(value);
							if(dalTagItems == null){
								dalTagItems = baseDao.getEntityById(DalTagItems.class, Integer.valueOf(includeMap.getKey()));
							}
							dalProgramDetAchieved.setAchTagItems(dalTagItems);
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
										List<DalProgramDetAchieved> newlyAddedAchieveOnList) {
		if(userIncluded != null){
			for(String value : userIncluded){
				DalTagItems dalTagItems = null;
				DalProgramDetAchieved dalProgramDetAchieved = new DalProgramDetAchieved();
				dalProgramDetAchieved.setAchMethod(method);
				dalProgramDetAchieved.setAchValue(value);
				if(dalTagItems == null){
					dalTagItems = baseDao.getEntityById(DalTagItems.class, Integer.valueOf(tagId));
				}
				dalProgramDetAchieved.setAchTagItems(dalTagItems);
				dalProgramDetAchieved.setDalProgramDetail(dalProgramDetail);
				newlyAddedAchieveOnList.add(dalProgramDetAchieved);
			}
		}
	}
}
