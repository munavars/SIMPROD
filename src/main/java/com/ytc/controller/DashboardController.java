package com.ytc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

	

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request, Model model) {
		//Need to call service implementation here
		return "index";
	}

	
}
