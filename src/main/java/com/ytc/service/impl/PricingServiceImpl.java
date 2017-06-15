/**
 * 
 */
package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.ytc.common.enums.BusinessUnitDescriptionEnum;
import com.ytc.common.enums.TagItemValueMapEnum;
import com.ytc.common.model.DropDown;
import com.ytc.common.model.Employee;
import com.ytc.common.model.NetPricing;
import com.ytc.common.model.PricingDetailsDropDown;
import com.ytc.common.model.PricingHeader;
import com.ytc.constant.ProgramConstant;
import com.ytc.constant.QueryConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalEmployee;
import com.ytc.dal.model.DalEmployeeHierarchy;
import com.ytc.dal.model.DalEmployeeTitle;
import com.ytc.dal.model.DalWorkflowMatrix;
import com.ytc.helper.ProgramServiceHelper;
import com.ytc.helper.ProgramServiceWorkflowHelper;
import com.ytc.service.IPricingService;
import com.ytc.service.IProgramService;
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
	private IProgramService programService;
	
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
	public PricingHeader getPricingDetails() {
		PricingHeader pricingHeader = new PricingHeader();
		populateDropDownValues(pricingHeader);
		
		getHeaderDetails(pricingHeader);
		
		return pricingHeader;
	}

	private void getHeaderDetails(PricingHeader pricingHeader) {
		/**Populating header information Begin*/
		/**
		 * 1. Get Business unit
		 * 2. Get work flow matrix details.
		 * 3. Identity approver information based on work flow matrix.
		 * */
		Employee employee = serviceContext.getEmployee();
		if(employee != null){
			String businessUnit = employee.getBUSINESS_UNIT();
			if(businessUnit != null){
				BusinessUnitDescriptionEnum businessUnitDescriptionEnum = BusinessUnitDescriptionEnum.getBUDescription(businessUnit);	
				pricingHeader.setBusinessUnitDescription(businessUnitDescriptionEnum.getBusinessUnitDescription());
				pricingHeader.setBusinessUnit(businessUnit);
			}
			pricingHeader.setRequestedByName(employee.getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + employee.getLAST_NAME());
			pricingHeader.setRequestedByTitle(baseDao.getById(DalEmployeeTitle.class, Integer.valueOf(employee.getTITLE_ID())).getTitle());
			pricingHeader.setRequestedByDate(ProgramServiceHelper.convertDateToString(Calendar.getInstance().getTime(), ProgramConstant.DATE_FORMAT));
			/**Work flow matrix.*/
			Map<String, Object> inputParameter = new HashMap<String, Object>();
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
			}
		}
		/**Populating header information End*/
	}

	private void populateDropDownValues(PricingHeader pricingHeader) {
		PricingDetailsDropDown pricingDetailsDropDown = new PricingDetailsDropDown();
		pricingDetailsDropDown.setGroupList(getGroupDropDownList("DalCustomer.getGroup"));
		pricingDetailsDropDown.setCustBillToList(programService.getTagValueDropDown(TagItemValueMapEnum.BILL_TO_NUMBER.getTagId(), 
																					serviceContext.getEmployee().getEMP_ID()));
		pricingDetailsDropDown.setCustByNameList(getDropDownList("DalCustomer.getCustomerName"));
		pricingDetailsDropDown.setCustShipToList(programService.getTagValueDropDown(TagItemValueMapEnum.SHIP_TO_NUMBER.getTagId(), 
																					serviceContext.getEmployee().getEMP_ID()));
		pricingDetailsDropDown.setTermsCodeList(getDropDownList("DalPricingTermCodes.getTermCodes"));
		pricingDetailsDropDown.setOtherShipReqsList(getDropDownList("DalPricingOtherShipRequirements.getOtherReqs"));
		pricingDetailsDropDown= getSelectedValueList(pricingDetailsDropDown);
		pricingHeader.setDropdownList(pricingDetailsDropDown);
		
	}
	private List<DropDown> getGroupDropDownList(String namedQueryValue){
		List<DropDown> dropdownList = null;		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("groupFlag", "YES");
		if(namedQueryValue != null){
			List<String> dalGroupList =  baseDao.getListFromNamedQueryWithParameter(namedQueryValue, parameters);
			if(dalGroupList != null){
				for(String dalGroup : dalGroupList){
					DropDown dropDown = new DropDown();
					if(!StringUtils.isEmpty(dalGroup)){
					dropDown.setKey(dalGroup);
					dropDown.setValue(dalGroup);
				
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
	public List<NetPricing> getCustomerPricingDetails(Integer empId, String bu) {
		List<NetPricing> pricingDetailList= new ArrayList<NetPricing>();
		Map<String, Object> queryParams = new HashMap<>();
		//String sql="select * from NETDOWN_P where [Bill-To No] in (select PAY_TO from CUSTOMER where ACCOUNT_MANAGER in (:empId))";
		String sql="";
		if("Consumer".equalsIgnoreCase(bu)){
			sql=QueryConstant.PRICING_LIST_P;
		}else{
			sql=QueryConstant.PRICING_LIST_T;
		}
		queryParams.put("empId",empId);
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
			netPricing.setBasePrice(null!=obj[9]?obj[9].toString():"");
			netPricing.setQuarterlyDisc(null!=obj[10]?obj[10].toString():"");
			netPricing.setSpecialDisc(null!=obj[11]?obj[11].toString():"");
			netPricing.setExceptionDisc(null!=obj[12]?obj[12].toString():"");
			netPricing.setNetPrice(null!=obj[13]?Double.parseDouble(obj[13].toString()):0);
			netPricing.setQuantityDisc(null!=obj[14]?obj[14].toString():"");
			netPricing.setVolBonus(null!=obj[19]?obj[19].toString():"");
			pricingDetailList.add(netPricing);
		}
		return pricingDetailList;
	}
	
}
