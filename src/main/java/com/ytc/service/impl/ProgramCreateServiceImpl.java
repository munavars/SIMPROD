package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.ProgramDetail;
import com.ytc.common.model.ProgramHeader;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalCustomer;
import com.ytc.dal.model.DalEmployee;
import com.ytc.dal.model.DalFrequency;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalProgramHeader;
import com.ytc.dal.model.DalProgramMaster;
import com.ytc.dal.model.DalStatus;
import com.ytc.service.IProgramCreateService;

public class ProgramCreateServiceImpl implements IProgramCreateService {
	
	@Autowired
	private IDataAccessLayer baseDao;

	@Override
	public Boolean createProgramDetails(ProgramHeader programHeader) {
		DalProgramHeader dalProgramHeader = new DalProgramHeader();	
		dalProgramHeader.setBu("P");
		dalProgramHeader.setCustomer(baseDao.getById(DalCustomer.class, 47));
		dalProgramHeader.setStatus(baseDao.getById(DalStatus.class, 3));
		DalEmployee emp = new DalEmployee();
		emp.setEMP_ID(47);
		dalProgramHeader.setRequest(emp);
		DalProgramDetail dalProgramDetail =  createProgramDetailsData(dalProgramHeader, programHeader);
		Random rand = new Random();
		int  n = rand.nextInt(1000) + 1;
		dalProgramHeader.setAccessPgmId(n);
		/** save Program Paid Based on*/
		createProgramPaidBasedOnData(dalProgramHeader, programHeader, dalProgramDetail);
		
		/** save Program Achieved Based on*/
		createProgramAchieveBasedOnData(dalProgramHeader, programHeader, dalProgramDetail);
		
		baseDao.create(dalProgramHeader);
		
		return Boolean.TRUE;
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
						dalProgramDetAchieved.setAchValue(value);
						dalProgramDetAchieved.setAchTagId(Integer.valueOf(includeMap.getKey()));
						dalProgramDetAchieved.setDalProgramDetail(dalProgramDetail);
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
						dalProgramDetPaid.setValue(value);
						dalProgramDetPaid.setTagId(Integer.valueOf(includeMap.getKey()));
						dalProgramDetPaid.setDalProgramDetails(dalProgramDetail);
						newlyAddedPaidOnList.add(dalProgramDetPaid);
					}
				}
			}
		}
	}
	
	private DalProgramDetail createProgramDetailsData(DalProgramHeader dalProgramHeader, ProgramHeader programHeader) {
		List<DalProgramDetail> dalProgramDetailList = new ArrayList<DalProgramDetail>();
		DalProgramDetail dalProgramDet = new DalProgramDetail();
		ProgramDetail programDetail = programHeader.getProgramDetailList().get(0);
		dalProgramDet.setPaidBasedOn(baseDao.getById(DalProgramDetPaid.class, Integer.valueOf(programDetail.getPaidBasedOn())));
		dalProgramDet.setPaidFrequency(baseDao.getById(DalFrequency.class, Integer.valueOf(programDetail.getPayoutFrequency())));		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(programDetail.getBeginDate().getTime());
		dalProgramDet.setProgramStartDate(cal);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(programDetail.getEndDate().getTime());
		dalProgramDet.setProgramEndDate(cal1);
		dalProgramDet.setBTL( ("Yes".equals(programDetail.getBTL()) ? "Y" : "N"));
		dalProgramDet.setPricingType(Integer.valueOf(programDetail.getPricingType()));
		dalProgramDet.setAccrualAmount(programDetail.getAmount().doubleValue());
		dalProgramDet.setAccrualType(programDetail.getAmountType());
		dalProgramDet.setPayTo(programDetail.getPayTo());
		dalProgramDet.setPaidType(Integer.valueOf(programDetail.getPaidType()));
		dalProgramDet.setIsTiered(programDetail.getProgramPaidOn().getIsTiered() == true ? "0" : "1");
		dalProgramDet.setTrueUp(programDetail.getProgramPaidOn().getIsTrueUp() == true ? "Y" : "N");
		dalProgramDet.setLongDesc(programDetail.getProgramPaidOn().getProgramDescription());
		dalProgramDetailList.add(dalProgramDet);
		dalProgramDet.setDalProgramHeader(dalProgramHeader);
		dalProgramHeader.setDalProgramDetailList(dalProgramDetailList);
		dalProgramDet.setProgramMaster(baseDao.getById(DalProgramMaster.class, Integer.valueOf(programDetail.getProgramName())));
		
		if(programDetail.getProgramAchieveOn().getAchieveBasedOn() != null){
				dalProgramDet.setAchBasedMetric(baseDao.getById(DalProgramDetAchieved.class, Integer.valueOf(programDetail.getProgramAchieveOn().getAchieveBasedOn())));
		}
		if(programDetail.getProgramAchieveOn().getAchieveFrequency()!= null){
			dalProgramDet.setAchBasedFreq(baseDao.getById(DalFrequency.class, Integer.valueOf(programDetail.getProgramAchieveOn().getAchieveFrequency())));
		}
		
		return dalProgramDet;
	}

}
