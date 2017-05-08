package com.ytc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping("/")
public class TestController  extends BaseController {

		@RequestMapping(value = "/customer", method = RequestMethod.GET)
		public String dashboard(HttpServletRequest request, Model model) {
			//Need to call service implementation here
			return "customer_details_dealertire_latest";
		}
		
		@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
		public String index(HttpServletRequest request, Model model) {
			//Need to call service implementation here
			return "index";
		}
}
