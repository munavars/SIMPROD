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
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ytc.common.model.PricingDetail;
import com.ytc.common.model.PricingHeader;
import com.ytc.constant.ProgramConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalCustomer;
import com.ytc.dal.model.DalPricingCustomerType;
import com.ytc.dal.model.DalPricingDetail;
import com.ytc.dal.model.DalPricingHeader;
import com.ytc.dal.model.DalPricingOtherShipRequirements;
import com.ytc.dal.model.DalPricingShipRequirements;
import com.ytc.dal.model.DalPricingTermCodes;
import com.ytc.dal.model.DalProgramType;
import com.ytc.dal.model.DalStatus;
import com.ytc.helper.PricingWorkflowServiceHelper;
import com.ytc.service.IPricingUpdateService;
import com.ytc.service.IPricingWorkflowService;
import com.ytc.service.ServiceContext;

/**
 * Purpose : Business logic related pricing form.
 * @author Cognizant.
 *
 */
public class PricingUpdateServiceImpl implements IPricingUpdateService {
	@Autowired
	private IDataAccessLayer baseDao;
	
	@Autowired
	private IPricingWorkflowService pricingWorkflowService;
	
	@Autowired
	private ServiceContext serviceContext;
	
	private static Logger LOGGER = Logger.getLogger(PricingUpdateServiceImpl.class.getName());

	@Override
	public PricingHeader savePricingHeaderDetails(PricingHeader pricingHeader) {

		/*
		 * if(pricingHeader.getId() != null &&
		 * pricingHeader.getPricingDetailList().get(0).getId() != null){
		 * 
		 * DalPricingDetail dalPricingDetail =
		 * baseDao.getById(DalPricingDetail.class,
		 * pricingHeader.getPricingDetailList().get(0).getId()); 
		 * }
		 */
		try {
			if (pricingHeader != null) {
				if(pricingHeader.getId() == null){
					createPricingDetails(pricingHeader);	
				}
				else{
					updatePricingDetails(pricingHeader);
				}
				
			}
		} catch (Exception e) {
			LOGGER.info("Error in saving Pricing Header Details" + e);
		}

		return pricingHeader;
	}
	

	private void getProgramTypeDetails(DalPricingHeader dalPricingHeader) {
		if(dalPricingHeader != null){
			List<DalProgramType> dalProgramTypeList = baseDao.getListFromNamedQuery("DalProgramType.getAllDetails");
			if(dalProgramTypeList != null){
				for(DalProgramType dalProgramType : dalProgramTypeList){
					if(ProgramConstant.PRICING_FORM_TYPE.equalsIgnoreCase(dalProgramType.getType()) ){
						dalPricingHeader.setDalProgramType(dalProgramType);
						break;
					}
				}
			}
		}
	}

	@Transactional
	private void updatePricingDetails(PricingHeader pricingHeader) {
		DalPricingHeader dalPricingHeader = null;
		if(pricingHeader != null && pricingHeader.getId() != null){
			dalPricingHeader = baseDao.getById(DalPricingHeader.class, pricingHeader.getId());
			if(dalPricingHeader != null){
				updatePricingHeaderData(dalPricingHeader, pricingHeader);
			}
		}
	}

