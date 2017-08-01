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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ytc.common.enums.BusinessUnitDescriptionEnum;
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
import com.ytc.dal.model.DalWorkflowStatus;
import com.ytc.helper.PricingServiceHelper;
import com.ytc.helper.PricingWorkflowServiceHelper;
import com.ytc.service.IPricingEmailService;
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
	
	@Autowired
	private IPricingEmailService pricingEmailService;
	
	private static Logger LOGGER = Logger.getLogger(PricingUpdateServiceImpl.class.getName());

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
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
			e.printStackTrace();
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
			List<DalPricingDetail> deletedDalPricingDetailList = null;
			if (validateCustomerId(pricingHeader) && validatePartProdTread(pricingHeader)) {
								
				/**Set Pricing request status*/
				if(pricingHeader.getStatus() != null){
					dalPricingHeader.setDalStatus(baseDao.getById(DalStatus.class, Integer.valueOf(pricingHeader.getStatus())));
				}
				
				if(!pricingHeader.getProgramButton().isApprover()){
					
					checkAndUpdatePricingHeaderData(dalPricingHeader, pricingHeader);
					
					dalPricingHeader.setUserComments(
							StringUtils.isEmpty(pricingHeader.getUserComments()) ? "" : pricingHeader.getUserComments());

					/** Save pricing Detail section information */
					if(pricingHeader.getProgramButton().isCreater()){
						/***
						 * Only if the logged in user is creater, there is a chance that, below information will be modified.
						 */
						deletedDalPricingDetailList = updatePricingDetailsData(pricingHeader, dalPricingHeader);
						
						if(deletedDalPricingDetailList != null && !deletedDalPricingDetailList.isEmpty()){
							
							for(DalPricingDetail dalPricingDetail : deletedDalPricingDetailList){
								dalPricingHeader.getDalPricingDetailList().remove(dalPricingDetail);
							}
						}
					}
				}				

				pricingWorkflowService.updateWorkflowDetails(dalPricingHeader, pricingHeader, serviceContext.getEmployee());
				
				DalPricingHeader updatedHeader = baseDao.update(dalPricingHeader);

				updatePricingDetailsDataForNewRecord(pricingHeader, updatedHeader);
				
				pricingEmailService.sendEmailData(pricingHeader, dalPricingHeader);
				
				/**Reset status value*/
				pricingHeader.setStatus(dalPricingHeader.getDalStatus().getType());
				
				/**Reset Program workflow*/
				getUpdatedWorkflowDetails(pricingHeader, dalPricingHeader);
				
				pricingHeader.setSuccess(true);
			}
		}
	}
	
	private void getUpdatedWorkflowDetails(PricingHeader pricingHeader, DalPricingHeader dalPricingHeader) {
		//DalWorkflowStatus.getProgramWorkflowDetails
		Map<String, Object> inputParam = new HashMap<String,Object>();
		inputParam.put("pricingHeaderId", dalPricingHeader.getId());
		List<DalWorkflowStatus> dalWorkflowStatusList = baseDao.getListFromNamedQueryWithParameter("DalWorkflowStatus.getPricingWorkflowDetails", inputParam);

		PricingWorkflowServiceHelper.populateWorkflowStatusDataAfterUpdate(pricingHeader, dalWorkflowStatusList);
	}

	private void updatePricingDetailsDataForNewRecord(PricingHeader pricingHeader, DalPricingHeader dalPricingHeader) {
		/**
		 * When delete and insert happens in same call along with Pricing header, below excpetion is thrown. So for now, create is done separately.
		 * Exception message faced - 'org.hibernate.AssertionFailure: possible non-threadsafe access to session'
		 * */
		if(pricingHeader.getProgramButton().isCreater() &&  pricingHeader != null && dalPricingHeader != null){
			List<DalPricingDetail> addedDalPricingDetailList = PricingServiceHelper.updatedNewlyAddedRow(pricingHeader, dalPricingHeader);
			if(addedDalPricingDetailList != null && !addedDalPricingDetailList.isEmpty()){
				for(DalPricingDetail dalPricingDetail : addedDalPricingDetailList){
					baseDao.create(dalPricingDetail);	
				}
				
				populatePricingDetailData(pricingHeader, addedDalPricingDetailList);
			}
		}		
	}	


	private List<DalPricingDetail> updatePricingDetailsData(PricingHeader pricingHeader, DalPricingHeader dalPricingHeader) {
		List<DalPricingDetail> deletedDalPricingDetailList = null;
		if(pricingHeader.getProgramButton().isCreater() &&  pricingHeader != null && dalPricingHeader != null){
			Set<Integer> idPresentCurrently = PricingServiceHelper.getUpdatedIds(pricingHeader);
			
			/*Map<String, Object> inputParam = new HashMap<String, Object>();
			inputParam.put("pricingHeaderId", dalPricingHeader.getId());
			List<DalPricingDetail> dalPricingDetailList = baseDao.getListFromNamedQueryWithParameter("DalPricingDetail.getAllRecordsForPricingHeaderId", inputParam);*/
			if(dalPricingHeader.getDalPricingDetailList() != null && !dalPricingHeader.getDalPricingDetailList().isEmpty()){
				for(Iterator<DalPricingDetail> iterator = dalPricingHeader.getDalPricingDetailList().iterator(); iterator.hasNext(); ){
					DalPricingDetail dalPricingDetail = iterator.next();
					if(idPresentCurrently.contains(dalPricingDetail.getId())){
						/**
						 * In UI, INVOICE demand table is read only and user cannot edit the existing.
						 * So if the ID column already has any value, then there will not be any modification.
						 * */
						continue;
					}
					else{
						if(deletedDalPricingDetailList == null){
							deletedDalPricingDetailList = new ArrayList<DalPricingDetail>();
						}
						deletedDalPricingDetailList.add(dalPricingDetail);
					}
				}
			}
			/*if(deletedDalPricingDetailList != null && !deletedDalPricingDetailList.isEmpty()){
				for(DalPricingDetail dalPricingDetail : deletedDalPricingDetailList){
					baseDao.delete(DalPricingDetail.class, dalPricingDetail.getId());	
				}
			}*/
			
		}
		return deletedDalPricingDetailList;
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
		
		dalPricingHeader.setSbm(pricingHeader.getSbmCheck());
	}

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
				
				if(pricingHeader.getTermCode() != null && !pricingHeader.getTermCode().isEmpty()){
					parameters.put("code", pricingHeader.getTermCode());
					List<DalPricingTermCodes> dalPricingTermCodesList = baseDao.getByType("DalPricingTermCodes.getCode",
							parameters);
					for (DalPricingTermCodes dalPricingTermCodes : dalPricingTermCodesList) {
						dalPricingHeader.setTermCodes(dalPricingTermCodes);
						parameters.remove("code", pricingHeader.getTermCode());
					}	
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
				dalPricingHeader.setSbm(pricingHeader.getSbmCheck());
				
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
				
				DalPricingHeader dalPricingHeaderReturned = baseDao.create(dalPricingHeader);
				
				if(dalPricingHeaderReturned != null){
					pricingHeader.setId(dalPricingHeaderReturned.getId());
					pricingEmailService.sendEmailData(pricingHeader, dalPricingHeader);
				}
				
				/**Reset status value*/
				pricingHeader.setId(dalPricingHeader.getId());
				pricingHeader.setStatus(dalPricingHeader.getDalStatus().getType());
				
				/**Reset Program work flow*/
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
					dalPricingDetail.setInvoiceDisc(Double.valueOf(PricingServiceHelper.trimPercentage(pricingDetail.getInvoiceDisc())));
				}
				if (!StringUtils.isEmpty(pricingDetail.getNetPrice())) {
					dalPricingDetail.setNetPrice(Double.valueOf(pricingDetail.getNetPrice()));
				}

				dalPricingDetail.setIsBonusableUnits(pricingDetail.getBonusUnits());
				dalPricingDetail.setIsCommissionable(pricingDetail.getCommissionable());
				dalPricingDetail.setPartNumber(pricingDetail.getPart());
				dalPricingDetail.setBusinessUnit(pricingDetail.getBusinessUnit());
				dalPricingDetail.setProdLine(pricingDetail.getProdLine());
				dalPricingDetail.setProdTread(pricingDetail.getTread());
				dalPricingDetail.setComments(pricingDetail.getComments());
				dalPricingDetail.setDalPricingHeader(dalPricingHeader);
			}

			dalPricingDetailsList.add(dalPricingDetail);
		}

		return dalPricingDetailsList;
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
		String invalidBusinessUnit = "";
		List<String> prodLine = new ArrayList<String>();
		List<String> prodTread = new ArrayList<String>();
		Map<String, Object> queryPart = new HashMap<>();
		Map<String, Object> queryProdLine = new HashMap<>();
		Map<String, Object> queryTread = new HashMap<>();
		Set<String> businessUnitSet = new HashSet<String>();
		for (PricingDetail pricingDetail : pricingDetailsList) {
			if (!StringUtils.isEmpty(pricingDetail.getPart())) {
				partNumber.add(pricingDetail.getPart());
			}
			if (!StringUtils.isEmpty(pricingDetail.getProdLine())) {
				prodLine.add(pricingDetail.getProdLine());
			}
			if (!StringUtils.isEmpty(pricingDetail.getTread())) {
				prodTread.add(pricingDetail.getTread());
			}
			if (!StringUtils.isEmpty(pricingDetail.getBusinessUnit())) {
				businessUnitSet.add(pricingDetail.getBusinessUnit());
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

		for (Iterator<String> iterator = businessUnitSet.iterator(); iterator.hasNext();) {
			String businessUnit = (String) iterator.next();
			boolean isValid = false;
			for (BusinessUnitDescriptionEnum c : BusinessUnitDescriptionEnum.values()){
				if(c.getBusinessUnitDescription().equalsIgnoreCase(businessUnit)){
					isValid = true;
					break;
				}
			}
			if (!isValid) {
				invalidBusinessUnit = invalidBusinessUnit + "," + businessUnit;
				isValidated = false;
			}

		}
		if (!"".equalsIgnoreCase(invalidBusinessUnit)) {
			validationMessage = validationMessage + "<br>" + "Incorrect Business Unit: " + invalidBusinessUnit.substring(1);
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
	
	private void populatePricingDetailData(PricingHeader pricingHeader, List<DalPricingDetail> addedDalPricingDetailList) {
		if(pricingHeader != null && addedDalPricingDetailList != null){
			if(pricingHeader.getPricingDetailList() != null){
				pricingHeader.getPricingDetailList().clear();
			}
			else{
				pricingHeader.setPricingDetailList(new ArrayList<PricingDetail>());
			}
			Set<Integer> uniqueIds = null;
			if(!addedDalPricingDetailList.isEmpty()){
				uniqueIds = new HashSet<Integer>();
				for(DalPricingDetail dalPricingDetail : addedDalPricingDetailList){
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
}
