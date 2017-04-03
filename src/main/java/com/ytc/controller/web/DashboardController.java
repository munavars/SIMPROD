package com.ytc.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytc.controller.BaseController;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

	
	/* @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    public @ResponseBody String deploymentRates(HttpServletRequest request, Model model) throws JSONException {
		 //model.addAttribute("name", "value");
		 //or
		 //
		 //JSONObject json = new JSONObject();
	    // json.put("name", value);
		 return "index";
		 
	 }
*/
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request, Model model) {
		//Need to call service implementation here
		return "index";
	}

	
}
