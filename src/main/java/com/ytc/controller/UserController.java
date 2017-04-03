package com.ytc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytc.common.model.User;
import com.ytc.common.result.ModelResult;
import com.ytc.service.ISecurityService;





@Controller
@RequestMapping(value = "/v1/login")
public class UserController extends BaseController  {

	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public @ResponseBody ModelResult<User> getDetail(HttpServletRequest request, @PathVariable String userName) {
		return new ModelResult<User>(getService(request).getUser(userName));
	}
	

	private ISecurityService getService(HttpServletRequest request) {
		return getServiceLocator(request).getSecurityService();
	}

}
