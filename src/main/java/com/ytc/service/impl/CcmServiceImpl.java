package com.ytc.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.AccuralCcmData;
import com.ytc.common.model.CcmDetails;
import com.ytc.common.model.DropDown;
import com.ytc.common.params.CreditMemoParams;
import com.ytc.constant.ProgramConstant;
import com.ytc.constant.QueryConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalCcmAccrualData;
import com.ytc.dal.model.DalCcmAudit;
import com.ytc.dal.model.DalFrequency;
import com.ytc.dal.model.DalProgramDetail;
import com.ytc.helper.ProgramServiceHelper;
import com.ytc.service.ICcmEmailService;
import com.ytc.service.ICcmService;
import com.ytc.service.util.ExcelGenerator;


class CcmServiceImpl implements ICcmService{
	
	private org.slf4j.Logger logger = LoggerFactory.getLogger(CcmServiceImpl.class);
	
	@Autowired
	private IDataAccessLayer baseDao;

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private ICcmEmailService ccmEmailService;  

	public List<DropDown> getFrequencyDropDownList(String namedQueryValue){
		List<DropDown> dropdownList = null;
		if(namedQueryValue != null){
			List<DalFrequency> dalFrequencyList =  baseDao.getListFromNamedQuery(namedQueryValue);
			if(dalFrequencyList != null){
				for(DalFrequency dalFrequency : dalFrequencyList){
					if(!"0".equalsIgnoreCase(dalFrequency.getFrequency())){
						DropDown dropDown = new DropDown();
						dropDown.setKey(dalFrequency.getFrequency());
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

	public List<DropDown> getPeriodDropDownList(){
		List<DropDown> dropdownList = null;
		String sql=QueryConstant.CCM_PERIOD;
		Map<String, Object> queryParams = new HashMap<>();
		//List<DalFrequency> dalFrequencyList =  baseDao.getListFromNamedQuery(namedQueryValue);
		List<Object> resultList =baseDao.getListFromNativeQuery(sql, queryParams);
		if(resultList != null){
			for (Iterator<Object> iterator = resultList.iterator(); iterator.hasNext();) {
				Object[] obj = (Object[]) iterator.next();
				DropDown dropDown = new DropDown();
				dropDown.setKey(obj[1].toString());
				dropDown.setValue(obj[0].toString());
				if(dropdownList == null){
					dropdownList = new ArrayList<DropDown>();
				}
				dropdownList.add(dropDown);
			}

		}


		return dropdownList;
	}	
	public boolean createMemoData(Integer id){	
		byte [] excelArray=new ExcelGenerator().generateExcel(baseDao, id);
		ccmEmailService.sendEmailData(excelArray);

		return true;
	}

	public List<CcmDetails> getCCMDetails(String frequency, String bu, Integer period, String status){	
		List<CcmDetails> ccmList = new ArrayList<CcmDetails>();
		DecimalFormat format = new DecimalFormat("#,##0.00");
		DecimalFormat unitFormat = new DecimalFormat("#,##0");
		String sql=QueryConstant.CCM_REPORT_NEW;
		Map<String, Object> queryParams = new HashMap<>();	
		List<String> selectedStatus=Arrays.asList(status.split(","));
		queryParams.put("bu", bu);
		queryParams.put("frequency", frequency);
		queryParams.put("period", period);
		queryParams.put("status", selectedStatus);
		List<DalCcmAccrualData> resultList =baseDao.list(DalCcmAccrualData.class, sql, queryParams);
		//List<Object> resultList =baseDao.getListFromNativeQuery(sql, queryParams);

		for (Iterator<DalCcmAccrualData> iterator = resultList.iterator(); iterator.hasNext();) {
			DalCcmAccrualData dalCcmAccrualData = (DalCcmAccrualData) iterator.next();
			CcmDetails ccmDetails=new CcmDetails();
			ccmDetails.setCcmId(dalCcmAccrualData.getId().toString());
			ccmDetails.setBu(dalCcmAccrualData.getBu());
			ccmDetails.setAccountManager(dalCcmAccrualData.getAccountManager());
			ccmDetails.setZoneManager(dalCcmAccrualData.getZoneManager());
			ccmDetails.setFrequency(dalCcmAccrualData.getFrequency());
			ccmDetails.setProgramId(dalCcmAccrualData.getProgramId().toString());
			ccmDetails.setProgramName(dalCcmAccrualData.getProgramName());
			ccmDetails.setDescription(null!=dalCcmAccrualData.getDescription()?dalCcmAccrualData.getDescription():"");
			ccmDetails.setPaidBasedOn(dalCcmAccrualData.getPaidBasedOn());
			ccmDetails.setGuarantee(dalCcmAccrualData.getGuarantee());
			String amount="";
			if("$".equalsIgnoreCase(dalCcmAccrualData.getAmountType())){
				amount=dalCcmAccrualData.getAmountType()+format.format(dalCcmAccrualData.getAmount());
			}else{
				amount=format.format(dalCcmAccrualData.getAmount())+dalCcmAccrualData.getAmountType();
			}
			ccmDetails.setAmount(amount);
			ccmDetails.setAmountType(dalCcmAccrualData.getAmountType());
			ccmDetails.setCreditAccured(format.format(dalCcmAccrualData.getCreditAccured()));
			ccmDetails.setEarned(format.format(dalCcmAccrualData.getEarned()));
			ccmDetails.setCreditEarned(format.format(dalCcmAccrualData.getCreditEarned()));
			ccmDetails.setVariance("");
			ccmDetails.setReview(dalCcmAccrualData.getProgramStatus());
			ccmDetails.setSubmitForApproval("");
			sql=QueryConstant.CCM_COMMENTS;
			queryParams = new HashMap<>();
			queryParams.put("id", dalCcmAccrualData.getId());
			String comments="";
			List<Object> commentList =baseDao.getListFromNativeQuery(sql, queryParams);	
			for (Object object : commentList) {
				comments=comments+object.toString()+"\n";
			}
			if(commentList.isEmpty()){
				comments="Comments Not Available";
			}
			ccmDetails.setComments(comments);			
			ccmDetails.setCreditMemo("");
			ccmDetails.setProgramStatus("");
			ccmDetails.setDocNo("");
			ccmDetails.setDocDate("");
			ccmDetails.setBillToNo(dalCcmAccrualData.getBillToNo());
			ccmDetails.setBillToName(dalCcmAccrualData.getBillToName());
			ccmDetails.setUnits(unitFormat.format(dalCcmAccrualData.getUnits()));
			ccmDetails.setBonusableUnits(unitFormat.format(dalCcmAccrualData.getBonusableUnits()));
			ccmDetails.setNadUnits(unitFormat.format(dalCcmAccrualData.getNadUnits()));
			ccmDetails.setUnitsNad(unitFormat.format(dalCcmAccrualData.getUnitsplusNad()));
			ccmDetails.setBonusableNad(unitFormat.format(dalCcmAccrualData.getBonusableUnitsplusNad()));
			ccmDetails.setInvSales(format.format(dalCcmAccrualData.getInvSales()));
			ccmDetails.setBonusableSales(format.format(dalCcmAccrualData.getBonusableSales()));
			ccmDetails.setNadSales(format.format(dalCcmAccrualData.getNadSales()));
			ccmDetails.setInvSalesNad(format.format(dalCcmAccrualData.getInvSalesplusNad()));
			ccmDetails.setBonusableSalesNad(format.format(dalCcmAccrualData.getBonusableSalesplusNad()));
			ccmDetails.setWarranty(format.format(dalCcmAccrualData.getWarranty()));
			ccmDetails.setPayTo(dalCcmAccrualData.getPayTo());
			ccmDetails.setBeginDate(ProgramServiceHelper.convertDateToString(dalCcmAccrualData.getBeginDate().getTime(), ProgramConstant.DATE_FORMAT));
			ccmDetails.setEndDate(ProgramServiceHelper.convertDateToString(dalCcmAccrualData.getEndDate().getTime(), ProgramConstant.DATE_FORMAT));
			ccmDetails.setGlCode(dalCcmAccrualData.getGlCode());
			ccmDetails.setPaymentMethod(dalCcmAccrualData.getPaymentMethod());
			ccmDetails.setProgramStatus(dalCcmAccrualData.getProgramStatus());
			ccmDetails.setBaseId(dalCcmAccrualData.getBaseItemId().toString());
			ccmDetails.setEdit("");
			//ccmDetails.setCreditBasedOn(populateCreditBasedOn(ccmDetails));
			ccmDetails.setCreditBasedOn(format.format(dalCcmAccrualData.getPaidBasedOnValue()));
			ccmDetails.setAccrualStartDate(ProgramServiceHelper.convertDateToString(dalCcmAccrualData.getAccrualStartDate().getTime(), ProgramConstant.DATE_FORMAT));
			ccmDetails.setAccrualEndDate(ProgramServiceHelper.convertDateToString(dalCcmAccrualData.getAccrualEndDate().getTime(), ProgramConstant.DATE_FORMAT));
			ccmDetails.setTbp(dalCcmAccrualData.getTbp());
			ccmDetails.setDocNo(null!=dalCcmAccrualData.getDocNumber()?dalCcmAccrualData.getDocNumber():"");
			ccmDetails.setDocDate(null!=dalCcmAccrualData.getDocDate()?(ProgramServiceHelper.convertDateToString(dalCcmAccrualData.getDocDate().getTime(), ProgramConstant.DATE_FORMAT)):"");
			ccmList.add(ccmDetails);
		}
		

		return ccmList;
	}


	public String saveCCMDetails(AccuralCcmData accuralCcmData, String user){	
		String status="fail";
		try{
			DalCcmAudit dalCcmAudit=new DalCcmAudit();
			dalCcmAudit.setCcmId(accuralCcmData.getId());
			dalCcmAudit.setAdjustedAmount(accuralCcmData.getAdjustedAmount());
			dalCcmAudit.setAdjustedCredit(accuralCcmData.getAdjustedCredit());
			dalCcmAudit.setAdjustedUser(user);
			dalCcmAudit.setStatusFlag(accuralCcmData.getReviewFlag());
			dalCcmAudit.setComments(accuralCcmData.getComments());
			dalCcmAudit.setAdjustedDate(Calendar.getInstance());

			String sql=QueryConstant.CCM_UPDATE;	
			Map<String, Object> queryParams = new HashMap<>();	
			queryParams.put("id", accuralCcmData.getId());
			queryParams.put("adjustedAmount", accuralCcmData.getAdjustedAmount());
			queryParams.put("adjustedCredit", accuralCcmData.getAdjustedCredit());
			queryParams.put("user", user);

			baseDao.updateNative(sql, queryParams);

			baseDao.create(dalCcmAudit);

			submitCcmForApproval(accuralCcmData.getId(),accuralCcmData.getComments());

			status="success";

		}catch (Exception e){
			logger.error("Exception occured in saveCCMDetails"+e.getMessage());
			throw e;
		}
		return status;
	}

	public int submitCcmForApproval(Integer approvalList, String comments){	

		String hql=QueryConstant.CCM_LIST;
		Map<String, Object> queryParams = new HashMap<>();	
		queryParams.put("id", approvalList);		
		List<DalCcmAccrualData> ccmList=baseDao.list(DalCcmAccrualData.class, hql, queryParams);
		for (Iterator<DalCcmAccrualData> iterator = ccmList.iterator(); iterator.hasNext();) {
			DalCcmAccrualData dalCcmAccrualData = (DalCcmAccrualData) iterator.next();
			DalProgramDetail dalpgm=baseDao.getById(DalProgramDetail.class, dalCcmAccrualData.getProgramId());
			CreditMemoParams creditMemoParams=new CreditMemoParams();
			creditMemoParams.setPgmDetailId(dalCcmAccrualData.getProgramId());
			creditMemoParams.setStartDate(dalCcmAccrualData.getAccrualStartDate());
			creditMemoParams.setEndDate(dalCcmAccrualData.getAccrualEndDate());
			List<Object> billToList= ccmBillToData(creditMemoParams);
			String headerColumnsBillto[]={"CUSTOMER NAME","PROGRAM ID","PROGRAM NAME","PAID BASED ON","GUARANTEE","AMOUNT","TYPE","CORP_NO","CORP_NAME","UNITS_INCLUDED","UNITS_EXCLUDED","BONUSABLE_UNITS_INCLUDED","BONUSABLE_UNITS_EXCLUDED","NAD_UNITS_INLCUDED","NAD_UNITS_EXCLUDED","UNITS_PLUS_NAD_INLCUDED","UNITS_PLUS_NAD_EXCLUDED","BONUSABLE_UNITS_PLUS_NAD_INCLUDED","BONUSABLE_UNITS_PLUS_NAD_EXCLUDED","INV_SALES_INCLUDED","INV_SALES_EXCLUDED","BONUSABLE_SALES_INCLUDED","BONUSABLE_SALES_EXCLUDED","NAD_SALES_INCLUDED","NAD_SALES_EXCLUDED","INV_SALES_PLUS_NAD_INCLUDED","INV_SALES_PLUS_NAD_EXCLUDED","BONUSABLE_SALES_PLUS_NAD_INCLUDED","BONUSABLE_SALES_PLUS_NAD_EXCLUDED","WARRANTY_INCLUDED","WARRANTY_EXCLUDED","CREDIT_ACCRUED","BUSINESS_UNIT","FRQENCY","ZONE_MGR","ACCT_MGR","BEGIN_DATE","END_DATE"};
			byte [] excelBillToArray=new ExcelGenerator().generateExcelList("CcmBillToCreditMemo", billToList,headerColumnsBillto);
			List<Object> partList=ccmPartData(creditMemoParams);
			String headerColumnsPart[]={"CUSTOMER NAME","PROGRAM ID","PROGRAM NAME","CORP_NO","CORP_NAME","PRODUCT_LINE","TREAD","PAID BASED ON","AMOUNT","TYPE","UNITS_INCLUDED","UNITS_EXCLUDED","BONUSABLE_UNITS_INCLUDED","BONUSABLE_UNITS_EXCLUDED","NAD_UNITS_INLCUDED","NAD_UNITS_EXCLUDED","UNITS_PLUS_NAD_INLCUDED","UNITS_PLUS_NAD_EXCLUDED","BONUSABLE_UNITS_PLUS_NAD_INCLUDED","BONUSABLE_UNITS_PLUS_NAD_EXCLUDED","INV_SALES_INCLUDED","INV_SALES_EXCLUDED","BONUSABLE_SALES_INCLUDED","BONUSABLE_SALES_EXCLUDED","NAD_SALES_INCLUDED","NAD_SALES_EXCLUDED","INV_SALES_PLUS_NAD_INCLUDED","INV_SALES_PLUS_NAD_EXCLUDED","BONUSABLE_SALES_PLUS_NAD_INCLUDED","BONUSABLE_SALES_PLUS_NAD_EXCLUDED","WARRANTY_INCLUDED","WARRANTY_EXCLUDED","CREDIT_ACCRUED","BUSINESS_UNIT","ZONE_MGR","ACCT_MGR","GUARANTEE"};
			byte [] excelPartArray=new ExcelGenerator().generateExcelList("CcmPartCreditMemo", partList,headerColumnsPart);
			 //Sending Email
			Map<String, byte[]> attachment=new HashMap<String, byte[]>();
			attachment.put("CcmBillToCreditMemo", excelBillToArray);
			attachment.put("CcmPartCreditMemo", excelPartArray);
			ccmEmailService.sendEmailData(dalCcmAccrualData,comments,dalpgm,baseDao,attachment);

		}

		return 0;
	}

	public int updateCcmStatus(Integer id){	

		String sql=QueryConstant.CCM_UPDATE_STATUS;	
		Map<String, Object> queryParams = new HashMap<>();			
		//Update status in DB
		queryParams.put("id", id);
		queryParams.put("status", "Not Reviewed");
		baseDao.updateNative(sql, queryParams);		


		return 0;
	}


	public int updateCCMDetails(AccuralCcmData accuralCcmData){	

		String sql=QueryConstant.CCM_UPDATE_DOC;	
		Map<String, Object> queryParams = new HashMap<>();			
		queryParams.put("id", accuralCcmData.getId());
		queryParams.put("docNo", accuralCcmData.getDocNumber());
		queryParams.put("docDate", accuralCcmData.getDocDate());		
		baseDao.updateNative(sql, queryParams);				

		return 0;
	}

	public String populateCreditBasedOn(CcmDetails ccmDetails){

		String creditBasedOn="";
		int paidBased=Integer.parseInt(ccmDetails.getBaseId());
		if(paidBased==3){
			creditBasedOn=(null!=ccmDetails.getUnits()?ccmDetails.getUnits().toString():"");
		}

		if(paidBased==1){
			creditBasedOn=(null!=ccmDetails.getNadUnits()?ccmDetails.getNadUnits().toString():"");
		}

		if(paidBased==2){
			creditBasedOn=(null!=ccmDetails.getBonusableUnits()?ccmDetails.getBonusableUnits().toString():"");
		}

		if(paidBased==4){
			creditBasedOn=(null!=ccmDetails.getUnitsNad()?ccmDetails.getUnitsNad().toString():"");
		}

		if(paidBased==5){
			creditBasedOn=(null!=ccmDetails.getBonusableNad()?ccmDetails.getBonusableNad().toString():"");
		}

		if(paidBased==8){
			creditBasedOn=ccmDetails.getInvSales();
		}

		if(paidBased==6){
			creditBasedOn=ccmDetails.getNadSales();
		}
		if(paidBased==7){
			creditBasedOn=ccmDetails.getBonusableSales();
		}
		if(paidBased==9){
			creditBasedOn=ccmDetails.getInvSalesNad();
		}
		if(paidBased==10){
			creditBasedOn=ccmDetails.getBonusableSalesNad();
		}
		return creditBasedOn;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> ccmBillToData(CreditMemoParams params) {
		StoredProcedureQuery query =entityManager.createNamedStoredProcedureQuery("sp_CcmBillToCreditMemoForPandT");
		query.setParameter("pgmDetId", params.getPgmDetailId());	
		query.setParameter("startDate", params.getStartDate());
		query.setParameter("endDate", params.getEndDate());
		query.execute();
		List<Object> billToData = (List<Object>)query.getResultList();
		return billToData;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> ccmPartData(CreditMemoParams params) {
		StoredProcedureQuery query =entityManager.createNamedStoredProcedureQuery("sp_CcmPartCreditMemoForPandT");
		query.setParameter("pgmDetId", params.getPgmDetailId());
		query.setParameter("startDate", params.getStartDate());
		query.setParameter("endDate", params.getEndDate());
		query.execute();
		List<Object> partData = query.getResultList();
		return partData;
	}

}
