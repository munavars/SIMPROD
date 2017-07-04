package com.ytc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytc.common.model.DropDown;
import com.ytc.dal.IDataAccessLayer;
import com.ytc.dal.model.DalFrequency;
import com.ytc.service.ICcmEmailService;
import com.ytc.service.ICcmService;
import com.ytc.service.IProgramEmailService;
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
	
}
