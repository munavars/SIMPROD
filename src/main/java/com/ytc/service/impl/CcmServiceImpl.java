package com.ytc.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.CcmDetails;
import com.ytc.common.model.DropDown;
import com.ytc.constant.ProgramConstant;
import com.ytc.constant.QueryConstant;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalFrequency;
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
	
	
	public boolean createMemoData(Integer id){	
		byte [] excelArray=new ExcelGenerator().generateExcel(baseDao, id);
		ccmEmailService.sendEmailData(excelArray);
			
		return true;
	}
	
	public List<CcmDetails> getCCMDetails(String frequency, String bu, String beginDate, String endDate){	
		List<CcmDetails> ccmList = new ArrayList<CcmDetails>();
		DecimalFormat format = new DecimalFormat("#,###.00");
		String sql=QueryConstant.CCM_REPORT;
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("bu", bu);
		queryParams.put("frequency", frequency);
		queryParams.put("beginDate", beginDate);
		queryParams.put("endDate", endDate);
		List<Object> resultList =baseDao.getListFromNativeQuery(sql, queryParams);
		for (Iterator<Object> iterator = resultList.iterator(); iterator.hasNext();) {
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
		}
		
					
		return ccmList;
	}
	
}