	private void updatePricingHeaderData(DalPricingHeader dalPricingHeader, PricingHeader pricingHeader) {
		if (pricingHeader != null && dalPricingHeader != null) {

			if (validateCustomerId(pricingHeader) && validatePartProdTread(pricingHeader)) {
								
				/**Set Pricing request status*/
				if(pricingHeader.getStatus() != null){
					dalPricingHeader.setDalStatus(baseDao.getById(DalStatus.class, Integer.valueOf(pricingHeader.getStatus())));
				}
				
				if(!pricingHeader.getProgramButton().isApprover()){
					
					checkAndUpdatePricingHeaderData(dalPricingHeader, pricingHeader);
					
					dalPricingHeader.setUserComments(
							StringUtils.isEmpty(pricingHeader.getUserComments()) ? "" : pricingHeader.getUserComments());

					/** Delete the detail information and re save it. This logic has to be revisited.*/
					
					if(dalPricingHeader.getDalPricingDetailList() != null && !dalPricingHeader.getDalPricingDetailList().isEmpty()){
						for(DalPricingDetail dalPricingDetail : dalPricingHeader.getDalPricingDetailList()){
							baseDao.delete(DalPricingDetail.class, dalPricingDetail.getId());
						}
					}
					/** Save pricing Detail section information */
					List<DalPricingDetail> dalPricingDetailsList = createPricingDetailsData(pricingHeader, 
																							dalPricingHeader);

					if (!dalPricingDetailsList.isEmpty()) {
						dalPricingHeader.setDalPricingDetailList(dalPricingDetailsList);
					}
				}				

				pricingWorkflowService.updateWorkflowDetails(dalPricingHeader, pricingHeader, serviceContext.getEmployee());
				
				baseDao.update(dalPricingHeader);
				
				/**Reset status value*/
				pricingHeader.setStatus(dalPricingHeader.getDalStatus().getType());
				
				/**Reset Program workflow*/
				PricingWorkflowServiceHelper.populateWorkflowStatusData(pricingHeader, dalPricingHeader);

				pricingHeader.setSuccess(true);
			}
		}
	}

	private void checkAndUpdatePricingHeaderData(DalPricingHeader dalPricingHeader, PricingHeader pricingHeader) {
		
		if(pricingHeader.getCustomerGroup() != null){
			if(pricingHeader.getCustomerGroup().contains(ProgramConstant.TAG_VALUE_DELIMITER)){
				String delimitedValue[] = pricingHeader.getCustomerGroup().split(ProgramConstant.TAG_VALUE_DELIMITER);
				dalPricingHeader.setCustomerGroup(Integer.valueOf(delimitedValue[0].trim()));
			}
			else{
				dalPricingHeader.setCustomerGroup(Integer.valueOf(pricingHeader.getCustomerGroup()));
			}	
		}
		
		
		if(!dalPricingHeader.getCustomer().getCustomerNumber().equals(pricingHeader.getCustomerId())){
			Map<String, Object> inputParameters = new HashMap<String, Object>();
			inputParameters.put("customerNumber", pricingHeader.getCustomerId());
			
			List<DalCustomer> dalCustomerList =  baseDao.getListFromNamedQueryWithParameter("DalCustomer.getCustomerFromCustomerNumber", inputParameters);
			DalCustomer dalCustomer = null;
			if(dalCustomerList != null && !dalCustomerList.isEmpty()){
				dalCustomer = dalCustomerList.get(0);
			}
			dalPricingHeader.setCustomer(dalCustomer); //baseDao.getById(DalCustomer.class, Integer.valueOf(pricingHeader.getCustomerId()))	
		}
		
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		if(!dalPricingHeader.getCustomerType().getCustomerType().equals(pricingHeader.getCustomerType())){
			parameters.put("customerType", pricingHeader.getCustomerType());
			List<DalPricingCustomerType> dalPricingCustomerTypeList = baseDao.getByType("DalPricingCustomerType.getCustomerType", parameters);
			for (DalPricingCustomerType dalPricingCustomerType : dalPricingCustomerTypeList) {
				dalPricingHeader.setCustomerType(dalPricingCustomerType);
				parameters.remove("customerType", pricingHeader.getCustomerType());
			}	
		}
		
		if(!dalPricingHeader.getTermCodes().getCode().equals(pricingHeader.getCustomerType())){
			parameters.put("code", pricingHeader.getTermCode());
			List<DalPricingTermCodes> dalPricingTermCodesList = baseDao.getByType("DalPricingTermCodes.getCode",
					parameters);
			for (DalPricingTermCodes dalPricingTermCodes : dalPricingTermCodesList) {
				dalPricingHeader.setTermCodes(dalPricingTermCodes);
				parameters.remove("code", pricingHeader.getTermCode());
			}	
		}
		
		if(!dalPricingHeader.getShippingReqs().getShipRqs().equals(pricingHeader.getShippingReqs())){
			parameters.put("shipRqs", pricingHeader.getShippingReqs());
			List<DalPricingShipRequirements> dalPricingShipRequirementsList = baseDao.getByType("DalPricingShipRequirements.getShipRqs", parameters);
			for (DalPricingShipRequirements dalPricingShipRequirements : dalPricingShipRequirementsList) {
				dalPricingHeader.setShippingReqs(dalPricingShipRequirements);
				parameters.remove("shipRqs", pricingHeader.getShippingReqs());
			}	
		}
		
		if(!dalPricingHeader.getOtherShippingreqs().getOtherReqs().equals(pricingHeader.getOtherShippingReqs())){
			parameters.put("otherReqs", pricingHeader.getOtherShippingReqs());
			List<DalPricingOtherShipRequirements> dalPricingOtherShipRequirementsList = baseDao.getByType("DalPricingOtherShipRequirements.getOtherReq", parameters);
			for (DalPricingOtherShipRequirements dalPricingOtherShipRequirements : dalPricingOtherShipRequirementsList) {
				dalPricingHeader.setOtherShippingreqs(dalPricingOtherShipRequirements);
				parameters.remove("otherReqs", pricingHeader.getOtherShippingReqs());
			}	
		}
	}

