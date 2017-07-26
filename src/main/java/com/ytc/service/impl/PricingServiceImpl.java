/**
 * 
 */
package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.ytc.common.enums.BusinessUnitDescriptionEnum;
import com.ytc.common.enums.ConsumerProgramStatusEnum;
import com.ytc.common.enums.TagItemValueMapEnum;
import com.ytc.common.model.DropDown;
import com.ytc.common.model.Employee;
import com.ytc.common.model.NetPricing;
import com.ytc.common.model.PricingDetail;
import com.ytc.common.model.PricingDetailsDropDown;
import com.ytc.common.model.PricingHeader;
import com.ytc.constant.ProgramConstant;
import com.ytc.constant.QueryConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalCustomer;
import com.ytc.dal.model.DalEmployeeTitle;
import com.ytc.dal.model.DalPricingDetail;
import com.ytc.dal.model.DalPricingHeader;
import com.ytc.helper.PricingServiceHelper;
import com.ytc.helper.PricingWorkflowServiceHelper;
import com.ytc.helper.ProgramServiceHelper;
import com.ytc.service.IPricingService;
import com.ytc.service.ServiceContext;

/**
 * Purpose: This class has the methods for fetching the pricing details.
 * @author Cognizant.
 *
 */
public class PricingServiceImpl implements IPricingService {
	
	@Autowired
	private IDataAccessLayer baseDao;
	
	@Autowired
	private ServiceContext serviceContext;
	
