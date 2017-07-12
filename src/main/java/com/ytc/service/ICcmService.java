package com.ytc.service;

import java.util.List;

import com.ytc.common.model.CcmDetails;
import com.ytc.common.model.DropDown;

public interface ICcmService {

	List<DropDown> getFrequencyDropDownList(String namedQueryValue);

	boolean createMemoData(Integer id);
	
	List<CcmDetails> getCCMDetails(String frequency, String bu, String beginDate, String endDate);
}
