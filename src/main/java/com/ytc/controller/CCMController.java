/**
 * 
 */
package com.ytc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytc.common.model.DropDown;

import com.ytc.service.ICcmService;
import com.ytc.service.ServiceContext;


@Controller
@RequestMapping(value = "/")
public class CCMController extends BaseController {

	@Autowired
	private ServiceContext serviceContext;
	

	@RequestMapping(value = "/ccm/v1/getFrequencyDropDown", method = RequestMethod.GET)
	public @ResponseBody List<DropDown> getSelectValueDetails(HttpServletRequest request) {
		List<DropDown> dropdownList = null;
		dropdownList = (List<DropDown>)(getService(request).getFrequencyDropDownList("DalFrequency.getAllDetailsWithSort"));
		return dropdownList;
	}
	private ICcmService getService(HttpServletRequest request) {
		return getServiceLocator(request).getCcmService();
	}

	
}
