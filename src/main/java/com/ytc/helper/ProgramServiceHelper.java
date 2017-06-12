package com.ytc.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ytc.common.model.ProgramAchieveOn;
import com.ytc.common.model.ProgramHeader;
import com.ytc.common.model.ProgramPaidOn;
import com.ytc.common.model.ProgramWorkflowStatus;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.model.DalEmployee;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalWorkflowStatus;

public class ProgramServiceHelper {
	
	public static String convertToString(Object obj){
		String returnValue = null;
		if(obj != null){
			returnValue = String.valueOf(obj);
		}
		return returnValue;
	}

	public static Integer convertToInteger(Object obj){
		Integer returnValue = null;
		if(obj != null){
			returnValue = Integer.parseInt(obj.toString());
		}
		return returnValue;
	}
	
	public static Date convertToDateFromCalendar(Calendar input1){
		Date newDate = null;
		if(input1 != null){
			newDate = input1.getTime();
		}
		return newDate;
	}
	
	public static String convertDateToString(Date inputDate, String format){
		String convertedDateString = null;
		
		if(inputDate != null && format != null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			//convertedDateString = sdf.format(new Date());
			convertedDateString = sdf.format(inputDate);
		}
		return convertedDateString;
	}
	
	public static Date convertDateToRequiredFormat(Date inputDate, String format){
		Date newDate = null;
		
		if(inputDate != null && format != null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String convertedDateString = sdf.format(inputDate);
			try {
				newDate = sdf.parse(convertedDateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newDate;
	}
	
	public static void getExistingPaidDetails(Map<String, Set<String>> existingIncludedMap,
			Map<String, Set<String>> existingExcludedMap, Set<DalProgramDetPaid> dalProgramDetPaidList) {
		Set<String> existingIncludeList = null;
		Set<String> existingExcludeList = null;
		if(dalProgramDetPaidList != null){
			
			for(DalProgramDetPaid dalProgramDetPaid : dalProgramDetPaidList){
				if(ProgramConstant.INCLUDED.equals(dalProgramDetPaid.getMethod())){
					if(existingIncludedMap.get(String.valueOf(dalProgramDetPaid.getTagId())) != null){
						existingIncludeList = existingIncludedMap.get(String.valueOf(dalProgramDetPaid.getTagId()));
						existingIncludeList.add(dalProgramDetPaid.getDisplayValue());
					}
					else{
						existingIncludeList = new HashSet<String>(); 
						existingIncludeList.add(dalProgramDetPaid.getDisplayValue());
						existingIncludedMap.put(String.valueOf(dalProgramDetPaid.getTagId()), existingIncludeList);
					}
				}
				else if(ProgramConstant.EXCLUDED.equals(dalProgramDetPaid.getMethod())){
					if(existingExcludedMap.get(String.valueOf(dalProgramDetPaid.getTagId())) != null){
						existingExcludeList = existingExcludedMap.get(String.valueOf(dalProgramDetPaid.getTagId()));
						existingExcludeList.add(dalProgramDetPaid.getDisplayValue());
					}
					else{
						existingExcludeList = new HashSet<String>(); 
						existingExcludeList.add(dalProgramDetPaid.getDisplayValue());
						existingExcludedMap.put(String.valueOf(dalProgramDetPaid.getTagId()), existingExcludeList);
					}
				}

			}
		}
	}


	public static void getExistingAchievedDetails(Map<String, Set<String>> existingIncludedMap,
			Map<String, Set<String>> existingExcludedMap, Set<DalProgramDetAchieved> dalProgramDetAchievedList) {
		Set<String> existingIncludeList = null;
		Set<String> existingExcludeList = null;
		if(dalProgramDetAchievedList != null){
			
			for(DalProgramDetAchieved dalProgramDetPaid : dalProgramDetAchievedList){
				if(ProgramConstant.INCLUDED.equals(dalProgramDetPaid.getAchMethod())){
					if(existingIncludedMap.get(String.valueOf(dalProgramDetPaid.getAchTagId())) != null){
						existingIncludeList = existingIncludedMap.get(String.valueOf(dalProgramDetPaid.getAchTagId()));
						existingIncludeList.add(dalProgramDetPaid.getDisplayValue());
					}
					else{
						existingIncludeList = new HashSet<String>(); 
						existingIncludeList.add(dalProgramDetPaid.getDisplayValue());
						existingIncludedMap.put(String.valueOf(dalProgramDetPaid.getAchTagId()), existingIncludeList);
					}
				}
				else if(ProgramConstant.EXCLUDED.equals(dalProgramDetPaid.getAchMethod())){
					if(existingExcludedMap.get(String.valueOf(dalProgramDetPaid.getAchTagId())) != null){
						existingExcludeList = existingExcludedMap.get(String.valueOf(dalProgramDetPaid.getAchTagId()));
						existingExcludeList.add(dalProgramDetPaid.getDisplayValue());
					}
					else{
						existingExcludeList = new HashSet<String>(); 
						existingExcludeList.add(dalProgramDetPaid.getDisplayValue());
						existingExcludedMap.put(String.valueOf(dalProgramDetPaid.getAchTagId()), existingExcludeList);
					}
				}

			}
		}
	}
	
	public static Map<String, Set<String>> deletePaidOnDetails(Map<String, Set<String>> existingIncludedMap,
			Map<String, Set<String>> existingExcludedMap, Set<DalProgramDetPaid> dalProgramDetPaidList, ProgramPaidOn programPaidOn) {
		Map<String, Set<String>> deletedValueForTagId = new HashMap<String, Set<String>>();
		/*Include-  deleted value*/
		for(Map.Entry<String, Set<String>> existingMap : existingIncludedMap.entrySet()){
			List<String> fromUIList = programPaidOn.getIncludedMap().get(existingMap.getKey());
			Set<String> existingValueSet = existingMap.getValue();
			if(fromUIList != null){
				for(String value : existingValueSet){
					if(!fromUIList.contains(value)){
						if(deletedValueForTagId.get(existingMap.getKey()) != null){
							Set<String> deletedValueSet = deletedValueForTagId.get(existingMap.getKey());
							deletedValueSet.add(value);
						}
						else{
							Set<String> deletedValueSet = new HashSet<String>();
							deletedValueSet.add(value);
							deletedValueForTagId.put(existingMap.getKey(), deletedValueSet);
						}
					}
				}				
			}
			else{
				for(String value : existingValueSet){
					if(deletedValueForTagId.get(existingMap.getKey()) != null){
						Set<String> deletedValueSet = deletedValueForTagId.get(existingMap.getKey());
						deletedValueSet.add(value);
					}
					else{
						Set<String> deletedValueSet = new HashSet<String>();
						deletedValueSet.add(value);
						deletedValueForTagId.put(existingMap.getKey(), deletedValueSet);
					}
				}
			}

		}
		
		/*Exclude-  deleted value*/
		for(Map.Entry<String, Set<String>> existingMap : existingExcludedMap.entrySet()){
			List<String> fromUIList = programPaidOn.getExcludedMap().get(existingMap.getKey());
			Set<String> existingValueSet = existingMap.getValue();
			if(fromUIList != null){
				for(String value : existingValueSet){
					if(!fromUIList.contains(value)){
						if(deletedValueForTagId.get(existingMap.getKey()) != null){
							Set<String> deletedValueSet = deletedValueForTagId.get(existingMap.getKey());
							deletedValueSet.add(value);
						}
						else{
							Set<String> deletedValueSet = new HashSet<String>();
							deletedValueSet.add(value);
							deletedValueForTagId.put(existingMap.getKey(), deletedValueSet);
						}
					}
				}				
			}
			else{
				for(String value : existingValueSet){
					if(deletedValueForTagId.get(existingMap.getKey()) != null){
						Set<String> deletedValueSet = deletedValueForTagId.get(existingMap.getKey());
						deletedValueSet.add(value);
					}
					else{
						Set<String> deletedValueSet = new HashSet<String>();
						deletedValueSet.add(value);
						deletedValueForTagId.put(existingMap.getKey(), deletedValueSet);
					}
				}
			}

		}
		return deletedValueForTagId;
	}
	
	
	public static Map<String, Set<String>> deleteAchieveOnDetails(Map<String, Set<String>> existingIncludedMap,
			Map<String, Set<String>> existingExcludedMap, ProgramAchieveOn programAchieveOn) {
		Map<String, Set<String>> deletedValueForTagId = new HashMap<String, Set<String>>();
		/*Include-  deleted value*/
		for(Map.Entry<String, Set<String>> existingMap : existingIncludedMap.entrySet()){
			List<String> fromUIList = programAchieveOn.getIncludedMap().get(existingMap.getKey());
			Set<String> existingValueSet = existingMap.getValue();
			if(fromUIList != null){
				for(String value : existingValueSet){
					if(!fromUIList.contains(value)){
						if(deletedValueForTagId.get(existingMap.getKey()) != null){
							Set<String> deletedValueSet = deletedValueForTagId.get(existingMap.getKey());
							deletedValueSet.add(value);
						}
						else{
							Set<String> deletedValueSet = new HashSet<String>();
							deletedValueSet.add(value);
							deletedValueForTagId.put(existingMap.getKey(), deletedValueSet);
						}
					}
				}				
			}
			else{
				for(String value : existingValueSet){
					if(deletedValueForTagId.get(existingMap.getKey()) != null){
						Set<String> deletedValueSet = deletedValueForTagId.get(existingMap.getKey());
						deletedValueSet.add(value);
					}
					else{
						Set<String> deletedValueSet = new HashSet<String>();
						deletedValueSet.add(value);
						deletedValueForTagId.put(existingMap.getKey(), deletedValueSet);
					}
				}
			}

		}
		
		/*Exclude-  deleted value*/
		for(Map.Entry<String, Set<String>> existingMap : existingExcludedMap.entrySet()){
			List<String> fromUIList = programAchieveOn.getExcludedMap().get(existingMap.getKey());
			Set<String> existingValueSet = existingMap.getValue();
			if(fromUIList != null){
				for(String value : existingValueSet){
					if(!fromUIList.contains(value)){
						if(deletedValueForTagId.get(existingMap.getKey()) != null){
							Set<String> deletedValueSet = deletedValueForTagId.get(existingMap.getKey());
							deletedValueSet.add(value);
						}
						else{
							Set<String> deletedValueSet = new HashSet<String>();
							deletedValueSet.add(value);
							deletedValueForTagId.put(existingMap.getKey(), deletedValueSet);
						}
					}
				}				
			}
			else{
				for(String value : existingValueSet){
					if(deletedValueForTagId.get(existingMap.getKey()) != null){
						Set<String> deletedValueSet = deletedValueForTagId.get(existingMap.getKey());
						deletedValueSet.add(value);
					}
					else{
						Set<String> deletedValueSet = new HashSet<String>();
						deletedValueSet.add(value);
						deletedValueForTagId.put(existingMap.getKey(), deletedValueSet);
					}
				}
			}

		}
		return deletedValueForTagId;
	}
	
	/**
	 * Method to get the first name  and last name concatenated.
	 * @param dalEmployee dalEmployee
	 * @return String i.e, first name and last name.
	 */
	public static String getName(DalEmployee dalEmployee){
		String name = null;
		if(dalEmployee != null){
			name = dalEmployee.getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + dalEmployee.getLAST_NAME();
		}
		return name;
	}

	/**
	 * populateWorkflowStatusData method is used to get the latest workflow status details.
	 * @param programHeader programHeader
	 * @param dalProgramDetail dalProgramDetail
	 */
	public static void populateWorkflowStatusData(ProgramHeader programHeader, DalProgramDetail dalProgramDetail) {
		if(programHeader != null && dalProgramDetail != null){
			List<DalWorkflowStatus> dalWorkflowStatusList = dalProgramDetail.getDalWorkflowStatusList();
			if(dalWorkflowStatusList != null && !dalWorkflowStatusList.isEmpty()){
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
				
				for(DalWorkflowStatus dalWorkflowStatus : dalWorkflowStatusList){
					ProgramWorkflowStatus programWorkflowStatus = new ProgramWorkflowStatus();
					
					programWorkflowStatus.setApprovalDate(dalWorkflowStatus.getModifiedDate().getTime());
					programWorkflowStatus.setApproverName(ProgramServiceHelper.getName(dalWorkflowStatus.getApprover()));
					programWorkflowStatus.setApproverRole(dalWorkflowStatus.getApprover().getTITLE().getTitle());
					programWorkflowStatus.setStatus(dalWorkflowStatus.getApprovalStatus().getType());
					if(programHeader.getProgramWorkflowStatusList() == null){
						programHeader.setProgramWorkflowStatusList(new ArrayList<ProgramWorkflowStatus>());
					}
					programHeader.getProgramWorkflowStatusList().add(programWorkflowStatus);
				}
			}
		}
	}
}