	@Transactional
	private void createPricingDetails(PricingHeader pricingHeader) {
		DalPricingHeader dalPricingHeader = null;
		if (pricingHeader != null) {

			if (validateCustomerId(pricingHeader) && validatePartProdTread(pricingHeader)) {
				dalPricingHeader = new DalPricingHeader();
				getProgramTypeDetails(dalPricingHeader);
				Map<String, Object> inputParameters = new HashMap<String, Object>();
				inputParameters.put("customerNumber", pricingHeader.getCustomerId());
				
				List<DalCustomer> dalCustomerList =  baseDao.getListFromNamedQueryWithParameter("DalCustomer.getCustomerFromCustomerNumber", inputParameters);
				DalCustomer dalCustomer = null;
				if(dalCustomerList != null && !dalCustomerList.isEmpty()){
					dalCustomer = dalCustomerList.get(0);
				}
				dalPricingHeader.setCustomer(dalCustomer); //baseDao.getById(DalCustomer.class, Integer.valueOf(pricingHeader.getCustomerId()))
				if(pricingHeader.getCustomerGroup() != null){
					if(pricingHeader.getCustomerGroup().contains(ProgramConstant.TAG_VALUE_DELIMITER)){
						String delimitedValue[] = pricingHeader.getCustomerGroup().split(ProgramConstant.TAG_VALUE_DELIMITER);
						dalPricingHeader.setCustomerGroup(Integer.valueOf(delimitedValue[0].trim()));
					}
					else{
						dalPricingHeader.setCustomerGroup(Integer.valueOf(pricingHeader.getCustomerGroup()));
					}	
				}
				/*dalPricingHeader.setCustomerGroup(Integer.valueOf(pricingHeader.getCustomerGroup()));*/
				Map<String, Object> parameters = new HashMap<String, Object>();
				
				parameters.put("customerType", pricingHeader.getCustomerType());
				List<DalPricingCustomerType> dalPricingCustomerTypeList = baseDao.getByType("DalPricingCustomerType.getCustomerType", parameters);
				for (DalPricingCustomerType dalPricingCustomerType : dalPricingCustomerTypeList) {
					dalPricingHeader.setCustomerType(dalPricingCustomerType);
					parameters.remove("customerType", pricingHeader.getCustomerType());
				}
				
				parameters.put("code", pricingHeader.getTermCode());
				List<DalPricingTermCodes> dalPricingTermCodesList = baseDao.getByType("DalPricingTermCodes.getCode",
						parameters);
				for (DalPricingTermCodes dalPricingTermCodes : dalPricingTermCodesList) {
					dalPricingHeader.setTermCodes(dalPricingTermCodes);
					parameters.remove("code", pricingHeader.getTermCode());
				}
				
				parameters.put("shipRqs", pricingHeader.getShippingReqs());
				List<DalPricingShipRequirements> dalPricingShipRequirementsList = baseDao.getByType("DalPricingShipRequirements.getShipRqs", parameters);
				for (DalPricingShipRequirements dalPricingShipRequirements : dalPricingShipRequirementsList) {
					dalPricingHeader.setShippingReqs(dalPricingShipRequirements);
					parameters.remove("shipRqs", pricingHeader.getShippingReqs());
				}

				parameters.put("otherReqs", pricingHeader.getOtherShippingReqs());
				List<DalPricingOtherShipRequirements> dalPricingOtherShipRequirementsList = baseDao.getByType("DalPricingOtherShipRequirements.getOtherReq", parameters);
				for (DalPricingOtherShipRequirements dalPricingOtherShipRequirements : dalPricingOtherShipRequirementsList) {
					dalPricingHeader.setOtherShippingreqs(dalPricingOtherShipRequirements);
					parameters.remove("otherReqs", pricingHeader.getOtherShippingReqs());
				}
				
				/**Set Pricing request status*/
				if(pricingHeader.getStatus() != null){
					dalPricingHeader.setDalStatus(baseDao.getById(DalStatus.class, Integer.valueOf(pricingHeader.getStatus())));
				}
				/**Set pricing type in program type*/
				List<DalProgramType> dalProgramTypeList = baseDao.getListFromNamedQuery("DalProgramType.getAllDetails");
				if(dalProgramTypeList != null && !dalProgramTypeList.isEmpty()){
					for(DalProgramType dalProgram : dalProgramTypeList){
						if(ProgramConstant.PRICING_FORM_TYPE.equalsIgnoreCase(dalProgram.getType())){
							dalPricingHeader.setDalProgramType(dalProgram);
							break;
						}
					}
					
				}
				
				dalPricingHeader.setUserComments(
						StringUtils.isEmpty(pricingHeader.getUserComments()) ? "" : pricingHeader.getUserComments());

				/** Save pricing Detail section information */
				List<DalPricingDetail> dalPricingDetailsList = createPricingDetailsData(pricingHeader, 
																						dalPricingHeader);

				if (!dalPricingDetailsList.isEmpty()) {
					dalPricingHeader.setDalPricingDetailList(dalPricingDetailsList);
				}

				pricingWorkflowService.updateWorkflowDetails(dalPricingHeader, pricingHeader, serviceContext.getEmployee());
				
				baseDao.create(dalPricingHeader);			
				
				/**Reset status value*/
				pricingHeader.setId(dalPricingHeader.getId());
				pricingHeader.setStatus(dalPricingHeader.getDalStatus().getType());
				
				/**Reset Program workflow*/
				PricingWorkflowServiceHelper.populateWorkflowStatusData(pricingHeader, dalPricingHeader);
				
				pricingHeader.setSuccess(true);
			}
		}
	}

