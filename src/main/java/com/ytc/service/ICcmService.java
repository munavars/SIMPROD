package com.ytc.service;

import java.util.List;

import com.ytc.common.model.CcmDetails;
import com.ytc.common.model.DropDown;

public interface ICcmService {

	List<DropDown> getFrequencyDropDownList(String namedQueryValue);

	boolean createMemoData(Integer id);
	
	List<CcmDetails> getCCMDetails(String frequency, String bu, Integer period);

	List<DropDown> getPeriodDropDownList();

	int saveCCMDetails(Integer id, double adjustedAmount, double adjustedCredit, String string);
}
