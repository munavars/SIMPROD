package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.ProgramMaster;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalBaseItems;
import com.ytc.dal.model.DalCustomer;
import com.ytc.dal.model.DalFrequency;
import com.ytc.dal.model.DalPaidType;
import com.ytc.dal.model.DalPricingType;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalProgramHeader;
import com.ytc.dal.model.DalTagItems;
import com.ytc.service.IProgramCreateService;

public class ProgramCreateServiceImpl implements IProgramCreateService {
	
	@Autowired
	private IDataAccessLayer baseDao;

	@Override
	public Boolean createProgramDetails(ProgramMaster programMaster) {
		DalProgramHeader dalProgramHeader = new DalProgramHeader();
		dalProgramHeader.setId(3731);
		dalProgramHeader.setAccessPgmId(4747);
		dalProgramHeader.setBu(programMaster.getProgramHeader().getBusinessUnit());
		dalProgramHeader.setCustomer(baseDao.getEntityById(DalCustomer.class, 47));
		dalProgramHeader.setStatusId(3);
		DalProgramDetail dalProgramDetail =  createProgramDetailsData(dalProgramHeader, programMaster);

		/** save Program Paid Based on*/
		createProgramPaidBasedOnData(dalProgramHeader, programMaster, dalProgramDetail);
		
		/** save Program Achieved Based on*/
		createProgramAchieveBasedOnData(dalProgramHeader, programMaster, dalProgramDetail);
		
		baseDao.create(dalProgramHeader);
		
		return Boolean.TRUE;
	}

	private void createProgramAchieveBasedOnData(DalProgramHeader dalProgramHeader, ProgramMaster programMaster,
			DalProgramDetail dalProgramDetail) {
		if(dalProgramDetail != null && programMaster != null && dalProgramHeader != null){
			if(programMaster.getProgramAchieveOn() != null ){
				List<DalProgramDetAchieved> newlyAddedAchieveOnList = new ArrayList<DalProgramDetAchieved>();
				if( programMaster.getProgramAchieveOn().getExcludedMap() != null && !programMaster.getProgramAchieveOn().getExcludedMap().isEmpty()){
					getAddedAchieveDetails(programMaster.getProgramPaidOn().getExcludedMap(), newlyAddedAchieveOnList, dalProgramDetail,
										ProgramConstant.EXCLUDED);
				}
				if( programMaster.getProgramAchieveOn().getIncludedMap() != null && !programMaster.getProgramAchieveOn().getIncludedMap().isEmpty()){
					getAddedAchieveDetails(programMaster.getProgramPaidOn().getIncludedMap(), newlyAddedAchieveOnList, dalProgramDetail,
										ProgramConstant.INCLUDED);
				}
				if(!newlyAddedAchieveOnList.isEmpty()){
					dalProgramDetail.setDalProgramDetAchievedList(newlyAddedAchieveOnList);
				}			
			}
		}
		
	}

	private void getAddedAchieveDetails(Map<String, List<String>> userAddedMap,
			List<DalProgramDetAchieved> newlyAddedAchieveOnList, DalProgramDetail dalProgramDetail, String method) {
		if(userAddedMap != null){
			for(Map.Entry<String, List<String>> includeMap : userAddedMap.entrySet()){
				List<String> userIncluded = includeMap.getValue();
				DalTagItems dalTagItems = null;
				if(userIncluded != null && userIncluded.isEmpty()){
					for(String value : userIncluded){
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
		}
		
	}

	private void createProgramPaidBasedOnData(DalProgramHeader dalProgramHeader, ProgramMaster programMaster,
			DalProgramDetail dalProgramDetail) {
		if(dalProgramDetail != null && programMaster != null && dalProgramHeader != null){
			if(programMaster.getProgramPaidOn() != null ){
				List<DalProgramDetPaid> newlyAddedPaidOnList = new ArrayList<DalProgramDetPaid>();
				if( programMaster.getProgramPaidOn().getExcludedMap() != null && !programMaster.getProgramPaidOn().getExcludedMap().isEmpty()){
					getAddedPaidDetails(programMaster.getProgramPaidOn().getExcludedMap(), newlyAddedPaidOnList, dalProgramDetail,
										ProgramConstant.EXCLUDED);
				}
				if( programMaster.getProgramPaidOn().getIncludedMap() != null && !programMaster.getProgramPaidOn().getIncludedMap().isEmpty()){
					getAddedPaidDetails(programMaster.getProgramPaidOn().getIncludedMap(), newlyAddedPaidOnList, dalProgramDetail,
										ProgramConstant.INCLUDED);
				}
				if(!newlyAddedPaidOnList.isEmpty()){
					dalProgramDetail.setDalProgramDetPaidList(newlyAddedPaidOnList);
				}	
						
			}
		}
	}

	private void getAddedPaidDetails(Map<String, List<String>> userAddedMap, 
			List<DalProgramDetPaid> newlyAddedPaidOnList, DalProgramDetail dalProgramDetail, String method) {
		if(userAddedMap != null){
			for(Map.Entry<String, List<String>> includeMap : userAddedMap.entrySet()){
				List<String> userIncluded = includeMap.getValue();
				DalTagItems dalTagItems = null;
				if(userIncluded != null && userIncluded.isEmpty()){
					for(String value : userIncluded){
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
		}
	}
	
	private DalProgramDetail createProgramDetailsData(DalProgramHeader dalProgramHeader, ProgramMaster programMaster) {
		List<DalProgramDetail> dalProgramDetailList = new ArrayList<DalProgramDetail>();
		DalProgramDetail dalProgramDet = new DalProgramDetail();
//		dalProgramHeader.setid
//		dalProgramDet.getDalProgramHeader().setAccessPgmId(Integer.valueOf(programMaster.getProgramDetail().getProgramName()));
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
		dalProgramDetailList.add(dalProgramDet);
		dalProgramDet.setDalProgramHeader(dalProgramHeader);
		dalProgramHeader.setDalProgramDetailList(dalProgramDetailList);
		return dalProgramDet;
	}

}
