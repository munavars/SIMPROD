package com.ytc.service;

import java.util.List;

import com.ytc.common.model.DropDown;

public interface ICcmService {

	List<DropDown> getFrequencyDropDownList(String namedQueryValue);
}
