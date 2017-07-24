package com.ytc.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.enums.BTLEnum;
import com.ytc.common.enums.BusinessUnitDescriptionEnum;
import com.ytc.common.enums.TagItemValueMapEnum;
import com.ytc.common.model.DropDown;
import com.ytc.common.model.Employee;
import com.ytc.common.model.NewCustomerDetail;
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
import com.ytc.dal.model.DalFrequency;
import com.ytc.dal.model.DalGLCode;
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
import com.ytc.helper.ProgramServiceWorkflowHelper;
import com.ytc.service.IProgramService;
import com.ytc.service.util.PdfGenerator;

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
			getButtonBehaviorDetails(inputParam.getEmployee(), programHeader, dalProgramDetail);
		}	
		else{
			getNewProgramDetails(inputParam.getCustomerId(), inputParam.getEmployee(), programHeader);
			getNewProgramButtonBehaviorDetails(programHeader);
		}
		programHeader.getProgramDetailList().get(0).setProgramTypeId(inputParam.getProgramTypeId());
		programHeader.getProgramDetailList().get(0).setProgramType(inputParam.getProgramType());
		return programHeader;
	}


	private void getNewProgramButtonBehaviorDetails(ProgramHeader programHeader) {
		if(programHeader != null){
			ProgramServiceWorkflowHelper.setNewProgramButtonProperties(programHeader);
		}
	}


	private void getButtonBehaviorDetails(Employee employee, ProgramHeader programHeader,
			DalProgramDetail dalProgramDetail) {
		if(employee != null && programHeader != null && dalProgramDetail != null){
			ProgramServiceWorkflowHelper.setProgramButtonProperties(employee, programHeader, dalProgramDetail);
		}
		
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
		/**Comments section*/
		populateCommentaryData(programHeader, dalProgramDetail);
		
		/**populate the workflow status*/
		ProgramServiceHelper.populateWorkflowStatusData(programHeader, dalProgramDetail);
	}

	private void populateCommentaryData(ProgramHeader programHeader, DalProgramDetail dalProgramDetail) {
		if(programHeader != null && dalProgramDetail != null && dalProgramDetail.getDalUserComments() != null){
			programHeader.getProgramDetailList().get(0).setComments(dalProgramDetail.getDalUserComments().getUserComments());
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
		DalEmployee emp = baseDao.getById(DalEmployee.class, employeeId); // this should be logged in user.
		programHeader.setRequestedDate(new Date());
		programHeader.setRequestId(emp.getId());
		programHeader.setRequestedBy(emp.getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + emp.getLAST_NAME());
		programHeader.setCreatedBy(emp.getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + emp.getLAST_NAME());
		programHeader.setCreatedDate(new Date());
		programHeader.setNewProgram(true);
		programHeader.setAuthorizedUser(ProgramConstant.YES);
	}


	public void populateTierData(ProgramHeader programHeader, DalProgramDetail dalProgramDetail) {
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
				//programHeader.getProgramDetailList().get(0).setActualMarker(dalProgramDetail.getActualMarker());
				programHeader.getProgramDetailList().get(0).setSchdTierMarker(dalProgramDetail.getSchdTierMarker());
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
		if(dalProgramDetail.getCreatedBy() != null){
			programHeader.setCreatedBy(dalProgramDetail.getCreatedBy().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + dalProgramDetail.getCreatedBy().getLAST_NAME());
		}
		
		programHeader.setCreatedDate(dalProgramDetail.getCreatedDate().getTime());
		/*if(dalProgramDetail.getZmAppById() != null){
			programHeader.setZoneManagerApprovedBy(dalProgramDetail.getZmAppById().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + dalProgramDetail.getZmAppById().getLAST_NAME());
			programHeader.setZoneManagerApprovedDate( ProgramServiceHelper.convertToDateFromCalendar(dalProgramDetail.getZmAppDate()));	
		}*/
		/*if(dalProgramDetail.getDirAppById() != null){
			programHeader.setDirectorApprovedBy(dalProgramDetail.getDirAppById().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + dalProgramDetail.getDirAppById().getLAST_NAME());
			programHeader.setDirectorApprovedDate( ProgramServiceHelper.convertToDateFromCalendar(dalProgramDetail.getDirAppDate()));	
		}
		if(dalProgramDetail.getExecAppById() != null){
			programHeader.setExecutiveApprovedBy(dalProgramDetail.getExecAppById().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + dalProgramDetail.getExecAppById().getLAST_NAME());
			programHeader.setExecutiveApprovedDate( ProgramServiceHelper.convertToDateFromCalendar(dalProgramDetail.getExecAppDate()));	
		}*/
		/*if(dalProgramDetail.getTbpAppById() != null){
			programHeader.setTbpApprovedBy(dalProgramDetail.getTbpAppById().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + dalProgramDetail.getTbpAppById().getLAST_NAME());
			programHeader.setTbpApprovedDate( ProgramServiceHelper.convertToDateFromCalendar(dalProgramDetail.getTbpAppDate()));	
		}*/
		
		if(dalProgramDetail.getAccuralData() != null){
			/**Commenting the below lines due to recent changes in ACCRUAL_DATA table.*/
			/*programHeader.setAccrualAmount(new BigDecimal(dalProgramDetail.getAccuralData().getTotalAccuredAmount()));
			programHeader.setPaidAmount(new BigDecimal(dalProgramDetail.getAccuralData().getTotalPaidAmount()));
			programHeader.setBalance(new BigDecimal(dalProgramDetail.getAccuralData().getBalance()));	*/
			programHeader.setAccrualAmount(new BigDecimal(0));
			programHeader.setPaidAmount(new BigDecimal(0));
			programHeader.setBalance(new BigDecimal(0));	
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
		programDetail.setLongDesc(dalProgramDetail.getLongDesc());
		programHeader.setStatus( ProgramServiceHelper.convertToString(dalProgramDetail.getStatus().getType()));
		programDetail.setTbpcheck(dalProgramDetail.getTbpCheck());
		programDetail.setGlCode(dalProgramDetail.getGlCode());
		programDetail.setEstimatedAccrual(dalProgramDetail.getEstimatedAccrual());
		if(dalProgramDetail.getCreatedDate() != null){
			programDetail.setCreatedDate( ProgramServiceHelper.convertDateToString(dalProgramDetail.getCreatedDate().getTime(), ProgramConstant.DATE_FORMAT));			
		}
		
		if(dalProgramDetail.getModifiedDate() != null){
			programDetail.setModifiedDate(ProgramServiceHelper.convertDateToString(dalProgramDetail.getModifiedDate().getTime(), ProgramConstant.DATE_FORMAT) );
		}
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
		 * 8. GL Code
		 * */
		ProgramDetail programDetail = new ProgramDetail();
		ProgramDetailsDropDown programDetailsDropDown = new ProgramDetailsDropDown();
		programDetailsDropDown.setProgramNameList(getProgramMasterDropDownList("DalProgramMaster.getAllDetails"));
		programDetailsDropDown.setPayoutFrequenceList(getFrequencyDropDownList("DalFrequency.getAllDetailsWithSort"));
		programDetailsDropDown.setPricingTypeList(getPricingTypeDropDownList("DalPricingType.getAllDetails"));
		programDetailsDropDown.setPaidTypeList(getPaidTypeDropDownList("DalPaidType.getAllDetails"));
		programDetailsDropDown.setPaidBasedOnList(getBaseItemsDropDownList("DalBaseItems.getAllDetails",programDetail));
		programDetailsDropDown.setBtlList(getBTLDropDownList());
		programDetailsDropDown.setPayToList(getPayToDropDownList(customerId));
		programDetailsDropDown.setGlCodeList(getGLCodeDropDownList("DalGLCode.getAllDetails",programDetail));
		programDetailsDropDown.setSapGlCodeList(getSapGLCodeDropDownList("DalGLCode.getAllDetails", programDetail));
		Date date = new Date();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.MONTH, 0);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
		programDetail.setBeginDate(cal.getTime());
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
		List<DropDown> dropdownList = new ArrayList<DropDown>();
		populateDefaultValuesForTag(dropdownList);
		if(namedQueryValue != null){
			List<DalTagItems> dalTagItemsList =  baseDao.getListFromNamedQuery(namedQueryValue);
			if(dalTagItemsList != null){
				for(DalTagItems dalTagItems : dalTagItemsList){
					if(dalTagItems.getTagLevel()!=0){
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
			
		}
		
		return dropdownList;
	}
	
	private void populateDefaultValuesForTag(List<DropDown> dropdownList) {
		//Commercial 
		DropDown dropDown = new DropDown();
		dropDown.setKey(ProgramConstant.COMMERCIAL);
		dropDown.setValue(TagItemValueMapEnum.COMMERCIAL_PRODUCT_LINE.getMasterColumnName());
		dropdownList.add(dropDown);
		//Consumer
		dropDown = new DropDown();
		dropDown.setKey(ProgramConstant.CONSUMER);
		dropDown.setValue(TagItemValueMapEnum.CONSUMER_PRODUCT_LINE.getMasterColumnName());
		dropdownList.add(dropDown);
		//OTR
		dropDown = new DropDown();
		dropDown.setKey(ProgramConstant.OTR);
		dropDown.setValue(TagItemValueMapEnum.OTR_PRODUCT_LINE.getMasterColumnName());
		dropdownList.add(dropDown);
	}


	private List<DropDown> getPayToDropDownList(String customerId){
		
		List<DropDown> dropdownList = null;
		if(customerId != null){
			DalCustomer dalCustomer = null;
			
			Map<String, Object> inputMap = new HashMap<String, Object>();
			inputMap.put("id", Integer.valueOf(customerId));
			List<DalCustomer> dalCustomerList = baseDao.getListFromNamedQueryWithParameter("DalCustomer.getCustomerNumber", inputMap);
			if(dalCustomerList != null && !dalCustomerList.isEmpty()){
				dalCustomer = dalCustomerList.get(0);
			}
			
			DropDown dropDown = new DropDown();
			dropDown.setKey(customerId);
			if(dalCustomer != null){
				dropDown.setValue(dalCustomer.getCustomerNumber());	
			}
			else{
				/** Below line is needed for some legacy data, as pay to value in certain records are not matching
				 * with customer information and data is missing in customer table.
				 * In almost all cases, newly created record will not satisfy this below condition.*/
				dropDown.setValue(customerId);
			}
			
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
					if(!"0".equalsIgnoreCase(dalFrequency.getFrequency())){
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
	
	private List<DropDown> getBaseItemsDropDownList(String namedQueryValue,ProgramDetail programDetail){
		List<DropDown> dropdownList = null;
		if(namedQueryValue != null){
			List<DalBaseItems> dalBaseItemsList =  baseDao.getListFromNamedQuery(namedQueryValue);
			programDetail.setBaseItemList(dalBaseItemsList);
			if(dalBaseItemsList != null){
				for(DalBaseItems dalBaseItems : dalBaseItemsList){
					if(!"0".equalsIgnoreCase(dalBaseItems.getBaseItem())){
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
			
		}
		
		return dropdownList;
	}

	@Override
	public List<DropDown> getTagValueDropDown(Integer tagId, Integer employeeId) {
		TagItemValueMapEnum tagItemValueMapEnum = null;
		List<DropDown> dropdownList = null;
		List<String> tagValueList = null;
		String query = null;
		if(tagId != null){
			tagItemValueMapEnum = TagItemValueMapEnum.tableDetail(tagId);
			if(tagItemValueMapEnum != null){
				query = String.format(QueryConstant.TAG_VALUE_LIST_BY_TAG_ID, tagItemValueMapEnum.getFetchColumnName(), tagItemValueMapEnum.getTableName()); 
				if(TagItemValueMapEnum.SHIP_TO_NUMBER.getTagId().equals(tagItemValueMapEnum.getTagId()) || 
						TagItemValueMapEnum.BILL_TO_NUMBER.getTagId().equals(tagItemValueMapEnum.getTagId()) ){
					query += String.format(QueryConstant.TAG_VALUE_CUSTOMER_WHERE_CLAUSE, employeeId);
				}
				
				query += QueryConstant.TAG_VALUE_LIST_ORDER_BY_CLAUSE;
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
		DecimalFormat cf = new DecimalFormat("#,###");
		String sql=QueryConstant.PROGRAM_LIST;
		Map<String, Object> queryParams = new HashMap<>();
		if("0".equalsIgnoreCase(custId)){
			sql=QueryConstant.PROGRAM_LIST_ALL;
			List<String> selectedValues = Arrays.asList(status.split(","));
			queryParams.put("status", selectedValues);
		}else{
			List<String> selectedValues = Arrays.asList(status.split(","));
			List<String> customerId = Arrays.asList(custId.split(","));
			queryParams.put("custId", customerId);
			queryParams.put("status", selectedValues);
		}
		
		
		List<DalProgramDetail> resultList =baseDao.getlist(DalProgramDetail.class, sql, queryParams);
		//This section shld be moved to converter object
		for (Iterator<DalProgramDetail> iterator = resultList.iterator(); iterator.hasNext();) {
			DalProgramDetail dalProgramDetail = (DalProgramDetail) iterator.next();
			ProgramDetail programDetail =new ProgramDetail();
			programDetail.setCustomerName(dalProgramDetail.getDalProgramHeader().getCustomer().getCustomerName());
			programDetail.setCustomerId(dalProgramDetail.getDalProgramHeader().getCustomer().getId().toString());
			programDetail.setProgramId(dalProgramDetail.getId());
			programDetail.setProgramName(dalProgramDetail.getProgramMaster().getProgram());
			programDetail.setPayoutFrequency(null!=dalProgramDetail.getPaidFrequency()?dalProgramDetail.getPaidFrequency().getFrequency():"");
			programDetail.setBeginDate(dalProgramDetail.getProgramStartDate().getTime());
			programDetail.setEndDate(dalProgramDetail.getProgramEndDate().getTime());
			programDetail.setDisplayBeginDate(ProgramServiceHelper.convertDateToString(dalProgramDetail.getProgramStartDate().getTime(), ProgramConstant.DATE_FORMAT));
			programDetail.setDisplayEndDate(ProgramServiceHelper.convertDateToString(dalProgramDetail.getProgramEndDate().getTime(), ProgramConstant.DATE_FORMAT));
			programDetail.setBTL("Y".equalsIgnoreCase(dalProgramDetail.getBTL())?ProgramConstant.YES:ProgramConstant.NO);
			/**Below commented condition is wrong. Need to check.*/
/*			if(dalProgramDetail.getDalProgramType() != null && dalProgramDetail.getDalProgramType().getId() == 1){
				programDetail.setPricingType(baseDao.getById(DalPricingType.class, dalProgramDetail.getPricingType()).getType());	
			}*/
			if(dalProgramDetail.getPricingType() != null && dalProgramDetail.getDalProgramType() != null && dalProgramDetail.getDalProgramType().getId() == 1){

				programDetail.setPricingType(baseDao.getById(DalPricingType.class, dalProgramDetail.getPricingType()).getType());	
			}else{
				programDetail.setPricingType("");
			}
			
			programDetail.setPaidBasedOn(null!=dalProgramDetail.getPaidBasedOn()?dalProgramDetail.getPaidBasedOn().getBaseItem():"");
			programDetail.setAchievedBasedOn(null!=dalProgramDetail.getAchBasedMetric()?dalProgramDetail.getAchBasedMetric().getBaseItem():"");
			if(dalProgramDetail.getIsTiered() != null){
				programDetail.setIsTiered(ProgramConstant.ZERO.equalsIgnoreCase(dalProgramDetail.getIsTiered())?ProgramConstant.NO:ProgramConstant.YES);
			}
			
			/*String accuralAmount=ProgramConstant.ZERO;
			if(dalProgramDetail.getAccrualAmount()!=0 ){
				if("$".equalsIgnoreCase(dalProgramDetail.getAccrualType())){
					accuralAmount=applyStyle(dalProgramDetail.getAccrualType()+df.format(dalProgramDetail.getAccrualAmount()));
				}else{
					accuralAmount=(df.format(dalProgramDetail.getAccrualAmount())+dalProgramDetail.getAccrualType());
				}
			 }*/	
			programDetail.setAccrualAmount(frameTheAmount(dalProgramDetail.getAccrualAmount(),dalProgramDetail.getAccrualType()));
			
			
			programDetail.setAccrualType(dalProgramDetail.getAccrualType());
			if(dalProgramDetail.getTrueUp() != null){
				programDetail.setTrueUp("Y".equalsIgnoreCase(dalProgramDetail.getTrueUp())?ProgramConstant.YES:ProgramConstant.NO);
			}
			//programDetail.setCurrentTier(Integer.toString(dalProgramDetail.getActualMarker()));
			programDetail.setCurrentTier(Integer.toString(dalProgramDetail.getSchdTierMarker()==null?0:dalProgramDetail.getSchdTierMarker()));
			programDetail.setBeginRange(null!=dalProgramDetail.getPgmDetailTier()?cf.format(dalProgramDetail.getPgmDetailTier().getBeginRange()):ProgramConstant.ZERO);
			String tierRate=ProgramConstant.ZERO;
			if(null!=dalProgramDetail.getPgmDetailTier()){
				tierRate=frameTheAmount(dalProgramDetail.getPgmDetailTier().getAmount(),dalProgramDetail.getPgmDetailTier().getTierType());
			}
			programDetail.setTierRate(tierRate);
			programDetail.setAccruedAmount("0");//null!=dalProgramDetail.getAccuralData()?df.format(dalProgramDetail.getAccuralData().getTotalAccuredAmount()):ProgramConstant.ZERO);
			programDetail.setCreditAmount(ProgramConstant.ZERO);
			programDetail.setPayables("0");//null!=dalProgramDetail.getAccuralData()?df.format(dalProgramDetail.getAccuralData().getTotalPaidAmount()):ProgramConstant.ZERO);
			programDetail.setGlBalance("0");//null!=dalProgramDetail.getAccuralData()?df.format(dalProgramDetail.getAccuralData().getBalance()):ProgramConstant.ZERO);
			programDetail.setLongDesc(null!=dalProgramDetail.getLongDesc()?dalProgramDetail.getLongDesc():ProgramConstant.PROGRAM_DESCRIPTION_NOT_FOUND);
			programDetail.setProgramType(dalProgramDetail.getDalProgramType().getType());
			//programDetail.setProgramTypeId( dalProgramDetail.getDalProgramType().getId());
			programDetail.setCreatedDate(ProgramServiceHelper.convertDateToString(dalProgramDetail.getCreatedDate().getTime(), ProgramConstant.DATE_FORMAT));
			programDetail.setModifiedDate(ProgramServiceHelper.convertDateToString(dalProgramDetail.getModifiedDate().getTime(), ProgramConstant.DATE_FORMAT));
			programDetailList.add(programDetail);
		}
		

		return programDetailList;
	}
	
	private String frameTheAmount(double amount, String type) {
		DecimalFormat df = new DecimalFormat("#,###.00");
		DecimalFormat dfless1 = new DecimalFormat("0.00");
		String outAmount="0";
		if(amount!=0 ){
			if(amount<1)
			{
				outAmount=dfless1.format(amount);
			}
			else
			{
				outAmount=df.format(amount);
			}
			if("$".equalsIgnoreCase(type)){
				outAmount="<b>"+type+outAmount+"</b>";
			}else{
				outAmount=outAmount+type;
			}
			return outAmount;
		 }else
			 return outAmount;
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
		Map<String, Object> queryParams = new HashMap<>();
		List<ProgramDetail> pgm=new ArrayList<ProgramDetail>();
		if(empId==0){
			pgm=getProgram("0","0,4,3");
		}else{
			String queryString = QueryConstant.EMPLOYEE_HIER_LIST;
			queryParams.put("loginId", empId);
			List<String> userIdList=baseDao.getListFromNativeQuery(queryString,queryParams);
			queryParams = new HashMap<>();
			//if((userIdList.size()>1)||((userIdList.size()==1)&&(baseDao.getEntityById(DalEmployeeHierarchy.class, Integer.parseInt(userIdList.get(0))).getBaseTitle().equalsIgnoreCase("Account Manager")))){
				String sql=QueryConstant.CUSTOMER_LIST_MGR;
				queryParams.put("userId", userIdList);
				List<String> customerId=baseDao.getListFromNativeQuery(sql, queryParams);	
				if(!customerId.isEmpty()){
				pgm=getProgram(customerId.toString().substring(1, customerId.toString().length()-1),"0,4,3");
				}
			//}
			
		}
			
		return pgm;
	}
	
	
	@Override
	public  byte[] downloadPDF(String id) {
		PdfGenerator pdf=new PdfGenerator();
		return pdf.generatePdf(baseDao, id);
	}
	

	@Override
	public List<NewCustomerDetail> getNewCustomerData(Integer employeeId) {
		
		List<NewCustomerDetail> customerDetailsList =  new ArrayList<NewCustomerDetail>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		
		String queryString = QueryConstant.NEW_CUSTOMER_QUERY;
		if (employeeId != 0 ) {
			String empQueryString = QueryConstant.EMPLOYEE_HIER_LIST;
			queryParams.put("loginId", employeeId);
			List<String> userIdList = baseDao.getListFromNativeQuery(empQueryString,queryParams);
			queryParams.clear();
			queryParams.put("userIdList", userIdList);
			queryString += QueryConstant.NEW_CUSTOMER_ACCOUNT_MANAGER_CHECK;
		}		
		
		List<DalCustomer> resultList = baseDao.getlist(DalCustomer.class, queryString, queryParams);
		if(resultList != null && !resultList.isEmpty()){
			for(DalCustomer dalCustomer : resultList){
				NewCustomerDetail customerDetail = new NewCustomerDetail();
				
				customerDetail.setCustomerId(String.valueOf(dalCustomer.getId()));
				customerDetail.setCustomerName(dalCustomer.getCustomerName());
				customerDetail.setCustomerNumber(dalCustomer.getCustomerNumber());
				customerDetail.setBusinessUnit(BusinessUnitDescriptionEnum.getBUDescription(dalCustomer.getBu()).getBusinessUnitDescription());
				String accountManagerName = null;
				try{
					DalEmployee accountManager = baseDao.getById(DalEmployee.class, Integer.valueOf(dalCustomer.getAccountManager()));
					accountManagerName = ProgramServiceHelper.getName(accountManager);
				}
				catch(NoResultException e){
					/**For now, if there is not matching account manager details, just return the account manager id.*/
					accountManagerName = dalCustomer.getAccountManager();
				}
				
				customerDetail.setAccountManager(accountManagerName);
				customerDetail.setCreatedDate(null!=dalCustomer.getCreatedDate()?ProgramServiceHelper.convertDateToString(dalCustomer.getCreatedDate().getTime(), ProgramConstant.DATE_FORMAT):"");
				customerDetail.setModifiedDate(null!=dalCustomer.getModifiedDate()?ProgramServiceHelper.convertDateToString(dalCustomer.getModifiedDate().getTime(), ProgramConstant.DATE_FORMAT):"");
				
				if(customerDetailsList == null){
					customerDetailsList = new ArrayList<NewCustomerDetail>();
				}
				customerDetailsList.add(customerDetail);
			}
		}
		
		return customerDetailsList;
	}


	@Override
	public ProgramPaidOn getTagDetails(String tag) {
		ProgramPaidOn programPaidOn = null;
		Map<String, List<String>> tagDetailsMap = null;
		List<Integer> tagIdList = null;
		List<String> tagValues = null;
		List<Integer> tagExcludedIdList = null;
		List<String> tagExcludedValues = null;
		if(ProgramConstant.COMMERCIAL.equals(tag) || ProgramConstant.CONSUMER.equals(tag) || ProgramConstant.OTR.equals(tag)){
			if(ProgramConstant.COMMERCIAL.equals(tag)){
				tagIdList = ProgramConstant.COMMERCIAL_TAG_ID_LIST;
				tagValues = ProgramConstant.COMMERCIAL_TAG_VALUE_LIST;
			}
			else if(ProgramConstant.CONSUMER.equals(tag)){
				tagIdList = ProgramConstant.CONSUMER_TAG_ID_LIST;
				tagValues = ProgramConstant.CONSUMER_TAG_VALUE_LIST;
				tagExcludedIdList = ProgramConstant.CONSUMER_EXCLUDE_TAG_ID_LIST;
				tagExcludedValues = ProgramConstant.CONSUMER_EXCLUDE_TAG_VALUE_LIST;
			}
			
			else if(ProgramConstant.OTR.equals(tag)){
				tagIdList = ProgramConstant.OTR_TAG_ID_LIST;
				tagValues = ProgramConstant.OTR_TAG_VALUE_LIST;
			}
			/**Include */
			tagDetailsMap = getTagValueMap(tagIdList, tagValues);
			if(tagDetailsMap != null ){
				if(programPaidOn == null){
					programPaidOn = new ProgramPaidOn();
				}
				programPaidOn.setIncludedMap(tagDetailsMap);	
			}
			/**Exclude */
			tagDetailsMap = getTagValueMap(tagExcludedIdList, tagExcludedValues);
			if(tagDetailsMap != null ){
				if(programPaidOn == null){
					programPaidOn = new ProgramPaidOn();
				}
				programPaidOn.setExcludedMap(tagDetailsMap);	
			}			
		}
		
		return programPaidOn;
	}


	private Map<String, List<String>> getTagValueMap(List<Integer> tagIdList,
			List<String> tagValues) {
		Map<String, List<String>> tagDetailsMap = null;
		if(tagIdList != null && tagValues != null){
			for(Integer tagId : tagIdList){
				List<String> returnList = getTagValueList(tagId);
				List<String> validValueList = new ArrayList<String>();
				for(String value : tagValues){
					if(returnList.contains(value)){
						validValueList.add(value);
					}
				}
				if(tagDetailsMap == null){
					tagDetailsMap = new HashMap<String, List<String>>();
				}
				if(!validValueList.isEmpty()){
					tagDetailsMap.put(String.valueOf(tagId),validValueList);	
				}
			}
		}
		return tagDetailsMap;
	}
	
	private List<String> getTagValueList(Integer tagId){
		
		TagItemValueMapEnum tagItemValueMapEnum = null;
		List<String> tagValueList = null;
		String query = null;
		if(tagId != null){
			tagItemValueMapEnum = TagItemValueMapEnum.tableDetail(tagId);
			if(tagItemValueMapEnum != null){
				query = String.format(QueryConstant.TAG_VALUE_LIST_BY_TAG_ID, tagItemValueMapEnum.getFetchColumnName(), tagItemValueMapEnum.getTableName()) 
						+ QueryConstant.TAG_VALUE_LIST_ORDER_BY_CLAUSE;
				tagValueList = baseDao.getTagValue(query);
			}
		}
		return tagValueList;
	}
	
	private List<DropDown> getGLCodeDropDownList(String namedQueryValue,ProgramDetail programDetail){
		List<DropDown> dropdownList = null;
		if(namedQueryValue != null){
			List<DalGLCode> dalGLCodeList =  baseDao.getListFromNamedQuery(namedQueryValue);
			programDetail.setGlCodeList(dalGLCodeList);
			if(dalGLCodeList != null){
				for(DalGLCode dalGlcode : dalGLCodeList){
					DropDown dropDown = new DropDown();
					dropDown.setKey(String.valueOf(dalGlcode.getId()));
					//dropDown.setKey(dalGlcode.getGlBucket());
					dropDown.setValue(dalGlcode.getGlNo());
					if(dropdownList == null){
						dropdownList = new ArrayList<DropDown>();
					}
					dropdownList.add(dropDown);
				}
				dropdownList = dropdownList.stream().sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue())).collect(Collectors.toList());	
				
			}
			
		}
		return dropdownList;
	}
	
	private List<DropDown> getSapGLCodeDropDownList(String namedQueryValue,ProgramDetail programDetail){
		List<DropDown> dropdownList = null;
		if(namedQueryValue != null){
			List<DalGLCode> dalGLCodeList =  baseDao.getListFromNamedQuery(namedQueryValue);
			programDetail.setGlCodeList(dalGLCodeList);
			if(dalGLCodeList != null){
				for(DalGLCode dalGlcode : dalGLCodeList){
					DropDown dropDown = new DropDown();
					dropDown.setKey(String.valueOf(dalGlcode.getId()));
					//dropDown.setKey(dalGlcode.getGlBucket());
					dropDown.setValue(dalGlcode.getSapGlCode());
					if(dropdownList == null){
						dropdownList = new ArrayList<DropDown>();
					}
					dropdownList.add(dropDown);
				}
				dropdownList = dropdownList.stream().sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue())).collect(Collectors.toList());	
				
			}
			
		}
		return dropdownList;
	}
	
}
