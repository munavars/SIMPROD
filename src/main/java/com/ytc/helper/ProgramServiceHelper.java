package com.ytc.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ytc.common.model.ProgramAchieveOn;
import com.ytc.common.model.ProgramPaidOn;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;

public class ProgramServiceHelper {
	
	public static String convertToString(Object obj){
		String returnValue = null;
		if(obj != null){
			returnValue = String.valueOf(obj);
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
			convertedDateString = sdf.format(new Date());
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
						existingIncludeList.add(dalProgramDetPaid.getValue());
					}
					else{
						existingIncludeList = new HashSet<String>(); 
						existingIncludeList.add(dalProgramDetPaid.getValue());
						existingIncludedMap.put(String.valueOf(dalProgramDetPaid.getTagId()), existingIncludeList);
					}
				}
				else if(ProgramConstant.EXCLUDED.equals(dalProgramDetPaid.getMethod())){
					if(existingExcludedMap.get(String.valueOf(dalProgramDetPaid.getTagId())) != null){
						existingExcludeList = existingExcludedMap.get(String.valueOf(dalProgramDetPaid.getTagId()));
						existingExcludeList.add(dalProgramDetPaid.getValue());
					}
					else{
						existingExcludeList = new HashSet<String>(); 
						existingExcludeList.add(dalProgramDetPaid.getValue());
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
						existingIncludeList.add(dalProgramDetPaid.getAchValue());
					}
					else{
						existingIncludeList = new HashSet<String>(); 
						existingIncludeList.add(dalProgramDetPaid.getAchValue());
						existingIncludedMap.put(String.valueOf(dalProgramDetPaid.getAchTagId()), existingIncludeList);
					}
				}
				else if(ProgramConstant.EXCLUDED.equals(dalProgramDetPaid.getAchMethod())){
					if(existingExcludedMap.get(String.valueOf(dalProgramDetPaid.getAchTagId())) != null){
						existingExcludeList = existingExcludedMap.get(String.valueOf(dalProgramDetPaid.getAchTagId()));
						existingExcludeList.add(dalProgramDetPaid.getAchValue());
					}
					else{
						existingExcludeList = new HashSet<String>(); 
						existingExcludeList.add(dalProgramDetPaid.getAchValue());
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
}
