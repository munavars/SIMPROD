package com.ytc.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.AccuralCcmData;
import com.ytc.common.model.CcmDetails;
import com.ytc.common.model.DropDown;
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
	@Autowired
	private IDataAccessLayer baseDao;
	
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
			ccmDetails.setCreditBasedOn(populateCreditBasedOn(ccmDetails));
			ccmList.add(ccmDetails);
		}
/*		for (Iterator<Object> iterator = resultList.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			CcmDetails ccmDetails=new CcmDetails();
			ccmDetails.setBu(null!=obj[0]?obj[0].toString():"");
			ccmDetails.setAccountManager(null!=obj[2]?obj[2].toString():"");
			ccmDetails.setZoneManager(null!=obj[1]?obj[1].toString():"");
			ccmDetails.setFrequency(null!=obj[3]?obj[3].toString():"");
			ccmDetails.setProgramId(null!=obj[4]?obj[4].toString():"");
			ccmDetails.setProgramName(null!=obj[5]?obj[5].toString():"");
			ccmDetails.setDescription(null!=obj[6]?obj[6].toString():"");
			ccmDetails.setPaidBasedOn(null!=obj[7]?obj[7].toString():"");
			ccmDetails.setGuarantee(null!=obj[8]?(obj[8].toString()):"");
			ccmDetails.setAmount(((null!=obj[9])&&(!"0.0".equalsIgnoreCase(obj[9].toString())))?format.format(Double.parseDouble(obj[9].toString())):"0");
			ccmDetails.setAmountType(null!=obj[10]?obj[10].toString():"");
			ccmDetails.setCreditAccured(((null!=obj[11])&&(!"0.0".equalsIgnoreCase(obj[11].toString())))?format.format(Double.parseDouble(obj[11].toString())):"0");
			ccmDetails.setEarned(0.0);
			ccmDetails.setCreditEarned(0.0);
			ccmDetails.setReview("");
			ccmDetails.setComments("");
			ccmDetails.setCreditBasedOn("");
			ccmDetails.setCreditMemo("");
			ccmDetails.setProgramStatus("");
			ccmDetails.setDocNo("");
			ccmDetails.setDocDate("");
			ccmDetails.setBillToNo(null!=obj[12]?obj[12].toString():"");
			ccmDetails.setBillToName(null!=obj[13]?obj[13].toString():"");
			ccmDetails.setUnits(null!=obj[14]? Double.valueOf((obj[14]).toString()).intValue():0);
			ccmDetails.setBonusableUnits(null!=obj[15]?Double.valueOf((obj[15]).toString()).intValue():0);
			ccmDetails.setNadUnits(null!=obj[16]?Double.valueOf((obj[16]).toString()).intValue():0);
			ccmDetails.setUnitsNad(null!=obj[17]?Double.valueOf((obj[17]).toString()).intValue():0);
			ccmDetails.setBonusableNad(null!=obj[18]?Double.valueOf((obj[18]).toString()).intValue():0);
			ccmDetails.setInvSales(((null!=obj[19])&&(!"0.0".equalsIgnoreCase(obj[19].toString())))?format.format(Double.parseDouble(obj[19].toString())):"0");
			ccmDetails.setBonusableSales(((null!=obj[20])&&(!"0.0".equalsIgnoreCase(obj[20].toString())))?format.format(Double.parseDouble(obj[20].toString())):"0");
			ccmDetails.setNadSales(((null!=obj[21])&&(!"0.0".equalsIgnoreCase(obj[21].toString())))?format.format(Double.parseDouble(obj[21].toString())):"0");
			ccmDetails.setInvSalesNad(((null!=obj[22])&&(!"0.0".equalsIgnoreCase(obj[22].toString())))?format.format(Double.parseDouble(obj[22].toString())):"0");
			ccmDetails.setBonusableSalesNad(((null!=obj[23])&&(!"0.0".equalsIgnoreCase(obj[23].toString())))?format.format(Double.parseDouble(obj[23].toString())):"0");
			ccmDetails.setWarranty(((null!=obj[24])&&(!"0.0".equalsIgnoreCase(obj[24].toString())))?format.format(Double.parseDouble(obj[24].toString())):"0");
			ccmDetails.setPayTo(null!=obj[25]?obj[25].toString():"");
			Timestamp fromDate=(Timestamp) obj[26];
			Timestamp toDate=(Timestamp) obj[27];
			ccmDetails.setBeginDate(null!=obj[26]?ProgramServiceHelper.convertDateToString(fromDate, ProgramConstant.DATE_FORMAT):"");
			ccmDetails.setEndDate(null!=obj[27]?ProgramServiceHelper.convertDateToString(toDate, ProgramConstant.DATE_FORMAT):"");
			ccmDetails.setGlCode(null!=obj[28]?obj[28].toString():"");
			ccmDetails.setPaymentMethod(null!=obj[29]?obj[29].toString():"");
			ccmDetails.setBaseId(null!=obj[30]?obj[30].toString():"");
			ccmList.add(ccmDetails);
		}*/
					
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
			System.out.println("Exception occured in saveCCMDetails"+e.getCause());
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
			//Sending Email
			ccmEmailService.sendEmailData(dalCcmAccrualData,comments,dalpgm,baseDao);

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
	
}
