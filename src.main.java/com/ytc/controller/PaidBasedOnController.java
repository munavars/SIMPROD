package com.ytc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytc.service.IPaidBasedOnService;





@Controller
@RequestMapping(value = "/v1/paid")
public class PaidBasedOnController extends BaseController  {

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public @ResponseBody List<String> getDetail(HttpServletRequest request, @PathVariable String name) {
		return getService(request).getDetail(name);
	}
	

	private IPaidBasedOnService getService(HttpServletRequest request) {
		return getServiceLocator(request).getPaidBasedOnService();
	}

}
