package com.ytc.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ytc.controller.BaseController;

@Controller
@RequestMapping("/login")
public class SecurityController extends BaseController {



	@RequestMapping(value = "", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		return "login";
	}



	
}
