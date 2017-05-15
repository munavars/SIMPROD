package com.ytc.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.enums.BTLEnum;
import com.ytc.common.enums.TagItemValueMapEnum;
import com.ytc.common.model.DropDown;
import com.ytc.common.model.Employee;
import com.ytc.common.model.ProgramAchieveOn;
import com.ytc.common.model.ProgramDetail;
import com.ytc.common.model.ProgramDetailsDropDown;
import com.ytc.common.model.ProgramHeader;
import com.ytc.common.model.ProgramInputParam;
import com.ytc.common.model.ProgramPaidOn;
import com.ytc.common.model.ProgramTierDetail;
import com.ytc.constant.ProgramConstant;
import com.ytc.constant.QueryConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalBaseItems;
import com.ytc.dal.model.DalCustomer;
import com.ytc.dal.model.DalEmployee;
import com.ytc.dal.model.DalEmployeeHierarchy;
import com.ytc.dal.model.DalFrequency;
import com.ytc.dal.model.DalPaidType;
import com.ytc.dal.model.DalPricingType;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalProgramDetailTier;
import com.ytc.dal.model.DalProgramHeader;
import com.ytc.dal.model.DalProgramMaster;
import com.ytc.dal.model.DalProgramType;
import com.ytc.dal.model.DalTagItems;
import com.ytc.helper.ProgramServiceHelper;
import com.ytc.service.IProgramService;

/**
 * @author Cognizant.
 *
 */
public class ProgramServiceImpl implements IProgramService {

	@Autowired
	private IDataAccessLayer baseDao;

	@Override
	public ProgramHeader getProgramDetails(ProgramInputParam inputParam) {
		ProgramHeader programHeader = new ProgramHeader();
		/**
		 * Irrespective of whether program id is null or not, drop down list has to be initialized.
		 * First step in this method is create program master dropdown value.
		 * */
		DalProgramDetail dalProgramDetail = null;
		if(inputParam.getProgramDetailId() != null){
			dalProgramDetail =  baseDao.getById(DalProgramDetail.class, inputParam.getProgramDetailId() );
		}
		
		if(inputParam.isExistingDetail()){
			if(dalProgramDetail.getDalProgramType() != null && dalProgramDetail.getDalProgramType().getType() != null){
				inputParam.setProgramType(dalProgramDetail.getDalProgramType().getType());
				inputParam.setProgramTypeId( dalProgramDetail.getDalProgramType().getId());
				getProgramTypeDetails(inputParam, programHeader);
			}
		}
		else{
			getProgramTypeDetails(inputParam, programHeader);	
		}
		
		if(dalProgramDetail != null){
			getExistingProgramDetails(inputParam.getEmployee(), programHeader, dalProgramDetail);
		}	
		else{
			getNewProgramDetails(inputParam.getCustomerId(), inputParam.getEmployee(), programHeader);
		}
		programHeader.getProgramDetailList().get(0).setProgramTypeId(inputParam.getProgramTypeId());
		
		return programHeader;
	}


	private void getProgramTypeDetails(ProgramInputParam inputParam, ProgramHeader programHeader) {
		if(inputParam.getProgramType() != null){
			List<DalProgramType> dalProgramTypeList = baseDao.getListFromNamedQuery("DalProgramType.getAllDetails");
			if(dalProgramTypeList != null){
				for(DalProgramType dalProgramType : dalProgramTypeList){
					if(ProgramConstant.CALCULATED_PROGRAM_TYPE.equalsIgnoreCase(dalProgramType.getType()) && 
							dalProgramType.getType().equalsIgnoreCase(inputParam.getProgramType()) ){
						inputParam.setProgramTypeId(dalProgramType.getId());
						programHeader.setCalculatedProgram(true);
						break;
					}
					else if(dalProgramType.getType().equalsIgnoreCase(inputParam.getProgramType())){
						inputParam.setProgramTypeId(dalProgramType.getId());
						programHeader.setCalculatedProgram(false);
						break;
					}
				}
			}
		}
		else{
			programHeader.setCalculatedProgram(true);
			List<DalProgramType> dalProgramTypeList = baseDao.getListFromNamedQuery("DalProgramType.getAllDetails");
			if(dalProgramTypeList != null){
				for(DalProgramType dalProgramType : dalProgramTypeList){
					if(ProgramConstant.CALCULATED_PROGRAM_TYPE.equalsIgnoreCase(dalProgramType.getType()) ){
						inputParam.setProgramTypeId(dalProgramType.getId());
						break;
					}
				}
			}
		}
	}