	@Override
	public List<DropDown> getTagValueDropDown(Integer tagId) {
		TagItemValueMapEnum tagItemValueMapEnum = null;
		List<DropDown> dropdownList = null;
		List<String> tagValueList = null;
		String query = null;
		if(tagId != null){
			tagItemValueMapEnum = TagItemValueMapEnum.tableDetail(tagId);
			if(tagItemValueMapEnum != null){
				query = String.format(QueryConstant.TAG_VALUE_LIST_BY_TAG_ID, tagItemValueMapEnum.getFetchColumnName(), tagItemValueMapEnum.getTableName()) 
						+ QueryConstant.TAG_VALUE_LIST_ORDER_BY_CLAUSE;
				tagValueList = baseDao.getTagValue(query);
				for(String value : tagValueList){
					if(!StringUtils.isEmpty(value)){
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
	public PricingHeader getPricingDetails(Integer customerId) {
		PricingHeader pricingHeader = new PricingHeader();
		
		if(customerId != null){
			
			DalCustomer dalCustomer = baseDao.getById(DalCustomer.class, customerId);
			populateDropDownValues(pricingHeader, dalCustomer);
			
			getHeaderDetails(pricingHeader, dalCustomer);
			/**Button Behavior*/
			PricingWorkflowServiceHelper.setNewProgramButtonProperties(pricingHeader);	
		}
		
		return pricingHeader;
	}

	
	private void getHeaderDetails(PricingHeader pricingHeader, DalCustomer dalCustomer) {
		/**Populating header information Begin*/
		/**
		 * 1. Get Business unit
		 * 2. Get work flow matrix details.
		 * 3. Identity approver information based on work flow matrix.
		 * */
		Employee employee = serviceContext.getEmployee();
		if(employee != null){
			String businessUnit = dalCustomer.getBu();
			if(businessUnit != null){
				BusinessUnitDescriptionEnum businessUnitDescriptionEnum = BusinessUnitDescriptionEnum.getBUDescription(businessUnit);	
				pricingHeader.setBusinessUnitDescription(businessUnitDescriptionEnum.getBusinessUnitDescription());
				pricingHeader.setBusinessUnit(businessUnit);
			}
			pricingHeader.setRequestedByName(employee.getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + employee.getLAST_NAME());
			pricingHeader.setRequestedByTitle(baseDao.getById(DalEmployeeTitle.class, Integer.valueOf(employee.getTITLE_ID())).getTitle());
			pricingHeader.setRequestedByDate(ProgramServiceHelper.convertDateToString(Calendar.getInstance().getTime(), ProgramConstant.DATE_FORMAT));
			pricingHeader.setCustomerId(dalCustomer.getCustomerNumber());
			pricingHeader.setCustomerGroup(dalCustomer.getCustomerName());
			pricingHeader.setStatus(ConsumerProgramStatusEnum.INPROGRESS.getProgramStatus());
			/**Work flow matrix.*/
			/*Map<String, Object> inputParameter = new HashMap<String, Object>();
			inputParameter.put("programType", ProgramConstant.PRICING_FORM_TYPE);
			inputParameter.put("businessUnit", businessUnit);
			List<DalWorkflowMatrix> dalWorkflowMatrixList = baseDao.getListFromNamedQueryWithParameter("DalWorkflowMatrix.getMatrixForBU", inputParameter);
			Integer nextApprover = null;
			if(dalWorkflowMatrixList != null && !dalWorkflowMatrixList.isEmpty()){
				DalWorkflowMatrix dalWorkflowMatrix = dalWorkflowMatrixList.get(0);
				if(dalWorkflowMatrix.getHierarchyLevel() != null){
					//Get employee hierarchy
					DalEmployeeHierarchy dalEmployeeHierarchy = baseDao.getEntityById(DalEmployeeHierarchy.class, employee.getEMP_ID());
					nextApprover = ProgramServiceWorkflowHelper.getEmployeeIdFromHierachy(dalEmployeeHierarchy, dalWorkflowMatrix.getHierarchyLevel());
				}
				else if(dalWorkflowMatrix.getEmpId() != null){
					nextApprover = dalWorkflowMatrix.getEmpId();
				}
				if(nextApprover != null){
					DalEmployee  dalEmployee = baseDao.getById(DalEmployee.class, nextApprover);
					if(dalEmployee != null){
						pricingHeader.setApprovedByName(ProgramServiceHelper.getName(dalEmployee));
						pricingHeader.setApprovedByTitle(dalEmployee.getTITLE().getTitle());
					}
				}
			}*/
		}
		/**Populating header information End*/
	}

	private void populateDropDownValues(PricingHeader pricingHeader, DalCustomer dalCustomer) {
		PricingDetailsDropDown pricingDetailsDropDown = new PricingDetailsDropDown();
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("groupFlag", "YES");
		parameters.put("customerId", dalCustomer.getId());
		String customerNumber = dalCustomer.getCustomerNumber();
		pricingDetailsDropDown.setGroupList(getGroupDropDownList("DalCustomer.getGroup", parameters));
		pricingDetailsDropDown.setCustBillToList(getTagValueDropDown(TagItemValueMapEnum.BILL_TO_NUMBER.getTagId(), 
																	 customerNumber));
		parameters.clear();
		parameters.put("customerId", dalCustomer.getId());
		pricingDetailsDropDown.setCustByNameList(getGroupDropDownList("DalCustomer.getCustomerName", parameters));
		pricingDetailsDropDown.setCustShipToList(getTagValueDropDown(TagItemValueMapEnum.SHIP_TO_NUMBER.getTagId(), 
																	 customerNumber));
		pricingDetailsDropDown.setTermsCodeList(getDropDownList("DalPricingTermCodes.getTermCodes"));
		pricingDetailsDropDown.setOtherShipReqsList(getDropDownList("DalPricingOtherShipRequirements.getOtherReqs"));
		pricingDetailsDropDown= getSelectedValueList(pricingDetailsDropDown);
		pricingHeader.setDropdownList(pricingDetailsDropDown);
		
	}
	
	private List<DropDown> getTagValueDropDown(Integer tagId, String customerNumber) {
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
					query += String.format(QueryConstant.TAG_VALUE_CUSTOMER_PRICING_WHERE_CLAUSE, customerNumber);
				}
				
				query += QueryConstant.TAG_VALUE_LIST_ORDER_BY_CLAUSE;
				tagValueList = baseDao.getTagValue(query);
				for(String value : tagValueList){
					if(value != null){
						DropDown dropDown = new DropDown();
						if(value.contains(ProgramConstant.TAG_VALUE_DELIMITER)){
							String delimitedValue[] = value.split(ProgramConstant.TAG_VALUE_DELIMITER);
							dropDown.setKey(delimitedValue[0].trim());
						}
						else{
							dropDown.setKey(value);
						}
						/*dropDown.setKey(value);*/
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
	
	private List<DropDown> getGroupDropDownList(String namedQueryValue, Map<String, Object> parameters){
		List<DropDown> dropdownList = null;		
		if(namedQueryValue != null){
			List<Object[]> dalGroupList =  baseDao.getListFromNamedQueryWithParameter(namedQueryValue, parameters);
			if(dalGroupList != null){
				for(Object[] dalGroup : dalGroupList){
					DropDown dropDown = new DropDown();
					if(!StringUtils.isEmpty(dalGroup)){
					dropDown.setKey(String.valueOf(dalGroup[0]));
					dropDown.setValue(String.valueOf(dalGroup[1]));
				
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
	private List<DropDown> getDropDownList(String namedQueryValue){
		List<DropDown> dropdownList = null;		
		if(namedQueryValue != null){
			List<String> dalList =  baseDao.getListFromNamedQuery(namedQueryValue);
			if(dalList != null){
				for(String values : dalList){
					DropDown dropDown = new DropDown();
					if(!StringUtils.isEmpty(values)){
					dropDown.setKey(values);
					dropDown.setValue(values);
				
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
	private PricingDetailsDropDown getSelectedValueList(PricingDetailsDropDown pricingDetailsDropDown){
		List<DropDown> defnList = null;		
		List<DropDown> dueDateList = null;		
		List<DropDown> cashList = null;	
			List<Object[]> dalList =  baseDao.getListFromNamedQuery("DalPricingTermCodes.getDefnsDue");
			if(dalList != null){
				for(Object[] values : dalList){
					DropDown defn = new DropDown();
					DropDown dueDate = new DropDown();
					DropDown cash = new DropDown();
					if(values != null){
						defn.setKey(StringUtils.isEmpty(values[0]) ? "" : values[0].toString());
						defn.setValue(StringUtils.isEmpty(values[1]) ? "" : values[1].toString());
					
					if(defnList == null){
						defnList = new ArrayList<DropDown>();
					}
					defnList.add(defn);
					pricingDetailsDropDown.setDefinitionList(defnList);
					
					if(dueDateList == null){
						dueDateList = new ArrayList<DropDown>();
					}
					dueDate.setKey(StringUtils.isEmpty(values[0]) ? "" : values[0].toString());
					dueDate.setValue(StringUtils.isEmpty(values[2]) ? "" : values[2].toString());
					dueDateList.add(dueDate);
					pricingDetailsDropDown.setDueDatesList(dueDateList);
					if(cashList == null){
						cashList = new ArrayList<DropDown>();
					}
					cash.setKey(StringUtils.isEmpty(values[0]) ? "" : values[0].toString());
					cash.setValue(StringUtils.isEmpty(values[3]) ? "" : values[3].toString());
					cashList.add(cash);
					pricingDetailsDropDown.setCashList(cashList);
					}
				}
			}
			
		
		return pricingDetailsDropDown;
	}
	
	@Override
	public List<NetPricing> getCustomerPricingDetails(Integer empId, String bu, Integer customerId) {
		List<NetPricing> pricingDetailList= new ArrayList<NetPricing>();
		Map<String, Object> queryParams = new HashMap<>();
		//String sql="select * from NETDOWN_P where [Bill-To No] in (select PAY_TO from CUSTOMER where ACCOUNT_MANAGER in (:empId))";
		String sql="";
		List<String> userIdList = null;
		boolean isTbpUser = false;
		if(empId != null){
			
			
			String tbpString = QueryConstant.IS_TBP_USERS;
			queryParams.put("empId", empId);
			List<Integer> tbpList = baseDao.getListFromNativeQuery(tbpString, queryParams);
			if(tbpList != null && tbpList.size() > 0){
				isTbpUser = true;
			}
			
			if(!isTbpUser){
				String queryString=QueryConstant.EMPLOYEE_HIER_LIST;
				queryParams.clear();
				queryParams.put("loginId", empId);
				userIdList=baseDao.getListFromNativeQuery(queryString,queryParams);
				if(userIdList.isEmpty()){
					return pricingDetailList;
				}
				queryParams.clear();
				if("Consumer".equalsIgnoreCase(bu)){
					sql=QueryConstant.PRICING_LIST_P;
				}else{
					sql=QueryConstant.PRICING_LIST_T;
				}
				queryParams.put("empId",userIdList);
				queryParams.put("customerId", customerId);				
			}
			else{
				queryParams.clear();
				if("Consumer".equalsIgnoreCase(bu)){
					sql=QueryConstant.TBP_PRICING_LIST_P;
				}else{
					sql=QueryConstant.TBP_PRICING_LIST_T;
				}
			}
			//List<DalNetPricingConsumer> resultList =baseDao.list(DalNetPricingConsumer.class, sql, queryParams);
			List<Object> resultList =baseDao.getListFromNativeQuery(sql, queryParams);
			for (Iterator<Object> iterator = resultList.iterator(); iterator.hasNext();) {
				Object[]  obj =  (Object[]) iterator.next();
				NetPricing netPricing=new NetPricing();
				netPricing.setBillToNumber(null!=obj[0]?obj[0].toString():"");
				netPricing.setBillToName(null!=obj[1]?obj[1].toString():"");
				netPricing.setSalesChannel(null!=obj[3]?obj[3].toString():"");
				netPricing.setProdLn(null!=obj[5]?obj[5].toString():"");
				netPricing.setTread(null!=obj[6]?obj[6].toString():"");
				netPricing.setPartNo(null!=obj[7]?obj[7].toString():"");
				netPricing.setPartDesc(null!=obj[8]?obj[8].toString():"");
				netPricing.setBasePrice(null!=obj[9]?PricingServiceHelper.changeAmountToUIFormat(obj[9].toString()):"");
				netPricing.setQuarterlyDisc(null!=obj[10]?obj[10].toString():"");
				netPricing.setSpecialDisc(null!=obj[11]?obj[11].toString():"");
				netPricing.setExceptionDisc(null!=obj[12]?obj[12].toString():"");
				netPricing.setNetPrice(null!=obj[13]?PricingServiceHelper.changeAmountToUIFormat(obj[13].toString()):"");
				netPricing.setQuantityDisc(null!=obj[14]?obj[14].toString():"");
				netPricing.setVolBonus(null!=obj[19]?obj[19].toString():"");
				pricingDetailList.add(netPricing);
			}
		}
		
		return pricingDetailList;
	}

	@Override
	public PricingHeader getPricingDetail(Integer pricingHeaderId) {
		PricingHeader pricingHeader = new PricingHeader();
		try{
			DalPricingHeader dalPricingHeader = null;
			if(pricingHeaderId != null){
				/**Get the pricing information*/
				dalPricingHeader = baseDao.getById(DalPricingHeader.class, pricingHeaderId);
				
				populateDropDownValues(pricingHeader, dalPricingHeader.getCustomer());
			}

			if(dalPricingHeader != null){
				pricingHeader.setId(dalPricingHeader.getId());
				getPricingDetailsData(pricingHeader, dalPricingHeader, serviceContext.getEmployee());	
			}
			/**Button Behavior*/
			PricingWorkflowServiceHelper.setProgramButtonProperties(serviceContext.getEmployee(), pricingHeader, dalPricingHeader);
			
			/**Work flow history*/
			PricingWorkflowServiceHelper.populateWorkflowStatusData(pricingHeader, dalPricingHeader);
		}
		catch(Exception e){
			e.printStackTrace();
		}


		
		return pricingHeader;
	}

	private void getPricingDetailsData(PricingHeader pricingHeader, DalPricingHeader dalPricingHeader,
			Employee employee) {
		if(pricingHeader != null && dalPricingHeader != null && employee != null){
			/**Pricing Header information*/
			populateHeaderData(pricingHeader, dalPricingHeader, employee);
			
			/**Populate PRICING_HEADER table information*/
			populatePricingHeaderData(pricingHeader, dalPricingHeader);
			
			/**Populate PRICING_DETAIL table information*/
			populatePricingDetailData(pricingHeader, dalPricingHeader);
			
			/**Populate workflow details.*/
			PricingWorkflowServiceHelper.populateWorkflowStatusData(pricingHeader, dalPricingHeader);
		}
		
	}

	private void populatePricingDetailData(PricingHeader pricingHeader, DalPricingHeader dalPricingHeader) {
		if(pricingHeader != null && dalPricingHeader != null){
			if(pricingHeader.getPricingDetailList() != null){
				pricingHeader.getPricingDetailList().clear();
			}
			else{
				pricingHeader.setPricingDetailList(new ArrayList<PricingDetail>());
			}
			Set<Integer> uniqueIds = null;
			if(dalPricingHeader.getDalPricingDetailList() != null && !dalPricingHeader.getDalPricingDetailList().isEmpty()){
				
				List<DalPricingDetail> dalWorkflowStatusList = PricingServiceHelper.getPricingDetailsSorted(dalPricingHeader.getDalPricingDetailList());

				uniqueIds = new HashSet<Integer>();
				for(DalPricingDetail dalPricingDetail : dalWorkflowStatusList ){
					PricingDetail pricingDetail = new PricingDetail();
					boolean isDuplicate = uniqueIds.add(dalPricingDetail.getId());
					if(!isDuplicate){
						continue;
					}
					pricingDetail.setAddChangeDel(dalPricingDetail.getAddChgDel());
					pricingDetail.setBeginDate(dalPricingDetail.getStartDate().getTime());
					pricingDetail.setEndDate(dalPricingDetail.getEndDate().getTime());
					pricingDetail.setProdLine(dalPricingDetail.getProdLine());
					pricingDetail.setTread(dalPricingDetail.getProdTread());
					pricingDetail.setPart(dalPricingDetail.getPartNumber());
					pricingDetail.setBusinessUnit(dalPricingDetail.getBusinessUnit());
					pricingDetail.setNetPrice(String.valueOf(dalPricingDetail.getNetPrice()));
					pricingDetail.setInvoiceDisc(String.valueOf( dalPricingDetail.getInvoiceDisc()));
					pricingDetail.setBonusUnits(dalPricingDetail.getIsBonusableUnits());
					pricingDetail.setCommissionable(dalPricingDetail.getIsCommissionable());
					pricingDetail.setComments(dalPricingDetail.getComments());
					/*pricingDetail.setProgramId(programId);*/
					pricingDetail.setId(dalPricingDetail.getId());
					
					pricingHeader.getPricingDetailList().add(pricingDetail);
				}
			}
		}
	}

	private void populatePricingHeaderData(PricingHeader pricingHeader, DalPricingHeader dalPricingHeader) {
		if(pricingHeader != null && dalPricingHeader != null){
			pricingHeader.setCustomerType(dalPricingHeader.getCustomerType().getCustomerType());
			pricingHeader.setCustomerId(dalPricingHeader.getCustomer().getCustomerNumber());
			pricingHeader.setCustomerGroup(String.valueOf(dalPricingHeader.getCustomerGroup()));
			pricingHeader.setCustomerGroupValue(dalPricingHeader.getCustomer().getCustomerName());
			pricingHeader.setUserComments(dalPricingHeader.getUserComments());
			pricingHeader.setTermCode(dalPricingHeader.getTermCodes().getCode());
			pricingHeader.setShippingReqs(dalPricingHeader.getShippingReqs().getShipRqs());
			pricingHeader.setOtherShippingReqs(dalPricingHeader.getOtherShippingreqs().getOtherReqs());
			pricingHeader.setSbmCheck(dalPricingHeader.getSbm());
		}
	}

	private void populateHeaderData(PricingHeader pricingHeader, DalPricingHeader dalPricingHeader, Employee employee) {
		if(pricingHeader != null && dalPricingHeader != null){
			pricingHeader.setBusinessUnit(dalPricingHeader.getCustomer().getBu());
			BusinessUnitDescriptionEnum businessUnitDescriptionEnum = BusinessUnitDescriptionEnum.getBUDescription(dalPricingHeader.getCustomer().getBu());
			pricingHeader.setBusinessUnitDescription( (businessUnitDescriptionEnum != null) ? businessUnitDescriptionEnum.getBusinessUnitDescription() : dalPricingHeader.getCreatedBy().getBUSINESS_UNIT());
			pricingHeader.setRequestedByName(ProgramServiceHelper.getName(dalPricingHeader.getCreatedBy()));
			pricingHeader.setRequestedByTitle(dalPricingHeader.getCreatedBy().getTITLE().getTitle());
			pricingHeader.setRequestedByDate(ProgramServiceHelper.convertDateToString(dalPricingHeader.getCreatedDate().getTime(), ProgramConstant.DATE_FORMAT));
			/*Read work flow status table and populate the Approved By user details.*/
			
			if(dalPricingHeader.getDalStatus() != null){
				pricingHeader.setStatus(dalPricingHeader.getDalStatus().getType());	
			}
		}
	}
}