	private List<DalPricingDetail> createPricingDetailsData(PricingHeader pricingHeader,
			DalPricingHeader dalPricingHeader) {

		List<DalPricingDetail> dalPricingDetailsList = new ArrayList<DalPricingDetail>();
		DalPricingDetail dalPricingDetail = null;

		for (PricingDetail pricingDetail : pricingHeader.getPricingDetailList()) {
			dalPricingDetail = new DalPricingDetail();
			if (pricingDetail != null) {
				dalPricingDetail.setAddChgDel(pricingDetail.getAddChangeDel());
				// dalPricingDetail.setDiscType(pricingDetail.getComments());
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(pricingDetail.getBeginDate().getTime());
				dalPricingDetail.setStartDate(cal);
				Calendar cal1 = Calendar.getInstance();
				cal1.setTimeInMillis(pricingDetail.getEndDate().getTime());
				dalPricingDetail.setEndDate(cal1);
				if (!StringUtils.isEmpty(pricingDetail.getInvoiceDisc())) {
					dalPricingDetail.setInvoiceDisc(Double.valueOf(trimPercentage(pricingDetail.getInvoiceDisc())));
				}
				if (!StringUtils.isEmpty(pricingDetail.getNetPrice())) {
					dalPricingDetail.setNetPrice(Double.valueOf(pricingDetail.getNetPrice()));
				}

				dalPricingDetail.setIsBonusableUnits(pricingDetail.getBonusUnits());
				dalPricingDetail.setIsCommissionable(pricingDetail.getCommissionable());
				dalPricingDetail.setPartNumber(pricingDetail.getPart());
				dalPricingDetail.setProdLine(pricingDetail.getProdLine());
				dalPricingDetail.setProdTread(pricingDetail.getTread());
				dalPricingDetail.setComments(pricingDetail.getComments());
				dalPricingDetail.setDalPricingHeader(dalPricingHeader);
			}

			dalPricingDetailsList.add(dalPricingDetail);
		}

		return dalPricingDetailsList;
	}