	private void getExistingProgramDetails(Employee employee, ProgramHeader programHeader,
											DalProgramDetail dalProgramDetail) {
		String customerId = ""; 
		if(dalProgramDetail.getPayTo() != null){
			customerId = String.valueOf( dalProgramDetail.getPayTo());	
		}

		populateDropDownValues(programHeader, customerId);
		DalProgramHeader dalProgramHeader = dalProgramDetail.getDalProgramHeader();
		
		populateProgramHeaderDetails(programHeader, dalProgramHeader, dalProgramDetail, employee);
		/** few more columns has to be populated here. That has to be worked on. For now, only main fields are considered.*/
		
		populateProgramDetailData(programHeader, dalProgramDetail);
		
		populatePaidBasedOnData(programHeader, dalProgramHeader, dalProgramDetail);
		
		if(programHeader.isCalculatedProgram()){
			populateAchieveBasedOnData(programHeader, dalProgramHeader, dalProgramDetail);
			
			populateTierData(programHeader, dalProgramDetail);
		}
		
		if(dalProgramHeader.getStatus() != null && !ProgramConstant.IN_PROGRESS_STATUS.equals(dalProgramHeader.getStatus().getType())){
			programHeader.setNewProgram(true);
		}
	}


	private void getNewProgramDetails(Integer custId, Employee employee, ProgramHeader programHeader) {
		populateDropDownValues(programHeader, String.valueOf(custId)); //Customer id should be 
		DalCustomer customer=baseDao.getById(DalCustomer.class, custId);
		programHeader.setCustomerId(customer.getId());
		programHeader.setCustomerName(customer.getCustomerName());
		programHeader.setBusinessUnit(customer.getBu());
		Integer employeeId = null;
		if(employee != null){
			employeeId = employee.getEMP_ID();
		}
		if(employeeId == null){ // Temporary code, this should be removed after integration testing.
			employeeId = 47;
		}
		DalEmployee emp = baseDao.getById(DalEmployee.class, employeeId); // this should be logged in user.
		programHeader.setRequestedDate(new Date());
		programHeader.setRequestId(emp.getId());
		programHeader.setRequestedBy(emp.getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + emp.getLAST_NAME());
		programHeader.setCreatedBy(emp.getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + emp.getLAST_NAME());
		programHeader.setCreatedDate(new Date());
		programHeader.setNewProgram(true);
		programHeader.setAuthorizedUser(ProgramConstant.YES);
	}


	private void populateTierData(ProgramHeader programHeader, DalProgramDetail dalProgramDetail) {
		if(dalProgramDetail != null && programHeader != null){
			List<ProgramTierDetail> programTierDetailSet = null;
			String amountTypeTier = null;
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("programDetailId", dalProgramDetail.getId());
			List<DalProgramDetailTier> dalProgramTierList = baseDao.getListFromNamedQueryWithParameter("DalProgramDetailTier.getAllTierForProgramId", 
															parameters);
			
			if(dalProgramTierList != null){
				programTierDetailSet = new ArrayList<ProgramTierDetail>();
				for(DalProgramDetailTier dalProgramDetailTier : dalProgramTierList){
					ProgramTierDetail detail = new ProgramTierDetail();
					detail.setId(dalProgramDetailTier.getId());
					detail.setLevel(dalProgramDetailTier.getLevel());
					detail.setProgramDetailId(dalProgramDetailTier.getProgramDetailId());
					detail.setTierType(dalProgramDetailTier.getTierType());
					if(amountTypeTier == null){
						amountTypeTier = dalProgramDetailTier.getTierType();
					}
					detail.setAmount(new BigDecimal(dalProgramDetailTier.getAmount()));
					detail.setBeginRange(dalProgramDetailTier.getBeginRange());
					programTierDetailSet.add(detail);
				}
				programHeader.getProgramDetailList().get(0).setProgramTierDetailList(programTierDetailSet);
				programHeader.getProgramDetailList().get(0).setAmountTypeTier(amountTypeTier);
				programHeader.getProgramDetailList().get(0).setActualMarker(dalProgramDetail.getActualMarker());
			}
		}
	}

