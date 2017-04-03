package com.ytc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytc.common.model.User;
import com.ytc.common.result.ModelResult;
import com.ytc.service.ISecurityService;

@Controller
@RequestMapping("/login")
public class SecurityController extends BaseController {


	@RequestMapping(value = "", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		return "login";
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public @ResponseBody ModelResult<User> getDetail(HttpServletRequest request, @PathVariable String userId) {
		return new ModelResult<User>(getService(request).authenticateUIUser(userId));
	}


	private ISecurityService getService(HttpServletRequest request) {
		return getServiceLocator(request).getSecurityService();
	}


}
