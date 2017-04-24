package com.ytc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.enums.BTLEnum;
import com.ytc.common.model.DropDown;
import com.ytc.common.model.ProgramAchieveOn;
import com.ytc.common.model.ProgramDetail;
import com.ytc.common.model.ProgramDetailsDropDown;
import com.ytc.common.model.ProgramHeader;
import com.ytc.common.model.ProgramMaster;
import com.ytc.common.model.ProgramPaidOn;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalBaseItems;
import com.ytc.dal.model.DalFrequency;
import com.ytc.dal.model.DalPaidType;
import com.ytc.dal.model.DalPricingType;
import com.ytc.dal.model.DalProgramDetAchieved;
import com.ytc.dal.model.DalProgramDetPaid;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.dal.model.DalProgramHeader;
import com.ytc.dal.model.DalProgramMaster;
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
	public ProgramMaster getProgramDetails(Integer programId, String customerId) {
		ProgramMaster programMaster = new ProgramMaster();
		/**
		 * Irrespective of whether program id is null or not, drop down list has to be initialized.
		 * First step in this method is create program master dropdown value.
		 * */
		populateDropDownValues(programMaster, customerId);
		if(programId != null){
			DalProgramHeader dalProgramHeader =  baseDao.getById(DalProgramHeader.class, programId);
			System.out.println( (dalProgramHeader != null)? "Program Id : " +dalProgramHeader.getId() : "Null bossu");
			ProgramHeader programHeader = new ProgramHeader();
			if(dalProgramHeader != null){
				programHeader.setCustomerName(dalProgramHeader.getCustomer().getCustomerName());
				programHeader.setBusinessUnit(dalProgramHeader.getBu());
				programHeader.setProgramId(dalProgramHeader.getId()); //Speak to Munavar. In DalModel, ID field is of String type.
				/*programHeader.setRequestId(Integer.parseInt(dalProgramHeader.getRequestId().getEMP_ID())); //Need to be corrected.
				programHeader.setRequestedBy(dalProgramHeader.getRequestId().getFIRST_NAME() + " " + dalProgramHeader.getRequestId().getLAST_NAME());*/ // this should be employee name. I think, we should
																								// get this from logged in user.,
				programHeader.setRequestedDate(dalProgramHeader.getRequestDate().getTime());
				
				/** few more columns has to be populated here. That has to be worked on. For now, only main fields are considered.*/
				programMaster.setProgramHeader(programHeader);
				DalProgramDetail dalProgramDetail = populateProgramDetailData(programMaster, dalProgramHeader, programId);
				
				populatePaidBasedOnData(programMaster, dalProgramHeader, dalProgramDetail);
				
				populateAchieveBasedOnData(programMaster, dalProgramHeader, dalProgramDetail);
			}
		}	
		
		return programMaster;
	}

	private void populatePaidBasedOnData(ProgramMaster programMaster, DalProgramHeader dalProgramHeader, DalProgramDetail dalProgramDetail) {
		ProgramPaidOn programPaidOn = null;
		if(programMaster != null && dalProgramDetail != null && dalProgramHeader != null){
			programPaidOn = new ProgramPaidOn();
			programPaidOn.setIsTiered("0".equals(dalProgramDetail.getIsTiered()) ? true : false);
			programPaidOn.setIsTrueUp("Y".equals(dalProgramDetail.getTrueUp()) ? true : false);
			programPaidOn.setProgramDescription(dalProgramDetail.getLongDesc());
			if(dalProgramDetail.getDalProgramDetPaidList() != null){
				Map<String, List<String>> includedMap = new HashMap<String, List<String>>();
				Map<String, List<String>> excludedMap = new HashMap<String, List<String>>();
				for(DalProgramDetPaid dalProgramDetPaid : dalProgramDetail.getDalProgramDetPaidList()){
					if("1".equals(dalProgramDetPaid.getMethod())){
						/**Include values*/
						if(includedMap.get(String.valueOf(dalProgramDetPaid.getTagId().getItemId())) != null){
							List<String> includeList = includedMap.get(String.valueOf(dalProgramDetPaid.getTagId().getItemId()));
							includeList.add(dalProgramDetPaid.getValue());
						}
						else{
							List<String> includeList = new ArrayList<String>();
							includeList.add(dalProgramDetPaid.getValue());
							includedMap.put(String.valueOf(dalProgramDetPaid.getTagId().getItemId()), includeList);
						}
					}
					else if("2".equals(dalProgramDetPaid.getMethod())){
						/**Include values*/
						if(excludedMap.get(String.valueOf(dalProgramDetPaid.getTagId().getItemId())) != null){
							List<String> excludeList = excludedMap.get(String.valueOf(dalProgramDetPaid.getTagId().getItemId()));
							excludeList.add(dalProgramDetPaid.getValue());
						}
						else{
							List<String> excludeList = new ArrayList<String>();
							excludeList.add(dalProgramDetPaid.getValue());
							excludedMap.put(String.valueOf(dalProgramDetPaid.getTagId().getItemId()), excludeList);
						}
					}
				}
			}
		}
	}
	
	private void populateAchieveBasedOnData(ProgramMaster programMaster, DalProgramHeader dalProgramHeader, DalProgramDetail dalProgramDetail) {
		ProgramAchieveOn programAchieveOn = null;
		if(programMaster != null && dalProgramDetail != null && dalProgramHeader != null){
			programAchieveOn = new ProgramAchieveOn();
			programAchieveOn.setAchieveBasedOn(String.valueOf(dalProgramDetail.getAchBasedMetric().getBaseItemId()));
			programAchieveOn.setAchieveFrequency(String.valueOf(dalProgramDetail.getAchBasedFreq().getFrequencyId()));
			if(dalProgramDetail.getDalProgramDetAchievedList() != null){
				Map<String, List<String>> includedMap = new HashMap<String, List<String>>();
				Map<String, List<String>> excludedMap = new HashMap<String, List<String>>();
				for(DalProgramDetAchieved dalProgramDetAchieved : dalProgramDetail.getDalProgramDetAchievedList()){
					if("1".equals(dalProgramDetAchieved.getAchMethod())){
						/**Include values*/
						if(includedMap.get(String.valueOf(dalProgramDetAchieved.getAchTagItems().getItemId())) != null){
							List<String> includeList = includedMap.get(String.valueOf(dalProgramDetAchieved.getAchTagItems().getItemId()));
							includeList.add(dalProgramDetAchieved.getAchValue());
						}
						else{
							List<String> includeList = new ArrayList<String>();
							includeList.add(dalProgramDetAchieved.getAchValue());
							includedMap.put(String.valueOf(dalProgramDetAchieved.getAchTagItems().getItemId()), includeList);
						}
					}
					else if("2".equals(dalProgramDetAchieved.getAchMethod())){
						/**Include values*/
						if(excludedMap.get(String.valueOf(dalProgramDetAchieved.getAchTagItems().getItemId())) != null){
							List<String> excludeList = excludedMap.get(String.valueOf(dalProgramDetAchieved.getAchTagItems().getItemId()));
							excludeList.add(dalProgramDetAchieved.getAchValue());
						}
						else{
							List<String> excludeList = new ArrayList<String>();
							excludeList.add(dalProgramDetAchieved.getAchValue());
							excludedMap.put(String.valueOf(dalProgramDetAchieved.getAchTagItems().getItemId()), excludeList);
						}
					}
				}
			}
		}
	}

	private DalProgramDetail populateProgramDetailData(ProgramMaster programMaster, DalProgramHeader dalProgramHeader, Integer programId) {
		ProgramDetail programDetail = null;
		DalProgramDetail returnDalProgramDetail = null;
		if(programMaster != null && dalProgramHeader !=null && dalProgramHeader.getDalProgramDetailList() != null ){
			for(DalProgramDetail dalProgramDetail : dalProgramHeader.getDalProgramDetailList()){
				if(dalProgramHeader.getAccessPgmId().equals(dalProgramDetail.getId())){
					programDetail = programMaster.getProgramDetail();
					programDetail.setProgramName(ProgramServiceHelper.convertToString(dalProgramDetail.getProgramMaster().getId()));
					programDetail.setPayoutFrequency(String.valueOf(dalProgramDetail.getPaidFrequency().getFrequencyId()));
					programDetail.setBeginDate(ProgramServiceHelper.convertDateToRequiredFormat(dalProgramDetail.getProgramStartDate().getTime(),
																						"MM/dd/yyyy")); 
					programDetail.setEndDate(ProgramServiceHelper.convertDateToRequiredFormat(dalProgramDetail.getProgramEndDate().getTime(),
																							"MM/dd/yyyy"));
					programDetail.setBtl("Y".equals(dalProgramDetail.getBTL()) ? true : false);
					programDetail.setPricingType( (dalProgramDetail.getDalPricingType() != null) ?
													ProgramServiceHelper.convertToString(dalProgramDetail.getDalPricingType().getId()) : null);
					programDetail.setAmount(new BigDecimal(dalProgramDetail.getAccrualAmount()));
					programDetail.setAmountType(dalProgramDetail.getAccrualType());
					programDetail.setPayTo(dalProgramDetail.getPayTo());
					programDetail.setPaidType( (dalProgramDetail.getDalPaidType() != null) ?
											ProgramServiceHelper.convertToString(dalProgramDetail.getDalPaidType().getId()) : null);
					programDetail.setPaidBasedOn( (dalProgramDetail.getPaidBasedOn() != null) ?
												ProgramServiceHelper.convertToString(dalProgramDetail.getPaidBasedOn().getBaseItemId()) : 
													null);
					programMaster.setProgramDetail(programDetail);
					returnDalProgramDetail = dalProgramDetail;
					break;
				}
			}
		}
		return returnDalProgramDetail;
	}

	/**
	 * 
	 * @param programMaster this value can be null or not null.
	 * @param customerId - this value will be customer id which will be used to build Pay to Drop down value.
	 */
	private void populateDropDownValues(ProgramMaster programMaster, String customerId) {
		if(programMaster == null){
			programMaster = new ProgramMaster();
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
		
		programMaster.setProgramDetail(programDetail);
		programMaster.setProgramPaidOn(programPaidOn);
		programMaster.setProgramAchieveOn(programAchieveOn);
	}
	
	private List<DropDown> getTagItemDropDownList(String namedQueryValue){
		List<DropDown> dropdownList = null;
		if(namedQueryValue != null){
			List<DalTagItems> dalTagItemsList =  baseDao.getListFromNamedQuery(namedQueryValue);
			if(dalTagItemsList != null){
				for(DalTagItems dalTagItems : dalTagItemsList){
					DropDown dropDown = new DropDown();
					dropDown.setKey(String.valueOf(dalTagItems.getItemId()));
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
					dropDown.setKey(String.valueOf(dalFrequency.getFrequencyId()));
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
					dropDown.setKey(String.valueOf(dalBaseItems.getBaseItemId()));
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
}