	private void populateProgramHeaderDetails(ProgramHeader programHeader, DalProgramHeader dalProgramHeader, DalProgramDetail dalProgramDetail,
											Employee employee) {
		programHeader.setCustomerName(dalProgramHeader.getCustomer().getCustomerName());
		programHeader.setCustomerId(dalProgramHeader.getCustomer().getId());
		programHeader.setBusinessUnit(dalProgramHeader.getBu());
		programHeader.setId(dalProgramHeader.getId());
		programHeader.setRequestId(dalProgramHeader.getRequest().getId()); 
		programHeader.setRequestedBy(dalProgramHeader.getRequest().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + dalProgramHeader.getRequest().getLAST_NAME());
		if(employee != null && employee.getLOGIN_ID().equals(dalProgramHeader.getRequest().getLOGIN_ID())){
			programHeader.setAuthorizedUser(ProgramConstant.YES);
		}
		else{
			programHeader.setAuthorizedUser(ProgramConstant.NO);
		}
		programHeader.setRequestedDate( (dalProgramHeader.getRequestDate() != null ) ? dalProgramHeader.getRequestDate().getTime() : null);
		if(dalProgramHeader.getCreatedBy() != null){
			programHeader.setCreatedBy(dalProgramHeader.getCreatedBy().getUserName());
		}
		
		programHeader.setCreatedDate(dalProgramHeader.getCreatedDate().getTime());
		if(dalProgramDetail.getZmAppById() != null){
			programHeader.setZoneManagerApprovedBy(dalProgramDetail.getZmAppById().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + dalProgramDetail.getZmAppById().getLAST_NAME());
			programHeader.setZoneManagerApprovedDate( ProgramServiceHelper.convertToDateFromCalendar(dalProgramDetail.getZmAppDate()));	
		}
		if(dalProgramDetail.getDirAppById() != null){
			programHeader.setDirectorApprovedBy(dalProgramDetail.getDirAppById().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + dalProgramDetail.getDirAppById().getLAST_NAME());
			programHeader.setDirectorApprovedDate( ProgramServiceHelper.convertToDateFromCalendar(dalProgramDetail.getDirAppDate()));	
		}
		if(dalProgramDetail.getExecAppById() != null){
			programHeader.setExecutiveApprovedBy(dalProgramDetail.getExecAppById().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + dalProgramDetail.getExecAppById().getLAST_NAME());
			programHeader.setExecutiveApprovedDate( ProgramServiceHelper.convertToDateFromCalendar(dalProgramDetail.getExecAppDate()));	
		}
		if(dalProgramDetail.getTbpAppById() != null){
			programHeader.setTbpApprovedBy(dalProgramDetail.getTbpAppById().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + dalProgramDetail.getTbpAppById().getLAST_NAME());
			programHeader.setTbpApprovedDate( ProgramServiceHelper.convertToDateFromCalendar(dalProgramDetail.getTbpAppDate()));	
		}
		
		if(dalProgramDetail.getAccuralData() != null){
			programHeader.setAccrualAmount(new BigDecimal(dalProgramDetail.getAccuralData().getTotalAccuredAmount()));
			programHeader.setPaidAmount(new BigDecimal(dalProgramDetail.getAccuralData().getTotalPaidAmount()));
			programHeader.setBalance(new BigDecimal(dalProgramDetail.getAccuralData().getBalance()));	
		}
		else{
			programHeader.setAccrualAmount(new BigDecimal(0));
			programHeader.setPaidAmount(new BigDecimal(0));
			programHeader.setBalance(new BigDecimal(0));	
		}
	}

	private void populatePaidBasedOnData(ProgramHeader programHeader, DalProgramHeader dalProgramHeader, DalProgramDetail dalProgramDetail) {
		ProgramPaidOn programPaidOn = null;
		if(programHeader != null && dalProgramDetail != null && dalProgramHeader != null){
			programPaidOn = programHeader.getProgramDetailList().get(0).getProgramPaidOn();
			programPaidOn.setIsTiered("0".equals(dalProgramDetail.getIsTiered()) ? false : true);
			programPaidOn.setIsTrueUp("Y".equals(dalProgramDetail.getTrueUp()) ? true : false);
			programPaidOn.setProgramDescription(dalProgramDetail.getLongDesc());
			if(dalProgramDetail.getDalProgramDetPaidList() != null){
				Map<String, List<String>> includedMap = new HashMap<String, List<String>>();
				Map<String, List<String>> excludedMap = new HashMap<String, List<String>>();
				for(DalProgramDetPaid dalProgramDetPaid : dalProgramDetail.getDalProgramDetPaidList()){
					if("1".equals(dalProgramDetPaid.getMethod())){
						/**Include values*/
						if(includedMap.get(String.valueOf(dalProgramDetPaid.getTagId())) != null){
							List<String> includeList = includedMap.get(String.valueOf(dalProgramDetPaid.getTagId()));
							/*Below condition has to be revisited. dalProgramDetail.getDalProgramDetPaidList() returns duplicate rows. 
							 * this has to be corrected. As a work around, below condition is added.*/
							if(!includeList.contains(dalProgramDetPaid.getValue())){
								includeList.add(dalProgramDetPaid.getDisplayValue());	
							}
							
						}
						else{
							List<String> includeList = new ArrayList<String>();
							includeList.add(dalProgramDetPaid.getDisplayValue());
							includedMap.put(String.valueOf(dalProgramDetPaid.getTagId()), includeList);
						}
					}
					else if("2".equals(dalProgramDetPaid.getMethod())){
						/**Include values*/
						if(excludedMap.get(String.valueOf(dalProgramDetPaid.getTagId())) != null){
							List<String> excludeList = excludedMap.get(String.valueOf(dalProgramDetPaid.getTagId()));
							/*Below condition has to be revisited. dalProgramDetail.getDalProgramDetPaidList() returns duplicate rows. 
							 * this has to be corrected. As a work around, below condition is added.*/
							if(!excludeList.contains(dalProgramDetPaid.getDisplayValue())){
								excludeList.add(dalProgramDetPaid.getDisplayValue());	
							}
						}
						else{
							List<String> excludeList = new ArrayList<String>();
							excludeList.add(dalProgramDetPaid.getDisplayValue());
							excludedMap.put(String.valueOf(dalProgramDetPaid.getTagId()), excludeList);
						}
					}
				}
				programPaidOn.setIncludedMap(includedMap);
				programPaidOn.setExcludedMap(excludedMap);
			}
		}
	}
	
