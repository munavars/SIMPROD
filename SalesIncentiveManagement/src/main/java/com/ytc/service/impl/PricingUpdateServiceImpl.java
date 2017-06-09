/**
 * 
 */
package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ytc.common.model.PricingDetail;
import com.ytc.common.model.PricingHeader;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalPartMaster;
import com.ytc.dal.model.DalPricingCustomerType;
import com.ytc.dal.model.DalPricingDetail;
import com.ytc.dal.model.DalPricingHeader;
import com.ytc.dal.model.DalPricingOtherShipRequirements;
import com.ytc.dal.model.DalPricingShipRequirements;
import com.ytc.dal.model.DalPricingTermCodes;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.service.IPricingUpdateService;

/**
 * @author ArunP
 *
 */
public class PricingUpdateServiceImpl implements IPricingUpdateService{
	@Autowired
	private IDataAccessLayer baseDao;
	private static Logger LOGGER = Logger.getLogger(PricingUpdateServiceImpl.class.getName());


	@Override
	public PricingHeader savePricingHeaderDetails(PricingHeader pricingHeader) {
		
		/*if(pricingHeader.getId() != null && pricingHeader.getPricingDetailList().get(0).getId() != null){
			
			DalPricingDetail dalPricingDetail = baseDao.getById(DalPricingDetail.class, pricingHeader.getPricingDetailList().get(0).getId());
		}*/
			try {
				if(pricingHeader != null ){
					createPricingDetails(pricingHeader);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOGGER.info("Error in saving Pricing Header Details" +e);
			}
				
		
	
		return pricingHeader;
	}
	@Transactional
private void createPricingDetails(PricingHeader pricingHeader) {
	DalPricingHeader dalPricingHeader = null;
	if(pricingHeader != null ){
		dalPricingHeader = new DalPricingHeader();	
		dalPricingHeader.setCustomerId(pricingHeader.getCustomerId());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("customerType", pricingHeader.getCustomerType());
		List<DalPricingCustomerType> dalPricingCustomerTypeList =baseDao.getByType("DalPricingCustomerType.getCustomerType", parameters);
		for(DalPricingCustomerType dalPricingCustomerType:dalPricingCustomerTypeList){
		dalPricingHeader.setCustomerType(dalPricingCustomerType);
		parameters.remove("customerType", pricingHeader.getCustomerType());
		}
	parameters.put("code", pricingHeader.getTermCode());
		List<DalPricingTermCodes> dalPricingTermCodesList =baseDao.getByType("DalPricingTermCodes.getCode", parameters);
		for(DalPricingTermCodes dalPricingTermCodes:dalPricingTermCodesList){
		dalPricingHeader.setTermCodes(dalPricingTermCodes);
		parameters.remove("code", pricingHeader.getTermCode());
		}
		parameters.put("shipRqs", pricingHeader.getShippingReqs());
		List<DalPricingShipRequirements> dalPricingShipRequirementsList =baseDao.getByType("DalPricingShipRequirements.getShipRqs", parameters);
		for(DalPricingShipRequirements dalPricingShipRequirements:dalPricingShipRequirementsList){
		dalPricingHeader.setShippingReqs(dalPricingShipRequirements);
		parameters.remove("shipRqs", pricingHeader.getShippingReqs());
		}
		
		parameters.put("otherReqs", pricingHeader.getOtherShippingReqs());
		List<DalPricingOtherShipRequirements> dalPricingOtherShipRequirementsList =baseDao.getByType("DalPricingOtherShipRequirements.getOtherReq", parameters);
		for(DalPricingOtherShipRequirements dalPricingOtherShipRequirements:dalPricingOtherShipRequirementsList){
		dalPricingHeader.setOtherShippingreqs(dalPricingOtherShipRequirements);
		parameters.remove("otherReqs", pricingHeader.getOtherShippingReqs());
		}
		dalPricingHeader.setUserComments(StringUtils.isEmpty(pricingHeader.getUserComments())  ? "": pricingHeader.getUserComments() );
	
		
		
		
		
		if(validatePartProdTread(pricingHeader.getPricingDetailList())){
		
		/** Save pricing Detail section information*/
		List<DalPricingDetail> dalPricingDetailsList = createPricingDetailsData(pricingHeader,dalPricingHeader);
		
		if(!dalPricingDetailsList.isEmpty()){
			 dalPricingHeader.setDalPricingDetailList(dalPricingDetailsList);
			 
		 }
		
		baseDao.merge(dalPricingHeader);
		
			pricingHeader.setSuccess(true);
		}
	}		
}
	
	
	private List<DalPricingDetail> createPricingDetailsData( PricingHeader pricingHeader,DalPricingHeader dalPricingHeader) {
		
		List<DalPricingDetail> dalPricingDetailsList = new ArrayList<DalPricingDetail>();
		DalPricingDetail dalPricingDetail = null;
		
		for(PricingDetail pricingDetail: pricingHeader.getPricingDetailList() ){
		 dalPricingDetail = new DalPricingDetail();
		if(pricingDetail != null ){
			dalPricingDetail.setAddChgDel(pricingDetail.getAddChangeDel());
			//dalPricingDetail.setDiscType(pricingDetail.getComments());
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(pricingDetail.getBeginDate().getTime());
			dalPricingDetail.setStartDate(cal);
			Calendar cal1 = Calendar.getInstance();
			cal1.setTimeInMillis(pricingDetail.getEndDate().getTime());
			dalPricingDetail.setEndDate(cal1);
			if(!StringUtils.isEmpty(pricingDetail.getInvoiceDisc())){
			dalPricingDetail.setInvoiceDisc(Double.valueOf(trimPercentage(pricingDetail.getInvoiceDisc())));
			}
			if(!StringUtils.isEmpty(pricingDetail.getNetPrice())){
			dalPricingDetail.setNetPrice(Double.valueOf(pricingDetail.getNetPrice()));
			}
			
			dalPricingDetail.setIsBonusableUnits(pricingDetail.getBonusUnits());
			dalPricingDetail.setIsCommissionable(pricingDetail.getCommissionable());
			dalPricingDetail.setPartNumber(pricingDetail.getPart());
			dalPricingDetail.setProdLine(pricingDetail.getProdLine());
			dalPricingDetail.setProdTread(pricingDetail.getTread());
			 dalPricingDetail.setDalPricingHeader(dalPricingHeader);
		}
		
		 dalPricingDetailsList.add(dalPricingDetail);
		}
		
		return dalPricingDetailsList;
		}
	public String trimPercentage(String str) {
		if(!StringUtils.isEmpty(str)){
	    if (str.charAt(str.length()-1)=='%'){
	        str = str.replace(str.substring(str.length()-1), "");
	        return str;
	    } else{
	        return str;
	    }
		}
		return str;
	}
	private boolean validatePartProdTread(List<PricingDetail> pricingDetailsList) {
		boolean isValidated = false ;
		List<String> partValidated=null;
		List<String> prodLineValidated=null;
		List<String> prodTreadValidated=null;
		boolean validatePart = false;
		 boolean validateProdLine = false;
		 boolean validateProdTread = false;
		List<String> partNumber = new ArrayList<String>();
		List<String> prodLine = new ArrayList<String>();
		List<String> prodTread = new ArrayList<String>();
		Map<String, Object> queryPart = new HashMap<>();
		Map<String, Object> queryProdLine= new HashMap<>();	
		Map<String, Object> queryTread = new HashMap<>();	
		for(PricingDetail pricingDetail: pricingDetailsList ){
				if(!StringUtils.isEmpty(pricingDetail.getPart()) && !StringUtils.isEmpty(pricingDetail.getProdLine()) && !StringUtils.isEmpty(pricingDetail.getTread())){
				partNumber.add(pricingDetail.getPart());
				queryPart.put("partNumber", partNumber);
				 partValidated= baseDao.getListFromNamedQueryWithParameter("DalPartMaster.getPartNumber",queryPart);
				 validatePart= arraysMatch(partNumber, partValidated);
				prodLine.add(pricingDetail.getProdLine());
				queryProdLine.put("productLine", prodLine);
				 prodLineValidated= baseDao.getListFromNamedQueryWithParameter("DalPartMaster.getProductLine",queryProdLine);
				 validateProdLine = arraysMatch(prodLine, prodLineValidated);
				prodTread.add(pricingDetail.getTread());
				queryTread.put("treadDesc", prodTread);
				prodTreadValidated= baseDao.getListFromNamedQueryWithParameter("DalPartMaster.getTreadDesc",queryTread);
				 validateProdTread = arraysMatch(prodTread, prodTreadValidated);
		if(validatePart && validateProdLine && validateProdTread){
			isValidated = true;
		}
				}else{
					isValidated = false;
					break;
				}
				
	}
		
		return isValidated;
		
		}
	public boolean arraysMatch(List<String> partNumber, List<String> partValidated) {
	   int count = 0;
	    List<String> match = new ArrayList(partValidated);
	    if(!partNumber.isEmpty()){
	    for (String element : partNumber) {
	        if (match.contains(element)) {
	            count++;
	        }
	    }
	    }
	    if(partNumber.size() == count){
	    return true;
	    }
		return false;
	}	
}