	public String trimPercentage(String str) {
		if (!StringUtils.isEmpty(str)) {
			if (str.charAt(str.length() - 1) == '%') {
				str = str.replace(str.substring(str.length() - 1), "");
				return str;
			} else {
				return str;
			}
		}
		return str;
	}

	/*private boolean validatePartProdTreadOld(List<PricingDetail> pricingDetailsList) {
		boolean isValidated = false;
		List<String> partValidated = null;
		List<String> prodLineValidated = null;
		List<String> prodTreadValidated = null;
		boolean validatePart = false;
		boolean validateProdLine = false;
		boolean validateProdTread = false;
		List<String> partNumber = new ArrayList<String>();
		List<String> prodLine = new ArrayList<String>();
		List<String> prodTread = new ArrayList<String>();
		Map<String, Object> queryPart = new HashMap<>();
		Map<String, Object> queryProdLine = new HashMap<>();
		Map<String, Object> queryTread = new HashMap<>();
		for (PricingDetail pricingDetail : pricingDetailsList) {
			if (!StringUtils.isEmpty(pricingDetail.getPart()) && !StringUtils.isEmpty(pricingDetail.getProdLine())
					&& !StringUtils.isEmpty(pricingDetail.getTread())) {
				partNumber.add(pricingDetail.getPart());
				queryPart.put("partNumber", partNumber);
				partValidated = baseDao.getListFromNamedQueryWithParameter("DalPartMaster.getPartNumber", queryPart);
				validatePart = arraysMatch(partNumber, partValidated);
				prodLine.add(pricingDetail.getProdLine());
				queryProdLine.put("productLine", prodLine);
				prodLineValidated = baseDao.getListFromNamedQueryWithParameter("DalPartMaster.getProductLine",
						queryProdLine);
				validateProdLine = arraysMatch(prodLine, prodLineValidated);
				prodTread.add(pricingDetail.getTread());
				queryTread.put("treadDesc", prodTread);
				prodTreadValidated = baseDao.getListFromNamedQueryWithParameter("DalPartMaster.getTreadDesc",
						queryTread);
				validateProdTread = arraysMatch(prodTread, prodTreadValidated);
				if (validatePart && validateProdLine && validateProdTread) {
					isValidated = true;
				}
			} else {
				isValidated = false;
				break;
			}

		}

		return isValidated;

	}*/