	private void populateAchieveBasedOnData(ProgramHeader programHeader, DalProgramHeader dalProgramHeader, DalProgramDetail dalProgramDetail) {
		ProgramAchieveOn programAchieveOn = null;
		if(programHeader != null && dalProgramDetail != null && dalProgramHeader != null){
			programAchieveOn = programHeader.getProgramDetailList().get(0).getProgramAchieveOn();
			if(dalProgramDetail.getAchBasedMetric() != null){
				programAchieveOn.setAchieveBasedOn(ProgramServiceHelper.convertToString(dalProgramDetail.getAchBasedMetric().getId()));	
			}
			if(dalProgramDetail.getAchBasedFreq() != null){
				programAchieveOn.setAchieveFrequency(ProgramServiceHelper.convertToString(dalProgramDetail.getAchBasedFreq().getId()));	
			}
			
			if(dalProgramDetail.getDalProgramDetAchievedList() != null){
				Map<String, List<String>> includedMap = new HashMap<String, List<String>>();
				Map<String, List<String>> excludedMap = new HashMap<String, List<String>>();
				for(DalProgramDetAchieved dalProgramDetAchieved : dalProgramDetail.getDalProgramDetAchievedList()){
					if("1".equals(dalProgramDetAchieved.getAchMethod())){
						/**Include values*/
						if(includedMap.get(String.valueOf(dalProgramDetAchieved.getAchTagId())) != null){
							List<String> includeList = includedMap.get(String.valueOf(dalProgramDetAchieved.getAchTagId()));
							if(!includeList.contains(dalProgramDetAchieved.getDisplayValue())){
								includeList.add(dalProgramDetAchieved.getDisplayValue());	
							}
						}
						else{
							List<String> includeList = new ArrayList<String>();
							includeList.add(dalProgramDetAchieved.getDisplayValue());
							includedMap.put(String.valueOf(dalProgramDetAchieved.getAchTagId()), includeList);
						}
					}
					else if("2".equals(dalProgramDetAchieved.getAchMethod())){
						/**Include values*/
						if(excludedMap.get(String.valueOf(dalProgramDetAchieved.getAchTagId())) != null){
							List<String> excludeList = excludedMap.get(String.valueOf(dalProgramDetAchieved.getAchTagId()));
							if(!excludeList.contains(dalProgramDetAchieved.getDisplayValue())){
								excludeList.add(dalProgramDetAchieved.getDisplayValue());	
							}
							
						}
						else{
							List<String> excludeList = new ArrayList<String>();
							excludeList.add(dalProgramDetAchieved.getDisplayValue());
							excludedMap.put(String.valueOf(dalProgramDetAchieved.getAchTagId()), excludeList);
						}
					}
				}
				programAchieveOn.setIncludedMap(includedMap);
				programAchieveOn.setExcludedMap(excludedMap);
			}
		}
	}

	private void populateProgramDetailData(ProgramHeader programHeader, DalProgramDetail dalProgramDetail) {
		ProgramDetail programDetail = programHeader.getProgramDetailList().get(0);
		programDetail.setId(dalProgramDetail.getId());
		programDetail.setProgramName(ProgramServiceHelper.convertToString(dalProgramDetail.getProgramMaster().getId()));
		programDetail.setPayoutFrequency(String.valueOf(dalProgramDetail.getPaidFrequency().getId()));
		programDetail.setBeginDate(ProgramServiceHelper.convertDateToRequiredFormat(dalProgramDetail.getProgramStartDate().getTime(),
																			"MM/dd/yyyy")); 
		programDetail.setEndDate(ProgramServiceHelper.convertDateToRequiredFormat(dalProgramDetail.getProgramEndDate().getTime(),
																				"MM/dd/yyyy"));
		programDetail.setBTL("Y".equals(dalProgramDetail.getBTL()) ? "Yes" : "No");
		programDetail.setPricingType( ProgramServiceHelper.convertToString(dalProgramDetail.getPricingType()));
		programDetail.setAmount(new BigDecimal(dalProgramDetail.getAccrualAmount()));
		programDetail.setAmountType(dalProgramDetail.getAccrualType());
		programDetail.setPayTo(dalProgramDetail.getPayTo());
		programDetail.setPaidType( ProgramServiceHelper.convertToString(dalProgramDetail.getPaidType()));
		programDetail.setPaidBasedOn( (dalProgramDetail.getPaidBasedOn() != null) ?
									ProgramServiceHelper.convertToString(dalProgramDetail.getPaidBasedOn().getId()) : 
										null);
		programHeader.setStatus( ProgramServiceHelper.convertToString(dalProgramDetail.getStatus().getType()));
	}

