/**
 * 
 */
package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.ytc.common.enums.TagItemValueMapEnum;
import com.ytc.common.model.DropDown;
import com.ytc.common.model.PricingDetailsDropDown;
import com.ytc.common.model.PricingHeader;
import com.ytc.constant.QueryConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.service.IPricingService;

/**
 * @author ArunP
 *
 */
public class PricingServiceImpl implements IPricingService {
	@Autowired
	private IDataAccessLayer baseDao;
	
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
		return pricingHeader;
	}

	private void populateDropDownValues(PricingHeader pricingHeader) {
		PricingDetailsDropDown pricingDetailsDropDown = new PricingDetailsDropDown();
		pricingDetailsDropDown.setGroupList(getGroupDropDownList("DalCustomer.getGroup"));
		pricingDetailsDropDown.setCustBillToList(getDropDownList("DalShipToMaster.getBillToName"));
		pricingDetailsDropDown.setCustByNameList(getDropDownList("DalCustomer.getCustomerName"));
		pricingDetailsDropDown.setCustShipToList(getDropDownList("DalShipToMaster.getShipToName"));
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
	
}