	private boolean validatePartProdTread(PricingHeader pricingHeader) {
		List<PricingDetail> pricingDetailsList = pricingHeader.getPricingDetailList();
		boolean isValidated = false;
		String validationMessage = "";
		List<String> partNumber = new ArrayList<String>();
		String invalidPartNumber = "";
		String invalidProdLine = "";
		String invalidProdTread = "";
		List<String> prodLine = new ArrayList<String>();
		List<String> prodTread = new ArrayList<String>();
		Map<String, Object> queryPart = new HashMap<>();
		Map<String, Object> queryProdLine = new HashMap<>();
		Map<String, Object> queryTread = new HashMap<>();
		for (PricingDetail pricingDetail : pricingDetailsList) {
			if (!StringUtils.isEmpty(pricingDetail.getPart()) && !StringUtils.isEmpty(pricingDetail.getProdLine())
					&& !StringUtils.isEmpty(pricingDetail.getTread())) {
				partNumber.add(pricingDetail.getPart());
				prodLine.add(pricingDetail.getProdLine());
				prodTread.add(pricingDetail.getTread());

			}
		}
		isValidated = true;
		Set<String> partNumberSet = new HashSet<String>(partNumber);
		for (Iterator<String> iterator = partNumberSet.iterator(); iterator.hasNext();) {
			String partNumb = (String) iterator.next();
			queryPart.put("partNumber", partNumb);
			if (baseDao.getListFromNamedQueryWithParameter("DalPartMaster.getPartNumber", queryPart).isEmpty()) {
				invalidPartNumber = invalidPartNumber + "," + partNumb;
				isValidated = false;
			}

		}
		if (!"".equalsIgnoreCase(invalidPartNumber)) {
			validationMessage = validationMessage + "Incorrect Part Numbers: " + invalidPartNumber.substring(1);
		}
		Set<String> prodLineSet = new HashSet<String>(prodLine);
		for (Iterator<String> iterator = prodLineSet.iterator(); iterator.hasNext();) {
			String prodLin = (String) iterator.next();
			queryProdLine.put("productLine", prodLin);
			if (baseDao.getListFromNamedQueryWithParameter("DalPartMaster.getProductLine", queryProdLine).isEmpty()) {
				invalidProdLine = invalidProdLine + "," + prodLin;
				isValidated = false;
			}

		}
		if (!"".equalsIgnoreCase(invalidProdLine)) {
			validationMessage = validationMessage + "<br>" + "Incorrect Prod Line: " + invalidProdLine.substring(1);
		}
		Set<String> prodTreadSet = new HashSet<String>(prodTread);
		for (Iterator<String> iterator = prodTreadSet.iterator(); iterator.hasNext();) {
			String prodTrad = (String) iterator.next();
			queryTread.put("treadDesc", prodTrad);
			if (baseDao.getListFromNamedQueryWithParameter("DalPartMaster.getTreadDesc", queryTread).isEmpty()) {
				invalidProdTread = invalidProdTread + "," + prodTrad;
				isValidated = false;
			}

		}
		if (!"".equalsIgnoreCase(invalidProdTread)) {
			validationMessage = validationMessage + "<br>" + "Incorrect Prod Tread: " + invalidProdTread.substring(1);
		}
		if (!isValidated) {
			pricingHeader.setValidationMessage(validationMessage);
		}
		return isValidated;

	}

	/*public boolean arraysMatch(List<String> partNumber, List<String> partValidated) {
		int count = 0;
		List<String> match = new ArrayList<String>(partValidated);
		if (!partNumber.isEmpty()) {
			for (String element : partNumber) {
				if (match.contains(element)) {
					count++;
				}
			}
		}
		if (partNumber.size() == count) {
			return true;
		}
		return false;
	}*/

	private boolean validateCustomerId(PricingHeader pricingHeader) {
		boolean validate = false;
		String custId = pricingHeader.getCustomerId();
		List<String> dalCustomer = null;
		Map<String, Object> queryParam = new HashMap<>();
		if (!StringUtils.isEmpty(custId)) {
			queryParam.put("customerNumber", custId);
			dalCustomer = baseDao.getByType("DalCustomer.getCustomerId", queryParam);
			/*
			 * if(!StringUtils.isEmpty(dalCustomer.get(0))){ validate = true; }
			 */
			if (!dalCustomer.isEmpty()) {
				validate = true;
			}
		}
		if (!validate) {
			pricingHeader.setValidationMessage("Entered Customer Id is not valid");
		}
		return validate;

	}
}