	/**
	 * 
	 * @param programMaster this value can be null or not null.
	 * @param customerId - this value will be customer id which will be used to build Pay to Drop down value.
	 */
	private void populateDropDownValues(ProgramHeader programHeader, String customerId) {
		if(programHeader == null){
			programHeader = new ProgramHeader();
		}
		
		/**
		 * Populate Program Details drop down list
		 * 1. Program Master
		 * 2. Frequency
		 * 3. BTL
		 * 4. Pricing Type
		 * 5. Paid Type
		 * 6. Pay to. Pay to is the customer Id.
		 * 7. Paid Based On	
		 * */
		ProgramDetail programDetail = new ProgramDetail();
		ProgramDetailsDropDown programDetailsDropDown = new ProgramDetailsDropDown();
		programDetailsDropDown.setProgramNameList(getProgramMasterDropDownList("DalProgramMaster.getAllDetails"));
		programDetailsDropDown.setPayoutFrequenceList(getFrequencyDropDownList("DalFrequency.getAllDetailsWithSort"));
		programDetailsDropDown.setPricingTypeList(getPricingTypeDropDownList("DalPricingType.getAllDetails"));
		programDetailsDropDown.setPaidTypeList(getPaidTypeDropDownList("DalPaidType.getAllDetails"));
		programDetailsDropDown.setPaidBasedOnList(getBaseItemsDropDownList("DalBaseItems.getAllDetails"));
		programDetailsDropDown.setBtlList(getBTLDropDownList());
		programDetailsDropDown.setPayToList(getPayToDropDownList(customerId));
		programDetail.setBeginDate(new Date());
		programDetail.setEndDate(new Date());
		programDetail.setDropdownList(programDetailsDropDown);
		/**
		 * Populate Paid Based on drop down list
		 * 1.Tag Items
		 * */
		ProgramPaidOn programPaidOn = new ProgramPaidOn();
		programPaidOn.setTagItemList(getTagItemDropDownList("DalTagItems.getAllDetails"));
		
		/**
		 * Populate Paid Achieve based on drop down list.
		 * 1.Achieve based on
		 * 2.Achieve frequency
		 * 3.Tag items.
		 * 
		 * All these values are already available in program detail and paid based on DTO's. Re use those list again.
		 * 
		 */
		ProgramAchieveOn programAchieveOn = new ProgramAchieveOn();
		programAchieveOn.setAchieveBasedOnList(programDetail.getDropdownList().getPaidBasedOnList());
		programAchieveOn.setAchieveFrequencyList(programDetail.getDropdownList().getPayoutFrequenceList());
		programAchieveOn.setTagItemList(programPaidOn.getTagItemList());
		programDetail.setProgramPaidOn(programPaidOn);
		programDetail.setProgramAchieveOn(programAchieveOn);
		
		programHeader.getProgramDetailList().add(programDetail);
		
	}
	
	private List<DropDown> getTagItemDropDownList(String namedQueryValue){
		List<DropDown> dropdownList = null;
		if(namedQueryValue != null){
			List<DalTagItems> dalTagItemsList =  baseDao.getListFromNamedQuery(namedQueryValue);
			if(dalTagItemsList != null){
				for(DalTagItems dalTagItems : dalTagItemsList){
					DropDown dropDown = new DropDown();
					dropDown.setKey(String.valueOf(dalTagItems.getId()));
					dropDown.setValue(dalTagItems.getItem());
					if(dropdownList == null){
						dropdownList = new ArrayList<DropDown>();
					}
					dropdownList.add(dropDown);
				}
			}
			
		}
		
		return dropdownList;
	}
	
	private List<DropDown> getPayToDropDownList(String customerId){
		
		List<DropDown> dropdownList = null;
		
		if(customerId != null){
			DropDown dropDown = new DropDown();
			dropDown.setKey(customerId);
			dropDown.setValue(customerId);
			dropdownList = new ArrayList<DropDown>();
			dropdownList.add(dropDown);
		}
		
		return dropdownList;		
	}
	
	private List<DropDown> getBTLDropDownList(){
		List<DropDown> dropdownList = null;
		
		List<BTLEnum> btlList = Arrays.asList(BTLEnum.values());
		if(btlList != null){
			for(BTLEnum btlEnum : btlList){
				DropDown dropDown = new DropDown();
				dropDown.setKey(btlEnum.getBtlOption());
				dropDown.setValue(btlEnum.getBtlOption());
				if(dropdownList == null){
					dropdownList = new ArrayList<DropDown>();
				}
				dropdownList.add(dropDown);
			}
		}
		
		return dropdownList;
	}
	
	private List<DropDown> getProgramMasterDropDownList(String namedQueryValue){
		List<DropDown> dropdownList = null;
		if(namedQueryValue != null){
			List<DalProgramMaster> dalProgramMasterList =  baseDao.getListFromNamedQuery(namedQueryValue);
			if(dalProgramMasterList != null){
				for(DalProgramMaster dalProgramMaster : dalProgramMasterList){
					DropDown dropDown = new DropDown();
					dropDown.setKey(String.valueOf(dalProgramMaster.getId()));
					dropDown.setValue(dalProgramMaster.getProgram());
					if(dropdownList == null){
						dropdownList = new ArrayList<DropDown>();
					}
					dropdownList.add(dropDown);
				}
			}
			
		}
		
		return dropdownList;
	}

	private List<DropDown> getFrequencyDropDownList(String namedQueryValue){
		List<DropDown> dropdownList = null;
		if(namedQueryValue != null){
			List<DalFrequency> dalFrequencyList =  baseDao.getListFromNamedQuery(namedQueryValue);
			if(dalFrequencyList != null){
				for(DalFrequency dalFrequency : dalFrequencyList){
					DropDown dropDown = new DropDown();
					dropDown.setKey(String.valueOf(dalFrequency.getId()));
					dropDown.setValue(dalFrequency.getFrequency());
					if(dropdownList == null){
						dropdownList = new ArrayList<DropDown>();
					}
					dropdownList.add(dropDown);
				}
			}
			
		}
		
		return dropdownList;
	}	
	
	private List<DropDown> getPricingTypeDropDownList(String namedQueryValue){
		List<DropDown> dropdownList = null;
		if(namedQueryValue != null){
			List<DalPricingType> dalPricingTypeList =  baseDao.getListFromNamedQuery(namedQueryValue);
			if(dalPricingTypeList != null){
				for(DalPricingType dalPricingType : dalPricingTypeList){
					DropDown dropDown = new DropDown();
					dropDown.setKey(String.valueOf(dalPricingType.getId()));
					dropDown.setValue(dalPricingType.getType());
					if(dropdownList == null){
						dropdownList = new ArrayList<DropDown>();
					}
					dropdownList.add(dropDown);
				}
			}
			
		}
		
		return dropdownList;
	}
	
	private List<DropDown> getPaidTypeDropDownList(String namedQueryValue){
		List<DropDown> dropdownList = null;
		if(namedQueryValue != null){
			List<DalPaidType> dalPaidTypeList =  baseDao.getListFromNamedQuery(namedQueryValue);
			if(dalPaidTypeList != null){
				for(DalPaidType dalPaidType : dalPaidTypeList){
					DropDown dropDown = new DropDown();
					dropDown.setKey(String.valueOf(dalPaidType.getId()));
					dropDown.setValue(dalPaidType.getType());
					if(dropdownList == null){
						dropdownList = new ArrayList<DropDown>();
					}
					dropdownList.add(dropDown);
				}
			}
			
		}
		
		return dropdownList;
	}
	
	private List<DropDown> getBaseItemsDropDownList(String namedQueryValue){
		List<DropDown> dropdownList = null;
		if(namedQueryValue != null){
			List<DalBaseItems> dalBaseItemsList =  baseDao.getListFromNamedQuery(namedQueryValue);
			if(dalBaseItemsList != null){
				for(DalBaseItems dalBaseItems : dalBaseItemsList){
					DropDown dropDown = new DropDown();
					dropDown.setKey(String.valueOf(dalBaseItems.getId()));
					dropDown.setValue(dalBaseItems.getBaseItem());
					if(dropdownList == null){
						dropdownList = new ArrayList<DropDown>();
					}
					dropdownList.add(dropDown);
				}
			}
			
		}
		
		return dropdownList;
	}

	@Override
	public List<DropDown> getTagValueDropDown(Integer tagId) {
		TagItemValueMapEnum tagItemValueMapEnum = null;
		List<DropDown> dropdownList = null;
		List<String> tagValueList = null;
		String query = null;
		if(tagId != null){
			tagItemValueMapEnum = TagItemValueMapEnum.tableDetail(tagId);
			if(tagItemValueMapEnum != null){
				/*query = String.format(QueryConstant.TAG_VALUE_LIST_BY_TAG_ID, tagItemValueMapEnum.getFetchColumnName(), tagItemValueMapEnum.getTableName()) 
						+ String.format(QueryConstant.TAG_VALUE_LIST_ORDER_BY_CLAUSE, tagItemValueMapEnum.getFetchColumnName());*/
				query = String.format(QueryConstant.TAG_VALUE_LIST_BY_TAG_ID, tagItemValueMapEnum.getFetchColumnName(), tagItemValueMapEnum.getTableName()) 
						+ QueryConstant.TAG_VALUE_LIST_ORDER_BY_CLAUSE;
				tagValueList = baseDao.getTagValue(query);
				for(String value : tagValueList){
					if(value != null){
						DropDown dropDown = new DropDown();
						dropDown.setKey(value);
						dropDown.setValue(value);
						if(dropdownList == null){
							dropdownList = new ArrayList<DropDown>();
						}
						dropdownList.add(dropDown);	
					}
				}
			}
		}
		return dropdownList;
	}
	
	
	@Override
	public List<ProgramDetail> getProgram(String custId, String status) {
		List<ProgramDetail> programDetailList= new ArrayList<ProgramDetail>();
		DecimalFormat df = new DecimalFormat("#.##"); 
		String sql=QueryConstant.PROGRAM_LIST;
		List<String> selectedValues = Arrays.asList(status.split(","));
		List<String> customerId = Arrays.asList(custId.split(","));
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("custId", customerId);
		queryParams.put("status", selectedValues);
		
		List<DalProgramDetail> resultList =baseDao.getlist(DalProgramDetail.class, sql, queryParams);
		//This section shld be moved to converter object
		for (Iterator<DalProgramDetail> iterator = resultList.iterator(); iterator.hasNext();) {
			DalProgramDetail dalProgramDetail = (DalProgramDetail) iterator.next();
			ProgramDetail programDetail =new ProgramDetail();
			//String queryString="select cast(CUSTOMER_NAME as varchar) CUSTOMER_NAME from CUSTOMER where Id in(select CUSTOMER_id from PROGRAM_HEADER where Id in(select pgm_hdr_id from PROGRAM_DETAIL where id=:id))";
/*			String queryString="select * from CUSTOMER where Id in(select CUSTOMER_id from PROGRAM_HEADER where Id in(select pgm_hdr_id from PROGRAM_DETAIL where id=:id))";
			Map<String, Object> querParams = new HashMap<>();
			querParams.put("id", dalProgramDetail.getId());
			List<DalCustomer> custlist=baseDao.getlist(DalCustomer.class,queryString,querParams);
			programDetail.setCustomerName(custlist.get(0).getCustomerName());
			programDetail.setCustomerId(custlist.get(0).getId().toString());*/
			programDetail.setCustomerName(dalProgramDetail.getDalProgramHeader().getCustomer().getCustomerName());
			programDetail.setCustomerId(dalProgramDetail.getDalProgramHeader().getCustomer().getId().toString());
			programDetail.setProgramId(dalProgramDetail.getId());
			programDetail.setProgramName(dalProgramDetail.getProgramMaster().getProgram());
			programDetail.setPayoutFrequency(dalProgramDetail.getPaidFrequency().getFrequency());
			programDetail.setBeginDate(dalProgramDetail.getProgramStartDate().getTime());
			programDetail.setEndDate(dalProgramDetail.getProgramEndDate().getTime());
			programDetail.setDisplayBeginDate(ProgramServiceHelper.convertDateToString(dalProgramDetail.getProgramStartDate().getTime(), ProgramConstant.DATE_FORMAT));
			programDetail.setDisplayEndDate(ProgramServiceHelper.convertDateToString(dalProgramDetail.getProgramEndDate().getTime(), ProgramConstant.DATE_FORMAT));
			programDetail.setBTL("Y".equalsIgnoreCase(dalProgramDetail.getBTL())?ProgramConstant.YES:ProgramConstant.NO);
			if(dalProgramDetail.getDalProgramType() != null && dalProgramDetail.getDalProgramType().getId() == 1){
				programDetail.setPricingType(baseDao.getById(DalPricingType.class, dalProgramDetail.getPricingType()).getType());	
			}
			if(dalProgramDetail.getPaidBasedOn() != null){
				programDetail.setPaidBasedOn(dalProgramDetail.getPaidBasedOn().getBaseItem());	
			}
			if(dalProgramDetail.getAchBasedMetric() != null){
				programDetail.setAchievedBasedOn(dalProgramDetail.getAchBasedMetric().getBaseItem());	
			}
			if(dalProgramDetail.getIsTiered() != null){
				programDetail.setIsTiered(dalProgramDetail.getIsTiered().equalsIgnoreCase(ProgramConstant.ZERO)?ProgramConstant.YES:ProgramConstant.NO);	
			}
			if(dalProgramDetail.getTrueUp() != null){
				programDetail.setTrueUp(dalProgramDetail.getTrueUp().equalsIgnoreCase("Y")?ProgramConstant.YES:ProgramConstant.NO);	
			}
			programDetail.setAccrualAmount(Double.valueOf(df.format(dalProgramDetail.getAccrualAmount())));
			programDetail.setAccrualType(dalProgramDetail.getAccrualType());
			
			programDetail.setCurrentTier(Integer.toString(dalProgramDetail.getForecastMarker()));
			programDetail.setBeginRange(null!=dalProgramDetail.getPgmDetailTier()?Integer.toString(dalProgramDetail.getPgmDetailTier().getBeginRange()):ProgramConstant.ZERO);
			String tierRate=ProgramConstant.ZERO;
			if(null!=dalProgramDetail.getPgmDetailTier()){
				if("$".equalsIgnoreCase(dalProgramDetail.getPgmDetailTier().getTierType())){
					tierRate=(dalProgramDetail.getPgmDetailTier().getTierType()+Double.toString(dalProgramDetail.getPgmDetailTier().getAmount()));
				}else{
					tierRate=(Double.toString(dalProgramDetail.getPgmDetailTier().getAmount())+dalProgramDetail.getPgmDetailTier().getTierType());
				}
			}
			programDetail.setTierRate(tierRate);
			programDetail.setAccruedAmount(null!=dalProgramDetail.getAccuralData()?dalProgramDetail.getAccuralData().getTotalAccuredAmount():0);
			programDetail.setCreditAmount(0);
			programDetail.setPayables(null!=dalProgramDetail.getAccuralData()?dalProgramDetail.getAccuralData().getTotalPaidAmount():0);
			programDetail.setGlBalance(null!=dalProgramDetail.getAccuralData()?dalProgramDetail.getAccuralData().getBalance():ProgramConstant.ZERO);
			programDetail.setLongDesc(null!=dalProgramDetail.getLongDesc()?dalProgramDetail.getLongDesc():"Empty");
			programDetail.setProgramType(dalProgramDetail.getDalProgramType() != null ? dalProgramDetail.getDalProgramType().getType() : null);
			programDetailList.add(programDetail);
		}
		

		return programDetailList;
	}
	
	/* (non-Javadoc)
	 * @see com.ytc.service.IProgramService#addProgramTier(java.lang.String)
	 */
	@Override
	public String addProgramTier(String id) {
		String status="success";
		DalProgramDetailTier dalProgramDetailTier=new DalProgramDetailTier();
		//values hard coded for testing
//		dalProgramDetailTier.setId(99999);
		dalProgramDetailTier.setAmount(20);
		dalProgramDetailTier.setBeginRange(1000);
		dalProgramDetailTier.setLevel(1);
		dalProgramDetailTier.setProgramDetailId(42);
		dalProgramDetailTier.setTierType("%");
		try{
			baseDao.create(dalProgramDetailTier, 5);
		}catch (Exception e){
			status="failure";	
		}
		return status;
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.ytc.service.IProgramService#updateProgramTier(java.lang.String)
	 */
	@Override
	public String updateProgramTier(ProgramTierDetail programTierDetail) {
		String status="success";
		DalProgramDetailTier dalProgramDetailTier=new DalProgramDetailTier();
		try {
			dalProgramDetailTier= baseDao.getById(DalProgramDetailTier.class, programTierDetail.getId());// new ObjectMapper().readValue(programTierDetail.getId(), DalProgramDetailTier.class);
			if((null!=dalProgramDetailTier.getId())&&(!"".equalsIgnoreCase(dalProgramDetailTier.getId().toString()))){
				dalProgramDetailTier.setAmount(programTierDetail.getAmount().doubleValue());
				baseDao.update(dalProgramDetailTier, 5);
			}else{
				baseDao.create(dalProgramDetailTier, 5);	
			} 

		}catch (Exception e){
			status="failure";
		}
		
		return status;
		
	}
	
	/* (non-Javadoc)
	 * @see com.ytc.service.IProgramService#deleteProgramTier(java.lang.String)
	 */
	@Override
	public String deleteProgramTier(String id) {
		String status="success";
		try{
			baseDao.delete(DalProgramDetailTier.class, Integer.parseInt(id));
		}catch(Exception e){
			status="failure";
		}
		
		return status;
		
	}
	
	/* (non-Javadoc)
	 * @see com.ytc.service.IProgramService#getProgramDashboard()
	 */
	@Override
	public List<ProgramDetail> getProgramDashboard(Integer id){
		Integer empId=id;
		List<ProgramDetail> pgm=new ArrayList<ProgramDetail>();
		String queryString = "SELECT BASE_EMP_ID FROM EMPLOYEE_HIERARCHY where BASE_EMP_ID = '"+ empId+"' or LVL1_EMP_ID = '"+empId+"' or LVL2_EMP_ID = '"+empId+
					"' or LVL3_EMP_ID = '"+empId+"' or LVL4_EMP_ID = '"+empId+"' or LVL5_EMP_ID = '"+empId+"'";

			Map<String, Object> queryParams = new HashMap<>();
			List<String> userIdList=baseDao.getListFromNativeQuery(queryString,queryParams);
			if((userIdList.size()>1)||((userIdList.size()==1)&&(baseDao.getEntityById(DalEmployeeHierarchy.class, Integer.parseInt(userIdList.get(0))).getBaseTitle().equalsIgnoreCase("Account Manager")))){
				String sql="select ID from CUSTOMER where ACCOUNT_MANAGER in(:userId)";
				queryParams.put("userId", userIdList);
				List<String> customerId=baseDao.getListFromNativeQuery(sql, queryParams);		
				pgm=getProgram(customerId.toString().substring(1, customerId.toString().length()-1),"0,4");
			}
			
		return pgm;
	}
	
}
